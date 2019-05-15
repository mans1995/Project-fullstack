package be.ipl.pae.dal.utilisateur;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalBackendServices;
import be.ipl.pae.exceptions.FatalException;

import org.mindrot.bcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDaoImpl implements UtilisateurDao {

  @Inject
  private DalBackendServices dalBackendServices;
  @Inject
  private BizFactory bizFactory;

  @Override
  public UtilisateurDto trouverUtilisateurParPseudo(String pseudo) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT util.mot_de_passe, util.role_util, util.nom,"
            + " util.prenom, util.email, util.no_version, util.id_utilisateur FROM"
            + " paeMobEras.utilisateurs util WHERE util.pseudo = ?;")) {
      ps.setString(1, pseudo);
      try (ResultSet rs = ps.executeQuery()) {
        // Vérifie qu'il y a un utilisateur (le premier rs.next renverra vrai)
        // puis on traite tout le bloc d'utilisateurs (avec do while) ici
        // y'aura qu'un utilisateur max, mais je laisse ça comme ça pour le
        // réutiliser plus tard pour d'autres classes.
        if (rs.next() != false) {
          UtilisateurDto utilisateurDto;
          do {
            utilisateurDto = bizFactory.getUtilisateurDto(pseudo, rs.getString(1));
            utilisateurDto.setRole(rs.getString(2));
            utilisateurDto.setNom(rs.getString(3));
            utilisateurDto.setPrenom(rs.getString(4));
            utilisateurDto.setEmail(rs.getString(5));
            utilisateurDto.setNoVersion(rs.getInt(6));
            utilisateurDto.setId(rs.getInt(7));
          } while (rs.next());
          return utilisateurDto;
        } else { // aucun mdp (donc aucun utilisateur) pas dans le resultSet
          return null;
        }
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

  @Override
  public UtilisateurDto trouverUtilisateurParId(int id) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT util.mot_de_passe, util.role_util, util.nom,"
            + " util.prenom, util.email, util.no_version, util.id_utilisateur, util.pseudo FROM"
            + " paeMobEras.utilisateurs util WHERE util.id_utilisateur = ?;")) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        // Vérifie qu'il y a un utilisateur (le premier rs.next renverra vrai)
        // puis on traite tout le bloc d'utilisateurs (avec do while) ici
        // y'aura qu'un utilisateur max, mais je laisse ça comme ça pour le
        // réutiliser plus tard pour d'autres classes.
        if (rs.next() != false) {
          UtilisateurDto utilisateurDto;
          do {
            utilisateurDto = bizFactory.getUtilisateurDto();
            utilisateurDto.setRole(rs.getString(2));
            utilisateurDto.setNom(rs.getString(3));
            utilisateurDto.setPrenom(rs.getString(4));
            utilisateurDto.setEmail(rs.getString(5));
            utilisateurDto.setNoVersion(rs.getInt(6));
            utilisateurDto.setId(rs.getInt(7));
            utilisateurDto.setPseudo(rs.getString(8));
          } while (rs.next());
          return utilisateurDto;
        } else { // aucun mdp (donc aucun utilisateur) pas dans le resultSet
          return null;
        }
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

  @Override
  public boolean chercherSiPseudoPresent(String pseudo) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT * FROM paeMobEras.utilisateurs util WHERE util.pseudo = ? FOR UPDATE;")) {
      ps.setString(1, pseudo);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

  @Override
  public boolean chercherSiEmailPresent(String email) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT * FROM paeMobEras.utilisateurs util WHERE util.email = ? FOR UPDATE;")) {
      ps.setString(1, email);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

  @Override
  public void ajouterUtilisateur(UtilisateurDto utilisateurDto, String role) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "INSERT INTO" + " paeMobEras.utilisateurs(role_util, pseudo, mot_de_passe,"
            + " nom, prenom, email, no_version) VALUES(?, ?, ?, ?, ?, ?, 1);")) {
      // rôle par défaut = étudiant -> si ya personne d'inscrit ce rôle doit être responsable
      // !!!
      ps.setString(1, role);
      String pseudo = utilisateurDto.getPseudo();
      ps.setString(2, pseudo);
      String motDePasse = utilisateurDto.getMotDePasse();
      // On crypte le mot de passe car c'est celui-là qui sera dans la db.
      motDePasse = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
      ps.setString(3, motDePasse);
      String nom = utilisateurDto.getNom();
      ps.setString(4, nom);
      String prenom = utilisateurDto.getPrenom();
      ps.setString(5, prenom);
      String email = utilisateurDto.getEmail();
      ps.setString(6, email);
      ps.executeUpdate();
    } catch (SQLException exception) {
      // Cette erreur peut être générée si, par exemple, pour pseudo on a "value too long for
      // type
      // character varying(100)".
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<UtilisateurDto> listerUtilisateurs() {
    List<UtilisateurDto> utilisateurs = new ArrayList<>();
    try (PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("SELECT u.id_utilisateur, u.role_util,"
            + " u.pseudo, u.mot_de_passe, u.nom, u.prenom, u.email, u.no_version"
            + " FROM paeMobEras.utilisateurs u ORDER BY u.id_utilisateur")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          UtilisateurDto utilisateur = remplirUtilisateur(rs);
          utilisateurs.add(utilisateur);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return utilisateurs;
  }

  @Override
  public List<UtilisateurDto> listerEtudiants() {
    List<UtilisateurDto> utilisateurs = new ArrayList<>();
    try (PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("SELECT u.id_utilisateur, u.role_util,"
            + " u.pseudo, u.mot_de_passe, u.nom, u.prenom, u.email, u.no_version"
            + " FROM paeMobEras.utilisateurs u"
            + " WHERE u.role_util = 'ETUD' ORDER BY u.id_utilisateur")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          UtilisateurDto utilisateur = remplirUtilisateur(rs);
          utilisateurs.add(utilisateur);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return utilisateurs;
  }

  private UtilisateurDto remplirUtilisateur(ResultSet rs) throws SQLException {
    UtilisateurDto utilisateur = bizFactory.getUtilisateurDto();
    utilisateur.setId(rs.getInt(1));
    utilisateur.setRole(rs.getString(2));
    utilisateur.setPseudo(rs.getString(3));
    utilisateur.setMotDePasse(rs.getString(4));
    utilisateur.setNom(rs.getString(5));
    utilisateur.setPrenom(rs.getString(6));
    utilisateur.setEmail(rs.getString(7));
    utilisateur.setNoVersion(rs.getInt(8));
    return utilisateur;
  }

  @Override
  public void indiquerProfesseur(UtilisateurDto utilisateurDto) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "UPDATE paeMobEras.utilisateurs SET role_util = ? WHERE id_utilisateur = ?;")) {
      ps.setString(1, "PROF");
      ps.setInt(2, utilisateurDto.getId());
      ps.executeUpdate();
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

  @Override
  public void indiquerEtudiant(UtilisateurDto utilisateurDto) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "UPDATE paeMobEras.utilisateurs SET role_util = ? WHERE id_utilisateur = ?;")) {
      ps.setString(1, "ETUD");
      ps.setInt(2, utilisateurDto.getId());
      ps.executeUpdate();
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

}
