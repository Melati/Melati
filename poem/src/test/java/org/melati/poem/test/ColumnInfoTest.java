/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.ColumnInfo;
import org.melati.poem.DisplayLevel;


/**
 * @author timp
 *
 */
public class ColumnInfoTest extends PoemTestCase {

  /**
   * @param name
   */
  public ColumnInfoTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * {@inheritDoc}
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Test method for {@link org.melati.poem.ColumnInfo#assertCanRead(org.melati.poem.AccessToken)}.
   */
  public void testAssertCanReadAccessToken() {

  }

  /**
   * Test method for {@link org.melati.poem.ColumnInfo#setTableinfoTroid(java.lang.Integer)}.
   */
  public void testSetTableinfoTroid() {
    ColumnInfo ci = getDb().getUserTable().getNameColumn().getColumnInfo();
    try { 
      ci.setTableinfoTroid(new Integer(99));
      fail("Should have bombed");
    } catch (IllegalArgumentException e) { 
      e = null;
    }
  }

  /**
   * Test method for {@link org.melati.poem.ColumnInfo#setName(java.lang.String)}.
   */
  public void testSetName() {

  }

  /**
   * Test method for {@link org.melati.poem.ColumnInfo#
   * setDisplaylevelIndex(java.lang.Integer)}.
   */
  public void testSetDisplaylevelIndex() {
    ColumnInfo ci = getDb().getUserTable().getPasswordColumn().getColumnInfo();
    assertEquals(DisplayLevel.record, ci.getDisplaylevel());
    assertEquals("name", getDb().getUserTable().displayColumn().getName());
    assertEquals(DisplayLevel.primary, 
        getDb().getUserTable().getNameColumn().getDisplayLevel());
    ci.setDisplaylevelIndex(DisplayLevel.primary.getIndex());
    assertEquals(DisplayLevel.summary, 
        getDb().getUserTable().getNameColumn().getDisplayLevel());
    assertEquals(DisplayLevel.primary, ci.getDisplaylevel());
    assertEquals("password",  getDb().getUserTable().displayColumn().getName());
    ci.setDisplaylevelIndex(DisplayLevel.record.getIndex());
    getDb().getUserTable().getNameColumn().getColumnInfo().
        setDisplaylevelIndex(DisplayLevel.primary.getIndex());
    getDb().getUserTable().getPasswordColumn().getColumnInfo().
        setDisplaylevelIndex(DisplayLevel.record.getIndex());
  }

  /**
   * Test method for {@link org.melati.poem.ColumnInfo#getDsdQualifiers()}.
   */
  public void testGetDsdQualifiers() {
    ColumnInfo ci = getDb().getUserTable().getPasswordColumn().getColumnInfo();
    Enumeration them = ci.getDsdQualifiers();
    int counter = 0;
    while (them.hasMoreElements()) {
      counter++;
      them.nextElement();
    }
    assertEquals(18, counter);
  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * getPoemDatabaseTables()}.
   */
  public void testGetPoemDatabaseTables() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * getColumnInfoTable()}.
   */
  public void testGetColumnInfoTable() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * getId_unsafe()}.
   */
  public void testGetId_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * setId_unsafe(java.lang.Integer)}.
   */
  public void testSetId_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getId()}.
   */
  public void testGetId() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setId(java.lang.Integer)}.
   */
  public void testSetIdInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setId(int)}.
   */
  public void testSetIdInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getIdField()}.
   */
  public void testGetIdField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getTableinfo_unsafe()}.
   */
  public void testGetTableinfo_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setTableinfo_unsafe(java.lang.Integer)}.
   */
  public void testSetTableinfo_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getTableinfoTroid()}.
   */
  public void testGetTableinfoTroid() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getTableinfo()}.
   */
  public void testGetTableinfo() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setTableinfo(org.melati.poem.TableInfo)}.
   */
  public void testSetTableinfo() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getTableinfoField()}.
   */
  public void testGetTableinfoField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getName_unsafe()}.
   */
  public void testGetName_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setName_unsafe(java.lang.String)}.
   */
  public void testSetName_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getName()}.
   */
  public void testGetName() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getNameField()}.
   */
  public void testGetNameField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplayorder_unsafe()}.
   */
  public void testGetDisplayorder_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplayorder_unsafe(java.lang.Integer)}.
   */
  public void testSetDisplayorder_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplayorder()}.
   */
  public void testGetDisplayorder() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplayorder(java.lang.Integer)}.
   */
  public void testSetDisplayorderInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplayorder(int)}.
   */
  public void testSetDisplayorderInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplayorderField()}.
   */
  public void testGetDisplayorderField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getUsercreateable_unsafe()}.
   */
  public void testGetUsercreateable_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setUsercreateable_unsafe(java.lang.Boolean)}.
   */
  public void testSetUsercreateable_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getUsercreateable()}.
   */
  public void testGetUsercreateable() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setUsercreateable(java.lang.Boolean)}.
   */
  public void testSetUsercreateableBoolean() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setUsercreateable(boolean)}.
   */
  public void testSetUsercreateableBoolean1() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getUsercreateableField()}.
   */
  public void testGetUsercreateableField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplaylevel_unsafe()}.
   */
  public void testGetDisplaylevel_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplaylevel_unsafe(java.lang.Integer)}.
   */
  public void testSetDisplaylevel_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplaylevelIndex()}.
   */
  public void testGetDisplaylevelIndex() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplaylevel()}.
   */
  public void testGetDisplaylevel() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplaylevel(org.melati.poem.DisplayLevel)}.
   */
  public void testSetDisplaylevel() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplaylevelField()}.
   */
  public void testGetDisplaylevelField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getSearchability_unsafe()}.
   */
  public void testGetSearchability_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setSearchability_unsafe(java.lang.Integer)}.
   */
  public void testSetSearchability_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getSearchabilityIndex()}.
   */
  public void testGetSearchabilityIndex() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setSearchabilityIndex(java.lang.Integer)}.
   */
  public void testSetSearchabilityIndex() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getSearchability()}.
   */
  public void testGetSearchability() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setSearchability(org.melati.poem.Searchability)}.
   */
  public void testSetSearchability() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getSearchabilityField()}.
   */
  public void testGetSearchabilityField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplayorderpriority_unsafe()}.
   */
  public void testGetDisplayorderpriority_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplayorderpriority_unsafe(java.lang.Integer)}.
   */
  public void testSetDisplayorderpriority_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplayorderpriority()}.
   */
  public void testGetDisplayorderpriority() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplayorderpriority(java.lang.Integer)}.
   */
  public void testSetDisplayorderpriorityInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setDisplayorderpriority(int)}.
   */
  public void testSetDisplayorderpriorityInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getDisplayorderpriorityField()}.
   */
  public void testGetDisplayorderpriorityField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getSortdescending_unsafe()}.
   */
  public void testGetSortdescending_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setSortdescending_unsafe(java.lang.Boolean)}.
   */
  public void testSetSortdescending_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getSortdescending()}.
   */
  public void testGetSortdescending() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setSortdescending(java.lang.Boolean)}.
   */
  public void testSetSortdescendingBoolean() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setSortdescending(boolean)}.
   */
  public void testSetSortdescendingBoolean1() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getSortdescendingField()}.
   */
  public void testGetSortdescendingField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getIndexed_unsafe()}.
   */
  public void testGetIndexed_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setIndexed_unsafe(java.lang.Boolean)}.
   */
  public void testSetIndexed_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getIndexed()}.
   */
  public void testGetIndexed() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setIndexed(java.lang.Boolean)}.
   */
  public void testSetIndexedBoolean() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setIndexed(boolean)}.
   */
  public void testSetIndexedBoolean1() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getIndexedField()}.
   */
  public void testGetIndexedField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getUnique_unsafe()}.
   */
  public void testGetUnique_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setUnique_unsafe(java.lang.Boolean)}.
   */
  public void testSetUnique_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getUnique()}.
   */
  public void testGetUnique() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setUnique(java.lang.Boolean)}.
   */
  public void testSetUniqueBoolean() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setUnique(boolean)}.
   */
  public void testSetUniqueBoolean1() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getUniqueField()}.
   */
  public void testGetUniqueField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getIntegrityfix_unsafe()}.
   */
  public void testGetIntegrityfix_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#setIntegrityfix_unsafe(java.lang.Integer)}.
   */
  public void testSetIntegrityfix_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * getIntegrityfixIndex()}.
   */
  public void testGetIntegrityfixIndex() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * setIntegrityfixIndex(java.lang.Integer)}.
   */
  public void testSetIntegrityfixIndex() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * getIntegrityfix()}.
   */
  public void testGetIntegrityfix() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#
   * setIntegrityfix(org.melati.poem.StandardIntegrityFix)}.
   */
  public void testSetIntegrityfix() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ColumnInfoBase#getIntegrityfixField()}.
   */
  public void testGetIntegrityfixField() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#setRangelow_string(java.lang.String)}.
   */
  public void testSetRangelow_string() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#getRangelow_stringField()}.
   */
  public void testGetRangelow_stringField() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#setRangelimit_string(java.lang.String)}.
   */
  public void testSetRangelimit_string() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#getRangelimit_stringField()}.
   */
  public void testGetRangelimit_stringField() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#ValueInfo()}.
   */
  public void testValueInfo() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#toTypeParameter()}.
   */
  public void testToTypeParameter() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#getType()}.
   */
  public void testGetType() {

  }

  /**
   * Test method for {@link org.melati.poem.ValueInfo#fieldAttributesRenamedAs(org.melati.poem.FieldAttributes)}.
   */
  public void testFieldAttributesRenamedAs() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getValueInfoTable()}.
   */
  public void testGetValueInfoTable() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getDisplayname_unsafe()}.
   */
  public void testGetDisplayname_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setDisplayname_unsafe(java.lang.String)}.
   */
  public void testSetDisplayname_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getDisplayname()}.
   */
  public void testGetDisplayname() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setDisplayname(java.lang.String)}.
   */
  public void testSetDisplayname() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getDisplaynameField()}.
   */
  public void testGetDisplaynameField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getDescription_unsafe()}.
   */
  public void testGetDescription_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setDescription_unsafe(java.lang.String)}.
   */
  public void testSetDescription_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getDescription()}.
   */
  public void testGetDescription() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setDescription(java.lang.String)}.
   */
  public void testSetDescription() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getDescriptionField()}.
   */
  public void testGetDescriptionField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getUsereditable_unsafe()}.
   */
  public void testGetUsereditable_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setUsereditable_unsafe(java.lang.Boolean)}.
   */
  public void testSetUsereditable_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getUsereditable()}.
   */
  public void testGetUsereditable() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setUsereditable(java.lang.Boolean)}.
   */
  public void testSetUsereditableBoolean() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setUsereditable(boolean)}.
   */
  public void testSetUsereditableBoolean1() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getUsereditableField()}.
   */
  public void testGetUsereditableField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getTypefactory_unsafe()}.
   */
  public void testGetTypefactory_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setTypefactory_unsafe(java.lang.Integer)}.
   */
  public void testSetTypefactory_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getTypefactoryCode()}.
   */
  public void testGetTypefactoryCode() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setTypefactoryCode(java.lang.Integer)}.
   */
  public void testSetTypefactoryCode() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getTypefactory()}.
   */
  public void testGetTypefactory() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setTypefactory(org.melati.poem.PoemTypeFactory)}.
   */
  public void testSetTypefactory() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getTypefactoryField()}.
   */
  public void testGetTypefactoryField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getNullable_unsafe()}.
   */
  public void testGetNullable_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setNullable_unsafe(java.lang.Boolean)}.
   */
  public void testSetNullable_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getNullable()}.
   */
  public void testGetNullable() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setNullable(java.lang.Boolean)}.
   */
  public void testSetNullableBoolean() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setNullable(boolean)}.
   */
  public void testSetNullableBoolean1() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getNullableField()}.
   */
  public void testGetNullableField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getSize_unsafe()}.
   */
  public void testGetSize_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setSize_unsafe(java.lang.Integer)}.
   */
  public void testSetSize_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getSize()}.
   */
  public void testGetSize() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setSize(java.lang.Integer)}.
   */
  public void testSetSizeInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setSize(int)}.
   */
  public void testSetSizeInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getSizeField()}.
   */
  public void testGetSizeField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getWidth_unsafe()}.
   */
  public void testGetWidth_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setWidth_unsafe(java.lang.Integer)}.
   */
  public void testSetWidth_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getWidth()}.
   */
  public void testGetWidth() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setWidth(java.lang.Integer)}.
   */
  public void testSetWidthInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setWidth(int)}.
   */
  public void testSetWidthInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getWidthField()}.
   */
  public void testGetWidthField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getHeight_unsafe()}.
   */
  public void testGetHeight_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setHeight_unsafe(java.lang.Integer)}.
   */
  public void testSetHeight_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getHeight()}.
   */
  public void testGetHeight() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setHeight(java.lang.Integer)}.
   */
  public void testSetHeightInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setHeight(int)}.
   */
  public void testSetHeightInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getHeightField()}.
   */
  public void testGetHeightField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getPrecision_unsafe()}.
   */
  public void testGetPrecision_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setPrecision_unsafe(java.lang.Integer)}.
   */
  public void testSetPrecision_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getPrecision()}.
   */
  public void testGetPrecision() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setPrecision(java.lang.Integer)}.
   */
  public void testSetPrecisionInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setPrecision(int)}.
   */
  public void testSetPrecisionInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getPrecisionField()}.
   */
  public void testGetPrecisionField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getScale_unsafe()}.
   */
  public void testGetScale_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setScale_unsafe(java.lang.Integer)}.
   */
  public void testSetScale_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getScale()}.
   */
  public void testGetScale() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setScale(java.lang.Integer)}.
   */
  public void testSetScaleInteger() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setScale(int)}.
   */
  public void testSetScaleInt() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getScaleField()}.
   */
  public void testGetScaleField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getRenderinfo_unsafe()}.
   */
  public void testGetRenderinfo_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setRenderinfo_unsafe(java.lang.String)}.
   */
  public void testSetRenderinfo_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getRenderinfo()}.
   */
  public void testGetRenderinfo() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setRenderinfo(java.lang.String)}.
   */
  public void testSetRenderinfo() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getRenderinfoField()}.
   */
  public void testGetRenderinfoField() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getRangelow_string_unsafe()}.
   */
  public void testGetRangelow_string_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setRangelow_string_unsafe(java.lang.String)}.
   */
  public void testSetRangelow_string_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getRangelow_string()}.
   */
  public void testGetRangelow_string() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getRangelimit_string_unsafe()}.
   */
  public void testGetRangelimit_string_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#setRangelimit_string_unsafe(java.lang.String)}.
   */
  public void testSetRangelimit_string_unsafe() {

  }

  /**
   * Test method for {@link org.melati.poem.generated.ValueInfoBase#getRangelimit_string()}.
   */
  public void testGetRangelimit_string() {

  }

}
