package be.ipl.pae.biz.utilisateur;

public interface Utilisateur extends UtilisateurDto {

  /**
   * Vérifie le mot de passe en paramètre est celui de l'utilisateur.
   * 
   * @param motDePasse le mot de passe à vérifier.
   * @return true si le mot de passe passé en paramètre correspond à celui de l'objet courant, false
   *         sinon.
   */
  boolean verifierMotDePasse(String motDePasse);

  /**
   * Un utilisateur valide est un utilisateur dont les champs obligatoires sont remplis et
   * respectent les REGEX.
   * 
   * @return vrai si le utilisateur est valide et faux sinon.
   */
  boolean utilisateurEstValide();

  /**
   * @return vrai si le role de l'utilisateur est "ETUD", faux sinon.
   */
  boolean estEtudiant();
}
