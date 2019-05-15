package be.ipl.pae.biz;

import be.ipl.pae.biz.infosetudiant.InfosEtudiantDto;
import be.ipl.pae.biz.mobilite.MobiliteDto;
import be.ipl.pae.biz.partenaire.PartenaireDto;
import be.ipl.pae.biz.pays.PaysDto;
import be.ipl.pae.biz.programme.ProgrammeDto;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;

public interface BizFactory {

  /**
   * Instancie un objet UtilisateurDto.
   * 
   * @param pseudo le pseudo entré par l'utilisateur en ligne.
   * @param motDePasse le mot de passe entré par l'utilisateur en ligne.
   * @return un nouvel objet {@link UtilisateurDto}.
   */
  UtilisateurDto getUtilisateurDto(String pseudo, String motDePasse);

  /**
   * Instancie un objet {@link UtilisateurDto}.
   * 
   * @return un nouvel objet {@link UtilisateurDto}.
   */
  UtilisateurDto getUtilisateurDto();

  /**
   * Instancie un objet {@link PartenaireDto}.
   * 
   * @return un nouvel objet {@link PartenaireDto}.
   */
  PartenaireDto getPartenaireDto();

  /**
   * Instancie un objet {@link MobiliteDto}.
   * 
   * @return un nouvel objet {@link MobiliteDto}.
   */
  MobiliteDto getMobiliteDto();

  /**
   * Instancie un objet {@link InfosEtudiantDto}.
   * 
   * @return un nouvel objet {@link InfosEtudiantDto}.
   */
  InfosEtudiantDto getInfosEtudiantDto();

  /**
   * Instancie un objet {@link ProgrammeDto}.
   * 
   * @return un nouvel objet {@link ProgrammeDto}.
   */
  ProgrammeDto getProgrammeDto();

  /**
   * Instancie un objet {@link PaysDto}.
   * 
   * @return un nouvel objet {@link PaysDto}.
   */
  PaysDto getPaysDto();


}
