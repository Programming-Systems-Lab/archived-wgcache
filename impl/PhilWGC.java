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

      pcm.joinWorkgroup("PSL-Workgroup");
      System.out.println("Phil joined existing workgroup: \"PSL-Workgroup\"");
    } catch (Exception e) {
      System.out.println("Phil failed to join workgroup: \"PSL-Workgroup\"");

  }