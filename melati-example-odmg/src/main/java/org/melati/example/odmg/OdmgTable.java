package org.melati.example.odmg;

import org.melati.poem.Database;
import org.melati.poem.DefinitionSource;
import org.melati.poem.JdbcTable;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifyable inheritance hook.
 */
public class OdmgTable extends JdbcTable {

 /**
  * Constructor. 
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableBaseJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */

  public OdmgTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }


 /**
  * Constructor.
  *
  * @generator org.melati.poem.prepro.TableDef#generateTableBaseJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @throws PoemException    if anything goes wrong
  */
  public OdmgTable(
      Database database, String name) throws PoemException {
    this(database, name, DefinitionSource.dsd);
  }

  // programmer's domain-specific code here
}




