package be.ipl.pae.utils;

public interface Util {

  String REGEX_NOM = "^[A-ZÉÈÊËÀÂÇ][a-zéèêëàâç]*([ '-][A-ZÉÈÊËÀÂÇ][a-zéèêëàâç]*)*$";
  String REGEX_PSEUDO = "^[A-Za-z0-9ÉÈÊËÀÂÇéèêëàâç$_\\[\\]{}()]{8,}$";
  String REGEX_EMAIL_UTILISATEUR = "^[a-z]{2,}(\\.[a-z]{2,})*@((student\\.)?vinci\\.be)$";
  // w c'est un raccourci pour [a-zA-Z_0-9], regex typique pour email
  String REGEX_EMAIL_PARTENAIRE = "^(\\w\\.?)+@\\w+(\\.\\w+)+$";
  String REGEX_MOT_DE_PASSE = "^[A-Za-z0-9ÉÈÊÀÂÇéèêàâç_µ€£$;:.,!?=+\\-*\\/%~#\\[\\]{}()]{8,}$";
  String REGEX_NUMERO_TEL = "^(\\+?[0-9]{5}|0[0-9]{3})(([0-9]{2}){3})$";
  String REGEX_EST_NOMBRE = "\\d+";
  String REGEX_CHAINE_VIDE = "^\\s+$";
  String REGEX_STATUT = "^(\\bM\\b)|(\\bMme\\b)|(\\bMlle\\b)$";
  String REGEX_SEXE = "^(\\bM\\b)|(\\bF\\b)$";
  String REGEX_IBAN = "\\b[A-Z]{2}[0-9]{2}(?:[ ]?[0-9]{4}){3}\\b";
  String REGEX_CODE_BIC = "([a-zA-Z]{4})([a-zA-Z]{2})(([2-9a-zA-Z]{1})([0-9a-np-zA-NP-Z]{1}))"
      + "((([0-9a-wy-zA-WY-Z]{1})([0-9a-zA-Z]{2}))|([xX]{3})|)";
  String REGEX_TYPE_CODE = "(SMS)|(SMP)";


  /**
   * Check si une string est vide ou null.
   * 
   * @param chaine la chaine à tester
   * @return true si valide, false sinon
   */
  static boolean estValide(String chaine) {
    return chaine != null && !chaine.equals("") && !chaine.matches(REGEX_CHAINE_VIDE);
  }
}

