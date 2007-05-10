package org.melati.util.test;

import java.util.Enumeration;
import java.util.Vector;

import org.melati.poem.util.EnumUtils;
import org.melati.util.Treeable;
import org.melati.util.test.generated.NodeBase;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>Persistent</code> <code>Node</code> object.
 * 
 * <p> 
 * Description: 
 *   A Tree Node. 
 * </p>
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Node</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> Contact Name </td></tr> 
 * <tr><td> parent </td><td> Node </td><td> Parent of this Node </td></tr> 
 * </table> 
 * 
 * @generator org.melati.poem.prepro.TableDef#generateMainJava 
 */
public class Node extends NodeBase implements Treeable {

 /**
  * Constructor 
  * for a <code>Persistent</code> <code>Node</code> object.
  * <p>
  * Description: 
  *   A Tree Node. 
  * </p>
  * 
  * @generator org.melati.poem.prepro.TableDef#generateMainJava 
  */
  public Node() { }

  // programmer's domain-specific code here
  /**
   * @see org.melati.util.Treeable#getChildren()
   */
  public Treeable[] getChildren() {
    return (Node.arrayOf(getNodeTable().getParentColumn().referencesTo(this)));
  }
  
  /**
   * Create an array from a vector. 
   * 
   * @param v the vector
   * @return an array
   */
  public static Treeable[] arrayOf(Vector v) {
    Treeable[] arr;
    synchronized (v) {
      arr = new Treeable[v.size()];
      v.copyInto(arr);
    }

    return arr;
  }

  /**
   * Create an array from an enumeration. 
   * 
   * @param v the enumeration
   * @return an array
   */
  public static Treeable[] arrayOf(Enumeration e) {
    Vector v = EnumUtils.vectorOf(e);
    return arrayOf(v);
  }
  
}

