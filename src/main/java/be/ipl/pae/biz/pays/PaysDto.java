package be.ipl.pae.biz.pays;

public interface PaysDto {
  /**
   * @return l'id du pays.
   */
  int getIdPays();

  /**
   * Initialise ou modifie l'identifiant du pays.
   * 
   * @param idPays l'identifiant du pays.
   */
  void setIdPays(int idPays);

  /**
   * @return l'identifiant du programme associé au pays (Erasmus+, Erabel, FAME, Suisse).
   */
  int getIdProgramme();

  /**
   * Initialise ou modifie l'identifiant du programme associé au pays (Erasmus+, Erabel, FAME,
   * Suisse).
   * 
   * @param idProgramme l'identifiant du programme associé au pays (Erasmus+, Erabel, FAME, Suisse).
   */
  void setIdProgramme(int idProgramme);

  /**
   * @return le nom du pays.
   */
  String getNomPays();

  /**
   * Initialise ou modifie le nom du pays.
   * 
   * @param nomPays le nom du pays.
   */
  void setNomPays(String nomPays);

  /**
   * @return le code associé au pays.
   */
  String getCodePays();

  /**
   * Initialise ou modifie le code associé au pays.
   * 
   * @param codePays le code associé au pays.
   */
  void setCodePays(String codePays);

}
