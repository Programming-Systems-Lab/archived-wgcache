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

    Cacheable x = new Cacheable("psl website", data, data.length());
    PersonalCacheModuleImpl pcm = new PersonalCacheModuleImpl("Gail");
      pcm.createWorkgroup("DASADA-Workgroup");
      System.out.println("Gail created and joined workgroup: \"DASADA-Workgroup\"");
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Gail failed to create/join workgroup: \"DASADA-Workgroup\"");
    try {
      pcm.joinWorkgroup("PSL-Workgroup");
      System.out.println("Gail joined existing workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {
      System.out.println("Gail failed to join workgroup: \"PSL-Workgroup\"");
   /* while (true) {
  */
      if (pcm != null) {
        //Object result = pcm.put(x);  
        //Cacheable out = pcm.query(x.key);
      }
  }