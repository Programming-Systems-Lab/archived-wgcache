/******************************************************************
*** File ConfigDialog.java 
***
***/

import java.awt.*;
import java.io.*;
import java.net.*;
import java.applet.*;

// 
// Class:     ConfigDialog
// Abstract:  Configuration dialog box.
//

public class ConfigDialog extends Dialog 
{
    //
    // Member variables
    //
    private DataInputStream in;
    private DataOutputStream out;
    private Socket proxySocket;
	private Config config;
	private Applet applet;


    //
    // Member methods
    //
    
	//
	// Constructor
	//
	public ConfigDialog(Frame parent, String title, Config configuration,
	                    Socket proxySocket, Applet applet) 
	{
	    this(parent, configuration, proxySocket, applet);
	    setTitle(title);
	}

    //
    // Constructor
    //
	public ConfigDialog(Frame parent, Config configuration,
						Socket proxySckt, Applet myApplet)
	{
	    super(parent, true);

        applet = myApplet;
		config = configuration;
		proxySocket = proxySckt;

		//{{INIT_CONTROLS
		setLayout(null);
		addNotify();
		resize(insets().left + insets().right + 612,insets().top + insets().bottom + 505);
		setForeground(new Color(0));
		setBackground(new Color(16756655));
		textFatherProxyHost = new java.awt.TextField();
		textFatherProxyHost.reshape(insets().left + 84,insets().top + 72,164,30);
		add(textFatherProxyHost);
		textFatherProxyPort = new java.awt.TextField();
		textFatherProxyPort.reshape(insets().left + 84,insets().top + 108,76,30);
		add(textFatherProxyPort);
		label4 = new java.awt.Label("Port:");
		label4.reshape(insets().left + 12,insets().top + 108,60,22);
		add(label4);
		label3 = new java.awt.Label("Host:");
		label3.reshape(insets().left + 12,insets().top + 72,60,18);
		add(label3);
		checkboxFatherProxy = new java.awt.Checkbox("Use Father proxy");
		checkboxFatherProxy.reshape(insets().left + 12,insets().top + 36,148,24);
		add(checkboxFatherProxy);
		listDenied = new java.awt.List(0,false);
		add(listDenied);
		listDenied.reshape(insets().left + 12,insets().top + 204,162,90);
		listDenied.setFont(new Font("Dialog", Font.BOLD, 12));
		listDenied.setBackground(new Color(8421631));
		textAddDeny = new java.awt.TextField();
		textAddDeny.reshape(insets().left + 12,insets().top + 168,160,30);
		add(textAddDeny);
		label5 = new java.awt.Label("Deny access to:");
		label5.reshape(insets().left + 12,insets().top + 144,132,18);
		add(label5);
		label6 = new java.awt.Label("Traffic Management:");
		label6.reshape(insets().left + 12,insets().top + 0,192,26);
		label6.setFont(new Font("Dialog", Font.BOLD, 16));
		label6.setForeground(new Color(0));
		label6.setBackground(new Color(16756655));
		add(label6);
		checkboxCache = new java.awt.Checkbox("Use Caching");
		checkboxCache.reshape(insets().left + 312,insets().top + 36,114,24);
		add(checkboxCache);
		textCacheSize = new java.awt.TextField();
		textCacheSize.reshape(insets().left + 432,insets().top + 72,112,30);
		add(textCacheSize);
		labelCacheSize = new java.awt.Label("Cache Size:");
		labelCacheSize.reshape(insets().left + 312,insets().top + 72,108,16);
		add(labelCacheSize);
		listMasks = new java.awt.List(0,false);
		add(listMasks);
		listMasks.reshape(insets().left + 312,insets().top + 204,164,90);
		listMasks.setFont(new Font("Dialog", Font.BOLD, 12));
		listMasks.setBackground(new Color(8421631));
		textAddMask = new java.awt.TextField();
		textAddMask.reshape(insets().left + 312,insets().top + 168,160,30);
		add(textAddMask);
		buttonAddMask = new java.awt.Button("Add");
		buttonAddMask.reshape(insets().left + 504,insets().top + 168,82,24);
		add(buttonAddMask);
		buttonClearMasks = new java.awt.Button("Clear");
		buttonClearMasks.reshape(insets().left + 504,insets().top + 240,82,24);
		add(buttonClearMasks);
		buttonRemoveMask = new java.awt.Button("Remove");
		buttonRemoveMask.reshape(insets().left + 504,insets().top + 204,82,24);
		add(buttonRemoveMask);
		label2 = new java.awt.Label("Cache Management:");
		label2.reshape(insets().left + 312,insets().top + 0,192,26);
		label2.setFont(new Font("Dialog", Font.BOLD, 16));
		add(label2);
		label7 = new java.awt.Label("Do not cache...");
		label7.reshape(insets().left + 312,insets().top + 144,132,22);
		add(label7);
		buttonRestore = new java.awt.Button("Restore");
		buttonRestore.reshape(insets().left + 336,insets().top + 420,72,24);
		add(buttonRestore);
		buttonSave = new java.awt.Button("Save");
		buttonSave.reshape(insets().left + 204,insets().top + 420,72,24);
		add(buttonSave);
		buttonExit = new java.awt.Button("Exit");
		buttonExit.reshape(insets().left + 72,insets().top + 420,72,24);
		add(buttonExit);
		checkboxCleanCache = new java.awt.Checkbox("Clean cache on saving");
		checkboxCleanCache.reshape(insets().left + 312,insets().top + 108,192,24);
		add(checkboxCleanCache);
		label10 = new java.awt.Label("Proxy status:");
		label10.reshape(insets().left + 324,insets().top + 300,144,26);
		label10.setFont(new Font("Dialog", Font.BOLD, 16));
		add(label10);
		label11 = new java.awt.Label("Files cached:");
		label11.reshape(insets().left + 276,insets().top + 324,108,22);
		add(label11);
		label12 = new java.awt.Label("Bytes cached:");
		label12.reshape(insets().left + 276,insets().top+348,108,22);
		add(label12);
		labelBytesFree = new java.awt.Label("");
		labelBytesFree.reshape(insets().left + 384,insets().top+372,72,32);
		add(labelBytesFree);
		labelBytesCached = new java.awt.Label("");
		labelBytesCached.reshape(insets().left +384,insets().top+348,72,32);
		add(labelBytesCached);
		labelFilesCached = new java.awt.Label("");
		labelFilesCached.reshape(insets().left + 384,insets().top + 324,48,22);
		add(labelFilesCached);
		label16 = new java.awt.Label("Bytes free:");
		label16.reshape(insets().left + 276,insets().top + 372,108,24);
		add(label16);
		label17 = new java.awt.Label("Hits:");
		label17.reshape(insets().left + 468,insets().top + 324,72,22);
		add(label17);
		label18 = new java.awt.Label("Misses:");
		label18.reshape(insets().left + 468,insets().top + 348,72,22);
		add(label18);
		labelHitRate = new java.awt.Label("");
		labelHitRate.reshape(insets().left + 552,insets().top + 372,48,22);
		add(labelHitRate);
		labelMisses = new java.awt.Label("");
		labelMisses.reshape(insets().left + 552,insets().top + 348,48,22);
		add(labelMisses);
		labelHits = new java.awt.Label("");
		labelHits.reshape(insets().left + 552,insets().top + 324,48,22);
		add(labelHits);
		label22 = new java.awt.Label("Hit rate:");
		label22.reshape(insets().left + 468,insets().top + 372,72,24);
		add(label22);
		buttonAddDeny = new java.awt.Button("Add");
		buttonAddDeny.reshape(insets().left + 192,insets().top + 168,82,24);
		add(buttonAddDeny);
		buttonClearDenied = new java.awt.Button("Clear");
		buttonClearDenied.reshape(insets().left + 192,insets().top + 240,82,24);
		add(buttonClearDenied);
		buttonRemoveDeny = new java.awt.Button("Remove");
		buttonRemoveDeny.reshape(insets().left + 192,insets().top + 204,82,24);
		add(buttonRemoveDeny);
		textNewPassword = new java.awt.TextField();
		textNewPassword.setEchoCharacter('*');
		textNewPassword.reshape(insets().left + 130,insets().top + 336,110,30);
		add(textNewPassword);
		textConfirmPassword = new java.awt.TextField();
		textConfirmPassword.setEchoCharacter('*');
		textConfirmPassword.reshape(insets().left + 130,insets().top + 372,110,30);
		add(textConfirmPassword);
		label1 = new java.awt.Label("new password:");
		label1.reshape(insets().left + 12,insets().top + 336,112,24);
		add(label1);
		label8 = new java.awt.Label("Confirm password:");
		label8.reshape(insets().left + 12,insets().top + 372,117,24);
		add(label8);
		label9 = new java.awt.Label("Change password:");
		label9.reshape(insets().left + 12,insets().top + 300,180,26);
		label9.setFont(new Font("Dialog", Font.BOLD, 16));
		add(label9);
		buttonReset = new java.awt.Button("Reset");
		buttonReset.reshape(insets().left + 480,insets().top + 420,72,24);
		add(buttonReset);
		labelStatus = new java.awt.Label("Idle");
		labelStatus.reshape(insets().left + 84,insets().top + 456,504,19);
		add(labelStatus);
		label14 = new java.awt.Label("Status:");
		label14.reshape(insets().left + 12,insets().top + 456,72,19);
		add(label14);
		setTitle("Proxy Configuration - "+config.getProxyMachineNameAndPort());
		//}}

		//
		// Get the input/output streams of proxy socket
		//
		try
		{
            in = new DataInputStream(proxySocket.getInputStream());
            out = new DataOutputStream(proxySocket.getOutputStream());
        }
        catch (IOException e)
        {
            labelStatus.setText("Fatal error: could not connect to proxy");
        }

        //
        // Update the controls of the dialog
        //
		setControls();
	}
	

