package org.melati.admin.test;

import org.melati.Melati;
import org.melati.admin.AdminSpecialised;
import org.melati.admin.test.generated.UploadedFileBase;
import org.melati.template.MarkupLanguage;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>Persistent</code> <code>UploadedFile</code> object.
 * 
 * <p> 
 * Description: 
 *   A file uploaded by a user. 
 * </p>
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>UploadedFile</code>
 * </caption>
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
 * See org.melati.poem.prepro.TableDef#generatePersistentJava
 */
public class UploadedFile 
    extends UploadedFileBase 
    implements AdminSpecialised {

 /**
  * Constructor 
  * for a <code>Persistent</code> <code>UploadedFile</code> object.
  * <p>
  * Description: 
  *   A file uploaded by a user. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generatePersistentJava 
  */
  public UploadedFile() { }

  // programmer's domain-specific code here
  
  public String adminHandle(
      Melati melatiContext,
      MarkupLanguage markupLanguage)
      throws Exception {
      return null;
    }

    public String adminSpecialFacilities(
      Melati melatiContext,
      MarkupLanguage markupLanguage)
      throws Exception {
      return "org/melati/admin/test/UploadedFile-specials.wm";
    }
    

}

