package be.ipl.pae.main;

import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.ihm.WebServer;
import be.ipl.pae.utils.Log;

public class Main {

  /**
   * Point d'entrée du programme. Charge le logger, le fichier properties et lance le serveur web.
   */
  public static void main(String[] args) throws Exception {
    // On commence par créer le log
    Log.createLogger();
    ConfigManager.load("prod.properties");
    WebServer srv = new WebServer();
    srv.start();
  }

}
