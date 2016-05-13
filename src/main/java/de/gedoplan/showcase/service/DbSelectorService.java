package de.gedoplan.showcase.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;

/**
 * DB-Auswahl-Service.
 *
 * Dieser Service symbolisiert die aktuelle DB-Auswahl durch den User. Die aktuell ausgewählte DB ist in der Property currentDbUrl
 * enthalten. Da der Service SessionScoped ist, hat jeder User seine eigen DB.
 *
 * Die verfügbaren DBs sind hier fest programmiert, könnten im echten Leben aber dynamisch ermittelt werden.
 *
 * @author dw
 */
@SessionScoped
public class DbSelectorService implements Serializable
{
  private List<String> dbUrls = new ArrayList<>();

  private String       currentDbUrl;

  @PostConstruct
  void postConstruct()
  {
    this.dbUrls.add("jdbc:h2:mem:showcase_1;DB_CLOSE_DELAY=-1");
    this.dbUrls.add("jdbc:h2:mem:showcase_2;DB_CLOSE_DELAY=-1");
    this.dbUrls.add("jdbc:h2:mem:showcase_3;DB_CLOSE_DELAY=-1");

    this.currentDbUrl = this.dbUrls.get(0);
  }

  public String getCurrentDbUrl()
  {
    return this.currentDbUrl;
  }

  public void setCurrentDbUrl(String currentDbUrl)
  {
    this.currentDbUrl = currentDbUrl;
  }

  public List<String> getDbUrls()
  {
    return this.dbUrls;
  }
}
