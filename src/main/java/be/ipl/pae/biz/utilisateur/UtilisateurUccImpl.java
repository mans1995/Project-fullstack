package be.ipl.pae.biz.utilisateur;

import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalServices;
import be.ipl.pae.dal.utilisateur.UtilisateurDao;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class UtilisateurUccImpl implements UtilisateurUcc {

  @Inject
  private UtilisateurDao utilisateurDao;
  @Inject
  private DalServices dalServices;

  @Override
  public UtilisateurDto seConnecter(String pseudo, String motDePasse) {
    // renvoie un UtilisateurDto de celui qui vient de se connecter
    dalServices.startTransaction();
    try {
      UtilisateurDto utilisateurDto = utilisateurDao.trouverUtilisateurParPseudo(pseudo);
      dalServices.commitTransaction();
      if (utilisateurDto == null) {
        return null;
      }
      // Cet utilisateur vient de la DB (en passant par l'utilisateurDto)
      Utilisateur utilisateur = (Utilisateur) utilisateurDto;
      if (!utilisateur.verifierMotDePasse(motDePasse)) {
        return null;
      }
      return utilisateurDto;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public void inscrireUtilisateur(UtilisateurDto utilisateurDto) {
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (!utilisateur.utilisateurEstValide()) {
      throw new BizException("Certains champs de l'utilisateur ne sont pas valides.");
    }
    // Démarrage de la transaction.
    dalServices.startTransaction();

    try {
      boolean pseudoPresent = utilisateurDao.chercherSiPseudoPresent(utilisateurDto.getPseudo());
      if (pseudoPresent) {
        dalServices.rollbackTransaction();
        throw new BizException("Ce pseudo existe déjà.");
      }
      boolean emailPresent = utilisateurDao.chercherSiEmailPresent(utilisateurDto.getEmail());
      if (emailPresent) {
        dalServices.rollbackTransaction();
        throw new BizException("Cette adresse email existe déjà.");
      }
      List<UtilisateurDto> utilisateurs = utilisateurDao.listerUtilisateurs();
      String role = utilisateurs.size() == 0 ? "RESP" : "ETUD";
      // Ajout de l'utilisateur.
      utilisateurDao.ajouterUtilisateur(utilisateurDto, role);

      // Tout s'est bien passé, on peut commit.
      dalServices.commitTransaction();
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public UtilisateurDto trouverUtilisateurParPseudo(String pseudo) {
    dalServices.startTransaction();
    try {
      UtilisateurDto utilisateurDto = utilisateurDao.trouverUtilisateurParPseudo(pseudo);
      dalServices.commitTransaction();
      return utilisateurDto;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<UtilisateurDto> listerUtilisateurs() {
    dalServices.startTransaction();
    try {
      List<UtilisateurDto> utilisateur = utilisateurDao.listerUtilisateurs();
      dalServices.commitTransaction();
      return (List<UtilisateurDto>) utilisateur;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<UtilisateurDto> listerEtudiants() {
    dalServices.startTransaction();
    try {
      List<UtilisateurDto> utilisateur = utilisateurDao.listerEtudiants();
      dalServices.commitTransaction();
      return (List<UtilisateurDto>) utilisateur;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public void indiquerProfesseur(UtilisateurDto utilisateurIndicateur,
      UtilisateurDto utilisateurIndique) {
    if (!"RESP".equals(utilisateurIndicateur.getRole())) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette opération.", true);
    }
    dalServices.startTransaction();
    try {
      utilisateurIndique = utilisateurDao.trouverUtilisateurParId(utilisateurIndique.getId());
      if (utilisateurIndique == null) {
        dalServices.rollbackTransaction();
        throw new BizException("Aucun utilisateur ne correspond à cet identifiant");
      }
      if ("PROF".equals(utilisateurIndique.getRole())) {
        dalServices.rollbackTransaction();
        throw new BizException("Cet utilisateur est déjà un professeur.");
      } else if ("RESP".equals(utilisateurIndique.getRole())) {
        dalServices.rollbackTransaction();
        throw new BizException("Le rôle de responsable n'est pas modifiable.");
      }
      utilisateurDao.indiquerProfesseur(utilisateurIndique);
      dalServices.commitTransaction();
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }

  }

  @Override
  public void indiquerEtudiant(UtilisateurDto utilisateurIndicateur,
      UtilisateurDto utilisateurIndique) {
    if (!"RESP".equals(utilisateurIndicateur.getRole())) {
      throw new BizException("Vous n'avez pas les droits pour effectuer cette opération.", true);
    }
    dalServices.startTransaction();
    try {
      utilisateurIndique = utilisateurDao.trouverUtilisateurParId(utilisateurIndique.getId());
      if (utilisateurIndique == null) {
        dalServices.rollbackTransaction();
        throw new BizException("Aucun utilisateur ne correspond à cet identifiant");
      }
      if ("ETUD".equals(utilisateurIndique.getRole())) {
        dalServices.rollbackTransaction();
        throw new BizException("Cet utilisateur est déjà un étudiant.");
      } else if ("RESP".equals(utilisateurIndique.getRole())) {
        dalServices.rollbackTransaction();
        throw new BizException("Le rôle de responsable n'est pas modifiable.");
      }
      utilisateurDao.indiquerEtudiant(utilisateurIndique);
      dalServices.commitTransaction();
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

}
