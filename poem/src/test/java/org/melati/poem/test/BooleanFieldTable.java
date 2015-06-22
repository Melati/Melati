// Delete this line to prevent overwriting of this file

package org.melati.poem.test;


import org.melati.poem.test.generated.BooleanFieldTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>BooleanFieldTable</code> object.
 * <p>
 * Description: 
 *   A table with only a boolean field in it. 
 * </p>
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>BooleanField</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> booleanfield </td><td> Boolean </td><td> &nbsp; </td></tr> 
 * </table> 
 * 
 * See org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class BooleanFieldTable<T extends BooleanField> extends BooleanFieldTableBase<BooleanField> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public BooleanFieldTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

