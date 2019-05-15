package be.ipl.pae.dal.pays;

import be.ipl.pae.biz.pays.PaysDto;

import java.util.List;

public interface PaysDao {

  /**
   * @return une liste contenant le nom de tous les pays présents dans la base de données.
   */
  List<PaysDto> listerPays();

}
