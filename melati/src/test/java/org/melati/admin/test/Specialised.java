package org.melati.admin.test;

import org.melati.Melati;
import org.melati.admin.AdminSpecialised;
import org.melati.admin.test.generated.SpecialisedBase;
import org.melati.template.MarkupLanguage;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>Persistent</code> <code>Specialised</code> object.
 * 
 * <p> 
 * Description: 
 *   An AdminSpecialised object. 
 * </p>
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Specialised</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> The name of the thing </td></tr> 
 * </table> 
 * 
 * @generator org.melati.poem.prepro.TableDef#generatePersistentJava 
 */
public class Specialised 
    extends SpecialisedBase 
    implements AdminSpecialised {

 /**
  * Constructor 
  * for a <code>Persistent</code> <code>Specialised</code> object.
  * <p>
  * Description: 
  *   An AdminSpecialised object. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generatePersistentJava 
  */
  public Specialised() { }

  // programmer's domain-specific code here
  
  public String adminHandle(
      Melati melatiContext,
      MarkupLanguage markupLanguage)
      throws Exception {
      return "org/melati/admin/test/Specialised";
    }

  public String adminSpecialFacilities(
      Melati melatiContext,
      MarkupLanguage markupLanguage)
      throws Exception {
      return null;
    }

}

