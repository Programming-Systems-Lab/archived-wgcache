/******************************************************************
*** File Admin.java
***
***/

import java.awt.*;
import java.applet.*;
import java.net.*;
import java.io.*;

//
// Class:     Admin
// Abstract:  Applet main file.
//

public class Admin extends Applet
{
	//
	// Member variables
	//
    private Socket proxySocket = null;     // Socket to proxy
    private DataOutputStream out = null;
    private DataInputStream in = null;
    String configString = null;            // Configurable parameters
    Config config = null;

	//
        // Enter key was pressed
	//
	void Password_EnterHit(Event event)
	{
		// Submit the password
		try
		{
		    // Send password to proxy
		    out.writeBytes(textPassword.getText() + '\n');
		    out.flush();

                   // Get response from proxy
		    if (!in.readLine().equals("ACCEPT"))
		    {
		        //
		        // Response was Nack, meaning that password is wrong.
		        // Show a message box to reflect that.
		        //

        		//{{CONNECTION
        		// Create with title, show as modal...
        		{
        			Container theFrame = this;
        			do {
        				theFrame = theFrame.getParent();
        			} while ((theFrame != null) && !(theFrame instanceof Frame));
        			if (theFrame == null)
        				theFrame = new Frame();
        			(new PasswordDialog((Frame)theFrame, "Wrong Password")).show();
        		}
        		//}}
		    }
		    else
		    {
		        //
		        // Response was Ack. Hide the password controls (security...)
		        //
		        textPassword.setText("");

                    //
                    // Get parameters values from proxy
                    //
                    config = new Config();
                    config.setIsAppletContext(true);
                    config.parse(in.readLine());

                    //
                    // Display Config Dialog
                    //
    			Container theFrame = this;
    			do
    			{
    				theFrame = theFrame.getParent();
    			}
    			while ((theFrame != null) && !(theFrame instanceof Frame));
    			if (theFrame == null)
    				theFrame = new Frame();
        		(new ConfigDialog((Frame)theFrame,config,proxySocket,this)).show();
    		    }
		}
		catch (Exception e)
		{}
		finally
		{}
	}

	//
        // Initialize applet
	//
	public void init()
	{

		super.init();

		//{{INIT_CONTROLS
		setLayout(null);
		addNotify();
		resize(323,38);
		setForeground(new Color(0));
		setBackground(new Color(0));
		textPassword = new java.awt.TextField();
		textPassword.setEchoCharacter('*');
		textPassword.reshape(0,0,323,38);
		textPassword.setBackground(new Color(16777215));
		add(textPassword);
		//}}
	}

	//
    // Start the applet
	//
    public void start() {      try  {
			super.start();      // Create socket to talk to proxy      proxySocket = new Socket(getCodeBase().getHost(), Integer.parseInt(getParameter("adminPort")));            out = new DataOutputStream(proxySocket.getOutputStream());      in = new DataInputStream(proxySocket.getInputStream());      } catch (IOException e){}    }
	//
	// Stop the applet
	//
	public void stop()
	{
		try
		{
			super.stop();

			// Close socket to proxy
			proxySocket.close();
		}
		catch (IOException e)
		{}
	}

	//
    // Events handler
	//
	public boolean handleEvent(Event event)
	{
		if (event.target == textPassword && event.id == Event.ACTION_EVENT) {
			Password_EnterHit(event);
			return true;
		}
		return super.handleEvent(event);
	}

	//{{DECLARE_CONTROLS
	java.awt.TextField textPassword;
	//}}
}
