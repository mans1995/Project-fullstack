pageTab.pageDocumentsMobilite.setEvent(() => {
    const mob = pageTab.pageDocumentsMobilite.getData();

    const pageDocMob = $('#page-documentsMobilite');
    const formDocuments = pageDocMob.find('#form-documents-documentsMobilite');
    if (mob === undefined) {
        pageTab.pageMobilites.changePage();
        return;
    }
    let listeDocuments;
    // mettre les documents ANTE dans la table json 
    // voir les priorités concernant le traitement d'un mobilité par rapport à un état    
    formDocuments.submit((event) => {
        event.preventDefault();
        let formCheckDocuments = $(event.delegateTarget);
        formCheckDocuments.find('.alert').remove();
        let contratBourse = formCheckDocuments.find("input[id='contraBourse-documentsMobilite']").is(':checked') ? true : false;
        let conventionStageEtude = formCheckDocuments.find("input[id='conventionStageEtude-documentsMobilite']").is(':checked') ? true : false;
        let charteEtudiant = formCheckDocuments.find("input[id='charteEtudiant-documentsMobilite']").is(':checked') ? true : false;
        let preuvePassageLinguistiqueAnte = formCheckDocuments.find("input[id='preuvePassageLinguistiqueAnte-documentsMobilite']").is(':checked') ? true : false;
        let documentsEngagement = formCheckDocuments.find("input[id='documentsEngagement-documentsMobilite']").is(':checked') ? true : false;
        let attestationSejour = formCheckDocuments.find("input[id='attestationSejour-documentsMobilite']").is(':checked') ? true : false;
        let releveNote = formCheckDocuments.find("input[id='releveNotes-documentsMobilite']").is(':checked') ? true : false;
        let certificatStage = formCheckDocuments.find("input[id='certificatStage-documentsMobilite']").is(':checked') ? true : false;
        let rapportFinalCompleteEnLigne = formCheckDocuments.find("input[id='rapportFinalCompleteEnLigne-documentsMobilite']").is(':checked') ? true : false;
        let preuvePassageLinguistiquePost = formCheckDocuments.find("input[id='preuvePassageLinguistiquePost-documentsMobilite']").is(':checked') ? true : false;
        let jsonTab = {
            idMobilite: mob.idMobilite,
            preContraBourse: contratBourse,
            preConventionDeStage: conventionStageEtude,
            preCharteEtudiant: charteEtudiant,
            prePreuvePassageTestsLinguistiques: preuvePassageLinguistiqueAnte,
            preDocumentEngagement: documentsEngagement,
            postAttestationSejour: attestationSejour,
            postReleveNotes: releveNote,
            postCertificatStage: certificatStage,
            postRapportFinalComplete: rapportFinalCompleteEnLigne,
            postPreuvePassageTestsLinguistiques: preuvePassageLinguistiquePost
        };
        supprimerAlertes();
        ajax('signerDocuments', JSON.stringify(jsonTab), (data) => {
            let alerteDepart = creerAlerte('document(s) au départ enregistré(s).', 'alert-success');
            let alerteRetour = creerAlerte('document(s) au retour enregistré(s).', 'alert-success');
            formCheckDocuments.find('.documentsASignerDepart .alert').remove();
            formCheckDocuments.find('.documentsASignerRetour .alert').remove();
            alerteDepart.hide();
            alerteRetour.hide();
            formCheckDocuments.find('.documentsASignerDepart').after(alerteDepart);
            formCheckDocuments.find('.documentsASignerRetour').after(alerteRetour);
            alerteDepart.slideDown();
            alerteRetour.slideDown();
            let proEco = formCheckDocuments.find("input[id='proEco-documentsMobilite']").is(':checked') ? true : false;
            let moby = formCheckDocuments.find("input[id='moby-documentsMobilite']").is(':checked') ? true : false;
            let jsonTabBis = {
                idMobilite: mob.idMobilite,
                encodeProeco: proEco,
                encodeMobi: moby
            }
            ajax('indiquerEtudiantEncodeDansLogiciels', JSON.stringify(jsonTabBis), (data) => {
                let alerte = creerAlerte('Logiciel(s) Externe(s) encodé(s).', 'alert-success');
                formCheckDocuments.find('.logicielsExternes .alert').remove();
                alerte.hide();
                formCheckDocuments.find('.logicielsExternes').after(alerte);
                alerte.slideDown();
                if (formCheckDocuments.find("input[id='premierPaiement-documentsMobilite']").is(':checked')) {
                    ajax('indiquerEnvoiPremiereDemandePaiement', JSON.stringify({
                        "idMobilite": mob.idMobilite,
                        "etat": mob.etat
                    }), (data) => {
                        let alerte = creerAlerte('paiement(s) enregistré(s).', 'alert-success');
                        formCheckDocuments.find('.demandesPaiement .alert').remove();
                        alerte.hide();
                        formCheckDocuments.find('.demandesPaiement').after(alerte);
                        alerte.slideDown();
                        if (formCheckDocuments.find("input[id='deuxiemePaiement-documentsMobilite']").is(':checked')) {
                            ajax('indiquerEnvoiSecondeDemandePaiement', JSON.stringify({
                                "idMobilite": mob.idMobilite,
                                "etat": mob.etat
                            }), (data) => {
                                let alerte = creerAlerte('paiement(s) enregistré(s).', 'alert-success');
                                formCheckDocuments.find('.demandesPaiement .alert').remove();
                                alerte.hide();
                                formCheckDocuments.find('.demandesPaiement').after(alerte);
                                alerte.slideDown();
                            });
                        }

                    });
                }
            });
        });

    });

    function preremplirlesDocuments() {


        pageDocMob.find('.enteteH1').append(mob.libelleProgramme + " " + mob.typeCode + " " + mob.nomLegalPartenaire);
        pageDocMob.find('.infosEtudiant').append(mob.nomUtilisateur + " " + mob.prenomUtilisateur);
        pageDocMob.find('.etatActuel').append(mob.etat);
        // griser les differents input PremierPaiment si creee , en preparation , a payer 
        if (mob.etat === "demandee") {
            formDocuments.find("input[id='premierPaiement-documentsMobilite']").prop('disabled', true);
        }
        // DeuxiemePaiement si a payer les soldes 
        if (mob.etat !== "a payer solde") {
            formDocuments.find("input[id='deuxiemePaiement-documentsMobilite']").prop('disabled', true);
        }
        // griser les DocumentsRetour si documentsDepart pas encore completer --> avec preciserDocumentsDepartSigne
        ajax('preciserDocumentsDepartMobiliteSignes', JSON.stringify({
            idMobilite: mob.idMobilite
        }), (data) => {
            if (data.etat !== undefined && data.etat === "nonSigne") {
                formDocuments.find("input[id='attestationSejour-documentsMobilite']").prop('disabled', true);
                formDocuments.find("input[id='releveNotes-documentsMobilite']").prop('disabled', true);
                formDocuments.find("input[id='rapportFinalCompleteEnLigne-documentsMobilite']").prop('disabled', true);
                formDocuments.find("input[id='certificatStage-documentsMobilite']").prop('disabled', true);
                formDocuments.find("input[id='preuvePassageLinguistiquePost-documentsMobilite']").prop('disabled', true);
            }
        })

        ajax('obtenirListeEtatsDocumentsEtMobilite', JSON.stringify({
            idMobilite: mob.idMobilite
        }), (data) => {
            listeDocuments = data.etats;
            if (listeDocuments !== undefined) {
                if (listeDocuments.preContratBourse === "signe") {
                    formDocuments.find("input[id='contraBourse-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.preConventionDeStageEtudes === "signe") {
                    formDocuments.find("input[id='conventionStageEtude-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.preCharteEtudiant === "signe") {
                    formDocuments.find("input[id='charteEtudiant-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.prePreuvePassageTestsLinguistiques === "signe") {
                    formDocuments.find("input[id='preuvePassageLinguistiqueAnte-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.preDocumentsEngagement === "signe") {
                    formDocuments.find("input[id='documentsEngagement-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.etatMobilite === "premier paiement demande" || listeDocuments.etatMobilite === "a payer solde" || listeDocuments.etatMobilite === "second paiement demande") {
                    formDocuments.find("input[id='premierPaiement-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.etatMobilite === "second paiement demande") {
                    formDocuments.find("input[id='deuxiemePaiement-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.encodeProeco === "signe") {
                    formDocuments.find("input[id='proEco-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.encodeMobi === "signe") {
                    formDocuments.find("input[id='moby-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.postAttestationSejour === "signe") {
                    formDocuments.find("input[id='attestationSejour-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.postReleveNotes === "signe") {
                    formDocuments.find("input[id='releveNotes-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.postRapportFinalComplete === "signe") {
                    formDocuments.find("input[id='rapportFinalCompleteEnLigne-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.postCertificatStage === "signe") {
                    formDocuments.find("input[id='certificatStage-documentsMobilite']").prop('checked', true);
                }
                if (listeDocuments.postPreuvePassageTestsLinguistiques === "signe") {
                    formDocuments.find("input[id='preuvePassageLinguistiquePost-documentsMobilite']").prop('checked', true);
                }
            }
        }, (xhr, status, text) => {
            if (infosUtilisateur === undefined) {
                numVersion = xhr.responseJSON.noVersion;
            }
        });
        if(mob.libelleProgramme === "Suisse"){
            formDocuments.find("input[id='contraBourse-documentsMobilite']").hide();
            formDocuments.find("label[for='contraBourse-documentsMobilite']").hide();
            formDocuments.find(".demandesPaiement").hide();
        }
    }
    preremplirlesDocuments(pageDocMob);

});