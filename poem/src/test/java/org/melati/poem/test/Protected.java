package org.melati.poem.test;

import org.melati.poem.AccessPoemException;
import org.melati.poem.test.generated.ProtectedBase;

/**
 * Melati POEM generated, programmer modifiable stub for a
 * <code>Persistent</code> <code>Protected</code> object.
 * 
 * <p>
 * Description: A protected table.
 * </p>
 * 
 * <table>
 * <caption> Field summary for SQL table <code>Protected</code>
 * </caption>
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td> id </td>
 * <td> Integer </td>
 * <td> &nbsp; </td>
 * </tr>
 * <tr>
 * <td> name </td>
 * <td> String </td>
 * <td> Name </td>
 * </tr>
 * <tr>
 * <td> canread </td>
 * <td> Capability </td>
 * <td> Capability required to read this row </td>
 * </tr>
 * <tr>
 * <td> canwrite </td>
 * <td> Capability </td>
 * <td> Capability required to write this row </td>
 * </tr>
 * </table>
 * 
 * See org.melati.poem.prepro.TableDef#generateMainJava
 */
public class Protected extends ProtectedBase {

  /**
   * Constructor for a <code>Persistent</code> <code>Protected</code>
   * object.
   * <p>
   * Description: A protected table.
   * </p>
   * 
   * See org.melati.poem.prepro.TableDef#generateMainJava
   */
  public Protected() {
  }

  // programmer's domain-specific code here
  /** FIXME this needs locking */
  public Integer getCanReadTroid() throws AccessPoemException {
    // readLock();
    return getCanRead_unsafe();
  }

}
