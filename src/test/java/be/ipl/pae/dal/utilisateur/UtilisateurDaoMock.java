package be.ipl.pae.dal.utilisateur;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.biz.utilisateur.UtilisateurImpl;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.utilisateur.UtilisateurDao;

import org.mindrot.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilisateurDaoMock implements UtilisateurDao {

  @Inject
  BizFactory bizFactory;

  private Set<UtilisateurDto> utilisateurs = new HashSet<>();
  private static int ID = 0;

  @Override
  public UtilisateurDto trouverUtilisateurParPseudo(String pseudo) {
    if (pseudo == null) {
      return null;
    }
    return new UtilisateurImpl(pseudo, BCrypt.hashpw("vraiMotDePasse", BCrypt.gensalt()));
  }

  @Override
  public boolean chercherSiPseudoPresent(String pseudo) {
    UtilisateurDto utilisateur =
        utilisateurs.stream().filter(x -> x.getPseudo().equals(pseudo)).findAny().orElse(null);
    if (utilisateur == null) {
      return false;
    }
    return true;
  }

  @Override
  public boolean chercherSiEmailPresent(String email) {
    UtilisateurDto utilisateur =
        utilisateurs.stream().filter(x -> x.getEmail().equals(email)).findAny().orElse(null);
    if (utilisateur == null) {
      return false;
    }
    return true;
  }

  @Override
  public void ajouterUtilisateur(UtilisateurDto utilisateurDto, String role) {
    UtilisateurDto utilisateur = bizFactory.getUtilisateurDto();
    utilisateur.setPseudo(utilisateurDto.getPseudo());
    utilisateur.setNom(utilisateurDto.getNom());
    utilisateur.setPrenom(utilisateurDto.getPrenom());
    utilisateur.setEmail(utilisateurDto.getEmail());
    utilisateur.setRole(role);
    utilisateur.setMotDePasse(BCrypt.hashpw(utilisateurDto.getMotDePasse(), BCrypt.gensalt()));
    utilisateur.setId(ID++);
    utilisateur.setNoVersion(1);
    utilisateurs.add(utilisateur);
  }

  @Override
  public List<UtilisateurDto> listerUtilisateurs() {
    return new ArrayList<UtilisateurDto>(utilisateurs);
  }

  @Override
  public List<UtilisateurDto> listerEtudiants() {
    List<UtilisateurDto> utilisateursReturn = new ArrayList<UtilisateurDto>();
    for (UtilisateurDto user : utilisateurs) {
      if (user.getRole().equals("ETUD")) {
        utilisateursReturn.add(user);
      }
    }
    return utilisateursReturn;
  }

  @Override
  public void indiquerProfesseur(UtilisateurDto utilisateurDto) {
    utilisateurDto.setRole("PROF");

  }

  @Override
  public void indiquerEtudiant(UtilisateurDto utilisateurDto) {
    utilisateurDto.setRole("ETUD");

  }

  @Override
  public UtilisateurDto trouverUtilisateurParId(int id) {
    for (UtilisateurDto user : utilisateurs) {
      if (user.getId() == id) {
        return user;
      }
    }
    return null;
  }

}
