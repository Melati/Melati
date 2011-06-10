package org.melati.poem.test;

import java.sql.ResultSet;

import org.melati.poem.test.generated.DynamicTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.Persistent;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>DynamicTable</code> object.
 * <p>
 * Description: 
 *   A table for adding and deleting columns from. 
 * </p>
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Dynamic</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> Name </td></tr> 
 * </table> 
 * 
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class DynamicTable extends DynamicTableBase {

  protected Dynamic dynamicOne, dynamicTwo;

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public DynamicTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
  /**
   * Create guestUser and administratorUser.
   * {@inheritDoc}
   * @see org.melati.poem.Table#unifyWithDB(java.sql.ResultSet)
   */
//  public synchronized void unifyWithDB(ResultSet colDescs, String troidColumnName)
//      throws PoemException {
//    super.unifyWithDB(colDescs, troidColumnName);
//  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.JdbcTable#postInitialise()
   */
  public void postInitialise() {
    super.postInitialise();
    dynamicOne = (Dynamic)newPersistent();
    dynamicOne.setName_unsafe("dynamicOne");

    dynamicTwo = (Dynamic)newPersistent();
    dynamicTwo.setName_unsafe("dynamicTwo");
    dynamicOne = (Dynamic)getNameColumn().ensure(dynamicOne);
    dynamicTwo = (Dynamic)getNameColumn().ensure(dynamicTwo);
  }

  /**
   * @return persistent 
   */
  public Persistent two() {
    return dynamicTwo;
  }
  /**
   * @return persistent 
   */
  public Persistent one() {
    return dynamicOne;
  }
}

