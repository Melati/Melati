package org.melati.poem;

public class TableInfoData extends TableInfoDataBase {

  public TableInfoData(String name, String displayname,
                       int displayorder, String description) {
    this.name = name;
    this.displayname = displayname;
    this.displayorder = new Integer(displayorder);
    this.description = description;
  }

  public TableInfoData() {
  }
}
