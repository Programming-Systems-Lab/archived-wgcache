package psl.wgcache.impl;

import java.util.*;
import java.io.*;

public class HashtableDBInterface {
  private String TABLENAME;
  private final static String CLASSNAME   = "HashtableDBInterface";
  
  /* Name of database */
  private static String dbname;          
  
  /* The database */
  private Hashtable h;
  
  /**
   * Constructor
   * 
   */
  public HashtableDBInterface(String userTableName) throws Exception {
    TABLENAME = userTableName;
    dbname = userTableName+".dat";
    try {
      FileInputStream fis = new FileInputStream(dbname);
      if (fis != null) { /* Already exists */
	ObjectInputStream ois = new ObjectInputStream(fis);
	h = (Hashtable)ois.readObject();
	ois.close();
	fis.close();
      } else {
	h = new Hashtable();
      }
    } catch(Exception e) { e.printStackTrace(); }
  }
    
  public synchronized void shutdown() {
    try { 
      /* Close the db and terminate the session */
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbname));
      oos.writeObject(h);
      oos.close();
    } catch(Exception e) { e.printStackTrace(); }
  }

  public synchronized Object get(Object queryTag) {
    return h.get(queryTag);
  }
    
  public synchronized void put(Object key, Object data) {
    h.put(key,data);
  }  
}


