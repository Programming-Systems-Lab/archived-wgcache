package psl.wgcache.roles;

import java.util.*;
import psl.wgcache.*;import psl.wgcache.exception.*;import psl.wgcache.impl.manager.*;import psl.wgcache.support.*;import psl.wgcache.impl.*;

public interface Workgroup extends Module {
  public void setName(String name);  public void addMember(PersonalCacheModule member);
  public void removeMember(PersonalCacheModule member);
  public PersonalCacheModule[] members();
  public void accessNotify(RequestTrace trace, Object name);
}
