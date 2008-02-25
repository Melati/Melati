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
 * <tr><td> filename </td><td> String </td><td> The name of the file, as 
 * uploaded </td></tr> 
 * <tr><td> path </td><td> String </td><td> The full path to this file on the 
 * server </td></tr> 
 * <tr><td> description </td><td> String </td><td> A description of the file 
 * </td></tr> 
 * <tr><td> size </td><td> String </td><td> The size of this file </td></tr> 
 * <tr><td> when </td><td> Date </td><td> The date on which this file was 
 * uploaded </td></tr> 
 * <tr><td> uploadedby </td><td> User </td><td> The user who uploaded this 
 * file </td></tr> 
 * <tr><td> ownedby </td><td> User </td><td> The account to which this file 
 * belongs </td></tr> 
 * <tr><td> deleted </td><td> Boolean </td><td> Whether this file been 
 * deleted or not </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class UploadedFileTable extends UploadedFileTableBase {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableJava 
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

