package psl.wgcache.impl;

import psl.*;
import java.util.*;

public class Cacheable { 
  public Cacheable() {
    this.key = null;
    this.data = null;
    this.size = 0;
  }
  public Cacheable(Object k,Object d,long s){    this.key = k;
    this.data = d;
    this.size = s;
  }    public Object key;  public Object data;
  public long size;
}
