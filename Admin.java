package psl.wgcache;

/******************************************************************
*** File Admin.java 
***
***/

import java.net.*;
import java.io.*;

// 
// Class:     Admin
// Abstract:  The admin thread listens on admin socket and handle all
//            communications with the remote administrator.
//

public class Admin extends Thread {
  //
  // Member variables
  //
  ServerSocket adminSocket = null;
  Socket appletSocket = null;
  String passwordCandidate = null;
  DataInputStream in = null;
  DataOutputStream out = null;
  Config config = null;
  Cache cache;
  final int defaultAdminPort = 6666;
  int adminPort = 0;
  
  //
  // Public methods
  //

  //
  // Constructor
  //
  Admin(Config configObject, Cache cacheManager) {
    config = configObject;
    cache = cacheManager;
    adminPort = defaultAdminPort;
    while (adminPort < 65536) {
      try {
        adminSocket = new ServerSocket(adminPort);
        config.setAdminPort(adminSocket.getLocalPort());
        System.out.println("Admin [daemon port] : " + adminPort);
        break;
      } catch (IOException e) {
        adminPort++;
        // System.out.println("Error opening admin socket");
      }
    }
  }
  //
  // Handle communications with remote administrator
  //
  public void run() {
    while(true) {
      try {
        appletSocket = adminSocket.accept();
System.out.println("connection accepted");
        in = new DataInputStream(appletSocket.getInputStream());
        out = new DataOutputStream(appletSocket.getOutputStream());
        
        do  {
          // Read password candidate sent by applet
          String passwordCandidate = in.readLine();
System.out.println("password received");
          // Send applet ack/nack on password
          if (config.getPassword().equals(passwordCandidate)) {
            out.writeBytes("ACCEPT\n");
System.out.println("password ACCEPT");
            break;
          } else {
            out.writeBytes("REJECT\n");
System.out.println("password REJECT");
          }
          out.flush();
        } while (true);
        //
        // Password is OK, so let's send the administrator the
        // parameters values and read his new values
        //
        while (true)  {
          out.writeBytes(config.toString());
          out.flush();

          config.parse(in.readLine());
          System.out.println("Configuration changed by administrator.");

          // Administrator wants to clean the cache 
          if (config.getCleanCache())  {
            cache.clean();
            config.setCleanCache(false); //no need to clean again
          }
        }
      } catch (Exception e) {
        //
        // This line was reached because the administrator closed
        // the connection with the proxy. That's fine, we are now
        // available for another administrator to log in.
        //
        e.printStackTrace();
        System.out.println("Connection with administrator closed.");
      } finally {
        try  {
          out.close();
          in.close();
        } catch(Exception exc) { }
      }
    } //while
  } 
}

