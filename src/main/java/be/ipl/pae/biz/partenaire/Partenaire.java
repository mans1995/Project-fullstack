package be.ipl.pae.biz.partenaire;

public interface Partenaire extends PartenaireDto {


  /**
   * Un partenaire valide est un partenaire dont les champs obligatoires sont remplis et respectent
   * le REGEX. Seuls les attributs departement, nombreEmployes, region, email, siteWeb et telephone
   * peuvent etre vides.
   * 
   * @return true si le partenaire est valide et false sinon
   */
  boolean partenaireEstValide();
}
