import java.net.*;import java.io.*;

public class QuickTest {	public QuickTest(String _desUrl) {
	//public static void main(String[] args) {
		String data = null;
		String des = "http://www.cs.columbia.edu";		try {			if(_desUrl != null) 				des = _desUrl;
			URL url = new URL(des);
			URLConnection conec = url.openConnection();			DataInputStream in = new DataInputStream(conec.getInputStream());
			data = in.readLine();			while(data != null) {
				data = in.readLine();				System.out.println("The content is: " + data);				//System.out.println("The content is: " + conec.getContent().toString());			}
  }catch(Exception e) {
     e.printStackTrace();
  }
}
}
