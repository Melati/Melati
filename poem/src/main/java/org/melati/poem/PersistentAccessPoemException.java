package org.melati.poem;

public abstract class PersistentAccessPoemException
    extends AccessPoemException {

  public Persistent object;

  public PersistentAccessPoemException(Persistent object, AccessToken token,
                                       Capability capability) {
    super(token, capability);
    this.object = object;
  }

  abstract String modeDescription();

  public String getActionDescription() {
    return modeDescription() + " the object " + object.toString();
  }
}
