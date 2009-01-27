package org.melati.admin.test;

import org.melati.admin.test.generated.ChildTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ChildTable</code> object.
 *
 * 
 * <table> 
 * <tr><th colspan='3'>
 * Field summary for SQL table <code>Child</code>
 * </th></tr>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> parent </td><td> Parent </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> The name </td></tr> 
 * <tr><td> image </td><td> UploadedImage </td><td> &nbsp; </td></tr> 
 * <tr><td> deleted </td><td> Boolean </td><td> &nbsp; </td></tr> 
 * </table> 
 * 
 * @see  org.melati.poem.prepro.TableDef#generateTableJava 
 */
public class ChildTable extends ChildTableBase {

 /**
  * Constructor.
  * 
  * @see org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ChildTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
  
  public Child ensure(Parent parent, String name) { 
    Child p = (Child)getNameColumn().firstWhereEq(name);
    if (p == null) { 
      p = (Child)newPersistent();
      p.setName(name);
      p.setParent(parent);
      p.setDeleted(Boolean.FALSE);
      getNameColumn().ensure(p);
    }
    return p;
  }

}

