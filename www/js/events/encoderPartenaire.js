pageTab.pageEncoderPartenaire.setEvent(() => {
    const donnees = pageTab.pageEncoderPartenaire.getData();
    if (donnees === undefined) {
        pageTab.pageAccueil.changePage();
        return;
    }
    const idMobilite = donnees.idMobilite;
    const idPays = donnees.idPays;
    const quadrimestre = donnees.quadrimestre;
    const idProgramme = donnees.idProgramme;

    const form = $('#form-encoderPartenaire');
    const selectPays = form.find('#pays-encoderPartenaire');

    const formPartenaireExistant = $('#form-ajouterPartenaire');
    const selectPartenaire = formPartenaireExistant.find('.selectPart');

    programmeInfo[idProgramme].pays.forEach((el) => {
        let option = $('<option>');
        option.val(el.idPays);
        option.text(el.nomPays);
        selectPays.append(option);
    });

    if (idPays) {
        selectPays.val(idPays);
        selectPays.prop('disabled', true);
    }

    const modalEncoderPart = $('#alerteEncoderPart');
    const modalBody = modalEncoderPart.find('.modal-body');
    const alertSuccess = creerAlerte('Le partenaire a bien été ajouté et la mobilité confirmée.', 'alert-success');

    if (utilisateur.role !== 'ETUD') {
        if (idPays) {
            ajax('listerPartenairesParPays', JSON.stringify({
                idPays: idPays
            }), (data) => {
                remplirPartenaireExistant(data.liste);
            });
        } else {
            ajax('listerPartenaires', '', (data) => {
                remplirPartenaireExistant(data.liste);
            });
        }
    } else {
        formPartenaireExistant.hide();
    }

    function remplirPartenaireExistant(listePartenaires) {
        listePartenaires.forEach((el, i) => {
            if (listePays[el.idPays - 1].idProgramme === idProgramme) {
                let option = $('<option>');
                option.val(el.id);
                option.text(el.nomLegal);
                selectPartenaire.append(option);
            }
        });
    }

    const formConfirmerMobi = modalEncoderPart.find('#form-alerteEncoderPart');
    const selectQuadri = formConfirmerMobi.find('#alerteEncoderPart-quadri');

    formPartenaireExistant.submit((event) => {
        event.preventDefault();
        if (quadrimestre) {
            selectQuadri.val(quadrimestre);
        }
        formConfirmerMobi.submit((event) => {
            event.preventDefault();
            formConfirmerMobi.find('#btn-alerteEncoderPart-confirmer').hide();
            ajax('confirmerMobilite', JSON.stringify({
                idMobilite: Number(idMobilite),
                quadrimestre: selectQuadri.val(),
                idPartenaire: Number(selectPartenaire.val())
            }), (data) => {
                confirmerSuccess();
            }, (xhr, status, text) => {
                confirmerFail(xhr, status, text);
                modalEncoderPart.on('hide.bs.modal', () => {
                    pageTab.pageAccueil.changePage();
                });
            });
        });
        modalEncoderPart.modal();
    });

    form.submit((event) => {
        event.preventDefault();
        if (quadrimestre) {
            selectQuadri.val(quadrimestre);
        }
        formConfirmerMobi.submit((event) => {
            event.preventDefault();
            formConfirmerMobi.find('#btn-alerteEncoderPart-confirmer').hide();
            encoderPartenaire();
        });
        modalEncoderPart.modal();
    });

    function encoderPartenaire() {
        ajax('encoderPartenaire', JSON.stringify({
            nomLegal: form.find('#nomLegal-encoderPartenaire').val(),
            nomDaffaires: form.find('#nomAffaires-encoderPartenaire').val(),
            nomComplet: form.find('#nomComplet-encoderPartenaire').val(),
            departement: form.find('#departement-encoderPartenaire').val(),
            typeOrganisation: form.find('input[name=radioTypeOrganisation]:checked').val(),
            nombreEmployes: form.find('#nombreEmployes-encoderPartenaire').val(),
            adresse: form.find('#adresse-encoderPartenaire').val(),
            idPays: selectPays.val(),
            region: form.find('#region-encoderPartenaire').val(),
            codePostal: form.find('#codePostal-encoderPartenaire').val(),
            ville: form.find('#ville-encoderPartenaire').val(),
            email: form.find('#email-encoderPartenaire').val(),
            siteWeb: form.find('#site-encoderPartenaire').val(),
            telephone: form.find('#telephone-encoderPartenaire').val(),
            idMobilite: idMobilite,
            quadrimestre: selectQuadri.val()
        }), (data) => {
            confirmerSuccess(data);
        }, (xhr, status, text) => {
            confirmerFail(xhr, status, text);
        });
    }

    function confirmerSuccess(data) {
        modalBody.empty();
        modalBody.append(alertSuccess);
        modalEncoderPart.on('hide.bs.modal', () => {
            pageTab.pageAccueil.changePage();
        });
    }

    function confirmerFail(xhr, status, text) {
        modalBody.empty();
        let messageErreur = '';
        if (xhr.responseJSON && xhr.responseJSON.errorInfo) {
            messageErreur = xhr.responseJSON.errorInfo;
        } else {
            messageErreur = 'erreur inconnue';
        }
        modalBody.append(creerAlerte('Le partenaire n\'a pas pu être ajouté : ' + messageErreur, 'alert-warning'));
    }

    form.find('#btnAnnuler-encoderPartenaire').click((event) => {
        event.preventDefault();
        pageTab.pageAccueil.changePage();
    })
});