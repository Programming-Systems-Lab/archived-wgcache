package psl.wgcache.impl;

import psl.*;
import java.util.*;

public class Cacheable implements java.io.Serializable { 
  public Cacheable() {
    this.key = null;
    this.data = null;
    this.size = 0;
  }
  public Cacheable(Object k,Object d,long s){     this.key = k;
    this.data = d;
    this.size = s;
  }  
  public String toString() {    return "Cacheable: (" + key + ", " + data + ", " + size + ") ";
  }  public Object key;  public Object data;
  public long size;
}
