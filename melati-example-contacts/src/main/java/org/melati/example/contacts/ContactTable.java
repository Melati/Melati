package org.melati.example.contacts;


import org.melati.example.contacts.generated.ContactTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ContactTable</code> object.
 * <p>
 * Description: 
 *   A Contact. 
 * </p>
 *
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
 * see  org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class ContactTable<T extends Contact> extends ContactTableBase<Contact> {

 /**
  * Constructor.
  * 
  * see org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ContactTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

  /**
   * @return the existing or newly created Contact
   */
  public Contact ensure(String name, Contact owner, String address) {
    Contact contact = (Contact)getNameColumn().firstWhereEq(name);
    if (contact != null)
      return contact;
    else {
      contact = (Contact)newPersistent();
      contact.setName(name);
      contact.setOwner(owner);
      contact.setAddress(address);

      return (Contact)getNameColumn().ensure(contact);
    }
  }


}

