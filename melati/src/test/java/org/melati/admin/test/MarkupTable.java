package org.melati.admin.test;

import org.melati.admin.test.generated.MarkupTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>MarkupTable</code> object.
 * <p>
 * Description: 
 *   An HTML markup fragment. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Markup</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> text </td><td> String </td><td> The text </td></tr> 
 * </table> 
 * 
 * @see  org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class MarkupTable extends MarkupTableBase {

 /**
  * Constructor.
  * 
  * @see org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public MarkupTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

