package psl.wgcache.impl;

import java.util.*;
import java.io.*;
import java.sql.*;
import org.hsql.*;

public class DBInterface {
  /* Name of database */
  private String dbname;
  Connection conn = null;
  private String tableName = null;    
  /**
   * Constructor
   *
   */
  public DBInterface(String userTableName) {
    dbname = userTableName;
    tableName = userTableName+"blah";
        try {
      System.out.println("DBName :" + dbname);
      System.out.println("TableName :" + tableName);      Class.forName("org.hsql.jdbcDriver");      conn=DriverManager.getConnection("jdbc:HypersonicSQL:"+dbname,"sa","");
    }
    catch(Exception e) {      e.printStackTrace();    }    try {
      Statement stat=conn.createStatement();
      stat.execute("CREATE TABLE "+tableName+"(key varchar(2000),data varchar(20000))");    }
    catch(SQLException ex) { //Throw an exception if table already exists 
      // ex.printStackTrace();
      System.out.println("Table already exists : with the name :" + tableName);
    }
    catch(Exception e) {      e.printStackTrace();
    }   
  }  public synchronized void shutdown() {    try  {      /* Close the db and terminate the session */      conn.close();	       }    catch(Exception e) {
      e.printStackTrace();
    }
  }  public synchronized Object get(Object queryRequest) {
    // Create a statement object
    ResultSet dbReply = null;
    Object dataReturned = null;    Object keyReturned = null;
    try {      Statement stat=conn.createStatement();      dbReply =stat.executeQuery("SELECT * FROM " + tableName +  " WHERE key = '"+ queryRequest +"'");      if(dbReply.next()) { //if data is found i.e.HIT
        keyReturned = (Object)dbReply.getString(1);        dataReturned = (Object)dbReply.getString(2);
      }
      else        dataReturned = null;    }    catch(SQLException ex) {      System.out.println("SQL exception: " + ex);    }    return dataReturned;  }  public synchronized void put(Object key, Object data) {
    try   {
       //use PreparedStatement as data may contain "'"
       PreparedStatement prep=conn.prepareCall("INSERT INTO " + tableName + " (key,data) VALUES (?,?)");
       prep.clearParameters();       // Fill the first parameter: key
       prep.setString(1,key.toString());       // Fill the second parameter: key
       prep.setString(2,data.toString());
       prep.execute();
       prep.close();
    }
    catch(SQLException ex) {      
       System.out.println("SQL exception: " + ex);
    }
 }  public synchronized void remove(Object key) {
    try  {
      Statement stat=conn.createStatement();      ResultSet result=stat.executeQuery("DELETE FROM " + tableName + " WHERE key='"+ key + "'" );
    }    catch(SQLException ex) {
        System.out.println("SQL exception: " + ex);    }  }
  public static void main(String[] args) {    DBInterface db = new DBInterface("WGcache12");      System.out.println("I am done");
    db.put("k1","Trying1");
    try {      String s =(String)db.get("k1");      System.out.println("DATA RECEIVED"+s);
    }catch (Exception e){}    db.shutdown();
  }}


