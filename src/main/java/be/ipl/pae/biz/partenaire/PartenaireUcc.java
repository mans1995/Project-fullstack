package be.ipl.pae.biz.partenaire;

import be.ipl.pae.biz.mobilite.MobiliteDto;

import java.util.List;

public interface PartenaireUcc {

  /**
   * Ajoute un partenaire.
   * 
   * @param partenaireDto le partenaire à ajouter.
   */
  void ajouterPartenaire(PartenaireDto partenaireDto, MobiliteDto mobiliteDto);

  /**
   * Liste tous les partenaires.
   * 
   * @return une liste de partenaireDTO
   */
  List<PartenaireDto> listerPartenaires();

  /**
   * Liste tous les partenaires en fonction du pays.
   * 
   * @return une liste de partenaireDTO
   */
  List<PartenaireDto> listerPartenairesParPays(int idPays);

  /**
   * Liste tous les partenaires en fonction de la ville.
   * 
   * @return une liste de partenaireDTO
   */
  List<PartenaireDto> listerPartenairesParVille(String ville);

  /**
   * Liste tous les partenaires en fonction du nom legal.
   * 
   * @return une liste de partenaireDTO
   */
  List<PartenaireDto> listerPartenairesParNomLegal(String nomLegal);
}
