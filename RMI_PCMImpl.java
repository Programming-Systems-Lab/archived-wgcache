package psl.wgcache;

/** Copyright (c) 2000: The Trustees of Columbia University and the City of New York.
 * All Rights Reserved.
 * 
 * 
 * @author  Alpa
 *  
 */
 
import java.rmi.*;
import java.rmi.server.*;

public class RMI_PCMImpl extends UnicastRemoteObject implements RMI_PCM {
  PersonalCacheModuleImpl parent; 
  RMI_PCMImpl(PersonalCacheModuleImpl pcmi) throws RemoteException {  
    parent = pcmi;
    try {
      String url = parent.getURL();
      Naming.rebind(url, this);
    } catch (Exception e) {
      System.out.println("ERROR: RMI_PCMimpl could not register");
      e.printStackTrace();
    } 
  }
  public Cacheable query(Object queryData) throws WGCException, RemoteException {
    return (parent.query(queryData));
  }    
  public void WGCPut(Cacheable data)throws RemoteException {
    // System.out.println(parent.getName() + ": saved [into personal cache] the data: ("+ data.key + "," + data.data +") pushed by WGCManager");
    // System.out.println(data);
    parent.genericPut(data);
  }
}   
