pageTab.pageConnexion.setEvent(function () {

    $('button#btn-page-inscription').click(() => $('a#inscription-tab').click());
    $('button#btn-page-connexion').click(() => $('a#connexion-tab').click());

    //
    // Page connexion
    //
    $('form#form-connexion').submit((event) => {
        event.preventDefault();
        let formConnexion = $(event.delegateTarget);
        formConnexion.find('.alert').remove();
        let jsonTab = {
            pseudo: formConnexion.find('input#pseudo-connexion').val(),
            motDePasse: formConnexion.find('input#mdp-connexion').val()
        };

        ajax('login', JSON.stringify(jsonTab), (data) => {
            utilisateur = data.utilisateur;
            premierChargementConnecte(pageTab.pageAccueil);
        }, (xhr, status, text) => {
            let messageErreur = xhr.responseJSON === undefined ? xhr.statusText : xhr.responseJSON.errorInfo;
            if (!messageErreur) {
                messageErreur = xhr.statusText;
            }
            let alerte = creerAlerte('Erreur : ' + messageErreur, 'alert-warning');
            alerte.hide();
            formConnexion.find('div:first').before(alerte);
            alerte.slideDown();
        });

    });


    //
    //Page connexion (=> INSCRIPTION)
    //
    $('form#form-inscription').submit((event) => {
        event.preventDefault();
        let formInscription = $(event.delegateTarget);
        formInscription.find('.alert').remove();
        let formNom = formInscription.find('input#nom-inscription').val();
        let formPrenom = formInscription.find('input#prenom-inscription').val();
        let formPseudo = formInscription.find('input#pseudo-inscription').val();
        let formEmail = formInscription.find('input#email-inscription').val();
        let formMdp = formInscription.find('input#mdp-inscription').val();
        let formVerifMdp = formInscription.find('input#verifmdp-inscription').val();
        let alerte;
        if (!formMdp.match(formVerifMdp)) {
            alerte = creerAlerte('Erreur : les deux mots de passe ne concordent pas ', 'alert-warning');
            alerte.hide();
            formInscription.find('div:last').before(alerte);
            alerte.slideDown();
        } else {
            let jsonTab = {
                nom: formNom,
                prenom: formPrenom,
                pseudo: formPseudo,
                email: formEmail,
                motDePasse: formMdp,
            };

            ajax('signup', JSON.stringify(jsonTab), (data) => {
                $('a#connexion-tab').click();
                let alerte = creerAlerte('Inscription validée. Vous pouvez vous connecter', 'alert-success');
                $('form#form-connexion').find('.alert').remove();
                alerte.hide();
                $('form#form-connexion').find('div').first().before(alerte);
                alerte.slideDown();
            }, (xhr, status, text) => {
                let messageAlert = xhr.responseJSON.errorInfo === undefined ? xhr.statusText : xhr.responseJSON.errorInfo;
                let alertStyle = 'alert-warning';
                if (!messageAlert) {
                    messageAlert = xhr.statusText;
                }
                let alerte = creerAlerte(messageAlert, alertStyle);
                alerte.hide();
                formInscription.find('div:first').before(alerte);
                alerte.slideDown();

            });
        }
    });
});