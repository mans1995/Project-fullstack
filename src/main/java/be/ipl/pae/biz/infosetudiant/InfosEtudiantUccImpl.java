package be.ipl.pae.biz.infosetudiant;

import be.ipl.pae.biz.utilisateur.Utilisateur;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalServices;
import be.ipl.pae.dal.infosetudiant.InfosEtudiantDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class InfosEtudiantUccImpl implements InfosEtudiantUcc {

  @Inject
  private InfosEtudiantDao infosEtudiantDao;
  @Inject
  private DalServices dalServices;

  @Override
  public void introduireDonnees(InfosEtudiantDto infosEtudiantDto, int noVersion) {
    InfosEtudiant infosEtudiant = (InfosEtudiant) infosEtudiantDto;
    if (!infosEtudiant.infosEtudiantValides()) {
      throw new BizException("Certains champs ne sont pas valides.");
    }
    // Démarrage de la transaction.
    dalServices.startTransaction();

    try {
      InfosEtudiantDto infosParMail =
          infosEtudiantDao.chercherInfosEtudiantParId(infosEtudiantDto.getIdUtilisateur());
      if (infosParMail != null && noVersion != 0) {
        // Les informations de l'étudiant ayant déjà été introduites une première fois, on en
        // fait ici
        // une actualisation, en prenant en compte l'optimistic lock (dans le infosEtudiantDao).
        if (infosEtudiantDao.actualiserInfosEtudiant(infosEtudiantDto, noVersion) == false) {
          dalServices.rollbackTransaction();
          throw new BizException(
              "Impossible d'actualiser les données. Elles ont peut-être déjà été mises à jour.");
        } else {
          dalServices.commitTransaction();
        }
      } else {
        // Les informations de l'étudiant sont introduites pour la première fois.
        // Ajout de l'utilisateur.
        infosEtudiantDao.ajouterInfosEtudiant(infosEtudiantDto);
        dalServices.commitTransaction();
      }
    } catch (FatalException exception) {
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<InfosEtudiantDto> rechercherEtudiants(String nom, String prenom) {
    dalServices.startTransaction();
    try {
      List<InfosEtudiantDto> infos = infosEtudiantDao.rechercherEtudiantsBasique(nom, prenom);
      dalServices.commitTransaction();
      return infos;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public InfosEtudiantDto chargerInfosEtudiantParId(int idEtudiant) {
    // Démarrage de la transaction.
    dalServices.startTransaction();
    try {
      InfosEtudiantDto infosParMail = infosEtudiantDao.chercherInfosEtudiantParId(idEtudiant);
      if (infosParMail == null) {
        dalServices.rollbackTransaction();
        throw new BizException("Impossible de récupérer les informations de cet étudiant");
      }
      dalServices.commitTransaction();
      return infosParMail;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }

  }

  @Override
  public InfosEtudiantDto chargerInfosEtudiantPourProf(UtilisateurDto prof, int idEtudiant) {
    Utilisateur profCaste = (Utilisateur) prof;
    if (profCaste.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette opération");
    }
    dalServices.startTransaction();
    try {
      InfosEtudiantDto infosEtudiantDto =
          infosEtudiantDao.chercherInfosEtudiantPourProf(idEtudiant);
      if (infosEtudiantDto == null) {
        dalServices.rollbackTransaction();
        throw new BizException("Cet utilisateur n'existe pas ou n'est pas un étudiant");
      }
      dalServices.commitTransaction();
      return infosEtudiantDto;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

}
