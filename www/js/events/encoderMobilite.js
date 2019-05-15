pageTab.pageEncoderMobilite.setEvent(function () {
    const isProf = utilisateur.role !== 'ETUD';
    const utilisateurAModifier = pageTab.pageEncoderMobilite.getData();
    if (isProf) {
        if (utilisateurAModifier === undefined) {
            pageTab.pageEtudiants.changePage();
            return;
        } else {
            $('#titre-encoderMobilite').append(' pour ' + utilisateurAModifier.nom + ' ' + utilisateurAModifier.prenom);
        }
    }

    let listePartenaires = [];
    const premiereMob = $('fieldset.mobilite:first');
    const mobilite = premiereMob.clone();
    updateNumMobilite(premiereMob, 1);

    ajax('listerPartenaires', '', (data) => {
        listePartenaires = data.liste;
        remplirChamps(premiereMob.parent().children('.mobilite').add(mobilite));
    });

    $('button#btn-ajouter-mob').click(() => ajouterChoix());

    function ajouterChoix() {
        let nbChoices = $('fieldset.mobilite').length + 1;
        let nMobilite = mobilite.clone();
        let buttonClose = $('<button type="button" class="btn btn-danger">Supprimer ce choix</button>');
        buttonClose.click(() => {
            nMobilite.remove();
            premiereMob.nextAll().each((i, el) => {
                updateNumMobilite($(el), i + 2);
            });
        });
        nMobilite.append(buttonClose);
        updateNumMobilite(nMobilite, nbChoices);
        setMobiliteEvents(nMobilite);
        nMobilite.hide();
        $('form#form-mobilite').append(nMobilite);
        nMobilite.slideDown();
    }

    function updateNumMobilite(mobilite, numMobilite) {
        mobilite.find('.titre-choix').text('Choix ' + numMobilite);
        mobilite.find('[id]').attr('id', (i, attr) => {
            return attr + numMobilite;
        });
        mobilite.find('[for]').attr('for', (i, attr) => {
            return attr + numMobilite;
        });
    }

    function onCodeChange(event) {
        const select = $(event.currentTarget);
        const mobilite = select.parentsUntil('.mobilite').parent();
        const selectPartenaire = mobilite.find('.partenaire');
        if (select.find(':selected').val() === 'SMP') {
            selectPartenaire.val('recherche');
            selectPartenaire.change();
            selectPartenaire.attr('disabled', true);
        } else {
            selectPartenaire.attr('disabled', false);
        }
    }

    function onPartChange(event) {
        const selectPartenaire = $(event.currentTarget);
        const mobilite = selectPartenaire.parentsUntil('.mobilite').parent();
        const groupPays = mobilite.find('div.groupPays');
        if (selectPartenaire.find(':selected').val() === 'recherche') {
            groupPays.show();
        } else {
            groupPays.hide();
        }
    }

    function onProgChange(event) {
        const selectProg = $(event.currentTarget);
        const mobilite = selectProg.parentsUntil('.mobilite').parent();
        const selectPays = mobilite.find('.selectPays');
        const selectPart = mobilite.find('.selectPart');
        selectPays.empty();
        selectPays.append('<option value="-1" selected>--- Non défini ---</option>');
        programmeInfo[selectProg.val()].pays.forEach((el) => {
            let option = $('<option>');
            option.val(el.idPays);
            option.text(el.nomPays);
            selectPays.append(option);
        });
        selectPart.empty();
        selectPart.append('<option value="recherche">--- Recherche entreprise ---</option>');
        programmeInfo[selectProg.val()].partenaires.forEach((el) => {
            let option = $('<option>');
            option.val(el.id);
            option.text(el.nomComplet + ' (' + listePays[el.idPays - 1].nomPays + '/' + el.ville + ')');
            selectPart.append(option);
        });
        selectPart.change();
    }

    function setMobiliteEvents(mobilite) {
        const selectMob = mobilite.find('.selectMob');
        const selectPart = mobilite.find('.selectPart');
        const selectProg = mobilite.find('.selectProg');
        selectMob.change(onCodeChange);
        selectPart.change(onPartChange);
        selectProg.change(onProgChange);
        selectMob.change();
        selectProg.change();
        selectPart.change();
    }

    function remplirChamps(mobilite) {
        const selectProg = mobilite.find('.selectProg');
        listeProgrammes.forEach((el) => {
            let option = $('<option>');
            option.val(el.id);
            option.text(el.libelle);
            selectProg.append(option);
        });
        programmeInfo.forEach((el) => {
            el.partenaires = [];
        })
        listePartenaires.forEach((el) => {
            if (el.typeCode === 'SMS') {
                let programmePartenaire = listePays[el.idPays - 1].idProgramme;
                programmeInfo[programmePartenaire].partenaires.push(el);
            }
        });
        setMobiliteEvents(premiereMob);
    }

    function envoyerMobilites(requetes) {
        ajax('encoderMobilite', JSON.stringify(requetes), () => {
            $('#form-mobilite').prepend(creerAlerte('Toutes les mobilités ont bien été encodées', 'alert-success'));
        }, (xhr, status, text) => {
            $('#form-mobilite').prepend(creerAlerte('Un des choix est incorrect, la demande n\'a pas pu être encodée', 'alert-danger'));
        });
    }

    $('button#btn-envoyer-mob').click((el) => {
        const mobilites = $('.mobilite');
        const requetes = [];
        mobilites.each((i, el) => {
            const jsonTab = {
                typeCode: $(el).find('.selectMob').val()
            };
            if (isProf) {
                jsonTab.idUtilisateur = utilisateurAModifier.id;
            }
            const idPartenaire = $(el).find('.selectPart').val();
            jsonTab.idProgramme = $(el).find('.selectProg').val();
            const quadrimestre = $(el).find('.selectQuadri').val();
            if (quadrimestre) {
                jsonTab.quadrimestre = quadrimestre;
            }
            if (idPartenaire !== 'recherche') {
                jsonTab.idPartenaire = idPartenaire;
            } else {
                jsonTab.idPays = $(el).find('.selectPays').val();
            }
            requetes.push(jsonTab);
        });
        envoyerMobilites(requetes);
    });
});