package org.melati.poem;

/**
 * @see CachedVersionedRow#versionForWriting(org.melati.util.Session)
 */

public class WriteCommittedVersionException extends AppBugPoemException {

  public CachedVersionedRow row;

  public WriteCommittedVersionException(CachedVersionedRow row) {
    this.row = row;
  }

  public String getMessage() {
    return
        "The application tried to write to the record " + row +
        " outside a cancellable transaction---you can't do that with Melati, " +
        "sorry.";
  }
}
