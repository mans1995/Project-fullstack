package be.ipl.pae.biz.programme;

public class ProgrammeImpl implements Programme {

  private int id;
  private String libelle;



  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
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
    ProgrammeImpl other = (ProgrammeImpl) obj;
    if (id != other.id) {
      return false;
    }
    if (libelle == null) {
      if (other.libelle != null) {
        return false;
      }
    } else if (!libelle.equals(other.libelle)) {
      return false;
    }
    return true;
  }

  public ProgrammeImpl() {
    super();
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getLibelle() {
    return libelle;
  }

  @Override
  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }


}
