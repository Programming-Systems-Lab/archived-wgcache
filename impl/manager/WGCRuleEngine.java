package psl.wgcache.impl.manager;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 *  Name:        WorkgroupManagerImpl.java
 *               It maintains a list of all the workgroups and provides methods to
 * 
 * Construction: A manager that has its own cache which is the shared cache and a list of the workgroups.
 * 
 * 
 * @author  Alpa
 *  
 */
import psl.wgcache.impl.*;

interface WGCRuleEngine {
  public void what_do_i_do_next(String instigator, Cacheable data);
}