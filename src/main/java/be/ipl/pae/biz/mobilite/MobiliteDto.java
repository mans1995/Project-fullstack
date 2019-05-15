package be.ipl.pae.biz.mobilite;

import java.time.LocalDate;

public interface MobiliteDto {

  /**
   * Une mobilite est a payer quand tous les pre documents sont signés.
   * 
   * @return true si les pre documents sont signés.
   */
  boolean estaPayer();

  /**
   * Une mobilite est a payer solde quand tous les post documents sont signés.
   * 
   * @return true si les post documents sont signés.
   */
  boolean estaPayerSolde();

  /**
   * Une mobilite est en preparation quand au moins 1 documents pre est signé.
   * 
   * @return true si un pre document est signé.
   */
  boolean estEnPreparation();

  /**
   * 
   * @return le pays lié a la mobilite.
   */
  int getIdPays();

  /**
   * Initialise ou modifie le pays de la mobilite.
   * 
   * @param idPays le pays a set.
   */
  void setIdPays(int idPays);

  /**
   * 
   * @return le partenaire lié a la mobilite.
   */
  int getIdPartenaire();

  /**
   * Initialise ou modifie le partenaire de la mobilite.
   * 
   * @param idPartenaire le partenaire a set.
   */
  void setIdPartenaire(int idPartenaire);

  /**
   * 
   * @return l'utilisateur lié a la mobilite.
   */
  int getIdUtilisateur();

  /**
   * Initialise ou modifie l'utilisateur de la mobilite.
   * 
   * @param idUtilisateur l'utilisateur a set.
   */
  void setIdUtilisateur(int idUtilisateur);

  /**
   * 
   * @return le programme lié a la mobilite.
   */
  int getIdProgramme();

  /**
   * Initialise ou modifie le programme de la mobilite.
   * 
   * @param idProgramme le programme a set.
   */
  void setIdProgramme(int idProgramme);

  /**
   * 
   * @return la preference de la mobilite.
   */
  int getPreference();

  /**
   * Initialise ou modifie la preference de la mobilite.
   * 
   * @param preference la preference a set.
   */
  void setPreference(int preference);

  /**
   * 
   * @return le type code de la mobilite.
   */
  String getTypeCode();

  /**
   * Initialise ou modifie le type code de la mobilite.
   * 
   * @param typeCode le type code a set.
   */
  void setTypeCode(String typeCode);

  /**
   * 
   * @return l'etat de la mobilite.
   */
  String getEtat();

  /**
   * Initialise ou modifie l'etat de la mobilite.
   * 
   * @param etat l'etat a set.
   */
  void setEtat(String etat);

  /**
   * 
   * @return l'etat avant l'annulation de la mobilite.
   */
  String getEtatAvantAnnulation();

  /**
   * Initialise ou modifie l'etat avant l'annulation de la mobilite.
   * 
   * @param etatAvantAnnulation l'etat avant l'annulation a set.
   */
  void setEtatAvantAnnulation(String etatAvantAnnulation);

  /**
   * 
   * @return la raison de l'annulation de la mobilite.
   */
  String getRaisonAnnulation();

  /**
   * Initialise ou modifie la raison de l'annulation de la mobilite.
   * 
   * @param raisonAnnulation le pays a set.
   */
  void setRaisonAnnulation(String raisonAnnulation);

  /**
   * 
   * @return l'annee academique de la mobilite.
   */
  String getAnneeAcademique();

  /**
   * Initialise ou modifie l'annee academique de la mobilite.
   * 
   * @param anneeAcademique l'annee academique a set.
   */
  void setAnneeAcademique(String anneeAcademique);

  /**
   * 
   * @return le nom du pays lié a la mobilite.
   */
  String getNomPays();

  /**
   * Initialise ou modifie le nom du pays de la mobilite.
   * 
   * @param nomPays le nom du pays a set.
   */
  void setNomPays(String nomPays);

  /**
   * 
   * @return le code du pays lié a la mobilite.
   */
  String getCodePays();

  /**
   * Initialise ou modifie le code du pays de la mobilite.
   * 
   * @param codePays le code du pays a set.
   */
  void setCodePays(String codePays);

  /**
   * 
   * @return la date d'introduction de la mobilite.
   */
  LocalDate getDateIntroduction();

  /**
   * Initialise ou modifie la date d'introduction de la mobilite.
   * 
   * @param dateIntroduction la date d'introduction a set.
   */
  void setDateIntroduction(LocalDate dateIntroduction);

