package be.ipl.pae.ihm;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.infosetudiant.InfosEtudiantDto;
import be.ipl.pae.biz.infosetudiant.InfosEtudiantUcc;
import be.ipl.pae.biz.mobilite.MobiliteDto;
import be.ipl.pae.biz.mobilite.MobiliteImpl;
import be.ipl.pae.biz.mobilite.MobiliteUcc;
import be.ipl.pae.biz.partenaire.PartenaireDto;
import be.ipl.pae.biz.partenaire.PartenaireUcc;
import be.ipl.pae.biz.pays.PaysDto;
import be.ipl.pae.biz.pays.PaysUcc;
import be.ipl.pae.biz.programme.ProgrammeDto;
import be.ipl.pae.biz.programme.ProgrammeUcc;
import be.ipl.pae.biz.utilisateur.Utilisateur;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.biz.utilisateur.UtilisateurUcc;
import be.ipl.pae.config.ConfigManager;
import be.ipl.pae.config.Inject;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.exceptions.DalException;
import be.ipl.pae.exceptions.FatalException;
import be.ipl.pae.utils.Log;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import com.owlike.genson.JsonBindingException;
import com.owlike.genson.stream.JsonStreamException;

import org.eclipse.jetty.servlet.DefaultServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MyServlet extends DefaultServlet {

  private Genson genson = new GensonBuilder().useDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
      .useDateAsTimestamp(false).create();
  private Builder jwtBuilder = JWT.create();
  private Algorithm algorithm = Algorithm.HMAC256(ConfigManager.getProperty("jwtSecret"));
  private JWTVerifier jwtVerifier = JWT.require(algorithm).build();
  private final int jwtExpiration = 24 * 60 * 60; // expiration du jwt en secondes
  private final String nomCookieJwt = "utilisateur";
  private final Cookie cookieLogout;
  private String html;
  private String js;
  private boolean cache = Boolean.parseBoolean(ConfigManager.getProperty("cache"));
  private boolean exceptionFataleSurvenue = false;

  @Inject
  private UtilisateurUcc utilisateurUcc;

  @Inject
  private PartenaireUcc partenaireUcc;

  @Inject
  private InfosEtudiantUcc infosEtudiantUcc;

  @Inject
  private PaysUcc paysUcc;

  @Inject
  private MobiliteUcc mobiliteUcc;

  @Inject
  private ProgrammeUcc programmeUcc;

  @Inject
  private BizFactory bizFactory;


  /**
   * Constructeur de la servlet. <br>
   * Initialise le cookie à envoyer au client en cas de déconnexion.
   */
  public MyServlet() {
    this.cookieLogout = new Cookie(nomCookieJwt, "");
    this.cookieLogout.setPath("/");
    this.cookieLogout.setMaxAge(0);
    this.cookieLogout.setHttpOnly(true);
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try {
      mergeHtml();
      mergeJs();
    } catch (IOException exception) {
      Log.severe("Erreur lors de l'initialisation de la servlet", exception);
      System.exit(1);
    }
  }

  private String lireRessource(String chemin) throws IOException {
    InputStream inputStream = this.getServletContext().getResourceAsStream(chemin);
    try (BufferedReader br =
        new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")))) {
      return br.lines().collect(Collectors.joining(System.lineSeparator()));
    }
  }

  private void mergeHtml() throws IOException {
    html = lireRessource("/html/header.html");
    Set<String> chemins = this.getServletContext().getResourcePaths("/html/pages/");
    for (String chemin : chemins) {
      html += lireRessource(chemin);
    }
    html += lireRessource("/html/footer.html");
  }

  private void mergeJs() throws IOException {
    js = lireRessource("/js/glob.js") + "\n" + lireRessource("/js/utils.js") + "\n";
    Set<String> chemins = this.getServletContext().getResourcePaths("/js/events/");
    for (String chemin : chemins) {
      js += lireRessource(chemin) + "\n";
    }
    js += lireRessource("/js/app.js");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String contenuAEnvoyer = null;
    String chemin = req.getPathInfo();
    if (!chemin.matches("^/.*\\..*$")) {
      // Si le chemin n'a pas d'extension de fichier
      if (!cache) {
        mergeHtml();
      }
      contenuAEnvoyer = this.html;
      resp.setContentType("text/html");
    } else {
      if ("/res/index.js".equals(chemin)) {
        if (!cache) {
          mergeJs();
        }
        contenuAEnvoyer = js;
        resp.setContentType("application/javascript");
      } else if (chemin.startsWith("/res/")) {
        super.doGet(req, resp);
        return;
      }
    }
    resp.setCharacterEncoding("UTF-8");
    if (contenuAEnvoyer == null) {
      resp.sendError(404);
    } else {
      resp.getWriter().println(contenuAEnvoyer);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String pseudoConnecte = verifierJwt(req.getCookies());
    UtilisateurDto utilisateur = utilisateurUcc.trouverUtilisateurParPseudo(pseudoConnecte);
    Map<String, Object> mapResponse = new HashMap<>();
    InputStream inputJson = req.getInputStream();
    try {
      String action = req.getParameter("action");
      action = action != null ? action : "";

      // Actions d'un utilisateur connecté et non d'un utilisateur non connecté à l'API
      if (!actionsNonConnecte(action, utilisateur, mapResponse, resp, inputJson)) {
        actionsConnecte(action, utilisateur, mapResponse, resp, inputJson);
      }
    } catch (DalException exception) {
      Log.info("Erreur de la dal lors d'une action du doPost de la servlet.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(500);
    } catch (FatalException exception) {
      Log.severe("Erreur fatale lors d'une action du doPost de la servlet.", exception);
      // renvoyer le message d'erreur + code 503 (Service Unavailable)
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(503);
      exceptionFataleSurvenue = true;
    } catch (ClassCastException exception) {
      Log.warning("Erreur de cast de classe" + " lors d'une action du doPost de la servlet.",
          exception);
      mapResponse.put("errorInfo", "Mauvais type");
      resp.setStatus(400);
    } catch (JsonBindingException | JsonStreamException exception) {
      Log.fine("Erreur lors de la lecture du fichier json dans la servlet.", exception);
      mapResponse.put("errorInfo", "format de la requête invalide");
      // 406 Not Acceptable
      resp.setStatus(406);
    } finally {
      inputJson.close();
    }
    envoyerReponse(resp, mapResponse);
    if (exceptionFataleSurvenue) {
      System.exit(1);
    }
  }

  private boolean actionsNonConnecte(String action, UtilisateurDto utilisateur,
      Map<String, Object> mapResponse, HttpServletResponse resp, InputStream inputJson) {
    switch (action) {
      case "whoami":
        mapResponse.put("utilisateur", utilisateur);
        return true;
      case "login":
        actionLogin(mapResponse, inputJson, resp);
        return true;
      case "signup":
        actionSignup(mapResponse, inputJson, resp);
        return true;
      default:
        break;
    }
    return false;
  }

  private void actionsConnecte(String action, UtilisateurDto utilisateur,
      Map<String, Object> mapResponse, HttpServletResponse resp, InputStream inputJson) {
    if (utilisateur == null) {
      mapResponse.put("errorInfo", "Action non autorisée : non connecté");
      resp.setStatus(401);
      return;
    }
    switch (action) {
      case "logout":
        // Supprime le cookie contenant le jwt du client
        resp.addCookie(cookieLogout);
        break;
      case "encoderPartenaire":
        actionEncoderPartenaire(mapResponse, inputJson, resp, utilisateur);
        break;
      case "chargerDonneesEtudiant":
        actionChargerDonneesEtudiant(mapResponse, inputJson, resp);
        break;
      case "introduireDonneesEtudiant":
        actionIntroduireDonneesEtudiant(mapResponse, inputJson, resp, utilisateur);
        break;
      case "rechercherEtudiants":
        actionRechercherEtudiants(mapResponse, inputJson, resp);
        break;
      case "listerPartenaires":
        actionlisterPartenaires(mapResponse, resp);
        break;
      case "listerPartenairesParPays":
        actionlisterPartenairesParPays(mapResponse, inputJson, resp);
        break;
      case "listerPartenairesParVille":
        actionlisterPartenairesParVille(mapResponse, inputJson, resp);
        break;
      case "listerPartenairesParNomLegal":
        actionlisterPartenairesParNomLegal(mapResponse, inputJson, resp);
        break;
      case "encoderMobilite":
        actionEncoderMobilite(inputJson, utilisateur, mapResponse, resp);
        break;
      case "listerPays":
        actionListerPays(mapResponse, resp);
        break;
      case "listerDemandes":
        actionListerDemandesMobilites(mapResponse, utilisateur, resp);
        break;
      case "listerMobilites":
        actionListerMobilites(mapResponse, resp);
        break;
      case "listerMobilitesParAnneeEtEtat":
        actionListerMobilitesParAnneeEtEtat(inputJson, mapResponse, resp);
        break;
      case "listerMobilitePourEtudiant":
        actionListerMobilitePourEtudiants(mapResponse, utilisateur.getId(), resp);
        break;
      case "listerProgrammes":
        actionListerProgrammes(mapResponse, resp);
        break;
      case "chercherToutesInfosEtudiantPourProf":
        actionChercherToutesInfosEtudiantPourProf(mapResponse, inputJson, utilisateur, resp);
        break;
      case "confirmerMobilite":
        actionConfirmerMobilite(mapResponse, inputJson, resp, utilisateur);
        break;
      case "listerEtudiants":
        actionListerEtudiants(mapResponse);
        break;
      case "listerUtilisateurs":
        actionListerUtilisateurs(mapResponse);
        break;
      case "chargerInfosEtudiantPourProf":
        actionChargerInfosEtudiantPourProf(mapResponse, inputJson, utilisateur, resp);
        break;
      case "supprimerMobilite":
        actionSupprimerMobilite(mapResponse, inputJson, resp);
        break;
      case "annulerMobilite":
        actionAnnulerMobilite(mapResponse, inputJson, utilisateur, resp);
        break;
      case "indiquerEnvoiPremiereDemandePaiement":
        actionIndiquerEnvoiPremiereDemandePaiement(mapResponse, inputJson, resp, utilisateur);
        break;
      case "indiquerEnvoiSecondeDemandePaiement":
        actionIndiquerEnvoiSecondeDemandePaiement(mapResponse, inputJson, resp, utilisateur);
        break;
      case "obtenirListeEtatsDocumentsEtMobilite":
        actionObtenirListeEtatsDocumentsEtMobilite(mapResponse, inputJson, resp, utilisateur);
        break;
      case "preciserReceptionDocumentsRetourMobilite":
        actionPreciserReceptionDocumentsRetourMobilite(mapResponse, inputJson, resp, utilisateur);
        break;
      case "preciserDocumentsDepartMobiliteSignes":
        actionPreciserDocumentsDepartMobiliteSignes(mapResponse, inputJson, resp, utilisateur);
        break;
      case "indiquerProfesseur":
        actionIndiquerProfesseur(inputJson, mapResponse, utilisateur, resp);
        break;
      case "indiquerEtudiant":
        actionIndiquerEtudiant(inputJson, mapResponse, utilisateur, resp);
        break;
      case "indiquerEtudiantEncodeDansLogiciels":
        actionIndiquerEtudiantEncodeDansLogiciels(inputJson, mapResponse, resp, utilisateur);
        break;
      case "listerDemandesPaiementsParAnneeAcademique":
        actionListerDemandesPaiementsParAnneeAcademique(mapResponse, inputJson, resp, utilisateur);
        break;
      case "obtenirDemandesPaiementsPourMobilite":
        actionObtenirDemandesPaiementsPourMobilite(mapResponse, inputJson, resp, utilisateur);
        break;
      case "signerDocuments":
        actionSignerDocuments(mapResponse, inputJson, resp);
        break;
      default:
        mapResponse.put("errorInfo", "Action inconnue");
        resp.setStatus(400);
        break;
    }
  }


  private void envoyerReponse(HttpServletResponse resp, Map<String, Object> mapResponse)
      throws IOException {
    // Ajout des headers et du json à la réponse
    resp.setContentType("application/json;charset=utf-8");
    resp.setHeader("Accept", "application/json");
    OutputStream out = resp.getOutputStream();
    genson.serialize(mapResponse, out);
    out.close();
  }

  private void actionLogin(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp) {
    UtilisateurDto utilisateurAConnecter = lireJson(inputJson, bizFactory.getUtilisateurDto());
    String pseudo = utilisateurAConnecter.getPseudo();
    String mdp = utilisateurAConnecter.getMotDePasse();
    UtilisateurDto utilisateur = this.utilisateurUcc.seConnecter(pseudo, mdp);
    if (utilisateur != null) {
      mapResponse.put("utilisateur", utilisateur);
      resp.addCookie(creerJwt(utilisateur.getPseudo()));
    } else {
      mapResponse.put("errorInfo", "Cette combinaison pseudo - mot de passe n'existe pas.");
      resp.setStatus(401);
    }

  }

  private void actionSignup(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp) {
    try {
      UtilisateurDto utilisateurDto = lireJson(inputJson, bizFactory.getUtilisateurDto());
      utilisateurUcc.inscrireUtilisateur(utilisateurDto);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative d'inscription d'un utilisateur.",
          exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(412);
    }
  }

  private void actionEncoderPartenaire(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    try {
      PartenaireDto partenaireDto = lireJson(inputJson, bizFactory.getPartenaireDto());
      MobiliteDto mobiliteDto = mobiliteUcc.trouverMobiliteParId(partenaireDto.getIdMobilite());
      if (mobiliteDto == null) {
        mapResponse.put("errorInfo", "La demande de mobilité n'existe pas");
        resp.setStatus(412);
        return;
      }
      Utilisateur utilisateur = (Utilisateur) utilisateurDto;
      if (utilisateur.estEtudiant() && mobiliteDto.getIdUtilisateur() != utilisateur.getId()) {
        mapResponse.put("errorInfo", "Vous n'avez pas le droit d'effectuer cette action");
        resp.setStatus(412);
        return;
      }
      partenaireUcc.ajouterPartenaire(partenaireDto, mobiliteDto);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative d'ajout d'un partenaire.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(412);
    }
  }

  private void actionIntroduireDonneesEtudiant(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    try {
      InfosEtudiantDto infosEtudiantDto = lireJson(inputJson, bizFactory.getInfosEtudiantDto());
      Utilisateur utilisateur = (Utilisateur) utilisateurDto;
      if (utilisateur.estEtudiant()) {
        infosEtudiantDto.setIdUtilisateur(utilisateur.getId());
      }
      infosEtudiantUcc.introduireDonnees(infosEtudiantDto, infosEtudiantDto.getNoVersion());
    } catch (BizException | DateTimeParseException exception) {
      Log.fine("Erreur venant du biz lors de la tentative d'introduction des données"
          + " étudiant par un utilisateur.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(412);
    }

  }

  private void actionRechercherEtudiants(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp) {
    try {
      InfosEtudiantDto infosEtudiantDto = lireJson(inputJson, bizFactory.getInfosEtudiantDto());
      List<InfosEtudiantDto> infos = infosEtudiantUcc.rechercherEtudiants(infosEtudiantDto.getNom(),
          infosEtudiantDto.getPrenom());
      mapResponse.put("liste", infos);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de recherche des étudiants"
          + " par un utilisateur.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionChargerDonneesEtudiant(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp) {
    try {
      InfosEtudiantDto infosEtudiantACharger =
          lireJson(inputJson, bizFactory.getInfosEtudiantDto());
      InfosEtudiantDto infosEtudiantDto =
          infosEtudiantUcc.chargerInfosEtudiantParId(infosEtudiantACharger.getIdUtilisateur());
      mapResponse.put("infos", infosEtudiantDto);
      // Renvoyer infosEtudiantDto.getNoVersion() vers le front pour stocker cette valeur (par ex
      // dans une variable) afin de pouvoir l'utiliser.
      // Tous les autre champs de infosEtudiantDto servent à préremplir les champs si
      // l'utilisateur
      // désire modifier ses données (donc il les avait déjà introduites und première fois).
    } catch (BizException exception) {
      int noVersion = 0;
      mapResponse.put("noVersion", noVersion);
      // Renvoyer uniquement le numéro de version vers le front.
      Log.finest("Erreur venant du biz lors de la tentative de chargement des données étudiant"
          + " par un utilisateur.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(412);
    } catch (NumberFormatException exception) {
      Log.fine("Erreur NumberFormatException", exception);
      throw new ClassCastException();
    }
  }

  private void actionlisterPartenairesParVille(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp) {
    try {
      PartenaireDto partenaireDto = lireJson(inputJson, bizFactory.getPartenaireDto());
      List<PartenaireDto> partenaires =
          partenaireUcc.listerPartenairesParVille(partenaireDto.getVille());
      mapResponse.put("liste", partenaires);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de recherche des partenaires"
          + " par un utilisateur.", exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }

  }

  private void actionlisterPartenairesParPays(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp) {
    try {
      PaysDto paysDto = lireJson(inputJson, bizFactory.getPaysDto());
      List<PartenaireDto> partenaires = partenaireUcc.listerPartenairesParPays(paysDto.getIdPays());
      mapResponse.put("liste", partenaires);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de recherche des partenaires"
          + " par un utilisateur.", exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }

  }

  private void actionlisterPartenairesParNomLegal(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp) {
    try {
      PartenaireDto partenaireDto = lireJson(inputJson, bizFactory.getPartenaireDto());
      List<PartenaireDto> partenaires =
          partenaireUcc.listerPartenairesParNomLegal(partenaireDto.getNomLegal());
      mapResponse.put("liste", partenaires);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de recherche des partenaires"
          + " par un utilisateur.", exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }

  }

  private void actionlisterPartenaires(Map<String, Object> mapResponse, HttpServletResponse resp) {
    try {
      List<PartenaireDto> partenaires = partenaireUcc.listerPartenaires();
      mapResponse.put("liste", partenaires);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de recherche des partenaires"
          + " par un utilisateur.", exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }

  }

  private void actionEncoderMobilite(InputStream inputJson, UtilisateurDto utilisateurDto,
      Map<String, Object> mapResponse, HttpServletResponse resp) {
    try {
      List<? extends MobiliteDto> listeMobiliteDto = lireJsonListeMobilites(inputJson);
      Utilisateur utilisateur = (Utilisateur) utilisateurDto;
      if (utilisateur.estEtudiant()) {
        for (MobiliteDto mobiliteDto : listeMobiliteDto) {
          mobiliteDto.setIdUtilisateur(utilisateurDto.getId());
        }
      }
      mobiliteUcc.ajouterMobilite(listeMobiliteDto);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative d'ajout d'une mobilite.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(400);
    } catch (DalException exception) {
      Log.fine("Erreur venant de la dal lors de la tentative d'ajout d'une mobilite.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(503);
    }
  }

  private void actionListerDemandesMobilites(Map<String, Object> mapResponse,
      UtilisateurDto utilisateurDto, HttpServletResponse resp) {
    try {
      List<MobiliteDto> mobilites = mobiliteUcc.listerDemandesDeMobilites(utilisateurDto);
      mapResponse.put("liste", mobilites);
    } catch (BizException exception) {
      Log.fine(
          "Erreur venant du biz lors de la tentative de demande de liste des demandes de mobilité.",
          exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionListerMobilites(Map<String, Object> mapResponse, HttpServletResponse resp) {
    try {
      List<MobiliteDto> mobilites = mobiliteUcc.listerMobilite();
      mapResponse.put("liste", mobilites);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de la demande de liste des mobilités.",
          exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionListerMobilitesParAnneeEtEtat(InputStream inputJson,
      Map<String, Object> mapResponse, HttpServletResponse resp) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteParAnneeEtEtat(mobiliteDto);
      mapResponse.put("liste", mobilites);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de lister les mobilités"
          + " par année et par état.", exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionListerMobilitePourEtudiants(Map<String, Object> mapResponse, int idUtilisateur,
      HttpServletResponse resp) {
    try {
      List<MobiliteDto> mobilites = mobiliteUcc.listerMobiliteDunEtudiant(idUtilisateur);
      mapResponse.put("liste", mobilites);
    } catch (BizException exception) {
      Log.fine(
          "Erreur venant du biz lors de la tentative de lister les mobilités pour les étudiants.",
          exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionListerPays(Map<String, Object> mapResponse, HttpServletResponse resp) {
    try {
      List<PaysDto> pays = paysUcc.listerPays();
      mapResponse.put("liste", pays);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative de recherche des pays.", exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionListerProgrammes(Map<String, Object> mapResponse, HttpServletResponse resp) {
    try {
      List<ProgrammeDto> programmes = programmeUcc.listerProgrammes();
      mapResponse.put("liste", programmes);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la tentative" + " de recherche des programmes.",
          exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionChercherToutesInfosEtudiantPourProf(Map<String, Object> mapResponse,
      InputStream inputJson, UtilisateurDto prof, HttpServletResponse resp) {
    InfosEtudiantDto infosEtudiantDto = lireJson(inputJson, bizFactory.getInfosEtudiantDto());
    try {
      infosEtudiantDto =
          infosEtudiantUcc.chargerInfosEtudiantPourProf(prof, infosEtudiantDto.getIdUtilisateur());

      mapResponse.put("etudiant", infosEtudiantDto);
    } catch (BizException exception) {
      Log.fine(
          "Erreur venant de la dal lors de la visualisation des infos d'un étudiant par un prof.",
          exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionConfirmerMobilite(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp, UtilisateurDto utilisateur) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      mobiliteUcc.confirmerMobilite(mobiliteDto, utilisateur);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la confirmation d'une mobilité par un prof.",
          exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(403);
    }
  }

  private void actionListerEtudiants(Map<String, Object> mapResponse) {
    List<UtilisateurDto> utilisateurs = utilisateurUcc.listerEtudiants();
    // Pas d'erreur biz à gérer ici, renvoi direct des infos (s'il y a eu une erreur fatale, elle
    // sera catch dans le switch case.
    mapResponse.put("etudiants", utilisateurs);
  }

  private void actionListerUtilisateurs(Map<String, Object> mapResponse) {
    List<UtilisateurDto> utilisateurs = utilisateurUcc.listerUtilisateurs();
    // Pas d'erreur biz à gérer ici, renvoi direct des infos (s'il y a eu une erreur fatale, elle
    // sera catch dans le switch case.
    mapResponse.put("etudiants", utilisateurs);
  }

  private void actionChargerInfosEtudiantPourProf(Map<String, Object> mapResponse,
      InputStream inputJson, UtilisateurDto utilisateur, HttpServletResponse resp) {
    InfosEtudiantDto infosEtudiantDto = lireJson(inputJson, bizFactory.getInfosEtudiantDto());
    try {
      InfosEtudiantDto infos = infosEtudiantUcc.chargerInfosEtudiantPourProf(utilisateur,
          infosEtudiantDto.getIdUtilisateur());
      mapResponse.put("infosEtudiant", infos);
    } catch (BizException exception) {
      Log.fine(
          "Erreur venant du biz lors du chargement des informations d'un étudiant pour un prof.",
          exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionSupprimerMobilite(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      boolean supprime = mobiliteUcc.supprimerMobilite(mobiliteDto);
      String etat = supprime ? "Succes" : "Echec";
      mapResponse.put("etat", etat);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la suppression d'une mobilité.", exception);
      resp.setStatus(400);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionAnnulerMobilite(Map<String, Object> mapResponse, InputStream inputJson,
      UtilisateurDto utilisateurDto, HttpServletResponse resp) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    String raisonAnnulation = mobiliteDto.getRaisonAnnulation();
    mobiliteDto = mobiliteUcc.trouverMobiliteParId(mobiliteDto.getIdMobilite());
    Utilisateur utilisateur = (Utilisateur) utilisateurDto;
    if (utilisateur.estEtudiant() && utilisateurDto.getId() != mobiliteDto.getIdUtilisateur()) {
      resp.setStatus(403);
      mapResponse.put("errorInfo",
          "Vous n'avez pas les autorisations nécessaires pour effectuer cette action.");
      return;
    }
    mobiliteDto.setRaisonAnnulation(raisonAnnulation);
    try {
      boolean annule = mobiliteUcc.annulerMobilite(mobiliteDto);
      String etat = annule ? "Succes" : "Echec";
      mapResponse.put("etat", etat);
      if (!annule) {
        resp.setStatus(412);
      }
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de l'annulation d'une mobilité.", exception);
      resp.setStatus(412);
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionIndiquerEnvoiPremiereDemandePaiement(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      boolean indique =
          mobiliteUcc.indiquerEnvoiPremiereDemandePaiement(utilisateurDto, mobiliteDto);
      String etat = indique ? "Succes" : "Echec";
      mapResponse.put("etat", etat);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de l'indication de l'envoi "
          + "d'une première demande de paiement.", exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionIndiquerEnvoiSecondeDemandePaiement(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      boolean indique =
          mobiliteUcc.indiquerEnvoiSecondeDemandePaiement(utilisateurDto, mobiliteDto);
      String etat = indique ? "Succes" : "Echec";
      mapResponse.put("etat", etat);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de l'indication de l'envoi d'une"
          + " seconde demande de paiement.", exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionObtenirListeEtatsDocumentsEtMobilite(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      Map<String, String> etats =
          mobiliteUcc.obtenirListeEtatsDocumentsEtMobilite(utilisateurDto, mobiliteDto);
      mapResponse.put("etats", etats);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de l'obtention de la liste des états"
          + " des documents et de la mobilité.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(412);
    }
  }

  private void actionPreciserReceptionDocumentsRetourMobilite(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      boolean signe =
          mobiliteUcc.preciserReceptionDocumentsRetourMobilite(utilisateurDto, mobiliteDto);
      String etat = signe ? "signe" : "nonSigne";
      mapResponse.put("etat", etat);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la précision de la réception des documents"
          + " de retour associés à une mobilité.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      resp.setStatus(412);
    }
  }

  private void actionPreciserDocumentsDepartMobiliteSignes(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      boolean signe =
          mobiliteUcc.preciserDocumentsDepartMobiliteSignes(utilisateurDto, mobiliteDto);
      String etat = signe ? "signe" : "nonSigne";
      mapResponse.put("etat", etat);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la précision de la réception"
          + " des documents de retour associés à une mobilité.", exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionIndiquerProfesseur(InputStream inputJson, Map<String, Object> mapResponse,
      UtilisateurDto utilisateur, HttpServletResponse resp) {
    UtilisateurDto utilisateurDto = lireJson(inputJson, bizFactory.getUtilisateurDto());
    try {
      utilisateurUcc.indiquerProfesseur(utilisateur, utilisateurDto);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la précision de la réception"
          + " des documents de retour associés à une mobilité.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
    }
  }

  private void actionIndiquerEtudiant(InputStream inputJson, Map<String, Object> mapResponse,
      UtilisateurDto utilisateur, HttpServletResponse resp) {
    UtilisateurDto utilisateurDto = lireJson(inputJson, bizFactory.getUtilisateurDto());
    try {
      utilisateurUcc.indiquerEtudiant(utilisateur, utilisateurDto);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de la précision de la réception"
          + " des documents de retour associés à une mobilité.", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
    }
  }

  private void actionIndiquerEtudiantEncodeDansLogiciels(InputStream inputJson,
      Map<String, Object> mapResponse, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      mobiliteUcc.indiquerEtudiantEncodeDansLogiciels(utilisateurDto, mobiliteDto);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de l'indication que l'étudiant a été "
          + "encodé dans les logiciels externes.", exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionListerDemandesPaiementsParAnneeAcademique(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      Map<MobiliteDto, Boolean[]> mobilitesPaiements =
          mobiliteUcc.listerDemandesPaiementsParAnneeAcademique(utilisateurDto, mobiliteDto);
      mapResponse.put("mobilites", mobilitesPaiements.keySet());
      mapResponse.put("paiements", mobilitesPaiements.values());
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de l'ajout de la récupération des "
          + "demandes de paiements par année académique.", exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }

  private void actionObtenirDemandesPaiementsPourMobilite(Map<String, Object> mapResponse,
      InputStream inputJson, HttpServletResponse resp, UtilisateurDto utilisateurDto) {
    MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
    try {
      Boolean[] paiements =
          mobiliteUcc.obtenirDemandesPaiementsPourMobilite(utilisateurDto, mobiliteDto);
      mapResponse.put("paiements", paiements);
    } catch (BizException exception) {
      Log.fine("Erreur venant du biz lors de l'ajout de la récupération des "
          + "demandes de paiements pour une mobilité.", exception);
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
      mapResponse.put("errorInfo", exception.getMessage());
    }
  }


  private void actionSignerDocuments(Map<String, Object> mapResponse, InputStream inputJson,
      HttpServletResponse resp) {
    try {
      MobiliteDto mobiliteDto = lireJson(inputJson, bizFactory.getMobiliteDto());
      mobiliteUcc.signerDocuments(mobiliteDto);
    } catch (BizException exception) {
      Log.fine("Erreur lors de la signature des documents", exception);
      mapResponse.put("errorInfo", exception.getMessage());
      if (exception.isForbidden()) {
        resp.setStatus(403);
      } else {
        resp.setStatus(400);
      }
    }
  }

  private <T> T lireJson(InputStream inputJson, T dto)
      throws JsonBindingException, JsonStreamException {
    T dtoARenvoyer = genson.deserializeInto(inputJson, dto);
    if (dtoARenvoyer == null) {
      throw new JsonBindingException("JSON invalide");
    }
    return dtoARenvoyer;
  }

  private List<? extends MobiliteDto> lireJsonListeMobilites(InputStream inputJson)
      throws JsonBindingException, JsonStreamException {
    List<? extends MobiliteDto> listeARenvoyer =
        genson.deserialize(inputJson, new GenericType<List<MobiliteImpl>>() {});
    if (listeARenvoyer == null) {
      throw new JsonBindingException("JSON invalide");
    }
    return listeARenvoyer;
  }


  /**
   * Vérifie un token et renvoie le pseudo de l'utilisateur si le token est toujours valide selon
   * l'algorithme et le "secret" du serveur.
   * 
   * @param cookies de la requete contenant le token jwt à vérifier.
   * @return le pseudo de l'utilisateur, ou null si le token a expiré ou est non valide.
   */
  private String verifierJwt(Cookie[] cookies) {
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("utilisateur".equals(cookie.getName())) {
          try {
            DecodedJWT decodedJwt = jwtVerifier.verify(cookie.getValue());
            return decodedJwt.getClaim("pseudo").asString();
          } catch (JWTVerificationException exception) {
            Log.fine("Erreur.", exception);
            return null;
          }
        }
      }
    }
    return null;
  }

  /**
   * Crée un token expirant selon la variable jwtExpiration (en secondes) et contenant un pseudo
   * fournit en paramètre.
   * 
   * @param pseudo à mettre dans le token.
   * @return un Cookie contenant le token.
   */
  private Cookie creerJwt(String pseudo) {
    String ltoken = jwtBuilder.withClaim("pseudo", pseudo)
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration * 1000)).sign(algorithm);
    Cookie cookie = new Cookie(nomCookieJwt, ltoken);
    cookie.setPath("/");
    cookie.setMaxAge(jwtExpiration);
    cookie.setHttpOnly(true);
    return cookie;
  }

}
