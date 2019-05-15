package be.ipl.pae.biz.programme;

import java.util.List;

public interface ProgrammeUcc {

  /**
   * @return la liste des programmes présents en base de données.
   */
  List<ProgrammeDto> listerProgrammes();
}
