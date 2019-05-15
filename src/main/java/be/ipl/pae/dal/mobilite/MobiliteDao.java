package be.ipl.pae.dal.mobilite;

import be.ipl.pae.biz.mobilite.MobiliteDto;

import java.util.List;
import java.util.Map;

public interface MobiliteDao {

  /**
   * Permet d'ajouter une mobilité dans la base de donnée.
   * 
   * @param mobiliteDto la mobilité a inserer.
   */
  void ajouterMobilite(MobiliteDto mobiliteDto);

  /**
   * Permet d'ajouter une mobilité avec pays dans la base de donnée.
   * 
   * @param mobiliteDto la mobilité a inserer.
   */
  void ajouterMobiliteAvecPays(MobiliteDto mobiliteDto);

  /**
   * Permet d'ajouter une mobilité avec partenaire dans la base de donnée.
   * 
   * @param mobiliteDto la mobilité a inserer.
   */
  void ajouterMobiliteAvecPartenaire(MobiliteDto mobiliteDto);

  /**
   * Renvoie la liste des mobilites.
   * 
   * @return la liste des mobilités.
   */
  List<MobiliteDto> listerMobilites();

  /**
   * Renvoie la liste des mobilites par annee et/ou etat.
   * 
   * @return la liste des mobilités.
   */
  List<MobiliteDto> listerMobilitesParAnneeEtEtat(MobiliteDto mobiliteDto);

  /**
   * Renvoie la liste des mobilités en fonction d'un utilisateur.
   * 
   * @return la liste des mobilités.
   */
  List<MobiliteDto> listerMobilitesParUtilisateur(int idUtilisateur);

  /**
   * Supprime la mobilité dont l'id est passé en paramètre.
   * 
   * @param mobilite la mobilité a supprimer.
   * 
   * @return true si supprimée.
   */
  boolean supprimerMobilite(MobiliteDto mobilite);

  /**
   * Change l'état de la mobilité en la passant à "creee" si elle est dans l'état "demandee".
   * 
   * @param mobilite la mobilite à update.
   */
  boolean confirmerMobilite(MobiliteDto mobilite);

  /**
   * Change l'état de la mobilité en la passant à "creee" si elle est dans l'état "demandee" en
   * ajoutant un partenaire existant.
   * 
   * @param mobilite la mobilite à update.
   */
  boolean confirmerMobiliteAvecPartenaire(MobiliteDto mobilite);

  /**
   * Change l'état de la mobilité en la passant à "annulee" si elle est dans un etat annulable.
   * 
   * @param mobilite la mobilite à update.
   */
  boolean annulerMobilite(MobiliteDto mobilite);

  /**
   * Renvoie la liste des mobilites.
   * 
   * @return la liste des demandes de mobilités.
   */
  List<MobiliteDto> listerDemandesDeMobilites();

  /**
   * Renvoie la liste des mobilites en fonction d'un utilisateur.
   * 
   * @return la liste des demandes de mobilités.
   */
  List<MobiliteDto> listerDemandesDeMobilitesParEtudiant(int idUtilisateur);

  /**
   * Methode qui met à true les documents qui ont été signés.
   * 
   * @param mobilite la mobilité contenant les documents.
   * @return true si les documents signés ont bien été mis à jour.
   */
  boolean signerDocuments(MobiliteDto mobilite);

  /**
   * Met l'état de la mobilité à "premier paiement demande" dans la base de données.
   * 
   * @param mobiliteDto la mobilité dont il faut changer l'état.
   * @return vrai si l'état de la mobilité a pu passé à "premiere demande paiement".
   */
  boolean indiquerEnvoiPremiereDemandePaiement(MobiliteDto mobiliteDto);

  /**
   * Met l'état de la mobilité à "second paiement demande" dans la base de données.
   * 
   * @param mobiliteDto la mobilité dont il faut changer l'état.
   * @return vrai si l'état de la mobilité a pu passé à "seconde demande paiement".
   */
  boolean indiquerEnvoiSecondeDemandePaiement(MobiliteDto mobiliteDto);

