package be.ipl.pae.biz.pays;

import java.util.List;

public interface PaysUcc {

  /**
   * @return une liste contenant le nom de tous les pays présents dans la base de données dans leur
   *         ordre d'identification.
   */
  List<PaysDto> listerPays();

}
