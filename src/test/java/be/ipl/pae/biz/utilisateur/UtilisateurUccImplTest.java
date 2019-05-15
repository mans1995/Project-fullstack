package be.ipl.pae.biz.utilisateur;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exceptions.BizException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UtilisateurUccImplTest {

  private UtilisateurDto utilisateurDtoValide;
  private UtilisateurDto utilisateurDtoNonValide;
  private Utilisateur utilisateurValide;
  private Utilisateur utilisateurValide2;
  private UtilisateurUcc utilisateurUcc;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    ConfigManager.load("test.properties");
    utilisateurDtoValide = new UtilisateurImpl("test", "vraiMotDePasse");
    utilisateurDtoNonValide = new UtilisateurImpl(null, "fauxMotDePasse");
    utilisateurValide = new UtilisateurImpl();
    utilisateurValide.setPseudo("Florianleo");
    utilisateurValide.setMotDePasse("vraiMotDePasse");
    utilisateurValide.setEmail("florian.timmermans@student.vinci.be");
    utilisateurValide.setNom("Test");
    utilisateurValide.setPrenom("Test");
    utilisateurValide.setId(10);
    utilisateurValide.setNoVersion(10);
    utilisateurValide2 = new UtilisateurImpl();
    utilisateurValide2.setPseudo("Florianleo");
    utilisateurValide2.setMotDePasse("vraMotDePasse");
    utilisateurValide2.setEmail("florian.timmermans@student.vinci.be");
    utilisateurValide2.setNom("Teste");
    utilisateurValide2.setPrenom("Teste");
    utilisateurValide2.setId(11);
    utilisateurValide2.setNoVersion(10);
    utilisateurUcc = new UtilisateurUccImpl();
    InjectionService.injectDependency(utilisateurUcc);
  }


  /**
   * Méthode qui test seConnecter avec un utilisateur valide.
   */
  @Test
  public void testSeConnecter01() {
    assertNotNull(utilisateurUcc.seConnecter(utilisateurDtoValide.getPseudo(),
        utilisateurDtoValide.getMotDePasse()));
  }

  /**
   * Méthode qui test seConnecter avec un pseudo non valide.
   */
  @Test
  public void testSeConnecter02() {
    assertNull(utilisateurUcc.seConnecter(utilisateurDtoNonValide.getPseudo(),
        utilisateurDtoValide.getMotDePasse()));
  }

  /**
   * Méthode qui test seConnecter avec un faux mot de passe.
   */
  @Test
  public void testSeConnecter03() {
    assertNull(utilisateurUcc.seConnecter(utilisateurDtoValide.getPseudo(),
        utilisateurDtoNonValide.getMotDePasse()));
  }

  /**
   * Méthode qui test inscrireUtilisateur avec un utilisateur non valide.
   */
  @Test
  public void testInscrireUtilisateur01() {
    utilisateurValide.setPseudo(null);
    BizException thrown = assertThrows(BizException.class,
        () -> utilisateurUcc.inscrireUtilisateur(utilisateurValide),
        "Expected inscrireUtilisateur(utilisateurValide) to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Certains champs de l'utilisateur ne sont pas valides."));
  }

  /**
   * Méthode qui test inscrireUtilisateur avec un pseudo deja existant.
   */
  @Test
  public void testInscrireUtilisateur02() {
    utilisateurUcc.inscrireUtilisateur(utilisateurValide);
    BizException thrown = assertThrows(BizException.class,
        () -> utilisateurUcc.inscrireUtilisateur(utilisateurValide2),
        "Expected inscrireUtilisateur(utilisateurValide2) to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Ce pseudo existe déjà."));
  }

  /**
   * Méthode qui test inscrireUtilisateur avec un mail deja existant.
   */
  @Test
  public void testInscrireUtilisateur03() {
    utilisateurValide2.setPseudo("AutrePseudo");;
    BizException thrown = assertThrows(BizException.class,
        () -> utilisateurUcc.inscrireUtilisateur(utilisateurValide2),
        "Expected inscrireUtilisateur(utilisateurValide2) to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Cette adresse email existe déjà."));
  }

  /**
   * Test trouver utilisateur par pseudo.
   */
  @Test
  public void testTrouverUtilisateurParPseudo() {
    assertTrue(utilisateurValide.getPseudo()
        .equals(utilisateurUcc.trouverUtilisateurParPseudo("Florianleo").getPseudo()));
  }

  /**
   * Test indiquer prof en etant prof.
   */
  @Test
  public void testIndiquerProfesseur01() {
    utilisateurValide.setRole("PROF");
    BizException thrown = assertThrows(BizException.class,
        () -> utilisateurUcc.indiquerProfesseur(utilisateurValide, utilisateurValide2),
        "Expected indiquerProfesseur(utilisateurValide, utilisateurValide2)"
            + " to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette opération."));
  }

  /**
   * Test indiquer prof en etant eleve.
   */
  @Test
  public void testIndiquerProfesseur02() {
    utilisateurValide.setRole("ETUD");
    BizException thrown = assertThrows(BizException.class,
        () -> utilisateurUcc.indiquerProfesseur(utilisateurValide, utilisateurValide2),
        "Expected indiquerProfesseur(utilisateurValide, utilisateurValide2)"
            + " to throw, but it didn't");

    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette opération."));
  }

  /**
   * Test indiquer prof en etant RESP.
   */
  @Test
  public void testIndiquerProfesseur03() {
    utilisateurValide.setRole("RESP");
    utilisateurValide2.setId(33);
    BizException thrown = assertThrows(BizException.class,
        () -> utilisateurUcc.indiquerProfesseur(utilisateurValide, utilisateurValide2),
        "Expected inscrireUtilisateur(utilisateurValide2) to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Aucun utilisateur ne correspond à cet identifiant"));
  }

}
