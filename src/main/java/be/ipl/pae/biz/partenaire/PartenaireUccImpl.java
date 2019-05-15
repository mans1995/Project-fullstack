package be.ipl.pae.biz.partenaire;

import be.ipl.pae.biz.mobilite.MobiliteDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalServices;
import be.ipl.pae.dal.mobilite.MobiliteDao;
import be.ipl.pae.dal.partenaire.PartenaireDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.ArrayList;
import java.util.List;

public class PartenaireUccImpl implements PartenaireUcc {

  @Inject
  private PartenaireDao partenaireDao;
  @Inject
  private MobiliteDao mobiliteDao;
  @Inject
  private DalServices dalServices;

  @Override
  public void ajouterPartenaire(PartenaireDto partenaireDto, MobiliteDto mobiliteDto) {
    Partenaire partenaire = (Partenaire) partenaireDto;
    partenaire.setTypeCode(mobiliteDto.getTypeCode());
    if (!partenaire.partenaireEstValide()) {
      throw new BizException("Tous les champs du partenaire ne sont pas valides.");
    }
    if (mobiliteDto.getIdPays() > 0 && mobiliteDto.getIdPays() != partenaireDto.getIdPays()) {
      throw new BizException(
          "Le pays de la mobilité ne correspond pas à celui du nouveau partenaire");
    } else {
      mobiliteDto.setIdPays(partenaireDto.getIdPays());
    }
    if (partenaireDto.getQuadrimestre() == null || !partenaireDto.getQuadrimestre().equals("Q1")
        && !partenaire.getQuadrimestre().equals("Q2")) {
      throw new BizException("Le quadrimestre de la mobilité est manquant ou invalide");
    } else {
      mobiliteDto.setQuadrimestre(partenaireDto.getQuadrimestre());
    }

    // Démarrage de la transaction.
    dalServices.startTransaction();

    try {
      if (partenaireDao.trouverPartenaireParNomComplet(partenaire.getNomComplet()) != null) {
        dalServices.rollbackTransaction();
        throw new BizException("Le partenaire existe deja.");
      }
      // Ajout du partenaire
      int idPartenaireAjoute = partenaireDao.ajouterPartenaire(partenaireDto);
      mobiliteDto.setIdPartenaire(idPartenaireAjoute);
      if (!mobiliteDao.ajouterPartenaireMobiliteExistante(mobiliteDto)
          || !mobiliteDao.confirmerMobilite(mobiliteDto)) {
        dalServices.rollbackTransaction();
        throw new BizException("Cette mobilité ne peut pas recevoir de nouveau partenaire.");
      }
      // Tout s'est bien passé, on peut commit.
      dalServices.commitTransaction();
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<PartenaireDto> listerPartenaires() {
    dalServices.startTransaction();
    try {
      List<PartenaireDto> partenaires = partenaireDao.listerPartenaires();
      dalServices.commitTransaction();
      return (ArrayList<PartenaireDto>) partenaires;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<PartenaireDto> listerPartenairesParPays(int idPays) {
    dalServices.startTransaction();
    try {
      List<PartenaireDto> partenaires = partenaireDao.listerPartenairesParPays(idPays);
      dalServices.commitTransaction();
      return (ArrayList<PartenaireDto>) partenaires;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<PartenaireDto> listerPartenairesParVille(String ville) {
    dalServices.startTransaction();
    try {
      List<PartenaireDto> partenaires = partenaireDao.listerPartenairesParVille(ville);
      dalServices.commitTransaction();
      return (ArrayList<PartenaireDto>) partenaires;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<PartenaireDto> listerPartenairesParNomLegal(String nomLegal) {
    dalServices.startTransaction();
    try {
      List<PartenaireDto> partenaires = partenaireDao.listerPartenairesParNomLegal(nomLegal);
      dalServices.commitTransaction();
      return (ArrayList<PartenaireDto>) partenaires;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

}
