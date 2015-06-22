// Delete this line to prevent overwriting of this file

package org.melati.admin.test;


import org.melati.admin.test.generated.UploadedImageTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>UploadedImageTable</code> object.
 * <p>
 * Description: 
 *   An image uploaded by a user. 
 * </p>
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>UploadedImage</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> filename </td><td> String </td><td> The path name of the file, 
 * relative to the server root </td></tr> 
 * </table> 
 * 
 * See org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class UploadedImageTable<T extends UploadedImage> extends UploadedImageTableBase<UploadedImage> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public UploadedImageTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

