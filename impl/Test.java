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
public class Test {  public static void main(String[] args) {    System.out.println("This is Test.java");    String data = "http://www.psl.cs.columbia.edu/";
        Cacheable x = new Cacheable("psl website", data, data.length());
    PersonalCacheModuleImpl pcm2 = new PersonalCacheModuleImpl("Gail");        try {
      pcm2.joinWorkgroup("PSL-Workgroup");
      System.out.println("Gail joined workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {      e.printStackTrace();
      System.out.println("Gail failed to join workgroup: \"PSL-Workgroup\"");    }    
    try {
      pcm2.createWorkgroup("DASADA-Workgroup");
      System.out.println("Gail joined workgroup: \"DASADA-Workgroup\"");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Gail failed to join workgroup: \"DASADA-Workgroup\"");    }     
    try {
      if (pcm2 != null) {
        Cacheable result = pcm2.pullFrom(x);  
        if (result != null) {
          System.out.println("RESULT: "+ result.data);
        } else {          System.out.println("RESULT IS NULL");
        }      }    } catch (Exception e) {      e.printStackTrace();      System.out.println("Miss");
    }
  }}
