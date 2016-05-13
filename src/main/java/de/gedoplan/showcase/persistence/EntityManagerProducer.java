package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.service.DbSelectorService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.SynchronizationType;

/**
 * EntityManager-Producer und -Disposer.
 *
 * @author dw
 */
@ApplicationScoped
public class EntityManagerProducer
{
  @Inject
  DbSelectorService                                       dbSelectorService;

  private ConcurrentHashMap<String, EntityManagerFactory> factoryMap = new ConcurrentHashMap<>();

  /**
   * EntityManagerFactory zum aktuellen DB-URL liefern.
   *
   * Die EMFs werden in der Map {@link #factoryMap} gehalten. Sollte dort der Eintrag für den aktuellen URL fehlen, wird er atomar
   * erzeugt.
   *
   * @return EntityManagerFactory
   */
  private EntityManagerFactory fetchEntityManagerFactory()
  {
    // Aktueller URL kommt aus anderem Service
    String url = this.dbSelectorService.getCurrentDbUrl();

    // Falls nötig, Map-Eintrag für URL erstellen
    this.factoryMap.computeIfAbsent(url, u -> {
      Map<String, String> prop = new HashMap<>();

      // dabei URL als Property übergeben
      prop.put("javax.persistence.jdbc.url", url);

      // und DDL erlauben
      prop.put("eclipselink.ddl-generation", "create-or-extend-tables");
      prop.put("eclipselink.ddl-generation.output-mode", "database");
      prop.put("hibernate.hbm2ddl.auto", "update");

      return Persistence.createEntityManagerFactory("showcase", prop);
    });

    return this.factoryMap.get(url);
  }

  /**
   * Producer für EntityManager.
   * 
   * @return EntityManager
   */
  @Produces
  @RequestScoped
  EntityManager createEntityMnager()
  {
    // EntityManager mit Anbindung an JTA liefern
    return fetchEntityManagerFactory().createEntityManager(SynchronizationType.SYNCHRONIZED);
  }

  /**
   * Disposer für EntityManager.
   * 
   * @param entityManager EntityManager
   */
  void closeEntityManager(@Disposes EntityManager entityManager)
  {
    if (entityManager.isOpen())
    {
      entityManager.close();
    }
  }

  /**
   * Alle genutzten EntityManagerFactories schliessen.
   */
  @PreDestroy
  void preDestroy()
  {
    this.factoryMap.forEach((url, emf) -> emf.close());
  }
}
