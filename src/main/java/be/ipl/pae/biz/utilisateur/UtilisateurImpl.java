package be.ipl.pae.biz.utilisateur;

import be.ipl.pae.utils.Util;

import com.owlike.genson.annotation.JsonIgnore;

import org.mindrot.bcrypt.BCrypt;

public class UtilisateurImpl implements Utilisateur {

  private String pseudo;
  private String nom;
  private String prenom;
  private String email;
  private String role;
  private int noVersion;
  private int id;

  private String motDePasse;


  public UtilisateurImpl(String pseudo, String motDePasse) {
    this.pseudo = pseudo;
    this.motDePasse = motDePasse;
  }

  public UtilisateurImpl() {
    super();
  }


  @Override
  public boolean utilisateurEstValide() {
    if (!Util.estValide(pseudo) || pseudo.length() > 100 || !pseudo.matches(Util.REGEX_PSEUDO)) {
      return false;
    }
    if (!Util.estValide(nom) || nom.length() > 100 || !nom.matches(Util.REGEX_NOM)) {
      return false;
    }
    if (!Util.estValide(prenom) || prenom.length() > 100 || !prenom.matches(Util.REGEX_NOM)) {
      return false;
    }
    if (!Util.estValide(email) || email.length() > 100
        || !email.matches(Util.REGEX_EMAIL_UTILISATEUR)) {
      return false;
    }
    if (!Util.estValide(motDePasse) || motDePasse.length() > 100
        || !motDePasse.matches(Util.REGEX_MOT_DE_PASSE)) {
      return false;
    }
    return true;
  }

  @Override
  public String getNom() {
    return nom;
  }

  @Override
  public void setNom(String nom) {
    this.nom = nom;
  }

  @Override
  public String getPrenom() {
    return prenom;
  }

  @Override
  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getRole() {
    return role;
  }

  @Override
  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String getPseudo() {
    return pseudo;
  }

  @Override
  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  @JsonIgnore
  // http://genson.io/Documentation/Javadoc/com/owlike/genson/annotation/JsonIgnore.html
  @Override
  public String getMotDePasse() {
    return motDePasse;
  }

  @Override
  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  @Override
  public boolean verifierMotDePasse(String motDePasse) {
    return BCrypt.checkpw(motDePasse, this.motDePasse);
  }

  @Override
  public int getNoVersion() {
    return noVersion;
  }

  @Override
  public void setNoVersion(int noVersion) {
    this.noVersion = noVersion;
  }

  @Override
  public void incrementNoVersion() {
    noVersion++;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UtilisateurImpl other = (UtilisateurImpl) obj;
    if (pseudo == null) {
      if (other.pseudo != null) {
        return false;
      }
    } else if (!pseudo.equals(other.pseudo)) {
      return false;
    }
    return true;
  }

  @Override
  public boolean estEtudiant() {
    return "ETUD".equals(this.role);
  }

}


