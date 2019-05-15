package be.ipl.pae.biz.pays;

import static org.junit.jupiter.api.Assertions.assertTrue;

import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.InjectionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaysUccImplTest {

  private PaysUcc paysUcc;

  /**
   * Set up avant chaque test.
   * 
   * @throws Exception si le setUp fail.
   */
  @BeforeEach
  public void setUp() throws Exception {
    ConfigManager.load("test.properties");
    paysUcc = new PaysUccImpl();
    InjectionService.injectDependency(paysUcc);
  }

  /**
   * Test lister pays.
   */
  @Test
  public void testListerPays() {
    assertTrue(paysUcc.listerPays().size() == 2);
  }

}
