package org.melati.poem;

public class TableInfoData extends TableInfoDataBase {

  public TableInfoData(String name, String displayname,
                       int displayorder, String description,
                       Integer cachelimit, boolean seqcached) {
    this.name = name;
    this.displayname = displayname;
    this.displayorder = new Integer(displayorder);
    this.description = description;
    this.cachelimit = cachelimit;
    this.seqcached = seqcached ? Boolean.TRUE : Boolean.FALSE;
  }

  public TableInfoData() {
  }
}
