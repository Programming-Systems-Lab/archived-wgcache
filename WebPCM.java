package psl.wgcache;

public class WebPCM {
  Daemon d = new Daemon(null);
  
  public WebPCM() {
    d.start();
  }

  public static void main(String args[]) {
    WebPCM w = new WebPCM();  
    System.out.println("DONE");
  }
}
