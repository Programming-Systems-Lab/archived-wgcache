package psl.wgcache;

import psl.wgcache.exception.*;
import psl.wgcache.support.*;import psl.wgcache.impl.*;

public interface Module {  public String getName();  
  public Cacheable pullFrom(Object name);
  //public void pushTo(RequestTrace trace, Cacheable x);
  public Criteria getCriteria();
  public void setCriteria(Criteria crit);
}
