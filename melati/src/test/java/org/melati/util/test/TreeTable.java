// Delete this line to prevent overwriting of this file

package org.melati.util.test;


import org.melati.poem.JdbcTable;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.Persistent;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable inheritance hook.
 */
public class TreeTable<P extends Persistent> extends JdbcTable<P> {

 /**
  * Constructor. 
  * 
  * @see org.melati.poem.prepro.DSD#generateProjectTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */

  public TreeTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
  // Don't forget to delete first line to prevent overwriting
}


