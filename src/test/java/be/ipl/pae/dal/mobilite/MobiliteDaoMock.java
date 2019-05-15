package be.ipl.pae.dal.mobilite;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.mobilite.MobiliteDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.mobilite.MobiliteDao;
import be.ipl.pae.exceptions.FatalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MobiliteDaoMock implements MobiliteDao {

  @Inject
  BizFactory bizFactory;

  private boolean throwException = false;

  private Set<MobiliteDto> mobilites = new HashSet<>();


  public void setThrowException(boolean toSet) {
    this.throwException = toSet;
  }

  @Override
  public void ajouterMobilite(MobiliteDto mobiliteDto) {
    if (throwException) {
      throw new FatalException();
    }
    MobiliteDto mobilite = remplirMobilite(mobiliteDto, false, false);
    mobilites.add(mobilite);
  }

  @Override
  public void ajouterMobiliteAvecPays(MobiliteDto mobiliteDto) {
    if (throwException) {
      throw new FatalException();
    }
    MobiliteDto mobilite = remplirMobilite(mobiliteDto, false, true);
    mobilites.add(mobilite);
  }

  @Override
  public void ajouterMobiliteAvecPartenaire(MobiliteDto mobiliteDto) {
    if (throwException) {
      throw new FatalException();
    }
    MobiliteDto mobilite = remplirMobilite(mobiliteDto, true, false);
    mobilites.add(mobilite);
  }

  @Override
  public List<MobiliteDto> listerMobilites() {
    if (this.throwException) {
      throw new FatalException();
    }
    List<MobiliteDto> mobilitesReturn = mobilites.stream()
        .filter(mobilite -> !mobilite.getEtat().equals("demandee")).collect(Collectors.toList());
    return mobilitesReturn;
  }

  @Override
  public List<MobiliteDto> listerMobilitesParAnneeEtEtat(MobiliteDto mobiliteDto) {
    if (throwException) {
      throw new FatalException();
    }
    List<MobiliteDto> mesMobilites = new ArrayList<>();

    mobilites.forEach(mobilite -> {
      if (mobilite.getEtat().equals(mobiliteDto.getEtat())
          || mobilite.getAnneeAcademique().equals(mobiliteDto.getAnneeAcademique())) {
        mesMobilites.add(mobilite);
      }
    });
    return mesMobilites;
  }

  @Override
  public List<MobiliteDto> listerMobilitesParUtilisateur(int idUtilisateur) {
    if (throwException) {
      throw new FatalException();
    }
    return mobilites.stream().filter(mobilite -> mobilite.getIdUtilisateur() == idUtilisateur)
        .collect(Collectors.toList());
  }

  private MobiliteDto remplirMobilite(MobiliteDto mobiliteDto, boolean partenaire, boolean pays) {
    if (throwException) {
      throw new FatalException();
    }
    MobiliteDto mobilite = bizFactory.getMobiliteDto();
    if (partenaire) {
      mobilite.setIdPartenaire(mobiliteDto.getIdPartenaire());
    }
    if (pays) {
      mobilite.setIdPays(mobiliteDto.getIdPays());
    }
    mobilite.setIdMobilite(mobiliteDto.getIdMobilite());
    mobilite.setIdUtilisateur(mobiliteDto.getIdUtilisateur());
    mobilite.setPreference(mobiliteDto.getPreference());
    mobilite.setTypeCode(mobiliteDto.getTypeCode());
    mobilite.setEtat(mobiliteDto.getEtat());
    mobilite.setEtatAvantAnnulation(mobiliteDto.getEtatAvantAnnulation());
    mobilite.setRaisonAnnulation(mobiliteDto.getRaisonAnnulation());
    mobilite.setAnneeAcademique(mobiliteDto.getAnneeAcademique());
    mobilite.setDateIntroduction(mobiliteDto.getDateIntroduction());
    mobilite.setEncodeProeco(mobiliteDto.estEncodeProeco());
    mobilite.setEncodeMobi(mobiliteDto.estEncodeMobi());
    mobilite.setPreContraBourse(mobiliteDto.estSignePreContraBourse());
    mobilite.setPreConventionDeStage(mobiliteDto.estSignePreConventionDeStage());
    mobilite.setPreCharteEtudiant(mobiliteDto.estSignePreCharteEtudiant());
    mobilite.setPrePreuvePassageTestsLinguistiques(
        mobiliteDto.estSignePrePreuvePassageTestsLinguistiques());
    mobilite.setPreDocumentEngagement(mobiliteDto.estSignePreDocumentEngagement());
    mobilite.setPostAttestationSejour(mobiliteDto.estSignePostAttestationSejour());
    mobilite.setPostReleveNotes(mobiliteDto.estSignePostReleveNotes());
    mobilite.setPostCertificatStage(mobiliteDto.estSignePostCertificatStage());
    mobilite.setPostRapportFinalComplete(mobiliteDto.estSignePostRapportFinalComplete());
    mobilite.setPostPreuvePassageTestsLinguistiques(
        mobiliteDto.estSignePostPreuvePassageTestsLinguistiques());
    mobilite.setNoVersion(mobiliteDto.getNoVersion());
    return mobilite;
  }

  @Override
  public boolean supprimerMobilite(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    return mobilites.removeIf(mobi -> mobi.getIdMobilite() == (mobilite.getIdMobilite()));
  }

  @Override
  public List<MobiliteDto> listerDemandesDeMobilites() {
    if (throwException) {
      throw new FatalException();
    }
    List<MobiliteDto> mobilitesReturn = mobilites.stream()
        .filter(mobilite -> mobilite.getEtat().equals("demandee")).collect(Collectors.toList());
    return mobilitesReturn;
  }

  @Override
  public List<MobiliteDto> listerDemandesDeMobilitesParEtudiant(int idUtilisateur) {
    if (throwException) {
      throw new FatalException();
    }
    List<MobiliteDto> mobilitesReturn =
        mobilites.stream().filter(mobilite -> mobilite.getEtat().equals("demandee"))
            .filter(mobilite -> mobilite.getIdUtilisateur() == idUtilisateur)
            .collect(Collectors.toList());
    return mobilitesReturn;
  }

  @Override
  public boolean signerDocuments(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    for (MobiliteDto mobi : mobilites) {
      if (mobi.getIdMobilite() == mobilite.getIdMobilite()) {
        mobi.setEtat(mobilite.getEtat());
        // Pre documents.
        mobi.setPreCharteEtudiant(mobilite.estSignePreCharteEtudiant());
        mobi.setPreContraBourse(mobilite.estSignePreContraBourse());
        mobi.setPreConventionDeStage(mobilite.estSignePreConventionDeStage());
        mobi.setPreDocumentEngagement(mobilite.estSignePreDocumentEngagement());
        mobi.setPrePreuvePassageTestsLinguistiques(
            mobilite.estSignePrePreuvePassageTestsLinguistiques());
        // Post documents.
        mobi.setPostAttestationSejour(mobilite.estSignePostAttestationSejour());
        mobi.setPostCertificatStage(mobilite.estSignePostCertificatStage());
        mobi.setPostPreuvePassageTestsLinguistiques(
            mobilite.estSignePostPreuvePassageTestsLinguistiques());
        mobi.setPostRapportFinalComplete(mobilite.estSignePostRapportFinalComplete());
        mobi.setPostReleveNotes(mobilite.estSignePostReleveNotes());
      }
    }
    return true;
  }

  @Override
  public boolean confirmerMobilite(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    if (mobilite.getEtat().equals("demandee")) {
      mobilite.setEtat("creee");
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean annulerMobilite(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    if (mobilite.getEtat().equals("annulee")) {
      return false;
    }
    mobilite.setEtat("annulee");
    return true;
  }

  @Override
  public boolean indiquerEnvoiPremiereDemandePaiement(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    if (mobilite.valeurSuperEtatEstAttentePremierPaiement()) {
      mobilite.setEtat("premier paiement demande");
      return true;
    }
    return false;
  }

  @Override
  public boolean indiquerEnvoiSecondeDemandePaiement(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    if (mobilite.valeurEtatEstaPayerSolde()) {
      mobilite.setEtat("second paiement demande");
      return true;
    }
    return false;
  }

  @Override
  public Map<String, String> obtenirListeEtatsDocumentsEtMobilite(MobiliteDto mobiliteDto) {
    if (throwException) {
      throw new FatalException();
    }
    Map<String, String> etats = new HashMap<>();
    etats.put("preContratBourse", mobiliteDto.estSignePreContraBourse() ? "signe" : "nonSigne");
    etats.put("preConventionDeStageEtudes",
        mobiliteDto.estSignePreConventionDeStage() ? "signe" : "nonSigne");
    etats.put("preCharteEtudiant", mobiliteDto.estSignePreCharteEtudiant() ? "signe" : "nonSigne");
    etats.put("prePreuvePassageTestsLinguistiques",
        mobiliteDto.estSignePrePreuvePassageTestsLinguistiques() ? "signe" : "nonSigne");
    etats.put("preDocumentsEngagement",
        mobiliteDto.estSignePreDocumentEngagement() ? "signe" : "nonSigne");
    etats.put("postAttestationSejour",
        mobiliteDto.estSignePostAttestationSejour() ? "signe" : "nonSigne");
    etats.put("postReleveNotes",
        mobiliteDto.estSignePostAttestationSejour() ? "signe" : "nonSigne");
    etats.put("postCertificatStage",
        mobiliteDto.estSignePostAttestationSejour() ? "signe" : "nonSigne");
    etats.put("postRapportFinalComplete",
        mobiliteDto.estSignePostAttestationSejour() ? "signe" : "nonSigne");
    etats.put("postPreuvePassageTestsLinguistiques",
        mobiliteDto.estSignePostAttestationSejour() ? "signe" : "nonSigne");
    etats.put("encodeProeco", mobiliteDto.estEncodeProeco() ? "signe" : "nonSigne");
    etats.put("encodeMobi", mobiliteDto.estEncodeMobi() ? "signe" : "nonSigne");
    return etats;
  }

  @Override
  public MobiliteDto trouverMobiliteParId(int idMobilite) {
    if (throwException) {
      throw new FatalException();
    }
    for (MobiliteDto mobi : mobilites) {
      if (mobi.getIdMobilite() == idMobilite) {
        return mobi;
      }
    }
    return null;
  }

  @Override
  public void indiquerEtudiantEncodeDansLogiciels(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    for (MobiliteDto mobi : mobilites) {
      if (mobi.getIdMobilite() == mobilite.getIdMobilite()) {
        mobi.setEncodeMobi(mobilite.estEncodeMobi());
        mobi.setEncodeProeco(mobilite.estEncodeProeco());
      }
    }
  }

  @Override
  public Map<MobiliteDto, Boolean[]> listerDemandesPaiementsParAnneeAcademique(MobiliteDto mobilite,
      int idProgrammeSuisse) {
    if (throwException) {
      throw new FatalException();
    }
    Map<MobiliteDto, Boolean[]> mobilitesPaiements = new HashMap<>();
    mobilites.stream()
        .filter(mobi -> mobilite.getAnneeAcademique().equals(mobi.getAnneeAcademique()))
        .forEach(mobi -> {
          mobilitesPaiements.put(mobi, new Boolean[] {false, false});
        });
    mobilitesPaiements.entrySet().stream().forEach(entry -> {
      MobiliteDto mobiliteDtoRet = entry.getKey();
      if (mobiliteDtoRet.getIdProgramme() != idProgrammeSuisse) {
        boolean premierPaiementDemande = false;
        boolean secondPaiementDemande = false;
        if (mobiliteDtoRet.valeurEtatEstAnnulee()) {
          if (mobiliteDtoRet.valeurEtatAvantAnnulationEstPremierPaiementDemande()) {
            premierPaiementDemande = true;
          }
        } else {
          if (mobiliteDtoRet.valeurEtatEstaPayerSolde()
              || mobiliteDtoRet.valeurEtatEstPremierPaiementDemande()) {
            premierPaiementDemande = true;
          } else if (mobiliteDtoRet.valeurEtatEstSecondPaiementDemande()) {
            premierPaiementDemande = true;
            secondPaiementDemande = true;
          }
        }
        mobilitesPaiements.put(mobiliteDtoRet,
            new Boolean[] {premierPaiementDemande, secondPaiementDemande});
      }
    });
    return mobilitesPaiements;
  }

  @Override
  public boolean preciserReceptionDocumentsRetourMobilite(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    boolean signe = true;
    for (MobiliteDto mobi : mobilites) {
      if (mobi.getIdMobilite() == mobilite.getIdMobilite()) {
        if (!mobi.estSignePostAttestationSejour()) {
          signe = false;
        }
        if (!mobi.estSignePostCertificatStage()) {
          signe = false;
        }
        if (!mobi.estSignePostPreuvePassageTestsLinguistiques()) {
          signe = false;
        }
        if (!mobi.estSignePostRapportFinalComplete()) {
          signe = false;
        }
        if (!mobi.estSignePostReleveNotes()) {
          signe = false;
        }
      }
    }
    return signe;
  }

  @Override
  public boolean preciserDocumentsDepartMobiliteSignes(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    boolean signe = true;
    for (MobiliteDto mobi : mobilites) {
      if (mobi.getIdMobilite() == mobilite.getIdMobilite()) {
        if (!mobi.estSignePreCharteEtudiant()) {
          signe = false;
        }
        if (!mobi.estSignePreContraBourse()) {
          signe = false;
        }
        if (!mobi.estSignePreConventionDeStage()) {
          signe = false;
        }
        if (!mobi.estSignePreDocumentEngagement()) {
          signe = false;
        }
        if (!mobi.estSignePrePreuvePassageTestsLinguistiques()) {
          signe = false;
        }
      }
    }
    return signe;
  }

  @Override
  public boolean ajouterPartenaireMobiliteExistante(MobiliteDto mobilite) {
    if (throwException) {
      throw new FatalException();
    }
    mobilites.stream().filter(mobi -> mobi.getIdMobilite() == mobilite.getIdMobilite())
        .forEach(mobi -> mobi.setIdPartenaire(mobilite.getIdPartenaire()));
    return true;
  }

  @Override
  public Boolean[] obtenirDemandesPaiementsPourMobilite(MobiliteDto mobiliteDto) {
    if (throwException) {
      throw new FatalException();
    }
    boolean premierPaiementDemande = false;
    boolean secondPaiementDemande = false;

    if (mobiliteDto.valeurEtatEstAnnulee()) {
      if (mobiliteDto.valeurEtatAvantAnnulationEstPremierPaiementDemande()) {
        premierPaiementDemande = true;
      }
    } else {
      if (mobiliteDto.valeurEtatEstaPayerSolde()
          || mobiliteDto.valeurEtatEstPremierPaiementDemande()) {
        premierPaiementDemande = true;
      } else if (mobiliteDto.valeurEtatEstSecondPaiementDemande()) {
        premierPaiementDemande = true;
        secondPaiementDemande = true;
      }
    }
    return new Boolean[] {premierPaiementDemande, secondPaiementDemande};
  }

  @Override
  public int trouverNombreCandidatures(int idEtudiant, String anneeAcademique) {
    if (throwException) {
      throw new FatalException();
    }
    return (int) mobilites.stream().filter(mobi -> mobi.getIdUtilisateur() == idEtudiant
        && mobi.getAnneeAcademique().equals(anneeAcademique)).count();
  }

  @Override
  public int trouverIdProgrammeParLibelle(String libelle) {
    if (throwException) {
      throw new FatalException();
    }
    if ("Suisse".equals(libelle)) {
      return 4;
    } else {
      return 1;
    }
  }

  @Override
  public boolean confirmerMobiliteAvecPartenaire(MobiliteDto mobilite) {
    if (mobilite.getEtat().equals("demandee")) {
      mobilite.setEtat("creee");
      return true;
    } else {
      return false;
    }
  }

}
