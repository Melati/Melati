/*
 *
 * Copyright (C) 2002 Peter Kehl
 * 
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *
 */

/*
Important notes - maybe to be added to org.melati.LogicalDatabases.properties,
because end users don't read sources.

0. Working with MySQL 3.23.24, MM.MySQL 2.0.11 JDBC driver (GPL)
   from http://mmmysql.sourceforge.net

1. Use JDBC URL of type jdbc:mysql://[host][:port]/dbname[?param=value[...]]
   ie. the simpliest one has 3 slashes: jdbc:mysql:///melatitest

2. Don't use asterix * for password, leave it empty (end of line), as:
   org.Melati.LogicalDatabase.melatitest.pass=

2a. or use explicit username and password and 
   GRANT ALL PRIVILEGES ON <dbname> 
   TO <username>@localhost IDENTIFIED BY '<password>';
3. Start MySQL in ANSI mode, for quoting names - ANSI.getQuotedName(String name)
   FIXME: Or change quotedName(), so MySQL runs in faster no-ANSI?
   
4. Start MySQL with transactioned tables as default. InnoDB is stable,
   BDB about being stable. 

   FIXME: Untested
   safe_mysqld --user=mysql --ansi --default-table-type=InnoDB

   Works for me:
   safe_mysqld --user=mysql --ansi --default-table-type=BDB 

5. FIXME: Allow TCP connections, as JDBC can't use unix ports.

6. FIXME: I tried to get the MySQL BOOL type to work, but it was 
   set to tinyint ....

*/

package org.melati.poem.dbms;

import java.sql.*;
import org.melati.poem.*;
import java.util.*;

public class MySQL extends AnsiStandard {


    public MySQL() {
       setDriverClassName("org.gjt.mm.mysql.Driver");	
    }

    // this should probably be MySQL type BOOL == char(1)
    public String getSqlDefinition(String sqlTypeName) throws SQLException {
	if( sqlTypeName.equals("BOOLEAN"))
	    return "INT"; 
//	    return "BOOL"; 
	return super.getSqlDefinition(sqlTypeName);
    }

    public String getStringSqlDefinition(int size) throws SQLException {
        if (size < 0) { 
            return "TEXT";
        }
        return super.getStringSqlDefinition(size); //VARCHAR(size) is OK
    }

    public String getBinarySqlDefinition(int size) throws SQLException {
        return "BLOB"; //How to define BLOB of limited size?
    }


    public static class MySQLStringPoemType extends StringPoemType {
    /*FIXME: 
      StringPoemType(boolean nullable, int size) calls
      SizedAtomPoemType(Types.VARCHAR, "VARCHAR", nullable, size)
      This works, however is it supposed to work this way? 
      Shouldn't it be
      MySQLStringPoemType be direct subclass of SizedAtomPoemType
      and call SizedAtomPoemType(Types.VARCHAR, "TEXT"..)?
     */
        public MySQLStringPoemType(boolean nullable, int size) {
            super(nullable, size);
        }

	//_assertValidRow(Object) is defined OK in StringPoemType

	//MySQL returns metadata info size 65535 for TEXT type
	protected boolean _canRepresent(SQLPoemType other) {
	   return
	        other instanceof StringPoemType &&
	        (getSize()<0 || getSize()==65535 ||
		 getSize()>=((StringPoemType)other).getSize() );
	}

        public PoemType canRepresent(PoemType other) {
            return other instanceof StringPoemType &&
		    _canRepresent((StringPoemType)other) &&
                   !(!getNullable() && ((StringPoemType)other).getNullable()) ?
                       other : null;
        }
    }


    public static class MySQLBooleanPoemType extends BooleanPoemType {
        public MySQLBooleanPoemType(boolean nullable) {
            super(nullable);
        }

	protected Object _getRaw(ResultSet rs, int col) throws SQLException {
 		synchronized (rs) {
   			int i = rs.getInt(col);
      			return rs.wasNull() ? null :
				(i==1 ? Boolean.TRUE : Boolean.FALSE);
    		}
  	}
        /*
	protected Object _getRaw(ResultSet rs, int col) throws SQLException {
 		synchronized (rs) {
   			String v = rs.getString(col);
      			return rs.wasNull() ? null :
				(v.equals("t") ? Boolean.TRUE : Boolean.FALSE);
    		}
  	}
        */

        protected void _setRaw(PreparedStatement ps, int col, Object bool)
	throws SQLException {
	    ps.setInt(col, ((Boolean)bool).booleanValue() ? 1 : 0 );
	}
        /*
        protected void _setRaw(PreparedStatement ps, int col, Object bool)
        throws SQLException {
            if (bool instanceof Boolean && bool == Boolean.TRUE) 
              ps.setString(col, "t");
            else
              ps.setString(col, "f");
        }
        */
        
	// We can leave original method from BooleanPoemType, 
        // it recognizes 0/1
	protected Object _rawOfString(String rawString)
	throws ParsingPoemException {
		rawString = rawString.trim();
		switch (rawString.charAt(0)) {
			case '1': return Boolean.TRUE;
       			case '0': return Boolean.FALSE;
		        default: throw new ParsingPoemException(this, rawString);
		}
	}
        
    }


    //About the same as in Postgresql.java, hope it's OK.
    public static class BlobPoemType extends IntegerPoemType {
        public BlobPoemType(boolean nullable) {
            super(Types.INTEGER, "BLOB", nullable);
        }

