package psl.wgcache.support;

import java.util.Vector;

/*
 * A RequestTrace instance is passed with a push or pull request to a
 * module, to allow that module to tell from whence the request came.
 */
public class RequestTrace
{
  private Vector hops;
  private String requester = null;

  public RequestTrace()
  {
    hops = new Vector();
  }

  public RequestTrace(String requester)
  {
    hops = new Vector();
    this.requester = requester;
  }

  public void addHop(String name)
  {
    hops.addElement(name);
  }

  public String getLastHop()
  {
    return (String)hops.lastElement();
  }

  public String[] getHops()
  {
    String[] hopArr = new String[hops.size()];
    hops.copyInto(hopArr);
    return hopArr;
  }

  public String getRequester()
  {
    return requester;
  }
}
