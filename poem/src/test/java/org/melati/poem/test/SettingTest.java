/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.Setting;

/**
 * @author timp
 *
 */
public class SettingTest extends PoemTestCase {

  /**
   * Constructor for SettingTest.
   * @param name
   */
  public SettingTest(String name) {
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
   * @see org.melati.poem.Setting#setValue(String)
   */
  public void testSetValue() {
    Setting s = getDb().getSettingTable().ensure("stringSetting","set","Set","A set setting");
    s.setValue("new");
    assertEquals("new",s.getCooked());
  }

  /*
   * Test method for 'org.melati.poem.Setting.getValueField()'
   */
  public void testGetValueField() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.Setting()'
   */
  public void testSetting() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.Setting(Integer, String, String, String, String)'
   */
  public void testSettingIntegerStringStringStringString() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.setRaw(Object)'
   */
  public void testSetRawObject() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.getRaw()'
   */
  public void testGetRaw() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.getCooked()'
   */
  public void testGetCooked() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.getIntegerCooked()'
   */
  public void testGetIntegerCooked() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.getStringCooked()'
   */
  public void testGetStringCooked() {

  }

  /*
   * Test method for 'org.melati.poem.Setting.getBooleanCooked()'
   */
  public void testGetBooleanCooked() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getPoemDatabaseTables()'
   */
  public void testGetPoemDatabaseTables() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getSettingTable()'
   */
  public void testGetSettingTable() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getId_unsafe()'
   */
  public void testGetId_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.setId_unsafe(Integer)'
   */
  public void testSetId_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getId()'
   */
  public void testGetId() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.setId(Integer)'
   */
  public void testSetIdInteger() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.setId(int)'
   */
  public void testSetIdInt() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getIdField()'
   */
  public void testGetIdField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getName_unsafe()'
   */
  public void testGetName_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.setName_unsafe(String)'
   */
  public void testSetName_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getName()'
   */
  public void testGetName() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.setName(String)'
   */
  public void testSetName() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getNameField()'
   */
  public void testGetNameField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getValue_unsafe()'
   */
  public void testGetValue_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.setValue_unsafe(String)'
   */
  public void testSetValue_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.SettingBase.getValue()'
   */
  public void testGetValue() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.setRangelow_string(String)'
   */
  public void testSetRangelow_string() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.getRangelow_stringField()'
   */
  public void testGetRangelow_stringField() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.setRangelimit_string(String)'
   */
  public void testSetRangelimit_string() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.getRangelimit_stringField()'
   */
  public void testGetRangelimit_stringField() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.ValueInfo()'
   */
  public void testValueInfo() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.toTypeParameter()'
   */
  public void testToTypeParameter() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.getType()'
   */
  public void testGetType() {

  }

  /*
   * Test method for 'org.melati.poem.ValueInfo.fieldAttributesRenamedAs(FieldAttributes)'
   */
  public void testFieldAttributesRenamedAs() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getValueInfoTable()'
   */
  public void testGetValueInfoTable() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getDisplayname_unsafe()'
   */
  public void testGetDisplayname_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setDisplayname_unsafe(String)'
   */
  public void testSetDisplayname_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getDisplayname()'
   */
  public void testGetDisplayname() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setDisplayname(String)'
   */
  public void testSetDisplayname() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getDisplaynameField()'
   */
  public void testGetDisplaynameField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getDescription_unsafe()'
   */
  public void testGetDescription_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setDescription_unsafe(String)'
   */
  public void testSetDescription_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getDescription()'
   */
  public void testGetDescription() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setDescription(String)'
   */
  public void testSetDescription() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getDescriptionField()'
   */
  public void testGetDescriptionField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getUsereditable_unsafe()'
   */
  public void testGetUsereditable_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setUsereditable_unsafe(Boolean)'
   */
  public void testSetUsereditable_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getUsereditable()'
   */
  public void testGetUsereditable() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setUsereditable(Boolean)'
   */
  public void testSetUsereditableBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setUsereditable(boolean)'
   */
  public void testSetUsereditableBoolean1() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getUsereditableField()'
   */
  public void testGetUsereditableField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getTypefactory_unsafe()'
   */
  public void testGetTypefactory_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setTypefactory_unsafe(Integer)'
   */
  public void testSetTypefactory_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getTypefactoryCode()'
   */
  public void testGetTypefactoryCode() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setTypefactoryCode(Integer)'
   */
  public void testSetTypefactoryCode() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getTypefactory()'
   */
  public void testGetTypefactory() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setTypefactory(PoemTypeFactory)'
   */
  public void testSetTypefactory() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getTypefactoryField()'
   */
  public void testGetTypefactoryField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getNullable_unsafe()'
   */
  public void testGetNullable_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setNullable_unsafe(Boolean)'
   */
  public void testSetNullable_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getNullable()'
   */
  public void testGetNullable() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setNullable(Boolean)'
   */
  public void testSetNullableBoolean() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setNullable(boolean)'
   */
  public void testSetNullableBoolean1() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getNullableField()'
   */
  public void testGetNullableField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getSize_unsafe()'
   */
  public void testGetSize_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setSize_unsafe(Integer)'
   */
  public void testSetSize_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getSize()'
   */
  public void testGetSize() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setSize(Integer)'
   */
  public void testSetSizeInteger() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setSize(int)'
   */
  public void testSetSizeInt() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getSizeField()'
   */
  public void testGetSizeField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getWidth_unsafe()'
   */
  public void testGetWidth_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setWidth_unsafe(Integer)'
   */
  public void testSetWidth_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getWidth()'
   */
  public void testGetWidth() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setWidth(Integer)'
   */
  public void testSetWidthInteger() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setWidth(int)'
   */
  public void testSetWidthInt() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getWidthField()'
   */
  public void testGetWidthField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getHeight_unsafe()'
   */
  public void testGetHeight_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setHeight_unsafe(Integer)'
   */
  public void testSetHeight_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getHeight()'
   */
  public void testGetHeight() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setHeight(Integer)'
   */
  public void testSetHeightInteger() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setHeight(int)'
   */
  public void testSetHeightInt() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getHeightField()'
   */
  public void testGetHeightField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getPrecision_unsafe()'
   */
  public void testGetPrecision_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setPrecision_unsafe(Integer)'
   */
  public void testSetPrecision_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getPrecision()'
   */
  public void testGetPrecision() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setPrecision(Integer)'
   */
  public void testSetPrecisionInteger() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setPrecision(int)'
   */
  public void testSetPrecisionInt() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getPrecisionField()'
   */
  public void testGetPrecisionField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getScale_unsafe()'
   */
  public void testGetScale_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setScale_unsafe(Integer)'
   */
  public void testSetScale_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getScale()'
   */
  public void testGetScale() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setScale(Integer)'
   */
  public void testSetScaleInteger() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setScale(int)'
   */
  public void testSetScaleInt() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getScaleField()'
   */
  public void testGetScaleField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getRenderinfo_unsafe()'
   */
  public void testGetRenderinfo_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setRenderinfo_unsafe(String)'
   */
  public void testSetRenderinfo_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getRenderinfo()'
   */
  public void testGetRenderinfo() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setRenderinfo(String)'
   */
  public void testSetRenderinfo() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getRenderinfoField()'
   */
  public void testGetRenderinfoField() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getRangelow_string_unsafe()'
   */
  public void testGetRangelow_string_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setRangelow_string_unsafe(String)'
   */
  public void testSetRangelow_string_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getRangelow_string()'
   */
  public void testGetRangelow_string() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getRangelimit_string_unsafe()'
   */
  public void testGetRangelimit_string_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.setRangelimit_string_unsafe(String)'
   */
  public void testSetRangelimit_string_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.generated.ValueInfoBase.getRangelimit_string()'
   */
  public void testGetRangelimit_string() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.hashCode()'
   */
  public void testHashCode() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.load(Transaction)'
   */
  public void testLoad() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.upToDate(Transaction)'
   */
  public void testUpToDate() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.writeDown(Transaction)'
   */
  public void testWriteDown() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.readLock(Transaction)'
   */
  public void testReadLockTransaction() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.writeLock(Transaction)'
   */
  public void testWriteLockTransaction() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.commit(Transaction)'
   */
  public void testCommit() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.rollback(Transaction)'
   */
  public void testRollback() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.invalidate()'
   */
  public void testInvalidate() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.Persistent(Table, Integer)'
   */
  public void testPersistentTableInteger() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.Persistent(Table)'
   */
  public void testPersistentTable() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.Persistent()'
   */
  public void testPersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.setStatusNonexistent()'
   */
  public void testSetStatusNonexistent() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.setStatusExistent()'
   */
  public void testSetStatusExistent() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.statusNonexistent()'
   */
  public void testStatusNonexistent() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.statusExistent()'
   */
  public void testStatusExistent() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.makePersistent()'
   */
  public void testMakePersistent() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.extras()'
   */
  public void testExtras() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getTable()'
   */
  public void testGetTable() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.setTable(Table, Integer)'
   */
  public void testSetTable() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getDatabase()'
   */
  public void testGetDatabase() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.troid()'
   */
  public void testTroid() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getTroid()'
   */
  public void testGetTroid() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.existenceLock(SessionToken)'
   */
  public void testExistenceLockSessionToken() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.readLock(SessionToken)'
   */
  public void testReadLockSessionToken() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.writeLock(SessionToken)'
   */
  public void testWriteLockSessionToken() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.deleteLock(SessionToken)'
   */
  public void testDeleteLock() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.existenceLock()'
   */
  public void testExistenceLock() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.readLock()'
   */
  public void testReadLock() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.writeLock()'
   */
  public void testWriteLock() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getCanRead()'
   */
  public void testGetCanRead() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanRead(AccessToken)'
   */
  public void testAssertCanReadAccessToken() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanRead()'
   */
  public void testAssertCanRead() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getReadable()'
   */
  public void testGetReadable() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getCanWrite()'
   */
  public void testGetCanWrite() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanWrite(AccessToken)'
   */
  public void testAssertCanWriteAccessToken() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanWrite()'
   */
  public void testAssertCanWrite() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getCanDelete()'
   */
  public void testGetCanDelete() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanDelete(AccessToken)'
   */
  public void testAssertCanDeleteAccessToken() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanDelete()'
   */
  public void testAssertCanDelete() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getCanSelect()'
   */
  public void testGetCanSelect() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanCreate(AccessToken)'
   */
  public void testAssertCanCreateAccessToken() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.assertCanCreate()'
   */
  public void testAssertCanCreate() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getRaw(String)'
   */
  public void testGetRawString() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getRawString(String)'
   */
  public void testGetRawString1() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.setRaw(String, Object)'
   */
  public void testSetRawStringObject() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.setRawString(String, String)'
   */
  public void testSetRawString() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getCooked(String)'
   */
  public void testGetCookedString() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getCookedString(String, MelatiLocale, int)'
   */
  public void testGetCookedString1() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.setCooked(String, Object)'
   */
  public void testSetCooked() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getField(String)'
   */
  public void testGetField() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.fieldsOfColumns(Enumeration)'
   */
  public void testFieldsOfColumns() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getFields()'
   */
  public void testGetFields() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getRecordDisplayFields()'
   */
  public void testGetRecordDisplayFields() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getDetailDisplayFields()'
   */
  public void testGetDetailDisplayFields() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getSummaryDisplayFields()'
   */
  public void testGetSummaryDisplayFields() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getSearchCriterionFields()'
   */
  public void testGetSearchCriterionFields() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.delete_unsafe()'
   */
  public void testDelete_unsafe() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.getPrimaryDisplayField()'
   */
  public void testGetPrimaryDisplayField() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.delete(Map)'
   */
  public void testDeleteMap() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.delete()'
   */
  public void testDelete() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.deleteAndCommit(Map)'
   */
  public void testDeleteAndCommitMap() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.deleteAndCommit()'
   */
  public void testDeleteAndCommit() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.duplicated()'
   */
  public void testDuplicated() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.duplicatedFloating()'
   */
  public void testDuplicatedFloating() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.toString()'
   */
  public void testToString() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.displayString(MelatiLocale, int)'
   */
  public void testDisplayStringMelatiLocaleInt() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.displayString(MelatiLocale)'
   */
  public void testDisplayStringMelatiLocale() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.displayString()'
   */
  public void testDisplayString() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.equals(Object)'
   */
  public void testEqualsObject() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.clone()'
   */
  public void testClone() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.dump(PrintStream)'
   */
  public void testDump() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.postWrite()'
   */
  public void testPostWrite() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.postInsert()'
   */
  public void testPostInsert() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.postModify()'
   */
  public void testPostModify() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.preEdit()'
   */
  public void testPreEdit() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.postEdit(boolean)'
   */
  public void testPostEdit() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.countMatchSQL(boolean, boolean)'
   */
  public void testCountMatchSQL() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.fromClause()'
   */
  public void testFromClause() {

  }

  /*
   * Test method for 'org.melati.poem.Persistent.otherMatchTables()'
   */
  public void testOtherMatchTables() {

  }

}
