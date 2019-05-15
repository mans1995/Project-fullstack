pageTab.pageMobilites.setEvent(function () {
    const tbody = $('#page-mobilites table tbody');

    $('#anneeAcademique-rechercherMobilites').val(new Date().getFullYear());

    let listeMobilites = [];
    listerMobilites();

    function listerMobilites() {
        ajax('listerMobilites', '', (data) => {
            listeMobilites = data.liste;
            remplirTable();
        });
    }

    $('#resetListeMobilites').click(listerMobilites);

    const modalAnnulerMobilite = $('#annulerMobilite');
    modalAnnulerMobilite.on('hide.bs.modal', () => pageTab.pageMobilites.changePage());
    const estEtud = utilisateur.role === 'ETUD';
    if (!estEtud) {
        $('table#tableMobilites thead tr').append('<th scope="col">Action');
    }

    function remplirTable() {
        tbody.empty();
        listeMobilites.forEach((el, i) => {
            const ligne = $('<tr>');
            ligne.append('<td>' + safeString(el.libelleProgramme));
            ligne.append('<td>' + safeString(el.nomUtilisateur) + ' ' + safeString(el.prenomUtilisateur));
            ligne.append('<td>' + safeString(el.typeCode));
            ligne.append('<td>' + safeString(el.nomLegalPartenaire));
            ligne.append('<td>' + safeString(el.departement));
            ligne.append('<td>' + safeString(el.nomPays));
            ligne.append('<td>' + safeString(el.quadrimestre));
            ligne.append('<td>' + safeString(el.anneeAcademique));
            ligne.append('<td>' + safeString(el.etat));
            ligne.append('<td>' + safeString(el.etatAvantAnnulation));
            const colAction = $('<td>');
            if (!estEtud && el.etat !== 'annulee' && el.etat !== 'second paiement demande') {
                const btnAnnuler = $('<button class="btn btn-danger">Annuler</button>');
                btnAnnuler.click((event) => {
                    annulerMobilite(el.idMobilite);
                });
                const boutonTraitement = $('<button class="btn btn-primary m-1 btnDetails-etudiants" id="btnDetails-etudiants" value="' + el.idMobilite + '">Traiter</button>');
                boutonTraitement.click(() => {
                    pageTab.pageDocumentsMobilite.changePage(el);
                });
                colAction.append(btnAnnuler).append(boutonTraitement);
            }
            ligne.append(colAction);
            tbody.append(ligne);
        });
    }

    function annulerMobilite(idMobilite) {
        const form = $('#formAnnulerMobilite');
        const modalBody = form.find('.modal-body');
        const input = form.find('input#annulerMobilite-raison');
        form.submit((event) => {
            event.preventDefault();
            form.find('button#btnAnnulerMobilite-confirmer').hide();
            ajax('annulerMobilite', JSON.stringify({
                idMobilite: idMobilite,
                raisonAnnulation: input.val()
            }), (data) => {
                input.val('');
                modalBody.children().hide();
                modalBody.prepend(creerAlerte('La demande a bien été annulée.', 'alert-success'));
            }, (xhr, status, text) => {
                let messageErreur = '';
                if (xhr.responseJSON && xhr.responseJSON.errorInfo) {
                    messageErreur = xhr.responseJSON.errorInfo;
                } else {
                    messageErreur = 'erreur inconnue';
                }
                modalBody.prepend(creerAlerte('La demande n\'a pas pu être annulée : ' + messageErreur, 'alert-warning'));
            });
        });
        modalAnnulerMobilite.on('shown.bs.modal', () => input.focus());
        modalAnnulerMobilite.modal();
    }

    $('#form-rechercherMobilites').submit((event) => {
        event.preventDefault();
        const form = $(event.delegateTarget);
        let anneeAcademique = form.find('#anneeAcademique-rechercherMobilites').val();
        anneeAcademique = anneeAcademique + '-' + (Number(anneeAcademique) + 1);
        let etat = form.find('#etat-rechercherMobilites').val();
        if (etat === 'null' || etat === '' || etat === undefined) {
            etat = null;
        }
        ajax('listerMobilitesParAnneeEtEtat', JSON.stringify({
            "anneeAcademique": anneeAcademique,
            "etat": etat
        }), (data) => {
            listeMobilites = data.liste;
            remplirTable();
        });
    })

});