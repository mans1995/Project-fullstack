package be.ipl.pae.biz.infosetudiant;

import be.ipl.pae.utils.Util;

import java.util.Date;

public class InfosEtudiantImpl implements InfosEtudiant {

  private String statut;
  private String nom;
  private String prenom;
  private Date dateNaissance;
  private int nationalite;
  private String nationaliteLibelle;
  private String adresse;
  private String telephone;
  private String email;
  private String sexe;
  private int nbAnneesReussies;
  private String iban;
  private String titulaireCompte;
  private String nomBanque;
  private String codeBic;
  private int idUtilisateur;
  private int noVersion;

  @Override
  public String getStatut() {
    return statut;
  }

  @Override
  public void setStatut(String statut) {
    this.statut = statut;
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
  public Date getDateNaissance() {
    return dateNaissance;
  }

  @Override
  public void setDateNaissance(Date dateNaissance) {
    this.dateNaissance = dateNaissance;
  }

  @Override
  public int getNationalite() {
    return nationalite;
  }

  @Override
  public void setNationalite(int nationalite) {
    this.nationalite = nationalite;
  }

  @Override
  public String getNationaliteLibelle() {
    return nationaliteLibelle;
  }

  @Override
  public void setNationaliteLibelle(String nationaliteLibelle) {
    this.nationaliteLibelle = nationaliteLibelle;
  }

  @Override
  public String getAdresse() {
    return adresse;
  }

  @Override
  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  @Override
  public String getTelephone() {
    return telephone;
  }

  @Override
  public void setTelephone(String telephone) {
    this.telephone = telephone;
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
  public String getSexe() {
    return sexe;
  }

  @Override
  public void setSexe(String sexe) {
    this.sexe = sexe;
  }

  @Override
  public int getNbAnneesReussies() {
    return nbAnneesReussies;
  }

  @Override
  public void setNbAnneesReussies(int nbAnneesReussies) {
    this.nbAnneesReussies = nbAnneesReussies;
  }

  @Override
  public String getIban() {
    return iban;
  }

  @Override
  public void setIban(String iban) {
    this.iban = iban;
  }

  @Override
  public String getTitulaireCompte() {
    return titulaireCompte;
  }

  @Override
  public void setTitulaireCompte(String titulaireCompte) {
    this.titulaireCompte = titulaireCompte;

  }

  @Override
  public String getNomBanque() {
    return nomBanque;
  }

  @Override
  public void setNomBanque(String nomBanque) {
    this.nomBanque = nomBanque;
  }

  @Override
  public String getCodeBic() {
    return codeBic;
  }

  @Override
  public void setCodeBic(String codeBic) {
    this.codeBic = codeBic;
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
  public int getIdUtilisateur() {
    return idUtilisateur;
  }

  @Override
  public void setIdUtilisateur(int idUtilisateur) {
    this.idUtilisateur = idUtilisateur;
  }

  @Override
  public boolean infosEtudiantValides() {
    if (idUtilisateur < 1) {
      return false;
    }
    if (noVersion < 0) {
      return false;
    }
    if (!Util.estValide(statut) || statut.length() > 4 || !statut.matches(Util.REGEX_STATUT)) {
      return false;
    }
    if (dateNaissance == null) {
      return false;
    }
    if (nationalite < 1) {
      return false;
    }
    if (!Util.estValide(adresse) || adresse.length() > 200) {
      return false;
    }
    if (!Util.estValide(telephone) || telephone.length() > 13
        || !telephone.matches(Util.REGEX_NUMERO_TEL)) {
      return false;
    }
    if (!Util.estValide(sexe) || sexe.length() > 1 || !sexe.matches(Util.REGEX_SEXE)) {
      return false;
    }
    if (nbAnneesReussies < 0) {
      return false;
    }
    if (!Util.estValide(iban) || iban.length() > 34 || !iban.matches(Util.REGEX_IBAN)) {
      return false;
    }
    if (!Util.estValide(titulaireCompte) || titulaireCompte.length() > 100
        || !titulaireCompte.matches(Util.REGEX_NOM)) {
      return false;
    }
    if (!Util.estValide(nomBanque) || nomBanque.length() > 100) {
      return false;
    }
    if (!Util.estValide(codeBic) || codeBic.length() > 11
        || !codeBic.matches(Util.REGEX_CODE_BIC)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
    result = prime * result + ((codeBic == null) ? 0 : codeBic.hashCode());
    result = prime * result + ((dateNaissance == null) ? 0 : dateNaissance.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((iban == null) ? 0 : iban.hashCode());
    result = prime * result + idUtilisateur;
    result = prime * result + nationalite;
    result = prime * result + nbAnneesReussies;
    result = prime * result + noVersion;
    result = prime * result + ((nom == null) ? 0 : nom.hashCode());
    result = prime * result + ((nomBanque == null) ? 0 : nomBanque.hashCode());
    result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
    result = prime * result + ((sexe == null) ? 0 : sexe.hashCode());
    result = prime * result + ((statut == null) ? 0 : statut.hashCode());
    result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
    result = prime * result + ((titulaireCompte == null) ? 0 : titulaireCompte.hashCode());
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
    InfosEtudiantImpl other = (InfosEtudiantImpl) obj;
    if (adresse == null) {
      if (other.adresse != null) {
        return false;
      }
    } else if (!adresse.equals(other.adresse)) {
      return false;
    }
    if (codeBic == null) {
      if (other.codeBic != null) {
        return false;
      }
    } else if (!codeBic.equals(other.codeBic)) {
      return false;
    }
    if (dateNaissance == null) {
      if (other.dateNaissance != null) {
        return false;
      }
    } else if (!dateNaissance.equals(other.dateNaissance)) {
      return false;
    }
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (iban == null) {
      if (other.iban != null) {
        return false;
      }
    } else if (!iban.equals(other.iban)) {
      return false;
    }
    if (idUtilisateur != other.idUtilisateur) {
      return false;
    }
    if (nationalite != other.nationalite) {
      return false;
    }
    if (nbAnneesReussies != other.nbAnneesReussies) {
      return false;
    }
    if (noVersion != other.noVersion) {
      return false;
    }
    if (nom == null) {
      if (other.nom != null) {
        return false;
      }
    } else if (!nom.equals(other.nom)) {
      return false;
    }
    if (nomBanque == null) {
      if (other.nomBanque != null) {
        return false;
      }
    } else if (!nomBanque.equals(other.nomBanque)) {
      return false;
    }
    if (prenom == null) {
      if (other.prenom != null) {
        return false;
      }
    } else if (!prenom.equals(other.prenom)) {
      return false;
    }
    if (sexe == null) {
      if (other.sexe != null) {
        return false;
      }
    } else if (!sexe.equals(other.sexe)) {
      return false;
    }
    if (statut == null) {
      if (other.statut != null) {
        return false;
      }
    } else if (!statut.equals(other.statut)) {
      return false;
    }
    if (telephone == null) {
      if (other.telephone != null) {
        return false;
      }
    } else if (!telephone.equals(other.telephone)) {
      return false;
    }
    if (titulaireCompte == null) {
      if (other.titulaireCompte != null) {
        return false;
      }
    } else if (!titulaireCompte.equals(other.titulaireCompte)) {
      return false;
    }
    return true;
  }

}
