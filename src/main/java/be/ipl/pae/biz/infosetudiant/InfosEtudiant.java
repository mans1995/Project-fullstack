package be.ipl.pae.biz.infosetudiant;

public interface InfosEtudiant extends InfosEtudiantDto {

  /**
   * Des informations d'un étudiants qui sont valides sont des informations dont les champs
   * obligatoires sont remplis et respectent les REGEX.
   * 
   * @return vrai si les informations d'un étudiant sont valides et faux sinon.
   */
  boolean infosEtudiantValides();

}
