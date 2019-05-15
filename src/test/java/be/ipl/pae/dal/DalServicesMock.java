package be.ipl.pae.dal;

import java.sql.PreparedStatement;

public class DalServicesMock implements DalBackendServices, DalServices {

  @Override
  public void startTransaction() {

  }

  @Override
  public void commitTransaction() {

  }

  @Override
  public void rollbackTransaction() {

  }

  @Override
  public PreparedStatement getPreparedStatement(String query) {
    return null;
  }

}
