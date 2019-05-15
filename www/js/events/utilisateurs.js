pageTab.pageUtilisateurs.setEvent(() => {
    if (utilisateur.role !== 'RESP') {
        pageTab.pageAccueil.changePage();
    }

    let listeUtilisateurs = [];
    const table = $('table.utilisateurs-tableUtilisateurs');
    const tbody = table.find('tbody');

    function creerFormRole(nameRadio, idRadioEtud, idRadioProf, idUtilisateur, roleCourant) {
        const formRole = $('<form>');
        const radioEtud = $('<input class="custom-control-input" type="radio" value="ETUD">');
        radioEtud.attr('id', idRadioEtud);
        radioEtud.attr('name', nameRadio);
        const radioProf = $('<input class="custom-control-input" type="radio" value="PROF">');
        radioProf.attr('id', idRadioProf);
        radioProf.attr('name', nameRadio);
        if (roleCourant === 'ETUD') {
            radioEtud.prop('checked', true);
        } else {
            radioProf.prop('checked', true);
        }
        radioEtud.change(() => indiquerEtudiant(idUtilisateur, radioEtud, radioProf));
        radioProf.change(() => indiquerProfesseur(idUtilisateur, radioEtud, radioProf));
        $('<div class="custom-control custom-radio custom-control-inline">')
            .append(radioEtud)
            .append('<label class="custom-control-label" for="' + idRadioEtud + '">Etudiant</label>')
            .appendTo(formRole);
        $('<div class="custom-control custom-radio custom-control-inline">')
            .append(radioProf)
            .append('<label class="custom-control-label" for="' + idRadioProf + '">Professeur</label>')
            .appendTo(formRole);
        return formRole;
    }

    function indiquerProfesseur(idUtilisateur, radioEtud, radioProf) {
        ajax('indiquerProfesseur', JSON.stringify({
            id: idUtilisateur
        }), (data) => {
            ajouterAlerte('L\'utilisateur a bien été indiqué comme professeur.', 'alert-success');
        }, () => {
            ajouterAlerte('L\'utilisateur n\'a pas pu indiqué comme professeur.', 'alert-warning');
            radioEtud.prop('checked', true);
            radioProf.prop('checked', false);
        });
    }

    function indiquerEtudiant(idUtilisateur, radioEtud, radioProf) {
        ajax('indiquerEtudiant', JSON.stringify({
            id: idUtilisateur
        }), (data) => {
            ajouterAlerte('L\'utilisateur a bien été indiqué comme étudiant.', 'alert-success');
        }, () => {
            ajouterAlerte('L\'utilisateur n\'a pas pu être indiqué comme étudiant.', 'alert-warning');
            radioEtud.prop('checked', false);
            radioProf.prop('checked', true);
        });
    }

    function ajouterAlerte(message, typeAlerte) {
        supprimerAlertes();
        const alerte = creerAlerte(message, typeAlerte, true);
        table.before(alerte);
    }

    ajax('listerUtilisateurs', '', (data) => {
        listeUtilisateurs = data.etudiants;
        remplirTableUtilisateurs();
    });

    function remplirTableUtilisateurs() {
        listeUtilisateurs.forEach((el, i) => {
            if (el.role !== 'RESP') {
                const ligne = $('<tr>');
                ligne.append('<td>' + el.nom);
                ligne.append('<td>' + el.prenom);
                ligne.append('<td>' + el.pseudo);
                ligne.append('<td>' + el.email);
                const formRole = creerFormRole('utilisateurRole' + i, 'utilisateurEtud-' + i,
                    'utilisateurProf' + i, el.id, el.role);
                ligne.append($('<td>').append(formRole));
                tbody.append(ligne);
            }
        });
    }
});