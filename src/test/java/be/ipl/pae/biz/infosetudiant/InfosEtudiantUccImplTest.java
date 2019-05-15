package be.ipl.pae.biz.infosetudiant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.biz.utilisateur.UtilisateurImpl;
import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.exceptions.BizException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class InfosEtudiantUccImplTest {

  private InfosEtudiantUcc infosEtudiantUcc;
  // private InfosEtudiant infosEtudiant1;
  // private InfosEtudiant infosEtudiant2;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si le setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    ConfigManager.load("test.properties");
    infosEtudiantUcc = new InfosEtudiantUccImpl();
    InjectionService.injectDependency(infosEtudiantUcc);

    InfosEtudiantDto infosEtudiantTest = new InfosEtudiantImpl();
    infosEtudiantTest.setStatut("Mlle");
    infosEtudiantTest.setNom("Mulhouse");
    infosEtudiantTest.setPrenom("Michel");
    infosEtudiantTest.setDateNaissance(
        Date.from(LocalDate.of(2000, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    infosEtudiantTest.setNationalite(2);
    infosEtudiantTest.setAdresse("Rue des champs, 12");
    infosEtudiantTest.setTelephone("0472656485");
    infosEtudiantTest.setEmail("jean@student.vinci.be");
    infosEtudiantTest.setSexe("M");
    infosEtudiantTest.setNbAnneesReussies(1);
    infosEtudiantTest.setIban("BE68539007547034");
    infosEtudiantTest.setTitulaireCompte("Marc Von Mei");
    infosEtudiantTest.setNomBanque("ING");
    infosEtudiantTest.setCodeBic("BBRUBEBB");
    infosEtudiantTest.setNoVersion(1);
    infosEtudiantTest.setIdUtilisateur(1);
    infosEtudiantUcc.introduireDonnees(infosEtudiantTest, 0);
    // infosEtudiant1 = infosEtudiantTest;
    // infosEtudiant2 = infosEtudiantTest;
  }

  /**
   * Test rechercher étudiants.
   */
  @Test
  public void testRechercherEtudiants01() {
    List<InfosEtudiantDto> etudiants = infosEtudiantUcc.rechercherEtudiants("", "");
    assertEquals(etudiants.size(), 1);
    etudiants = infosEtudiantUcc.rechercherEtudiants("Mulhouse", "");
    assertEquals(etudiants.size(), 1);
    etudiants = infosEtudiantUcc.rechercherEtudiants("Mulhouse", "Michel");
    assertEquals(etudiants.size(), 1);
    etudiants = infosEtudiantUcc.rechercherEtudiants("", "Michel");
    assertEquals(etudiants.size(), 1);
    etudiants = infosEtudiantUcc.rechercherEtudiants("Mul", "");
    assertEquals(etudiants.size(), 1);
    etudiants = infosEtudiantUcc.rechercherEtudiants("", "Mic");
    assertEquals(etudiants.size(), 1);
    etudiants = infosEtudiantUcc.rechercherEtudiants("Mul", "Mic");
    assertEquals(etudiants.size(), 1);
    etudiants = infosEtudiantUcc.rechercherEtudiants("Marc", "");
    assertEquals(etudiants.size(), 0);
    etudiants = infosEtudiantUcc.rechercherEtudiants("", "Marc");
    assertEquals(etudiants.size(), 0);
  }

  /**
   * Test charger infos étudiant par id avec id existant.
   */
  @Test
  public void testChargerInfosEtudiantParId01() {
    InfosEtudiantDto infosEtudiantDto = null;
    infosEtudiantDto = infosEtudiantUcc.chargerInfosEtudiantParId(1);
    assertNotEquals(infosEtudiantDto, null);
  }

  /**
   * Test charger infos étudiant par id avec id inexistant.
   */
  @Test
  public void testChargerInfosEtudiantParId02() {
    BizException thrown =
        assertThrows(BizException.class, () -> infosEtudiantUcc.chargerInfosEtudiantParId(2),
            "Expected infosEtudiantUcc.chargerInfosEtudiantParId(2) to throw, but it didn't");
    assertTrue(
        thrown.getMessage().contains("Impossible de récupérer les informations de cet étudiant"));
  }

  /**
   * Test charger infos étudiant pour prof, si l'utilisateur est bien un prof.
   */
  @Test
  public void testChargerInfosEtudiantPourProf01() {
    UtilisateurDto utilisateurDto = new UtilisateurImpl();
    utilisateurDto.setRole("PROF");
    InfosEtudiantDto etudiant = null;
    etudiant = infosEtudiantUcc.chargerInfosEtudiantPourProf(utilisateurDto, 1);
    assertNotEquals(etudiant, null);
  }

  /**
   * Test charger infos étudiant pour prof, si l'utilisateur est bien un prof mais que l'utilisateur
   * à chercher n'existe pas.
   */
  @Test
  public void testChargerInfosEtudiantPourProf02() {
    UtilisateurDto utilisateurDto = new UtilisateurImpl();
    utilisateurDto.setRole("PROF");
    BizException thrown = assertThrows(BizException.class,
        () -> infosEtudiantUcc.chargerInfosEtudiantPourProf(utilisateurDto, 2),
        "Expected infosEtudiantUcc.chargerInfosEtudiantPourProf(utilisateurDto, 2) "
            + "to throw, but it didn't");
    assertTrue(
        thrown.getMessage().contains("Cet utilisateur n'existe pas ou n'est pas un étudiant"));
  }

  /**
   * Test charger infos étudiant pour prof, si l'utilisateur est un étudiant.
   */
  @Test
  public void testChargerInfosEtudiantPourProf01bis() {
    UtilisateurDto utilisateurDto = new UtilisateurImpl();
    utilisateurDto.setRole("ETUD");
    BizException thrown = assertThrows(BizException.class,
        () -> infosEtudiantUcc.chargerInfosEtudiantPourProf(utilisateurDto, 1),
        "Expected infosEtudiantUcc.chargerInfosEtudiantPourProf(utilisateurDto, 1);"
            + " to throw, but it didn't");
    assertTrue(
        thrown.getMessage().contains("Vous n'avez pas les droits pour effectuer cette opération"));
  }

  /**
   * Test introduire données avec des infos mal remplies.
   */
  @Test
  public void testIntroduireDonnees01() {
    InfosEtudiantDto infosEtudiantDto = infosEtudiantUcc.chargerInfosEtudiantParId(1);
    infosEtudiantDto.setTelephone("XXX");
    BizException thrown = assertThrows(BizException.class,
        () -> infosEtudiantUcc.introduireDonnees(infosEtudiantDto, 0),
        "Expected infosEtudiantUcc.introduireDonnees(infosEtudiantDto, 0) to throw, but it didn't");
    assertTrue(thrown.getMessage().contains("Certains champs ne sont pas valides."));
    infosEtudiantDto.setTelephone("0472656485");
  }

  /**
   * Test introduire données concernant l'optimistic lock s'il y a de la concurrence.
   */
  @Test
  public void testIntroduireDonnees02() {
    InfosEtudiantDto infosEtudiantDto = infosEtudiantUcc.chargerInfosEtudiantParId(1);
    infosEtudiantDto.setNoVersion(2);
    BizException thrown = assertThrows(BizException.class,
        () -> infosEtudiantUcc.introduireDonnees(infosEtudiantDto, 1),
        "Expected infosEtudiantUcc.introduireDonnees(infosEtudiantDto, 1) to throw, but it didn't");
    assertTrue(thrown.getMessage().contains(
        "Impossible d'actualiser les données. Elles ont peut-être déjà été mises à jour."));
    infosEtudiantDto.setNoVersion(1);
  }

}
