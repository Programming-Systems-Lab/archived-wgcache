package psl.wgcache;

public class NoopCriteria  implements Criteria, Serializable {
  public void apply(CriteriaInfo info) {
    // testing!!!    
    // info.push(info.getCacheable(),info.getReceivedBy());
    System.out.println("CRITERIA APPLIED done");
  }
  public String toString() {
    return "This criteria does nothing.\n";
  }
}
