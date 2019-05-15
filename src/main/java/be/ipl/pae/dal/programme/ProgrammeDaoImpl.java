package be.ipl.pae.dal.programme;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.programme.ProgrammeDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalBackendServices;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgrammeDaoImpl implements ProgrammeDao {

  @Inject
  private DalBackendServices dalBackendServices;
  @Inject
  private BizFactory bizFactory;

  @Override
  public List<ProgrammeDto> listerProgrammes() {
    List<ProgrammeDto> programmes = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT prg.id_programme, prg.libelle FROM paeMobEras.programmes prg;")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          ProgrammeDto programmeDto = bizFactory.getProgrammeDto();
          programmeDto.setId(rs.getInt(1));
          programmeDto.setLibelle(rs.getString(2));
          programmes.add(programmeDto);
        }
      }
    } catch (SQLException exception) {
      throw new FatalException("Erreur de lecture dans la base de données.", exception);
    }
    return programmes;
  }

}
