package org.melati.example.contacts;

import org.melati.example.contacts.generated.ContactCategoryTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ContactCategoryTable</code> object.
 * Description: 
 *   Contacts Categories 
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>ContactCategory</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> category </td><td> Category </td><td> Category </td></tr> 
 * <tr><td> contact </td><td> Contact </td><td> Contact </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class ContactCategoryTable extends ContactCategoryTableBase {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ContactCategoryTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}
