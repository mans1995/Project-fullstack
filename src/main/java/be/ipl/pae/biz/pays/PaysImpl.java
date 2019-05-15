package be.ipl.pae.biz.pays;

public class PaysImpl implements Pays {

  private int idPays;
  private int idProgramme;
  private String nomPays;
  private String codePays;

  public PaysImpl() {
    super();
  }

  @Override
  public int getIdPays() {
    return idPays;
  }

  @Override
  public void setIdPays(int idPays) {
    this.idPays = idPays;
  }

  @Override
  public int getIdProgramme() {
    return idProgramme;
  }

  @Override
  public void setIdProgramme(int idProgramme) {
    this.idProgramme = idProgramme;
  }

  @Override
  public String getNomPays() {
    return nomPays;
  }

  @Override
  public void setNomPays(String nomPays) {
    this.nomPays = nomPays;
  }

  @Override
  public String getCodePays() {
    return codePays;
  }

  @Override
  public void setCodePays(String codePays) {
    this.codePays = codePays;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((codePays == null) ? 0 : codePays.hashCode());
    result = prime * result + idProgramme;
    result = prime * result + ((nomPays == null) ? 0 : nomPays.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PaysImpl other = (PaysImpl) obj;
    if (codePays == null) {
      if (other.codePays != null) {
        return false;
      }
    } else if (!codePays.equals(other.codePays)) {
      return false;
    }
    if (idProgramme != other.idProgramme) {
      return false;
    }
    if (nomPays == null) {
      if (other.nomPays != null) {
        return false;
      }
    } else if (!nomPays.equals(other.nomPays)) {
      return false;
    }
    return true;
  }



}
