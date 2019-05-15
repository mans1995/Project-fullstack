navbarEvent = function () {
    //
    // Navbar
    //

    const btnAccueil = $('button#btn-accueil');
    btnAccueil.click(() => pageTab.pageAccueil.changePage());

    const btnMesInformations = $('button#btn-mesInformations');
    btnMesInformations.click(() => pageTab.pageMesInformations.changePage());

    const btnEncoderMobilite = $('button#btn-encoderMobilite');
    btnEncoderMobilite.click(() => pageTab.pageEncoderMobilite.changePage());

    const btnLesPartenaires = $('button#btn-lesPartenaires')
    btnLesPartenaires.click(() => pageTab.pagePartenaires.changePage());

    const btnLesMobilites = $('button#btn-lesMobilites')
    btnLesMobilites.click(() => pageTab.pageMobilites.changePage());

    const btnLesEtudiants = $('button#btn-lesEtudiants')
    btnLesEtudiants.click(() => pageTab.pageEtudiants.changePage());

    const btnLesUtilisateurs = $('button#btn-lesUtilisateurs')
    btnLesUtilisateurs.click(() => pageTab.pageUtilisateurs.changePage());

    const btnListeDesPaiements = $('button#btn-listeDesPaiements')
    btnListeDesPaiements.click(() => pageTab.pageListeDesPaiements.changePage());

    if (utilisateur.role !== 'ETUD') {
        btnEncoderMobilite.hide();
        btnMesInformations.hide();
        btnListeDesPaiements.show();
    } else {
        btnEncoderMobilite.show();
        btnMesInformations.show();
        btnListeDesPaiements.hide();
    }

    if (utilisateur.role !== 'RESP') {
        $('button#btn-lesUtilisateurs').hide();
    } else {
        $('button#btn-lesUtilisateurs').show();
    }

    $('button#btn-deconnexion').click(() => {
        ajax('logout', '',
            () => {
                pageTab.pageConnexion.changePage();
            }
        );
    });


};