package be.ipl.pae.biz.infosetudiant;

import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.exceptions.BizException;

import java.util.List;

public interface InfosEtudiantUcc {

  /**
   * Permet à un étudiant d'introduire ou de modifier ses données.<br>
   * ATTENTION : il faut avoir récupéré le numéro de version des informations de l'étudiant via la
   * méthode chargerNoVersionInfosEtudiantParMail au moment de charger ces informations avant
   * d'utiliser cette méthode introduireDonnees au moment de sauver ces informations.
   * 
   * @param infosEtudiantDto l'objet contenant les informations sur l'étudiant.
   * @param noVersion le numéro de version de l'objet {@link InfosEtudiantDto} avant qu'il ne soit
   *        modifié. S'il vaut 0, on ne fait pas d'actualisation des données mais une simple
   *        insertion dans la base de données si ce n'est pas déjà fait.
   */
  void introduireDonnees(InfosEtudiantDto infosEtudiantDto, int noVersion);

  /**
   * Permet de récupérer une liste d'informations concernant les étudiants correspondant (même
   * partiellement) aux nom et prénom en paramètre.
   * 
   * @param nom le nom de/des étudiant(s) recherchés.
   * @param prenom le prénom de/des étudiant(s) recherchés.
   * @return une liste des informations des étudiants correspondant aux critères de recherche.
   */
  List<InfosEtudiantDto> rechercherEtudiants(String nom, String prenom);

  /**
   * Permet de récupérer informations (uniquement l'id utilisateur, le statut, le nom, le prénom, le
   * sexe et le nombre d'années réussies) qui sont déjà dans la base de données d'un étudiant.
   * 
   * @param idEtudiant l'id de l'étudiant dont on cherche les informations.
   * @return les informations de l'étudiant si elles existent, lance une {@link BizException}.
   */
  InfosEtudiantDto chargerInfosEtudiantParId(int idEtudiant);

  /**
   * Permet de récupérer toutes les informations qui sont déjà dans la base de données d'un
   * étudiant.
   * 
   * @param prof l'utilisateur qui cherche les informations d'un étudiant précis.
   * @param idEtudiant l'identifiant de l'utilisateur donc le professeur cherche les informations
   * @return les informations de l'étudiant si elles existent, lance une {@link BizException}.
   */
  InfosEtudiantDto chargerInfosEtudiantPourProf(UtilisateurDto prof, int idEtudiant);

}
