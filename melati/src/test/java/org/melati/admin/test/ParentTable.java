package org.melati.admin.test;

import org.melati.admin.test.generated.ParentTableBase;
import org.melati.poem.DefinitionSource;
import org.melati.poem.Database;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub 
 * for a <code>ParentTable</code> object.
 *
 * 
 * <table> 
 * <caption>
 * Field summary for SQL table <code>Parent</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr> 
 * <tr><td> name </td><td> String </td><td> &nbsp; </td></tr> 
 * <tr><td> image </td><td> UploadedImage </td><td> &nbsp; </td></tr> 
 * </table> 
 * 
 * See  org.melati.poem.prepro.TableDef#generateTableJava
 */
public class ParentTable<T extends Parent> extends ParentTableBase<Parent> {

 /**
  * Constructor.
  * 
  * @see org.melati.poem.prepro.TableDef#generateTableJava 
  * @param database          the POEM database we are using
  * @param name              the name of this <code>Table</code>
  * @param definitionSource  which definition is being used
  * @throws PoemException    if anything goes wrong
  */
  public ParentTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here
  
  public Parent ensure(String name) { 
    Parent p = (Parent)getNameColumn().firstWhereEq(name);
    if (p == null) { 
      p = (Parent)newPersistent();
      p.setName(name);
      getNameColumn().ensure(p);
    }
    return p;
  }

}

