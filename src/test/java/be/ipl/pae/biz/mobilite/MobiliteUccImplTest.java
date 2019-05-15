package be.ipl.pae.biz.mobilite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.utilisateur.Utilisateur;
import be.ipl.pae.biz.utilisateur.UtilisateurImpl;
import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.dal.mobilite.MobiliteDaoMock;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MobiliteUccImplTest {

  private MobiliteUcc mobiliteUcc;
  private Mobilite mobiliteAvecPartenaire;
  private Mobilite mobiliteSansPartenaire;
  private Utilisateur utilisateur;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si le setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {

    ConfigManager.load("test.properties");
    mobiliteUcc = new MobiliteUccImpl();
    InjectionService.injectDependency(mobiliteUcc);
    MobiliteImpl mobiliteTest = new MobiliteImpl();
    mobiliteTest.setIdMobilite(1);
    mobiliteTest.setIdPays(24);
    mobiliteTest.setIdUtilisateur(5);
    mobiliteTest.setPreference(1);
    mobiliteTest.setTypeCode("SMS");
    mobiliteTest.setEtat("demandee");
    mobiliteTest.setEtatAvantAnnulation("");
    mobiliteTest.setRaisonAnnulation("");
    mobiliteTest.setAnneeAcademique("2018-2019");
    mobiliteTest.setDateIntroduction(LocalDate.now());
    mobiliteTest.setNomLegalPartenaire("Nom Legal");
    mobiliteTest.setCodePays("30");
    mobiliteTest.setDepartement("Departement");
    mobiliteTest.setEmailUtilisateur("leo.vde@student.vinci.be");
    mobiliteTest.setLibelleProgramme("SMS");
    mobiliteTest.setNomPays("Belgique");
    mobiliteTest.setNomUtilisateur("Leo");
    mobiliteTest.setPrenomUtilisateur("Vanderelst");
    mobiliteTest.setEncodeProeco(false);
    mobiliteTest.setEncodeMobi(false);
    mobiliteTest.setPreContraBourse(false);
    mobiliteTest.setPreConventionDeStage(false);
    mobiliteTest.setPreCharteEtudiant(false);
    mobiliteTest.setPrePreuvePassageTestsLinguistiques(false);
    mobiliteTest.setPreDocumentEngagement(false);
    mobiliteTest.setPostAttestationSejour(false);
    mobiliteTest.setPostReleveNotes(false);
    mobiliteTest.setPostCertificatStage(false);
    mobiliteTest.setPostRapportFinalComplete(false);
    mobiliteTest.setPostPreuvePassageTestsLinguistiques(false);
    mobiliteTest.setNoVersion(1);
    mobiliteSansPartenaire = mobiliteTest;
    mobiliteSansPartenaire.setIdPartenaire(0);
    mobiliteAvecPartenaire = mobiliteTest;
    mobiliteAvecPartenaire.setIdPartenaire(1);
    mobiliteAvecPartenaire.setIdProgramme(3);
    mobiliteAvecPartenaire.setIdMobilite(1);
    utilisateur = new UtilisateurImpl();
    utilisateur.setPseudo("Florianleo");
    utilisateur.setMotDePasse("vraiMotDePasse");
    utilisateur.setEmail("florian.timmermans@student.vinci.be");
    utilisateur.setNom("Test");
    utilisateur.setPrenom("Test");
    utilisateur.setId(1);
    utilisateur.setNoVersion(10);
    utilisateur.setRole("ETUD");
    List<Mobilite> mobilites = new ArrayList<>();
    mobilites.add(mobiliteAvecPartenaire);
    mobiliteUcc.ajouterMobilite(mobilites);
  }

  @AfterEach
  public void apresTout() {
    mobiliteUcc.supprimerMobilite(mobiliteAvecPartenaire);
  }

  /**
   * Methode d'introspection pour gérer les Fatalexecption.
   * 
   * @param throwException true ou false a set.
   */
  private void throwFatalException(boolean throwException) {
    try {
      Field mobiliteDaoUcc = mobiliteUcc.getClass().getDeclaredField("mobiliteDao");
      mobiliteDaoUcc.setAccessible(true);
      MobiliteDaoMock mobilite = (MobiliteDaoMock) mobiliteDaoUcc.get(mobiliteUcc);
      Field field = mobilite.getClass().getDeclaredField("throwException");
      field.setAccessible(true);
      field.setBoolean(mobilite, throwException);
    } catch (NoSuchFieldException exception) {
      exception.printStackTrace();
    } catch (IllegalArgumentException exception) {
      exception.printStackTrace();
    } catch (IllegalAccessException exception) {
      exception.printStackTrace();
    }

  }

  /**
   * Test ajout Sql Fatal Exception.
   */

  @Test
  public void testAjouterMobiliteFatal() {
    throwFatalException(true);
    List<Mobilite> mobilites = new ArrayList<>();
    mobilites.add(mobiliteSansPartenaire);
    assertThrows(FatalException.class, () -> mobiliteUcc.ajouterMobilite(mobilites),
        "Expected mobiliteUcc.ajouterMobilite(mobilites) to throw, but it didn't");
    throwFatalException(false);
  }


  /**
   * Test où la mobilite est incorrecte.
   */
  @Test
  public void testAjouterMobilite01() {
    mobiliteSansPartenaire.setTypeCode("TEST");
    List<Mobilite> mobilites = new ArrayList<>();
    mobilites.add(mobiliteSansPartenaire);
    BizException thrown =
        assertThrows(BizException.class, () -> mobiliteUcc.ajouterMobilite(mobilites),
            "Expected ajouterPartenaire(mobilites) to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Tous les champs de la mobilite ne sont pas valides."));
  }


  /**
   * Test où la mobilite est incorrecte.
   */
  @Test
  public void testAjouterMobilite02() {
    mobiliteAvecPartenaire.setTypeCode("");
    List<Mobilite> mobilites = new ArrayList<>();
    mobilites.add(mobiliteAvecPartenaire);
    BizException thrown =
        assertThrows(BizException.class, () -> mobiliteUcc.ajouterMobilite(mobilites),
            "Expected ajouterPartenaire(mobilites) to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Tous les champs de la mobilite ne sont pas valides."));
  }

  /**
   * Test lister mobilite FatalException.
   */

  @Test
  public void testListerMobiliteFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class, () -> mobiliteUcc.listerMobilite(),
        "Expected listerMobilite() to throw, but it didn't");
    throwFatalException(false);
  }


  /**
   * Test lister mobilites.
   */
  @Test
  public void testListerMobilite01() {
    List<MobiliteDto> mobilites = mobiliteUcc.listerMobilite();
    assertEquals(mobilites.size(), 0);
  }

  /**
   * Test listerDemandesDeMobilites FatalException.
   */
  @Test
  public void testListerDemandesDeMobilitesFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class, () -> mobiliteUcc.listerDemandesDeMobilites(utilisateur),
        "Expected listerDemandesDeMobilites() to throw, but it didn't");
    throwFatalException(false);
  }



  /**
   * Test lister les demandes avec prof.
   */
  @Test
  public void testListerDemandesDeMobilites01() {
    utilisateur.setRole("PROF");
    List<MobiliteDto> mobilites = mobiliteUcc.listerDemandesDeMobilites(utilisateur);
    assertEquals(mobilites.size(), 1);
  }

  /**
   * Test lister les demandes avec etudiant.
   */
  @Test
  public void testListerDemandesDeMobilites02() {
    List<MobiliteDto> mobilites = mobiliteUcc.listerDemandesDeMobilites(utilisateur);
    assertEquals(mobilites.size(), 0);
  }

  /**
   * Test lister mobilite d'un etudiant FatalException.
   */
  @Test
  public void testListerMobiliteDunEtudiantFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class, () -> mobiliteUcc.listerMobiliteDunEtudiant(5),
        "Expected listerMobiliteDunEtudiant(5) to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test lister mobilite d'un etudiant avec une mobilite.
   */
  @Test
  public void testListerMobiliteDunEtudiant01() {
    List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteDunEtudiant(5);
    assertEquals(mobilites.size(), 1);
  }

  /**
   * Test lister mobilite d'un etudiant sans mobilite.
   */
  @Test
  public void testListerMobiliteDunEtudiant02() {
    List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteDunEtudiant(4);
    assertEquals(mobilites.size(), 0);
  }

  /**
   * Test lister mobilite par annee et/ou etat avec etat et annee existante, si l'utilisateur qui
   * veut lister n'est pas étudiant.
   */
  @Test
  public void testListerMobiliteParAnneeEtatFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class,
        () -> mobiliteUcc.listerMobiliteParAnneeEtEtat(mobiliteAvecPartenaire),
        "Expected listerMobiliteParAnneeEtEtat() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test lister mobilite par annee et/ou etat avec etat et annee existante, si l'utilisateur qui
   * veut lister n'est pas étudiant.
   */
  @Test
  public void testListerMobiliteParAnneeEtat01() {
    List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteParAnneeEtEtat(mobiliteAvecPartenaire);
    assertEquals(mobilites.size(), 1);
  }

  /**
   * Test lister mobilite par annee et/ou etat avec etat et annee inexistants, si l'utilisateur
   * n'est pas étudiant.
   */
  @Test
  public void testListerMobiliteParAnneeEtat02() {
    mobiliteAvecPartenaire.setEtat("creee");
    mobiliteAvecPartenaire.setAnneeAcademique("");
    List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteParAnneeEtEtat(mobiliteAvecPartenaire);
    assertEquals(mobilites.size(), 0);
  }

  /**
   * Test lister mobilite par annee et/ou etat avec une annee inexistante, si l'utilisateur qui veut
   * lister n'est pas étudiant.
   */
  @Test
  public void testListerMobiliteParAnneeEtat03() {
    mobiliteSansPartenaire.setAnneeAcademique("2008-2009");
    List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteParAnneeEtEtat(mobiliteAvecPartenaire);
    assertEquals(mobilites.size(), 1);
  }

  /**
   * Test lister mobilite par annee et/ou etat avec un etat inexistant, si l'utilisateur qui veut
   * lister n'est pas étudiant.
   */
  @Test
  public void testListerMobiliteParAnneeEtat04() {
    mobiliteSansPartenaire.setEtat("creee");
    List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteParAnneeEtEtat(mobiliteAvecPartenaire);
    assertEquals(mobilites.size(), 1);
  }

  /**
   * Test confirmer mobilite FatalException.
   */
  @Test
  public void testConfirmerMobiliteFatal() {
    mobiliteAvecPartenaire.setEtat("demandee");
    utilisateur.setRole("PROF");
    throwFatalException(true);
    assertThrows(FatalException.class,
        () -> mobiliteUcc.confirmerMobilite(mobiliteAvecPartenaire, utilisateur),
        "Expected confirmerMobilite() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test confirmer mobilite avec une mobilite en etat demandee.
   */
  @Test
  public void testConfirmerMobilite01() {
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.confirmerMobilite(mobiliteAvecPartenaire, utilisateur),
        "Expected confirmerMobilite(mobiliteAvecPartenaire, utilisateur) to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test confirmer mobilite avec une mobilite en etat creee.
   */
  @Test
  public void testConfirmerMobilite02() {
    mobiliteAvecPartenaire.setEtat("creee");
    utilisateur.setRole("PROF");
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.confirmerMobilite(mobiliteAvecPartenaire, utilisateur),
        "Expected confirmerMobilite(mobiliteAvecPartenaire, utilisateur)"
            + " to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("La mobilité a déjà été confirmée ou annulée"));
  }

  /**
   * Test confirmer mobilite avec une mobilite en etat annulee.
   */
  @Test
  public void testConfirmerMobilite03() {
    mobiliteAvecPartenaire.setEtat("annulee");
    utilisateur.setRole("PROF");
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.confirmerMobilite(mobiliteAvecPartenaire, utilisateur),
        "Expected confirmerMobilite(mobiliteAvecPartenaire, utilisateur)"
            + " to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("La mobilité a déjà été confirmée ou annulée"));
  }

  /**
   * Test confirmer mobilite avec un etat possible et un prof.
   */
  @Test
  public void testConfirmerMobilite04() {
    mobiliteAvecPartenaire.setEtat("demandee");
    utilisateur.setRole("PROF");
    mobiliteUcc.confirmerMobilite(mobiliteAvecPartenaire, utilisateur);
    assertTrue(mobiliteAvecPartenaire.getEtat().equals("creee"));
  }

  /**
   * Test annuler mobilité FatalException.
   */
  @Test
  public void testAnnulerMobiliteFatal() {
    throwFatalException(true);
    mobiliteAvecPartenaire.setEtat("creee");
    assertThrows(FatalException.class, () -> mobiliteUcc.annulerMobilite(mobiliteAvecPartenaire),
        "Expected annulerMobilite(mobiliteAvecPartenaire) to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test annuler mobilité avec etat annulable.
   */
  @Test
  public void testAnnulerMobilite01() {
    mobiliteAvecPartenaire.setEtat("creee");
    mobiliteUcc.annulerMobilite(mobiliteAvecPartenaire);
    assertTrue(mobiliteAvecPartenaire.getEtat().equals("annulee"));
  }

  /**
   * Test annuler ou elle n'est pas dans un etat annulable.
   */
  @Test
  public void testAnnulerMobilite02() {
    mobiliteAvecPartenaire.setEtat("a payer solde");
    BizException thrown =
        assertThrows(BizException.class, () -> mobiliteUcc.annulerMobilite(mobiliteAvecPartenaire),
            "Expected annulerMobilite(mobiliteAvecPartenaire) to throw, but it didn't");
    assertTrue(
        thrown.getMessage().contains("La mobilité n'est pas dans un état possible à annuler"));
  }

  /**
   * Test trouver mobilte par id FatalException.
   */
  @Test
  public void testTrouverMobiliteParIdFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class, () -> mobiliteUcc.trouverMobiliteParId(1),
        "Expected trouverMobiliteParId(1) to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test trouver mobilte par id.
   */
  @Test
  public void testTrouverMobiliteParId01() {
    assertTrue(mobiliteAvecPartenaire
        .getIdMobilite() == (mobiliteUcc.trouverMobiliteParId(1).getIdMobilite()));
  }

  /**
   * Test trouver mobilte par id avec une mobilite inexistante.
   */
  @Test
  public void testTrouverMobiliteParId02() {
    assertTrue(mobiliteUcc.trouverMobiliteParId(15) == null);
  }

  /**
   * Test signer documents FatalException.
   */
  @Test
  public void testSignerDocumentsFatal() {
    throwFatalException(true);
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    assertThrows(FatalException.class, () -> mobiliteUcc.signerDocuments(mobiliteAvecPartenaire),
        "Expected mobiliteUcc.signerDocuments(mobiliteAvecPartenaire) to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test signer documents avec bons documents.
   */
  @Test
  public void testSignerDocuments01() {
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    mobiliteUcc.signerDocuments(mobiliteAvecPartenaire);
    assertTrue(mobiliteUcc.trouverMobiliteParId(1).getEtat().equals("en preparation"));
  }

  /**
   * Test signer documents avec un documents post signer alors que tous les pre ne le sont pas.
   */
  @Test
  public void testSignerDocuments02() {
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    mobiliteAvecPartenaire.setPostAttestationSejour(true);
    BizException thrown =
        assertThrows(BizException.class, () -> mobiliteUcc.signerDocuments(mobiliteAvecPartenaire),
            "Expected mobiliteUcc.signerDocuments(mobiliteAvecPartenaire, utilisateur) to throw");
    assertTrue(thrown.getMessage().contains(
        "Impossible de signer les post documents si tous les pre documents ne sont pas signés"));
  }

  /**
   * Test signer documents avec tous les documents pre signés.
   */
  @Test
  public void testSignerDocuments03() {
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    mobiliteAvecPartenaire.setPreContraBourse(true);
    mobiliteAvecPartenaire.setPreConventionDeStage(true);
    mobiliteAvecPartenaire.setPreDocumentEngagement(true);
    mobiliteAvecPartenaire.setPrePreuvePassageTestsLinguistiques(true);
    mobiliteUcc.signerDocuments(mobiliteAvecPartenaire);
    assertTrue(mobiliteUcc.trouverMobiliteParId(1).getEtat().equals("a payer"));
  }

  /**
   * Test signer documents avec tous les pre et 1 post signé.
   */
  @Test
  public void testSignerDocuments04() {
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    mobiliteAvecPartenaire.setPreContraBourse(true);
    mobiliteAvecPartenaire.setPreConventionDeStage(true);
    mobiliteAvecPartenaire.setPreDocumentEngagement(true);
    mobiliteAvecPartenaire.setPrePreuvePassageTestsLinguistiques(true);
    mobiliteAvecPartenaire.setPostAttestationSejour(true);
    mobiliteUcc.signerDocuments(mobiliteAvecPartenaire);
    assertTrue(mobiliteUcc.trouverMobiliteParId(1).getEtat().equals("a payer"));
  }

  /**
   * Test signer documents avec tous les pre et tous les post signés.
   */
  @Test
  public void testSignerDocuments06() {
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    mobiliteAvecPartenaire.setPreContraBourse(true);
    mobiliteAvecPartenaire.setPreConventionDeStage(true);
    mobiliteAvecPartenaire.setPreDocumentEngagement(true);
    mobiliteAvecPartenaire.setPrePreuvePassageTestsLinguistiques(true);
    mobiliteAvecPartenaire.setPostAttestationSejour(true);
    mobiliteAvecPartenaire.setPostCertificatStage(true);
    mobiliteAvecPartenaire.setPostPreuvePassageTestsLinguistiques(true);
    mobiliteAvecPartenaire.setPostRapportFinalComplete(true);
    mobiliteAvecPartenaire.setPostReleveNotes(true);
    mobiliteUcc.signerDocuments(mobiliteAvecPartenaire);
    assertTrue(mobiliteUcc.trouverMobiliteParId(1).getEtat().equals("a payer solde"));
  }

  /**
   * Test envoyer demande premier paiement FatalException.
   */
  @Test
  public void testEnvoyerDemandePremierPaiementFatal() {
    throwFatalException(true);
    mobiliteAvecPartenaire.setEtat("demandee");
    utilisateur.setRole("PROF");
    assertThrows(FatalException.class,
        () -> mobiliteUcc.indiquerEnvoiPremiereDemandePaiement(utilisateur, mobiliteAvecPartenaire),
        "Expected mobiliteUcc.indiquerEnvoiPremiereDemandePaiement() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test envoyer demande premier paiement avec eleve.
   */
  @Test
  public void testEnvoyerDemandePremierPaiement01() {
    mobiliteAvecPartenaire.setEtat("demandee");
    utilisateur.setRole("ETUD");
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.indiquerEnvoiPremiereDemandePaiement(utilisateur, mobiliteAvecPartenaire),
        "Expected confirmerMobilite(mobiliteAvecPartenaire, utilisateur) to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test envoyer demande premier paiement dans un etat non acceptant.
   */
  @Test
  public void testEnvoyerDemandePremierPaiement02() {
    mobiliteAvecPartenaire.setEtat("demandee");
    utilisateur.setRole("PROF");
    assertFalse(
        mobiliteUcc.indiquerEnvoiPremiereDemandePaiement(utilisateur, mobiliteAvecPartenaire));
  }

  /**
   * Test envoyer demande premier paiement dans un etat acceptant.
   */
  @Test
  public void testEnvoyerDemandePremierPaiement03() {
    mobiliteAvecPartenaire.setEtat("creee");
    utilisateur.setRole("PROF");
    assertTrue(
        mobiliteUcc.indiquerEnvoiPremiereDemandePaiement(utilisateur, mobiliteAvecPartenaire));
  }

  /**
   * Test envoyer demande premier paiement dans un etat acceptant.
   */
  @Test
  public void testEnvoyerDemandePremierPaiement04() {
    mobiliteAvecPartenaire.setEtat("en preparation");
    utilisateur.setRole("PROF");
    assertTrue(
        mobiliteUcc.indiquerEnvoiPremiereDemandePaiement(utilisateur, mobiliteAvecPartenaire));
  }

  /**
   * Test envoyer demande premier paiement dans un etat acceptant.
   */
  @Test
  public void testEnvoyerDemandePremierPaiement05() {
    mobiliteAvecPartenaire.setEtat("a payer");
    utilisateur.setRole("PROF");
    assertTrue(
        mobiliteUcc.indiquerEnvoiPremiereDemandePaiement(utilisateur, mobiliteAvecPartenaire));
  }


  /**
   * Test envoyer demande second paiement FatalException.
   */
  @Test
  public void testEnvoyerSecondPaiementFatal() {
    throwFatalException(true);
    mobiliteAvecPartenaire.setEtat("a payer solde");
    utilisateur.setRole("PROF");
    assertThrows(FatalException.class,
        () -> mobiliteUcc.indiquerEnvoiSecondeDemandePaiement(utilisateur, mobiliteAvecPartenaire),
        "Expected indiquerEnvoiSecondeDemandePaiement() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test envoyer demande second paiement avec eleve.
   */
  @Test
  public void testEnvoyerSecondPaiement01() {
    mobiliteAvecPartenaire.setEtat("a payer solde");
    utilisateur.setRole("ETUD");
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.indiquerEnvoiSecondeDemandePaiement(utilisateur, mobiliteAvecPartenaire),
        "Expected confirmerMobilite(mobiliteAvecPartenaire, utilisateur) to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test envoyer demande second paiement dans un etat non acceptant.
   */
  @Test
  public void testEnvoyerSecondPaiement02() {
    mobiliteAvecPartenaire.setEtat("demandee");
    utilisateur.setRole("PROF");
    assertFalse(
        mobiliteUcc.indiquerEnvoiSecondeDemandePaiement(utilisateur, mobiliteAvecPartenaire));
  }

  /**
   * Test envoyer demande second paiement dans un etat acceptant.
   */
  @Test
  public void testEnvoyerSecondPaiement03() {
    mobiliteAvecPartenaire.setEtat("a payer solde");
    utilisateur.setRole("PROF");
    assertTrue(
        mobiliteUcc.indiquerEnvoiSecondeDemandePaiement(utilisateur, mobiliteAvecPartenaire));
  }

  /**
   * Test obtenirListeEtatsDocumentsEtMobilite FatalException.
   */
  @Test
  public void testObtenirListeEtatsDocumentsEtMobiliteFatal() {
    throwFatalException(true);
    utilisateur.setRole("PROF");
    assertThrows(FatalException.class,
        () -> mobiliteUcc.obtenirListeEtatsDocumentsEtMobilite(utilisateur, mobiliteAvecPartenaire),
        "Expected obtenirListeEtatsDocumentsEtMobilite() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test obtenirListeEtatsDocumentsEtMobilite avec eleve.
   */
  @Test
  public void testObtenirListeEtatsDocumentsEtMobilite01() {
    utilisateur.setRole("ETUD");
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.obtenirListeEtatsDocumentsEtMobilite(utilisateur, mobiliteAvecPartenaire),
        "Expected obtenirListeEtatsDocumentsEtMobilite(utilisateur, mobiliteAvecPartenaire)"
            + " to throw");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test obtenirListeEtatsDocumentsEtMobilite avec prof.
   */
  @Test
  public void testObtenirListeEtatsDocumentsEtMobilite02() {
    utilisateur.setRole("PROF");
    mobiliteAvecPartenaire.setEncodeMobi(true);
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    Map<String, String> etats = new HashMap<>();
    etats.put("preContratBourse", "nonSigne");
    etats.put("preConventionDeStageEtudes", "nonSigne");
    etats.put("preCharteEtudiant", "signe");
    etats.put("prePreuvePassageTestsLinguistiques", "nonSigne");
    etats.put("preDocumentsEngagement", "nonSigne");
    etats.put("postAttestationSejour", "nonSigne");
    etats.put("postReleveNotes", "nonSigne");
    etats.put("postCertificatStage", "nonSigne");
    etats.put("postRapportFinalComplete", "nonSigne");
    etats.put("postPreuvePassageTestsLinguistiques", "nonSigne");
    etats.put("encodeProeco", "nonSigne");
    etats.put("encodeMobi", "signe");
    Map<String, String> etatsRecus =
        mobiliteUcc.obtenirListeEtatsDocumentsEtMobilite(utilisateur, mobiliteAvecPartenaire);
    assertTrue(etats.equals(etatsRecus));
  }

  /**
   * Test indiquerEtudiantEncodeDansLogiciels FatalException.
   */
  @Test
  public void testIndiquerEtudiantEncodeDansLogicielsFatal() {
    throwFatalException(true);
    utilisateur.setRole("PROF");
    mobiliteAvecPartenaire.setEncodeMobi(true);
    assertThrows(FatalException.class,
        () -> mobiliteUcc.indiquerEtudiantEncodeDansLogiciels(utilisateur, mobiliteAvecPartenaire),
        "Expected indiquerEtudiantEncodeDansLogiciels() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test indiquerEtudiantEncodeDansLogiciels avec un eleve.
   */
  @Test
  public void testIndiquerEtudiantEncodeDansLogiciels01() {
    utilisateur.setRole("ETUD");
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.indiquerEtudiantEncodeDansLogiciels(utilisateur, mobiliteAvecPartenaire),
        "Expected indiquerEtudiantEncodeDansLogiciels(utilisateur, mobiliteAvecPartenaire)"
            + " to throw");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test indiquerEtudiantEncodeDansLogiciels avec un document encoder.
   */
  @Test
  public void testIndiquerEtudiantEncodeDansLogiciels02() {
    utilisateur.setRole("PROF");
    mobiliteAvecPartenaire.setEncodeMobi(true);
    mobiliteUcc.indiquerEtudiantEncodeDansLogiciels(utilisateur, mobiliteAvecPartenaire);
    assertTrue(mobiliteUcc.trouverMobiliteParId(1).estEncodeMobi());
  }

  /**
   * Test indiquerEtudiantEncodeDansLogiciels avec un document encoder.
   */
  @Test
  public void testIndiquerEtudiantEncodeDansLogiciels03() {
    utilisateur.setRole("PROF");
    mobiliteAvecPartenaire.setEncodeProeco(true);
    mobiliteUcc.indiquerEtudiantEncodeDansLogiciels(utilisateur, mobiliteAvecPartenaire);
    assertTrue(mobiliteUcc.trouverMobiliteParId(1).estEncodeProeco());
  }

  /**
   * Test listerDemandesPaiementsParAnneeAcademique FatalException.
   */
  @Test
  public void testListerDemandesPaiementsParAnneeAcademiqueFatal() {
    throwFatalException(true);
    utilisateur.setRole("PROF");
    assertThrows(FatalException.class,
        () -> mobiliteUcc.listerDemandesPaiementsParAnneeAcademique(utilisateur,
            mobiliteAvecPartenaire),
        "Expected indiquerEtudiantEncodeDansLogiciels() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test listerDemandesPaiementsParAnneeAcademique FatalException.
   */
  @Test
  public void testListerDemandesPaiementsParAnneeAcademiqueBizException() {
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.listerDemandesPaiementsParAnneeAcademique(utilisateur,
            mobiliteAvecPartenaire),
        "Expected preciserReceptionDocumentsRetourMobilite() to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test listerDemandesPaiementsParAnneeAcademique avec une bonne annee académique.
   */
  @Test
  public void testListerDemandesPaiementsParAnneeAcademique01() {
    utilisateur.setRole("PROF");
    assertTrue(
        mobiliteUcc.listerDemandesPaiementsParAnneeAcademique(utilisateur, mobiliteAvecPartenaire)
            .size() == 1);
  }

  /**
   * Test listerDemandesPaiementsParAnneeAcademique avec une mauvaise annee.
   */
  @Test
  public void testListerDemandesPaiementsParAnneeAcademique02() {
    utilisateur.setRole("PROF");
    mobiliteAvecPartenaire.setAnneeAcademique("2040-2050");
    assertTrue(
        mobiliteUcc.listerDemandesPaiementsParAnneeAcademique(utilisateur, mobiliteAvecPartenaire)
            .size() == 0);
  }


  /**
   * Test obtenirDemandesPaiementsPourMobilite FatalException.
   */
  @Test
  public void testObtenirDemandesPaiementsPourMobiliteFatal() {
    throwFatalException(true);
    utilisateur.setRole("PROF");
    assertThrows(FatalException.class,
        () -> mobiliteUcc.obtenirDemandesPaiementsPourMobilite(utilisateur, mobiliteAvecPartenaire),
        "Expected indiquerEtudiantEncodeDansLogiciels() to throw, but it didn't");
    throwFatalException(false);
  }


  /**
   * Test obtenirDemandesPaiementsPourMobilite pas les droits.
   */
  @Test
  public void testObtenirDemandesPaiementsPourMobiliteBizException() {
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.obtenirDemandesPaiementsPourMobilite(utilisateur, mobiliteAvecPartenaire),
        "Expected preciserDocumentsDepartMobiliteSignes() to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test obtenirDemandesPaiementsPourMobilite correct.
   */
  @Test
  public void testObtenirDemandesPaiementsPourMobilite01() {
    utilisateur.setRole("PROF");
    Boolean[] test =
        mobiliteUcc.obtenirDemandesPaiementsPourMobilite(utilisateur, mobiliteAvecPartenaire);
    assertEquals(test[0], false);
    assertEquals(test[1], false);
  }

  /**
   * Test preciserDocumentsDepartMobilitesSignes pas les droits.
   */
  @Test
  public void testPreciserDocumentsDepartMobiliteSignesFatal() {
    throwFatalException(true);
    utilisateur.setRole("PROF");
    assertThrows(FatalException.class,
        () -> mobiliteUcc.preciserDocumentsDepartMobiliteSignes(utilisateur,
            mobiliteAvecPartenaire),
        "Expected preciserDocumentsDepartMobiliteSignes() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test preciserDocumentsDepartMobilitesSignes pas les droits.
   */
  @Test
  public void testPreciserDocumentsDepartMobiliteSignesBizException() {
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.preciserDocumentsDepartMobiliteSignes(utilisateur,
            mobiliteAvecPartenaire),
        "Expected preciserDocumentsDepartMobiliteSignes() to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test preciserDocumentsDepartMobiliteSignes.
   */

  @Test
  public void testPreciserDocumentsDepartMobiliteSignes01() {
    utilisateur.setRole("PROF");
    mobiliteAvecPartenaire.setPreCharteEtudiant(true);
    mobiliteAvecPartenaire.setPreContraBourse(true);
    mobiliteAvecPartenaire.setPreConventionDeStage(true);
    mobiliteAvecPartenaire.setPreDocumentEngagement(true);
    mobiliteAvecPartenaire.setPrePreuvePassageTestsLinguistiques(true);
    assertFalse(
        mobiliteUcc.preciserDocumentsDepartMobiliteSignes(utilisateur, mobiliteAvecPartenaire));
  }


  /**
   * Test preciserDocumentsDepartMobilitesSignes pas les droits.
   */
  @Test
  public void testPreciserReceptionDocumentsRetourMobiliteFatal() {
    throwFatalException(true);
    utilisateur.setRole("PROF");
    assertThrows(FatalException.class,
        () -> mobiliteUcc.preciserReceptionDocumentsRetourMobilite(utilisateur,
            mobiliteAvecPartenaire),
        "Expected preciserReceptionDocumentsRetourMobilite() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test preciserDocumentsDepartMobilitesSignes pas les droits.
   */
  @Test
  public void testPreciserReceptionDocumentsRetourMobiliteBizException() {
    BizException thrown = assertThrows(BizException.class,
        () -> mobiliteUcc.preciserReceptionDocumentsRetourMobilite(utilisateur,
            mobiliteAvecPartenaire),
        "Expected preciserReceptionDocumentsRetourMobilite() to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette action."));
  }

  /**
   * Test preciserDocumentsDepartMobiliteSignes.
   */

  @Test
  public void testPreciserReceptionDocumentsRetourMobilite01() {
    utilisateur.setRole("PROF");
    assertFalse(
        mobiliteUcc.preciserReceptionDocumentsRetourMobilite(utilisateur, mobiliteAvecPartenaire));
  }


}
