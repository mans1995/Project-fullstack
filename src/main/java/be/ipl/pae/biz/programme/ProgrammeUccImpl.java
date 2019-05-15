package be.ipl.pae.biz.programme;

import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalServices;
import be.ipl.pae.dal.programme.ProgrammeDao;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class ProgrammeUccImpl implements ProgrammeUcc {

  @Inject
  private ProgrammeDao programmeDao;
  @Inject
  private DalServices dalServices;

  @Override
  public List<ProgrammeDto> listerProgrammes() {
    dalServices.startTransaction();
    try {
      List<ProgrammeDto> programmes = programmeDao.listerProgrammes();
      dalServices.commitTransaction();
      return programmes;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

}
