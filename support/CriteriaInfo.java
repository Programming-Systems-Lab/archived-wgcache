package psl.wgcache.support;

import psl.wgcache.*;
import psl.wgcache.exception.*;
import psl.wgcache.impl.*;

/**
 * An object of type CriteriaInfo is passed to Criteria objects, so
 * this class must contain all WG cache operations that a Criteria
 * might want to perform.
 */
public interface CriteriaInfo
{
  // values returned by getReceivedVia()
  public static final int VIA_PULL    = 1;
  public static final int VIA_PUSH    = 2;
  public static final int VIA_UNKNOWN = 3;

  public Cacheable getCacheable();    // the object to be cached
  public int       getReceivedVia();  // how the Cacheable was received
  public History   getHistory();
  public Module    getReceivedBy();   // the Module using the criteria
  public String    getReceivedFrom(); // where this Cacheable came from
  public String    getRequester();    // the user this came from
  public RequestTrace getRequestTrace();
  public Module[]  getMembers(); 
  public Module[]  getWorkgroups();

  public void      push(Cacheable x, Module m);
  public Cacheable pull(String name, Module m) throws WGCException;
}