  /**
   * 
   * @return true si encodé.
   */
  boolean estEncodeProeco();

  /**
   * Initialise a true ou false.
   * 
   * @param encodeProeco boolean a set.
   */
  void setEncodeProeco(boolean encodeProeco);

  /**
   * 
   * @return true si encodé.
   */
  boolean estEncodeMobi();

  /**
   * Initialise a true ou false.
   * 
   * @param encodeMobi boolean a set.
   */
  void setEncodeMobi(boolean encodeMobi);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePreContraBourse();

  /**
   * Met a signé ou non le document.
   * 
   * @param preContraBourse signé(true) ou pas signé(false).
   */
  void setPreContraBourse(boolean preContraBourse);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePreConventionDeStage();

  /**
   * Met a signé ou non le document.
   * 
   * @param preConventionDeStage signé(true) ou pas signé(false).
   */
  void setPreConventionDeStage(boolean preConventionDeStage);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePreCharteEtudiant();

  /**
   * Met a signé ou non le document.
   * 
   * @param preCharteEtudiant signé(true) ou pas signé(false).
   */
  void setPreCharteEtudiant(boolean preCharteEtudiant);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePrePreuvePassageTestsLinguistiques();

  /**
   * Met a signé ou non le document.
   * 
   * @param prePreuvePassageTestsLinguistiques signé(true) ou pas signé(false).
   */
  void setPrePreuvePassageTestsLinguistiques(boolean prePreuvePassageTestsLinguistiques);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePreDocumentEngagement();

  /**
   * Met a signé ou non le document.
   * 
   * @param preDocumentEngagement signé(true) ou pas signé(false).
   */
  void setPreDocumentEngagement(boolean preDocumentEngagement);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePostAttestationSejour();

  /**
   * Met a signé ou non le document.
   * 
   * @param postAttestationSejour signé(true) ou pas signé(false).
   */
  void setPostAttestationSejour(boolean postAttestationSejour);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePostReleveNotes();

  /**
   * Met a signé ou non le document.
   * 
   * @param postReleveNotes signé(true) ou pas signé(false).
   */
  void setPostReleveNotes(boolean postReleveNotes);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePostCertificatStage();

  /**
   * Met a signé ou non le document.
   * 
   * @param postCertificatStage signé(true) ou pas signé(false).
   */
  void setPostCertificatStage(boolean postCertificatStage);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePostRapportFinalComplete();

  /**
   * Met a signé ou non le document.
   * 
   * @param postRapportFinalComplete signé(true) ou pas signé(false).
   */
  void setPostRapportFinalComplete(boolean postRapportFinalComplete);

  /**
   * 
   * @return true si signé.
   */
  boolean estSignePostPreuvePassageTestsLinguistiques();

  /**
   * Met a signé ou non le document.
   * 
   * @param postPreuvePassageTestsLinguistiques signé(true) ou pas signé(false).
   */
  void setPostPreuvePassageTestsLinguistiques(boolean postPreuvePassageTestsLinguistiques);

  /**
   * 
   * @return le numero de version.
   */
  int getNoVersion();

  /**
   * Incrémente le numéro de version de la mobilite dans la base de données.
   */
  void incrementNoVersion();

  /**
   * Initialise ou modifie le numero de version de la mobilite.
   * 
   * @param noVersion le numero de version a set.
   */
  void setNoVersion(int noVersion);

  /**
   * 
   * @return l'id de la mobilite.
   */
  int getIdMobilite();

  /**
   * Initialise ou modifie l'id de la mobilite.
   * 
   * @param idMobilite l'id a set.
   */
  void setIdMobilite(int idMobilite);

  /**
   * 
   * @return le numero d'ordre de candidature de la mobilite.
   */
  int getNoCandidature();

  /**
   * Initialise ou modifie le numero d'ordre de candidature de la mobilite.
   * 
   * @param noCandidature le numero d'ordre de candidature à set.
   */
  void setNoCandidature(int noCandidature);

  /**
   * Initialise ou modifie le nom legal du partenaire lie à la mobilite.
   * 
   * @param nomLegalPartenaire le nom legal du partenaire à set.
   */
  void setNomLegalPartenaire(String nomLegalPartenaire);

  /**
   * 
   * @return le nom legal du partenaire lié à la mobilite.
   */
  String getNomLegalPartenaire();

  /**
   * Initialise ou modifie l'email de l'utilisateur lie à la mobilite.
   * 
   * @param emailUtilisateur l'email de l'utilisateur à set.
   */
  void setEmailUtilisateur(String emailUtilisateur);

