package psl.wgcache.impl.manager;/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        Manager.java *  Description: This class implements a manager which creates a workgroup manager. *               Currently there is only one workgroupmanager for the workgroups. *               It provides only one method that returns a workgroup manager which is static.
 * 
 * Construction: A static workgroup manager.
 * 
 *  * 
 * @author  Alpa
 *  
 */

public class Manager{
  private static WorkgroupManagerImpl wgmanager;

  public Manager(){
    wgmanager = new WorkgroupManagerImpl();
  }
  public WorkgroupManagerImpl getManager(){
    return wgmanager;  
  }
}
