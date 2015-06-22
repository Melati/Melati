// Delete this line to prevent overwriting of this file

package org.melati.poem.test;


import org.melati.poem.test.generated.ProtectedTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ProtectedTable</code> object.
 * <p>
 * Description: 
 *   A protected table. 
 * </p>
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>Protected</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> spy </td><td> User </td><td> Spy </td></tr> 
 * <tr><td> mission </td><td> String </td><td> Secret mission </td></tr> 
 * <tr><td> canRead </td><td> Capability </td><td> Capability required to 
 * read this row  </td></tr> 
 * <tr><td> canWrite </td><td> Capability </td><td> Capability required to 
 * write this row  </td></tr> 
 * <tr><td> canDelete </td><td> Capability </td><td> Capability required to 
 * delete this row  </td></tr> 
 * <tr><td> canSelect </td><td> Capability </td><td> Capability required to 
 * select this row  </td></tr> 
 * <tr><td> deleted </td><td> Boolean </td><td> Whether this record is 
 * existant  </td></tr> 
 * </table> 
 * 
 * See org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class ProtectedTable<T extends Protected> extends ProtectedTableBase<Protected> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ProtectedTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

