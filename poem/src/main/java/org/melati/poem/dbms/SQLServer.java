package org.melati.poem.dbms;

import java.sql.*;

 /**
  * A driver for the Microsoft SQL server 
  **/

public class SQLServer extends AnsiStandard {
  
  public SQLServer() {
    //setDriverClassName("com.merant.datadirect.jdbc.sqlserver.SQLServerDriver"); //buggy
    //setDriverClassName("sun.jdbc.odbc.JdbcOdbcDriver"); //does not work
    //setDriverClassName("com.ashna.jturbo.driver.Driver"); //works
    setDriverClassName("com.jnetdirect.jsql.JSQLDriver"); //works
    //FreeTDS driver now have many unimplemented features and => does not work.
  }

  public String getQuotedName(String name) {
    //if you don't want to set 'use ANSI quoted identifiers' database property
    //to 'true' (on SQL Server)
    
    /*if(name.equalsIgnoreCase("nullable")) return "\"" + name+"\"";
    if(name.equalsIgnoreCase("unique")) return "\"" + name+"\"";
    if(name.equalsIgnoreCase("user")) return "q" + name;
    if(name.equalsIgnoreCase("group")) return "q" + name;
    return name;*/

    //if you already set 'use ANSI quoted identifiers' property to 'true'
    return super.getQuotedName(name);
  }

  public String getSqlDefinition(String sqlTypeName) throws SQLException {
    if (sqlTypeName.equals("BOOLEAN")) {
      return ("BIT");
    }
    return super.getSqlDefinition(sqlTypeName);
  }

  public String getStringSqlDefinition(int size) throws SQLException {
    if (size < 0) { 
      return "TEXT";
    }
    return super.getStringSqlDefinition(size);
  }

}
