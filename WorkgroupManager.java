package psl.wgcache;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public interface WorkgroupManager extends Remote {
  public void newWorkgroup(String name) throws WGCException, RemoteException;
  public void deleteWorkgroup(String name) throws WGCException,RemoteException;   
  public Enumeration getWorkgroups() throws RemoteException;
  public String[] getWorkgroupNames() throws RemoteException;
  public Cacheable query(Object cname) throws WGCException,RemoteException;
  public void joinWorkgroup(String wgName, String url, String roleName) throws WGCException,RemoteException;
  public void leaveWorkgroup(String wgName, String roleName) throws WGCException,RemoteException;  
  public void accessNotify(String pcmName, Cacheable data) throws RemoteException;
  public Cacheable pullFrom(Object queryData, String wgName)throws WGCException,RemoteException;
  public void pushToWorkGroup(String dataSrc, Cacheable toBePushed,String wgName) throws WGCException,RemoteException;
}
