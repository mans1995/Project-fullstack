package be.ipl.pae.dal;

import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.exceptions.FatalException;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DalServicesImpl implements DalBackendServices, DalServices {

  private ThreadLocal<Connection> connections;
  private BasicDataSource connectionPoolSource;

  /**
   * Constructeur pour la DalServices.
   */
  public DalServicesImpl() {
    // Création du connectionPool avec un nombre de connexions égal à 8 par défaut.
    connectionPoolSource = new BasicDataSource();
    connectionPoolSource.setUsername(ConfigManager.getProperty("dbusername"));
    connectionPoolSource.setPassword(ConfigManager.getProperty("dbpassword"));
    String url = "jdbc:postgresql://" + ConfigManager.getProperty("dbip") + ":"
        + ConfigManager.getProperty("dbport") + "/" + ConfigManager.getProperty("dbname");
    connectionPoolSource.setUrl(url);
    connections = new ThreadLocal<>();
  }

  @Override
  public PreparedStatement getPreparedStatement(String query) {
    try {
      return connections.get().prepareStatement(query);
    } catch (SQLException exception) {
      throw new FatalException(
          "Problème création du PrepareStatement associé à la query : " + query, exception);
    }
  }

  @Override
  public void startTransaction() {
    /*
     * if (connections.get() != null) { throw new FatalException("Transaction déjà ouvete."); }
     */
    try {
      // si on n'a pas pu récupérer de connexion du ThreadLocal
      if (connections.get() == null) {
        // on génère une nouvelle connexion via le DataSource qu'on ajoute cette nouvelle connexion
        // au ThreadLocal
        connections.set(connectionPoolSource.getConnection());
      }
      connections.get().setAutoCommit(false);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException("Problème lors du démarrage de la transaction.", exception);
    }
  }

  @Override
  public void commitTransaction() {
    if (connections.get() == null) {
      new FatalException("Connection nulle dans le commit.");
    }
    try {
      connections.get().commit();
      // "true" pour permettre l'auto commit des queries suivantes si elles ne font pas partie d'une
      // transaction.
      connections.get().setAutoCommit(true);
      connections.get().close();
      connections.remove();
    } catch (SQLException exception) {
      throw new FatalException("Problème lors d'un commit.", exception);
    }
  }

  @Override
  public void rollbackTransaction() {
    if (connections.get() == null) {
      throw new FatalException("Connection nulle dans le rollback.");
    }
    try {
      connections.get().rollback();
      // true pour permettre l'auto commit des queries suivantes si elles ne font pas partie d'une
      // transaction
      connections.get().setAutoCommit(true);
      connections.get().close();
      connections.remove();
    } catch (SQLException exception) {
      throw new FatalException("Problème lors d'un rollback.", exception);
    }
  }

}
