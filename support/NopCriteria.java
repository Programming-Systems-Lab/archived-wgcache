package psl.wgcache.support;

public class NopCriteria  implements Criteria {
  public void apply(CriteriaInfo info) {
    // testing!!!    System.out.println("CRITERIA APPLIED");
  }
  public String toString() {
    return "This criteria does nothing.\n";
  }
}
