package psl.wgcache.impl;
/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        Test.java
 *  *  Description: This is a test class that intantiates PersonalCacheModule and joins a workgroup.
 *               It creates a PersonalCacheModule,OracleTest,that :
 *                  - creates a workgroup - Oracle-Parser *               It creates another PersonalCacheModule,ParserTest,that : *                  - joins the workgroup - Oracle-Parser *                  - creates another workgroup - Parser *               Different functions like put,query,pullFrom,push can be tested using this setup.
 * 
 * Construction: PersonalCacheModules and workgroups to test various functionalities.
 *              
 *   
 * @author  Alpa
 *  
 */
import psl.wgcache.*;
import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import psl.wgcache.support.*;
import psl.wgcache.impl.manager.*;
public class GailWGC {  public static void main(String[] args) {    System.out.println("Gail's personal cache system");    String data = "http://www.psl.cs.columbia.edu/";
    Cacheable x = new Cacheable("psl website", data, data.length());
    PersonalCacheModuleImpl pcm = new PersonalCacheModuleImpl("Gail");        try {
      pcm.createWorkgroup("DASADA-Workgroup");
      System.out.println("Gail created and joined workgroup: \"DASADA-Workgroup\"");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Gail failed to create/join workgroup: \"DASADA-Workgroup\"");    }     
    try {
      pcm.joinWorkgroup("PSL-Workgroup");
      System.out.println("Gail joined existing workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {      e.printStackTrace();
      System.out.println("Gail failed to join workgroup: \"PSL-Workgroup\"");    }    
   /* while (true) {      try {        Thread.currentThread().sleep(1000);        System.out.print(" . ");      } catch (Exception e) { }    }
  */    try {
      if (pcm != null) {
        //Object result = pcm.put(x);          //pcm.pushToWorkgroup(x);
        //Cacheable out = pcm.query(x.key);        //System.out.println("Testing query after put: " + out.data);
      }    } catch (Exception e) {      e.printStackTrace();    }
  }}
