package be.ipl.pae.biz.pays;

import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalServices;
import be.ipl.pae.dal.pays.PaysDao;
import be.ipl.pae.exceptions.FatalException;

import java.util.List;

public class PaysUccImpl implements PaysUcc {

  @Inject
  private PaysDao paysDao;
  @Inject
  private DalServices dalServices;

  @Override
  public List<PaysDto> listerPays() {
    dalServices.startTransaction();
    try {
      List<PaysDto> pays = paysDao.listerPays();
      dalServices.commitTransaction();
      return pays;
    } catch (FatalException exception) {
      // Le rollback va fermer la connexion
      dalServices.rollbackTransaction();
      throw new FatalException(exception.getMessage(), exception);
    }
  }

}
