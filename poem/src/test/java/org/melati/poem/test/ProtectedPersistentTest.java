/**
 * 
 */
package org.melati.poem.test;

import java.util.Enumeration;

import org.melati.poem.AccessToken;
import org.melati.poem.CachedCount;
import org.melati.poem.Capability;
import org.melati.poem.DeletionIntegrityPoemException;
import org.melati.poem.Field;
import org.melati.poem.Group;
import org.melati.poem.GroupCapability;
import org.melati.poem.GroupMembership;
import org.melati.poem.NonRootSetAccessTokenPoemException;
import org.melati.poem.Persistent;
import org.melati.poem.PoemThread;
import org.melati.poem.ReadPersistentAccessPoemException;
import org.melati.poem.User;
import org.melati.poem.util.StringUtils;

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
  }

  /**
   * {@inheritDoc}
   * @see org.melati.poem.test.PersistentTest#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  protected void databaseUnchanged() { 
    deleteUser("moneypenny");
    deleteUser("smiley");
    deleteUser("bond");
    deleteGroup("officeWorkers");
    deleteGroup("spyMasters");
    deleteCapability("monitor");
    deleteCapability("commission");
    getDb().getUserTable().getTableInfo().setDefaultcanread(null);
    super.databaseUnchanged();
  } 
  private void deleteCapability(String name) {
    Capability c = (Capability)getDb().getCapabilityTable().getNameColumn().firstWhereEq(name);
    
    if (c != null) { 
      System.err.println("Cleaning up: " + c);      
      c.delete();      
    } else { 
      System.err.println("Nothing to clean up");            
    }
  }

  private void deleteUser(String name) {
    User u = (User)getDb().getUserTable().getLoginColumn().firstWhereEq(name);
    if (u != null) { 
      System.err.println("Cleaning up: " + u);      
      u.delete();
    } else 
      System.err.println("Nothing to clean up");      
  }
  private void deleteGroup(String group) {
    Group g = (Group)getDb().getGroupTable().getNameColumn().firstWhereEq(group);
    if (g != null) { 
      System.err.println("Cleaning up: " + g);
      Enumeration<Persistent> gcs = getDb().getGroupCapabilityTable().getGroupColumn().selectionWhereEq(g.getTroid());
      while(gcs.hasMoreElements()) { 
        GroupCapability gc = (GroupCapability)gcs.nextElement();
        System.err.println("Cleaning up: " + gc);
        gc.delete();
      }
      Enumeration<Persistent> gms = getDb().getGroupMembershipTable().getGroupColumn().
                            selectionWhereEq(g.getTroid());
      while (gms.hasMoreElements()) { 
        GroupMembership gm = (GroupMembership)gms.nextElement();
        System.err.println("Cleaning up: " + gm);
        gm.delete();
      }
      try {
        g.delete();
      } catch (DeletionIntegrityPoemException e) {
        Enumeration refs = e.references;
        while (refs.hasMoreElements()) { 
          Object o = refs.nextElement();
          System.err.println("Failed to delete " + g + " due to " +o);
        }
      }
    }
    
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.PersistentTest#testDelete()
   */
  public void testDelete() {
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    super.testDelete();
  }

  /** 
   * {@inheritDoc}
   * @see org.melati.poem.test.PersistentTest#testDeleteAndCommit()
   */
  public void testDeleteAndCommit() {
    if (!getDb().getDbms().canDropColumns()) {
      return;
    }
    super.testDeleteAndCommit();
  }

  /**
   * @see org.melati.poem.Persistent#assertCanRead(AccessToken)
   */
  public void testAssertCanReadAccessToken() {
    Persistent admin = getDb().getUserTable().administratorUser();
    AccessToken guest  = getDb().getUserTable().guestUser();
    if (admin.getTable().getTableInfo().getDefaultcanread() == null) // May be set from previous run
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
    PoemThread.setAccessToken(g);
    try {
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
    /** @see #everythingDatabaseUnchanged() */
    //p.getTable().getTableInfo().setDefaultcanread(null);
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
    /** @see #everythingDatabaseUnchanged() */
    //p.getTable().getTableInfo().setDefaultcanread(null);
  }

  /**
   * @see org.melati.poem.Persistent#getPrimaryDisplayField()
   */
  public void testGetPrimaryDisplayField() {
    Persistent p = getDb().getGroupMembershipTable().getObject(0);
    Field f = p.getPrimaryDisplayField();
    assertEquals("id: 0", f.toString());
  }


  /**
   * @see org.melati.poem.Table#cachedCount(String, boolean, boolean)
   */
  public void testCachedCountStringBooleanBoolean() {
    EverythingDatabase db = (EverythingDatabase)getDb();
    Capability commission = db.getCapabilityTable().ensure("commission");
    Capability monitor = db.getCapabilityTable().ensure("monitor");
    
    User spy = ensureUser("bond");
    User smiley = ensureUser("smiley");
    User moneypenny = ensureUser("moneypenny");

    Protected spyMission = (Protected)db.getProtectedTable().newPersistent();
    spyMission.setCanRead(monitor);
    spyMission.setCanSelect(monitor);
    spyMission.setCanWrite(monitor);
    spyMission.setCanDelete(commission);
    spyMission.setSpy(spy);
    spyMission.setMission("impossible");
    spyMission.setDeleted(false);
    spyMission.makePersistent();

    Group officeWorkers = db.getGroupTable().ensure("officeWorkers");
    GroupMembership inOfficeWorkersMoneyPenny = (GroupMembership)db.getGroupMembershipTable().newPersistent();
    inOfficeWorkersMoneyPenny.setGroup(officeWorkers);
    inOfficeWorkersMoneyPenny.setUser(moneypenny);
    inOfficeWorkersMoneyPenny.makePersistent();
    //GroupCapability officeWorkersMonitor = db.getGroupCapabilityTable().ensure(officeWorkers, monitor);
    
    Group spyMasters = db.getGroupTable().ensure("spyMasters");
    GroupMembership inSpyMastersSmiley = (GroupMembership)db.getGroupMembershipTable().newPersistent();
    inSpyMastersSmiley.setGroup(spyMasters);
    inSpyMastersSmiley.setUser(smiley);
    inSpyMastersSmiley.makePersistent();
    
    db.getGroupCapabilityTable().ensure(spyMasters, commission);
    db.getGroupCapabilityTable().ensure(spyMasters, monitor);
    
    PoemThread.setAccessToken(smiley);
    spyMission.assertCanRead();
    String query = db.getProtectedTable().getMissionColumn().fullQuotedName() + "='impossible'";
    CachedCount cached = db.getProtectedTable().
      cachedCount(query,false,true); 
    assertEquals(1, cached.count());
    
    spyMission.delete();

    
    // Deletion performed above
  }
  
  private User ensureUser(String name) {
    User u = (User)((EverythingDatabase)getDb()).getUserTable().newPersistent();
    u.setLogin(name);
    u.setName(StringUtils.capitalised(name));
    u.setPassword(name);
    u.makePersistent();
    return u;
  }
}
