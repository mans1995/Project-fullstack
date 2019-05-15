package be.ipl.pae.dal.partenaire;

import be.ipl.pae.biz.partenaire.PartenaireDto;

import java.util.List;

public interface PartenaireDao {

  /**
   * Methode qui permet d'ajouter un partenaire dans la DB.
   * 
   * @param partenaireDto un partenaire
   * @return l'id du partenaire ajouté
   */
  int ajouterPartenaire(PartenaireDto partenaireDto);


  /**
   * Renvoie la liste des partenaires.
   * 
   * @return la liste des partenaires.
   */
  List<PartenaireDto> listerPartenaires();

  /**
   * Methode qui cherche et renvoi le partenaire qui a le nom passe en parametre.
   * 
   * @param nomComplet le nom a retrouver.
   * @return le partenaire trouve.
   */
  PartenaireDto trouverPartenaireParNomComplet(String nomComplet);

  /**
   * Renvoie la liste des partenaires en fonction du pays.
   * 
   * @return la liste des partenaires.
   */
  List<PartenaireDto> listerPartenairesParPays(int idPays);

  /**
   * Renvoie la liste des partenaires de la ville.
   * 
   * @return la liste des partenaires.
   */
  List<PartenaireDto> listerPartenairesParVille(String ville);

  /**
   * Renvoie la liste des partenaires en fonction du nom rechercher.
   * 
   * @param nomLegal le nom legal du(des) partenaire(s) a rechercher.
   * @return la liste des partenaires.
   */
  List<PartenaireDto> listerPartenairesParNomLegal(String nomLegal);
}
