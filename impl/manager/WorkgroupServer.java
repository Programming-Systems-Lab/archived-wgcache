package psl.wgcache.impl.manager;

import psl.wgcache.exception.*;
import psl.wgcache.impl.*;
import psl.wgcache.roles.*;
import java.rmi.server.*;

public class WorkgroupServer {
  public static void main(String[] args) {
    try{
      WorkgroupManagerImpl wgm = new WorkgroupManagerImpl();
      Naming.rebind("manager",wgm);
      System.out.println("Server waiting for clients ... ");      