        protected boolean _canRepresent(SQLPoemType other) {
            return other instanceof BinaryPoemType;
        }

        public PoemType canRepresent(PoemType other) {
            return other instanceof BinaryPoemType &&
                   !(!getNullable() && ((BinaryPoemType)other).getNullable()) ?
                       other : null;
        }
    }

    public PoemType canRepresent(PoemType storage, PoemType type) {
      if (storage instanceof IntegerPoemType &&
          type instanceof BooleanPoemType) {
        return type;
      } else {
        return storage.canRepresent(type);
      }
    }


    public SQLPoemType defaultPoemTypeOfColumnMetaData(ResultSet md)
        throws SQLException {
	ResultSetMetaData rsmd= md.getMetaData();

        /*
	try{
    	    int num= rsmd.getColumnCount();
	    System.err.println("rsmd has "+new Integer(num)+" columns:");
	    for( int i=0; i<num; i++) {
		try {System.err.print( "type: "+rsmd.getColumnType(i) ); }
		catch(Exception e) {}
		try {System.err.println( "name: "+rsmd.getColumnName(i) );}
		catch(Exception e) {}
	    }
	} catch(SQLException e) {}
        System.err.println("TYPE:" + md.getString("TYPE_NAME"));
        */

        //I leave case as Postgres driver has it.

	if( md.getString("TYPE_NAME").equals("blob") )
	    return new BlobPoemType( md.getInt("NULLABLE") ==
                                  DatabaseMetaData.columnNullable );
	else
	if( md.getString("TYPE_NAME").equals("text") )
	    return new MySQLStringPoemType( md.getInt("NULLABLE")==
		DatabaseMetaData.columnNullable, md.getInt("COLUMN_SIZE") );
	else
	if( md.getString("TYPE_NAME").equals("boolean") )
	    return new MySQLBooleanPoemType( md.getInt("NULLABLE")==
		DatabaseMetaData.columnNullable );
	else
    	    return super.defaultPoemTypeOfColumnMetaData(md);
    }


    public SQLPoemException exceptionForUpdate(
        Table table, String sql, boolean insert, SQLException e) {

      String m = e.getMessage();

      // MySQL's duplicate key (or any unique field) message is:
      // "ERROR 1062: Duplicate entry '106' for key 1"
      //  Duplicate index value      <--|           |
      //                                            |
      //  Which 'index' (unique field) it is, in <--|
      //  order as table was defined, starting from 1.

      if (m != null &&
          m.indexOf("1062") >= 0) {

	// It's not simple as in Postgres. This duplicated 'index' is one
	// of possibly more unique columns. That involves searching for its
	// column. For error "Duplicate entry '106' for key 4"
	// we search 4th unique field = we loop over columns, skip first 3 that
	// are unique and return 4th unique.

	try { //Try parsing error message.

	    int preIndex, postIndex; //Places of apostrophes around index value
	    int preColumn; //Place of "key ", which is in front of column number
		
    	    preIndex= m.indexOf('\'');
	    postIndex= m.lastIndexOf('\'');
	    preColumn= m.indexOf("key ");
	
	    String indexValue= m.substring(preIndex+1,postIndex);
	    String indexColumn= m.substring(preColumn+4);

            System.err.println("Duplicated value "+indexValue+
		" of "+indexColumn+"th unique field."); 
	
    	    int indexNum= Integer.parseInt(indexColumn);
	    Column column= table.troidColumn(); //Just to satisfy compiler.
	    //At the end, it will (should) be our column anyway.
	    
    	    for( Enumeration columns= table.columns();
		columns.hasMoreElements();)
	    {
		column= (Column)columns.nextElement();
		if( column.getUnique())
	    	    if( --indexNum==0)
			break; //We found it!
            }
	    //Now, it's found & indexNum==0.
    	    if(indexNum==0)
    		return new DuplicateKeySQLPoemException( column, sql, insert, e);
	}
	catch( NumberFormatException f) {}
		
        return new DuplicateKeySQLPoemException(table, sql, insert, e);
      }

      return super.exceptionForUpdate(table, sql, insert, e);
    }

  public String unreservedName(String name) {
    if(name.equalsIgnoreCase("group")) name = "melati_" + name;
    return name;
  }
  public String melatiName(String name) {
    if(name.equalsIgnoreCase("melati_group")) name = "group";
    return name;
  }

/*
 MySQL requires TEXT and BLOB field indices to have an 
 explicit length, 30 should be fine.
 */

  public String getIndexLength(Column column) {
    PoemType t = column.getType();
    if (t instanceof StringPoemType && ((StringPoemType)t).getSize() < 0) return "(30)";
    if (t instanceof BlobPoemType) return "(30)";
    return "";
  }  

  public String givesCapabilitySQL(User user, String capabilityExpr) {
    return
        "SELECT groupmembership.*  " + 
        "FROM groupmembership LEFT JOIN groupcapability " +
        "ON groupmembership." + getQuotedName("group") +
        " =  groupcapability." + getQuotedName("group") + " " +
        "WHERE " + getQuotedName("user") + " = " + user.troid() + " " +
        "AND groupcapability." + getQuotedName("group") + " IS NOT NULL " +
        "AND capability = " + capabilityExpr;
  }


}










