package psl.wgcache;

public class WebPCM {
  Daemon d = new Daemon();;
  
  public WebPCM() {
    d.main(null);
  }

  public static void main(String args[]) {
    WebPCM w = new WebPCM();  
    System.out.println("DONE");
  }
}
