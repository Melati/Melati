package org.melati.poem.odmg;

import java.util.*;

import org.melati.poem.*;
import org.melati.*;

/** POEM implementation of the ODMG Database API **/
class Database implements org.odmg.Database
{
  public static final String cvs = "$Id";

  /** hide this from general use **/
  private Database() {}
  /** provide a package factory method - so we could return a different type if needed **/
  static final Database getNewDatabase() { return new Database(); }

  private org.melati.poem.Database _poemDB = null;
  private String _logicalDB = null;

  org.melati.poem.Database getPoemDatabase() { return _poemDB; }

  /** Opens a connection to the db
    @parameter openParameters the Poem logical db name
  **/
  public void open(String openParameters, int openType) 
    throws org.odmg.ODMGException
  {
    _logicalDB = openParameters;
    try {
      _poemDB = LogicalDatabase.getDatabase(_logicalDB);
    } catch (DatabaseInitException err) {
      throw new org.odmg.ODMGException(err.getMessage());
    }
  }

  public void close() 
  { 
    _poemDB = null;
    _cachedTables = null;
  }

  private Map _cachedTables = new HashMap();

  /** Retrieves a collection wrapper for the selected table i
    @parameter objectIdentifier the name of the table for which a collection is required
  **/
  public Object lookup(String objectIdentifier) 
    throws org.odmg.ObjectNameNotFoundException
  { 
    if (_poemDB == null) throw new org.odmg.DatabaseClosedException();
    Object theObject = _cachedTables.get(objectIdentifier);
    if (theObject == null)
    {
      theObject = new PoemTableAsDCollection(_poemDB.getTable(objectIdentifier));
      _cachedTables.put(objectIdentifier,theObject);
    }
    return theObject;
  }

  /** Not supported with the Poem / ODMG wrapper **/
  public void makePersistent(Object objectToBePersistent) { throw new org.odmg.NotImplementedException(); }
  /** Not supported with the Poem / ODMG wrapper **/
  public void deletePersistent(Object persistedObject) { throw new org.odmg.NotImplementedException(); }
  /** Not supported with the Poem / ODMG wrapper **/
  public void bind(Object objectToBePersistent, String objectIdentifier) { throw new org.odmg.NotImplementedException(); }
  /** Not supported with the Poem / ODMG wrapper **/
  public void unbind(String objectIdentifier) { throw new org.odmg.NotImplementedException(); }
   
}

