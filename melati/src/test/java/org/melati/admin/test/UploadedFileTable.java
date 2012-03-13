// Delete this line to prevent overwriting of this file

package org.melati.admin.test;


import org.melati.admin.test.generated.UploadedFileTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>UploadedFileTable</code> object.
 * <p>
 * Description: 
 *   A file uploaded by a user. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>UploadedFile</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> filename </td><td> String </td><td> The path name of the file, 
 * relative to the server root </td></tr> 
 * <tr><td> datefield </td><td> Date </td><td> &nbsp; </td></tr> 
 * <tr><td> datefielddropdown </td><td> Date </td><td> &nbsp; </td></tr> 
 * <tr><td> datefieldmydropdown </td><td> Date </td><td> &nbsp; </td></tr> 
 * <tr><td> timestampfield </td><td> Timestamp </td><td> &nbsp; </td></tr> 
 * </table> 
 * 
 * see  org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class UploadedFileTable<T extends UploadedFile> extends UploadedFileTableBase<UploadedFile> {

 /**
  * Constructor.
  * 
  * see org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public UploadedFileTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

