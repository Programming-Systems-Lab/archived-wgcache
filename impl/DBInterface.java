package psl.wgcache.impl;

import java.util.*;
import java.io.*;
import com.odi.*;
import com.odi.util.*;

public class DBInterface {
 /* Name of roots */
    private /* bad! static */ String TABLENAME;
  private final static String CLASSNAME   = "DBInterface";
  
    /* Name of database */
  private static String dbname;          

  /* Global session context shared by all threads */
  private Session session               = null;
  private Database db                   = null;
  private Map table                     = null;
  private Transaction tr                = null;
  
    /**
   * Constructor
   * 
   */
  public DBInterface(String userTableName) throws Exception {
    TABLENAME = userTableName;
    dbname = userTableName+".odb";
    if(session == null) {
      //session = Session.createGlobal(null, null);
      Properties props = System.getProperties();
      props.put("com.odi.useDatabaseLocking", "false");
      session = Session.create(null,props);
      // session = Session.create(null, null);
      session.join();
      Transaction.setDefaultAbortRetain(ObjectStore.RETAIN_HOLLOW);
    }

    /* Open the database or create a new one if necessary. */
    try {
      db = Database.open(dbname, ObjectStore.OPEN_UPDATE);
      // System.out.println( " Opened database(" + dbname + ")");
    } catch (DatabaseNotFoundException e) {
      db = Database.create(dbname, ObjectStore.ALL_READ | ObjectStore.ALL_WRITE);
      // System.out.println( " Created database(" + dbname + ")");
    }

    /* Find the table root or create it if it is not there. */
    tr = Transaction.begin(ObjectStore.UPDATE);

    try {
      table = (Map)db.getRoot(TABLENAME);
      // System.out.println(CLASSNAME + " Loaded root(" + TABLENAME + ")");
    } catch (DatabaseRootNotFoundException e) {
      /* Create a database root and associate it with a hashtable
       * that will contain the extent for all tags
       */
      table = new OSHashMap();
      db.createRoot(TABLENAME, table);
      // System.out.println(CLASSNAME + " Created root(" + TABLENAME + ")");
    } finally {
      /* End the transaction */
      tr.commit(ObjectStore.RETAIN_HOLLOW);
    }    
  }
  
  
  public synchronized void shutdown() {
    System.out.println(CLASSNAME + " Server Shutdown requested");
    try { 
      /* Close the db and terminate the session */
      db.close();
      session.leave();
      session.terminate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public Object get(Object queryTag) {
    tr = Transaction.begin(ObjectStore.UPDATE);
    Object temp = table.get(queryTag);
    /* End the transaction */
    tr.commit(ObjectStore.RETAIN_HOLLOW);
    return temp;
  }
  
  public void put(Object key, Object data) {
    tr = Transaction.begin(ObjectStore.UPDATE);
    table.put(key,data);  
    /* End the transaction */
    tr.commit(ObjectStore.RETAIN_HOLLOW);
  }  
}
