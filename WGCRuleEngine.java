package psl.wgcache;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        WorkgroupManagerImpl.java
 *  Description: This class implements the workgroup manager.
 *               Currently there is only one manager for the workgroups.
 *               It maintains a list of all the workgroups and provides methods to
 *               create new workgroup, delete workgroups.
 * 
 * Construction: A manager that has its own cache which is the shared cache and a list of the workgroups.
 * 
 * 
 * 
 * @author  Alpa
 *  
 */

public interface WGCRuleEngine {
  public void what_do_i_do_next(String instigator, Cacheable data);
}
