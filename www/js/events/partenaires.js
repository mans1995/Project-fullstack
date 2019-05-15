pageTab.pagePartenaires.setEvent(() => {

    let listePartenaires = [];

    function listerPartenaires() {
        ajax('listerPartenaires', '', (data) => {
            listePartenaires = data.liste;
            remplirRecherchePartenaires();
        });
    }

    listerPartenaires();
    $('#resetListePartenaires').click(listerPartenaires);

    function remplirRecherchePartenaires() {
        const tab = $('.tablePartenaires');
        remplirTableau(listePartenaires, tab, isProf);
    }

    function remplirTableau(lesPartenaires, tableau) {
        let tbodyTablePartenaire = tableau.find('tbody');
        tbodyTablePartenaire.empty();
        lesPartenaires.forEach((el) => {
            tbodyTablePartenaire.append('<tr>');
            tbodyTablePartenaire.append('<td>' + safeString(el.typeCode));
            tbodyTablePartenaire.append('<td>' + safeString(el.nomLegal));
            let localisation = safeString(el.ville);
            let region = safeString(el.region);
            if (region !== '') {
                localisation += ', ' + region;
            }
            localisation += ' (' + safeString(el.nomPays) + ')';
            tbodyTablePartenaire.append('<td>' + localisation);
            let email = safeString(el.email);
            tbodyTablePartenaire.append('<td>' + safeString(el.telephone) + ' / ' +
                '<a href="mailto:' + email + '">' + email + '</a></td>');
            let lienSiteWeb = safeString(el.siteWeb);
            tbodyTablePartenaire.append('<td><a href="' + lienSiteWeb + '">' + lienSiteWeb + '</a></td>');
        });
    }

    const selectRecherchePartenaire = $('select#pays-rechercherPartenaires');
    listePays.forEach((el, i) => {
        let option = $('<option>');
        option.val(el.idPays);
        option.text(el.nomPays);
        selectRecherchePartenaire.append(option);
    })

    $('#form-rechercherPartenairesParNom').submit((event) => {
        let json = JSON.stringify({
            nomLegal: $('#nomLegal-rechercherPartenaires').val()
        });
        eventRecherche(event, 'listerPartenairesParNomLegal', json);
    });

    $('#form-rechercherPartenairesParPays').submit((event) => {
        let json = JSON.stringify({
            idPays: selectRecherchePartenaire.val()
        });
        eventRecherche(event, 'listerPartenairesParPays', json);
    });

    $('#form-rechercherPartenairesParVille').submit((event) => {
        let json = JSON.stringify({
            ville: $('#ville-rechercherPartenaires').val()
        });
        eventRecherche(event, 'listerPartenairesParVille', json);
    });

    function eventRecherche(event, action, jsonRecherche) {
        event.preventDefault();
        ajax(action, jsonRecherche, (data) => {
            listePartenaires = data.liste;
            remplirRecherchePartenaires();
        });
    }
});