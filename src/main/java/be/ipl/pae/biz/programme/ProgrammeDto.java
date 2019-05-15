package be.ipl.pae.biz.programme;

public interface ProgrammeDto {

  /**
   * 
   * @return l'id du programme.
   */
  int getId();

  /**
   * 
   * @param id l'id à set.
   */
  void setId(int id);

  /**
   * 
   * @return le libelle du programme.
   */
  String getLibelle();

  /**
   * Initialise ou modifie le libelle du programme.
   * 
   * @param libelle le libelle à set.
   */
  void setLibelle(String libelle);
}
