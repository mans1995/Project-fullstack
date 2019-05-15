pageTab.pageEtudiants.setEvent(function () {

    let listeEtudiants = [];
    ajax('listerEtudiants', '', (data) => {
        listeEtudiants = data.etudiants;
        remplirTableau();
    });

    const tab = $('.tableEtudiants');
    let isProf;
    if (utilisateur.role !== "ETUD") {
        tab.find('thead tr').append('<th scope="col">Action</th>');
        isProf = true;
    } else {
        isProf = false;
    }


    const RechercheEtudiants = $('form#form-rechercherEtudiants');
    const modalDetailsEtudiant = $('#detailsEtudiant');
    RechercheEtudiants.submit((event) => {
        event.preventDefault();
        let formRechercherEtudiants = $(event.delegateTarget);
        formRechercherEtudiants.find('.alert').remove();
        let formNom = formRechercherEtudiants.find('input#nom-rechercherEtudiants').val();
        let formPrenom = formRechercherEtudiants.find('input#prenom-rechercherEtudiants').val();
        let jsonTab = {
            nom: formNom,
            prenom: formPrenom
        };

        ajax('rechercherEtudiants', JSON.stringify(jsonTab), (data) => {
            listeEtudiants = data.liste;
            remplirTableau();
        }, (xhr, status, text) => {
            let messageErreur = xhr.responseJSON === undefined ? xhr.statusText : xhr.responseJSON.errorInfo;
            if (!messageErreur) {
                messageErreur = xhr.statusText;
            }
            let alerte = creerAlerte('Erreur : ' + messageErreur, 'alert-warning');
            alerte.hide();
            formRechercherEtudiants.find('div:first').before(alerte);
            alerte.slideDown();
        });

    });

    function remplirTableau() {
        let tbodyTableEtudiant = tab.find('tbody');
        tbodyTableEtudiant.empty();
        listeEtudiants.forEach((el) => {
            const ligne = $('<tr>');
            const idEtudiant = el.id === undefined ? el.idUtilisateur : el.id;
            ligne.append('<td>' + safeString(idEtudiant));
            ligne.append('<td>' + safeString(el.nom));
            ligne.append('<td>' + safeString(el.prenom));
            ligne.append('<td>' + safeString(el.email));
            if (isProf) {
                const boutonDetails = $('<button class="btn btn-info m-1 btnDetails-etudiants" id="btnDetails-etudiants" value="' + idEtudiant + '">Détails</button>');
                boutonDetails.click(detailsEtudiants);
                const boutonEncoderMob = $('<button class="btn btn-primary m-1 btnEncoderMobilite-etudiants" value="' + idEtudiant + '">Encoder une mobilité</button>');
                boutonEncoderMob.click(() => pageTab.pageEncoderMobilite.changePage(el));
                const boutonEncoderInfo = $('<button class="btn btn-primary m-1 btnEncoderMobilite-etudiants" value="' + idEtudiant + '">Encoder ses infos</button>');
                boutonEncoderInfo.click(() => pageTab.pageMesInformations.changePage(el));
                const colAction = $('<td>');
                colAction.append(boutonDetails).append(boutonEncoderMob).append(boutonEncoderInfo);
                ligne.append(colAction);
            }
            tbodyTableEtudiant.append(ligne);
        });
    }

    function detailsEtudiants() {
        let id_Etudiant = $(this).val();
        let detailsEtudiant = [];
        let jsonTab = {
            idUtilisateur: id_Etudiant
        };

        function remplirTableDetailsEtudiantsVueProf(etudiant) {
            let pageDetail = $('#detailsEtudiant div.modal-body');
            pageDetail.append('<h2>#' + etudiant.idUtilisateur + '</h2><h3>' +
                etudiant.statut + ' ' + etudiant.nom + ' ' + etudiant.prenom + '</h3>Sexe : ' +
                etudiant.sexe + '<br/>Date de naissance : ' +
                etudiant.dateNaissance + '<br/>Nationalité : ' +
                etudiant.nationaliteLibelle + '<br/>Adresse : ' +
                etudiant.adresse + '<br/>Telephone : ' +
                etudiant.telephone + '<br/>Email : ' +
                etudiant.email + '<br/>#Années réussies : ' +
                etudiant.nbAnneesReussies + '<br/><h4>Compte bancaire : </h4>Titulaire du compte : ' +
                etudiant.titulaireCompte + '<br/>Nom de la banque : ' +
                etudiant.nomBanque + '<br/>IBAN : ' +
                etudiant.iban + '<br/>code BIC : ' +
                etudiant.codeBic + '');
        }

        modalDetailsEtudiant.find('div.modal-body').empty();
        ajax('chargerInfosEtudiantPourProf', JSON.stringify(jsonTab), (data) => {
            detailsEtudiant = data.infosEtudiant;
            if (detailsEtudiant !== undefined) {
                remplirTableDetailsEtudiantsVueProf(detailsEtudiant);
            }
        }, (xhr, status, text) => {
            let messageErreur = xhr.responseJSON === undefined ? xhr.statusText : xhr.responseJSON.errorInfo;
            if (!messageErreur) {
                messageErreur = xhr.statusText;
            }
            let alerte = creerAlerte('Erreur : ' + messageErreur, 'alert-warning');
            alerte.hide();
            modalDetailsEtudiant.find('div.modal-body').append(alerte);
            alerte.slideDown();
        });
        modalDetailsEtudiant.modal();
    };
});