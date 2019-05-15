package be.ipl.pae.config;

import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.utils.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigManager {

  private static Properties prop;

  /**
   * Chargement du fichier des propriétés de l'application (de type {@link Properties}).
   * 
   * @param filePath chemin du fichier de propriétés à charger.
   */
  public static void load(String filePath) {
    prop = new Properties();
    Path path = FileSystems.getDefault().getPath(filePath);
    try (InputStream in = Files.newInputStream(path)) {
      prop.load(in);
    } catch (IOException exception) {
      Log.severe("Erreur lors du chargement des propriété du ConfigManager.", exception);
      throw new FatalException("Impossible de trouver " + filePath);
    }
  }

  /**
   * Permet d'obtenir une propriété de type {@link Properties} se trouvant dans le fichier de
   * configuration de l'application.
   * 
   * @param key la clé pour laquelle on cherche la valeur correspondant à une propriété.
   * @return la valeur correspondant à une propriété.
   */
  public static String getProperty(String key) {
    return prop.getProperty(key);
  }

}
