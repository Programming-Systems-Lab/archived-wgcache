package psl.wgcache;

import java.util.*;

public interface Workgroup extends Module {
  public void setName(String name);
  public void addMember(String memberUrl, String PCMName);
  public void removeMember(String PCMName);  
  public boolean containsMember(String pcmName);
  //public void accessNotify(RequestTrace trace, Object name);
  public boolean compareTo(Workgroup fromClient);
  public Cacheable pullFrom(Object cname);
  public int numMembers();
  public void removeAll();
  public void pushToWorkgroup(String dataSrc,Cacheable x);  
  public void pushToMember (String memDataSrc, Cacheable x, String targetMember);
}
