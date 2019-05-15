package be.ipl.pae.biz.utilisateur;

public interface UtilisateurDto {

  /**
   * @return le pseudo de l'utilisateur {@link UtilisateurDto}.
   */
  String getPseudo();

  /**
   * Initialise ou modifie le pseudo de l'utilisateur {@link UtilisateurDto}.
   * 
   * @param pseudo le pseudo à modifier.
   */
  void setPseudo(String pseudo);

  /**
   * @return le mot de passe de l'utilisateur {@link UtilisateurDto}.
   */
  String getMotDePasse();

  /**
   * Initialise ou modifie le mot de passe de l'utilisateur {@link UtilisateurDto}.
   * 
   * @param motDePasse le mot de passe à modifier.
   */
  void setMotDePasse(String motDePasse);

  /**
   * @return nom de l'utilisateur {@link UtilisateurDto}.
   */
  String getNom();

  /**
   * Initialise ou modifie le nom de l'utilisateur {@link UtilisateurDto}.
   * 
   * @param nom le nom à modifier.
   */
  void setNom(String nom);

  /**
   * @return prénom de l'utilisateur {@link UtilisateurDto}.
   */
  String getPrenom();

  /**
   * Initialise ou modifie le prénom de l'utilisateur {@link UtilisateurDto}.
   * 
   * @param prenom le prénom à modifier.
   */
  void setPrenom(String prenom);

  /**
   * @return adresse email de l'utilisateur {@link UtilisateurDto}.
   */
  String getEmail();

  /**
   * Initialise ou modifie l'adresse email de l'utilisateur {@link UtilisateurDto}.
   * 
   * @param email l'adresse email à modifier.
   */
  void setEmail(String email);

  /**
   * @return rôle de l'utilisateur {@link UtilisateurDto}.
   */
  String getRole();

  /**
   * Initialise ou modifie le rôle de l'utilisateur {@link UtilisateurDto}.
   * 
   * @param role rôle l'adresse email à modifier.
   */
  void setRole(String role);

  /**
   * @return le numéro de version de l'utilisateur, dont la valeur minimale est égale à 1, dans la
   *         base de données.
   */
  int getNoVersion();

  /**
   * Initialise ou modifie le numéro de version de l'utilisateur {@link UtilisateurDto} dont la
   * valeur minimale est égale à 1.
   * 
   * @param noVersion le numéro de version de l'utilisateur dont la valeur minimale est égale à 1.
   */
  void setNoVersion(int noVersion);

  /**
   * Incrémente le numéro de version de l'utilisateur dans la base de données.
   */
  void incrementNoVersion();

  /**
   * @return l'identifiant de l'utilisateur dont la valeur minimale est égale à 1.
   */
  int getId();

  /**
   * Initialise ou modifie l'identifiant de l'utilisateur {@link UtilisateurDto} dont la valeur
   * minimale est égale à 1.
   * 
   * @param id l'identifiant de l'utilisateur dont la valeur minimale est égale à 1.
   */
  void setId(int id);

}
