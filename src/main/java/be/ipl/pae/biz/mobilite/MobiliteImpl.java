package be.ipl.pae.biz.mobilite;

import be.ipl.pae.utils.Util;

import java.time.LocalDate;



public class MobiliteImpl implements Mobilite {

  private int idMobilite;
  private int idPays;
  private int idPartenaire;
  private int idUtilisateur;
  private int idProgramme;
  private int preference;
  private int noCandidature;
  private String typeCode;
  private String etat;
  private String etatAvantAnnulation;
  private String raisonAnnulation;
  private String anneeAcademique;
  private String nomPays;
  private String codePays;
  private String nomLegalPartenaire;
  private String nomUtilisateur;
  private String prenomUtilisateur;
  private String emailUtilisateur;
  private String libelleProgramme;
  private String quadrimestre;
  private String departement;
  private LocalDate dateIntroduction;
  private boolean encodeProeco;
  private boolean encodeMobi;
  // Pre documents
  private boolean preContraBourse;
  private boolean preConventionDeStage;
  private boolean preCharteEtudiant;
  private boolean prePreuvePassageTestsLinguistiques;
  private boolean preDocumentEngagement;
  // Post documents
  private boolean postAttestationSejour;
  private boolean postReleveNotes;
  private boolean postCertificatStage;
  private boolean postRapportFinalComplete;
  private boolean postPreuvePassageTestsLinguistiques;
  private int noVersion;

  /**
   * Constructeur vide.
   */
  public MobiliteImpl() {
    super();
  }

  @Override
  public boolean mobiliteEstValide() {
    if (!Util.estValide(typeCode) || !typeCode.matches(Util.REGEX_TYPE_CODE)) {
      return false;
    }
    if (idUtilisateur <= 0) {
      return false;
    }
    if (quadrimestre != null && !quadrimestre.equals("Q1") && !quadrimestre.equals("Q2")) {
      return false;
    }
    if (noVersion < 0) {
      return false;
    }
    return true;

  }

  @Override
  public boolean estEnPreparation() {
    if (this.estSignePreCharteEtudiant() || this.estSignePreContraBourse()
        || this.estSignePreConventionDeStage() || this.estSignePreDocumentEngagement()
        || this.estSignePrePreuvePassageTestsLinguistiques()) {
      return true;
    }
    return false;
  }

  @Override
  public boolean estaPayer() {
    if (this.estSignePreCharteEtudiant() && this.estSignePreContraBourse()
        && this.estSignePreConventionDeStage() && this.estSignePreDocumentEngagement()
        && this.estSignePrePreuvePassageTestsLinguistiques()) {
      return true;
    }
    return false;
  }

  @Override
  public boolean estaPayerSolde() {
    if (this.estSignePostAttestationSejour() && this.estSignePostCertificatStage()
        && this.estSignePostPreuvePassageTestsLinguistiques()
        && this.estSignePostRapportFinalComplete() && this.estSignePostReleveNotes()) {
      return true;
    }
    return false;
  }

  @Override
  public void incrementNoVersion() {
    noVersion++;
  }

  @Override
  public int getIdMobilite() {
    return idMobilite;
  }

