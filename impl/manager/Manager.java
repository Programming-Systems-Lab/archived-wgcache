package psl.wgcache.impl.manager;

public class Manager{  private static WorkgroupManagerImpl wgmanager;   public Manager(){    wgmanager = new WorkgroupManagerImpl();  }
  public WorkgroupManagerImpl getManager(){    return wgmanager;  
  }}
