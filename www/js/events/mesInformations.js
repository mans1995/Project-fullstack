pageTab.pageMesInformations.setEvent(function () {
    const isProf = utilisateur.role !== 'ETUD';
    const utilisateurAModifier = pageTab.pageMesInformations.getData();
    let utilisateurCourant;
    if (isProf) {
        if (utilisateurAModifier === undefined) {
            pageTab.pageEtudiants.changePage();
            return;
        } else {
            utilisateurCourant = utilisateurAModifier;
        }
    } else {
        utilisateurCourant = utilisateur;
    }

    const mesInfos = $('form#form-mesInformations');
    let numVersion = undefined;
    let infosUtilisateur;

    mesInfos.submit((event) => {
        event.preventDefault();
        let formMesInformations = $(event.delegateTarget);
        formMesInformations.find('.alert').remove();
        let formStatut = formMesInformations.find("input[name='radioStatut']:checked").val();
        let formNom = formMesInformations.find('input#nom-mesInformations').val();
        let formPrenom = formMesInformations.find('input#prenom-mesInformations').val();
        let formDateNaissance = formMesInformations.find('input#calendar-mesInformations').val();
        let formNationalite = formMesInformations.find('.nationalite-mesInscriptions option:selected').val();
        let formAdresse = formMesInformations.find('input#adresse-mesInformations').val();
        let formEmail = formMesInformations.find('input#email-mesInformations').val();
        let formTel = formMesInformations.find('input#telephone-mesInformations').val();
        let formSexe = formMesInformations.find("input[name='radioSexe']:checked").val();
        let formNbresAnneesReussies = formMesInformations.find('input#nbresAneesReussies-mesInformations').val();
        let formTitulaireCompte = formMesInformations.find('input#titulaireCompte-mesInformations').val();
        let formNomBanque = formMesInformations.find('input#nomBanque-mesInformations').val();
        let formIban = formMesInformations.find('input#iban-mesInformations').val();
        let formBic = formMesInformations.find('input#bic-mesInformations').val();
        let jsonTab = {
            statut: formStatut,
            nom: formNom,
            prenom: formPrenom,
            dateNaissance: formDateNaissance,
            nationalite: formNationalite,
            adresse: formAdresse,
            email: formEmail,
            telephone: formTel,
            sexe: formSexe,
            nbAnneesReussies: formNbresAnneesReussies,
            titulaireCompte: formTitulaireCompte,
            nomBanque: formNomBanque,
            iban: formIban,
            codeBic: formBic,
            noVersion: numVersion
        };
        if (isProf) {
            jsonTab.idUtilisateur = utilisateurAModifier.id;
        }
        ajax('introduireDonneesEtudiant', JSON.stringify(jsonTab), (data) => {
            let alerte = creerAlerte('Modification enregistrée.', 'alert-success');
            $('form#form-mesInformations').find('.alert').remove();
            alerte.hide();
            $('form#form-mesInformations').find('legend').first().after(alerte);
            alerte.slideDown();
        }, (xhr, status, text) => {
            let messageAlert = xhr.responseJSON.errorInfo === undefined ? xhr.statusText : xhr.responseJSON.errorInfo;
            let alertStyle = 'alert-warning';
            if (!messageAlert) {
                messageAlert = xhr.statusText;
            }
            let alerte = creerAlerte(messageAlert, alertStyle);
            alerte.hide();
            formMesInformations.find('legend:first').after(alerte);
            alerte.slideDown();

        });
    });

    function preremplirLesInfos(formMesInfos) {
        const selectNationalite = formMesInfos.find('.nationalite-mesInscriptions');
        let idPays = undefined;
        formMesInfos.find('#nomComplet-mesInformations').append(safeString(utilisateurCourant.nom) + ' ' + safeString(utilisateurCourant.prenom));
        formMesInfos.find('#pseudo-mesInformations').append(safeString(utilisateurCourant.pseudo));
        const email = safeString(utilisateurCourant.email);
        formMesInfos.find('#email-mesInformations').append('<a href="mailto:' + email + '">' + email);
        ajax('chargerDonneesEtudiant', JSON.stringify({
            idUtilisateur: utilisateurCourant.id
        }), (data) => {
            infosUtilisateur = data.infos;
            if (infosUtilisateur !== undefined) {
                formMesInfos.find("input[name='radioStatut'][value='" + infosUtilisateur.statut + "']").attr('checked', true);
                formMesInfos.find('input#nom-mesInformations').val(infosUtilisateur.nom);
                formMesInfos.find('input#prenom-mesInformations').val(infosUtilisateur.prenom);
                formMesInfos.find('input#calendar-mesInformations').val(infosUtilisateur.dateNaissance);
                idPays = infosUtilisateur.nationalite
                formMesInfos.find('select#nationalite-mesInformations').val(infosUtilisateur.nationalite);
                formMesInfos.find('input#adresse-mesInformations').val(infosUtilisateur.adresse);
                formMesInfos.find('input#email-mesInformations').val(infosUtilisateur.email);
                formMesInfos.find('input#telephone-mesInformations').val(infosUtilisateur.telephone);
                formMesInfos.find("input[name='radioSexe'][value='" + infosUtilisateur.sexe + "']").attr('checked', true);
                formMesInfos.find('input#nbresAneesReussies-mesInformations').val(infosUtilisateur.nbAnneesReussies);
                formMesInfos.find('input#titulaireCompte-mesInformations').val(infosUtilisateur.titulaireCompte);
                formMesInfos.find('input#nomBanque-mesInformations').val(infosUtilisateur.nomBanque);
                formMesInfos.find('input#iban-mesInformations').val(infosUtilisateur.iban);
                formMesInfos.find('input#bic-mesInformations').val(infosUtilisateur.codeBic);
                numVersion = infosUtilisateur.noVersion;
            }
        }, (xhr, status, text) => {
            if (infosUtilisateur === undefined) {
                numVersion = xhr.responseJSON.noVersion;
            }
        });
        remplirNationalitePaysAvecId(listePays, selectNationalite, idPays);


    }

    function remplirNationalitePaysAvecId(listePays, selectNationalite, idPays) {
        selectNationalite.empty();
        listePays.forEach((el) => {
            let option = $('<option>');
            option.val(el.idPays);
            if (idPays !== undefined && idPays === el.idPays)
                option.attr('selected', 'selected');
            option.text(el.nomPays);
            selectNationalite.append(option);
        });
    }

    preremplirLesInfos(mesInfos);
});