// Delete this line to prevent overwriting of this file

package org.melati.poem.test;


import org.melati.poem.test.generated.EverythingAbstractTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>EverythingAbstractTable</code> object.
 * <p>
 * Description: 
 *   Every datatype in one abstract table. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>EverythingAbstract</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> binaryfield </td><td> byte[] </td><td> &nbsp; </td></tr> 
 * <tr><td> stringfield </td><td> String </td><td> &nbsp; </td></tr> 
 * <tr><td> passwordfield </td><td> String </td><td> &nbsp; </td></tr> 
 * <tr><td> booleanfield </td><td> Boolean </td><td> &nbsp; </td></tr> 
 * <tr><td> datefield </td><td> Date </td><td> &nbsp; </td></tr> 
 * <tr><td> doublefield </td><td> Double </td><td> &nbsp; </td></tr> 
 * <tr><td> integerfield </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> longfield </td><td> Long </td><td> &nbsp; </td></tr> 
 * <tr><td> bigdecimalfield </td><td> BigDecimal </td><td> &nbsp; </td></tr> 
 * <tr><td> timestampfield </td><td> Timestamp </td><td> &nbsp; </td></tr> 
 * </table> 
 * 
 * see  org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class EverythingAbstractTable<T extends EverythingAbstract> extends EverythingAbstractTableBase<EverythingAbstract> {

 /**
  * Constructor.
  * 
  * see org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public EverythingAbstractTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

