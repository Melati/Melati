package org.melati.poem;

import org.melati.poem.dbms.test.*;
import org.melati.poem.test.*;
import org.melati.poem.util.test.*;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * A class which can be used within Eclipse or from another Maven project to
 * access these tests with the Surefire plugin.
 * 
 * See my comment on http://jira.codehaus.org/browse/SUREFIRE-569
 * 
 * @author timp
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
        suite.addTestSuite(DynamicTableTest.class);
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
        suite.addTestSuite(PoemDatabaseFactoryTest.class);
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
        suite.addTestSuite(TableSortedMapTest.class);
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
