package psl.wgcache.roles;

import java.rmi.*;
import psl.wgcache.exception.*;
import psl.wgcache.impl.*;

public interface RMI_PCM extends Remote {
  public Cacheable query(Object queryData)throws WGCException,RemoteException;
  public void WGCPut(Cacheable data)throws RemoteException;}


