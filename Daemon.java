package psl.wgcache;

/******************************************************************
*** File Daemon.java 
***
***/

import java.net.*;
import java.io.*;


// 
// Class:     Daemon
// Abstract:  Web daemon thread. creates main socket on port 8080
//            and listens on it forever. For each client request,
//            creates proxy thread to handle the request.
//
public class Daemon extends Thread  {
  //
  // Member variables
  //
  static ServerSocket MainSocket = null;
  static Cache cache = null;
  static Config config;  
  static String adminPath;
  public static PersonalCacheModuleImpl pcm;
  static String args[];
  int daemonPort = 0;
  int tempDaemonPort =0;
  
  final static int defaultDaemonPort = 8080;
  final static int maxDaemonPort = 65536;
  	
  public Daemon (PersonalCacheModuleImpl _p) {
    pcm = _p;
  }

  public void run() {
    try	{
      // Create the Cache Manager and Configuration objects
      System.out.println("Initializing...");

      // Create main socket
      daemonPort = defaultDaemonPort;
      while (daemonPort < 65536) {
        try {
          MainSocket = new ServerSocket(daemonPort);
          System.out.print("Creating Daemon Socket...");
          System.out.println(" port " + daemonPort + " OK");
          System.out.println("Proxy [daemon port] : " + daemonPort);
          break;
        } catch (IOException e) {
          daemonPort++;
          // System.out.println("Error opening daemon socket");
        }
      }
      System.out.print("Creating Config Object...");
      config = new Config();
      config.setIsAppletContext(false);
      config.setLocalHost(InetAddress.getLocalHost().getHostName());
      String tmp = InetAddress.getLocalHost().toString();
      config.setLocalIP(tmp.substring(tmp.indexOf('/')+1));
      config.setProxyMachineNameAndPort(InetAddress.getLocalHost().getHostName()+":"+daemonPort);
      File adminDir = new File("Applet");
      config.setAdminPath(adminDir.getAbsolutePath());
      System.out.println("OK");

      System.out.print("Creating Cache Manager...");
      cache = new Cache(config);
      System.out.println("OK");

      // Start the admin thread
      System.out.print("Creating Admin Thread...");
      Admin adminThd = new Admin(config,cache); 
      adminThd.start();
      System.out.println(" port " + config.getAdminPort() + " OK");
        			
      if (config.getIsFatherProxy()) { 
        System.out.println("Using Father Proxy "+ config.getFatherProxyHost()+ ":"+config.getFatherProxyPort()+" .");
      } else {
        System.out.println("Not Using Father Proxy .");
      }
        
      // Main loop
      System.out.println("Proxy up and running!");
      while (true) {
        System.out.println("Listen on main socket");
        Socket ClientSocket = MainSocket.accept();
        System.out.println("got a connection ... ");
        // Pass request to new proxy thread
        Proxy thd = new Proxy(ClientSocket,cache,config);
        thd.start();
      }
    } catch (Exception e)	{
      e.printStackTrace();
    } finally {
      try	{
        MainSocket.close();
      } catch (Exception exc)	{
        exc.printStackTrace();
      }
    }
  }
}

