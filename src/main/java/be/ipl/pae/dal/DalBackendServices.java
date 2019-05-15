package be.ipl.pae.dal;

import java.sql.PreparedStatement;

public interface DalBackendServices {

  /**
   * Connecte l'application serveur à la base de données en utilisant les propriétés définies dans
   * le fichier dbconfig.properties.
   */
  // void connecter();

  /**
   * @param query une query SQL.
   * @return l'objet {@link PreparedStatement} associé à la query passée en paramètre faite à la
   *         base de données.
   */
  PreparedStatement getPreparedStatement(String query);

}
