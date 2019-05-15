package be.ipl.pae.biz.utilisateur;

import be.ipl.pae.exceptions.BizException;

import java.util.List;

public interface UtilisateurUcc {

  /**
   * Permet à un utilisateur de se connecter à son compte s'il fournit les bonnes informations
   * d'authentification.
   * 
   * @param pseudo le pseudo entré par l'utilisateur en ligne.
   * @param motDePasse le mot de passe entré par l'utilisateur en ligne.
   * @return un utilisateur {@link UtilisateurDto} si le pseudo et le mot de passe existent dans la
   *         base de données, null sinon.
   */
  UtilisateurDto seConnecter(String pseudo, String motDePasse);

  /**
   * Permet d'inscrire un utilisateur en l'ajoutant à la base de données si les informations de son
   * inscription respectent les valeurs attendues. Lance une exception {@link BizException} si cet
   * utilisateur existe déjà (c'est-à-dire soit si son adresse email est déjà existante, soit si son
   * pseudo est déjà existant) ou bien si les informations de son inscriptions sont invalides.
   * 
   * @param utilisateurDto un objet de transfert contenant les informations de l'utilisateur à
   *        ajouter dans la base de données.
   */
  void inscrireUtilisateur(UtilisateurDto utilisateurDto);

  /**
   * Renvoie un utilisateur en fonction du pseudo.
   * 
   * @param pseudo le pseudo
   * @return un {@link UtilisateurDto}
   */
  UtilisateurDto trouverUtilisateurParPseudo(String pseudo);

  /**
   * Renvoie une liste de tous les utilisateurs existants.
   * 
   * @return une liste des utilisateurs.
   */
  List<UtilisateurDto> listerUtilisateurs();

  /**
   * Renvoie une liste de tous les étudiants existants.
   * 
   * @return une liste des utilisateurs.
   */
  List<UtilisateurDto> listerEtudiants();

  /**
   * Permet à un responsable d'indiquer un étudiant comme étant professeur.
   * 
   * @param utilisateurIndicateur utilisateur (doit être professeur responsable) indiquant
   *        l'utilisateur à indiquer en tant que professeur.
   * @param utilisateurIndique utilisateur indiqué en tant que professeur.
   */
  void indiquerProfesseur(UtilisateurDto utilisateurIndicateur, UtilisateurDto utilisateurIndique);

  /**
   * Permet à un responsable d'indiquer un professeur comme étant étudiant.
   * 
   * @param utilisateurIndicateur utilisateur (doit être professeur responsable) indiquant
   *        l'utilisateur à indiquer en tant qu'étudiant.<br>
   *        Cela permet à un responsable qui s'est trompé de remettre le rôle d'étudiant à un
   *        utilisateur avec le rôle erroné de professeur.
   * @param utilisateurIndique utilisateur indiqué en tant que professeur.
   */
  void indiquerEtudiant(UtilisateurDto utilisateurIndicateur, UtilisateurDto utilisateurIndique);


}
