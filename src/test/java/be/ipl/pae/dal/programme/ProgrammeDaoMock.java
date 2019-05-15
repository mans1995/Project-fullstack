package be.ipl.pae.dal.programme;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.programme.ProgrammeDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.programme.ProgrammeDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgrammeDaoMock implements ProgrammeDao {

  @Inject
  BizFactory bizFactory;

  private Set<ProgrammeDto> programmes;


  @Override
  public List<ProgrammeDto> listerProgrammes() {
    programmes = new HashSet<>();
    ProgrammeDto programme1 = bizFactory.getProgrammeDto();
    programme1.setId(1);
    programme1.setLibelle("SMS");
    programmes.add(programme1);
    ProgrammeDto programme2 = bizFactory.getProgrammeDto();
    programme2.setId(2);
    programme2.setLibelle("SMP");
    programmes.add(programme2);
    return new ArrayList<ProgrammeDto>(programmes);
  }


}
