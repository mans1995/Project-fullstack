package be.ipl.pae.dal.partenaire;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.partenaire.PartenaireDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalBackendServices;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartenaireDaoImpl implements PartenaireDao {

  @Inject
  private DalBackendServices dalBackendServices;
  @Inject
  private BizFactory bizFactory;

  @Override
  public int ajouterPartenaire(PartenaireDto partenaireDto) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO paeMobEras.partenaires(type_code, nom_legal,"
            + " nom_affaires, nom_complet, departement, type_organisation, nombre_employes,"
            + " adresse, pays, region, code_postal, ville, email, site_web, telephone, no_version)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)"
            + " RETURNING id_partenaire;")) {
      ps.setString(1, partenaireDto.getTypeCode());
      ps.setString(2, partenaireDto.getNomLegal());
      ps.setString(3, partenaireDto.getNomDaffaires());
      ps.setString(4, partenaireDto.getNomComplet());
      ps.setString(5, partenaireDto.getDepartement());
      ps.setString(6, partenaireDto.getTypeOrganisation());
      ps.setInt(7, partenaireDto.getNombreEmployes());
      ps.setString(8, partenaireDto.getAdresse());
      ps.setInt(9, partenaireDto.getIdPays());
      ps.setString(10, partenaireDto.getRegion());
      ps.setString(11, partenaireDto.getCodePostal());
      ps.setString(12, partenaireDto.getVille());
      ps.setString(13, partenaireDto.getEmail());
      ps.setString(14, partenaireDto.getSiteWeb());
      ps.setString(15, partenaireDto.getTelephone());
      try (ResultSet rs = ps.executeQuery()) {
        rs.next();
        return rs.getInt(1);
      }
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage(), exception);
    }

  }

  @Override
  public List<PartenaireDto> listerPartenaires() {
    List<PartenaireDto> partenaires = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT p.type_code, p.nom_legal, p.nom_affaires,"
            + " p.nom_complet, p.departement, p.type_organisation, p.nombre_employes,"
            + " p.adresse, p.pays, pa.nom_pays, pa.code_pays, p.region, p.code_postal,"
            + " p.ville, p.email, p.site_web, p.telephone, p.id_partenaire FROM"
            + " paeMobEras.partenaires p, paeMobEras.pays pa WHERE p.pays=pa.id_pays"
            + " ORDER BY p.nom_complet")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PartenaireDto partenaire = remplirPartenaire(rs);
          partenaires.add(partenaire);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return partenaires;
  }

  @Override
  public List<PartenaireDto> listerPartenairesParPays(int idPays) {
    List<PartenaireDto> partenaires = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT p.type_code, p.nom_legal, p.nom_affaires,"
            + " p.nom_complet, p.departement, p.type_organisation, p.nombre_employes,"
            + " p.adresse, p.pays, pa.nom_pays, pa.code_pays, p.region, p.code_postal,"
            + " p.ville, p.email, p.site_web, p.telephone, p.id_partenaire FROM"
            + " paeMobEras.partenaires p, paeMobEras.pays pa"
            + " WHERE p.pays=pa.id_pays AND pa.id_pays = ? ORDER BY p.nom_complet")) {
      ps.setInt(1, idPays);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PartenaireDto partenaire = remplirPartenaire(rs);
          partenaires.add(partenaire);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return partenaires;
  }

  @Override
  public List<PartenaireDto> listerPartenairesParVille(String ville) {
    List<PartenaireDto> partenaires = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT p.type_code, p.nom_legal, p.nom_affaires,"
            + " p.nom_complet, p.departement, p.type_organisation,"
            + " p.nombre_employes, p.adresse, p.pays, pa.nom_pays, pa.code_pays,"
            + " p.region, p.code_postal,"
            + " p.ville, p.email, p.site_web, p.telephone, p.id_partenaire FROM"
            + " paeMobEras.partenaires p, paeMobEras.pays pa"
            + " WHERE p.pays=pa.id_pays AND p.ville LIKE ? ORDER BY p.nom_complet")) {
      ps.setString(1, "%" + ville + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PartenaireDto partenaire = remplirPartenaire(rs);
          partenaires.add(partenaire);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return partenaires;
  }

  @Override
  public List<PartenaireDto> listerPartenairesParNomLegal(String nomLegal) {
    List<PartenaireDto> partenaires = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT p.type_code, p.nom_legal, p.nom_affaires,"
            + " p.nom_complet, p.departement, p.type_organisation,"
            + " p.nombre_employes, p.adresse, p.pays, pa.nom_pays, pa.code_pays,"
            + " p.region, p.code_postal,"
            + " p.ville, p.email, p.site_web, p.telephone, p.id_partenaire FROM"
            + " paeMobEras.partenaires p, paeMobEras.pays pa"
            + " WHERE p.pays=pa.id_pays AND p.nom_legal LIKE ? ORDER BY p.nom_complet")) {
      ps.setString(1, "%" + nomLegal + "%");
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PartenaireDto partenaire = remplirPartenaire(rs);
          partenaires.add(partenaire);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return partenaires;
  }

  @Override
  public PartenaireDto trouverPartenaireParNomComplet(String nomComplet) {

    PartenaireDto partenaire = null;
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("SELECT p.type_code, p.nom_legal, p.nom_affaires,"
            + " p.nom_complet, p.departement, p.type_organisation,"
            + " p.nombre_employes, p.adresse, p.pays, pa.nom_pays, pa.code_pays,"
            + " p.region, p.code_postal,"
            + " p.ville, p.email, p.site_web, p.telephone, p.id_partenaire FROM"
            + " paeMobEras.partenaires p, paeMobEras.pays pa"
            + " WHERE p.pays=pa.id_pays AND p.nom_complet = ? ORDER BY p.nom_complet")) {
      ps.setString(1, nomComplet);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          partenaire = remplirPartenaire(rs);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }

    return partenaire;
  }


  private PartenaireDto remplirPartenaire(ResultSet rs) throws SQLException {
    // utilisé pour eviter d'avoir une dupliaction de code dans les methode listerPartenaires et
    // trouverPartenaireParNomComplet
    PartenaireDto partenaire = bizFactory.getPartenaireDto();
    partenaire.setTypeCode(rs.getString(1));
    partenaire.setNomLegal(rs.getString(2));
    partenaire.setNomDaffaires(rs.getString(3));
    partenaire.setNomComplet(rs.getString(4));
    partenaire.setDepartement(rs.getString(5));
    partenaire.setTypeOrganisation(rs.getString(6));
    partenaire.setNombreEmployes(rs.getInt(7));
    partenaire.setAdresse(rs.getString(8));
    partenaire.setIdPays(rs.getInt(9));
    partenaire.setNomPays(rs.getString(10));
    partenaire.setCodePays(rs.getString(11));
    partenaire.setRegion(rs.getString(12));
    partenaire.setCodePostal(rs.getString(13));
    partenaire.setVille(rs.getString(14));
    partenaire.setEmail(rs.getString(15));
    partenaire.setSiteWeb(rs.getString(16));
    partenaire.setTelephone(rs.getString(17));
    partenaire.setId(rs.getInt(18));
    return partenaire;
  }
}
