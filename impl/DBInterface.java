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
    dbname = userTableName+".dat";      
    try {
      Class.forName("org.hsql.jdbcDriver");
      conn=DriverManager.getConnection("jdbc:HypersonicSQL:dbname","sa","");
      Statement stat=conn.createStatement();
      stat.execute("CREATE TABLE " + userTableName + "(key varchar(2000),data varchar(20000))");
    }
    catch(SQLException ex) { //Throw an exception if table already exists       System.out.println("Table already exists : with the name :" + userTableName);
    }
    catch(Exception e) {
      e.printStackTrace();
    }  }  public synchronized void shutdown() {    try  {
	  /* Close the db and terminate the session */
      conn.close();	   
    }
    catch(Exception e) {
        e.printStackTrace();    }  }  public synchronized Object get(Object queryRequest) {
    // Create a statement object
    ResultSet dbReply = null;
    Object dataReturned = null;
    try {      Statement stat=conn.createStatement();      dbReply =stat.executeQuery("SELECT element FROM " + tableName +  " WHERE key = '"+ queryRequest +"'");      if(dbReply.next()) //if data is found i.e.HIT        dataReturned = (Object)dbReply.getString(1);      else        dataReturned = null;    }    catch(SQLException ex) {      System.out.println("SQL exception: " + ex);    }    return dataReturned;  }  public synchronized void put(Object key, Object data) {
    try   {
       //use PreparedStatement as data may contain "'"
       PreparedStatement prep=conn.prepareCall("INSERT INTO " + tableName + " (key,element) VALUES (?,?)");
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
        System.out.println("SQL exception: " + ex);    }  }}