  /**
   * Permet de récupérer une liste des états documents d'un mobilité et l'état de la mobilité.
   * 
   * @param mobiliteDto la mobilité pour laquelle il faut effectuer l'indication.
   * @return une map des états des documents d'un mobilité et de l'état de la mobilité.
   */
  Map<String, String> obtenirListeEtatsDocumentsEtMobilite(MobiliteDto mobiliteDto);

  /**
   * Permet de mettre à jour une mobilité de la base de données en indiquant si l'étudiant associé à
   * cette mobilité a été encodé dans les logiciels externes ProEco et/ou Mobi.
   * 
   * @param mobiliteDto la mobilité dans laquelle encoder les informations.
   */
  void indiquerEtudiantEncodeDansLogiciels(MobiliteDto mobiliteDto);

  /**
   * Permet de récupérer une liste des mobilités pour une certaine année académique avec les
   * indications permettant de savoir si les demandes de paiements associées ont été effectuées.
   * 
   * @param mobiliteDto la mobilité contenant l'année académique pour laquelle on veut récupérer les
   *        demandes de paiement des mobilités.
   * @param idProgrammeSuisse l'id du programme dont le libellé est "Suisse"
   * @return une map associant deux booléens (un par demande de paiement) à chaque mobilité liée à
   *         l'année académique voulue.
   */
  Map<MobiliteDto, Boolean[]> listerDemandesPaiementsParAnneeAcademique(MobiliteDto mobiliteDto,
      int idProgrammeSuisse);

  /**
   * Permet de savoir si tous les documents de retour(post) d'une mobilité on été signés.
   * 
   * @param mobiliteDto la mobilité pour laquelle on veut savoir si tous les documents de retour
   *        (post) ont été rendus signés.
   * @return vrai si tous les documents de retour (post) ont été signés pour la mobilité.
   */
  boolean preciserReceptionDocumentsRetourMobilite(MobiliteDto mobiliteDto);

  /**
   * Permet les demandes de paiements associées à une mobilité.
   * 
   * @param mobiliteDto la mobilité contenant l'identifiant, pour laquelle on veut récupérer les
   *        demandes de paiement des mobilités.
   * @return un tableau des demandes de paiement pour une mobilité.
   */
  Boolean[] obtenirDemandesPaiementsPourMobilite(MobiliteDto mobiliteDto);

  /**
   * Permet de savoir si tous les documents de départ (pre) d'une mobilité on été signés.
   * 
   * @param mobiliteDto la mobilité pour laquelle on veut savoir si tous les documents de départ
   *        (pre) ont été rendus signés.
   * @return vrai si tous les documents de départ (pre) ont été signés pour la mobilité.
   */
  boolean preciserDocumentsDepartMobiliteSignes(MobiliteDto mobiliteDto);

  /**
   * Renvoie une mobilite en fonction de son id.
   * 
   * @param idMobilite l'id.
   * @return une mobilite.
   */
  MobiliteDto trouverMobiliteParId(int idMobilite);

  /**
   * Lie un partenaire à une mobilite existante.
   * 
   * @param mobilite la mobilite a lier au partenaire.
   * @return vrai le partenaire a été ajouté à la mobilité.
   */
  boolean ajouterPartenaireMobiliteExistante(MobiliteDto mobilite);

  /**
   * Compte le nombre de candidatures d'un étudiant.
   * 
   * @param idEtudiant l'étudiant pour qui trouver le nombre de candidatures.
   * @param anneeAcademique l'annee academique des candidatures.
   * @return le nombre de candidatures de l'étudiant.
   */
  int trouverNombreCandidatures(int idEtudiant, String anneeAcademique);

  /**
   * Permet de récupérer l'identifiant d'un programme de la base de données via son libellé.
   * 
   * @param libelle le libellé du programme recherché
   * @return l'identifiant du programme recherché.
   */
  int trouverIdProgrammeParLibelle(String libelle);
}
