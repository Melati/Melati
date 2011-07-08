/**
 * 
 */
package org.melati.poem.dbms.test;

import org.melati.poem.BigDecimalPoemType;
import org.melati.poem.BinaryPoemType;
import org.melati.poem.BooleanPoemType;
import org.melati.poem.DatePoemType;
import org.melati.poem.DoublePoemType;
import org.melati.poem.IntegerPoemType;
import org.melati.poem.LongPoemType;
import org.melati.poem.StringPoemType;
import org.melati.poem.TimestampPoemType;
import org.melati.poem.dbms.DbmsFactory;

/**
 * @author timp
 * @since 23 Jan 2007
 *
 */
public class MySQLTest extends DbmsSpec {

  /**
   * Constructor.
   * @param name
   */
  public MySQLTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.test.DbmsSpec#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.dbms.test.DbmsSpec#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected void setObjectUnderTest() {
    it = DbmsFactory.getDbms("org.melati.poem.dbms.MySQL");
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#createTableOptionsSql()}.
   */
  public void testCreateTableOptionsSql() {
    assertEquals(" TYPE='InnoDB' ", it.createTableOptionsSql());
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getStringSqlDefinition(java.lang.String)}.
   */
  public void testGetStringSqlDefinition() throws Exception {
    assertEquals("VARCHAR(0)", it.getStringSqlDefinition(0));    
    assertEquals("text",  it.getStringSqlDefinition(-1));
  }
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getSqlDefinition(java.lang.String)}.
   * @throws Exception 
   */
  public void testGetSqlDefinition() throws Exception {
    assertEquals("bool", it.getSqlDefinition("BOOLEAN"));
    assertEquals("DOUBLE PRECISION", it.getSqlDefinition("DOUBLE PRECISION"));
    assertEquals("INT8", it.getSqlDefinition("INT8"));
    assertEquals("Big Decimal", it.getSqlDefinition("Big Decimal"));
  }


  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getBinarySqlDefinition(int)}.
   */
  public void testGetBinarySqlDefinition() throws Exception {
    assertEquals("BLOB", it.getBinarySqlDefinition(0));        
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getIndexLength(org.melati.poem.Column)}.
   * @throws Exception 
   */
  public void testGetIndexLength() throws Exception {
    assertEquals("", it.getIndexLength(getDb().getUserTable().troidColumn()));
    assertEquals("(30)", it.getIndexLength(getDb().getTableInfoTable().getDescriptionColumn()));
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * canRepresent(org.melati.poem.PoemType, org.melati.poem.PoemType)}.
   */
  public void testCanRepresent() {
    assertNull(it.canRepresent(StringPoemType.nullableInstance, IntegerPoemType.nullableInstance));
    assertNull(it.canRepresent(IntegerPoemType.nullableInstance,StringPoemType.nullableInstance));

    assertNull(it.canRepresent(new BigDecimalPoemType(false),new BigDecimalPoemType(true)));
    assertTrue(it.canRepresent(new BigDecimalPoemType(true),new BigDecimalPoemType(false))
               instanceof BigDecimalPoemType);

    assertNull(it.canRepresent(new StringPoemType(true, 255), new StringPoemType(true, -1)));

    assertTrue(it.canRepresent(
            new StringPoemType(true, -1), new StringPoemType(true, -1)) 
            instanceof StringPoemType);

    assertNull(it.canRepresent(new TimestampPoemType(true), new DatePoemType(true))); 

    assertTrue(it.canRepresent(
            new BooleanPoemType(true), new BooleanPoemType(false)) 
            instanceof BooleanPoemType);

    assertTrue(it.canRepresent(
            new IntegerPoemType(true), new BooleanPoemType(false)) 
            instanceof BooleanPoemType);

    assertTrue(it.canRepresent(
            new IntegerPoemType(false), new BooleanPoemType(false)) 
            instanceof BooleanPoemType);

    assertNull(it.canRepresent(new DoublePoemType(false), new BigDecimalPoemType(true)));

    assertNull(it.canRepresent(new DoublePoemType(true), new BigDecimalPoemType(false))); 

    assertNull(it.canRepresent(new IntegerPoemType(false), new LongPoemType(true)));

    
    assertNull(it.canRepresent(new BinaryPoemType(false,10), new BinaryPoemType(true,10))); 
    assertNull(it.canRepresent(new BinaryPoemType(true,10), new BinaryPoemType(true,11))); 
    assertTrue(it.canRepresent(
            new BinaryPoemType(true,-1), 
            new BinaryPoemType(true,-1)) instanceof BinaryPoemType); 
    assertTrue(it.canRepresent(
            new BinaryPoemType(true,2500), 
            new BinaryPoemType(true,10)) instanceof BinaryPoemType); 
 
  
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getForeignKeyDefinition(java.lang.String, java.lang.String, 
   * java.lang.String, java.lang.String, java.lang.String)}.
   */
  public void testGetForeignKeyDefinition() {
    assertEquals(" ADD FOREIGN KEY (user) REFERENCES user(id) ON DELETE CASCADE",
            it.getForeignKeyDefinition("test", "user", "user", "id", "delete"));
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * getPrimaryKeyDefinition(java.lang.String)}.
   */
  public void testGetPrimaryKeyDefinition() {
    assertEquals(" ADD PRIMARY KEY (name)", it.getPrimaryKeyDefinition("name"));
  }
  
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * givesCapabilitySQL(java.lang.Integer, java.lang.String)}.
   */
  public void testGivesCapabilitySQL() {
    String actual = it.givesCapabilitySQL(new Integer(42), "hello");
    String expected = "SELECT " + it.getQuotedName("groupmembership") + ".* "
            + "FROM " + it.getQuotedName("groupmembership") + " LEFT JOIN "
            + it.getQuotedName("groupcapability") + " ON "
            + it.getQuotedName("groupmembership") + "."
            + it.getQuotedName("group") + " =  "
            + it.getQuotedName("groupcapability") + "."
            + it.getQuotedName("group") + " WHERE " + it.getQuotedName("user")
            + " = 42" + " " + "AND " + it.getQuotedName("groupcapability")
            + "." + it.getQuotedName("group") + " IS NOT NULL " + "AND "
            + it.getQuotedName("capability") + " = hello";

    assertEquals(expected, actual);

  }
  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#
   * caseInsensitiveRegExpSQL(java.lang.String, java.lang.String)}.
   */
  public void testCaseInsensitiveRegExpSQL() {
    String expected = "a LIKE '%b%'";
    String actual = it.caseInsensitiveRegExpSQL("a", "b");
    assertEquals(expected, actual);
  }

  public void testCaseInsensitiveRegExpSQLQuoted() {
    String expected = "a LIKE '%b%'";
    String actual = it.caseInsensitiveRegExpSQL("a", "\"b\"");
    assertEquals(expected, actual);
  }

  public void testCaseInsensitiveRegExpSQLBlank() {
    String expected = " LIKE '%%'";
    String actual = it.caseInsensitiveRegExpSQL("", "");
    assertEquals(expected, actual);
  }

  /**
   * Test method for {@link org.melati.poem.dbms.Dbms#createTableSql()}.
   */
  public void testCreateTableSql() {
    if (getDb().getDbms() == it)
      assertEquals("CREATE TABLE user (id INT NOT NULL, " + 
              "name VARCHAR(60) NOT NULL, " + 
              "login VARCHAR(255) NOT NULL, " + 
              "password VARCHAR(20) NOT NULL) TYPE='InnoDB' ", 
              it.createTableSql(getDb().getUserTable()));
  }
  
  


}
