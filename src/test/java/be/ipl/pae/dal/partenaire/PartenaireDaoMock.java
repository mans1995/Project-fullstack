package be.ipl.pae.dal.partenaire;

import be.ipl.pae.biz.BizFactory;
import be.ipl.pae.biz.partenaire.PartenaireDto;
import be.ipl.pae.config.Inject;
import be.ipl.pae.dal.partenaire.PartenaireDao;
import be.ipl.pae.exceptions.FatalException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartenaireDaoMock implements PartenaireDao {

  @Inject
  BizFactory bizFactory;

  private boolean throwException = false;

  private Set<PartenaireDto> partenaires;

  public PartenaireDaoMock() {
    partenaires = new HashSet<>();
  }

  @Override
  public int ajouterPartenaire(PartenaireDto partenaireDto) {
    if (throwException) {
      throw new FatalException();
    }
    PartenaireDto partenaire = remplirPartenaire(partenaireDto);
    partenaires.add(partenaire);
    return partenaire.getId();
  }

  @Override
  public ArrayList<PartenaireDto> listerPartenaires() {
    if (throwException) {
      throw new FatalException();
    }
    return new ArrayList<PartenaireDto>(partenaires);
  }

  @Override
  public PartenaireDto trouverPartenaireParNomComplet(String nomComplet) {
    if (throwException) {
      throw new FatalException();
    }
    PartenaireDto partenaire = partenaires.stream()
        .filter(x -> x.getNomComplet().equals(nomComplet)).findAny().orElse(null);
    if (partenaire == null) {
      return null;
    }
    return partenaire;
  }

  @Override
  public List<PartenaireDto> listerPartenairesParPays(int idPays) {
    if (throwException) {
      throw new FatalException();
    }
    List<PartenaireDto> mesPartenaires = new ArrayList<>();
    partenaires.stream().filter(partenaire -> partenaire.getIdPays() == idPays)
        .sorted(new Comparator<PartenaireDto>() {
          @Override
          public int compare(PartenaireDto o1, PartenaireDto o2) {
            // Des pays alphabétiquement plus bas/haut ont un id plus petit/grand.
            return o1.getIdPays() - o2.getIdPays();
          }
        }).forEach(partenaire -> {
          PartenaireDto partenaireDto = remplirPartenaire(partenaire);
          mesPartenaires.add(partenaireDto);
        });
    return mesPartenaires;
  }

  @Override
  public List<PartenaireDto> listerPartenairesParVille(String ville) {
    if (throwException) {
      throw new FatalException();
    }
    List<PartenaireDto> mesPartenaires = new ArrayList<>();
    partenaires.stream().filter(partenaire -> ville.equals(partenaire.getVille()))
        .sorted(new Comparator<PartenaireDto>() {
          @Override
          public int compare(PartenaireDto o1, PartenaireDto o2) {
            return o1.getVille().compareTo(o2.getVille());
          }
        }).forEach(partenaire -> {
          PartenaireDto partenaireDto = remplirPartenaire(partenaire);
          mesPartenaires.add(partenaireDto);
        });
    return mesPartenaires;
  }

  @Override
  public List<PartenaireDto> listerPartenairesParNomLegal(String nomLegal) {
    if (throwException) {
      throw new FatalException();
    }
    List<PartenaireDto> mesPartenaires = new ArrayList<>();
    partenaires.stream().filter(partenaire -> partenaire.getNomLegal().contains(nomLegal))
        .sorted(new Comparator<PartenaireDto>() {
          @Override
          public int compare(PartenaireDto o1, PartenaireDto o2) {
            return o1.getNomComplet().compareTo(o2.getNomComplet());
          }
        }).forEach(partenaire -> {
          PartenaireDto partenaireDto = remplirPartenaire(partenaire);
          mesPartenaires.add(partenaireDto);
        });
    return mesPartenaires;
  }

  private PartenaireDto remplirPartenaire(PartenaireDto partenaireDto) {
    
    PartenaireDto partenaire = bizFactory.getPartenaireDto();
    partenaire.setTypeCode(partenaireDto.getTypeCode());
    partenaire.setNomLegal(partenaireDto.getNomLegal());
    partenaire.setNomDaffaires(partenaireDto.getNomDaffaires());
    partenaire.setNomComplet(partenaireDto.getNomComplet());
    partenaire.setDepartement(partenaireDto.getDepartement());
    partenaire.setTypeOrganisation(partenaireDto.getTypeOrganisation());
    partenaire.setNombreEmployes(partenaireDto.getNombreEmployes());
    partenaire.setAdresse(partenaireDto.getAdresse());
    partenaire.setIdPays(partenaireDto.getIdPays());
    partenaire.setRegion(partenaireDto.getRegion());
    partenaire.setCodePostal(partenaireDto.getCodePostal());
    partenaire.setVille(partenaireDto.getVille());
    partenaire.setEmail(partenaireDto.getEmail());
    partenaire.setSiteWeb(partenaireDto.getSiteWeb());
    partenaire.setTelephone(partenaireDto.getTelephone());
    return partenaire;
  }

}
