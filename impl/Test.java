package psl.wgcache.impl;

import psl.wgcache.*;
import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.wgcache.support.*;
import psl.wgcache.impl.manager.*;

/**
 * This is just a test class that intantiates PersonalCacheModule and joins a workgroup blah 
 * 
 * @author  Alpa
 *  
 */

 public class Test {
   public static void main(String[] args) {
     String data = "TESTING DATA 1";
     Cacheable x = new Cacheable("TAG1",data,data.length());
     PersonalCacheModuleImpl pcm = new PersonalCacheModuleImpl("Test");
     try {
       pcm.createWorkgroup("blah");
       System.out.println("JOINED A WORKGROUP");
     }catch (WGCException w){}
     if(pcm!=null) {
       pcm.put(x);       
     }
     try {
       pcm.createWorkgroup("blah");
       System.out.println("JOINED A WORKGROUP");
     }catch (WGCException w){
        System.out.println("WORKGROUP ALREADY EXISTS");
      } 
      System.out.println("EXISTING WORKGROUPS:");
      pcm.printWorkgroupNames();
  }
}
