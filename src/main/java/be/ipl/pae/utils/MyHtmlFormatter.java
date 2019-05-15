package be.ipl.pae.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MyHtmlFormatter extends Formatter {

  /*
   * Basé sur les informations disponibles sur le site :
   * https://www.vogella.com/tutorials/Logging/article.html
   */

  /**
   * This method is called for every log records.
   */
  public String format(LogRecord rec) {
    StringBuffer buf = new StringBuffer(10000);
    buf.append("<tr style=\"width:100%; border: 1px solid black;\">\n");

    // colorize levels
    String hexColor = "#00FF00";
    if (rec.getLevel().intValue() == Level.SEVERE.intValue()) {
      hexColor = "#F92D52";
    } else if (rec.getLevel().intValue() == Level.WARNING.intValue()) {
      hexColor = "#F99205";
    } else if (rec.getLevel().intValue() == Level.INFO.intValue()) {
      hexColor = "#FCC803";
    } else if (rec.getLevel().intValue() == Level.CONFIG.intValue()) {
      hexColor = "#4ED55F";
    } else if (rec.getLevel().intValue() == Level.FINE.intValue()) {
      hexColor = "#5AC4F8";
    } else if (rec.getLevel().intValue() == Level.FINER.intValue()) {
      hexColor = "#36A6D6";
    } else if (rec.getLevel().intValue() == Level.FINEST.intValue()) {
      hexColor = "#0376F7";
    } else /* ça ne devrait pas se passer normalement */ {
      hexColor = "#00FF00";
    }

    // colorize any levels
    buf.append("\t<td style=\"color:" + hexColor + ";width:10%; border: 1px solid black;\">");
    buf.append("<b>");
    buf.append(rec.getLevel());
    buf.append("</b>");
    buf.append("</td>\n");
    buf.append("\t<td style=\"width:15%; border: 1px solid black;\">");
    buf.append(calcDate(rec.getMillis()));
    buf.append("</td>\n");
    buf.append("\t<td style=\"width:100%;overflow:hidden;word-wrap:break-word;"
        + " border: 1px solid black;text-align:left;padding:10px\">");
    buf.append(formatMessage(rec));
    buf.append("</td>\n");
    buf.append("</tr>\n");

    return buf.toString();
  }

  /**
   * Cette methode retourne la date en string.
   * 
   * @param millisecs les ms.
   * @return une String de la date.
   */
  private String calcDate(long millisecs) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy [HH:mm:ss]");
    Date resultdate = new Date(millisecs);
    return dateFormat.format(resultdate);
  }

  /**
   * This method is called just after the handler using this formatter is created.
   */
  public String getHead(Handler handler) {
    return "<!DOCTYPE html>\n<head>\n<style>\n" + "table { width: 100% }\n"
        + "th { font:bold 10pt Tahoma; }\n" + "td { font:normal 10pt Tahoma; }\n"
        + "h1 {font:normal 11pt Tahoma;}\n" + "</style>\n" + "</head>\n"
        + "<body style=\"width:100%;text-align:center\">\n"
        + "<table border=\"0\" cellpadding=\"5\" cellspacing=\"3\""
        + " style=\"width:70%;table-layout:fixed;"
        + " border: 1px solid black;margin:auto;border-collapse:collapse\">\n"
        + "<tr align=\"left\">\n"
        + "\t<th style=\"width:10%;text-align:center\">Niveau du Log</th>\n"
        + "\t<th style=\"width:15%;text-align:center\">Temps</th>\n"
        + "\t<th style=\"width:75%;text-align:center\">Message du Log</th>\n" + "</tr>\n";
  }

  /**
   * this method is called just after the handler using this formatter is closed.
   */
  public String getTail(Handler handler) {
    return "</table>\n</body>\n</html>";
  }

}
