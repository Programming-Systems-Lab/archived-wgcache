package psl.wgcache.roles;

import java.util.*;

import psl.wgcache.*;import psl.wgcache.exception.*;import psl.wgcache.impl.manager.*;import psl.wgcache.support.*;
import psl.wgcache.impl.*;
public interface CacheManager
{	public void put(Object key,Object data,long size);	public Object query(Object queryTag)throws Exception;	
}
