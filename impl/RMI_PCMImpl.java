package psl.wgcache.impl;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 * 
 * @author  Alpa
 *  
 */
 
import psl.wgcache.exception.*;
import psl.wgcache.roles.*;
import java.rmi.*;
import java.rmi.server.*;

  PersonalCacheModuleImpl parent; 
    try {
      Naming.rebind(url, this);
    } catch (Exception e) {
      System.out.println("ERROR: RMI_PCMimpl could not register");
      e.printStackTrace();
    } 
  }    
    System.out.println(parent.getName() + ": saved [into personal cache] the data: ("+ data.key + "," + data.data +") pushed by WGCManager");
    parent.genericPut(data);
}   