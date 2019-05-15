package be.ipl.pae.dal.mobilite;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.mobilite.MobiliteDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.DalBackendServices;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.FatalException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobiliteDaoImpl implements MobiliteDao {
  @Inject
  private DalBackendServices dalBackendServices;
  @Inject
  private BizFactory bizFactory;

  private final String baseQuerySelectMobilite =
      "SELECT m.id_mobilite, m.id_pays, pa.nom_pays, pa.code_pays,"
          + " m.id_partenaire, p.nom_legal, m.id_utilisateur, u.nom, u.prenom, u.email,"
          + " m.type_code, m.etat, m.etat_avant_annulation,"
          + " m.raison_annulation, m.annee_academique, m.date_introduction,"
          + " m.encode_proeco, m.encode_mobi, m.pre_contrat_bourse,"
          + " m.pre_convention_de_stage_etudes, m.pre_charte_etudiant,"
          + " m.pre_preuve_passage_tests_linguistiques, m.pre_documents_engagement,"
          + " m.post_attestation_sejour, m.post_releve_notes, m.post_certificat_stage,"
          + " m.post_rapport_final_complete, m.post_preuve_passage_tests_linguistiques,"
          + " pr.libelle, m.no_candidature, m.quadrimestre, p.departement,"
          + " m.id_programme, m.no_version FROM"
          + " paeMobEras.mobilites m LEFT OUTER JOIN paeMobEras.pays pa ON m.id_pays=pa.id_pays"
          + " LEFT OUTER JOIN paeMobEras.programmes pr ON m.id_programme=pr.id_programme"
          + " LEFT OUTER JOIN paeMobEras.partenaires p ON m.id_partenaire=p.id_partenaire"
          + " INNER JOIN paeMobEras.utilisateurs u ON m.id_utilisateur=u.id_utilisateur";

  @Override
  public void ajouterMobilite(MobiliteDto mobiliteDto) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("INSERT INTO paeMobEras.mobilites(id_utilisateur, type_code, etat,"
            + " annee_academique, date_introduction, encode_proeco, encode_mobi,"
            + " pre_contrat_bourse, pre_convention_de_stage_etudes, pre_charte_etudiant,"
            + " pre_documents_engagement, post_attestation_sejour, post_rapport_final_complete,"
            + " id_programme, no_candidature, quadrimestre, no_version) "
            + " VALUES(?, ?, ?, ?, ?, ?, ?, false, false, false, false,"
            + " false, false, ?, ?, ?, 1);")) {
      ps.setInt(1, mobiliteDto.getIdUtilisateur());
      ps.setString(2, mobiliteDto.getTypeCode());
      ps.setString(3, mobiliteDto.getEtat());
      ps.setString(4, mobiliteDto.getAnneeAcademique());
      Timestamp date = Timestamp.valueOf(mobiliteDto.getDateIntroduction().atStartOfDay());
      ps.setTimestamp(5, date);
      ps.setBoolean(6, mobiliteDto.estEncodeProeco());
      ps.setBoolean(7, mobiliteDto.estEncodeMobi());
      ps.setInt(8, mobiliteDto.getIdProgramme());
      ps.setInt(9, mobiliteDto.getNoCandidature());
      ps.setString(10, mobiliteDto.getQuadrimestre());
      ps.executeUpdate();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage(), exception);
    }

  }

  @Override
  public void ajouterMobiliteAvecPays(MobiliteDto mobiliteDto) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "INSERT INTO paeMobEras.mobilites(id_pays, id_utilisateur, type_code, etat,"
            + " annee_academique, date_introduction, encode_proeco, encode_mobi,"
            + " pre_contrat_bourse, pre_convention_de_stage_etudes, pre_charte_etudiant,"
            + " pre_documents_engagement, post_attestation_sejour, post_rapport_final_complete,"
            + " id_programme, no_candidature, quadrimestre, no_version) "
            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, false, false, false, false, false, false, "
            + " (SELECT pa.id_programme FROM paeMobEras.pays pa WHERE pa.id_pays = ?),"
            + " ?, ?, 1);")) {
      ps.setInt(1, mobiliteDto.getIdPays());
      ps.setInt(2, mobiliteDto.getIdUtilisateur());
      ps.setString(3, mobiliteDto.getTypeCode());
      ps.setString(4, mobiliteDto.getEtat());
      ps.setString(5, mobiliteDto.getAnneeAcademique());
      Timestamp date = Timestamp.valueOf(mobiliteDto.getDateIntroduction().atStartOfDay());
      ps.setTimestamp(6, date);
      ps.setBoolean(7, mobiliteDto.estEncodeProeco());
      ps.setBoolean(8, mobiliteDto.estEncodeMobi());
      ps.setInt(9, mobiliteDto.getIdPays());
      ps.setInt(10, mobiliteDto.getNoCandidature());
      ps.setString(11, mobiliteDto.getQuadrimestre());
      ps.executeUpdate();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage(), exception);
    }

  }

  @Override
  public void ajouterMobiliteAvecPartenaire(MobiliteDto mobiliteDto) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "INSERT INTO paeMobEras.mobilites(id_partenaire, id_utilisateur, type_code,"
            + " etat, annee_academique, date_introduction, encode_proeco, encode_mobi,"
            + " pre_contrat_bourse, pre_convention_de_stage_etudes, pre_charte_etudiant,"
            + " pre_documents_engagement, post_attestation_sejour,"
            + " post_rapport_final_complete, id_pays, id_programme, "
            + " no_candidature, quadrimestre, no_version)"
            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, false, false, false, false, false, false,"
            + " (SELECT p.pays FROM paeMobEras.partenaires p WHERE p.id_partenaire = ?),"
            + " (SELECT pa.id_programme FROM paeMobEras.partenaires p, paeMobEras.pays pa"
            + "  WHERE p.id_partenaire = ? AND p.pays = pa.id_pays), ?, ?, 1);")) {
      ps.setInt(1, mobiliteDto.getIdPartenaire());
      ps.setInt(2, mobiliteDto.getIdUtilisateur());
      ps.setString(3, mobiliteDto.getTypeCode());
      ps.setString(4, mobiliteDto.getEtat());
      ps.setString(5, mobiliteDto.getAnneeAcademique());
      Timestamp date = Timestamp.valueOf(mobiliteDto.getDateIntroduction().atStartOfDay());
      ps.setTimestamp(6, date);
      ps.setBoolean(7, mobiliteDto.estEncodeProeco());
      ps.setBoolean(8, mobiliteDto.estEncodeMobi());
      ps.setInt(9, mobiliteDto.getIdPartenaire());
      ps.setInt(10, mobiliteDto.getIdPartenaire());
      ps.setInt(11, mobiliteDto.getNoCandidature());
      ps.setString(12, mobiliteDto.getQuadrimestre());
      ps.executeUpdate();
    } catch (SQLException exception) {
      throw new FatalException(exception.getMessage(), exception);
    }
  }

  @Override
  public List<MobiliteDto> listerMobilites() {
    List<MobiliteDto> mobilites = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        baseQuerySelectMobilite + " WHERE m.etat <> 'demandee' ORDER BY m.id_mobilite;")) {
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          MobiliteDto mobilite = remplirMobilite(rs);
          mobilites.add(mobilite);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return mobilites;
  }

  @Override
  public List<MobiliteDto> listerMobilitesParAnneeEtEtat(MobiliteDto mobiliteDto) {
    List<MobiliteDto> mobilites = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        baseQuerySelectMobilite + " WHERE m.etat <> 'demandee' AND (? IS NULL OR m.etat = ?) "
            + "AND (? IS NULL OR m.annee_academique = ?) ORDER BY m.id_mobilite;")) {
      String etat = mobiliteDto.getEtat();
      String anneeAcademique = mobiliteDto.getAnneeAcademique();
      ps.setString(1, etat);
      ps.setString(2, etat);
      ps.setString(3, anneeAcademique);
      ps.setString(4, anneeAcademique);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          MobiliteDto mobilite = remplirMobilite(rs);
          mobilites.add(mobilite);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return mobilites;
  }

  @Override
  public List<MobiliteDto> listerMobilitesParUtilisateur(int idUtilisateur) {
    List<MobiliteDto> mobilites = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(baseQuerySelectMobilite
        + " WHERE m.id_utilisateur = ? AND m.etat <> 'demandee'" + " ORDER BY m.id_mobilite")) {
      ps.setInt(1, idUtilisateur);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          MobiliteDto mobilite = remplirMobilite(rs);
          mobilites.add(mobilite);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return mobilites;
  }

  @Override
  public boolean supprimerMobilite(MobiliteDto mobilite) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("DELETE FROM paeMobEras.mobilites WHERE id_mobilite = ?")) {
      ps.setInt(1, mobilite.getIdMobilite());
      return ps.executeUpdate() == 1;
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }

  }

  @Override
  public boolean ajouterPartenaireMobiliteExistante(MobiliteDto mobilite) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("UPDATE paeMobEras.mobilites" + " SET id_partenaire = ?, id_pays = ?"
            + " WHERE id_mobilite = ? AND id_partenaire IS NULL")) {
      ps.setInt(1, mobilite.getIdPartenaire());
      ps.setInt(2, mobilite.getIdPays());
      ps.setInt(3, mobilite.getIdMobilite());
      return ps.executeUpdate() > 0;
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public boolean confirmerMobilite(MobiliteDto mobilite) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("UPDATE paeMobEras.mobilites SET etat = 'creee', quadrimestre = ?"
            + " WHERE id_mobilite = ? AND etat = 'demandee' AND id_partenaire IS NOT NULL")) {
      ps.setString(1, mobilite.getQuadrimestre());
      ps.setInt(2, mobilite.getIdMobilite());
      return ps.executeUpdate() > 0;
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public boolean confirmerMobiliteAvecPartenaire(MobiliteDto mobilite) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("UPDATE paeMobEras.mobilites m SET etat = 'creee', quadrimestre = ?, "
            + " id_partenaire = p.id_partenaire, id_pays = p.pays FROM paeMobEras.partenaires p"
            + " WHERE p.id_partenaire = ? AND m.id_mobilite = ? AND etat = 'demandee'"
            + "  AND (p.pays = m.id_pays OR m.id_pays IS NULL)")) {
      ps.setString(1, mobilite.getQuadrimestre());
      ps.setInt(2, mobilite.getIdPartenaire());
      ps.setInt(3, mobilite.getIdMobilite());
      return ps.executeUpdate() > 0;
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public boolean annulerMobilite(MobiliteDto mobilite) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement("UPDATE paeMobEras.mobilites SET raison_annulation = ?, "
            + "etat_avant_annulation = etat, etat = 'annulee' WHERE id_mobilite = ? ")) {
      ps.setString(1, mobilite.getRaisonAnnulation());
      ps.setInt(2, mobilite.getIdMobilite());
      return ps.executeUpdate() > 0;
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public boolean signerDocuments(MobiliteDto mobilite) {

    try (PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("UPDATE paeMobEras.mobilites"
            + " SET etat = ?, post_attestation_sejour = ?, post_releve_notes = ?,"
            + " post_certificat_stage = ?, post_rapport_final_complete = ?,"
            + " post_preuve_passage_tests_linguistiques = ?,"
            + " pre_contrat_bourse = ?, pre_convention_de_stage_etudes = ?,"
            + " pre_charte_etudiant = ?, pre_preuve_passage_tests_linguistiques = ?,"
            + " pre_documents_engagement = ? WHERE id_mobilite = ?")) {
      ps.setString(1, mobilite.getEtat());
      ps.setBoolean(2, mobilite.estSignePostAttestationSejour());
      ps.setBoolean(3, mobilite.estSignePostReleveNotes());
      ps.setBoolean(4, mobilite.estSignePostCertificatStage());
      ps.setBoolean(5, mobilite.estSignePostRapportFinalComplete());
      ps.setBoolean(6, mobilite.estSignePostPreuvePassageTestsLinguistiques());
      ps.setBoolean(7, mobilite.estSignePreContraBourse());
      ps.setBoolean(8, mobilite.estSignePreConventionDeStage());
      ps.setBoolean(9, mobilite.estSignePreCharteEtudiant());
      ps.setBoolean(10, mobilite.estSignePrePreuvePassageTestsLinguistiques());
      ps.setBoolean(11, mobilite.estSignePreDocumentEngagement());
      ps.setInt(12, mobilite.getIdMobilite());

      return ps.executeUpdate() > 0;
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public List<MobiliteDto> listerDemandesDeMobilites() {
    List<MobiliteDto> mobilites = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(baseQuerySelectMobilite
        + " WHERE m.etat = ? ORDER BY m.id_utilisateur, m.no_candidature, m.id_mobilite")) {
      ps.setString(1, "demandee");
      try (ResultSet rs = ps.executeQuery()) {
        int nbDemandes = 0;
        int dernierIdUtilisateur = 0;
        int candidature = -1;
        while (rs.next()) {
          MobiliteDto mobilite = remplirMobilite(rs);
          if (mobilite.getNoCandidature() != candidature) {
            nbDemandes = 0;
            candidature = mobilite.getNoCandidature();
          }
          if (mobilite.getIdUtilisateur() != dernierIdUtilisateur) {
            nbDemandes = 0;
            dernierIdUtilisateur = mobilite.getIdUtilisateur();
          }
          mobilite.setPreference(++nbDemandes);
          mobilites.add(mobilite);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return mobilites;
  }

  @Override
  public List<MobiliteDto> listerDemandesDeMobilitesParEtudiant(int idUtilisateur) {
    List<MobiliteDto> mobilites = new ArrayList<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(baseQuerySelectMobilite
        + " WHERE m.id_utilisateur = ? AND m.etat = ? ORDER BY m.no_candidature, m.id_mobilite")) {
      ps.setInt(1, idUtilisateur);
      ps.setString(2, "demandee");
      try (ResultSet rs = ps.executeQuery()) {
        int nbDemandes = 0;
        int candidature = -1;
        while (rs.next()) {
          MobiliteDto mobilite = remplirMobilite(rs);
          if (mobilite.getNoCandidature() != candidature) {
            candidature = mobilite.getNoCandidature();
            nbDemandes = 0;
          }
          mobilite.setPreference(++nbDemandes);
          mobilites.add(mobilite);
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return mobilites;
  }

  private MobiliteDto remplirMobilite(ResultSet rs) throws SQLException {
    MobiliteDto mobiliteDto = bizFactory.getMobiliteDto();
    mobiliteDto.setIdMobilite(rs.getInt(1));
    mobiliteDto.setIdPays(rs.getInt(2));
    mobiliteDto.setNomPays(rs.getString(3));
    mobiliteDto.setCodePays(rs.getString(4));
    mobiliteDto.setIdPartenaire(rs.getInt(5));
    mobiliteDto.setNomLegalPartenaire(rs.getString(6));
    mobiliteDto.setIdUtilisateur(rs.getInt(7));
    mobiliteDto.setNomUtilisateur(rs.getString(8));
    mobiliteDto.setPrenomUtilisateur(rs.getString(9));
    mobiliteDto.setEmailUtilisateur(rs.getString(10));
    mobiliteDto.setTypeCode(rs.getString(11));
    mobiliteDto.setEtat(rs.getString(12));
    mobiliteDto.setEtatAvantAnnulation(rs.getString(13));
    mobiliteDto.setRaisonAnnulation(rs.getString(14));
    mobiliteDto.setAnneeAcademique(rs.getString(15));
    mobiliteDto.setDateIntroduction(rs.getTimestamp(16).toLocalDateTime().toLocalDate());
    mobiliteDto.setEncodeProeco(rs.getBoolean(17));
    mobiliteDto.setEncodeMobi(rs.getBoolean(18));
    mobiliteDto.setPreContraBourse(rs.getBoolean(19));
    mobiliteDto.setPreConventionDeStage(rs.getBoolean(20));
    mobiliteDto.setPreCharteEtudiant(rs.getBoolean(21));
    mobiliteDto.setPrePreuvePassageTestsLinguistiques(rs.getBoolean(22));
    mobiliteDto.setPreDocumentEngagement(rs.getBoolean(23));
    mobiliteDto.setPostAttestationSejour(rs.getBoolean(24));
    mobiliteDto.setPostReleveNotes(rs.getBoolean(25));
    mobiliteDto.setPostCertificatStage(rs.getBoolean(26));
    mobiliteDto.setPostRapportFinalComplete(rs.getBoolean(27));
    mobiliteDto.setPostPreuvePassageTestsLinguistiques(rs.getBoolean(28));
    mobiliteDto.setLibelleProgramme(rs.getString(29));
    mobiliteDto.setNoCandidature(rs.getInt(30));
    mobiliteDto.setQuadrimestre(rs.getString(31));
    mobiliteDto.setDepartement(rs.getString(32));
    mobiliteDto.setIdProgramme(rs.getInt(33));
    mobiliteDto.setNoVersion(rs.getInt(34));
    return mobiliteDto;
  }

  @Override
  public boolean indiquerEnvoiPremiereDemandePaiement(MobiliteDto mobiliteDto) {
    if (mobiliteDto.valeurSuperEtatEstAttentePremierPaiement()) {
      try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
          "UPDATE paeMobEras.mobilites" + " SET etat = ? WHERE id_mobilite = ? ")) {
        ps.setString(1, "premier paiement demande");
        ps.setInt(2, mobiliteDto.getIdMobilite());
        return ps.executeUpdate() > 0;
      } catch (SQLException exc) {
        throw new FatalException(exc.getMessage());
      }
    } else {
      return false;
    }
  }

  @Override
  public boolean indiquerEnvoiSecondeDemandePaiement(MobiliteDto mobiliteDto) {
    if (mobiliteDto.valeurEtatEstaPayerSolde()) {
      try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
          "UPDATE paeMobEras.mobilites" + " SET etat = ? WHERE id_mobilite = ? ")) {
        ps.setString(1, "second paiement demande");
        ps.setInt(2, mobiliteDto.getIdMobilite());
        return ps.executeUpdate() > 0;
      } catch (SQLException exc) {
        throw new FatalException(exc.getMessage());
      }
    } else {
      return false;
    }
  }

  @Override
  public Map<String, String> obtenirListeEtatsDocumentsEtMobilite(MobiliteDto mobiliteDto) {
    Map<String, String> etats = new HashMap<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT pre_contrat_bourse, pre_convention_de_stage_etudes, pre_charte_etudiant, "
            + "pre_preuve_passage_tests_linguistiques, pre_documents_engagement, "
            + "post_attestation_sejour, post_releve_notes, post_certificat_stage, "
            + "post_rapport_final_complete, post_preuve_passage_tests_linguistiques, "
            + "encode_proeco, encode_mobi, "
            + "etat FROM paeMobEras.mobilites WHERE id_mobilite = ?")) {
      ps.setInt(1, mobiliteDto.getIdMobilite());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          etats.put("preContratBourse", rs.getBoolean(1) ? "signe" : "nonSigne");
          etats.put("preConventionDeStageEtudes", rs.getBoolean(2) ? "signe" : "nonSigne");
          etats.put("preCharteEtudiant", rs.getBoolean(3) ? "signe" : "nonSigne");
          etats.put("prePreuvePassageTestsLinguistiques", rs.getBoolean(4) ? "signe" : "nonSigne");
          etats.put("preDocumentsEngagement", rs.getBoolean(5) ? "signe" : "nonSigne");
          etats.put("postAttestationSejour", rs.getBoolean(6) ? "signe" : "nonSigne");
          // https://coderanch.com/t/306777/databases/retrieving-null-values-ResultSet
          boolean postReleveNotes = rs.getBoolean(7); // peut être NULL dans la DB
          if (!rs.wasNull()) {
            etats.put("postReleveNotes", postReleveNotes ? "signe" : "nonSigne");
          }
          etats.put("postCertificatStage", rs.getBoolean(8) ? "signe" : "nonSigne");
          // https://coderanch.com/t/306777/databases/retrieving-null-values-ResultSet
          boolean postRapportFinalComplete = rs.getBoolean(9); // peut être NULL dans las DB
          if (!rs.wasNull()) {
            etats.put("postRapportFinalComplete", postRapportFinalComplete ? "signe" : "nonSigne");
          }
          boolean postPreuvePassageTestsLinguistiques = rs.getBoolean(10);
          if (!rs.wasNull()) {
            etats.put("postPreuvePassageTestsLinguistiques",
                postPreuvePassageTestsLinguistiques ? "signe" : "nonSigne");
          }
          etats.put("encodeProeco", rs.getBoolean(11) ? "signe" : "nonSigne");
          etats.put("encodeMobi", rs.getBoolean(12) ? "signe" : "nonSigne");
          etats.put("etatMobilite", rs.getString(13));
        }
        return etats;
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public MobiliteDto trouverMobiliteParId(int idMobilite) {
    MobiliteDto mobilite = null;
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement(baseQuerySelectMobilite + " WHERE m.id_mobilite = ?")) {
      ps.setInt(1, idMobilite);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          mobilite = remplirMobilite(rs);
        }
        if (mobilite == null) {
          throw new BizException("Cette mobilité n'existe pas.");
        }
        return mobilite;
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public void indiquerEtudiantEncodeDansLogiciels(MobiliteDto mobiliteDto) {
    try (PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("UPDATE paeMobEras.mobilites"
            + " SET encode_proeco = ?, encode_mobi = ? WHERE id_mobilite = ? ")) {
      ps.setBoolean(1, mobiliteDto.estEncodeProeco());
      ps.setBoolean(2, mobiliteDto.estEncodeMobi());
      ps.setInt(3, mobiliteDto.getIdMobilite());
      ps.executeUpdate();
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public Map<MobiliteDto, Boolean[]> listerDemandesPaiementsParAnneeAcademique(
      MobiliteDto mobiliteDto, int idProgrammeSuisse) {
    Map<MobiliteDto, Boolean[]> mobilitesPaiements = new HashMap<>();
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        baseQuerySelectMobilite + " WHERE m.annee_academique = ? AND m.etat <> 'demandee' AND"
            + " (m.etat <> 'annulee' OR m.etat_avant_annulation <> 'demandee')"
            + " ORDER BY m.id_mobilite;")) {
      ps.setString(1, mobiliteDto.getAnneeAcademique());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          MobiliteDto mobiliteDtoRet = remplirMobilite(rs);

          boolean premierPaiementDemande = false;
          boolean secondPaiementDemande = false;
          if (mobiliteDtoRet.getIdProgramme() != idProgrammeSuisse) {
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
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return mobilitesPaiements;
  }

  @Override
  public Boolean[] obtenirDemandesPaiementsPourMobilite(MobiliteDto mobiliteDto) {
    try (PreparedStatement ps = this.dalBackendServices
        .getPreparedStatement(baseQuerySelectMobilite + " WHERE m.id_mobilite = ?;")) {
      ps.setInt(1, mobiliteDto.getIdMobilite());
      try (ResultSet rs = ps.executeQuery()) {
        boolean premierPaiementDemande = false;
        boolean secondPaiementDemande = false;
        while (rs.next()) {
          MobiliteDto mobiliteDtoRet = remplirMobilite(rs);
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
        }
        return new Boolean[] {premierPaiementDemande, secondPaiementDemande};
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public boolean preciserReceptionDocumentsRetourMobilite(MobiliteDto mobiliteDto) {
    boolean signe = true;
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT post_attestation_sejour, post_releve_notes, post_certificat_stage,"
            + " post_rapport_final_complete, post_preuve_passage_tests_linguistiques"
            + " FROM paeMobEras.mobilites WHERE id_mobilite = ?")) {
      ps.setInt(1, mobiliteDto.getIdMobilite());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          if (signe) {
            boolean postAttestationSejour = rs.getBoolean(1);
            signe = postAttestationSejour;
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean postReleveNotes = rs.getBoolean(2); // peut être NULL dans la DB
            if (rs.wasNull()) {
              signe = false;
            } else {
              signe = postReleveNotes;
            }
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean postCertificatStage = rs.getBoolean(3);
            signe = postCertificatStage;
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean postRapportFinalComplete = rs.getBoolean(4); // peut être NULL dans la DB
            if (rs.wasNull()) {
              signe = false;
            } else {
              signe = postRapportFinalComplete;
            }
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean postPreuvePassageTestsLinguistiques = rs.getBoolean(5); // p e NULL dans la DB
            if (rs.wasNull()) {
              signe = false;
            } else {
              signe = postPreuvePassageTestsLinguistiques;
            }
          }
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return signe;
  }

  @Override
  public boolean preciserDocumentsDepartMobiliteSignes(MobiliteDto mobiliteDto) {
    boolean signe = true;
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT pre_contrat_bourse, pre_convention_de_stage_etudes, pre_charte_etudiant,"
            + " pre_preuve_passage_tests_linguistiques, pre_documents_engagement, etat"
            + " FROM paeMobEras.mobilites WHERE id_mobilite = ?")) {
      ps.setInt(1, mobiliteDto.getIdMobilite());
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          if (signe) {
            boolean preContratBourse = rs.getBoolean(1);
            signe = preContratBourse;
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean preConventionDeStageEtudes = rs.getBoolean(2);
            signe = preConventionDeStageEtudes;
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean preCharteEtudiant = rs.getBoolean(3);
            signe = preCharteEtudiant;
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean prePreuvePassageTestsLinguistiques = rs.getBoolean(4);
            signe = prePreuvePassageTestsLinguistiques;
          }
          if (signe) { // si le cas précédent a renvoyé true, on va vérifier le suivant
            boolean preDocumentsEngagement = rs.getBoolean(5);
            signe = preDocumentsEngagement;
          }
          if (!rs.getString(6).equals("premier paiement demande")) {
            signe = false;
          }
        }
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
    return signe;
  }

  @Override
  public int trouverNombreCandidatures(int idEtudiant, String anneeAcademique) {
    try (PreparedStatement ps =
        this.dalBackendServices.getPreparedStatement("SELECT count(DISTINCT m.no_candidature)"
            + " FROM paeMobEras.mobilites m WHERE m.id_utilisateur = ?"
            + " AND m.annee_academique = ?")) {
      ps.setInt(1, idEtudiant);
      ps.setString(2, anneeAcademique);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getInt(1);
      } else {
        return 0;
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

  @Override
  public int trouverIdProgrammeParLibelle(String libelle) {
    try (PreparedStatement ps = this.dalBackendServices.getPreparedStatement(
        "SELECT id_programme " + "FROM paeMobEras.programmes WHERE libelle = ?")) {
      ps.setString(1, libelle);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getInt(1);
      } else {
        return 0;
      }
    } catch (SQLException exc) {
      throw new FatalException(exc.getMessage());
    }
  }

}
