import java.net.*;

public class ClientTest {
  private String url;
  private int port; 
  private Socket servSoc;

  public ClientTest() {
    try {
      this.url = InetAddress.getLocalHost().getHostName();
      this.port = 8080;
      servSoc = new Socket(url,port);
    } catch(Exception e) {
        e.printStackTrace();
     }
  }
 public static void main(String [] args) {
  ClientTest cl = new ClientTest();
  System.out.println("DONE CLIENT");
}
}

