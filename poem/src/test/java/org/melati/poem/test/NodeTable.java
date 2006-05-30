package org.melati.poem.test;

import org.melati.poem.test.generated.NodeTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>NodeTable</code> object.
 * <p>
 * Description: 
 *   A Tree Node. 
 * </p>
 *
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
 * @generator  org.melati.poem.prepro.TableDef#generateTableMainJava 
 */
public class NodeTable extends NodeTableBase {

 /**
  * Constructor.
  * 
  * @generator org.melati.poem.prepro.TableDef#generateTableMainJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public NodeTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
}

