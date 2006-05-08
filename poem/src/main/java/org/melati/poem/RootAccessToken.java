/**
 * 
 */
package org.melati.poem;

/**
 * An <tt>AccessToken</tt> (aka User) which can do anything.
 *
 */
class RootAccessToken implements AccessToken {
  /**
   * The RootAccessToken gives any Capability.
   * @see org.melati.poem.AccessToken#givesCapability
   */
  public boolean givesCapability(Capability capability) {
    return true;
  }

}
