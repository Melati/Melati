package org.melati.poem;

public class WritePersistentAccessPoemException
    extends PersistentAccessPoemException {
  public WritePersistentAccessPoemException(
      Persistent object, AccessToken token, Capability capability) {
    super(object, token, capability);
  }

  public String modeDescription() {
    return "write";
  }
}
