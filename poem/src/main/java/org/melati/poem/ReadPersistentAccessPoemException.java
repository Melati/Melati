package org.melati.poem;

public class ReadPersistentAccessPoemException
    extends PersistentAccessPoemException {
  public ReadPersistentAccessPoemException(
      Persistent object, AccessToken token, Capability capability) {
    super(object, token, capability);
  }

  public String modeDescription() {
    return "read";
  }
}
