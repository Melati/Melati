package org.melati.poem;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.melati.poem.dbms.test.AnsiStandardTest;
import org.melati.poem.dbms.test.DbmsFactoryTest;
import org.melati.poem.dbms.test.FirstSQLTest;
import org.melati.poem.dbms.test.HsqldbTest;
import org.melati.poem.dbms.test.HsqldbThrowerTest;
import org.melati.poem.dbms.test.InterbaseTest;
import org.melati.poem.dbms.test.MSAccessTest;
import org.melati.poem.dbms.test.MckoiTest;
import org.melati.poem.dbms.test.MimerTest;
import org.melati.poem.dbms.test.MySQLTest;
import org.melati.poem.dbms.test.OracleTest;
import org.melati.poem.dbms.test.PostgresqlTest;
import org.melati.poem.dbms.test.SQLServerTest;
import org.melati.poem.test.BinaryTest;
import org.melati.poem.test.CachedCountTest;
import org.melati.poem.test.CachedExistsTest;
import org.melati.poem.test.CachedSelectionTest;
import org.melati.poem.test.CachedTailoredQueryTest;
import org.melati.poem.test.CapabilityTableTest;
import org.melati.poem.test.CapabilityTest;
import org.melati.poem.test.ColumnInfoTest;
import org.melati.poem.test.ColumnTest;
import org.melati.poem.test.ColumnTypePoemTypeTest;
import org.melati.poem.test.DatabasePerformInCommittedTransactionTest;
import org.melati.poem.test.DatabaseTest;
import org.melati.poem.test.DatabaseUnifyWithDBTest;
import org.melati.poem.test.DeletedPoemTypeTest;
import org.melati.poem.test.DisplayLevelPoemTypeTest;
import org.melati.poem.test.DisplayLevelTest;
import org.melati.poem.test.DynamicTableTest;
import org.melati.poem.test.EverythingDatabaseTest;
import org.melati.poem.test.FieldSetTest;
import org.melati.poem.test.FieldTest;
import org.melati.poem.test.GroupCapabilityTableTest;
import org.melati.poem.test.GroupCapabilityTest;
import org.melati.poem.test.GroupMembershipTest;
import org.melati.poem.test.GroupTest;
import org.melati.poem.test.MultiThreadedCachedSelectionTest;
import org.melati.poem.test.NonSQLPoemTypeTest;
import org.melati.poem.test.NotNullableBinaryPoemTypeTest;
import org.melati.poem.test.NotNullableBooleanPoemTypeTest;
import org.melati.poem.test.NotNullableDatePoemTypeTest;
import org.melati.poem.test.NotNullableDefaultBigDecimalPoemTypeTest;
import org.melati.poem.test.NotNullableDoublePoemTypeTest;
import org.melati.poem.test.NotNullableIntegerPoemTypeTest;
import org.melati.poem.test.NotNullableLongPoemTypeTest;
import org.melati.poem.test.NotNullableReferencePoemTypeTest;
import org.melati.poem.test.NotNullableSetBigDecimalPoemTypeTest;
import org.melati.poem.test.NotNullableSizedPasswordPoemTypeTest;
import org.melati.poem.test.NotNullableSizedStringPoemTypeTest;
import org.melati.poem.test.NotNullableTimestampPoemTypeTest;
import org.melati.poem.test.NotNullableUnlimitedStringPoemTypeTest;
import org.melati.poem.test.NotNullablelntegrityFixPoemTypeTest;
import org.melati.poem.test.NullableBinaryPoemTypeTest;
import org.melati.poem.test.NullableBooleanPoemTypeTest;
import org.melati.poem.test.NullableDatePoemTypeTest;
import org.melati.poem.test.NullableDefaultBigDecimalPoemTypeTest;
import org.melati.poem.test.NullableDoublePoemTypeTest;
import org.melati.poem.test.NullableIntegerPoemTypeTest;
import org.melati.poem.test.NullableLongPoemTypeTest;
import org.melati.poem.test.NullableReferencePoemTypeTest;
import org.melati.poem.test.NullableSetBigDecimalPoemTypeTest;
import org.melati.poem.test.NullableSizedStringPoemTypeTest;
import org.melati.poem.test.NullableTimestampPoemTypeTest;
import org.melati.poem.test.NullableUnlimitedStringPoemTypeTest;
import org.melati.poem.test.PersistentFactoryTest;
import org.melati.poem.test.PersistentTest;
import org.melati.poem.test.PoemDatabaseTest;
import org.melati.poem.test.PoemLocaleTest;
import org.melati.poem.test.PoemThreadTest;
import org.melati.poem.test.PoemTransactionTest;
import org.melati.poem.test.PoemTypeFactoryTest;
import org.melati.poem.test.PreparedStatementFactoryTest;
import org.melati.poem.test.PreparedTailoredQueryTest;
import org.melati.poem.test.ProtectedPersistentTest;
import org.melati.poem.test.RangedNullableIntegerPoemTypeTest;
import org.melati.poem.test.ResultSetEnumerationTest;
import org.melati.poem.test.SearchabilityPoemTypeTest;
import org.melati.poem.test.SearchabilityTest;
import org.melati.poem.test.SessionTokenTest;
import org.melati.poem.test.SettingTableTest;
import org.melati.poem.test.SettingTest;
import org.melati.poem.test.SqlExceptionPoemTypeTest;
import org.melati.poem.test.StandardIntegrityFixTest;
import org.melati.poem.test.StringPoemTypeTest;
import org.melati.poem.test.TableCategoryTest;
import org.melati.poem.test.TableFactoryTest;
import org.melati.poem.test.TableInfoTest;
import org.melati.poem.test.TableMapTest;
//import org.melati.poem.test.TableSortedMapTest;
import org.melati.poem.test.TableTest;
import org.melati.poem.test.TailoredQueryTest;
import org.melati.poem.test.TroidPoemTypeTest;
import org.melati.poem.test.UserTest;
import org.melati.poem.util.test.ArrayEnumerationTest;
import org.melati.poem.util.test.ArrayUtilsTest;
import org.melati.poem.util.test.CacheTest;
import org.melati.poem.util.test.CachedIndexFactoryTest;
import org.melati.poem.util.test.ClassUtilsTest;
import org.melati.poem.util.test.ConsEnumerationTest;
import org.melati.poem.util.test.DictionaryOrderTest;
import org.melati.poem.util.test.EmptyEnumerationTest;
import org.melati.poem.util.test.EnumUtilsTest;
import org.melati.poem.util.test.FilteredEnumerationTest;
import org.melati.poem.util.test.FlattenedEnumerationTest;
import org.melati.poem.util.test.IntegerEnumerationTest;
import org.melati.poem.util.test.LimitedEnumerationTest;
import org.melati.poem.util.test.LongEnumerationTest;
import org.melati.poem.util.test.SortUtilsTest;
import org.melati.poem.util.test.StringUtilsTest;

