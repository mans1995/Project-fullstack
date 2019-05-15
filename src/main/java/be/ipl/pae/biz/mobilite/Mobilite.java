package be.ipl.pae.biz.mobilite;

public interface Mobilite extends MobiliteDto {

  /**
   * Une mobilite valide est une mobilite dont les champs obligatoires sont remplis et respectent le
   * REGEX.
   * 
   * @return true si la mobilite est valide et false sinon
   */
  boolean mobiliteEstValide();

}
