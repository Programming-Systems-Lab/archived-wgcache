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
     PersonalCacheModuleImpl pcm1 = new PersonalCacheModuleImpl("Oracle");     PersonalCacheModuleImpl pcm2 = new PersonalCacheModuleImpl("Parser");
     try {
       pcm1.createWorkgroup("Oracle-Parser");
       System.out.println("JOINED Oracle-Parser");
     }catch (WGCException w){}
     if(pcm1!=null) {
       pcm1.put(x);       
     }     try {
       pcm2.createWorkgroup("Parser");
       System.out.println("JOINED Parser");
     }catch (WGCException w){}
     try {
       pcm2.joinWorkgroup("Oracle-Parser");
       System.out.println("JOINED Oracle-Parser");
     }catch (WGCException w){
        System.out.println("FAILED to join the group Oracle-Parser");
      }
     try {
       if(pcm2!=null) {
       Cacheable result = pcm2.query(x);  
       System.out.println("RESULT"+result);       }     }catch (WGCException w) {}           pcm1.printJoinedWorkgroupNames();      pcm2.printJoinedWorkgroupNames();
  }
}
