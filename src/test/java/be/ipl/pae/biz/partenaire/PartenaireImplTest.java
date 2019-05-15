package be.ipl.pae.biz.partenaire;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartenaireImplTest {

  private PartenaireImpl partenaire;
  private String testLongueur;

  /**
   * Set up pour les tests.
   * 
   * @throws Exception si setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    partenaire = new PartenaireImpl();
    partenaire.setTypeCode("SMS");
    partenaire.setNomLegal("Nom legal");
    partenaire.setNomDaffaires("nom affaire");
    partenaire.setNomComplet("nom complet");
    partenaire.setDepartement("departement");
    partenaire.setTypeOrganisation("org");
    partenaire.setNombreEmployes(10);
    partenaire.setAdresse("adresse");
    partenaire.setIdPays(2);
    partenaire.setRegion("region");
    partenaire.setCodePostal("1020");
    partenaire.setVille("bxl");
    partenaire.setEmail("jean@live.be");
    partenaire.setSiteWeb("www.test.com");
    partenaire.setTelephone("0472656485");
    partenaire.setId(1);
    partenaire.setNoVersion(1);
    testLongueur =
        "teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeest";
  }

  /**
   * Test avec un partenaire Valide.
   */
  @Test
  public void testPartenaireEstValide01() {
    assertTrue(partenaire.partenaireEstValide());
  }

  /**
   * Test avec un typeCode non valide.
   */
  @Test
  public void testPartenaireEstValide02() {
    partenaire.setTypeCode("");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setTypeCode(null);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec nomLegal non valide.
   */
  @Test
  public void testPartenaireEstValide03() {
    partenaire.setNomLegal("");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setNomLegal(null);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec nomDaffaires non valide.
   */
  @Test
  public void testPartenaireEstValide04() {
    partenaire.setNomDaffaires("");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setNomDaffaires(null);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec nomComplet non valide.
   */
  @Test
  public void testPartenaireEstValide05() {
    partenaire.setNomComplet("");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setNomComplet(null);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec typeOrganisation non valide.
   */
  @Test
  public void testPartenaireEstValide06() {
    partenaire.setTypeOrganisation("");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setTypeOrganisation(null);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec adresse non valide.
   */
  @Test
  public void testPartenaireEstValide07() {
    partenaire.setAdresse("");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setAdresse(null);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec ville non valide.
   */
  @Test
  public void testPartenaireEstValide09() {
    partenaire.setVille("");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setVille(null);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec typeCode trop long.
   */
  @Test
  public void testPartenaireEstValide10() {
    partenaire.setTypeCode(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec nomLegal trop long.
   */
  @Test
  public void testPartenaireEstValide11() {
    partenaire.setNomLegal(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec un nomDaffaires trop long.
   */
  @Test
  public void testPartenaireEstValide12() {
    partenaire.setNomDaffaires(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec nomComplet trop long.
   */
  @Test
  public void testPartenaireEstValide13() {
    partenaire.setNomComplet(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec typeOrganisation trop long.
   */
  @Test
  public void testPartenaireEstValide14() {
    partenaire.setTypeOrganisation(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec adresse trop long.
   */
  @Test
  public void testPartenaireEstValide15() {
    partenaire.setAdresse(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec ville trop long.
   */
  @Test
  public void testPartenaireEstValide17() {
    partenaire.setVille(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec departement trop long.
   */
  @Test
  public void testPartenaireEstValide18() {
    partenaire.setDepartement("departement");
    assertTrue(partenaire.partenaireEstValide());
    partenaire.setDepartement(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec region trop long.
   */
  @Test
  public void testPartenaireEstValide19() {
    partenaire.setRegion("region");
    assertTrue(partenaire.partenaireEstValide());
    partenaire.setRegion(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec email non valide regex.
   */
  @Test
  public void testPartenaireEstValide20() {
    partenaire.setEmail("jean@live.be");
    assertTrue(partenaire.partenaireEstValide());
    partenaire.setEmail("test");
    assertFalse(partenaire.partenaireEstValide());
    partenaire.setEmail(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec siteWeb trop long.
   */
  @Test
  public void testPartenaireEstValide21() {
    partenaire.setSiteWeb("www.test.com");
    assertTrue(partenaire.partenaireEstValide());
    partenaire.setSiteWeb(testLongueur);
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec telephone non valide regex.
   */
  @Test
  public void testPartenaireEstValide22() {
    partenaire.setTelephone("abc");
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test avec telephone trop long.
   */
  @Test
  public void testPartenaireEstValide23() {
    partenaire.setTelephone("0451265345");
    assertTrue(partenaire.partenaireEstValide());
    partenaire.setTelephone("044555555555555555555");
    assertFalse(partenaire.partenaireEstValide());
  }

  /**
   * Test de la méthode incrementNoVersion.
   */
  @Test
  public void testIncrementNoVersion() {
    int noVersion = partenaire.getNoVersion();
    partenaire.incrementNoVersion();
    assertTrue(partenaire.getNoVersion() == noVersion + 1);
  }

  /**
   * Test setId modifie bien l'id.
   */
  @Test
  public void testSetId() {
    partenaire.setId(100);
    assertTrue(partenaire.getId() == 100);
  }

  /**
   * Test setNoVersion modifie bien le numéro de version.
   */
  @Test
  public void testSetNoVersion() {
    partenaire.setNoVersion(100);
    assertTrue(partenaire.getNoVersion() == 100);
  }

}
