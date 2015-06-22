// Delete this line to prevent overwriting of this file

package org.melati.example.odmg;


import org.melati.example.odmg.generated.ChildTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ChildTable</code> object.
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>Child</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> &nbsp; </td></tr> 
 * <tr><td> parent </td><td> Parent </td><td> &nbsp; </td></tr> 
 * </table> 
 * 
 * See org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class ChildTable<T extends Child> extends ChildTableBase<Child> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ChildTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

