/**
 * 
 */
package org.melati.poem;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author timp
 * @since 12 Jun 2007
 * 
 */
public final class TableFactory {

  /**
   * Disable instantiation.
   */
  private TableFactory() {
  }

  /**
   * @param db
   *          the database to create table in and lookup existing tables
   * @param pojo
   *          class to introspect
   * @return A new or existing table
   */
  public static Table fromInstance(Database db, Object pojo) {
    return fromClass(db, pojo.getClass());
  }

  /**
   * @param db
   *          the database to create table in and lookup existing tables
   * @param clazz
   *          class to introspect
   * @return A new or existing table
   */
  public static Table fromClass(Database db, Class clazz) {
    String name = clazz.getName();
    System.err.println(name);
    if (clazz.isPrimitive())
      throw new IllegalArgumentException(
              "Cannot create a Table for a primitive type");
    if (clazz.isArray())
      throw new IllegalArgumentException("Cannot create a Table for an array");
    String simpleName = name.substring(name.lastIndexOf('.') + 1);
    try {
      return db.getTable(simpleName);
    } catch (NoSuchTablePoemException e) {
      System.err.println(simpleName + " table not found");
    }
    // OK So we have to create one
    TableInfo tableInfo = (TableInfo)db.getTableInfoTable().newPersistent();
    tableInfo.setName(simpleName);
    tableInfo.setDisplayname("Introspection created table");
    tableInfo.setDisplayorder(13);
    tableInfo.setSeqcached(new Boolean(false));
    tableInfo.setCategory_unsafe(new Integer(1));
    tableInfo.setCachelimit(0);
    tableInfo.makePersistent();

    Table table = new Table(db, simpleName, DefinitionSource.runtime);
    table.defineColumn(new ExtraColumn(table, "id", TroidPoemType.it,
            DefinitionSource.runtime, table.extrasIndex++));
    table.setTableInfo(tableInfo);
    table.unifyWithColumnInfo();
    try {
      table.unifyWithDB(null);
    } catch (SQLException e) {
      throw new SQLPoemException(e);
    }

    PoemThread.commit();
    db.defineTable(table);

    Hashtable props = new Hashtable();
    java.lang.reflect.Field[] fields = clazz.getFields();
    for (int i = 0; i < fields.length; i++) {
      if (Modifier.isPublic(fields[i].getModifiers())
              && !fields[i].getType().isArray()) {
        System.err.println("adding public field " + fields[i]);
        Prop p = (Prop)props.get(fields[i].getName());
        if (p == null) {
          p = new Prop(fields[i].getName());
          p.setClazz(fields[i].getType());
          p.setPublic(true);
        } else 
          throw new RuntimeException("Field " + fields[i].getName() + " Already seen.");
      }
    }
    Method[] methods = clazz.getMethods();

    for (int i = 0; i < methods.length; i++) {
      // System.err.println(methods[i]);
      if (Modifier.isPublic(methods[i].getModifiers())) {
        if (methods[i].getName().startsWith("set")
                && methods[i].getGenericParameterTypes().length == 1
                && !methods[i].getGenericParameterTypes()[0].getClass()
                        .isArray()) {
          System.err.println("Storing setter " + methods[i].getName());
          String propName = methods[i].getName().substring(3);
          Prop p = (Prop)props.get(propName);
          Class setClass = methods[i].getGenericParameterTypes()[0].getClass();
          if (p == null) {
            p = new Prop(propName);
            p.setSet(setClass);
            props.put(propName, p);
          } else { 
            if (p.clazz != null && p.clazz == setClass)
              p.setSet(setClass);
            else 
              if (p.getGot() != null && p.getGot() == setClass)
                p.setSet(setClass);
          }
        }
        if (methods[i].getName().startsWith("get")
                && methods[i].getGenericParameterTypes().length == 0
                && !methods[i].getReturnType().isArray()) {
          System.err.println("Storing getter " + methods[i].getName());
          String propName = methods[i].getName().substring(3);
          Prop p = (Prop)props.get(propName);
          Class gotClass = methods[i].getReturnType();
          if (p == null) {
            p = new Prop(propName);
            p.setGot(methods[i].getReturnType());
            props.put(propName, p);
          }else { 
            if (p.clazz != null && p.clazz == gotClass)
              p.setGot(gotClass);
            else 
              if (p.getSet() != null && p.getSet() == gotClass)
                p.setGot(gotClass);
          }
        }
      }
    }
    Enumeration propsEn = props.elements();
    while (propsEn.hasMoreElements()) {
      Prop p = (Prop)propsEn.nextElement();
      System.err.println("Adding column:" + p.getName());
      if (p.getGot() != null && !p.isPublic)
        addColumn(table, p.getName(), p.getGot(), p.getGot() == p.getSet());
    }

    return table;
  }

