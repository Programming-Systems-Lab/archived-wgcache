package psl.wgcache.impl.manager;

import java.rmi.*;
import java.rmi.server.*;

public class WorkgroupServer {
  public static void main(String[] args) {
    try{      System.out.println("Constructing Server ... ");      
      ManagerImpl mgi = new ManagerImpl();      System.out.println("Binding Server to the registry ... ");      
      Naming.rebind("manager",mgi);
      System.out.println("Server waiting for clients ... ");          }catch (Exception e) {      e.printStackTrace();    }  }} 