/**
 * A class which can be used within Eclipse or from another Maven project to
 * access these tests with the Surefire plugin.
 * 
 * See my comment on http://jira.codehaus.org/browse/SUREFIRE-569
 * See also http://jira.codehaus.org/browse/SUREFIRE-120 
 * Note that this does not implement junit.framework.Test or it would be 
 * discovered by the Maven Surefire plugin. 
 * 
 * @author timp
 * @since 2010-10-13
 */
public class ExportedTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("All reusable Tests");
        suite.addTestSuite(FirstSQLTest.class);
        suite.addTestSuite(MimerTest.class);
        suite.addTestSuite(DbmsFactoryTest.class);
        suite.addTestSuite(MSAccessTest.class);
        suite.addTestSuite(OracleTest.class);
        suite.addTestSuite(HsqldbThrowerTest.class);
        suite.addTestSuite(InterbaseTest.class);
        suite.addTestSuite(AnsiStandardTest.class);
        suite.addTestSuite(MySQLTest.class);
        suite.addTestSuite(MckoiTest.class);
        suite.addTestSuite(SQLServerTest.class);
        suite.addTestSuite(HsqldbTest.class);
        suite.addTestSuite(PostgresqlTest.class);
        suite.addTestSuite(PreparedStatementFactoryTest.class);
        suite.addTestSuite(NotNullableDatePoemTypeTest.class);
        suite.addTestSuite(PoemThreadTest.class);
        suite.addTestSuite(UserTest.class);
        suite.addTestSuite(RangedNullableIntegerPoemTypeTest.class);
        suite.addTestSuite(ResultSetEnumerationTest.class);
        suite.addTestSuite(PersistentFactoryTest.class);
        suite.addTestSuite(NullableLongPoemTypeTest.class);
        suite.addTestSuite(NotNullableSetBigDecimalPoemTypeTest.class);
        suite.addTestSuite(MultiThreadedCachedSelectionTest.class);
        suite.addTestSuite(NotNullableReferencePoemTypeTest.class);
        suite.addTestSuite(CapabilityTest.class);
        suite.addTestSuite(FieldTest.class);
        suite.addTestSuite(PreparedStatementFactoryTest.class);
        suite.addTestSuite(ResultSetEnumerationTest.class);
        suite.addTestSuite(DatabaseTest.class);
        suite.addTestSuite(ColumnTest.class);
        suite.addTestSuite(TableTest.class);
