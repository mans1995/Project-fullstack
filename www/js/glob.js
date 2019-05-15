//
// Variables et fonctions
//

const urlAPI = '/';
const fadeLength = 500;
let utilisateur = [];
const titreBase = document.title;
let isProf = false;
const pageTab = {
    pageConnexion: createPage('Connexion', '/connexion'),
    pageAccueil: createPage('Accueil', '/'),
    pageMesInformations: createPage('Mes Informations', '/mesInfos'),
    pageEncoderPartenaire: createPage('Encoder Partenaire', '/encoderPartenaire'),
    pagePartenaires: createPage('Rechercher des partenaires', '/recherchePartenaire'),
    pageMobilites: createPage('Rechercher des mobilités', '/rechercheMobilites'),
    pageEtudiants: createPage('Rechercher des étudiants', '/rechercheEtudiants'),
    pageEncoderMobilite: createPage('Encoder des mobilités', '/encoderMobilite'),
    pageDocumentsMobilite: createPage('Traiter une mobilité', '/traitementMobilite'),
    pageUtilisateurs: createPage('Les utilisateurs', '/utilisateurs'),
    pageListeDesPaiements: createPage('Liste des paiements', '/listeDesPaiements')
};
let navbarEvent;
let listePays = [];
let listeProgrammes = [];
const programmeInfo = [];

function initPages() {
    pageTab.pageConnexion.setPage($('#page-connexion'));
    pageTab.pageConnexion.setFonction((page) => {
        utilisateur = [];
        hideNavigation();
    });
    pageTab.pageAccueil.setPage($('#page-accueil'));
    pageTab.pageAccueil.setFonction((page) => {
        const demandesProf = page.find('#demandes-prof');
        const demandesEtud = page.find('#demandes-etud');
        if (utilisateur.role !== 'ETUD') {
            demandesEtud.hide();
            demandesProf.show();
        } else {
            demandesProf.hide();
            demandesEtud.show();
        }
    })

    pageTab.pageEncoderPartenaire.setPage($('#page-encoderPartenaire'));

    pageTab.pageMesInformations.setPage($('#page-mesInformations'));

    pageTab.pagePartenaires.setPage($('#page-partenaires'));

    pageTab.pageMobilites.setPage($('#page-mobilites'));

    pageTab.pageEtudiants.setPage($('#page-etudiants'));

    pageTab.pageEncoderMobilite.setPage($('#page-encoderMobilite'));

    pageTab.pageDocumentsMobilite.setPage($('#page-documentsMobilite'));

    pageTab.pageUtilisateurs.setPage($('#page-utilisateurs'));

    pageTab.pageListeDesPaiements.setPage($('#page-listeDesPaiements'));

}