package be.ipl.pae.dal.infosetudiant;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.infosetudiant.InfosEtudiantDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.infosetudiant.InfosEtudiantDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InfosEtudiantDaoMock implements InfosEtudiantDao {

  @Inject
  BizFactory bizFactory;

  private Set<InfosEtudiantDto> infosEtudiants = new HashSet<>();

  @Override
  public InfosEtudiantDto chercherInfosEtudiantParId(int idEtudiant) {
    return infosEtudiants.stream().filter(infos -> idEtudiant == infos.getIdUtilisateur()).findAny()
        .orElse(null);
  }

  @Override
  public void ajouterInfosEtudiant(InfosEtudiantDto infosEtudiantDto) {
    InfosEtudiantDto infosEtudiant = bizFactory.getInfosEtudiantDto();
    setInfosWithoutNoVersion(infosEtudiant, infosEtudiantDto);
    infosEtudiant.setNoVersion(1);
    infosEtudiants.add(infosEtudiantDto);
  }

  private void setInfosWithoutNoVersion(InfosEtudiantDto infosEtudiant,
      InfosEtudiantDto infosEtudiantDto) {
    infosEtudiant.setStatut(infosEtudiantDto.getStatut());
    infosEtudiant.setNom(infosEtudiantDto.getNom());
    infosEtudiant.setPrenom(infosEtudiantDto.getPrenom());
    infosEtudiant.setDateNaissance(infosEtudiantDto.getDateNaissance());
    infosEtudiant.setNationalite(infosEtudiantDto.getNationalite());
    infosEtudiant.setAdresse(infosEtudiantDto.getAdresse());
    infosEtudiant.setTelephone(infosEtudiantDto.getTelephone());
    infosEtudiant.setEmail(infosEtudiantDto.getEmail());
    infosEtudiant.setSexe(infosEtudiantDto.getSexe());
    infosEtudiant.setNbAnneesReussies(infosEtudiantDto.getNbAnneesReussies());
    infosEtudiant.setIban(infosEtudiantDto.getIban());
    infosEtudiant.setTitulaireCompte(infosEtudiantDto.getTitulaireCompte());
    infosEtudiant.setNomBanque(infosEtudiantDto.getNomBanque());
    infosEtudiant.setCodeBic(infosEtudiantDto.getCodeBic());
  }

  @Override
  public boolean actualiserInfosEtudiant(InfosEtudiantDto infosEtudiantDto, int noVersion) {
    int noVersionAncien = infosEtudiantDto.getNoVersion();
    InfosEtudiantDto infosEtudiant = infosEtudiants.stream()
        .filter(info -> info.getIdUtilisateur() == infosEtudiantDto.getIdUtilisateur()
            && noVersion == noVersionAncien)
        .findAny().orElse(null);
    if (infosEtudiant == null) {
      return false;
    } else {
      infosEtudiantDto.incrementNoVersion();
      setInfosWithoutNoVersion(infosEtudiant, infosEtudiantDto);
      infosEtudiant.setNoVersion(infosEtudiantDto.getNoVersion());
      return true;
    }
  }

  @Override
  public List<InfosEtudiantDto> rechercherEtudiantsBasique(String nom, String prenom) {
    List<InfosEtudiantDto> infos = new ArrayList<>();
    infosEtudiants.stream()
        .filter(info -> info.getNom().contains(nom) && info.getPrenom().contains(prenom))
        .forEach(info -> {
          InfosEtudiantDto monInfo = bizFactory.getInfosEtudiantDto();
          monInfo.setIdUtilisateur(info.getIdUtilisateur());
          monInfo.setStatut(info.getStatut());
          monInfo.setNom(info.getNom());
          monInfo.setPrenom(info.getPrenom());
          monInfo.setSexe(info.getSexe());
          monInfo.setNbAnneesReussies(info.getNbAnneesReussies());
          monInfo.setNoVersion(info.getNoVersion());
          infos.add(monInfo);
        });
    return infos;
  }

  @Override
  public InfosEtudiantDto chercherInfosEtudiantPourProf(int idEtudiant) {
    InfosEtudiantDto monInfo = bizFactory.getInfosEtudiantDto();
    infosEtudiants.stream().filter(info -> info.getIdUtilisateur() == idEtudiant).forEach(info -> {
      monInfo.setIdUtilisateur(idEtudiant);
      monInfo.setStatut(info.getStatut());
      monInfo.setNom(info.getNom());
      monInfo.setPrenom(info.getPrenom());
      monInfo.setDateNaissance(info.getDateNaissance());
      monInfo.setNationalite(info.getNationalite());
      monInfo.setAdresse(info.getAdresse());
      monInfo.setTelephone(info.getTelephone());
      monInfo.setEmail(info.getEmail());
      monInfo.setSexe(info.getSexe());
      monInfo.setNbAnneesReussies(info.getNbAnneesReussies());
      monInfo.setIban(info.getIban());
      monInfo.setTitulaireCompte(info.getTitulaireCompte());
      monInfo.setNomBanque(info.getNomBanque());
      monInfo.setCodeBic(info.getCodeBic());
      monInfo.setNoVersion(info.getNoVersion());
    }); // il n'y en aura qu'un de toute façon.
    if (monInfo.getIdUtilisateur() == 0) { // on n'a trouvé aucun infosEtudiant
      return null;
    }
    return monInfo;
  }

}
