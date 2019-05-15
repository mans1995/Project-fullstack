package be.ipl.pae.biz.partenaire;

import be.ipl.pae.utils.Util;

import com.owlike.genson.annotation.JsonIgnore;

public class PartenaireImpl implements Partenaire {

  private String typeCode;
  private String nomLegal;
  private String nomDaffaires;
  private String nomComplet;
  private String departement;
  private String typeOrganisation;
  private int nombreEmployes;
  private String adresse;
  private int idPays;
  private String region;
  private String codePostal;
  private String ville;
  private String email;
  private String siteWeb;
  private String telephone;
  private String nomPays;
  private String codePays;
  private String quadrimestre;
  private int idMobilite;
  private int noVersion;
  private int id;

  /**
   * Constructeur vide.
   */
  public PartenaireImpl() {
    super();
  }

  @Override
  public boolean partenaireEstValide() {
    if (!Util.estValide(typeCode) || typeCode.length() != 3) {
      return false;
    }
    if (!Util.estValide(nomLegal) || nomLegal.length() > 100) {
      return false;
    }
    if (!Util.estValide(nomDaffaires) || nomDaffaires.length() > 100) {
      return false;
    }
    if (!Util.estValide(nomComplet) || nomComplet.length() > 100) {
      return false;
    }
    if (Util.estValide(departement) && departement.length() > 100) {
      return false;
    }
    if (!Util.estValide(typeOrganisation) || typeOrganisation.length() > 3) {
      return false;
    }
    if (!Util.estValide(adresse) || adresse.length() > 200) {
      return false;
    }
    if (Util.estValide(region) && region.length() > 100) {
      return false;
    }
    if (!Util.estValide(ville) || ville.length() > 100) {
      return false;
    }
    if (Util.estValide(email)
        && (!email.matches(Util.REGEX_EMAIL_PARTENAIRE) || email.length() > 100)) {
      return false;
    }
    if (Util.estValide(siteWeb) && siteWeb.length() > 100) {
      return false;
    }
    if (Util.estValide(telephone)
        && (!telephone.matches(Util.REGEX_EST_NOMBRE) || telephone.length() > 12)) {
      return false;
    }
    return true;
  }

  @Override
  public String getTypeCode() {
    return typeCode;
  }

