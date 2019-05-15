package be.ipl.pae.biz.mobilite;


import be.ipl.pae.biz.utilisateur.Utilisateur;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalServices;
import be.ipl.pae.dal.mobilite.MobiliteDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MobiliteUccImpl implements MobiliteUcc {
  @Inject
  private MobiliteDao mobiliteDao;
  @Inject
  private DalServices dalServices;

  @Override
  public void ajouterMobilite(List<? extends MobiliteDto> listeMobiliteDto) {
    LocalDate dateIntroduction = LocalDate.now();
    String anneeAcademique = dateIntroduction.getYear() + "-" + (dateIntroduction.getYear() + 1);

    // Démarrage de la transaction.
    dalServices.startTransaction();

    for (MobiliteDto mobiliteDto : listeMobiliteDto) {
      mobiliteDto.setEtat("demandee");
      mobiliteDto.setAnneeAcademique(anneeAcademique);
      mobiliteDto.setDateIntroduction(dateIntroduction);
      mobiliteDto.setEncodeProeco(false);
      mobiliteDto.setEncodeMobi(false);
      mobiliteDto.setNoVersion(1);
      Mobilite mobilite = (Mobilite) mobiliteDto;
      mobiliteDto
          .setNoCandidature(mobiliteDao.trouverNombreCandidatures(mobiliteDto.getIdUtilisateur(),
              mobiliteDto.getAnneeAcademique()) + 1);
      if (!mobilite.mobiliteEstValide()) {
        dalServices.rollbackTransaction();
        throw new BizException("Tous les champs de la mobilite ne sont pas valides.");
      }
    }

    try {
      // Ajout des mobilités
      for (MobiliteDto mobiliteDto : listeMobiliteDto) {

        if (mobiliteDto.getIdPartenaire() > 0) {
          mobiliteDao.ajouterMobiliteAvecPartenaire(mobiliteDto);
        } else {
          if (mobiliteDto.getIdPays() > 0) {
            mobiliteDao.ajouterMobiliteAvecPays(mobiliteDto);
          } else {
            mobiliteDao.ajouterMobilite(mobiliteDto);
          }
        }
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
  public List<MobiliteDto> listerMobilite() {
    dalServices.startTransaction();
    try {
      List<MobiliteDto> mobilites = mobiliteDao.listerMobilites();
      dalServices.commitTransaction();
      return (List<MobiliteDto>) mobilites;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<MobiliteDto> listerMobiliteParAnneeEtEtat(MobiliteDto mobiliteDto) {
    dalServices.startTransaction();
    try {
      List<MobiliteDto> mobilites = mobiliteDao.listerMobilitesParAnneeEtEtat(mobiliteDto);
      dalServices.commitTransaction();
      return (List<MobiliteDto>) mobilites;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<MobiliteDto> listerMobiliteDunEtudiant(int idUtilisateur) {
    dalServices.startTransaction();
    try {
      List<MobiliteDto> mobilites = mobiliteDao.listerMobilitesParUtilisateur(idUtilisateur);
      dalServices.commitTransaction();
      return (List<MobiliteDto>) mobilites;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public void confirmerMobilite(MobiliteDto mobilite, UtilisateurDto utilisateur) {
    if (((Utilisateur) utilisateur).estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    } else {
      dalServices.startTransaction();
      try {
        MobiliteDto mobiliteDto = mobiliteDao.trouverMobiliteParId(mobilite.getIdMobilite());
        if (mobiliteDto.getIdPartenaire() == 0) {
          if (!mobiliteDao.confirmerMobiliteAvecPartenaire(mobilite)) {
            throw new BizException("La mobilité ou le partenaire n'existe pas "
                + "ou bien la mobilité a déjà été confirmée.");
          }
        } else if (!mobiliteDao.confirmerMobilite(mobilite)) {
          dalServices.rollbackTransaction();
          throw new BizException("La mobilité a déjà été confirmée ou annulée");
        }
        dalServices.commitTransaction();
      } catch (FatalException exception) {
        // Le rollback va fermer la connexion
        dalServices.rollbackTransaction();
        throw new FatalException(exception.getMessage(), exception);
      }
    }

  }

  @Override
  public boolean annulerMobilite(MobiliteDto mobiliteDto) {
    if (!mobiliteDto.valeurSuperEtatEstEnCours()) {
      throw new BizException("La mobilité n'est pas dans un état possible à annuler");
    } else {
      dalServices.startTransaction();
      try {
        boolean annulee = mobiliteDao.annulerMobilite(mobiliteDto);
        dalServices.commitTransaction();
        return annulee;
      } catch (FatalException exception) {
        // Le rollback va fermer la connexion
        dalServices.rollbackTransaction();
        throw new FatalException(exception.getMessage(), exception);
      }
    }
  }

  @Override
  public boolean supprimerMobilite(MobiliteDto mobilite) {
    dalServices.startTransaction();
    try {
      boolean supprime = mobiliteDao.supprimerMobilite(mobilite);
      dalServices.commitTransaction();
      return supprime;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public boolean signerDocuments(MobiliteDto mobilite) {
    MobiliteDto mobiliteEnCours = trouverMobiliteParId(mobilite.getIdMobilite());
    // Pre documents
    mobiliteEnCours.setPreCharteEtudiant(mobilite.estSignePreCharteEtudiant());
    mobiliteEnCours.setPreContraBourse(mobilite.estSignePreContraBourse());
    mobiliteEnCours.setPreConventionDeStage(mobilite.estSignePreConventionDeStage());
    mobiliteEnCours.setPreDocumentEngagement(mobilite.estSignePreDocumentEngagement());
    mobiliteEnCours.setPrePreuvePassageTestsLinguistiques(
        mobilite.estSignePrePreuvePassageTestsLinguistiques());
    // Post documents
    mobiliteEnCours.setPostAttestationSejour(mobilite.estSignePostAttestationSejour());
    mobiliteEnCours.setPostCertificatStage(mobilite.estSignePostCertificatStage());
    mobiliteEnCours.setPostPreuvePassageTestsLinguistiques(
        mobilite.estSignePostPreuvePassageTestsLinguistiques());
    mobiliteEnCours.setPostRapportFinalComplete(mobilite.estSignePostRapportFinalComplete());
    mobiliteEnCours.setPostReleveNotes(mobilite.estSignePostReleveNotes());

    String etat = mobiliteEnCours.getEtat();
    if (mobiliteEnCours.estEnPreparation()) {
      etat = "en preparation";
    }
    if (mobiliteEnCours.estaPayer()) {
      etat = "a payer";
    }
    if (!mobiliteEnCours.estaPayer() && (mobiliteEnCours.estSignePostAttestationSejour()
        || mobiliteEnCours.estSignePostCertificatStage()
        || mobiliteEnCours.estSignePostPreuvePassageTestsLinguistiques()
        || mobiliteEnCours.estSignePostRapportFinalComplete()
        || mobiliteEnCours.estSignePostReleveNotes())) {
      throw new BizException(
          "Impossible de signer les post documents si tous les pre documents ne sont pas signés");
    }
    if (mobiliteEnCours.estaPayer() && mobiliteEnCours.estaPayerSolde()) {
      etat = "a payer solde";
    }

    dalServices.startTransaction();
    try {
      int idProgrammeSuisse = mobiliteDao.trouverIdProgrammeParLibelle("Suisse");
      // Note : si l'id vaut 0 c'est qu'il n'y a pas de programme associé à cette mobilité ou que
      // Suisse n'existe pas dans les programmes.
      if (mobiliteEnCours.getIdProgramme() != idProgrammeSuisse) {
        mobiliteEnCours.setEtat(etat);
      }
      if (mobiliteEnCours.getIdProgramme() == idProgrammeSuisse && (etat.equals("en preparation")
          || etat.equals("a payer") || etat.equals("a payer solde"))) {
        mobiliteEnCours.setEtat("en preparation");
      }
      // else : c'est la Suisse... son état ne va pas dans les états dits "de paiement"
      // mais reste "en prerapration" peu importe que les docs soient signés ou pas
      boolean signe = mobiliteDao.signerDocuments(mobiliteEnCours);
      dalServices.commitTransaction();
      return signe;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public MobiliteDto trouverMobiliteParId(int idMobilite) {
    dalServices.startTransaction();
    try {
      MobiliteDto mobiliteDto = mobiliteDao.trouverMobiliteParId(idMobilite);
      dalServices.commitTransaction();
      return mobiliteDto;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<MobiliteDto> listerDemandesDeMobilites(UtilisateurDto utilisateurDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    List<MobiliteDto> mobilites;
    dalServices.startTransaction();
    try {
      if (utilisateur.estEtudiant()) {
        mobilites = mobiliteDao.listerDemandesDeMobilitesParEtudiant(utilisateurDto.getId());
      } else {
        mobilites = mobiliteDao.listerDemandesDeMobilites();
      }
      dalServices.commitTransaction();
      return (List<MobiliteDto>) mobilites;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public boolean indiquerEnvoiPremiereDemandePaiement(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }

    dalServices.startTransaction();
    try {
      int idProgrammeSuisse = mobiliteDao.trouverIdProgrammeParLibelle("Suisse");
      if (mobiliteDto.getIdProgramme() == idProgrammeSuisse) {
        dalServices.rollbackTransaction();
        throw new BizException("Action inutile. La Suisse prend la bourse en charge.");
      }
      boolean indique = mobiliteDao.indiquerEnvoiPremiereDemandePaiement(mobiliteDto);
      dalServices.commitTransaction();
      return indique;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public boolean indiquerEnvoiSecondeDemandePaiement(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }

    dalServices.startTransaction();
    try {
      int idProgrammeSuisse = mobiliteDao.trouverIdProgrammeParLibelle("Suisse");
      if (mobiliteDto.getIdProgramme() == idProgrammeSuisse) {
        dalServices.rollbackTransaction();
        throw new BizException("Action inutile. La Suisse prend la bourse en charge.", true);
      }
      boolean indique = mobiliteDao.indiquerEnvoiSecondeDemandePaiement(mobiliteDto);
      dalServices.commitTransaction();
      return indique;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public Map<String, String> obtenirListeEtatsDocumentsEtMobilite(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }
    dalServices.startTransaction();
    try {
      Map<String, String> etats = mobiliteDao.obtenirListeEtatsDocumentsEtMobilite(mobiliteDto);
      dalServices.commitTransaction();
      return etats;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public void indiquerEtudiantEncodeDansLogiciels(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }
    dalServices.startTransaction();
    try {
      mobiliteDao.indiquerEtudiantEncodeDansLogiciels(mobiliteDto);
      dalServices.commitTransaction();
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public Map<MobiliteDto, Boolean[]> listerDemandesPaiementsParAnneeAcademique(
      UtilisateurDto utilisateurDto, MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }

    dalServices.startTransaction();
    try {
      int idProgrammeSuisse = mobiliteDao.trouverIdProgrammeParLibelle("Suisse");
      Map<MobiliteDto, Boolean[]> mobilitesPaiements =
          mobiliteDao.listerDemandesPaiementsParAnneeAcademique(mobiliteDto, idProgrammeSuisse);
      dalServices.commitTransaction();
      return mobilitesPaiements;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public Boolean[] obtenirDemandesPaiementsPourMobilite(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }

    dalServices.startTransaction();
    try {
      int idProgrammeSuisse = mobiliteDao.trouverIdProgrammeParLibelle("Suisse");
      mobiliteDto = mobiliteDao.trouverMobiliteParId(mobiliteDto.getIdMobilite());
      if (mobiliteDto.getIdProgramme() == idProgrammeSuisse) {
        // La Suisse prend en charge donc pas de demande à faire
        return new Boolean[] {false, false};
      }
      Boolean[] paiements = mobiliteDao.obtenirDemandesPaiementsPourMobilite(mobiliteDto);
      dalServices.commitTransaction();
      return paiements;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public boolean preciserReceptionDocumentsRetourMobilite(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }
    dalServices.startTransaction();
    try {
      mobiliteDto = mobiliteDao.trouverMobiliteParId(mobiliteDto.getIdMobilite());
      boolean signe = mobiliteDao.preciserReceptionDocumentsRetourMobilite(mobiliteDto);
      dalServices.commitTransaction();
      return signe;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public boolean preciserDocumentsDepartMobiliteSignes(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant()) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette action.", true);
    }
    dalServices.startTransaction();
    try {
      mobiliteDto = mobiliteDao.trouverMobiliteParId(mobiliteDto.getIdMobilite());
      boolean signe = mobiliteDao.preciserDocumentsDepartMobiliteSignes(mobiliteDto);
      dalServices.commitTransaction();
      return signe;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

}
