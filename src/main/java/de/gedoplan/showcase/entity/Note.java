package de.gedoplan.showcase.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Demo-Entity.
 *
 * @author dw
 */
@Entity
@Access(AccessType.FIELD)
public class Note
{
  @Id
  private String id;

  private String text;

  public Note(String id, String text)
  {
    this.id = id;
    this.text = text;
  }

  protected Note()
  {
  }

  public String getId()
  {
    return this.id;
  }

  public String getText()
  {
    return this.text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (getClass() != obj.getClass())
    {
      return false;
    }
    Note other = (Note) obj;
    if (this.id == null)
    {
      if (other.id != null)
      {
        return false;
      }
    }
    else if (!this.id.equals(other.id))
    {
      return false;
    }
    return true;
  }

  @Override
  public String toString()
  {
    return "Note [id=" + this.id + ", text=" + this.text + "]";
  }
}
