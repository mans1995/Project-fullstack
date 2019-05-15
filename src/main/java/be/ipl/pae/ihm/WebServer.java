package be.ipl.pae.ihm;

import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.InjectionService;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServlet;

public class WebServer {

  /**
   * Démarre le serveur web.
   */
  public void start() {
    try {
      WebAppContext context = new WebAppContext();
      HttpServlet servlet = new MyServlet();
      InjectionService.injectDependency(servlet);
      context.setResourceBase("www");
      context.addServlet(new ServletHolder(servlet), "/*");
      context.setMaxFormContentSize(10000000); // limite de 10MB envoyé par le client

      Server server = new Server(Integer.parseInt(ConfigManager.getProperty("svPort")));
      server.setHandler(context);

      server.start();
    } catch (Exception exception) {
      exception.printStackTrace();
      System.exit(1);
    }

  }
}
