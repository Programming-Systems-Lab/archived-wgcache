import java.net.*;
import java.io.*;

public class SocketTest extends Thread {
  private String url;
  private int port;
  private ServerSocket proxySock;

		try {
			this.url = InetAddress.getLocalHost().getHostName();
			this.port = 8080;
			proxySock = new ServerSocket(port);
			}catch(Exception e) {
			e.printStackTrace();
		}
  public void run() {		
		while(true) {
				Socket client = proxySock.accept();
				System.out.println("Accepted from: " + client.getInetAddress().getHostName());
				DataInputStream in = new DataInputStream(client.getInputStream());
				String data = in.readLine();
					System.out.println("ERROR EMPTY STRING");
					System.exit(0);
				}
				//PARSE THE DATA TO GET THE REQUESTURL
				System.out.println("The request URL is: " + requestUrl);
				URLConnection conec = url.openConnection();
				String recContent = inStream.readLine();
					os.writeBytes(recContent);
					recContent = inStream.readLine();
				}
				e.printStackTrace();
			}		
		}
  public static void main(String [] args) {
    SocketTest soc = new SocketTest();
    soc.start();
    System.out.println("DONE");
  }
}