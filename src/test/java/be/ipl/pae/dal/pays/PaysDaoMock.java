package be.ipl.pae.dal.pays;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.pays.PaysDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.pays.PaysDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaysDaoMock implements PaysDao {

  @Inject
  BizFactory bizFactory;

  private Set<PaysDto> pays;

  @Override
  public List<PaysDto> listerPays() {
    pays = new HashSet<>();
    PaysDto pays1 = bizFactory.getPaysDto();
    pays1.setIdPays(1);
    pays1.setCodePays("BK");
    pays1.setNomPays("Balekistan");
    pays1.setIdProgramme(1);
    pays.add(pays1);
    PaysDto pays2 = bizFactory.getPaysDto();
    pays2.setIdPays(2);
    pays2.setCodePays("BE");
    pays2.setNomPays("Belgique");
    pays2.setIdProgramme(2);
    pays.add(pays2);
    return new ArrayList<PaysDto>(pays);
  }


}
