package org.melati.example.contacts;


import org.melati.example.contacts.generated.CategoryTableBase;
import org.melati.poem.Database;
import org.melati.poem.DefinitionSource;
import org.melati.poem.PoemException;

/**
 * Melati POEM generated, programmer modifiable stub
 * for a <code>CategoryTable</code> object.
 * <p>
 * Description:
 * A Category for Contacts.
 * </p>
 * <p>
 * <p>
 * <table>
 * <caption>
 * Field summary for SQL table <code>Category</code>
 * </caption>
 * <tr><th>Name</th><th>Type</th><th>Description</th></tr>
 * <tr><td> id </td><td> Integer </td><td> &nbsp; </td></tr>
 * <tr><td> name </td><td> String </td><td> Category Name </td></tr>
 * </table>
 * <p>
 * See org.melati.poem.prepro.TableDef#generateTableJava
 *
 * @author timp
 * @since 2017-10-18
 */
public class CategoryTable<T extends Category> extends CategoryTableBase<Category> {

  /**
   * Constructor.
   * <p>
   * See org.melati.poem.prepro.TableDef#generateTableJava
   *
   * @param database         the POEM database we are using
   * @param name             the name of this <code>Table</code>
   * @param definitionSource which definition is being used
   * @throws PoemException if anything goes wrong
   */
  public CategoryTable(
      Database database, String name,
      DefinitionSource definitionSource) throws PoemException {
    super(database, name, definitionSource);
  }

  // programmer's domain-specific code here

  public Category ensure(String name) {
    Category it = (Category) getNameColumn().firstWhereEq(name);
    if (it != null)
      return it;
    else {
      it = (Category) newPersistent();
      it.setName(name);
      return (Category) getNameColumn().ensure(it);
    }
  }

}

