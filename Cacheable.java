package psl.wgcache;

import java.io.*;
import java.util.*;

public class Cacheable implements Serializable { 
  public Object key;
  public Object data;
  public long size;

  public Cacheable() {
    this.key = null;
    this.data = null;
    this.size = 0;
  }
  
  public Cacheable(Object k,Object d,long s){ 
    this.key = k;
    this.data = d;
    this.size = s;
  }  
  
  public String toString() {
    return "Cacheable: (" + key + ", " + data + ", " + size + ") ";
  }
  
}
