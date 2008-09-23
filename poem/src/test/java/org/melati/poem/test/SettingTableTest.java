/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.PoemTypeFactory;
import org.melati.poem.Setting;
import org.melati.poem.SettingTable.UnsetException;

/**
 * @author timp
 */
public class SettingTableTest extends PoemTestCase {

  /**
   * Constructor for SettingTableTest.
   * 
   * @param name
   */
  public SettingTableTest(String name) {
    super(name);
  }

  /*
   * @see PoemTestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
  }

  /*
   * @see PoemTestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.poem.SettingTable#postInitialise()
   */
  public void testPostInitialise() {

  }

  /**
   * @see org.melati.poem.SettingTable#SettingTable(Database, String,
   *      DefinitionSource)
   */
  public void testSettingTable() {

  }

  /**
   * @see org.melati.poem.SettingTable#getCooked(String)
   */
  public void testGetCooked() {
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    //getDb().setLogSQL(true);
    Setting stringSetting = getDb().getSettingTable().ensure("stringSetting",
        "set", "String", "A set string setting");
    stringSetting.setValue("new");
    assertEquals("new", stringSetting.getCooked());
    assertEquals("new", (String) getDb().getSettingTable().getCooked(
    "stringSetting"));
    assertNull(getDb().getSettingTable().getCooked("unsetSetting"));
    Setting setting2 = getDb().getSettingTable().ensure("nullIntegerSetting", null, "Null Integer",
         "A null Integer setting");
    assertNull(getDb().getSettingTable().getCooked("nullIntegerSetting"));
    // Second time from cache
    assertNull(getDb().getSettingTable().getCooked("nullIntegerSetting"));
    Setting setting3 = getDb().getSettingTable().ensure("integerSettingA", 13, "Integer",
         "A set Integer setting");
    assertEquals(new Integer(13), getDb().getSettingTable().getCooked(
         "integerSettingA"));
    assertEquals(new Integer(13), getDb().getSettingTable().getCooked(
         "integerSettingA"));
    
    stringSetting.delete();
    setting2.delete();
    setting3.delete();
  }

  /**
   * @see org.melati.poem.SettingTable#get(String)
   */
  public void testGet() {
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    Setting setting1 = getDb().getSettingTable().ensure("integerSettingG", 12, "Integer",
        "A set Integer setting");
    assertEquals("12", getDb().getSettingTable().get("integerSettingG"));
    assertNull(getDb().getSettingTable().get("unsetSetting"));
    setting1.delete();

  }

  /**
   * @see org.melati.poem.SettingTable#getOrDie(String)
   */
  public void testGetOrDie() {
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    Setting s = getDb().getSettingTable().ensure("integerSettingGOD", 12, "Integer",
    "A set Integer setting");
    assertEquals("12", (String) getDb().getSettingTable().getOrDie("integerSettingGOD"));
    try {
      getDb().getSettingTable().getOrDie("unsetSetting");
      fail("Should have blown up");
    } catch (UnsetException e) {
      e = null;
    }
    s.delete();
  }

  /**
   * @see org.melati.poem.SettingTable# ensure(String, PoemTypeFactory, Object,
   *      String, String)
   */
  public void testEnsureStringPoemTypeFactoryObjectStringString() {
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    Setting stringSetting1 = getDb().getSettingTable().ensure("stringSetting",
        PoemTypeFactory.STRING, "set", "String", "A set string setting");
    Setting stringSetting2 = getDb().getSettingTable().ensure("stringSetting",
        "set", "String", "A set string setting");
    assertEquals(stringSetting1, stringSetting2);
    stringSetting1.delete();

    
    Setting integerSetting1 = getDb().getSettingTable().ensure(
        "integerSetting", PoemTypeFactory.INTEGER, new Integer(13), "Integer",
        "An integer setting");
    Setting integerSetting2 = getDb().getSettingTable().ensure(
        "integerSetting", 13, "Integer",
        "An integer setting");
    assertEquals(integerSetting1, integerSetting2);
    integerSetting1.delete();
    Setting booleanSetting1 = getDb().getSettingTable().ensure(
        "booleanSetting", PoemTypeFactory.BOOLEAN, new Boolean(true), "Boolean",
        "A boolean setting");
    Setting booleanSetting2 = getDb().getSettingTable().ensure(
        "booleanSetting", true, "Boolean",
        "A boolean setting");
    assertEquals(booleanSetting1, booleanSetting2);
    booleanSetting1.delete();
  }

  /**
   * @see org.melati.poem.SettingTable# ensure(String, String, String, String)
   */
  public void testEnsureStringStringStringString() {

  }

  /**
   * @see org.melati.poem.SettingTable# ensure(String, int, String, String)
   */
  public void testEnsureStringIntStringString() {

  }

  /**
   * @see org.melati.poem.SettingTable# ensure(String, boolean, String, String)
   */
  public void testEnsureStringBooleanStringString() {

  }

}
