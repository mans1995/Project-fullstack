package be.ipl.pae.biz.utilisateur;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.bcrypt.BCrypt;

public class UtilisateurImplTest {

  private UtilisateurImpl utilisateur;
  private UtilisateurImpl utilisateurValide;
  private String testLongueur;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    String sel = BCrypt.gensalt();
    String mdpHashe = BCrypt.hashpw("vraiMotDePasse", sel);
    utilisateur = new UtilisateurImpl("utilisateurTest", mdpHashe);
    utilisateurValide = new UtilisateurImpl();
    utilisateurValide.setId(1);
    utilisateurValide.setPseudo("Florianleo");
    utilisateurValide.setMotDePasse(mdpHashe);
    utilisateurValide.setEmail("florian.timmermans@student.vinci.be");
    utilisateurValide.setNom("Test");
    utilisateurValide.setPrenom("Test");
    utilisateurValide.setNoVersion(1);
    testLongueur =
        "teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            + "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeest";
  }


  /**
   * Méthode qui vérifie que la méthode verifierMotDePasse renvoi bien vrai.
   */
  @Test
  public void testVerifierMotDePasse01() {
    assertTrue(utilisateur.verifierMotDePasse("vraiMotDePasse"));
  }


  /**
   * Méthode qui vérifie que la méthode verifierMotDePasse renvoi bien faux.
   */
  @Test
  public void testVerifierMotDePasse02() {
    assertFalse(utilisateur.verifierMotDePasse("fauxMotDePasse"));
  }

  /**
   * Test avec un utilisateur valide.
   */
  @Test
  public void testUtilisateurEstValide00() {
    assertTrue(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un pseudo non valide.
   */
  @Test
  public void testUtilisateurEstValide01() {
    utilisateurValide.setPseudo("");
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setPseudo(null);
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setPseudo("Non@Valide");
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un email non valide.
   */
  @Test
  public void testUtilisateurEstValide02() {
    utilisateurValide.setEmail("");
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setEmail(null);
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setEmail("Non@Valide");
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un mot de passe non valide.
   */
  @Test
  public void testUtilisateurEstValide03() {
    utilisateurValide.setMotDePasse("");
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setMotDePasse(null);
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setMotDePasse("Non@Valide");
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un nom non valide.
   */
  @Test
  public void testUtilisateurEstValide04() {
    utilisateurValide.setNom("");
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setNom(null);
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setNom("Non@Valide");
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un prenom non valide.
   */
  @Test
  public void testUtilisateurEstValide05() {
    utilisateurValide.setPrenom("");
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setPrenom(null);
    assertFalse(utilisateurValide.utilisateurEstValide());
    utilisateurValide.setPrenom("Non@Valide");
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un pseudo trop long.
   */
  @Test
  public void testUtilisateurValide06() {
    utilisateurValide.setPseudo(testLongueur);
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un email trop long.
   */
  @Test
  public void testUtilisateurValide07() {
    utilisateurValide.setEmail(testLongueur);
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un mot de passe trop long.
   */
  @Test
  public void testUtilisateurValide08() {
    utilisateurValide.setMotDePasse(testLongueur);
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un nom trop long.
   */
  @Test
  public void testUtilisateurValide09() {
    utilisateurValide.setNom(testLongueur);
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un prenom trop long.
   */
  @Test
  public void testUtilisateurValide10() {
    utilisateurValide.setPrenom(testLongueur);
    assertFalse(utilisateurValide.utilisateurEstValide());
  }

  /**
   * Test avec un id inférieur à 1.
   */
  /*
   * A supprimer?
   * 
   * @Test public void testUtilisateurValide11() { utilisateurValide.setId(-1);
   * assertFalse(utilisateurValide.utilisateurEstValide()); }
   */

  /**
   * Test avec un numéro de version inférieur à 1.
   */
  /*
   * A supprimer?
   * 
   * @Test public void testUtilisateurValide12() { utilisateurValide.setNoVersion(-1);
   * assertFalse(utilisateurValide.utilisateurEstValide()); }
   */

  /**
   * Test de la méthode incrementNoVersion.
   */
  @Test
  public void testIncrementNoVersion() {
    int noVersion = utilisateurValide.getNoVersion();
    utilisateurValide.incrementNoVersion();
    assertTrue(utilisateurValide.getNoVersion() == noVersion + 1);
  }

  /**
   * Test setId modifie bien l'id.
   */
  @Test
  public void testSetId() {
    utilisateur.setId(100);
    assertTrue(utilisateur.getId() == 100);
  }

  /**
   * Test setNoVersion modifie bien le numéro de version.
   */
  @Test
  public void testSetNoVersion() {
    utilisateur.setNoVersion(100);
    assertTrue(utilisateur.getNoVersion() == 100);
  }
}
