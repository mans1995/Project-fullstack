pageTab.pageListeDesPaiements.setEvent(() => {
    if (utilisateur.role === 'ETUD') {
        pageTab.pageAccueil.changePage();
    }

    const form = $('form#form-listeDesPaiements');
    const table = $('table#table-listeDesPaiements');
    const tbody = table.find('tbody');
    const inputAnnee = form.find('input#listeDesPaiements-anneeAcademique');
    inputAnnee.val(new Date().getFullYear());

    let listePaiements = {
        paiements: [],
        mobilites: []
    };

    form.submit((event) => {
        event.preventDefault();
        supprimerAlertes();
        let annee = inputAnnee.val();
        annee = annee + '-' + (Number(annee) + 1);
        ajax('listerDemandesPaiementsParAnneeAcademique', JSON.stringify({
            anneeAcademique: annee
        }), (data) => {
            listePaiements = data;
            remplirTable();
        }, (xhr, status, text) => {
            let messageErreur = '';
            if (xhr.responseJSON && xhr.responseJSON.errorInfo) {
                messageErreur = xhr.responseJSON.errorInfo;
            } else {
                messageErreur = 'erreur inconnue';
            }
            const alert = creerAlerte('La liste des paiements n\'a pas pu être récupéré : ' + messageErreur, 'alert-danger');
            table.before(alert);
        });
    });

    form.submit();

    function remplirTable() {
        tbody.empty();
        listePaiements.mobilites.forEach((el, i) => {
            let titreMobilite = safeString(el.libelleProgramme) + ' ' + safeString(el.typeCode) + ' ' + safeString(el.nomPays);
            if (el.etat === 'annulee') {
                titreMobilite += ' (annulée)';
            }
            let nomCompletEtudiant = safeString(el.nomUtilisateur) + ' ' + safeString(el.prenomUtilisateur);
            const ligne = $('<tr>');
            ligne.append('<td>' + titreMobilite);
            ligne.append('<td>' + nomCompletEtudiant);
            ligne.append('<td>' + safeString(el.nomLegalPartenaire));
            ligne.append($('<td>').append(checkbox(listePaiements.paiements[i][0])));
            ligne.append($('<td>').append(checkbox(listePaiements.paiements[i][1])));
            tbody.append(ligne);
        });
    }

    function checkbox(coche) {
        const div = $('<div class="custom-control custom-checkbox">');
        const checkbox = $('<input type="checkbox" class="custom-control-input" disabled>');
        checkbox.prop('checked', coche);
        div.append(checkbox);
        div.append('<label class="custom-control-label">')
        return div;
    }
});