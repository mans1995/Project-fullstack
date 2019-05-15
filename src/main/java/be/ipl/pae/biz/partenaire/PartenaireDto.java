package be.ipl.pae.biz.partenaire;

public interface PartenaireDto {

  /**
   * Initialise ou modifie le type code du partenaire {@link PartenaireDto}.
   * 
   * @param typeCode le type code à modifier.
   */
  void setTypeCode(String typeCode);

  /**
   * Initialise ou modifie le nom legal du partenaire {@link PartenaireDto}.
   * 
   * @param nomLegal le nom legal à modifier.
   */
  void setNomLegal(String nomLegal);

  /**
   * Initialise ou modifie le nom d'affaires du partenaire {@link PartenaireDto}.
   * 
   * @param nomDaffaires le nom d'affaires à modifier.
   */
  void setNomDaffaires(String nomDaffaires);

  /**
   * Initialise ou modifie le nom complet du partenaire {@link PartenaireDto}.
   * 
   * @param nomComplet le nom complet à modifier.
   */
  void setNomComplet(String nomComplet);

  /**
   * Initialise ou modifie le departement du partenaire {@link PartenaireDto}.
   * 
   * @param departement le departement à modifier.
   */
  void setDepartement(String departement);

  /**
   * Initialise ou modifie le type d'organisation du partenaire {@link PartenaireDto}.
   * 
   * @param typeOrganisation le type d'organisation à modifier.
   */
  void setTypeOrganisation(String typeOrganisation);

  /**
   * Initialise ou modifie le nombre d'employes du partenaire {@link PartenaireDto}.
   * 
   * @param nombreEmployes le nombre d'employes à modifier.
   */
  void setNombreEmployes(int nombreEmployes);

  /**
   * Initialise ou modifie l'adresse du partenaire {@link PartenaireDto}.
   * 
   * @param adresse l'adresse à modifier.
   */
  void setAdresse(String adresse);

  /**
   * Initialise ou modifie le pays du partenaire {@link PartenaireDto}.
   * 
   * @param idPays le pays à modifier.
   */
  void setIdPays(int idPays);

  /**
   * Initialise ou modifie la region du partenaire {@link PartenaireDto}.
   * 
   * @param region la region à modifier
   */
  void setRegion(String region);

  /**
   * Initialise ou modifie le code postal du partenaire {@link PartenaireDto}.
   * 
   * @param codePostal le code postal à modifier
   */
  void setCodePostal(String codePostal);

  /**
   * Initialise ou modifie la ville du partenaire {@link PartenaireDto}.
   * 
   * @param ville la ville à modifier
   */
  void setVille(String ville);

  /**
   * Initialise ou modifie l'email du partenaire {@link PartenaireDto}.
   * 
   * @param email l'email à modifier
   */
  void setEmail(String email);

  /**
   * Initialise ou modifie le site web du partenaire {@link PartenaireDto}.
   * 
   * @param siteWeb le site web à modifier
   */
  void setSiteWeb(String siteWeb);

  /**
   * Initialise ou modifie le telephone du partenaire {@link PartenaireDto}.
   * 
   * @param telephone le telephone à modifier
   */
  void setTelephone(String telephone);

  /**
   * Initialise ou modifie le nom du pays de la mobilite.
   * 
   * @param nomPays le nom du pays a set.
   */
  void setNomPays(String nomPays);

  /**
   * Initialise ou modifie le code du pays de la mobilite.
   * 
   * @param codePays le code du pays a set.
   */
  void setCodePays(String codePays);

  /**
   * @return le type code du partenaire (SMS/SMP) {@link PartenaireDto}.
   */
  String getTypeCode();

  /**
   * @return le nom legal du partenaire {@link PartenaireDto}.
   */
  String getNomLegal();

  /**
   * @return le nom d'affaires du partenaire {@link PartenaireDto}.
   */
  String getNomDaffaires();

  /**
   * @return le nom complet du partenaire {@link PartenaireDto}.
   */
  String getNomComplet();

  /**
   * @return le departement du partenaire {@link PartenaireDto}.
   */
  String getDepartement();

  /**
   * @return le type d'organisation du partenaire {@link PartenaireDto}.
   */
  String getTypeOrganisation();

  /**
   * @return le nombre d'employes du partenaire {@link PartenaireDto}.
   */
  int getNombreEmployes();

  /**
   * @return l'adresse du partenaire {@link PartenaireDto}.
   */
  String getAdresse();

  /**
   * @return le pays du partenaire {@link PartenaireDto}.
   */
  int getIdPays();

  /**
   * @return la region du partenaire {@link PartenaireDto}.
   */
  String getRegion();

  /**
   * @return le code postal du partenaire {@link PartenaireDto}.
   */
  String getCodePostal();

  /**
   * @return la ville du partenaire {@link PartenaireDto}.
   */
  String getVille();

  /**
   * @return l'email du partenaire {@link PartenaireDto}.
   */
  String getEmail();

  /**
   * @return le site web du partenaire {@link PartenaireDto}.
   */
  String getSiteWeb();

  /**
   * @return le telephone du partenaire {@link PartenaireDto}.
   */
  String getTelephone();

  /**
   * 
   * @return le nom du pays lié a la mobilite.
   */
  String getNomPays();

  /**
   * 
   * @return le code du pays lié a la mobilite.
   */
  String getCodePays();

  /**
   * @return le n° de version du partenaire dans la base de données.
   */
  int getNoVersion();

  /**
   * Initialise ou modifie le numéro de version du partenaire {@link PartenaireDto}.
   * 
   * @param noVersion le numéro de version du partenaire.
   */
  void setNoVersion(int noVersion);

  /**
   * Incrémente le numéro de version du partenaire dans la base de données.
   */
  void incrementNoVersion();

  /**
   * @return l'identifiant du partenaire.
   */
  int getId();

  /**
   * Initialise ou modifie l'identifiant du partenaire {@link PartenaireDto}.
   * 
   * @param id l'identifiant du partenaire.
   */
  void setId(int id);

  /**
   * Initialise ou modifie l'identifiant d'une mobilité lié à ce partenaire.
   * 
   * @param idMobilite d'une mobilité lié à ce partenaire.
   */
  void setIdMobilite(int idMobilite);

  /**
   * 
   * @return id d'une mobilité lié à ce partenaire.
   */
  int getIdMobilite();

  /**
   * Initialise ou modifie l'identifiant d'une mobilité lié à ce partenaire.
   * 
   * @param quadrimestre d'une mobilité lié à ce partenaire.
   */
  void setQuadrimestre(String quadrimestre);

  /**
   * 
   * @return le quadrimestre d'une mobilité lié à ce partenaire.
   */
  String getQuadrimestre();
}
