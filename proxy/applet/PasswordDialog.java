/******************************************************************
*** File PasswordDialog.java 
***
***/

import java.awt.*;

// 
// Class:     PasswordDialog
// Abstract:  Dialog box with message "wrong password".
//

public class PasswordDialog extends Dialog 
{
	//
	// Member methods
	//

	//
    // Constructor
	//
	public PasswordDialog(Frame parent, String title) 
	{
	    this(parent);
	    setTitle(title);
	}

	//
    // Initialize
	//
	public PasswordDialog(Frame parent) 
	{
	    super(parent, true);

		//{{INIT_CONTROLS
		setLayout(null);
		addNotify();
		resize(insets().left + insets().right + 312,insets().top + insets().bottom + 131);
		setForeground(new Color(0));
		setBackground(new Color(16711680));
		label2 = new java.awt.Label("please try again",Label.CENTER);
		label2.reshape(insets().left + 84,insets().top + 36,144,26);
		add(label2);
		Ok = new java.awt.Button("OK");
		Ok.reshape(insets().left + 120,insets().top + 72,66,22);
		add(Ok);
		label1 = new java.awt.Label("You have entered a wrong password");
		label1.reshape(insets().left + 12,insets().top + 12,276,24);
		label1.setForeground(new Color(0));
		label1.setBackground(new Color(16711680));
		add(label1);
		setTitle("WrongPassword");
		//}}
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
    // OK bottun pressed
	//
	void Ok_Clicked(Event event) 
	{
		//{{CONNECTION
		// Hide the Dialog
		hide();
		//}}
	}


	//
    // Events handler
	//
	public boolean handleEvent(Event event) 
	{
	    if(event.id == Event.WINDOW_DESTROY) 
	    {
	        hide();
	        return true;
	    }
		if (event.target == Ok && event.id == Event.ACTION_EVENT) 
		{
			Ok_Clicked(event);
			return true;
		}
		return super.handleEvent(event);
	}

	//{{DECLARE_CONTROLS
	java.awt.Label label2;
	java.awt.Button Ok;
	java.awt.Label label1;
	//}}
}
