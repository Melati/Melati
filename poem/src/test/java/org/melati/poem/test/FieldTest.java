package org.melati.poem.test;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Enumeration;

import org.melati.poem.AccessPoemException;
import org.melati.poem.BaseFieldAttributes;
import org.melati.poem.Field;
import org.melati.poem.FieldAttributes;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.PoemLocale;
import org.melati.poem.ReferencePoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.User;

/**
 * @author timp
 * @since 30/11/2006
 */
public class FieldTest extends PoemTestCase {

  Field<String> stringField = null;
  Field<Integer> integerField = null;
  /**
   * Constructor for FieldTest.
   * @param name
   */
  public FieldTest(String name) {
    super(name);
  }

  /**
   * @see PoemTestCase#setUp()
   */
  protected void setUp()
      throws Exception {
    super.setUp();
    stringField = new Field<String>("stringfield", new BaseFieldAttributes<String>("stringName",
        "String Display Name", "String description",
        StringPoemType.nullableInstance,
        20,    // width
        1,     // height
        null,  // render info
        false, // indexed
        true,  // userEditable
        true   // user createbale
        ));
    integerField = new Field<Integer>(new Integer(1), new BaseFieldAttributes<Integer>("integerName",
        "Integer Display Name", "Integer description",
        IntegerPoemType.nullableInstance,
        20,    // width
        1,     // height
        null,  // render info
        false, // indexed
        true,  // userEditable
        true   // user createbale
        ));
  }

  /**
   * @see PoemTestCase#tearDown()
   */
  protected void tearDown()
      throws Exception {
    super.tearDown();
  }

  /**
   * @see org.melati.poem.Field#Field(Object, FieldAttributes)
   */
  public void testFieldObjectFieldAttributes() {
    
  }

  /**
   * @see org.melati.poem.Field#Field(AccessPoemException, FieldAttributes)
   * Deliberately allowing abuse of type system
   */
  @SuppressWarnings({ "unchecked"})
  public void testFieldAccessPoemExceptionFieldAttributes() {
    @SuppressWarnings("rawtypes")
    Field f = new Field<String>(new AccessPoemException(), stringField);
    try {
      f.getRaw();
      fail("Should have blown up");
    } catch (AccessPoemException e) {
      e = null;
    }
    try {
      f.getRawString();
      fail("Should have blown up");
    } catch (AccessPoemException e) {
      e = null;
    }
    try {
      f.getCooked();
      fail("Should have blown up");
    } catch (AccessPoemException e) {
      e = null;
    }
    try {
      f.getCookedString(PoemLocale.HERE, DateFormat.MEDIUM);
      fail("Should have blown up");
    } catch (AccessPoemException e) {
      e = null;
    }
    try {
      f.sameRawAs(integerField);
      fail("Should have blown up");
    } catch (AccessPoemException e) {
      e = null;
    }
  }

  /**
   * @see org.melati.poem.Field#clone()
   */
  @SuppressWarnings("unchecked")
  public void testClone() {
    Field<String> s2 = (Field<String>)stringField.clone();
    assertFalse(s2.equals(stringField));
    assertEquals(stringField.getName(), s2.getName());

    Field<Integer> i2 = (Field<Integer>)integerField.clone();
    assertFalse(i2.equals(integerField));
    assertEquals(integerField.getName(), i2.getName());
  }

  /**
   * @see org.melati.poem.Field#getName()
   */
  public void testGetName() {
    assertEquals("stringName", stringField.getName());
    assertEquals("integerName", integerField.getName());
  }

  /**
   * @see org.melati.poem.Field#getDisplayName()
   */
  public void testGetDisplayName() {
    assertEquals("String Display Name", stringField.getDisplayName());
    assertEquals("Integer Display Name", integerField.getDisplayName());
  }

  /**
   * @see org.melati.poem.Field#getDescription()
   */
  public void testGetDescription() {
    assertEquals("String description", stringField.getDescription());
    assertEquals("Integer description", integerField.getDescription());

  }

  /**
   * @see org.melati.poem.Field#getType()
   */
  public void testGetType() {
    assertEquals(StringPoemType.nullableInstance, stringField.getType());
    assertEquals(IntegerPoemType.nullableInstance, integerField.getType());

  }

  /**
   * @see org.melati.poem.Field#getIndexed()
   */
  public void testGetIndexed() {
    assertFalse(stringField.getIndexed());
    assertFalse(integerField.getIndexed());
  }

  /**
   * @see org.melati.poem.Field#getUserEditable()
   */
  public void testGetUserEditable() {
    assertTrue(stringField.getUserEditable());
    assertTrue(integerField.getUserEditable());
  }

  /**
   * @see org.melati.poem.Field#getUserCreateable()
   */
  public void testGetUserCreateable() {
    assertTrue(stringField.getUserCreateable());
    assertTrue(integerField.getUserCreateable());
  }

  /**
   * @see org.melati.poem.Field#getWidth()
   */
  public void testGetWidth() {
    assertEquals(20, stringField.getWidth());
    assertEquals(20, integerField.getWidth());
  }

  /**
   * @see org.melati.poem.Field#getHeight()
   */
  public void testGetHeight() {
    assertEquals(1, stringField.getHeight());
    assertEquals(1, integerField.getHeight());
  }

  /**
   * @see org.melati.poem.Field#getRenderInfo()
   */
  public void testGetRenderInfo() {
    assertNull(stringField.getRenderInfo());
    assertNull(integerField.getRenderInfo());
  }

  /**
   * @see org.melati.poem.Field#getRaw()
   */
  public void testGetRaw() {
    assertEquals("stringfield", stringField.getRaw());
    assertEquals(new Integer(1), integerField.getRaw());
  }

