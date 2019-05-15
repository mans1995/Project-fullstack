package be.ipl.pae.dal.infosetudiant;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.infosetudiant.InfosEtudiantDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalBackendServices;
import be.ipl.pae.exceptions.FatalException;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InfosEtudiantDaoImpl implements InfosEtudiantDao {

  @Inject
  private DalBackendServices dalBackendServices;
  @Inject
  private BizFactory bizFactory;

  @Override
  public InfosEtudiantDto chercherInfosEtudiantParId(int idEtudiant) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT info.id_utilisateur, info.statut, u.nom,"
            + " u.prenom, info.date_naissance, info.nationalite, p.nom_pays, info.adresse,"
            + " info.telephone, u.email, info.sexe,"
            + " info.nb_annees_reussies, info.iban, info.titulaire_compte,"
            + " info.nom_banque, info.code_bic, info.no_version"
            + " FROM paeMobEras.infos_etudiants info INNER JOIN paeMobEras.utilisateurs u"
            + " ON info.id_utilisateur = u.id_utilisateur INNER JOIN paeMobEras.pays p"
            + " ON info.nationalite = p.id_pays WHERE info.id_utilisateur = ?;")) {
      ps.setInt(1, idEtudiant);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          InfosEtudiantDto infosEtudiantDto = remplirEtudiantAvecResultSet(rs);
          return infosEtudiantDto;
        } else {
          return null;
        }
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

  @Override
  public InfosEtudiantDto chercherInfosEtudiantPourProf(int idEtudiant) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT info.id_utilisateur, info.statut, u.nom, u.prenom,"
            + " info.date_naissance, info.nationalite, p.nom_pays, "
            + " info.adresse, info.telephone,"
            + " u.email, info.sexe, info.nb_annees_reussies, info.iban,"
            + " info.titulaire_compte, info.nom_banque, info.code_bic, info.no_version"
            + " FROM paeMobEras.infos_etudiants info INNER JOIN paeMobEras.utilisateurs u"
            + " ON info.id_utilisateur = u.id_utilisateur INNER JOIN paeMobEras.pays p"
            + " ON info.nationalite = p.id_pays WHERE info.id_utilisateur = ?;")) {
      ps.setInt(1, idEtudiant);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          InfosEtudiantDto infosEtudiantDto = remplirEtudiantAvecResultSet(rs);
          return infosEtudiantDto;
        } else {
          return null;
        }
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
  }

  private InfosEtudiantDto remplirEtudiantAvecResultSet(ResultSet rs) throws SQLException {
    InfosEtudiantDto infosEtudiantDto = bizFactory.getInfosEtudiantDto();
    infosEtudiantDto.setIdUtilisateur(rs.getInt(1));
    infosEtudiantDto.setStatut(rs.getString(2));
    infosEtudiantDto.setNom(rs.getString(3));
    infosEtudiantDto.setPrenom(rs.getString(4));
    infosEtudiantDto.setDateNaissance(rs.getDate(5));
    infosEtudiantDto.setNationalite(rs.getInt(6));
    infosEtudiantDto.setNationaliteLibelle(rs.getString(7));
    infosEtudiantDto.setAdresse(rs.getString(8));
    infosEtudiantDto.setTelephone(rs.getString(9));
    infosEtudiantDto.setEmail(rs.getString(10));
    infosEtudiantDto.setSexe(rs.getString(11));
    infosEtudiantDto.setNbAnneesReussies(rs.getInt(12));
    infosEtudiantDto.setIban(rs.getString(13));
    infosEtudiantDto.setTitulaireCompte(rs.getString(14));
    infosEtudiantDto.setNomBanque(rs.getString(15));
    infosEtudiantDto.setCodeBic(rs.getString(16));
    infosEtudiantDto.setNoVersion(rs.getInt(17));
    return infosEtudiantDto;
  }

  @Override
  public void ajouterInfosEtudiant(InfosEtudiantDto infosEtudiantDto) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO paeMobEras.infos_etudiants(statut, date_naissance,"
            + " nationalite, adresse, telephone, sexe, nb_annees_reussies,"
            + " iban, titulaire_compte, nom_banque, code_bic,id_utilisateur, no_version)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1);")) {
      remplirInfosEtudiant(infosEtudiantDto, ps);
      ps.setInt(12, infosEtudiantDto.getIdUtilisateur());
      ps.executeUpdate();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public boolean actualiserInfosEtudiant(InfosEtudiantDto infosEtudiantDto, int noVersion) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "UPDATE paeMobEras.infos_etudiants" + " SET statut = ?, date_naissance = ?,"
            + " nationalite = ?, adresse = ?, telephone = ?,"
            + " sexe = ?, nb_annees_reussies = ?, iban = ?, titulaire_compte = ?,"
            + " nom_banque = ?, code_bic = ?, no_version = ?"
            + " WHERE id_utilisateur = ? AND no_version = ?;")) {
      remplirInfosEtudiant(infosEtudiantDto, ps);
      infosEtudiantDto.setNoVersion(noVersion + 1);
      ps.setInt(12, infosEtudiantDto.getNoVersion());
      ps.setInt(13, infosEtudiantDto.getIdUtilisateur());
      ps.setInt(14, noVersion);
      System.out.println(noVersion + " DAOIMPL");
      int nbLignes = ps.executeUpdate();
      if (nbLignes <= 0) {
        System.out.println("fail");
        return false;
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage(), exception);
    }
    return true;
  }

  private void remplirInfosEtudiant(InfosEtudiantDto infosEtudiantDto, PreparedStatement ps)
      throws SQLException {
    ps.setString(1, infosEtudiantDto.getStatut());
    ps.setDate(2, new Date(infosEtudiantDto.getDateNaissance().getTime()));
    ps.setInt(3, infosEtudiantDto.getNationalite());
    ps.setString(4, infosEtudiantDto.getAdresse());
    ps.setString(5, infosEtudiantDto.getTelephone());
    ps.setString(6, infosEtudiantDto.getSexe());
    ps.setInt(7, infosEtudiantDto.getNbAnneesReussies());
    ps.setString(8, infosEtudiantDto.getIban());
    ps.setString(9, infosEtudiantDto.getTitulaireCompte());
    ps.setString(10, infosEtudiantDto.getNomBanque());
    ps.setString(11, infosEtudiantDto.getCodeBic());
  }

  @Override
  public List<InfosEtudiantDto> rechercherEtudiantsBasique(String nom, String prenom) {
    List<InfosEtudiantDto> infos = new ArrayList<>();
    try (PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("SELECT u.id_utilisateur, info.statut, u.nom,"
            + " u.prenom, info.sexe, info.nb_annees_reussies, u.email"
            + " FROM paeMobEras.utilisateurs u LEFT OUTER JOIN paeMobEras.infos_etudiants info"
            + " ON u.id_utilisateur = info.id_utilisateur"
            + " WHERE u.nom LIKE ? AND u.prenom LIKE ? AND u.role_util = 'ETUD';")) {
      ps.setString(1, "%" + nom + "%");
      ps.setString(2, "%" + prenom + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          InfosEtudiantDto infosEtudiantDto = bizFactory.getInfosEtudiantDto();
          infosEtudiantDto.setIdUtilisateur(rs.getInt(1));
          infosEtudiantDto.setStatut(rs.getString(2));
          infosEtudiantDto.setNom(rs.getString(3));
          infosEtudiantDto.setPrenom(rs.getString(4));
          infosEtudiantDto.setSexe(rs.getString(5));
          infosEtudiantDto.setNbAnneesReussies(rs.getInt(6));
          infosEtudiantDto.setEmail(rs.getString(7));
          infos.add(infosEtudiantDto);
        }
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage(), exception);
    }
    return infos;
  }

}