  @Override
  public void setIdMobilite(int idMobilite) {
    this.idMobilite = idMobilite;
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
  public int getIdPartenaire() {
    return idPartenaire;
  }

  @Override
  public void setIdPartenaire(int idPartenaire) {
    this.idPartenaire = idPartenaire;
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
  public int getIdProgramme() {
    return idProgramme;
  }

  @Override
  public void setIdProgramme(int idProgramme) {
    this.idProgramme = idProgramme;
  }

  @Override
  public int getPreference() {
    return preference;
  }

  @Override
  public void setPreference(int preference) {
    this.preference = preference;
  }

  @Override
  public int getNoCandidature() {
    return noCandidature;
  }

  @Override
  public void setNoCandidature(int noCandidature) {
    this.noCandidature = noCandidature;
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
  public String getEtat() {
    return etat;
  }

  @Override
  public void setEtat(String etat) {
    this.etat = etat;
  }

  @Override
  public String getEtatAvantAnnulation() {
    return etatAvantAnnulation;
  }

  @Override
  public void setEtatAvantAnnulation(String etatAvantAnnulation) {
    this.etatAvantAnnulation = etatAvantAnnulation;
  }

  @Override
  public String getRaisonAnnulation() {
    return raisonAnnulation;
  }

  @Override
  public void setRaisonAnnulation(String raisonAnnulation) {
    this.raisonAnnulation = raisonAnnulation;
  }

  @Override
  public String getAnneeAcademique() {
    return anneeAcademique;
  }

  @Override
  public void setAnneeAcademique(String anneeAcademique) {
    this.anneeAcademique = anneeAcademique;
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
  public String getNomLegalPartenaire() {
    return nomLegalPartenaire;
  }

  @Override
  public void setNomLegalPartenaire(String nomLegalPartenaire) {
    this.nomLegalPartenaire = nomLegalPartenaire;
  }

  @Override
  public String getNomUtilisateur() {
    return nomUtilisateur;
  }

  @Override
  public void setNomUtilisateur(String nomUtilisateur) {
    this.nomUtilisateur = nomUtilisateur;
  }

  @Override
  public String getPrenomUtilisateur() {
    return prenomUtilisateur;
  }

  @Override
  public void setPrenomUtilisateur(String prenomUtilisateur) {
    this.prenomUtilisateur = prenomUtilisateur;
  }

  @Override
  public String getEmailUtilisateur() {
    return emailUtilisateur;
  }

  @Override
  public void setEmailUtilisateur(String emailUtilisateur) {
    this.emailUtilisateur = emailUtilisateur;
  }

  @Override
  public String getLibelleProgramme() {
    return libelleProgramme;
  }

  @Override
  public void setLibelleProgramme(String libelleProgramme) {
    this.libelleProgramme = libelleProgramme;
  }

  @Override
  public String getQuadrimestre() {
    return quadrimestre;
  }

  @Override
  public void setQuadrimestre(String quadrimestre) {
    this.quadrimestre = quadrimestre;
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
  public LocalDate getDateIntroduction() {
    return dateIntroduction;
  }

  @Override
  public void setDateIntroduction(LocalDate dateIntroduction) {
    this.dateIntroduction = dateIntroduction;
  }

  @Override
  public boolean estEncodeProeco() {
    return encodeProeco;
  }

  @Override
  public void setEncodeProeco(boolean encodeProeco) {
    this.encodeProeco = encodeProeco;
  }

  @Override
  public boolean estEncodeMobi() {
    return encodeMobi;
  }

  @Override
  public void setEncodeMobi(boolean encodeMobi) {
    this.encodeMobi = encodeMobi;
  }

  @Override
  public boolean estSignePreContraBourse() {
    return preContraBourse;
  }

  @Override
  public void setPreContraBourse(boolean preContraBourse) {
    this.preContraBourse = preContraBourse;
  }

  @Override
  public boolean estSignePreConventionDeStage() {
    return preConventionDeStage;
  }

  @Override
  public void setPreConventionDeStage(boolean preConventionDeStage) {
    this.preConventionDeStage = preConventionDeStage;
  }

  @Override
  public boolean estSignePreCharteEtudiant() {
    return preCharteEtudiant;
  }

  @Override
  public void setPreCharteEtudiant(boolean preCharteEtudiant) {
    this.preCharteEtudiant = preCharteEtudiant;
  }

  @Override
  public boolean estSignePrePreuvePassageTestsLinguistiques() {
    return prePreuvePassageTestsLinguistiques;
  }

  @Override
  public void setPrePreuvePassageTestsLinguistiques(boolean prePreuvePassageTestsLinguistiques) {
    this.prePreuvePassageTestsLinguistiques = prePreuvePassageTestsLinguistiques;
  }

  @Override
  public boolean estSignePreDocumentEngagement() {
    return preDocumentEngagement;
  }

  @Override
  public void setPreDocumentEngagement(boolean preDocumentEngagement) {
    this.preDocumentEngagement = preDocumentEngagement;
  }

  @Override
  public boolean estSignePostAttestationSejour() {
    return postAttestationSejour;
  }

  @Override
  public void setPostAttestationSejour(boolean postAttestationSejour) {
    this.postAttestationSejour = postAttestationSejour;
  }

  @Override
  public boolean estSignePostReleveNotes() {
    return postReleveNotes;
  }

  @Override
  public void setPostReleveNotes(boolean postReleveNotes) {
    this.postReleveNotes = postReleveNotes;
  }

  @Override
  public boolean estSignePostCertificatStage() {
    return postCertificatStage;
  }

  @Override
  public void setPostCertificatStage(boolean postCertificatStage) {
    this.postCertificatStage = postCertificatStage;
  }

  @Override
  public boolean estSignePostRapportFinalComplete() {
    return postRapportFinalComplete;
  }

  @Override
  public void setPostRapportFinalComplete(boolean postRapportFinalComplete) {
    this.postRapportFinalComplete = postRapportFinalComplete;
  }

  @Override
  public boolean estSignePostPreuvePassageTestsLinguistiques() {
    return postPreuvePassageTestsLinguistiques;
  }

  @Override
  public void setPostPreuvePassageTestsLinguistiques(boolean postPreuvePassageTestsLinguistiques) {
    this.postPreuvePassageTestsLinguistiques = postPreuvePassageTestsLinguistiques;
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
  public boolean valeurEtatEstDemandee() {
    return "demandee".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatEstCree() {
    return "creee".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatEstEnPreparation() {
    return "en preparation".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatEstAPayer() {
    return "a payer".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatEstPremierPaiementDemande() {
    return "premier paiement demande".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatEstaPayerSolde() {
    return "a payer solde".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatEstSecondPaiementDemande() {
    return "second paiement demande".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatEstAnnulee() {
    return "annulee".equals(this.getEtat());
  }

  @Override
  public boolean valeurSuperEtatEstEnCours() {
    return "demandee".equals(this.getEtat()) || "creee".equals(this.getEtat())
        || "en preparation".equals(this.getEtat()) || "a payer".equals(this.getEtat())
        || "premier paiement demande".equals(this.getEtat());
  }

  @Override
  public boolean valeurSuperEtatEstAttentePremierPaiement() {
    return "creee".equals(this.getEtat()) || "en preparation".equals(this.getEtat())
        || "a payer".equals(this.getEtat());
  }

  @Override
  public boolean valeurEtatAvantAnnulationEstDemandee() {
    return "demandee".equals(this.getEtatAvantAnnulation());
  }

  @Override
  public boolean valeurEtatAvantAnnulationEstCree() {
    return "creee".equals(this.getEtatAvantAnnulation());
  }

  @Override
  public boolean valeurEtatAvantAnnulationEstEnPreparation() {
    return "en preparation".equals(this.getEtatAvantAnnulation());
  }

  @Override
  public boolean valeurEtatAvantAnnulationEstAPayer() {
    return "a payer".equals(this.getEtatAvantAnnulation());
  }

  @Override
  public boolean valeurEtatAvantAnnulationEstPremierPaiementDemande() {
    return "premier paiement demande".equals(this.getEtatAvantAnnulation());
  }

  @Override
  public boolean valeurEtatAvantAnnulationEstaPayerSolde() {
    return "a payer solde".equals(this.getEtatAvantAnnulation());
  }

  @Override
  public boolean valeurEtatAvantAnnulationEstSecondPaiementDemande() {
    return "second paiement demande".equals(this.getEtatAvantAnnulation());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((anneeAcademique == null) ? 0 : anneeAcademique.hashCode());
    result = prime * result + ((codePays == null) ? 0 : codePays.hashCode());
    result = prime * result + ((dateIntroduction == null) ? 0 : dateIntroduction.hashCode());
    result = prime * result + ((emailUtilisateur == null) ? 0 : emailUtilisateur.hashCode());
    result = prime * result + (encodeMobi ? 1231 : 1237);
    result = prime * result + (encodeProeco ? 1231 : 1237);
    result = prime * result + ((etat == null) ? 0 : etat.hashCode());
    result = prime * result + ((etatAvantAnnulation == null) ? 0 : etatAvantAnnulation.hashCode());
    result = prime * result + idMobilite;
    result = prime * result + idPartenaire;
    result = prime * result + idPays;
    result = prime * result + idProgramme;
    result = prime * result + idUtilisateur;
    result = prime * result + ((libelleProgramme == null) ? 0 : libelleProgramme.hashCode());
    result = prime * result + noVersion;
    result = prime * result + ((nomLegalPartenaire == null) ? 0 : nomLegalPartenaire.hashCode());
    result = prime * result + ((nomPays == null) ? 0 : nomPays.hashCode());
    result = prime * result + ((nomUtilisateur == null) ? 0 : nomUtilisateur.hashCode());
    result = prime * result + (postAttestationSejour ? 1231 : 1237);
    result = prime * result + (postCertificatStage ? 1231 : 1237);
    result = prime * result + (postPreuvePassageTestsLinguistiques ? 1231 : 1237);
    result = prime * result + (postRapportFinalComplete ? 1231 : 1237);
    result = prime * result + (postReleveNotes ? 1231 : 1237);
    result = prime * result + (preCharteEtudiant ? 1231 : 1237);
    result = prime * result + (preContraBourse ? 1231 : 1237);
    result = prime * result + (preConventionDeStage ? 1231 : 1237);
    result = prime * result + (preDocumentEngagement ? 1231 : 1237);
    result = prime * result + (prePreuvePassageTestsLinguistiques ? 1231 : 1237);
    result = prime * result + preference;
    result = prime * result + ((prenomUtilisateur == null) ? 0 : prenomUtilisateur.hashCode());
    result = prime * result + ((raisonAnnulation == null) ? 0 : raisonAnnulation.hashCode());
    result = prime * result + ((typeCode == null) ? 0 : typeCode.hashCode());
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
    MobiliteImpl other = (MobiliteImpl) obj;
    if (anneeAcademique == null) {
      if (other.anneeAcademique != null) {
        return false;
      }
    } else if (!anneeAcademique.equals(other.anneeAcademique)) {
      return false;
    }
    if (codePays == null) {
      if (other.codePays != null) {
        return false;
      }
    } else if (!codePays.equals(other.codePays)) {
      return false;
    }
    if (dateIntroduction == null) {
      if (other.dateIntroduction != null) {
        return false;
      }
    } else if (!dateIntroduction.equals(other.dateIntroduction)) {
      return false;
    }
    if (emailUtilisateur == null) {
      if (other.emailUtilisateur != null) {
        return false;
      }
    } else if (!emailUtilisateur.equals(other.emailUtilisateur)) {
      return false;
    }
    if (encodeMobi != other.encodeMobi) {
      return false;
    }
    if (encodeProeco != other.encodeProeco) {
      return false;
    }
    if (etat == null) {
      if (other.etat != null) {
        return false;
      }
    } else if (!etat.equals(other.etat)) {
      return false;
    }
    if (etatAvantAnnulation == null) {
      if (other.etatAvantAnnulation != null) {
        return false;
      }
    } else if (!etatAvantAnnulation.equals(other.etatAvantAnnulation)) {
      return false;
    }
    if (idMobilite != other.idMobilite) {
      return false;
    }
    if (idPartenaire != other.idPartenaire) {
      return false;
    }
    if (idPays != other.idPays) {
      return false;
    }
    if (idProgramme != other.idProgramme) {
      return false;
    }
    if (idUtilisateur != other.idUtilisateur) {
      return false;
    }
    if (libelleProgramme == null) {
      if (other.libelleProgramme != null) {
        return false;
      }
    } else if (!libelleProgramme.equals(other.libelleProgramme)) {
      return false;
    }
    if (noVersion != other.noVersion) {
      return false;
    }
    if (nomLegalPartenaire == null) {
      if (other.nomLegalPartenaire != null) {
        return false;
      }
    } else if (!nomLegalPartenaire.equals(other.nomLegalPartenaire)) {
      return false;
    }
    if (nomPays == null) {
      if (other.nomPays != null) {
        return false;
      }
    } else if (!nomPays.equals(other.nomPays)) {
      return false;
    }
    if (nomUtilisateur == null) {
      if (other.nomUtilisateur != null) {
        return false;
      }
    } else if (!nomUtilisateur.equals(other.nomUtilisateur)) {
      return false;
    }
    if (postAttestationSejour != other.postAttestationSejour) {
      return false;
    }
    if (postCertificatStage != other.postCertificatStage) {
      return false;
    }
    if (postPreuvePassageTestsLinguistiques != other.postPreuvePassageTestsLinguistiques) {
      return false;
    }
    if (postRapportFinalComplete != other.postRapportFinalComplete) {
      return false;
    }
    if (postReleveNotes != other.postReleveNotes) {
      return false;
    }
    if (preCharteEtudiant != other.preCharteEtudiant) {
      return false;
    }
    if (preContraBourse != other.preContraBourse) {
      return false;
    }
    if (preConventionDeStage != other.preConventionDeStage) {
      return false;
    }
    if (preDocumentEngagement != other.preDocumentEngagement) {
      return false;
    }
    if (prePreuvePassageTestsLinguistiques != other.prePreuvePassageTestsLinguistiques) {
      return false;
    }
    if (preference != other.preference) {
      return false;
    }
    if (prenomUtilisateur == null) {
      if (other.prenomUtilisateur != null) {
        return false;
      }
    } else if (!prenomUtilisateur.equals(other.prenomUtilisateur)) {
      return false;
    }
    if (raisonAnnulation == null) {
      if (other.raisonAnnulation != null) {
        return false;
      }
    } else if (!raisonAnnulation.equals(other.raisonAnnulation)) {
      return false;
    }
    if (typeCode == null) {
      if (other.typeCode != null) {
        return false;
      }
    } else if (!typeCode.equals(other.typeCode)) {
      return false;
    }
    return true;
  }

}