  /**
   * 
   * @return l'email de l'utilisateur lié à la mobilite.
   */
  String getEmailUtilisateur();

  /**
   * Initialise ou modifie le prenom de l'utilisateur lie à la mobilite.
   * 
   * @param prenomUtilisateur le prenom de l'utilisateur à set.
   */
  void setPrenomUtilisateur(String prenomUtilisateur);

  /**
   * 
   * @return le prenom de l'utilisateur lié à la mobilite.
   */
  String getPrenomUtilisateur();

  /**
   * Initialise ou modifie le nom de l'utilisateur lie à la mobilite.
   * 
   * @param nomUtilisateur le nom de l'utilisateur à set.
   */
  void setNomUtilisateur(String nomUtilisateur);

  /**
   * 
   * @return le nom de l'utilisateur lié à la mobilite.
   */
  String getNomUtilisateur();

  /**
   * Initialise ou modifie le libelle du programme lie à la mobilite.
   * 
   * @param libelleProgramme le libelle du programme à set.
   */
  void setLibelleProgramme(String libelleProgramme);

  /**
   * 
   * @return le libelle du programme lie à la mobilite.
   */
  String getLibelleProgramme();

  /**
   * Initialise ou modifie le quadrimestre de la mobilite.
   * 
   * @param quadrimestre le quadrimestre de la mobilité à set.
   */
  void setQuadrimestre(String quadrimestre);

  /**
   * 
   * @return le quadrimestre de la mobilité.
   */
  String getQuadrimestre();

  /**
   * Initialise ou modifie le département du partenaire lié la mobilite.
   * 
   * @param departement le departement à set.
   */
  void setDepartement(String departement);

  /**
   * 
   * @return le département du partenaire lié la mobilite.
   */
  String getDepartement();

  /**
   * 
   * @return vrai si l'état de la mobilité est "demandee".
   */
  boolean valeurEtatEstDemandee();

  /**
   * 
   * @return vrai si l'état de la mobilité est "creee".
   */
  boolean valeurEtatEstCree();

  /**
   * 
   * @return vrai si l'état de la mobilité est "en preparation".
   */
  boolean valeurEtatEstEnPreparation();

  /**
   * 
   * @return vrai si l'état de la mobilité est "a payer".
   */
  boolean valeurEtatEstAPayer();

  /**
   * 
   * @return vrai si l'état de la mobilité est "premier paiement demande".
   */
  boolean valeurEtatEstPremierPaiementDemande();

  /**
   * 
   * @return vrai si l'état de la mobilité est "a payer solde".
   */
  boolean valeurEtatEstaPayerSolde();

  /**
   * 
   * @return vrai si l'état de la mobilité est "second paiement demande".
   */
  boolean valeurEtatEstSecondPaiementDemande();

  /**
   * 
   * @return vrai si l'état de la mobilité est "annulee".
   */
  boolean valeurEtatEstAnnulee();

  /**
   * 
   * @return vrai si l'état de la mobilité est dans "demandee, creee, en preparation, a payer,
   *         premier paiement demande".
   */
  boolean valeurSuperEtatEstEnCours();

  /**
   * 
   * @return vrai si l'état de la mobilité est dans "creee, en preparation, a payer".
   */
  boolean valeurSuperEtatEstAttentePremierPaiement();

  /**
   * 
   * @return vrai si l'état de la mobilité avant annulation est "demandee".
   */
  boolean valeurEtatAvantAnnulationEstDemandee();

  /**
   * 
   * @return vrai si l'état de la mobilité avant annulation est "creee".
   */
  boolean valeurEtatAvantAnnulationEstCree();

  /**
   * 
   * @return vrai si l'état de la mobilité avant annulation est "en preparation".
   */
  boolean valeurEtatAvantAnnulationEstEnPreparation();

  /**
   * 
   * @return vrai si l'état de la mobilité avant annulation est "a payer".
   */
  boolean valeurEtatAvantAnnulationEstAPayer();

  /**
   * 
   * @return vrai si l'état de la mobilité avant annulation est "premier paiement demande".
   */
  boolean valeurEtatAvantAnnulationEstPremierPaiementDemande();

  /**
   * 
   * @return vrai si l'état de la mobilité avant annulation est "a payer solde".
   */
  boolean valeurEtatAvantAnnulationEstaPayerSolde();

  /**
   * 
   * @return vrai si l'état de la mobilité avant annulation est "second paiement demande".
   */
  boolean valeurEtatAvantAnnulationEstSecondPaiementDemande();

}
