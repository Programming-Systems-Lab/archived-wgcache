package psl.wgcache;

import java.rmi.*;

public interface RMI_PCM extends Remote {
  public Cacheable query(Object queryData)throws WGCException,RemoteException;
  public void WGCPut(Cacheable data)throws RemoteException;
}


