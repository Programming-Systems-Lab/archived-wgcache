package psl.wgcache;

public class CriteriaInfoImpl  implements CriteriaInfo,java.io.Serializable {
  protected Module receiver = null;
  protected String receivedFromMod = null;
  protected Cacheable cacheable = null;
  protected RequestTrace trace = null;
  protected int receivedVia = VIA_UNKNOWN;
  protected History hist = null;
	protected Module toBePushedModule;

  public CriteriaInfoImpl(Module receiver, Cacheable cacheable, 
			  RequestTrace trace, int receivedVia, History hist)
  {
    this.receiver = receiver;
    this.cacheable = cacheable;
    this.trace = trace;
    this.receivedVia = receivedVia;
    this.hist = hist;

    receivedFromMod = trace.getLastHop();
    this.trace.addHop(receiver.getName());
  }
  public Cacheable getCacheable()  {
    return cacheable;
  }

  public int getReceivedVia()  {
    return receivedVia;
  }

  public History getHistory()  {
    return hist;
  }

  public Module getReceivedBy()  {
    return receiver;
  }

  // returns null if pulled from outside source
  public String getReceivedFrom()  {
    return receivedFromMod;
  }

  public String getRequester()  {
    return trace.getRequester();
  }

  public RequestTrace getRequestTrace()  {
    return trace;
  }

  public Module[] getMembers()  {
    Module[] retval = null;
    /*
    if(receiver instanceof WorkgroupImpl) {
      retval = ((WorkgroupImpl)receiver).members();
    } else {
      retval = new Module[0];
    }
    */
    return retval;
  }

	public Module getToBePushed() {
		return toBePushedModule;
	}


  public Module[] getWorkgroups()  {
    Module[] retval = null;
    /*
    if(receiver instanceof PersonalCacheModuleImpl) {
      retval = ((PersonalCacheModuleImpl)receiver).workgroups();
    } else {
      retval = new Module[0];
    }
    */
    return retval;
  }

  public void push(Cacheable x, Module m)  {
    // Don't push it back to where it came from
		log("In the push while applying criteria with module name :" + m.getName() + "and the recieved from module :" + receiver);
		// if(!m.getName().equals(receiver)) m.pushTo(trace, x);
  }

  public Cacheable pull(String name, Module m)    throws WGCException  {
    Cacheable result = null;
    // result = m.pullFrom(trace, name);
    return result;
  }
	private void log(String mesg){
		System.out.println("CriteriaInfoImpl :"+ mesg);
	}
}
