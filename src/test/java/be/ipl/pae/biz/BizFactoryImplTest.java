package be.ipl.pae.biz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import be.ipl.pae.biz.infosetudiant.InfosEtudiantDto;
import be.ipl.pae.biz.infosetudiant.InfosEtudiantImpl;
import be.ipl.pae.biz.mobilite.MobiliteDto;
import be.ipl.pae.biz.mobilite.MobiliteImpl;
import be.ipl.pae.biz.partenaire.PartenaireDto;
import be.ipl.pae.biz.partenaire.PartenaireImpl;
import be.ipl.pae.biz.pays.PaysDto;
import be.ipl.pae.biz.pays.PaysImpl;
import be.ipl.pae.biz.programme.ProgrammeDto;
import be.ipl.pae.biz.programme.ProgrammeImpl;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.biz.utilisateur.UtilisateurImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class BizFactoryImplTest {

  private BizFactory factory;
  private UtilisateurDto user;
  private PartenaireDto partenaire;
  private MobiliteDto mobilite;
  private PaysDto pays;
  private ProgrammeDto programme;
  private InfosEtudiantDto infosEtudiant;

  /**
   * Set up pour les fonctions tests.
   * 
   * @throws Exception lancée si erreur setUp.
   */
  @BeforeEach
  public void setUp() throws Exception {
    factory = new BizFactoryImpl();
    user = new UtilisateurImpl();
    partenaire = new PartenaireImpl();
    mobilite = new MobiliteImpl();
    pays = new PaysImpl();
    programme = new ProgrammeImpl();
    infosEtudiant = new InfosEtudiantImpl();
  }

  /**
   * Test du getUtilisateurDTO, vérification du bon renvoi.
   * 
   * @throws SecurityException quand cette exception exceptionnelle se déroule.
   * @throws NoSuchFieldException quand cette exception exceptionnelle se déroule.
   * @throws IllegalAccessException quand cette exception exceptionnelle se déroule.
   * @throws IllegalArgumentException quand cette exception exceptionnelle se déroule.
   */
  @Test
  public void testGetUtilisateurDtoAvecParametres() throws NoSuchFieldException, SecurityException,
      IllegalArgumentException, IllegalAccessException {
    Field fieldMdp;
    Field fieldPseudo;
    fieldPseudo = user.getClass().getDeclaredField("pseudo");
    fieldPseudo.setAccessible(true);
    fieldMdp = user.getClass().getDeclaredField("motDePasse");
    fieldMdp.setAccessible(true);
    String mdp = (String) fieldMdp.get(user);
    String pseudo = (String) fieldPseudo.get(user);
    assertEquals(user, factory.getUtilisateurDto(pseudo, mdp));
  }

  @Test
  public void testGetUtilisateurDto() {
    assertEquals(user, factory.getUtilisateurDto());
  }

  @Test
  public void testGetPartenaireDto() {
    assertEquals(partenaire, factory.getPartenaireDto());
  }

  @Test
  public void testGetMobiliteDto() {
    assertEquals(mobilite, factory.getMobiliteDto());
  }

  @Test
  public void testGetProgrammeDto() {
    assertEquals(programme, factory.getProgrammeDto());
  }

  @Test
  public void testGetPaysDto() {
    assertEquals(pays, factory.getPaysDto());
  }

  @Test
  public void testGetInfosEtudiantDto() {
    assertEquals(infosEtudiant, factory.getInfosEtudiantDto());
  }

}
