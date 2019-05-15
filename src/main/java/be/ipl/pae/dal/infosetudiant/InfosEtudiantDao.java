package be.ipl.pae.dal.infosetudiant;

import be.ipl.pae.biz.infosetudiant.InfosEtudiantDto;

import java.util.List;

public interface InfosEtudiantDao {

  /**
   * Permet de vérifier si un étudiant ayant l'id en paramètre existe déjà dans la base de données
   * et renvoyer ces données si c'est le cas.
   * 
   * @param idEtudiant l'id de l'étudiant dont on cherche s'il existe déjà dans la base de données.
   * @return un objet {@link InfosEtudiantDto} si l'email existe déjà, null sinon.
   */
  InfosEtudiantDto chercherInfosEtudiantParId(int idEtudiant);

  /**
   * Ajoute les informations d'un étudiant dans la base de données.
   * 
   * @param infosEtudiantDto l'objet contenant les données de l'étudiant étant à ajouter à la base
   *        de données.
   */
  void ajouterInfosEtudiant(InfosEtudiantDto infosEtudiantDto);

  /**
   * Actualise les informations d'un étudiant dans la base de données.
   * 
   * @param infosEtudiantDto l'objet contenant les données de l'étudiant étant à ajouter à la base
   *        de données.
   */
  boolean actualiserInfosEtudiant(InfosEtudiantDto infosEtudiantDto, int noVersion);

  /**
   * Permet de récupérer une liste d'informations concernant les étudiants correspondant (même
   * partiellement) aux nom et prénom en paramètre.<br>
   * Cette version basique ne permet que de récupérer l'id utilisateur, le statut, le nom, le
   * prénom, le sexe et le nombre d'années réussies des étudiants.
   * 
   * @param nom le nom de/des étudiant(s) recherchés.
   * @param prenom le prénom de/des étudiant(s) recherchés.
   * @return une liste des informations des étudiants correspondant aux critères de recherche.
   */
  List<InfosEtudiantDto> rechercherEtudiantsBasique(String nom, String prenom);

  /**
   * Récupère l'ensemble des informations d'un étudiant.
   * 
   * @param idEtudiant l'identifiant de l'étudiant dont on cherche les informations.
   * @return les informations de l'étudiant correspondant à l'identifiant passé en paramètre.
   */
  InfosEtudiantDto chercherInfosEtudiantPourProf(int idEtudiant);

}
