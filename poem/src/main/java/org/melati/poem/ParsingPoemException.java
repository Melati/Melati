package org.melati.poem;

public class ParsingPoemException extends NormalPoemException {
  public PoemType type;
  public String string;

  public ParsingPoemException(PoemType type, String string,
                              Exception exception) {
    super(exception);
    this.type = type;
    this.string = string;
  }

  public ParsingPoemException(PoemType type, String string) {
    this(type, string, null);
  }
}