	//
    // Report error to user
	//
    void reportError(String error)
    {
        labelStatus.setText(error);
    }
    

	//
    // Check if string valid
	//
    private boolean isValidAlpha(String s)
    {
        if ( (s.indexOf(" ") == -1) &&
             (s.indexOf("?") == -1))
        {
            return true;
        }
        
        return false;
    }
    
	//
    // Check if number valid
	//
    private boolean isValidNumber(String s,long max)
    {
        long number;

        try
        {
            number = Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        
        return (number <= max);
    }
    
	//
    // Check that all the controls contains valid data
	//
    private boolean checkValidData()
    {
        if (checkboxFatherProxy.getState())
        {
            if (!isValidAlpha(textFatherProxyHost.getText()))
            {
                reportError("Invalid father proxy host name");
                return false;
            }
            if (!isValidNumber(textFatherProxyPort.getText(),65536))
            {
                reportError("Invalid father proxy port");
                return false;
            }
        }

        if (!isValidAlpha(textNewPassword.getText()))
        {
            reportError("Invalid new password");
            return false;
        }
        
        if (!textNewPassword.getText().equals(textConfirmPassword.getText()))
        {
            reportError("new password and confirm password do not match");
            return false;
        }
        
        if (checkboxCache.getState())
        {
            if (!isValidNumber(textCacheSize.getText(),100000000))
            {
                reportError("Invalid cache size");
                return false;
            }
        }
        
        return true;
    }   
	

	// Reset bottun was clicked
	void buttonReset_Clicked(Event event) 
	{
		config.reset();
		setControls();
		textNewPassword.setText("");
		textConfirmPassword.setText("");
		labelStatus.setText("Returned to default configuration");
	}

    // Restore bottun was clicked
	void buttonRestore_Clicked(Event event) 
	{
		setControls();
		labelStatus.setText("Configuration restored");
	}

    // Add string to Denied list box
	void textAddDeny_EnterHit(Event event) 
	{
        buttonAddDeny_Clicked(event);
	}

    // Add string to Cache Mask list box
	void textAddMask_EnterHit(Event event) 
	{
        buttonAddMask_Clicked(event);
}

    // Clear Cache Mask list box
	void buttonClearMasks_Clicked(Event event) 
	{
		//{{CONNECTION
		// Clear the List
		listMasks.clear();
		//}}
	}

    // Remove an item from the Cache Mask list box
	void buttonRemoveMask_Clicked(Event event) 
	{
	    int index = listMasks.getSelectedIndex();
	    if (index != -1)
	        listMasks.delItem(index);
	}

    // Add string to Cache Mask list box
	void buttonAddMask_Clicked(Event event) 
	{
	    if (textAddMask.getText().length() == 0)
	        return;
	        
        if(!isValidAlpha(textAddMask.getText()))
        {
            reportError("Invalid cache mask");
            return;
        }

		//{{CONNECTION
		// Add a string to the List...
		listMasks.addItem(textAddMask.getText());
		//}}
	}

    // Enable father proxy host + port edit boxes
	void checkboxFatherProxy_Action(Event event) 
	{
        textFatherProxyHost.enable(checkboxFatherProxy.getState());
        textFatherProxyPort.enable(checkboxFatherProxy.getState());
	}

    // Enable cache size edit box
	void checkboxCache_Action(Event event) 
	{
		textCacheSize.enable(checkboxCache.getState());
	}

    // Clear button on denied hosts list box was clicked
	void buttonClear_Clicked(Event event) 
	{
		//{{CONNECTION
		// Clear the List
		listDenied.clear();
		//}}
	}

    // Remove button on denied hosts list box was clicked
	void buttonRemove_Clicked(Event event) 
	{
	    int index = listDenied.getSelectedIndex();
	    if (index != -1)
	        listDenied.delItem(index);
	}

    // Add button on denied hosts list box was clicked
	void buttonAddDeny_Clicked(Event event) 
	{
	    if (textAddDeny.getText().length() == 0)
	        return;

        if(!isValidAlpha(textAddDeny.getText()))
        {
            reportError("Invalid host");
            return;
        }

		//{{CONNECTION
		// Add a string to the List... Get the contents of the TextField
		listDenied.addItem(textAddDeny.getText());
		//}}
	}

    // Exit button was clicked
	void buttonExit_Clicked(Event event)
	{	    
		//{{CONNECTION
		// Hide the Dialog
		hide();
		//}}
	    applet.stop();
	    applet.start();
	}

    // Save button was clicked
	void buttonSave_Clicked(Event event) 
	{
        if (!checkValidData())
        {
            return;
        }
        
        try
        {
            updateConfiguration();

            // Send local configuration to proxy
            labelStatus.setText("Sending information to proxy...");
            out.writeBytes(config.toString());
            
            // And get back the parameters from proxy
            config.parse(in.readLine());
            setControls();
            labelStatus.setText("Configuration saved");
        }
        catch (IOException e)
        {
            labelStatus.setText("Lost connection to proxy.");
        }
    }
            
	void radioButtonCaching_Action(Event event) 
	{
	    textCacheSize.enable();
	}

	void radioButtonNoCaching_Action(Event event) 
	{
	    textCacheSize.disable();
	}

	//
	// Set the UI controls according to config object parameters
	//
	private void setControls()
	{
        String items[];
        int i;

        checkboxFatherProxy.setState(config.getIsFatherProxy());
        textFatherProxyHost.setText(config.getFatherProxyHost());
        textFatherProxyPort.setText(new Integer(config.getFatherProxyPort()).toString());
        textFatherProxyHost.enable(config.getIsFatherProxy());
        textFatherProxyPort.enable(config.getIsFatherProxy());

        textAddDeny.setText("");
        listDenied.clear();
        items = config.getDeniedHosts();
        for (i=0; i<items.length; i++)
            listDenied.addItem(items[i]);
            
        checkboxCache.setState(config.getIsCaching());
		checkboxCleanCache.setState(config.getCleanCache());
	    textCacheSize.enable(config.getIsCaching());
	    textCacheSize.setText(new Long(config.getCacheSize()).toString());
 
        textAddMask.setText("");
        listMasks.clear();
        items = config.getCacheMasks();
        for (i=0; i<items.length; i++)
            listMasks.addItem(items[i]);
	    
	    labelFilesCached.setText(new Long(config.getFilesCached()).toString());
	    labelBytesCached.setText(new Long(config.getBytesCached()).toString());
	    labelBytesFree.setText(new Long(config.getBytesFree()).toString());
	    labelHits.setText(new Long(config.getHits()).toString());
	    labelMisses.setText(new Long(config.getMisses()).toString());
	    labelHitRate.setText(new Double(config.getHitRatio()).toString() + "%");
	    
	}

	//
	// Set the config object parameters according to UI controls
	//
	private void updateConfiguration()
	{
        int i;
        String items[];
        
        // Update father proxy
        config.setIsFatherProxy(checkboxFatherProxy.getState());
        config.setFatherProxyHost(textFatherProxyHost.getText());
        config.setFatherProxyPort(Integer.parseInt(textFatherProxyPort.getText()));
        
        // Update denied list
        items = new String[listDenied.countItems()];
        for (i=0; i<listDenied.countItems(); i++)
           items[i] = listDenied.getItem(i);
        config.setDeniedHosts(items);
           
        if (textNewPassword.getText().length() != 0)
            config.setPassword(textNewPassword.getText());
        
    	config.setIsCaching(checkboxCache.getState());
    	config.setCacheSize(Long.parseLong(textCacheSize.getText()));
        config.setCleanCache(checkboxCleanCache.getState());
        
        items = new String[listMasks.countItems()];
        for (i=0; i<listMasks.countItems(); i++)
           items[i] = listMasks.getItem(i);
        config.setCacheMasks(items);
    }

	//
	// Show the dialog
	//
    public synchronized void show() 
    {
    	Rectangle bounds = getParent().bounds();
    	Rectangle abounds = bounds();

    	move(bounds.x + (bounds.width - abounds.width)/ 2,
    	     bounds.y + (bounds.height - abounds.height)/2);

    	super.show();
    }

	//
	// Events handler
	//
	public boolean handleEvent(Event event)
	{
	    if(event.id == Event.WINDOW_DESTROY)
	    {
	        hide();
	        applet.stop();
	        applet.start();
	        return true;
	    }
		if (event.target == buttonSave && event.id == Event.ACTION_EVENT)
		{
			buttonSave_Clicked(event);
			return true;
		}
		if (event.target == buttonRemoveDeny && event.id == Event.ACTION_EVENT)
		{
			buttonRemove_Clicked(event);
			return true;
		}
		if (event.target == buttonClearDenied && event.id == Event.ACTION_EVENT)
		{
			buttonClear_Clicked(event);
			return true;
		}
		if (event.target == checkboxCache && event.id == Event.ACTION_EVENT)
		{
			checkboxCache_Action(event);
			return true;
		}
		if (event.target == checkboxFatherProxy && event.id == Event.ACTION_EVENT)
		{
			checkboxFatherProxy_Action(event);
			return true;
		}
		if (event.target == buttonAddMask && event.id == Event.ACTION_EVENT)
		{
			buttonAddMask_Clicked(event);
			return true;
		}
		if (event.target == buttonAddDeny && event.id == Event.ACTION_EVENT)
		{
			buttonAddDeny_Clicked(event);
			return true;
		}
		if (event.target == buttonRemoveMask && event.id == Event.ACTION_EVENT)
		{
			buttonRemoveMask_Clicked(event);
			return true;
		}
		if (event.target == buttonClearMasks && event.id == Event.ACTION_EVENT)
		{
			buttonClearMasks_Clicked(event);
			return true;
		}
		if (event.target == textAddMask && event.id == Event.ACTION_EVENT)
		{
			textAddMask_EnterHit(event);
			return true;
		}
		if (event.target == textAddDeny && event.id == Event.ACTION_EVENT)
		{
			textAddDeny_EnterHit(event);
			return true;
		}
		if (event.target == buttonReset && event.id == Event.ACTION_EVENT)
		{
			buttonReset_Clicked(event);
			return true;
		}
		if (event.target == buttonExit && event.id == Event.ACTION_EVENT)
		{
			buttonExit_Clicked(event);
			return true;
		}
		if (event.target == buttonRestore && event.id == Event.ACTION_EVENT)
		{
			buttonRestore_Clicked(event);
			return true;
		}
		return super.handleEvent(event);
	}

	//{{DECLARE_CONTROLS
	java.awt.TextField textFatherProxyHost;
	java.awt.TextField textFatherProxyPort;
	java.awt.Label label4;
	java.awt.Label label3;
	java.awt.Checkbox checkboxFatherProxy;
	java.awt.List listDenied;
	java.awt.TextField textAddDeny;
	java.awt.Label label5;
	java.awt.Label label6;
	java.awt.Checkbox checkboxCache;
	java.awt.TextField textCacheSize;
	java.awt.Label labelCacheSize;
	java.awt.List listMasks;
	java.awt.TextField textAddMask;
	java.awt.Button buttonAddMask;
	java.awt.Button buttonClearMasks;
	java.awt.Button buttonRemoveMask;
	java.awt.Label label2;
	java.awt.Label label7;
	java.awt.Button buttonRestore;
	java.awt.Button buttonSave;
	java.awt.Button buttonExit;
	java.awt.Checkbox checkboxCleanCache;
	java.awt.Label label10;
	java.awt.Label label11;
	java.awt.Label label12;
	java.awt.Label labelBytesFree;
	java.awt.Label labelBytesCached;
	java.awt.Label labelFilesCached;
	java.awt.Label label16;
	java.awt.Label label17;
	java.awt.Label label18;
	java.awt.Label labelHitRate;
	java.awt.Label labelMisses;
	java.awt.Label labelHits;
	java.awt.Label label22;
	java.awt.Button buttonAddDeny;
	java.awt.Button buttonClearDenied;
	java.awt.Button buttonRemoveDeny;
	java.awt.TextField textNewPassword;
	java.awt.TextField textConfirmPassword;
	java.awt.Label label1;
	java.awt.Label label8;
	java.awt.Label label9;
	java.awt.Button buttonReset;
	java.awt.Label labelStatus;
	java.awt.Label label14;
	//}}
}
