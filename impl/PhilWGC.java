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
public class PhilWGC {  public static void main(String[] args) {    System.out.println("Phil's personal cache system: an innocent bystander");    PersonalCacheModuleImpl pcm = new PersonalCacheModuleImpl("Phil");        try {
      pcm.joinWorkgroup("PSL-Workgroup");
      System.out.println("Phil joined existing workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {      e.printStackTrace();
      System.out.println("Phil failed to join workgroup: \"PSL-Workgroup\"");    }

  }}
