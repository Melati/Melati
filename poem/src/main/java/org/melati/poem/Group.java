package org.melati.poem;

public class Group extends GroupBase {

  public Group() {
  }

  public Group(String name) {
    this.name = name;
  }

  protected void assertCanRead(AccessToken token) {}
}
