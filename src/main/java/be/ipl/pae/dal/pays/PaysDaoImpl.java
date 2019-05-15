package be.ipl.pae.dal.pays;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.pays.PaysDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalBackendServices;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaysDaoImpl implements PaysDao {

  @Inject
  private DalBackendServices dalBackendServices;
  @Inject
  private BizFactory bizFactory;

  @Override
  public List<PaysDto> listerPays() {
    List<PaysDto> pays = new ArrayList<>();
    try (PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("SELECT pays.id_pays, pays.id_programme,"
            + " pays.nom_pays, pays.code_pays FROM paeMobEras.pays pays ;")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          PaysDto paysDto = bizFactory.getPaysDto();
          paysDto.setIdPays(rs.getInt(1));
          paysDto.setIdProgramme(rs.getInt(2));
          paysDto.setNomPays(rs.getString(3));
          paysDto.setCodePays(rs.getString(4));
          pays.add(paysDto);
        }
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
    return pays;
  }

}
