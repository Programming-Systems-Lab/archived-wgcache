package psl.wgcache;

import java.rmi.*;
import java.rmi.server.*;
import java.net.*;

public class WorkgroupServer {
  public static WorkgroupManagerImpl wgm = null;
  public static void main(String[] args) {
    try{
      System.out.println("Constructing Server ... ");      
      wgm = new WorkgroupManagerImpl();
      System.out.println("Binding Server to the registry ... ");   
      //String url = "rmi://"+InetAddress.getLocalHost().getHostName()+ ":9999/manager";
      //System.out.println("manager" + url);
      Naming.rebind("manager", wgm);
      System.out.println("Server waiting for clients ... ");      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
} 
