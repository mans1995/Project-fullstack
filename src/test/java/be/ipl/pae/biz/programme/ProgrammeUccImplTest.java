package be.ipl.pae.biz.programme;

import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProgrammeUccImplTest {

  private ProgrammeUcc programmeUcc;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si le setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    ConfigManager.load("test.properties");
    programmeUcc = new ProgrammeUccImpl();
    InjectionService.injectDependency(programmeUcc);
  }

  /**
   * Test lister pays.
   */
  @Test
  public void testListerPays() {
    assertTrue(programmeUcc.listerProgrammes().size() == 2);

  }

}
