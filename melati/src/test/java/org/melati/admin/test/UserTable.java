// Delete this line to prevent overwriting of this file

package org.melati.admin.test;


import org.melati.admin.test.generated.UserTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>UserTable</code> object.
 * <p>
 * Description: 
 *   An emailed User. 
 * </p>
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>User</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> email </td><td> String </td><td> The user's email address 
 * </td></tr> 
 * </table> 
 * 
 * See org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class UserTable<T extends User> extends UserTableBase<User> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public UserTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

