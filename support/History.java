package psl.wgcache.support;

import psl.wgcache.impl.*;
import psl.wgcache.*;
import java.util.*;

// Very simple history -- just keeps track of accesses and times
public class History{
  public static final int MAX_ACCESSES = 64; // only track the last 64 accesses
  private Hashtable accessHash; // String --> Vector

  public History(){
    accessHash = new Hashtable();
  }

  public void addAccess(Object docName)  {
    Vector accessVec = (Vector)accessHash.get(docName);
    if(accessVec == null) {
      accessVec = new Vector();
      accessHash.put(docName, accessVec);
    }
    accessVec.addElement(new Date());
    while(accessVec.size() > 64)
      accessVec.removeElementAt(0);
  }

  public Date[] getAccesses(Object docName)  {
    Date[] dates;
    Vector dateVec = (Vector)accessHash.get(docName);
    if(dateVec == null) {
      dates = new Date[0];
    } else {
      dates = new Date[dateVec.size()];
      dateVec.copyInto(dates);
    }
    return dates;
  }

  // have there been n accesses of docName since d?
  public boolean accessesSince(Object docName, int n, Date d)  {
    int sofar = 0;
    Date[] accesses = getAccesses(docName);
    for(int i = accesses.length - 1; i >= 0; i--)
      if(accesses[i].before(d)) {
	break;
      } else {
	if(++sofar >= n)
	  break;
      }
    System.err.println("History.accessesSince(\"" + docName + "\", " + n
		       + ", " + d + ")");
    return (sofar >= n);
  }
}
