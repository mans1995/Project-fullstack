package be.ipl.pae.biz.mobilite;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class MobiliteImplTest {

  private MobiliteImpl mobilite;
  private String testLongueur;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si le setUp fail.
   */
  @BeforeEach
  void setUp() throws Exception {
    mobilite = new MobiliteImpl();
    mobilite.setIdMobilite(1);
    mobilite.setIdPartenaire(1);
    mobilite.setIdPays(1);
    mobilite.setIdUtilisateur(1);
    mobilite.setPreference(1);
    mobilite.setTypeCode("SMS");
    mobilite.setEtat("demande");
    mobilite.setEtatAvantAnnulation("");
    mobilite.setRaisonAnnulation("");
    mobilite.setAnneeAcademique("2018-2019");
    mobilite.setDateIntroduction(LocalDate.now());
    mobilite.setEncodeProeco(true);
    mobilite.setEncodeMobi(true);
    mobilite.setPreContraBourse(false);
    mobilite.setPreConventionDeStage(false);
    mobilite.setPreCharteEtudiant(false);
    mobilite.setPrePreuvePassageTestsLinguistiques(false);
    mobilite.setPreDocumentEngagement(false);
    mobilite.setPostAttestationSejour(false);
    mobilite.setPostReleveNotes(false);
    mobilite.setPostCertificatStage(false);
    mobilite.setPostRapportFinalComplete(false);
    mobilite.setPostPreuvePassageTestsLinguistiques(false);
    mobilite.setNoVersion(1);

    testLongueur =
        "teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeest";
  }

  @Test
  public void testMobiliteEstValide00() {
    assertTrue(mobilite.mobiliteEstValide());
  }

  @Test
  public void testMobiliteEstValide01() {
    mobilite.setTypeCode("");
    assertFalse(mobilite.mobiliteEstValide());
    mobilite.setTypeCode(null);
    assertFalse(mobilite.mobiliteEstValide());
    mobilite.setTypeCode(testLongueur);
    assertFalse(mobilite.mobiliteEstValide());
  }


  /**
   * Test avec un numéro de version inférieur à 1.
   */
  @Test
  public void testMobiliteEstValide02() {
    mobilite.setNoVersion(-1);
    assertFalse(mobilite.mobiliteEstValide());
  }

  /**
   * Test avec un id pays inférieur à 1.
   */
  @Test
  public void testMobiliteEstValide03() {
    mobilite.setIdPays(-1);
    assertTrue(mobilite.mobiliteEstValide());
  }

  /**
   * Test avec un id partenaire inférieur à 1.
   */
  @Test
  public void testMobiliteEstValide04() {
    mobilite.setIdPartenaire(-1);
    assertTrue(mobilite.mobiliteEstValide());
  }

  /**
   * Test avec un id partenaire et id pays inférieur à 1.
   */
  @Test
  public void testMobiliteEstValide05() {
    /*
     * mobilite.setIdPartenaire(-1); mobilite.setIdPays(-1);
     * assertFalse(mobilite.mobiliteEstValide());
     */
  }

  /**
   * Test en preparation.
   */
  @Test
  public void testEstEnPreparation() {
    mobilite.setPreContraBourse(true);
    assertTrue(mobilite.estEnPreparation());
  }

  /**
   * Test a payer.
   */
  @Test
  public void testApayer() {
    mobilite.setPreContraBourse(true);
    mobilite.setPreCharteEtudiant(true);
    mobilite.setPreConventionDeStage(true);
    mobilite.setPreDocumentEngagement(true);
    mobilite.setPrePreuvePassageTestsLinguistiques(true);
    assertTrue(mobilite.estaPayer());
  }

  /**
   * Test de la méthode incrementNoVersion.
   */
  @Test
  public void testIncrementNoVersion() {
    int noVersion = mobilite.getNoVersion();
    mobilite.incrementNoVersion();
    assertTrue(mobilite.getNoVersion() == noVersion + 1);
  }


  /**
   * Test setNoVersion modifie bien le numéro de version.
   */
  @Test
  public void testSetNoVersion() {
    mobilite.setNoVersion(100);
    assertTrue(mobilite.getNoVersion() == 100);
  }
}
