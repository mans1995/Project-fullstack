//
// Fonctions utilitaires
//

/**
 * Créer un pseudo-objet page
 * @param titrePage {string} Titre de la page à afficher dans le browser
 * @param urlPath {string} Chemin de l'URL vers cette page
 */
function createPage(titrePage, urlPath) {
    let page;
    let pageInitial;
    let fonction;
    let event;
    let data;
    const resetPage = () => {
        page.remove();
        page = pageInitial.clone();
    }
    const self = {
        /**
         * Change la page courante
         */
        changePage: function (nData) {
            resetPage();
            data = nData;
            if (fonction instanceof Function) {
                fonction(page);
            }
            let pages = $('#pages');
            const lastPage = pages.children().first();
            lastPage.hide().detach();
            page.appendTo(pages).fadeIn(fadeLength);
            page.find('input:first').focus();
            $('div.alert').remove();
            if (window.location.pathname !== urlPath) {
                window.history.pushState(null, '', urlPath);
            }
            document.title = titrePage + ' - ' + titreBase;
            if (event instanceof Function) {
                event();
            }
        },
        /**
         * Setter de page
         * @param {JQuery<HTMLElement>} nPage 
         */
        setPage: function (nPage) {
            page = nPage;
            pageInitial = nPage.clone();
        },
        /**
         * Setter de fonction
         * @param {function(JQuery<HTMLElement>):void} nFonction à exécuter à chaque changement vers cette page
         */
        setFonction: function (nFonction) {
            fonction = nFonction;
        },
        getUrlPath: () => {
            return urlPath;
        },
        getTitrePage: () => {
            return titrePage;
        },
        setEvent: function (nEvent) {
            event = nEvent;
        },
        resetPage: resetPage,
        getData: () => {
            return data;
        }
    }
    return self;
}

/**
 * Trouve une page par son chemin d'url
 * Renvoie la page d'accueil si aucune page n'est trouvée
 * @param {string} urlPath 
 */
function findPageByPath(urlPath) {
    let page;
    for (const key in pageTab) {
        if (pageTab[key].getUrlPath() === urlPath) {
            page = pageTab[key];
            break;
        }
    }
    if (!page) {
        page = pageTab.pageAccueil;
    }
    return page;
}

/**
 * Affiche la navbar et sidebar
 */
function showNavigation() {
    const navbar = $('#navbar');
    const sidebar = $('#sidebar-wrapper');
    navbar.find('div#msg-bonjour em:first').text(utilisateur.prenom);
    if (navbar.is(':hidden')) {
        navbar.fadeIn(fadeLength, () => {

        });
    }
    if (sidebar.is(':hidden')) {
        sidebar.fadeIn(fadeLength);
    }

}

function hideNavigation() {
    $('#navbar').hide();
    $('#sidebar-wrapper').hide();
}

/**
 * Fonction utilitaire pour envoyer une requète AJAX à l'API
 * @param {string} json Le json à envoyé à l'API
 * @param {function} onSuccess Fonction appelée à la réception d'une réponse à la requète
 * @param {function} onError Fonction appelée à la réception d'un code erreur à la requète
 * @param {string} type Type de la requète (POST par défaut)
 */
function ajax(action, json = '', onSuccess, onError, type = 'POST') {
    return $.ajax({
        url: urlAPI + '?action=' + action,
        type: type,
        data: json,
        contentType: 'application/json; charset: utf-8',
        dataType: 'json',
        success: onSuccess,
        error: onError
    });
}

/**
 * Crée une alerte bootstrap
 * @param {string} message Le message de l'alerte
 * @param {string} typeAlerte La class bootstrap définissant le type de l'alerte
 * @returns {JQuery<HTMLElement>} L'alerte créee
 */
function creerAlerte(message, typeAlerte, fermable = false) {
    let alerte = $('<div class="alert" role="alert">');
    alerte.text(message);
    alerte.addClass(typeAlerte);
    if (fermable) {
        alerte.addClass('alert-dismissible');
        alerte.append(creerBtnFermer());
    }
    return alerte;
}

function supprimerAlertes() {
    $('div.alert').remove();
}

function creerBtnFermer() {
    return $('<button type="button" class="close" data-dismiss="alert" aria-label="Close">')
        .append('<span aria-hidden="true">&times;</span>');
}

function premierChargementConnecte(page) {
    $.when(ajax('listerPays'),
        ajax('listerProgrammes'),
        ajax('listerEtudiants'),
    ).then((dataPays, dataProgrammes) => {
        listePays = dataPays[0].liste;
        listeProgrammes = dataProgrammes[0].liste;
        listeProgrammes.forEach((el) => {
            programmeInfo[el.id] = {
                libelle: el.libelle,
                pays: [],
                partenaires: []
            };
        });
        listePays.forEach((el) => {
            programmeInfo[el.idProgramme].pays.push(el);
        });
        page.changePage();
        navbarEvent();
        showNavigation();
    });
}

function safeString(string) {
    return string === undefined || string === null ? '' : string;
}