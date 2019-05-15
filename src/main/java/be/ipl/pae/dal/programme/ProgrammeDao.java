package be.ipl.pae.dal.programme;

import be.ipl.pae.biz.programme.ProgrammeDto;

import java.util.List;

public interface ProgrammeDao {

  /**
   * Renvoie une liste des programmes existants.
   * 
   * @return la liste des programmes présents en base de données.
   */
  List<ProgrammeDto> listerProgrammes();
}
