package org.melati.example.contacts;

import org.melati.example.contacts.generated.ContactBase;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>Persistent</code> <code>Contact</code> object.
 * 
 * <p> 
 * Description: 
 *   A Contact. 
 * </p>
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Contact</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> Contact Name </td></tr> 
 * <tr><td> owner </td><td> Contact </td><td> Contact who owns this contact 
 * </td></tr> 
 * <tr><td> address </td><td> String </td><td> Contact Address </td></tr> 
 * <tr><td> updates </td><td> Integer </td><td> How many times has this 
 * record been updated? </td></tr> 
 * <tr><td> lastupdated </td><td> Date </td><td> When was this last updated? 
 * </td></tr> 
 * <tr><td> lastupdateuser </td><td> User </td><td> Who last updated this? 
 * </td></tr> 
 * </table> 
 * 
 * @generator org.melati.poem.prepro.TableDef#generateMainJava 
 */
public class Contact extends ContactBase {

 /**
  * Constructor 
  * for a <code>Persistent</code> <code>Contact</code> object.
  * <p>
  * Description: 
  *   A Contact. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generateMainJava 
  */
  public Contact() { }

  // programmer's domain-specific code here
}
