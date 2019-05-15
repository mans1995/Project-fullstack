package be.ipl.pae.biz.mobilite;

import be.ipl.pae.biz.utilisateur.UtilisateurDto;

import java.util.List;
import java.util.Map;

public interface MobiliteUcc {
  /**
   * Ajoute une mobilite.
   * 
   * @param listeMobiliteDto la liste des mobilites à ajouter.
   */
  void ajouterMobilite(List<? extends MobiliteDto> listeMobiliteDto);

  /**
   * Liste les mobilites existantes.
   * 
   * @return une liste de MobiliteDto.
   */
  List<MobiliteDto> listerMobilite();

  /**
   * Liste les mobilites existantes par annee et/ou par etat.
   * 
   * @param mobiliteDto un dto contenant une annee academique et/ou un etat.
   * @return une liste de MobiliteDto.
   */
  List<MobiliteDto> listerMobiliteParAnneeEtEtat(MobiliteDto mobiliteDto);

  /**
   * Liste les mobilite en fonction d'un etudiant.
   * 
   * @return une liste de MobiliteDto.
   */
  List<MobiliteDto> listerMobiliteDunEtudiant(int idUtilisateur);

  /**
   * Confirme une mobilite avec un partenaire existant.
   * 
   * @param mobilite la mobilite a confirmer.
   * @param utilisateur le prof.
   */
  void confirmerMobilite(MobiliteDto mobilite, UtilisateurDto utilisateur);

  /**
   * Supprime la mobilite dont l'id est passé en paramètre.
   * 
   * @param mobilite la mobilite a supprimer.
   * 
   * @return true si supprimée.
   */
  boolean supprimerMobilite(MobiliteDto mobilite);

  /**
   * Change l'état de la mobilité en la passant à "annulee" si elle est dans un etat annulable.
   * 
   * @param mobilite la mobilite à update.
   */
  boolean annulerMobilite(MobiliteDto mobilite);

  /**
   * Renvoie la liste des mobilités, celles de l'étudiant si c'est un étudiant qui fait la
   * recherche, toute les mobilités sinon.
   * 
   * @return une liste des demandes de mobilités.
   */
  List<MobiliteDto> listerDemandesDeMobilites(UtilisateurDto utilisateurDto);

  /**
   * Methode qui met à true les documents qui ont été signés.
   * 
   * @param mobilite la mobilité contenant les documents.
   * @return true si les documents signés ont bien été mis à jour.
   */
  boolean signerDocuments(MobiliteDto mobilite);

  /**
   * Le professeur indique qu'il a envoyé la première demande de paiement pour une mobilité d'un
   * étudiant.
   * 
   * @param utilisateurDto l'utilisateur qui désire indiquer la première demande de paiement.
   * @param mobiliteDto la mobilité pour laquelle il faut effectuer l'indication.
   * @return vrai si l'état de la mobilité a pu passé à "premiere demande paiement".
   */
  boolean indiquerEnvoiPremiereDemandePaiement(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto);

  /**
   * Le professeur indique qu'il a envoyé la seconde demande de paiement pour une mobilité d'un
   * étudiant.
   * 
   * @param utilisateurDto l'utilisateur qui désire indiquer la seconde demande de paiement.
   * @param mobiliteDto la mobilité pour laquelle il faut effectuer l'indication.
   * @return vrai si l'état de la mobilité a pu passé à "seconde demande paiement".
   */
  boolean indiquerEnvoiSecondeDemandePaiement(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto);

  /**
   * Permet de récupérer une liste des états documents d'un mobilité et l'état de la mobilité.
   * 
   * @param utilisateurDto l'utilisateur qui désire récupérer les états.
   * @param mobiliteDto la mobilité pour laquelle il faut effectuer l'indication.
   * @return une map des états des documents d'une mobilité et de l'état de la mobilité.
   */
  Map<String, String> obtenirListeEtatsDocumentsEtMobilite(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto);

  /**
   * Renvoie une mobilite en fonction de son id. Utilisée de façon interne à l'API.
   * 
   * @param idMobilite l'id.
   * @return une mobilite.
   */
  MobiliteDto trouverMobiliteParId(int idMobilite);

  /**
   * Permet de mettre à jour une mobilité de la base de données en indiquant si l'étudiant associé à
   * cette mobilité a été encodé dans les logiciels externes ProEco et/ou Mobi. L'objet dto de la
   * mobilité doit contenir les informations concernant ces deux encodages.
   * 
   * @param utilisateurDto l'utilisateur qui désire encoder les informations de l'étudiant pour la
   *        mobilité.
   * @param mobiliteDto la mobilité dans laquelle encoder les informations.
   */
  void indiquerEtudiantEncodeDansLogiciels(UtilisateurDto utilisateurDto, MobiliteDto mobiliteDto);

  /**
   * Permet de récupérer une liste des mobilités pour une certaine année académique avec les
   * indications permettant de savoir si les demandes de paiements associées ont été effectuées.
   * 
   * @param utilisateurDto l'utilisateur qui désire lister les demandes de paiements par année
   *        académique.
   * @param mobiliteDto la mobilité contenant l'année académique pour laquelle on veut récupérer les
   *        demandes de paiement des mobilités.
   * @return une map associant deux booléens (un par demande de paiement) à chaque mobilité liée à
   *         l'année académique voulue.
   */
  Map<MobiliteDto, Boolean[]> listerDemandesPaiementsParAnneeAcademique(
      UtilisateurDto utilisateurDto, MobiliteDto mobiliteDto);

  /**
   * Permet les demandes de paiements associées à une mobilité.
   * 
   * @param utilisateurDto l'utilisateur qui désire obtenir les demandes de paiement pour une
   *        mobilité.
   * @param mobiliteDto la mobilité contenant l'identifiant, pour laquelle on veut récupérer les
   *        demandes de paiement des mobilités.
   * @return un tableau des demandes de paiement pour une mobilité.
   */
  Boolean[] obtenirDemandesPaiementsPourMobilite(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto);

  /**
   * Permet de savoir si tous les documents de retour(post) d'une mobilité on été signés.
   * 
   * @param utilisateurDto l'utilisateur qui savoir si les documents de retour ont été signés.
   * @param mobiliteDto la mobilité pour laquelle on veut savoir si tous les documents de retour
   *        (post) ont été rendus signés.
   * @return vrai si tous les documents de retour (post) ont été signés pour la mobilité.
   */
  boolean preciserReceptionDocumentsRetourMobilite(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto);

  /**
   * Permet de savoir si tous les documents de départ (pre) d'une mobilité on été signés.
   * 
   * @param utilisateurDto l'utilisateur qui savoir si les documents de départ ont été signés.
   * @param mobiliteDto la mobilité pour laquelle on veut savoir si tous les documents de départ
   *        (pre) ont été rendus signés.
   * @return vrai si tous les documents de départ (pre) ont été signés pour la mobilité.
   */
  boolean preciserDocumentsDepartMobiliteSignes(UtilisateurDto utilisateurDto,
      MobiliteDto mobiliteDto);

}
