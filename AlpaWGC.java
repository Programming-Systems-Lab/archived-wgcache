package psl.wgcache;

/** 
 * Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *   
 * @author  Alpa
 *  
 */

public class AlpaWGC {
  public static void main(String[] args) {
    System.out.println("Alpa's personal cache system: workgroup creator");
    PersonalCacheModuleImpl pcm = new PersonalCacheModuleImpl("Alpa");
    
    try {
      pcm.createWorkgroup("PSL-Workgroup");
      System.out.println("Alpa created and joined workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Alpa failed to create/join workgroup: \"PSL-Workgroup\"");
    }
    
    System.out.print("Waiting to push data to Gail ");
    for (int i=0; i<10; i++) {      System.out.print("$");             //Thread.currentThread().sleep(1000);      System.out.print("  .  ");
    }

    System.out.println("\nNow, going to push data to Gail");
    /* try {
      if (pcm != null) {
        String data = "http://www.wgcache.com/";
        Cacheable x = new Cacheable("cool WGC website", data, data.length());
        Object result = pcm.put(x); 
        // pcm.pushToWorkgroup(x);
        // Cacheable out = pcm.query(x.key);
        // System.out.println("Testing query after put: " + out.data);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }*/
  }
}
