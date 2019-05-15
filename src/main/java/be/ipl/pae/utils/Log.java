package be.ipl.pae.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

  public static final Logger LOGGER = Logger.getLogger(Log.class.getName());
  private static final StringWriter stringWriter = new StringWriter();
  private static final PrintWriter printWriter = new PrintWriter(stringWriter);

  /**
   * Cree un fichier log.
   */
  public static void createLogger() {
    LOGGER.setLevel(Level.FINEST);
    try {
      FileHandler fileHandler = new FileHandler("logs/paelogs_"
          + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_hh-mm-ss"))
          + ".html", true);
      MyHtmlFormatter simpleFormatter = new MyHtmlFormatter();
      fileHandler.setFormatter(simpleFormatter);
      LOGGER.addHandler(fileHandler);
      LOGGER.setUseParentHandlers(false);
    } catch (SecurityException | IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * Envoie un message severe.
   * 
   * @param message le message throw.
   * @param exception l'exception threw.
   */
  public static void severe(String message, Throwable exception) {
    logIt(Level.SEVERE, message, exception);
  }

  /**
   * Envoie un message warning.
   * 
   * @param message le message throw.
   * @param exception l'exception threw.
   */
  public static void warning(String message, Throwable exception) {
    logIt(Level.WARNING, message, exception);
  }

  /**
   * Envoie un message info.
   * 
   * @param message le message throw.
   * @param exception l'exception threw.
   */
  public static void info(String message, Throwable exception) {
    logIt(Level.INFO, message, exception);
  }

  /**
   * Envoie un message config.
   * 
   * @param message le message throw.
   * @param exception l'exception threw.
   */
  public static void config(String message, Throwable exception) {
    logIt(Level.CONFIG, message, exception);
  }

  /**
   * Envoie un message fine.
   * 
   * @param message le message throw.
   * @param exception l'exception threw.
   */
  public static void fine(String message, Throwable exception) {
    logIt(Level.FINE, message, exception);
  }

  /**
   * Envoie un message finer.
   * 
   * @param message le message throw.
   * @param exception l'exception threw.
   */
  public static void finer(String message, Throwable exception) {
    logIt(Level.FINER, message, exception);
  }

  /**
   * Envoie un message finest.
   * 
   * @param message le message throw.
   * @param exception l'exception threw.
   */
  public static void finest(String message, Throwable exception) {
    logIt(Level.FINEST, message, exception);
  }

  /**
   * Méthode générique pour créer des messages de log.
   * 
   * @param level le niveau de gravité de l'exception.
   * @param message le essage throw.
   * @param exception l'exception threw.
   */
  public static void logIt(Level level, String message, Throwable exception) {
    exception.printStackTrace(printWriter);
    String ssStackTrace = stringWriter.toString(); // stack trace as a string
    LOGGER.log(level, "<b>" + message + "</b>" + "<br/><br/><i style=\"line-height:14pt\">"
        + ssStackTrace + "<i>");
  }

}
