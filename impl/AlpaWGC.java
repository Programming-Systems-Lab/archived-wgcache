package psl.wgcache.impl;

 * All Rights Reserved.
 * 
 *  Name:        Test.java
 * 
 *               It creates a PersonalCacheModule,OracleTest,that :
 *                  - creates a workgroup - Oracle-Parser
 * 
 * Construction: PersonalCacheModules and workgroups to test various functionalities.
 *              
 *   
 * @author  Alpa
 *  
 */

import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.wgcache.support.*;
import psl.wgcache.impl.manager.*;

      pcm.createWorkgroup("PSL-Workgroup");
      System.out.println("Alpa created and joined workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {
      System.out.println("Alpa failed to create/join workgroup: \"PSL-Workgroup\"");
    System.out.print("Waiting to push data to Gail ");
      try {

      if (pcm != null) {
        String data = "http://www.wgcache.com/";
        Cacheable x = new Cacheable("cool WGC website", data, data.length());
         Object result = pcm.put(x); 
         //pcm.pushToWorkgroup(x);
      }
  }