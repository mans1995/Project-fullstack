package be.ipl.pae.biz.partenaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.biz.mobilite.Mobilite;
import be.ipl.pae.biz.mobilite.MobiliteImpl;
import be.ipl.pae.biz.mobilite.MobiliteUcc;
import be.ipl.pae.biz.mobilite.MobiliteUccImpl;
import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.InjectionService;
import be.ipl.pae.dal.partenaire.PartenaireDaoMock;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PartenaireUccImplTest {

  private PartenaireUcc partenaireUcc;
  private MobiliteUcc mobiliteUcc;
  private Partenaire partenaire1;
  private Partenaire partenaire2;
  private Mobilite mobiliteTest;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    ConfigManager.load("test.properties");
    PartenaireImpl partenaireTest = remplirPartenaire("Nom Test complet", "bxl", 2);
    partenaire1 = partenaireTest;
    partenaire2 = partenaireTest;
    mobiliteTest = remplirMobilite(2);
    mobiliteUcc = new MobiliteUccImpl();
    InjectionService.injectDependency(mobiliteUcc);
    partenaireUcc = new PartenaireUccImpl();
    InjectionService.injectDependency(partenaireUcc);
    List<Mobilite> mobilites = new ArrayList<>();
    mobilites.add(mobiliteTest);
    mobiliteUcc.ajouterMobilite(mobilites);
  }

  /**
   * Set up après chaque test.
   */
  @AfterEach
  public void apresTout() {
    mobiliteUcc.supprimerMobilite(mobiliteTest);

  }

  /**
   * Methode d'introspection pour gérer les Fatalexecption.
   * 
   * @param throwException true ou false a set.
   */
  private void throwFatalException(boolean throwException) {
    try {
      Field partenaireDaoUcc = partenaireUcc.getClass().getDeclaredField("partenaireDao");
      partenaireDaoUcc.setAccessible(true);
      PartenaireDaoMock partenaire = (PartenaireDaoMock) partenaireDaoUcc.get(partenaireUcc);
      Field field = partenaire.getClass().getDeclaredField("throwException");
      field.setAccessible(true);
      field.setBoolean(partenaire, throwException);
    } catch (NoSuchFieldException exception) {
      exception.printStackTrace();
    } catch (IllegalArgumentException exception) {
      exception.printStackTrace();
    } catch (IllegalAccessException exception) {
      exception.printStackTrace();
    }

  }

  private PartenaireImpl remplirPartenaire(String nomComplet, String nomVille, int idPays) {
    PartenaireImpl partenaireTest = new PartenaireImpl();
    partenaireTest.setTypeCode("SMS");
    partenaireTest.setNomLegal("Nom legal");
    partenaireTest.setNomDaffaires("nom affaire");
    partenaireTest.setNomComplet(nomComplet);
    partenaireTest.setDepartement("departement");
    partenaireTest.setTypeOrganisation("org");
    partenaireTest.setNombreEmployes(10);
    partenaireTest.setAdresse("adresse");
    partenaireTest.setIdPays(idPays);
    partenaireTest.setRegion("region");
    partenaireTest.setCodePostal("1020");
    partenaireTest.setVille(nomVille);
    partenaireTest.setEmail("jean@live.be");
    partenaireTest.setSiteWeb("www.test.com");
    partenaireTest.setTelephone("0472656485");
    partenaireTest.setQuadrimestre("Q1");
    partenaireTest.setId(1);
    partenaireTest.setNoVersion(1);
    return partenaireTest;
  }

  private MobiliteImpl remplirMobilite(int idPays) {
    MobiliteImpl mobilite = new MobiliteImpl();
    mobilite.setIdPays(idPays);
    mobilite.setIdUtilisateur(6);
    mobilite.setPreference(2);
    mobilite.setTypeCode("SMS");
    mobilite.setEtat("demandee");
    mobilite.setEtatAvantAnnulation("");
    mobilite.setRaisonAnnulation("");
    mobilite.setAnneeAcademique("2017-2018");
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
    return mobilite;
  }

  /**
   * Test ajouter partenaire Fatal Exception.
   */
  @Test
  void testAjouterFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class,
        () -> partenaireUcc.ajouterPartenaire(partenaire1, mobiliteTest),
        "Expected mobiliteUcc.ajouterPartenaire() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test où l'entreprise est incorrect.
   */
  @Test
  void testAjouterPartenaire01() {

    partenaire1.setNomLegal(null);
    BizException thrown = assertThrows(BizException.class,
        () -> partenaireUcc.ajouterPartenaire(partenaire1, mobiliteTest),
        "Expected ajouterPartenaire(partenaire1) to throw, but it didn't");

    assertTrue(thrown.getMessage().contains("Tous les champs du partenaire ne sont pas valides."));
  }



  /**
   * Test où l'entreprise possède un nomComplet qui existe déjà parmi les partenaires enregistrés.
   */
  @Test
  void testAjouterPartenaire02() {
    mobiliteTest = remplirMobilite(2);
    partenaireUcc.ajouterPartenaire(partenaire1, mobiliteTest);
    BizException thrown = assertThrows(BizException.class,
        () -> partenaireUcc.ajouterPartenaire(partenaire2, mobiliteTest),
        "Expected ajouterPartenaire() to throw, but it didn't");
    assertTrue(thrown.getMessage().contains("Le partenaire existe deja."));
  }

  /**
   * Test lister partenaire Fatal.
   */
  @Test
  void testListerPartenaireFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class, () -> partenaireUcc.listerPartenaires(),
        "Expected mobiliteUcc.listerPartenaires() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test listerPartenaires.
   */
  @Test
  void testListerPartenaires() {
    mobiliteTest = remplirMobilite(32);
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenaires();
    partenaireUcc.ajouterPartenaire(remplirPartenaire("NomComplet101_1", "Test101_1", 32),
        mobiliteTest);
    mesPartenaires = partenaireUcc.listerPartenaires();
    assertEquals(mesPartenaires.size(), 1);
  }

  /**
   * Test lister partenaire par pays Fatal.
   */
  @Test
  void testListerPartenaireParPaysFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class, () -> partenaireUcc.listerPartenairesParPays(1),
        "Expected mobiliteUcc.listerPartenaires() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test listerPartenairesParPays avec un pays existant.
   */
  @Test
  void testListerPartenairesParPays01() {
    mobiliteTest = remplirMobilite(102);
    partenaireUcc.ajouterPartenaire(remplirPartenaire("NomComplet101_2", "Test101_2", 102),
        mobiliteTest);

    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParPays(102);
    assertEquals(mesPartenaires.size(), 1);
  }

  /**
   * Test listerPartenairesParPays avec un pays non existant.
   */
  @Test
  void testListerPartenairesParPays02() {
    mobiliteTest = remplirMobilite(103);
    partenaireUcc.ajouterPartenaire(remplirPartenaire("NomComplet101_3", "Test101_3", 103),
        mobiliteTest);
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParPays(104);
    assertEquals(mesPartenaires.size(), 0);
  }

  /**
   * Test listerPartenairesParPays avec un pays sans partenaire.
   */
  @Test
  void testListerPartenairesParPays03() {
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParPays(104);
    assertEquals(mesPartenaires.size(), 0);
  }

  /**
   * Test lister partenaire par ville Fatal.
   */
  @Test
  void testListerPartenaireParVilleFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class, () -> partenaireUcc.listerPartenairesParVille("Ville1"),
        "Expected mobiliteUcc.listerPartenairesParVille() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test listerPartenairesParVilee avec une ville existante.
   */
  @Test
  void testListerPartenairesParVille01() {
    mobiliteTest = remplirMobilite(106);
    partenaireUcc.ajouterPartenaire(remplirPartenaire("NomComplet106_1", "Ville1", 106),
        mobiliteTest);
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParVille("Ville1");
    assertEquals(mesPartenaires.size(), 1);
  }


  /**
   * Test listerPartenairesParVilee avec une ville non existante.
   */
  @Test
  void testListerPartenairesParVille02() {
    mobiliteTest = remplirMobilite(106);
    partenaireUcc.ajouterPartenaire(remplirPartenaire("NomComplet106_2", "Ville2", 106),
        mobiliteTest);
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParVille("Ville3");
    assertEquals(mesPartenaires.size(), 0);
  }

  /**
   * Test listerPartenairesParPays avec une ville sans partenaire.
   */
  @Test
  void testListerPartenairesParVille03() {
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParVille("Ville4");
    assertEquals(mesPartenaires.size(), 0);
  }

  /**
   * Test lister partenaire par nom legal Fatal.
   */
  @Test
  void testListerPartenaireParNomLegalFatal() {
    throwFatalException(true);
    assertThrows(FatalException.class,
        () -> partenaireUcc.listerPartenairesParNomLegal("Nom Legal"),
        "Expected mobiliteUcc.listerPartenairesParNomLegal() to throw, but it didn't");
    throwFatalException(false);
  }

  /**
   * Test lister partenaire par nom legal avec un partenaire existant.
   */
  @Test
  void testListerPartenaireParNomLegal01() {
    mobiliteTest = remplirMobilite(110);
    Partenaire part = remplirPartenaire("NomComplet110", "Ville10", 110);
    part.setNomLegal("Existant");
    partenaireUcc.ajouterPartenaire(part, mobiliteTest);
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParNomLegal("Existant");
    assertEquals(mesPartenaires.size(), 1);
  }

  /**
   * Test lister partenaire par nom legal avec partenaire non existant.
   */
  @Test
  void testListerPartenaireParNomLegal02() {
    List<PartenaireDto> mesPartenaires = partenaireUcc.listerPartenairesParNomLegal("Non existant");
    assertEquals(mesPartenaires.size(), 0);
  }



}
