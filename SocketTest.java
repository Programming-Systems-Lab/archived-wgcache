import java.net.*;
import java.io.*;

public class SocketTest extends Thread {
  private String url;
  private int port;
  private ServerSocket proxySock;
	public SocketTest() {
		try {
			this.url = InetAddress.getLocalHost().getHostName();
			this.port = 8080;
			proxySock = new ServerSocket(port);
			}catch(Exception e) {
			e.printStackTrace();
		}	
  } 
  public void run() {		
		while(true) {
			try {
				Socket client = proxySock.accept();
				System.out.println("Accepted from: " + client.getInetAddress().getHostName());
				DataInputStream in = new DataInputStream(client.getInputStream());
				String data = in.readLine();
				if(data ==null) {
					System.out.println("ERROR EMPTY STRING");
					System.exit(0);
				}
				String requestUrl = data.substring(data.indexOf("http://"),data.indexOf("HTTP"));
				System.out.println("The request URL is: " + requestUrl);
				URL url = new URL(requestUrl);
				URLConnection conec = url.openConnection();
				DataInputStream inStream = new DataInputStream(conec.getInputStream());
				DataOutputStream os = new DataOutputStream(client.getOutputStream());
				String recContent = inStream.readLine();
				while (recContent != null) {
					os.writeBytes(recContent);
					recContent = inStream.readLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}	
  } 	
  public static void main(String [] args) {
    SocketTest soc = new SocketTest();
    soc.start();
    System.out.println("DONE");
  }
}
