package be.ipl.pae.config;

import be.ipl.pae.exceptions.FatalException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class InjectionService {

  private static Map<String, Object> instances = new HashMap<>();

  /**
   * Injecte une dépendance entre un objet et ses attributs marqués par l'annotation {@link Inject}.
   */
  public static void injectDependency(Object object) {
    Field[] fields = object.getClass().getDeclaredFields();
    for (Field f : fields) {
      if (f.isAnnotationPresent(Inject.class)) { // il faut injecter
        f.setAccessible(true);
        try {
          f.set(object, getDependency(f.getType().getName()));
        } catch (IllegalArgumentException | IllegalAccessException exception) {
          throw new FatalException(
              "Impossible d'injecter une dépendance pour : " + f.getType().getName(), exception);
        }
      }
    }
  }

  /**
   * @return une nouvelle instance de classe (objet) à injecter dans l'objet possédant un attribut
   *         de cette même classe annoté via l'annotation {@link Inject}.
   */
  private static Object getDependency(String name) {
    // on fait tjrs dalFactoryProp, trouver le moyen de changer si on a d'autres fichiers de prop
    String implName = ConfigManager.getProperty(name);
    if (instances.containsKey(implName)) { // si on a déjà instancié la classe
      return instances.get(implName);
    } else {
      try {
        Constructor<?> constructor = Class.forName(implName).getDeclaredConstructor();
        constructor.setAccessible(true);
        Object object = constructor.newInstance();
        injectDependency(object); // si jamais la classe qu'on crée a aussi besoin de dépendances
        instances.put(implName, object);
        return object;
      } catch (Throwable throwable) {
        throw new FatalException("Impossible d'obtenir une dépendance pour : " + name, throwable);
      }
    }
  }

}
