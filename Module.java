package psl.wgcache;

public interface Module {
  public String getName();  
  public Cacheable pullFrom(Object name);

  // public void pushTo(RequestTrace trace, Cacheable x);
  public Criteria getCriteria();
  public void setCriteria(Criteria crit);
}
