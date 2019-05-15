package be.ipl.pae.biz;

import be.ipl.pae.biz.infosetudiant.InfosEtudiantDto;
import be.ipl.pae.biz.infosetudiant.InfosEtudiantImpl;
import be.ipl.pae.biz.mobilite.MobiliteDto;
import be.ipl.pae.biz.mobilite.MobiliteImpl;
import be.ipl.pae.biz.partenaire.PartenaireDto;
import be.ipl.pae.biz.partenaire.PartenaireImpl;
import be.ipl.pae.biz.pays.PaysDto;
import be.ipl.pae.biz.pays.PaysImpl;
import be.ipl.pae.biz.programme.ProgrammeDto;
import be.ipl.pae.biz.programme.ProgrammeImpl;
import be.ipl.pae.biz.utilisateur.UtilisateurDto;
import be.ipl.pae.biz.utilisateur.UtilisateurImpl;

public class BizFactoryImpl implements BizFactory {

  @Override
  public UtilisateurDto getUtilisateurDto(String pseudo, String motDePasse) {
    return new UtilisateurImpl(pseudo, motDePasse);
  }

  @Override
  public UtilisateurDto getUtilisateurDto() {
    return new UtilisateurImpl();
  }

  @Override
  public PartenaireDto getPartenaireDto() {
    return new PartenaireImpl();
  }

  @Override
  public MobiliteDto getMobiliteDto() {
    return new MobiliteImpl();
  }

  @Override
  public InfosEtudiantDto getInfosEtudiantDto() {
    return new InfosEtudiantImpl();
  }

  @Override
  public ProgrammeDto getProgrammeDto() {
    return new ProgrammeImpl();
  }

  @Override
  public PaysDto getPaysDto() {
    return new PaysImpl();
  }

}
