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
public class Test {  public static void main(String[] args) {    System.out.println("This is Test.java");
    String data = "TESTING DATA 1";
    Cacheable x = new Cacheable("TAG1",data,data.length());
    //PersonalCacheModuleImpl pcm1 = new PersonalCacheModuleImpl("OracleTest");
    PersonalCacheModuleImpl pcm2 = new PersonalCacheModuleImpl("ParserTest");    //System.out.println("Done creating the personal cache modules");
    /*try {
      pcm1.createWorkgroup("Oracle-Parser");
      System.out.println("PCM1 joined Oracle-Parser");
    }catch (WGCException w){}
    if(pcm1!=null) {
      pcm1.put(x);       
    }         //pcm1.printAllWorkgroupNames();*/
    try {
      pcm2.joinWorkgroup("Oracle-Parser");
      System.out.println(" PCM2 joined Oracle-Parser");
    }catch (Exception w){      w.printStackTrace();
      System.out.println("FAILED to join the group Oracle-Parser");    }
    try {
      pcm2.createWorkgroup("Parser");
      System.out.println("PCM2 joined Parser");
    }catch (WGCException w){} 
    //pcm1.printAllWorkgroupNames();    pcm2.printAllWorkgroupNames();        pcm2.printJoinedWorkgroupNames();    try {
      if(pcm2 !=null) {
        //Cacheable result = pcm1.query(x);  
        Cacheable result = pcm2.pullFrom(new RequestTrace(),x);  
        //pcm1.pushToWorkgroup(x);        //Cacheable result = pcm2.query(x);        //Cacheable result = null;        if(result !=null)
          System.out.println("RESULT: "+ result.data);        else           System.out.println("RESULT IS NULL");      }    }catch (Exception w) {      w.printStackTrace();      System.out.println("Miss");
    }
    //pcm1.printJoinedWorkgroupNames();    //pcm2.printJoinedWorkgroupNames();  }}
