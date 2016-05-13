package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.entity.Note;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * DB-Zugriffsklasse für {@link Note}.
 * 
 * @author dw
 */
public class NoteRepository
{
  @Inject
  EntityManager entityManager;

  @Transactional(value = TxType.MANDATORY)
  public void persist(Note note)
  {
    this.entityManager.persist(note);
  }

  public Note findById(String id)
  {
    return this.entityManager.find(Note.class, id);
  }
}