  private static void addColumn(Table table, String name, Class field,
          boolean hasSetter) {
    if (field == null)
      throw new RuntimeException("Field " + name + " has null class");
    ColumnInfo columnInfo = (ColumnInfo)table.getDatabase()
            .getColumnInfoTable().newPersistent();
    columnInfo.setTableinfo(table.getInfo());
    columnInfo.setName(name);
    columnInfo.setDisplayname(name);
    columnInfo.setDisplayorder(99);
    columnInfo.setSearchability(Searchability.yes);
    columnInfo.setIndexed(false);
    columnInfo.setUnique(false);
    columnInfo.setDescription("An introspected member");
    columnInfo.setUsercreateable(hasSetter);
    columnInfo.setUsereditable(hasSetter);
    columnInfo.setSize(8);
    columnInfo.setWidth(20);
    columnInfo.setHeight(1);
    columnInfo.setPrecision(0);
    columnInfo.setScale(0);
    columnInfo.setNullable(false);
    columnInfo.setDisplaylevel(DisplayLevel.record);
    if (field == java.lang.Boolean.class) {
      columnInfo.setTypefactory(PoemTypeFactory.BOOLEAN);
      columnInfo.setSize(1);
      columnInfo.setWidth(10);
    } else if (field == boolean.class) {
      columnInfo.setTypefactory(PoemTypeFactory.BOOLEAN);
      columnInfo.setSize(1);
      columnInfo.setWidth(10);
    } else if (field == java.lang.Integer.class) {
      columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
    } else if (field == int.class) {
      columnInfo.setTypefactory(PoemTypeFactory.INTEGER);
    } else if (field == java.lang.Double.class) {
      columnInfo.setTypefactory(PoemTypeFactory.DOUBLE);
    } else if (field == double.class) {
      columnInfo.setTypefactory(PoemTypeFactory.DOUBLE);
    } else if (field == java.lang.Long.class) {
      columnInfo.setTypefactory(PoemTypeFactory.LONG);
    } else if (field == long.class) {
      columnInfo.setTypefactory(PoemTypeFactory.LONG);
    } else if (field == java.math.BigDecimal.class) {
      columnInfo.setTypefactory(PoemTypeFactory.BIGDECIMAL);
      columnInfo.setPrecision(22);
      columnInfo.setScale(2);
    } else if (field == java.lang.String.class) {
      columnInfo.setTypefactory(PoemTypeFactory.STRING);
      columnInfo.setSize(-1);
    } else if (field == java.sql.Date.class) {
      columnInfo.setTypefactory(PoemTypeFactory.DATE);
    } else if (field == java.sql.Timestamp.class) {
      columnInfo.setTypefactory(PoemTypeFactory.TIMESTAMP);
    }
    // FIXME What about Binary?
    // FIXME Also arrays
    else {
      Table referredTable = fromClass(table.getDatabase(), field);
      columnInfo.setTypefactory(PoemTypeFactory.forCode(table.getDatabase(),
              referredTable.info.troid().intValue()));
    }
    columnInfo.makePersistent();
    table.addColumnAndCommit(columnInfo);
  }

  /**
   * Details of a member variable.
   */
  static final class Prop {
    String name = null;
    Class set = null;
    Class got = null;
    Class clazz = null;
    boolean isPublic;
    
    Prop(String name) {
      this.name = name;
    }

    /**
     * @param got
     *          the Class of the getter
     */
    public void setGot(Class got) {
      this.got = got;
    }

    /**
     * @param set
     *          the Class of the setter
     */
    public void setSet(Class set) {
      this.set = set;
    }

    /**
     * @return the name
     */
    public String getName() {
      return name;
    }

    /**
     * @return the set
     */
    public Class getSet() {
      return set;
    }

    /**
     * @return the got
     */
    public Class getGot() {
      return got;
    }

    /**
     * @return the clazz
     */
    public Class getClazz() {
      return clazz;
    }

    /**
     * @param clazz the clazz to set
     */
    public void setClazz(Class clazz) {
      this.clazz = clazz;
    }

    /**
     * @return the isPublic
     */
    public boolean isPublic() {
      return isPublic;
    }

    /**
     * @param isPublic the isPublic to set
     */
    public void setPublic(boolean isPublic) {
      this.isPublic = isPublic;
    }
  }
}
