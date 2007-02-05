/**
 * 
 */
package org.melati.poem.test;

import org.melati.poem.AccessToken;
import org.melati.poem.Field;
import org.melati.poem.NonRootSetAccessTokenPoemException;
import org.melati.poem.Persistent;
import org.melati.poem.PoemThread;
import org.melati.poem.ReadPersistentAccessPoemException;

/**
 * @author timp
 * @since 26 Jan 2007
 *
 */
public class ProtectedPersistentTest extends PersistentTest {

  /**
   * Constructor.
   * @param name
   */
  public ProtectedPersistentTest(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PersistentTest#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    setDbName(PoemTestCase.everythingDatabaseName);
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PersistentTest#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  /**
   * @see org.melati.poem.Persistent#assertCanRead(AccessToken)
   */
  public void testAssertCanReadAccessToken() {
    Persistent admin = getDb().getUserTable().administratorUser();
    AccessToken guest  = getDb().getUserTable().guestUser();
    admin.assertCanRead(guest);
    admin.getTable().getTableInfo().setDefaultcanread(getDb().getCanAdminister());
    try {
      admin.assertCanRead(guest);
      System.err.println("WTF:" + guest.givesCapability(getDb().administerCapability()));
      fail("Should have bombed");
    } catch (ReadPersistentAccessPoemException e) {
      e = null;
    }
    AccessToken a  = getDb().getUserTable().administratorUser();
    admin.assertCanRead(a);
    admin.getTable().getTableInfo().setDefaultcanread(null);
  }

  /**
   * @see org.melati.poem.Persistent#assertCanRead()
   */
  public void testAssertCanRead() {
    Persistent p = getDb().getUserTable().administratorUser();
    p.assertCanRead();
    p.getTable().getTableInfo().setDefaultcanread(getDb().getCanAdminister());
    AccessToken g  = getDb().getUserTable().guestUser();
    try {
      PoemThread.setAccessToken(g);
      p.assertCanRead();
      fail("Should have bombed");
    } catch (ReadPersistentAccessPoemException e) {
      e = null;
    }
    AccessToken a  = getDb().getUserTable().administratorUser();
    try { 
      PoemThread.setAccessToken(a);
      fail("Should have bombed");
    } catch (NonRootSetAccessTokenPoemException e) {
      e = null;
    }
  }

  /**
   * @see org.melati.poem.Persistent#getReadable()
   */
  public void testGetReadable() {
    Persistent p = getDb().getUserTable().administratorUser();
    assertTrue(p.getReadable());
    p.getTable().getTableInfo().setDefaultcanread(getDb().getCanAdminister());
    AccessToken g  = getDb().getUserTable().guestUser();
    PoemThread.setAccessToken(g);
    assertFalse(p.getReadable());
  }

  /**
   * @see org.melati.poem.Persistent#getPrimaryDisplayField()
   */
  public void testGetPrimaryDisplayField() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Field f = p.getPrimaryDisplayField();
    assertEquals("id: 0", f.toString());
    Persistent p2 = ((TestDatabase)getDb()).getENExtendedTable().newPersistent();
    p2.setCooked("stringfield2", "primary search field");
    p2.makePersistent();
    Field f2 = p2.getPrimaryDisplayField();
    assertEquals("stringfield2: primary search field", f2.toString());
    p2.delete();
  }

  
}
