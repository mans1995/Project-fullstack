pageTab.pageAccueil.setEvent(function () {

    let listeDemandes = [];
    let listeMobilites = [];

    ajax('listerDemandes', '', (data) => {
        listeDemandes = data.liste;
        remplirTableDemandes();
    });

    const estEtud = utilisateur.role === 'ETUD';

    if (estEtud) {
        ajax('listerMobilitePourEtudiant', '', (data) => {
            listeMobilites = data.liste;
            remplirTableMobilites();
        });
    }
    const tableDemandesProf = $('#demandes-prof table#table-demandes');
    const demandesProf = tableDemandesProf.find('tbody');

    const tableDemandesEtud = $('#demandes-etud table#table-etud-demandes');
    const tableMobilitesEtud = $('#demandes-etud table#table-etud-mobilites');
    const demandesEtud = tableDemandesEtud.find('tbody');
    const mobilitesEtud = tableMobilitesEtud.find('tbody');

    $('button#btn-recherche-partenaires').click(() => {
        pageTab.pagePartenaires.changePage();
    });

    $('button#btn-recherche-mobilites').click(() => {
        pageTab.pageMobilites.changePage();
    });

    $('button#btn-recherche-etudiants').click(() => {
        pageTab.pageEtudiants.changePage();
    });

    $('button#btn-extraireCsv').click(() => {
        extraireCsv();
    });

    const btnConfirmer = $('<button type="button" class="btn btn-primary m-1">Confirmer</button>');
    const btnAjoutPart = $('<button type="button" class="btn btn-primary m-1">Ajouter un partenaire</button>');
    const btnAnnuler = $('<button type="button" class="btn btn-danger m-1">Annuler</button>');
    const btnRefuser = $('<button type="button" class="btn btn-danger m-1">Refuser</button>');
    const modalAnnulerMobilite = $('#accueilAnnulerMobilite');
    const modalConfirmerMobilite = $('#accueilConfirmerMobilite');

    modalAnnulerMobilite.on('hide.bs.modal', () => pageTab.pageAccueil.changePage());
    modalConfirmerMobilite.on('hide.bs.modal', () => pageTab.pageAccueil.changePage());

    function remplirTableMobilites() {
        listeMobilites.forEach((el, i) => {
            const ligne = $('<tr>');
            ligne.append('<td>' + safeString(el.libelleProgramme));
            ligne.append('<td>' + safeString(el.typeCode));
            ligne.append('<td>' + safeString(el.nomLegalPartenaire));
            ligne.append('<td>' + safeString(el.nomPays));
            ligne.append('<td>' + safeString(el.anneeAcademique));
            ligne.append('<td>' + safeString(el.etat))
            const colAction = $('<td>');
            ligne.append(colAction);
            if (el.etat !== 'annulee') {
                const btn = btnAnnuler.clone();
                btn.click(() => annulerMobilite(el.idMobilite, 'Annulation de la mobilité en cours',
                    'La mobilité a bien été annulée.',
                    'La mobilité n\'a pas pu être annulée : '));
                colAction.append(btn);
            }
            mobilitesEtud.append(ligne);
        });
    }

    function remplirTableDemandes() {
        if (!estEtud) {
            listeDemandes.forEach((el, i) => {
                const date = el.dateIntroduction.dayOfMonth + '/' + el.dateIntroduction.monthValue + '/' + el.dateIntroduction.year;
                const ligne = $('<tr>');
                if (el.preference === 1) {
                    ligne.addClass('table-primary');
                } else {
                    ligne.addClass('table-secondary');
                }
                ligne.append('<th scope="row">' + safeString(el.noCandidature));
                ligne.append('<td>' + safeString(el.nomUtilisateur) + ' ' + safeString(el.prenomUtilisateur));
                ligne.append('<td>' + safeString(el.departement));
                ligne.append('<td>' + safeString(el.preference));
                ligne.append('<td>' + safeString(el.libelleProgramme));
                ligne.append('<td>' + safeString(el.typeCode));
                ligne.append('<td>' + safeString(el.quadrimestre));
                ligne.append('<td>' + safeString(el.nomLegalPartenaire));
                ligne.append('<td>' + safeString(el.nomPays));
                ligne.append('<td>' + date);
                let btn, btn2;
                if (el.nomLegalPartenaire) {
                    btn = btnConfirmer.clone();
                    btn.click(() => {
                        confirmerMobilite(el.idMobilite, el.quadrimestre);
                    });
                } else {
                    btn = btnAjoutPart.clone();
                    btn.click(() => {
                        pageTab.pageEncoderPartenaire.changePage({
                            idMobilite: el.idMobilite,
                            idPays: el.idPays,
                            quadrimestre: el.quadrimestre,
                            idProgramme: el.idProgramme
                        });
                    });
                }
                btn2 = btnRefuser.clone();
                btn2.click(() => annulerMobilite(el.idMobilite, 'Refuser la demande de mobilité',
                    'La demande a bien été refusée.',
                    'La demande n\'a pas pu être refusée : '));
                ligne.append($('<td></td>').append(btn).append(btn2));
                demandesProf.append(ligne);
            });
        } else {
            listeDemandes.forEach((el, i) => {
                const date = el.dateIntroduction.dayOfMonth + '/' + el.dateIntroduction.monthValue + '/' + el.dateIntroduction.year;
                const ligne = $('<tr>');
                if (el.preference === 1) {
                    ligne.addClass('table-primary');
                } else {
                    ligne.addClass('table-secondary');
                }
                ligne.append('<th scope="row">' + safeString(el.noCandidature));
                ligne.append('<td>' + safeString(el.preference));
                ligne.append('<td>' + safeString(el.libelleProgramme));
                ligne.append('<td>' + safeString(el.typeCode));
                ligne.append('<td>' + safeString(el.nomLegalPartenaire));
                ligne.append('<td>' + safeString(el.nomPays));
                ligne.append('<td>' + date);
                const colAction = $('<td>');
                ligne.append(colAction);
                if (el.nomLegalPartenaire === null) {
                    const btn = btnAjoutPart.clone();
                    btn.click(() => {
                        pageTab.pageEncoderPartenaire.changePage({
                            idMobilite: el.idMobilite,
                            idPays: el.idPays,
                            quadrimestre: el.quadrimestre,
                            idProgramme: el.idProgramme
                        });
                    });
                    colAction.append(btn);
                }
                const btn2 = btnAnnuler.clone();
                btn2.click(() => annulerMobilite(el.idMobilite, 'Annulation de la demande de mobilité',
                    'La demande a bien été annulée.',
                    'La demande n\'a pas pu être annulée : '));
                colAction.append(btn2);
                demandesEtud.append(ligne);
            });
        }
    }

    function annulerMobilite(idMobilite, titreModal = '', messageSuccess = '', messageFailed = '') {
        modalAnnulerMobilite.find('#titre-accueilAnnulerMobilite').text(titreModal);
        const form = $('#form-accueilAnnulerMobilite');
        const modalBody = form.find('.modal-body');
        const input = form.find('input#accueilAnnulerMobilite-raison');
        form.submit((event) => {
            event.preventDefault();
            form.find('button#btn-accueilAnnulerMobilite-confirmer').hide();
            ajax('annulerMobilite', JSON.stringify({
                idMobilite: idMobilite,
                raisonAnnulation: input.val()
            }), (data) => {
                input.val('');
                modalBody.children().hide();
                modalBody.prepend(creerAlerte(messageSuccess, 'alert-success'));
            }, (xhr, status, text) => {
                let messageErreur = '';
                if (xhr.responseJSON && xhr.responseJSON.errorInfo) {
                    messageErreur = xhr.responseJSON.errorInfo;
                } else {
                    messageErreur = 'erreur inconnue';
                }
                modalBody.prepend(creerAlerte(messageFailed + messageErreur, 'alert-warning'));
            });
        });
        modalAnnulerMobilite.on('shown.bs.modal', () => input.focus());
        modalAnnulerMobilite.modal();
    }

    function confirmerMobilite(idMobilite, quadrimestre) {
        const form = modalConfirmerMobilite.find('#form-accueilConfirmerMobilite');
        const modalBody = form.find('.modal-body');
        const selectQuadri = form.find('#accueilConfirmerMobilite-quadri');
        if (quadrimestre) {
            selectQuadri.val(quadrimestre);
        }
        form.submit((event) => {
            event.preventDefault();
            form.find('button#btn-accueilConfirmerMobilite-confirmer').hide();
            ajax('confirmerMobilite', JSON.stringify({
                idMobilite: idMobilite,
                quadrimestre: selectQuadri.val()
            }), (data) => {
                modalBody.children().hide();
                modalBody.prepend(creerAlerte('La demande a bien été confirmé', 'alert-success'));
            }, (xhr, status, text) => {
                let messageErreur = '';
                if (xhr.responseJSON && xhr.responseJSON.errorInfo) {
                    messageErreur = xhr.responseJSON.errorInfo;
                } else {
                    messageErreur = 'erreur inconnue';
                }
                modalBody.prepend(creerAlerte('La demande n\'a pas pu être confirmé : ' + messageErreur, 'alert-warning'));
            });
        });
        modalConfirmerMobilite.modal();
    }

    function extraireCsv() {
        let csv = [];
        let rows = tableDemandesProf.find('tr');
        for (let i = 0; i < rows.length; i++) {
            let row = []
            let cols = rows.eq(i).find('td, th');
            for (let j = 0; j < cols.length - 1; j++)
                row.push(cols.eq(j).text());

            csv.push(row.join(','));
        }
        downloadCSV(csv.join('\n'), 'demandesMobilites.csv');
    }

    function downloadCSV(csv, filename) {
        let csvFile;
        let downloadLink;
        csvFile = new Blob(["\ufeff", csv], {
            type: "text/csv"
        });
        downloadLink = document.createElement('a');
        downloadLink.download = filename;
        downloadLink.href = window.URL.createObjectURL(csvFile);
        downloadLink.style.display = "none";
        document.body.appendChild(downloadLink);
        downloadLink.click();
    }

});