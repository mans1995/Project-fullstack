package be.ipl.pae.biz.infosetudiant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class InfosEtudiantImplTest {

  private InfosEtudiantImpl infosEtudiantValide;
  private InfosEtudiantImpl infosEtudiantNonValide;
  private String testLongueur;

  /**
   * Methode pour set up avant les tests.
   * 
   * @throws Exception si setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    infosEtudiantValide = new InfosEtudiantImpl();
    infosEtudiantValide.setStatut("Mlle");
    infosEtudiantValide.setNom("Michel");
    infosEtudiantValide.setPrenom("René");
    infosEtudiantValide.setDateNaissance(
        Date.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    infosEtudiantValide.setNationalite(2);
    infosEtudiantValide.setAdresse("Rue des champs, 12");
    infosEtudiantValide.setTelephone("0472656485");
    infosEtudiantValide.setEmail("jean@student.vinci.be");
    infosEtudiantValide.setSexe("M");
    infosEtudiantValide.setNbAnneesReussies(1);
    infosEtudiantValide.setIban("BE68539007547034");
    infosEtudiantValide.setTitulaireCompte("Marc Von Mei");
    infosEtudiantValide.setNomBanque("ING");
    infosEtudiantValide.setCodeBic("BBRUBEBB");
    infosEtudiantValide.setNoVersion(1);
    infosEtudiantValide.setIdUtilisateur(1);
    infosEtudiantNonValide = infosEtudiantValide;
    testLongueur =
        "teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeest";
  }

  /**
   * Test avec des informations d'étudiant valides.
   */
  @Test
  public void testPartenaireEstValide01() {
    assertTrue(infosEtudiantValide.infosEtudiantValides());
  }

  /**
   * Test avec statut non valide.
   */
  @Test
  public void testPartenaireEstValide02() {
    infosEtudiantNonValide.setStatut("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setStatut(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setStatut(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setStatut("R");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec une date de naissance non valide.
   */
  @Test
  public void testPartenaireEstValide03() {
    infosEtudiantNonValide.setDateNaissance(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec un identifiant de nationalité non valide.
   */
  @Test
  public void testPartenaireEstValide04() {
    infosEtudiantNonValide.setNationalite(-1);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec adresse non valide.
   */
  @Test
  public void testPartenaireEstValide05() {
    infosEtudiantNonValide.setAdresse("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setAdresse(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setAdresse(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec numéro de téléphone non valide.
   */
  @Test
  public void testPartenaireEstValide06() {
    infosEtudiantNonValide.setTelephone("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setTelephone(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setTelephone(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setTelephone("01kdslkdlg");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec sexe non valide.
   */
  @Test
  public void testPartenaireEstValide07() {
    infosEtudiantNonValide.setSexe("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setSexe(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setSexe(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setSexe("R");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec nombre d'années réussies non valide.
   */
  @Test
  public void testPartenaireEstValide08() {
    infosEtudiantNonValide.setNbAnneesReussies(-1);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec IBAN non valide.
   */
  @Test
  public void testPartenaireEstValide09() {
    infosEtudiantNonValide.setIban("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setIban(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setSexe(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setIban("BEE68539007547034");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setIban("BE685390075470341");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec titulaire compte non valide.
   */
  @Test
  public void testPartenaireEstValide10() {
    infosEtudiantNonValide.setTitulaireCompte("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setTitulaireCompte(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setTitulaireCompte(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec nom banque non valide.
   */
  @Test
  public void testPartenaireEstValide11() {
    infosEtudiantNonValide.setNomBanque("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setNomBanque(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setNomBanque(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec code BIC non valide.
   */
  @Test
  public void testPartenaireEstValide12() {
    infosEtudiantNonValide.setCodeBic("");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setCodeBic(null);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setCodeBic(testLongueur);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setCodeBic("BBRUBEBBE");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
    infosEtudiantNonValide.setCodeBic("BBRUBEBB1");
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec un id de l'utilisateur associé inférieur à 1.
   */
  @Test
  public void testPartenaireValide13() {
    infosEtudiantNonValide.setIdUtilisateur(-1);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test avec un numéro de version inférieur à 1.
   */
  @Test
  public void testPartenaireValide14() {
    infosEtudiantNonValide.setNoVersion(-1);
    assertFalse(infosEtudiantNonValide.infosEtudiantValides());
  }

  /**
   * Test de la méthode getAdresse.
   */
  public void testGetAdresse() {
    assertTrue("Rue des champs, 12".equals(infosEtudiantValide.getAdresse()));
  }

  /**
   * Test de la méthode getCodeBic.
   */
  public void testGetCodeBic() {
    assertTrue("BBRUBEBB".equals(infosEtudiantValide.getCodeBic()));
  }

  /**
   * Test de la méthode getDateNaissance.
   */
  public void testGetDateNaissance() {
    assertTrue(Date.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant())
        .equals(infosEtudiantValide.getDateNaissance()));
  }

  /**
   * Test de la méthode getEmail.
   */
  public void testGetEmail() {
    assertTrue("jean@student.vinci.be".equals(infosEtudiantValide.getEmail()));
  }

  /**
   * Test de la méthode getIban.
   */
  public void testGetIban() {
    assertTrue("BE68539007547034".equals(infosEtudiantValide.getIban()));
  }

  /**
   * Test de la méthode getNationalite.
   */
  public void testGetNationalite() {
    assertTrue(infosEtudiantValide.getNationalite() == 2);
  }

  /**
   * Test de la méthode getNbAnneesReussies.
   */
  public void testGetNbAnneesReussies() {
    assertTrue(infosEtudiantValide.getNbAnneesReussies() == 1);
  }

  /**
   * Test de la méthode getNom.
   */
  public void testGetNom() {
    assertTrue("Michel".equals(infosEtudiantValide.getNom()));
  }

  /**
   * Test de la méthode getPrenom.
   */
  public void testGetPrenom() {
    assertTrue("René".equals(infosEtudiantValide.getPrenom()));
  }

  /**
   * Test de la méthode getNomBanque.
   */
  public void testGetNomBanque() {
    assertTrue("ING".equals(infosEtudiantValide.getNomBanque()));
  }

  /**
   * Test de la méthode getStatut.
   */
  public void testGetStatut() {
    assertTrue("Mlle".equals(infosEtudiantValide.getStatut()));
  }

  /**
   * Test de la méthode getSexe.
   */
  public void testGetSexe() {
    assertTrue("M".equals(infosEtudiantValide.getStatut()));
  }

  /**
   * Test de la méthode getTitulaireCompte.
   */
  public void testGetTitulaireCompte() {
    assertTrue("Marc Von Mei".equals(infosEtudiantValide.getTitulaireCompte()));
  }

  /**
   * Test de la méthode getTelephone.
   */
  public void testGetTelephone() {
    assertTrue("0472656485".equals(infosEtudiantValide.getTelephone()));
  }


  /**
   * Test de la méthode incrementNoVersion.
   */
  @Test
  public void testIncrementNoVersion() {
    int noVersion = infosEtudiantNonValide.getNoVersion();
    infosEtudiantNonValide.incrementNoVersion();
    assertTrue(infosEtudiantNonValide.getNoVersion() == noVersion + 1);
  }

  /**
   * Test setId modifie bien l'id de l'utilisateur associé.
   */
  @Test
  public void testSetId() {
    infosEtudiantNonValide.setIdUtilisateur(100);
    assertTrue(infosEtudiantNonValide.getIdUtilisateur() == 100);
  }

  /**
   * Test setNoVersion modifie bien le numéro de version.
   */
  @Test
  public void testSetNoVersion() {
    infosEtudiantNonValide.setNoVersion(100);
    assertTrue(infosEtudiantNonValide.getNoVersion() == 100);
  }

  /**
   * Test set iban.
   */
  @Test
  public void testSetIban() {
    final String iban = infosEtudiantValide.getIban();
    infosEtudiantValide.setIban("");
    assertFalse(infosEtudiantValide.infosEtudiantValides());
    infosEtudiantValide.setIban("AZERTYUIOPAZERTYUIOPAZERTYUIOPAZERTYUIOP");
    assertFalse(infosEtudiantValide.infosEtudiantValides());
    String iban2 = iban.substring(0, iban.length() - 2) + "A";
    infosEtudiantValide.setIban(iban2);
    assertFalse(infosEtudiantValide.infosEtudiantValides());
    infosEtudiantValide.setIban(iban);
  }

  /**
   * Test set nom.
   */
  @Test
  public void testSetTitulaireComte() {
    String nom = infosEtudiantValide.getTitulaireCompte();
    infosEtudiantValide.setTitulaireCompte("M8");
    assertFalse(infosEtudiantValide.infosEtudiantValides());
    infosEtudiantValide.setTitulaireCompte(nom);
  }

  /**
   * Test set libelle nationalite.
   */
  @Test
  public void testSetNationaliteLibelle() {
    String nationaliteLibelle = infosEtudiantValide.getNationaliteLibelle();
    infosEtudiantValide.setNationaliteLibelle("Testakian");
    assertTrue("Testakian".equals(infosEtudiantValide.getNationaliteLibelle()));
    infosEtudiantValide.setNationaliteLibelle(nationaliteLibelle);
  }
}
