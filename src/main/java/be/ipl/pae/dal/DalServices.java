package be.ipl.pae.dal;

public interface DalServices {

  /**
   * Démarre une transaction en mettant l'auto commit à false et en récupérant une connexion afin de
   * l'utiliser pour faire des requêtes.
   */
  void startTransaction();

  /**
   * Effectue un commit une transaction, remet l'auto commit à true et ferme la connexion utilisée
   * pour la transaction en la remettant dans le pool de connexions.
   */
  void commitTransaction();

  /**
   * Effectue un rollback sur une transaction, remet l'auto commit à true et ferme la connexion
   * utilisée pour la transaction en la remettant dans le pool de connexions.
   */
  void rollbackTransaction();

}
