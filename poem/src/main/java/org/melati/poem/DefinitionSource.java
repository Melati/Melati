package org.melati.poem;

public class DefinitionSource {
  private String name;

  public DefinitionSource(Object what) {
    this.name = what.toString();
  }

  public String toString() {
    return name;
  }

  public static final DefinitionSource
      dsd = new DefinitionSource("the data structure definition"),
      infoTables = new DefinitionSource("the data dictionary"),
      sqlMetaData = new DefinitionSource("the JDBC metadata"),
      runtime = new DefinitionSource("the running application");
}