/* Fails in Contacts - addedtable does not get dropped.
        suite.addTestSuite(DynamicTableTest.class); */
        suite.addTestSuite(PoemDatabaseTest.class);
        suite.addTestSuite(PoemTransactionTest.class);
        suite.addTestSuite(CachedSelectionTest.class);
        suite.addTestSuite(TableCategoryTest.class);
        suite.addTestSuite(TailoredQueryTest.class);
        suite.addTestSuite(NullableSizedStringPoemTypeTest.class);
        suite.addTestSuite(SessionTokenTest.class);
        suite.addTestSuite(ColumnInfoTest.class);
        suite.addTestSuite(TableMapTest.class);
        suite.addTestSuite(NonSQLPoemTypeTest.class);
        /* Will be different for most projects
        suite.addTestSuite(PoemDatabaseFactoryTest.class);
        */
        suite.addTestSuite(NullableDoublePoemTypeTest.class);
        suite.addTestSuite(DatabaseTest.class);
        suite.addTestSuite(CachedCountTest.class);
        suite.addTestSuite(CapabilityTableTest.class);
        suite.addTestSuite(TableInfoTest.class);
        suite.addTestSuite(NotNullableUnlimitedStringPoemTypeTest.class);
        suite.addTestSuite(ColumnTest.class);
        suite.addTestSuite(BinaryTest.class);
        suite.addTestSuite(TableFactoryTest.class);
        suite.addTestSuite(StringPoemTypeTest.class);
        suite.addTestSuite(DatabaseUnifyWithDBTest.class);
        suite.addTestSuite(NullableUnlimitedStringPoemTypeTest.class);
        suite.addTestSuite(CachedExistsTest.class);
        suite.addTestSuite(DisplayLevelPoemTypeTest.class);
        suite.addTestSuite(GroupCapabilityTest.class);
        suite.addTestSuite(SettingTest.class);
        suite.addTestSuite(SearchabilityPoemTypeTest.class);
        suite.addTestSuite(DeletedPoemTypeTest.class);
        suite.addTestSuite(GroupMembershipTest.class);
        suite.addTestSuite(GroupCapabilityTableTest.class);
        suite.addTestSuite(PoemLocaleTest.class);
        suite.addTestSuite(SearchabilityTest.class);
        suite.addTestSuite(SettingTableTest.class);
        suite.addTestSuite(TableTest.class);
        suite.addTestSuite(NullableBinaryPoemTypeTest.class);
        suite.addTestSuite(NullableDatePoemTypeTest.class);
        suite.addTestSuite(SqlExceptionPoemTypeTest.class);
        suite.addTestSuite(TroidPoemTypeTest.class);
        suite.addTestSuite(ProtectedPersistentTest.class);
        suite.addTestSuite(DisplayLevelTest.class);
        suite.addTestSuite(NotNullableBinaryPoemTypeTest.class);
        suite.addTestSuite(NotNullableBooleanPoemTypeTest.class);
        suite.addTestSuite(NotNullableSizedPasswordPoemTypeTest.class);
        suite.addTestSuite(EverythingDatabaseTest.class);
        suite.addTestSuite(NullableIntegerPoemTypeTest.class);
        suite.addTestSuite(FieldSetTest.class);
        suite.addTestSuite(PreparedTailoredQueryTest.class);
        suite.addTestSuite(NullableReferencePoemTypeTest.class);
        suite.addTestSuite(NotNullablelntegrityFixPoemTypeTest.class);
        suite.addTestSuite(GroupTest.class);
        suite.addTestSuite(DynamicTableTest.class);
        suite.addTestSuite(ColumnTypePoemTypeTest.class);
        suite.addTestSuite(NullableTimestampPoemTypeTest.class);
        suite.addTestSuite(NotNullableLongPoemTypeTest.class);
        suite.addTestSuite(NullableSetBigDecimalPoemTypeTest.class);
        suite.addTestSuite(DatabasePerformInCommittedTransactionTest.class);
        suite.addTestSuite(PersistentTest.class);
        suite.addTestSuite(NotNullableSizedStringPoemTypeTest.class);
        suite.addTestSuite(NotNullableTimestampPoemTypeTest.class);
        suite.addTestSuite(PoemTypeFactoryTest.class);
        suite.addTestSuite(NullableBooleanPoemTypeTest.class);
        suite.addTestSuite(CachedTailoredQueryTest.class);
        suite.addTestSuite(PoemDatabaseTest.class);
        suite.addTestSuite(NotNullableDoublePoemTypeTest.class);
        // Works fin in peom, but not in Contacts
        // suite.addTestSuite(TableSortedMapTest.class);
        suite.addTestSuite(PoemTransactionTest.class);
        suite.addTestSuite(NotNullableIntegerPoemTypeTest.class);
        suite.addTestSuite(NotNullableDefaultBigDecimalPoemTypeTest.class);
        suite.addTestSuite(StandardIntegrityFixTest.class);
        suite.addTestSuite(NullableDefaultBigDecimalPoemTypeTest.class);
        suite.addTestSuite(CachedSelectionTest.class);
        suite.addTestSuite(BooleanPossibleRawEnumerationTest.class);
        suite.addTestSuite(LongEnumerationTest.class);
        suite.addTestSuite(ConsEnumerationTest.class);
        suite.addTestSuite(ArrayUtilsTest.class);
        suite.addTestSuite(CachedIndexFactoryTest.class);
        suite.addTestSuite(DictionaryOrderTest.class);
        suite.addTestSuite(SortUtilsTest.class);
        suite.addTestSuite(IntegerEnumerationTest.class);
        suite.addTestSuite(StringUtilsTest.class);
        suite.addTestSuite(ArrayEnumerationTest.class);
        suite.addTestSuite(LimitedEnumerationTest.class);
        suite.addTestSuite(FilteredEnumerationTest.class);
        suite.addTestSuite(FlattenedEnumerationTest.class);
        suite.addTestSuite(EmptyEnumerationTest.class);
        suite.addTestSuite(EnumUtilsTest.class);
        suite.addTestSuite(ClassUtilsTest.class);
        suite.addTestSuite(CacheTest.class);
        return suite;
    }

}
