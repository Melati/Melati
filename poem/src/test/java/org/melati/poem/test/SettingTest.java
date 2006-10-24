/**
 * 
 */
package org.melati.poem.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    Setting stringSetting = getDb().getSettingTable().ensure("stringSetting","set","String","A set string setting");
    stringSetting.setValue("new");
    assertEquals("new",stringSetting.getCooked());
    stringSetting.setRaw("new2");
    assertEquals("new2",stringSetting.getCooked());
    Setting integerSetting = getDb().getSettingTable().ensure("integerSetting",12,"Integer","A set Integer setting");
    integerSetting.setRaw(new Integer(13));
    assertEquals(new Integer(13),integerSetting.getCooked());
  }

  /**
   * @see org.melati.poem.Setting#getValueField()
   */
  public void testGetValueField() {

  }

  /**
   * @see org.melati.poem.Setting.Setting(Integer, String, String, String, String)'
   */
  public void testSettingIntegerStringStringStringString() {

  }

  /*
   * @see org.melati.poem.Setting.setRaw(Object)'
   */
  public void testSetRawObject() {

  }

  /*
   * @see org.melati.poem.Setting.getRaw()'
   */
  public void testGetRaw() {

  }

  /*
   * @see org.melati.poem.Setting.getCooked()'
   */
  public void testGetCooked() {

  }

  /*
   * @see org.melati.poem.Setting.getIntegerCooked()'
   */
  public void testGetIntegerCooked() {

  }

  /*
   * @see org.melati.poem.Setting.getStringCooked()'
   */
  public void testGetStringCooked() {

  }

  /*
   * @see org.melati.poem.Setting.getBooleanCooked()'
   */
  public void testGetBooleanCooked() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getPoemDatabaseTables()'
   */
  public void testGetPoemDatabaseTables() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getSettingTable()'
   */
  public void testGetSettingTable() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getId_unsafe()'
   */
  public void testGetId_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.setId_unsafe(Integer)'
   */
  public void testSetId_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getId()'
   */
  public void testGetId() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.setId(Integer)'
   */
  public void testSetIdInteger() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.setId(int)'
   */
  public void testSetIdInt() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getIdField()'
   */
  public void testGetIdField() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getName_unsafe()'
   */
  public void testGetName_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.setName_unsafe(String)'
   */
  public void testSetName_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getName()'
   */
  public void testGetName() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.setName(String)'
   */
  public void testSetName() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getNameField()'
   */
  public void testGetNameField() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getValue_unsafe()'
   */
  public void testGetValue_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.setValue_unsafe(String)'
   */
  public void testSetValue_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.SettingBase.getValue()'
   */
  public void testGetValue() {

  }

  /*
   * @see org.melati.poem.ValueInfo.setRangelow_string(String)'
   */
  public void testSetRangelow_string() {

  }

  /*
   * @see org.melati.poem.ValueInfo.getRangelow_stringField()'
   */
  public void testGetRangelow_stringField() {

  }

  /*
   * @see org.melati.poem.ValueInfo.setRangelimit_string(String)'
   */
  public void testSetRangelimit_string() {

  }

  /*
   * @see org.melati.poem.ValueInfo.getRangelimit_stringField()'
   */
  public void testGetRangelimit_stringField() {

  }

  /*
   * @see org.melati.poem.ValueInfo.ValueInfo()'
   */
  public void testValueInfo() {

  }

  /*
   * @see org.melati.poem.ValueInfo.toTypeParameter()'
   */
  public void testToTypeParameter() {

  }

  /*
   * @see org.melati.poem.ValueInfo.getType()'
   */
  public void testGetType() {

  }

  /*
   * @see org.melati.poem.ValueInfo.fieldAttributesRenamedAs(FieldAttributes)'
   */
  public void testFieldAttributesRenamedAs() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getValueInfoTable()'
   */
  public void testGetValueInfoTable() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getDisplayname_unsafe()'
   */
  public void testGetDisplayname_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setDisplayname_unsafe(String)'
   */
  public void testSetDisplayname_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getDisplayname()'
   */
  public void testGetDisplayname() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setDisplayname(String)'
   */
  public void testSetDisplayname() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getDisplaynameField()'
   */
  public void testGetDisplaynameField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getDescription_unsafe()'
   */
  public void testGetDescription_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setDescription_unsafe(String)'
   */
  public void testSetDescription_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getDescription()'
   */
  public void testGetDescription() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setDescription(String)'
   */
  public void testSetDescription() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getDescriptionField()'
   */
  public void testGetDescriptionField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getUsereditable_unsafe()'
   */
  public void testGetUsereditable_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setUsereditable_unsafe(Boolean)'
   */
  public void testSetUsereditable_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getUsereditable()'
   */
  public void testGetUsereditable() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setUsereditable(Boolean)'
   */
  public void testSetUsereditableBoolean() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setUsereditable(boolean)'
   */
  public void testSetUsereditableBoolean1() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getUsereditableField()'
   */
  public void testGetUsereditableField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getTypefactory_unsafe()'
   */
  public void testGetTypefactory_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setTypefactory_unsafe(Integer)'
   */
  public void testSetTypefactory_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getTypefactoryCode()'
   */
  public void testGetTypefactoryCode() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setTypefactoryCode(Integer)'
   */
  public void testSetTypefactoryCode() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getTypefactory()'
   */
  public void testGetTypefactory() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setTypefactory(PoemTypeFactory)'
   */
  public void testSetTypefactory() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getTypefactoryField()'
   */
  public void testGetTypefactoryField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getNullable_unsafe()'
   */
  public void testGetNullable_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setNullable_unsafe(Boolean)'
   */
  public void testSetNullable_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getNullable()'
   */
  public void testGetNullable() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setNullable(Boolean)'
   */
  public void testSetNullableBoolean() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setNullable(boolean)'
   */
  public void testSetNullableBoolean1() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getNullableField()'
   */
  public void testGetNullableField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getSize_unsafe()'
   */
  public void testGetSize_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setSize_unsafe(Integer)'
   */
  public void testSetSize_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getSize()'
   */
  public void testGetSize() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setSize(Integer)'
   */
  public void testSetSizeInteger() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setSize(int)'
   */
  public void testSetSizeInt() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getSizeField()'
   */
  public void testGetSizeField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getWidth_unsafe()'
   */
  public void testGetWidth_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setWidth_unsafe(Integer)'
   */
  public void testSetWidth_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getWidth()'
   */
  public void testGetWidth() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setWidth(Integer)'
   */
  public void testSetWidthInteger() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setWidth(int)'
   */
  public void testSetWidthInt() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getWidthField()'
   */
  public void testGetWidthField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getHeight_unsafe()'
   */
  public void testGetHeight_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setHeight_unsafe(Integer)'
   */
  public void testSetHeight_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getHeight()'
   */
  public void testGetHeight() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setHeight(Integer)'
   */
  public void testSetHeightInteger() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setHeight(int)'
   */
  public void testSetHeightInt() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getHeightField()'
   */
  public void testGetHeightField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getPrecision_unsafe()'
   */
  public void testGetPrecision_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setPrecision_unsafe(Integer)'
   */
  public void testSetPrecision_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getPrecision()'
   */
  public void testGetPrecision() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setPrecision(Integer)'
   */
  public void testSetPrecisionInteger() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setPrecision(int)'
   */
  public void testSetPrecisionInt() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getPrecisionField()'
   */
  public void testGetPrecisionField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getScale_unsafe()'
   */
  public void testGetScale_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setScale_unsafe(Integer)'
   */
  public void testSetScale_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getScale()'
   */
  public void testGetScale() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setScale(Integer)'
   */
  public void testSetScaleInteger() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setScale(int)'
   */
  public void testSetScaleInt() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getScaleField()'
   */
  public void testGetScaleField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getRenderinfo_unsafe()'
   */
  public void testGetRenderinfo_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setRenderinfo_unsafe(String)'
   */
  public void testSetRenderinfo_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getRenderinfo()'
   */
  public void testGetRenderinfo() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setRenderinfo(String)'
   */
  public void testSetRenderinfo() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getRenderinfoField()'
   */
  public void testGetRenderinfoField() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getRangelow_string_unsafe()'
   */
  public void testGetRangelow_string_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setRangelow_string_unsafe(String)'
   */
  public void testSetRangelow_string_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getRangelow_string()'
   */
  public void testGetRangelow_string() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getRangelimit_string_unsafe()'
   */
  public void testGetRangelimit_string_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.setRangelimit_string_unsafe(String)'
   */
  public void testSetRangelimit_string_unsafe() {

  }

  /*
   * @see org.melati.poem.generated.ValueInfoBase.getRangelimit_string()'
   */
  public void testGetRangelimit_string() {

  }

  /*
   * @see org.melati.poem.Persistent.hashCode()'
   */
  public void testHashCode() {

  }

  /*
   * @see org.melati.poem.Persistent.load(Transaction)'
   */
  public void testLoad() {

  }

  /*
   * @see org.melati.poem.Persistent.upToDate(Transaction)'
   */
  public void testUpToDate() {

  }

  /*
   * @see org.melati.poem.Persistent.writeDown(Transaction)'
   */
  public void testWriteDown() {

  }

  /*
   * @see org.melati.poem.Persistent.readLock(Transaction)'
   */
  public void testReadLockTransaction() {

  }

  /*
   * @see org.melati.poem.Persistent.writeLock(Transaction)'
   */
  public void testWriteLockTransaction() {

  }

  /*
   * @see org.melati.poem.Persistent.commit(Transaction)'
   */
  public void testCommit() {

  }

  /*
   * @see org.melati.poem.Persistent.rollback(Transaction)'
   */
  public void testRollback() {

  }

  /*
   * @see org.melati.poem.Persistent.invalidate()'
   */
  public void testInvalidate() {

  }

  /*
   * @see org.melati.poem.Persistent.Persistent(Table, Integer)'
   */
  public void testPersistentTableInteger() {

  }

  /*
   * @see org.melati.poem.Persistent.Persistent(Table)'
   */
  public void testPersistentTable() {

  }

  /*
   * @see org.melati.poem.Persistent.Persistent()'
   */
  public void testPersistent() {

  }

  /*
   * @see org.melati.poem.Persistent.setStatusNonexistent()'
   */
  public void testSetStatusNonexistent() {

  }

  /*
   * @see org.melati.poem.Persistent.setStatusExistent()'
   */
  public void testSetStatusExistent() {

  }

  /*
   * @see org.melati.poem.Persistent.statusNonexistent()'
   */
  public void testStatusNonexistent() {

  }

  /*
   * @see org.melati.poem.Persistent.statusExistent()'
   */
  public void testStatusExistent() {

  }

  /*
   * @see org.melati.poem.Persistent.makePersistent()'
   */
  public void testMakePersistent() {

  }

  /*
   * @see org.melati.poem.Persistent.extras()'
   */
  public void testExtras() {

  }

  /*
   * @see org.melati.poem.Persistent.getTable()'
   */
  public void testGetTable() {

  }

  /*
   * @see org.melati.poem.Persistent.setTable(Table, Integer)'
   */
  public void testSetTable() {

  }

  /*
   * @see org.melati.poem.Persistent.getDatabase()'
   */
  public void testGetDatabase() {

  }

  /*
   * @see org.melati.poem.Persistent.troid()'
   */
  public void testTroid() {

  }

  /*
   * @see org.melati.poem.Persistent.getTroid()'
   */
  public void testGetTroid() {

  }

  /*
   * @see org.melati.poem.Persistent.existenceLock(SessionToken)'
   */
  public void testExistenceLockSessionToken() {

  }

  /*
   * @see org.melati.poem.Persistent.readLock(SessionToken)'
   */
  public void testReadLockSessionToken() {

  }

  /*
   * @see org.melati.poem.Persistent.writeLock(SessionToken)'
   */
  public void testWriteLockSessionToken() {

  }

  /*
   * @see org.melati.poem.Persistent.deleteLock(SessionToken)'
   */
  public void testDeleteLock() {

  }

  /*
   * @see org.melati.poem.Persistent.existenceLock()'
   */
  public void testExistenceLock() {

  }

  /*
   * @see org.melati.poem.Persistent.readLock()'
   */
  public void testReadLock() {

  }

  /*
   * @see org.melati.poem.Persistent.writeLock()'
   */
  public void testWriteLock() {

  }

  /*
   * @see org.melati.poem.Persistent.getCanRead()'
   */
  public void testGetCanRead() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanRead(AccessToken)'
   */
  public void testAssertCanReadAccessToken() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanRead()'
   */
  public void testAssertCanRead() {

  }

  /*
   * @see org.melati.poem.Persistent.getReadable()'
   */
  public void testGetReadable() {

  }

  /*
   * @see org.melati.poem.Persistent.getCanWrite()'
   */
  public void testGetCanWrite() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanWrite(AccessToken)'
   */
  public void testAssertCanWriteAccessToken() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanWrite()'
   */
  public void testAssertCanWrite() {

  }

  /*
   * @see org.melati.poem.Persistent.getCanDelete()'
   */
  public void testGetCanDelete() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanDelete(AccessToken)'
   */
  public void testAssertCanDeleteAccessToken() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanDelete()'
   */
  public void testAssertCanDelete() {

  }

  /*
   * @see org.melati.poem.Persistent.getCanSelect()'
   */
  public void testGetCanSelect() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanCreate(AccessToken)'
   */
  public void testAssertCanCreateAccessToken() {

  }

  /*
   * @see org.melati.poem.Persistent.assertCanCreate()'
   */
  public void testAssertCanCreate() {

  }

  /*
   * @see org.melati.poem.Persistent.getRaw(String)'
   */
  public void testGetRawString() {

  }

  /*
   * @see org.melati.poem.Persistent.getRawString(String)'
   */
  public void testGetRawString1() {

  }

  /*
   * @see org.melati.poem.Persistent.setRaw(String, Object)'
   */
  public void testSetRawStringObject() {

  }

  /*
   * @see org.melati.poem.Persistent.setRawString(String, String)'
   */
  public void testSetRawString() {

  }

  /*
   * @see org.melati.poem.Persistent.getCooked(String)'
   */
  public void testGetCookedString() {

  }

  /*
   * @see org.melati.poem.Persistent.getCookedString(String, MelatiLocale, int)'
   */
  public void testGetCookedString1() {

  }

  /*
   * @see org.melati.poem.Persistent.setCooked(String, Object)'
   */
  public void testSetCooked() {

  }

  /*
   * @see org.melati.poem.Persistent.getField(String)'
   */
  public void testGetField() {

  }

  /*
   * @see org.melati.poem.Persistent.fieldsOfColumns(Enumeration)'
   */
  public void testFieldsOfColumns() {

  }

  /*
   * @see org.melati.poem.Persistent.getFields()'
   */
  public void testGetFields() {

  }

  /*
   * @see org.melati.poem.Persistent.getRecordDisplayFields()'
   */
  public void testGetRecordDisplayFields() {

  }

  /*
   * @see org.melati.poem.Persistent.getDetailDisplayFields()'
   */
  public void testGetDetailDisplayFields() {

  }

  /*
   * @see org.melati.poem.Persistent.getSummaryDisplayFields()'
   */
  public void testGetSummaryDisplayFields() {

  }

  /*
   * @see org.melati.poem.Persistent.getSearchCriterionFields()'
   */
  public void testGetSearchCriterionFields() {

  }

  /*
   * @see org.melati.poem.Persistent.delete_unsafe()'
   */
  public void testDelete_unsafe() {

  }

  /*
   * @see org.melati.poem.Persistent.getPrimaryDisplayField()'
   */
  public void testGetPrimaryDisplayField() {

  }

  /*
   * @see org.melati.poem.Persistent.delete(Map)'
   */
  public void testDeleteMap() {

  }

  /*
   * @see org.melati.poem.Persistent.delete()'
   */
  public void testDelete() {

  }

  /*
   * @see org.melati.poem.Persistent.deleteAndCommit(Map)'
   */
  public void testDeleteAndCommitMap() {

  }

  /*
   * @see org.melati.poem.Persistent.deleteAndCommit()'
   */
  public void testDeleteAndCommit() {

  }

  /*
   * @see org.melati.poem.Persistent.duplicated()'
   */
  public void testDuplicated() {

  }

  /*
   * @see org.melati.poem.Persistent.duplicatedFloating()'
   */
  public void testDuplicatedFloating() {

  }

  /*
   * @see org.melati.poem.Persistent.toString()'
   */
  public void testToString() {

  }

  /*
   * @see org.melati.poem.Persistent.displayString(MelatiLocale, int)'
   */
  public void testDisplayStringMelatiLocaleInt() {

  }

  /*
   * @see org.melati.poem.Persistent.displayString(MelatiLocale)'
   */
  public void testDisplayStringMelatiLocale() {

  }

  /*
   * @see org.melati.poem.Persistent.displayString()'
   */
  public void testDisplayString() {

  }

  /*
   * @see org.melati.poem.Persistent.equals(Object)'
   */
  public void testEqualsObject() {

  }

  /*
   * @see org.melati.poem.Persistent.clone()'
   */
  public void testClone() {

  }

  /**
   * @see org.melati.poem.Persistent#dump(PrintStream)
   */
  public void testDump() {
    Setting stringSetting = getDb().getSettingTable().ensure("stringSetting","set","String","A set string setting");
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    stringSetting.dump(ps);
    System.err.println(baos.toString().trim() + ":");
    assertTrue(baos.toString().startsWith("setting/0"));}

  /**
   * @see org.melati.poem.Persistent.postWrite()
   */
  public void testPostWrite() {

  }

  /**
   * @see org.melati.poem.Persistent#postInsert()
   */
  public void testPostInsert() {

  }

  /**
   * @see org.melati.poem.Persistent#postModify()
   */
  public void testPostModify() {

  }

  /**
   * @see org.melati.poem.Persistent#preEdit()
   */
  public void testPreEdit() {

  }

  /**
   * @see org.melati.poem.Persistent#postEdit(boolean)
   */
  public void testPostEdit() {

  }

  /**
   * @see org.melati.poem.Persistent#countMatchSQL(boolean, boolean)
   */
  public void testCountMatchSQL() {

  }

  /**
   * @see org.melati.poem.Persistent#fromClause()
   */
  public void testFromClause() {

  }

  /**
   * @see org.melati.poem.Persistent#otherMatchTables()
   */
  public void testOtherMatchTables() {

  }

}
