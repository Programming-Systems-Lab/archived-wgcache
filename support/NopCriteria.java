package psl.wgcache.support;

public class NopCriteria  implements Criteria {
  public void apply(CriteriaInfo info) {
    // testing!!!    		info.push(info.getCacheable(),info.getReceivedBy());		System.out.println("CRITERIA APPLIED done");
  }
  public String toString() {
    return "This criteria does nothing.\n";
  }
}
