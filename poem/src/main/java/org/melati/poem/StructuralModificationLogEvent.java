package org.melati.poem;

public class StructuralModificationLogEvent extends SQLLogEvent {
  public StructuralModificationLogEvent(String sql) {
    super(sql);
  }

  public String toString() {
    return "Structural modification\n" + super.toString();
  }
}