  @Override
  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }

  @Override
  public String getNomLegal() {
    return nomLegal;
  }

  @Override
  public void setNomLegal(String nomLegal) {
    this.nomLegal = nomLegal;
  }

  @Override
  public String getNomDaffaires() {
    return nomDaffaires;
  }

  @Override
  public void setNomDaffaires(String nomDaffaires) {
    this.nomDaffaires = nomDaffaires;
  }

  @Override
  public String getNomComplet() {
    return nomComplet;
  }

  @Override
  public void setNomComplet(String nomComplet) {
    this.nomComplet = nomComplet;
  }

  @Override
  public String getDepartement() {
    return departement;
  }

  @Override
  public void setDepartement(String departement) {
    this.departement = departement;
  }

  @Override
  public String getTypeOrganisation() {
    return typeOrganisation;
  }

  @Override
  public void setTypeOrganisation(String typeOrganisation) {
    this.typeOrganisation = typeOrganisation;
  }

  @Override
  public int getNombreEmployes() {
    return nombreEmployes;
  }

  @Override
  public void setNombreEmployes(int nombreEmployes) {
    this.nombreEmployes = nombreEmployes;
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
  public int getIdPays() {
    return idPays;
  }

  @Override
  public void setIdPays(int idPays) {
    this.idPays = idPays;
  }

  @Override
  public String getRegion() {
    return region;
  }

  @Override
  public void setRegion(String region) {
    this.region = region;
  }

  @Override
  public String getCodePostal() {
    return codePostal;
  }

  @Override
  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  @Override
  public String getVille() {
    return ville;
  }

  @Override
  public void setVille(String ville) {
    this.ville = ville;
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
  public String getSiteWeb() {
    return siteWeb;
  }

  @Override
  public void setSiteWeb(String siteWeb) {
    this.siteWeb = siteWeb;
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
  public String getNomPays() {
    return nomPays;
  }

  @Override
  public void setNomPays(String nomPays) {
    this.nomPays = nomPays;
  }

  @Override
  public String getCodePays() {
    return codePays;
  }

  @Override
  public void setCodePays(String codePays) {
    this.codePays = codePays;
  }

  @Override
  @JsonIgnore
  public String getQuadrimestre() {
    return quadrimestre;
  }

  @Override
  public void setQuadrimestre(String quadrimestre) {
    this.quadrimestre = quadrimestre;
  }

  @Override
  @JsonIgnore
  public int getIdMobilite() {
    return idMobilite;
  }

  @Override
  public void setIdMobilite(int idMobilite) {
    this.idMobilite = idMobilite;
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
    result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
    result = prime * result + ((codePostal == null) ? 0 : codePostal.hashCode());
    result = prime * result + ((departement == null) ? 0 : departement.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + id;
    result = prime * result + idPays;
    result = prime * result + noVersion;
    result = prime * result + ((nomComplet == null) ? 0 : nomComplet.hashCode());
    result = prime * result + ((nomDaffaires == null) ? 0 : nomDaffaires.hashCode());
    result = prime * result + ((nomLegal == null) ? 0 : nomLegal.hashCode());
    result = prime * result + nombreEmployes;
    result = prime * result + ((region == null) ? 0 : region.hashCode());
    result = prime * result + ((siteWeb == null) ? 0 : siteWeb.hashCode());
    result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
    result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
    result = prime * result + ((typeOrganisation == null) ? 0 : typeOrganisation.hashCode());
    result = prime * result + ((ville == null) ? 0 : ville.hashCode());
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
    PartenaireImpl other = (PartenaireImpl) obj;
    if (adresse == null) {
      if (other.adresse != null) {
        return false;
      }
    } else if (!adresse.equals(other.adresse)) {
      return false;
    }
    if (codePostal != other.codePostal) {
      return false;
    }
    if (departement == null) {
      if (other.departement != null) {
        return false;
      }
    } else if (!departement.equals(other.departement)) {
      return false;
    }
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (idPays != other.idPays) {
      return false;
    }
    if (noVersion != other.noVersion) {
      return false;
    }
    if (nomComplet == null) {
      if (other.nomComplet != null) {
        return false;
      }
    } else if (!nomComplet.equals(other.nomComplet)) {
      return false;
    }
    if (nomDaffaires == null) {
      if (other.nomDaffaires != null) {
        return false;
      }
    } else if (!nomDaffaires.equals(other.nomDaffaires)) {
      return false;
    }
    if (nomLegal == null) {
      if (other.nomLegal != null) {
        return false;
      }
    } else if (!nomLegal.equals(other.nomLegal)) {
      return false;
    }
    if (nombreEmployes != other.nombreEmployes) {
      return false;
    }
    if (region == null) {
      if (other.region != null) {
        return false;
      }
    } else if (!region.equals(other.region)) {
      return false;
    }
    if (siteWeb == null) {
      if (other.siteWeb != null) {
        return false;
      }
    } else if (!siteWeb.equals(other.siteWeb)) {
      return false;
    }
    if (telephone == null) {
      if (other.telephone != null) {
        return false;
      }
    } else if (!telephone.equals(other.telephone)) {
      return false;
    }
    if (typeCode == null) {
      if (other.typeCode != null) {
        return false;
      }
    } else if (!typeCode.equals(other.typeCode)) {
      return false;
    }
    if (typeOrganisation == null) {
      if (other.typeOrganisation != null) {
        return false;
      }
    } else if (!typeOrganisation.equals(other.typeOrganisation)) {
      return false;
    }
    if (ville == null) {
      if (other.ville != null) {
        return false;
      }
    } else if (!ville.equals(other.ville)) {
      return false;
    }
    return true;
  }

}
