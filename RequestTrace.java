package psl.wgcache;

import java.util.Vector;

/*
 * A RequestTrace instance is passed with a push or pull request to a
 * module, to allow that module to tell from whence the request came.
 */
public class RequestTrace implements java.io.Serializable {
  private Vector hops;
  private String requester = null;

  public RequestTrace() {
    hops = null;
  }
  public RequestTrace(String requester) {
    hops = null;
    this.requester = requester;
  }
  public void addHop(String name) {
    if(hops == null)
      hops = new Vector();
    hops.addElement(name);
  }
  public String getLastHop() {
    if(hops != null) {
      // System.out.println("JUST CHECKING :" + hops);
      return (String)hops.lastElement();
    } else return null;
  }
  public String[] getHops(){
    String[] hopArr = new String[hops.size()];
    hops.copyInto(hopArr);
    return hopArr;
  }

  public String getRequester() {
    return requester;
  }
}