  /**
   * @see org.melati.poem.Field#getRawString()
   */
  public void testGetRawString() {
    assertEquals("stringfield", stringField.getRawString());
    assertEquals("1", integerField.getRawString());
  }

  /**
   * @see org.melati.poem.Field#getCooked()
   */
  public void testGetCooked() {
    assertEquals("stringfield", stringField.getCooked());
    assertEquals(new Integer(1), integerField.getCooked());
  }

  /**
   * @see org.melati.poem.Field#getCookedString(PoemLocale, int)
   */
  public void testGetCookedString() {
    assertEquals("stringfield", stringField.getCookedString(PoemLocale.HERE, DateFormat.MEDIUM));
    assertEquals("1", integerField.getCookedString(PoemLocale.HERE, DateFormat.MEDIUM));
  }

  /**
   * @see org.melati.poem.Field#withRaw(Object)
   */
  public void testWithRaw() {
    Field<String> stringField2 = stringField.withRaw("stringField2");
    assertEquals("stringField2", (String)stringField2.getRaw());
    
    Field<Integer> integerField2 = integerField.withRaw(new Integer(2));
    assertEquals(new Integer(2), integerField2.getRaw());
  }

  /**
   * @see org.melati.poem.Field#withNullable(boolean)
   */
  public void testWithNullable() {
    assertTrue(stringField.getType().getNullable());
    Field<String> stringField2 = stringField.withNullable(false);
    assertFalse(stringField2.getType().getNullable());

    assertTrue(integerField.getType().getNullable());
    Field<Integer> integerField2 = integerField.withNullable(false);
    assertFalse(integerField2.getType().getNullable());
  }

  /**
   * @see org.melati.poem.Field#withName(String)
   */
  public void testWithName() {
    Field<String> stringField2 = stringField.withName("stringField2");
    assertEquals("stringField2", stringField2.getName());
    Field<Integer> integerField2 = integerField.withName("integerField2");
    assertEquals("integerField2", integerField2.getName());
  }

  /**
   * @see org.melati.poem.Field#withDescription(String)
   */
  public void testWithDescription() {
    Field<String> stringField2 = stringField.withDescription("stringField2");
    assertEquals("stringField2", stringField2.getDescription());
    Field<Integer> integerField2 = integerField.withDescription("integerField2");
    assertEquals("integerField2", integerField2.getDescription());
  }

  /**
   * @see org.melati.poem.Field#getPossibilities()
   */
  public void testGetPossibilities() {
    Enumeration en = stringField.getPossibilities();
    assertNull(en);
    en = integerField.getPossibilities();
    assertNull(en);
  }

  /**
   * @see org.melati.poem.Field#getFirst1000Possibilities()
   */
  public void testGetFirst1000Possibilities() {
    Enumeration en = stringField.getFirst1000Possibilities();
    assertNull(en);
    en = integerField.getFirst1000Possibilities();
    assertNull(en);
    
    Field tableCategoryField = getDb().getTableInfoTable().getCategoryColumn().asEmptyField();
    Enumeration possibleCategories = tableCategoryField.getFirst1000Possibilities();
    int counter = 0;
    while (possibleCategories.hasMoreElements()) {
      counter++;
      possibleCategories.nextElement();
    }
    assertEquals(3, counter);
  }

  /**
   * @see org.melati.poem.Field#sameRawAs(Field)
   */
  public void testSameRawAs() {
    Field<String> stringField2 = stringField.withRaw("stringField2");
    assertFalse(stringField.sameRawAs(stringField2));
    stringField2 = stringField2.withRaw("stringfield");
    assertTrue(stringField.sameRawAs(stringField2));
    Field<Integer> integerField2 = integerField.withRaw(new Integer(2));
    assertFalse(integerField.sameRawAs(integerField2));
    integerField2 = integerField2.withRaw(new Integer(1));
    assertTrue(integerField.sameRawAs(integerField2));
  }

  /**
   * @see org.melati.poem.Field#dump(PrintStream)
   */
  public void testDump() {

  }

  /**
   * @see org.melati.poem.Field#toString()
   */
  public void testToString() {

  }

  /**
   * @see org.melati.poem.Field#basic(Object, String, org.melati.poem.PoemType)
   */
  public void testBasic() {
    Field f1 = Field.basic("basicField", "basicField", StringPoemType.nullableInstance);
    Field f2 = Field.string("basicField", "basicField");
    assertTrue(f1.sameRawAs(f2));
  }

  /**
   * @see org.melati.poem.Field#string(String, String)
   */
  public void testString() {

  }

  /**
   * @see org.melati.poem.Field#integer(Integer, String)
   */
  public void testInteger() {
    Field f1 = Field.basic(new Integer(13), "integerField", IntegerPoemType.nullableInstance);
    Field f2 = Field.integer(new Integer(13), "integerField");
    assertTrue(f1.sameRawAs(f2));
  }

  /**
   * @see org.melati.poem.Field#reference(org.melati.poem.Persistent, String)
   */
  public void testReferencePersistentString() {
    User u = getDb().guestUser();
    Field f1 = Field.basic(u.getTroid(), "referenceField", 
            new ReferencePoemType(getDb().getUserTable(), true));
    Field f2 = Field.reference(u, "referenceField");
    assertTrue(f1.sameRawAs(f2));
  }

  /**
   * @see org.melati.poem.Field#reference(org.melati.poem.Table, String)
   */
  public void testReferenceTableString() {
    Field f1 = Field.basic(null, "referenceField", 
            new ReferencePoemType(getDb().getUserTable(), true));
    Field f2 = Field.reference(getDb().getUserTable(), "referenceField");
    assertTrue(f1.sameRawAs(f2));
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  public void testHashCode() {

  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  public void testEquals() {

  }

}
