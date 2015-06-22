// Delete this line to prevent overwriting of this file

package org.melati.poem.test;


import org.melati.poem.test.generated.EAExtendedTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>EAExtendedTable</code> object.
 * <p>
 * Description: 
 *   Every datatype inheritted from an abstract table and normally defined. 
 * </p>
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>EAExtended</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> binaryfield2 </td><td> byte[] </td><td> Second Binary Field 
 * </td></tr> 
 * <tr><td> stringfield2 </td><td> String </td><td> Second String Field 
 * </td></tr> 
 * <tr><td> passwordfield2 </td><td> String </td><td> Second Password Field 
 * </td></tr> 
 * <tr><td> booleanfield2 </td><td> Boolean </td><td> Second Boolean Field 
 * </td></tr> 
 * <tr><td> datefield2 </td><td> Date </td><td> Second Date Field </td></tr> 
 * <tr><td> doublefield2 </td><td> Double </td><td> Second Double Field 
 * </td></tr> 
 * <tr><td> integerfield2 </td><td> Integer </td><td> Second Integer Field 
 * </td></tr> 
 * <tr><td> longfield2 </td><td> Long </td><td> Second Long Field </td></tr> 
 * <tr><td> bigdecimalfield2 </td><td> BigDecimal </td><td> Second BigDecimal 
 * Field </td></tr> 
 * <tr><td> timestampfield2 </td><td> Timestamp </td><td> Second Timestamp 
 * Field </td></tr> 
 * </table> 
 * 
 * See org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class EAExtendedTable<T extends EAExtended> extends EAExtendedTableBase<EAExtended> {

 /**
  * Constructor.
  * 
  * See org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public EAExtendedTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

