package be.ipl.pae.dal.utilisateur;

import be.ipl.pae.biz.utilisateur.UtilisateurDto;

import java.util.List;

public interface UtilisateurDao {

  /**
   * Recherche un utilisateur en base de données.
   * 
   * @param pseudo le pseudo de utilisateur à rechercher.
   * @return un {@link UtilisateurDto} dont le pseudo passé en paramètre correspond au pseudo de
   *         l'utilisateur trouvé, renvoie null si aucun utilisateur n'est trouvé.
   */
  UtilisateurDto trouverUtilisateurParPseudo(String pseudo);

  /**
   * Recherche un utilisateur en base de données.
   * 
   * @param id l'identifiant de utilisateur à rechercher.
   * @return un {@link UtilisateurDto} dont le pseudo passé en paramètre correspond au pseudo de
   *         l'utilisateur trouvé, renvoie null si aucun utilisateur n'est trouvé.
   */
  UtilisateurDto trouverUtilisateurParId(int id);

  /**
   * Ajoute un utilisateur dans la base de données.
   * 
   * @param utilisateurDto l'objet contenant les données de l'utilisateur à ajouter à la base de
   *        données.
   * @param role le rôle de l'utilisateur à ajouter.
   */
  void ajouterUtilisateur(UtilisateurDto utilisateurDto, String role);

  /**
   * Permet de vérifier si un utilisateur ayant le pseudo en paramètre existe déjà dans la base de
   * données.
   * 
   * @param pseudo le pseudo de l'utilisateur dont on cherche s'il existe déjà dans la base de
   *        données.
   * @return vrai si le pseudo en paramètre existe, faux sinon.
   */
  boolean chercherSiPseudoPresent(String pseudo);

  /**
   * Permet de vérifier si un utilisateur ayant l'adresse email en paramètre existe déjà dans la
   * base de données.
   * 
   * @param email l'adresse email de l'utilisateur dont on cherche s'il existe déjà dans la base de
   *        données.
   * @return vrai si l'adresse email en paramètre existe, faux sinon.
   */
  boolean chercherSiEmailPresent(String email);

  /**
   * Renvoie une liste des utilisateurs.
   * 
   * @return une liste des utilisateurs inscrits.
   */
  List<UtilisateurDto> listerUtilisateurs();

  /**
   * Renvoie une liste des étudiants.
   * 
   * @return une liste des étudiants inscrits.
   */
  List<UtilisateurDto> listerEtudiants();

  /**
   * Donne le rôle de professeur à l'utilisateur en paramètre.
   * 
   * @param utilisateurDto l'utilisateur à indiquer en tant que professeur.
   */
  void indiquerProfesseur(UtilisateurDto utilisateurDto);

  /**
   * Donne le rôle d'étudiant à l'utilisateur en paramètre.
   * 
   * @param utilisateurDto l'utilisateur à indiquer en tant qu'étudiant.
   */
  void indiquerEtudiant(UtilisateurDto utilisateurDto);

}
