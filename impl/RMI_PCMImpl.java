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
public class RMI_PCMImpl extends UnicastRemoteObject implements RMI_PCM {
  PersonalCacheModuleImpl parent;   RMI_PCMImpl(PersonalCacheModuleImpl pcmi) throws RemoteException {      parent = pcmi;
    try {
      Naming.rebind(parent.getName(), this);
    } catch (Exception e) {
      System.out.println("ERROR: RMI_PCMimpl could not register");
      e.printStackTrace();
    }   }  public Cacheable query(Object queryData) throws WGCException, RemoteException {    return (parent.query(queryData));
  }      public void WGCPut(Cacheable data)throws RemoteException {
    parent.genericPut(data);  }
}   
