package org.melati.poem.test;

import org.melati.poem.test.generated.EmptyAbstractTableTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>EmptyAbstractTableTable</code> object.
 * Description: 
 *   Empty abstract table to create a stub 
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>EmptyAbstractTable</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class EmptyAbstractTableTable extends EmptyAbstractTableTableBase {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public EmptyAbstractTableTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
