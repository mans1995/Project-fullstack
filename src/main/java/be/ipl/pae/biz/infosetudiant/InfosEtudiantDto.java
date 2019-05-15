package be.ipl.pae.biz.infosetudiant;

import java.util.Date;

public interface InfosEtudiantDto {

  /**
   * @return le statut de l'étudiant (M, Mme, Mlle).
   */
  String getStatut();

  /**
   * Initialise ou modifie le statut de l'étudiant (M, Mme, Mlle).
   * 
   * @param statut le statut de l'étudiant (M, Mme, Mlle).
   */
  void setStatut(String statut);

  /**
   * @return le nom de l'étudiant.
   */
  String getNom();

  /**
   * Initialise ou modifie le nom de l'étudiant.
   * 
   * @param nom le nom de l'étudiant
   */
  void setNom(String nom);

  /**
   * @return le prénom de l'étudiant.
   */
  String getPrenom();

  /**
   * Initialise ou modifie le prénom de l'étudiant.
   * 
   * @param prenom le prénom de l'étudiant
   */
  void setPrenom(String prenom);

  /**
   * @return la date de naissance de l'étudiant.
   */
  Date getDateNaissance();

  /**
   * Initialise ou modifie la date de naissance de l'étudiant.
   * 
   * @param dateNaissance la date de naissance de l'etudiant.
   */
  void setDateNaissance(Date dateNaissance);

  /**
   * @return la nationalité de l'étudiant.
   */
  int getNationalite();

  /**
   * Initialise et modifie la nationalité de l'étudiant.
   * 
   * @param nationalite la nationalité de l'étudiant.
   */
  void setNationalite(int nationalite);

  /**
   * @return l'adresse de l'étudiant.
   */
  String getAdresse();

  /**
   * Initialise ou modifie l'adresse de l'étudiant.
   * 
   * @param adresse l'adresse a set.
   */
  void setAdresse(String adresse);

  /**
   * @return le numéro de téléphone de l'étudiant.
   */
  String getTelephone();

  /**
   * Initialise ou modifie le numéro de téléphone de l'étudiant.
   * 
   * @param telephone le numéro de téléphone de l'étudiant.
   */
  void setTelephone(String telephone);

  /**
   * @return l'adresse email de l'étudiant.
   */
  String getEmail();

  /**
   * Initialise ou modifie l'adresse email de l'étudiant.
   * 
   * @param email l'adresse email de l'étudiant.
   */
  void setEmail(String email);

  /**
   * @return le sexe de l'étudiant.
   */
  String getSexe();

  /**
   * Initialise ou modifie le sexe de l'étudiant.
   * 
   * @param sexe le sexe de l'étudiant.
   */
  void setSexe(String sexe);

  /**
   * @return le nombre d'années réussies par l'étudiant concernant ses études supérieures.
   */
  int getNbAnneesReussies();

  /**
   * Initialise ou modifie le nombre d'années réussies par l'étudiant concernant ses études
   * supérieures.
   * 
   * @param nbAnneesReussies le nombre d'années réussies par l'étudiant concernant ses études
   *        supérieures.
   */
  void setNbAnneesReussies(int nbAnneesReussies);

  /**
   * @return le code IBAN de la carte bancaire associée à l'étudiant.
   */
  String getIban();

  /**
   * Initialise ou modifie le code IBAN de la carte bancaire associée à l'étudiant.
   * 
   * @param iban le code IBAN de la carte bancaire associée à l'étudiant.
   */
  void setIban(String iban);

  /**
   * @return les noms et/ou prénoms du titulaire du compte sur lequel verser l'argent pour
   *         l'étudiant.
   */
  String getTitulaireCompte();

  /**
   * Initialisa ou modifie les noms et/ou prénoms du titulaire du compte sur lequel verser l'argent
   * pour l'étudiant.
   * 
   * @param titulaireCompte les noms et/ou prénoms du titulaire du compte sur lequel verser l'argent
   *        pour l'étudiant.
   */
  void setTitulaireCompte(String titulaireCompte);

  /**
   * @return le nom de la banque associée à la carte bancaire du titulaire du compte associé à
   *         l'étudiant.
   */
  String getNomBanque();

  /**
   * Initialise ou modifie le nom de la banque associée à la carte bancaire du titulaire du compte
   * associé à l'étudiant.
   * 
   * @param nomBanque le nom de la banque associée à la carte bancaire du titulaire du compte
   *        associé à l'étudiant.
   */
  void setNomBanque(String nomBanque);

  /**
   * @return le code BIC de la carte bancaire associée à l'étudiant.
   */
  String getCodeBic();

  /**
   * Initialise ou modifie le code BIC de la carte bancaire associée à l'étudiant.
   * 
   * @param codeBic le code BIC de la carte bancaire associée à l'étudiant.
   */
  void setCodeBic(String codeBic);

  /**
   * @return le numéro de version des informations de l'étudiant dans la base de données.
   */
  int getNoVersion();

  /**
   * Initialise ou modifie le numéro de version des informations de l'étudiant
   * {@link InfosEtudiantDto}.
   * 
   * @param noVersion le numéro de version des informations de l'étudiant.
   */
  void setNoVersion(int noVersion);

  /**
   * Incrémente le numéro de version des informations de l'étudiant dans la base de données.
   */
  void incrementNoVersion();

  /**
   * @return l'identifiant utilisatehr des informations de l'étudiant.
   */
  int getIdUtilisateur();

  /**
   * Initialise ou modifie l'identifiant utililsateur des informations de l'étudiant
   * {@link InfoEtudiantDto}.
   * 
   * @param idUtilisateur l'identifiant utilisateur des informations de l'étudiant .
   */
  void setIdUtilisateur(int idUtilisateur);

  void setNationaliteLibelle(String nationaliteLibelle);

  String getNationaliteLibelle();

}
