package psl.wgcache;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;

import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import psl.wgcache.Cacheable;
import psl.wgcache.PersonalCacheModuleImpl;
import psl.wgcache.WGCException;

public class PCM_Visual {  private static final int MAX_WIDTH = 500;
  private static final int MAX_HEIGHT = 300;  private static final int NUM_ENTRIES = 10;
  // private AWT variables
  private Frame _frame = null;
  private Panel _panel = null;
  private static final Color _panelColour = Color.yellow; // Color.green;
  private static final Color _buttonColour = new Color(128, 128, 255);
  private static final Color _textColour = new Color(128, 64, 0);
  // Top Panel settings ////////////////////////////////
  private Panel _topPanel = null;
  private Label _topLabel1 = null;
  private Label _topLabel2 = null;
  private static final Font _largeFont = new Font("SansSerif", Font.BOLD, 20);
  private static final Font _mediumFont = new Font("SansSerif", Font.BOLD, 14);
  private static final Font _smallFont = new Font("SansSerif", Font.BOLD, 12);
  // Mid Panel settings ////////////////////////////////
  private Panel _midPanel = null;
  private Panel _midPanel1 = null;
  private Panel _midPanel2 = null;
  private Panel _midPanel3 = null;
  private Panel _midPanel1_1 = null;
  private Panel _midPanel2_1 = null;
  private Panel _midPanel3_1 = null;
  private ScrollPane _midPanel1Scp = null;
  private ScrollPane _midPanel2Scp = null;
  private ScrollPane _midPanel3Scp = null;
  private Label _midLabel1 = null;
  private Label _midLabel2 = null;
  private Label _midLabel3 = null;
  // Bot Panel settings ////////////////////////////////
  private JTabbedPane _botPane = null;
  private Panel       _botPanel1 = null;
  private Panel       _botPanel2 = null;
  private Button      _quitButton = null;
  private Panel       _botPanel1_1 = null;
  private Panel       _botPanel1_2 = null;
  private Panel       _botPanel1_3 = null;
  private Button      _botPanel1_1_AddDataBut = null;
  private Button      _botPanel1_1_AddWGBut = null;
  
  private Panel       _botPanel1_2_1 = null;
  private Panel       _botPanel1_2_2 = null;
  private GridBagLayout _botPanel1_2_1GBL = null;
  private GridBagConstraints _botPanel1_2_1GBC = null;
  private Label       _botPanel1_2_KeyLabel = null;
  private Label       _botPanel1_2_DataLabel = null;
  private TextField   _botPanel1_2_KeyText = null;
  private TextField   _botPanel1_2_DataText = null;
  private Button      _botPanel1_2_OKBut = null;
  private Button      _botPanel1_2_ESCBut = null;
  
  private Panel       _botPanel1_3_1 = null;
  private Panel       _botPanel1_3_2 = null;
  private GridBagLayout _botPanel1_3_1GBL = null;
  private GridBagConstraints _botPanel1_3_1GBC = null;
  private Label       _botPanel1_3_WGLabel = null;
  private Label       _botPanel1_3_UsersLabel = null;
  private Label       _botPanel1_3_TypeLabel = null;
  private TextField   _botPanel1_3_WGText = null;
  private TextField   _botPanel1_3_UsersText = null;
  private CheckboxGroup _botPanel1_3_TypeCBoxGrp = null;
  private Checkbox    _botPanel1_3_TypeCheckbox1 = null;
  private Checkbox    _botPanel1_3_TypeCheckbox2 = null;
  private Button      _botPanel1_3_AddWG_But = null;
  private Button      _botPanel1_3_ESCBut = null;
  private TextArea    _botPanel2_TextArea = null;
  // Personal Cache Module stuff ///////////////////////
  private String _roleName = null;
  private PersonalCacheModuleImpl _pcmi = null;

  private PCM_Visual(String name) {
    // Frame settings //////////////////////////////////
    _frame = new Frame(name);
    _frame.setSize(MAX_WIDTH, MAX_HEIGHT);
    _frame.setResizable(false);
    _frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        System.exit(0);
      }
    });
    
    // Panel settings //////////////////////////////////
    _panel = new Panel(new BorderLayout());
    _panel.setBackground(_panelColour);
    _frame.add(_panel);

    // Header settings /////////////////////////////////
    _topPanel = new Panel(new GridLayout(2, 1));
    _topLabel1 = new Label("Personal Cache Module - WGC", Label.CENTER);
    _topLabel1.setFont(_largeFont);
    _topLabel1.setForeground(_textColour);
    _topLabel2 = new Label(_roleName = System.getProperty("User"), Label.CENTER);
    _topLabel2.setFont(_largeFont);
    _topLabel2.setForeground(_textColour);
    _topPanel.add(_topLabel1);
    _topPanel.add(_topLabel2);
    _panel.add(_topPanel, BorderLayout.NORTH);

    // Main panel settings /////////////////////////////
    _midPanel = new Panel(new GridLayout(1, 3, 5, 25));
        _midPanel1 = new Panel(new BorderLayout());
    _midPanel1.add(_midLabel1 = new Label("Cached data [key-data]", Label.CENTER), BorderLayout.NORTH);
    _midLabel1.setFont(_smallFont);
    _midLabel1.setForeground(_textColour);
    _midPanel1.add(_midPanel1Scp = new ScrollPane(), BorderLayout.CENTER);    _midPanel1Scp.add(_midPanel1_1 = new Panel(new GridLayout(NUM_ENTRIES, 1)));
    // _midPanel1.add(_midPanel1_1 = new Panel(new GridLayout(NUM_ENTRIES, 1)), BorderLayout.CENTER);
        _midPanel2 = new Panel(new BorderLayout());    _midPanel2.add(_midLabel2 = new Label("Workgroups", Label.CENTER), BorderLayout.NORTH);
    _midLabel2.setFont(_smallFont);
    _midLabel2.setForeground(_textColour);    _midPanel2.add(_midPanel2Scp = new ScrollPane(), BorderLayout.CENTER);    _midPanel2Scp.add(_midPanel2_1 = new Panel(new GridLayout(NUM_ENTRIES, 1)));
        _midPanel3 = new Panel(new BorderLayout());
    _midPanel3.add(_midLabel3 = new Label("WG info", Label.CENTER), BorderLayout.NORTH);
    _midLabel3.setFont(_smallFont);
    _midLabel3.setForeground(_textColour);
    _midPanel3.add(_midPanel3Scp = new ScrollPane(), BorderLayout.CENTER);    _midPanel3Scp.add(_midPanel3_1 = new Panel(new GridLayout(NUM_ENTRIES, 1)));
    // _midPanel3.add(_midPanel3_1 = new Panel(new GridLayout(NUM_ENTRIES, 1)), BorderLayout.CENTER);
        _midPanel.add(_midPanel1);
    _midPanel.add(_midPanel2);
    _midPanel.add(_midPanel3);
    _panel.add(_midPanel, BorderLayout.CENTER);

    // Bottom panel settings ///////////////////////////
    _botPane = new JTabbedPane(SwingConstants.LEFT);
    _botPanel1 = new Panel(new CardLayout());
    _botPanel1_1 = new Panel(new GridLayout(1, 2));
    _botPanel1_1_AddDataBut = new Button("Add Cached Data");
    _botPanel1_1_AddDataBut.setFont(_largeFont);
    _botPanel1_1_AddDataBut.setForeground(Color.white);
    _botPanel1_1_AddDataBut.setBackground(_buttonColour);
    _botPanel1_1_AddDataBut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        ((CardLayout) _botPanel1.getLayout()).show(_botPanel1, "ADD DATA");
      }
    });
    _botPanel1_1_AddWGBut = new Button("Add Workgroup");
    _botPanel1_1_AddWGBut.setFont(_largeFont);
    _botPanel1_1_AddWGBut.setForeground(Color.white);
    _botPanel1_1_AddWGBut.setBackground(_buttonColour);
    _botPanel1_1_AddWGBut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        ((CardLayout) _botPanel1.getLayout()).show(_botPanel1, "ADD WG");
      }
    });
    _botPanel1_1.add(_botPanel1_1_AddDataBut);
    _botPanel1_1.add(_botPanel1_1_AddWGBut);
    // ------------------------------------------------------------------------------- //
    _botPanel1_2 = new Panel(new GridLayout(1, 2));
    _botPanel1_2_KeyLabel = new Label("Key: ", Label.RIGHT);
    _botPanel1_2_KeyLabel.setFont(_mediumFont);
    _botPanel1_2_DataLabel = new Label("Data: ", Label.RIGHT);
    _botPanel1_2_DataLabel.setFont(_mediumFont);
    _botPanel1_2_KeyText = new TextField();
    _botPanel1_2_KeyText.setForeground(Color.white);
    _botPanel1_2_KeyText.setBackground(_buttonColour);
    _botPanel1_2_DataText = new TextField();
    _botPanel1_2_DataText.setForeground(Color.white);
    _botPanel1_2_DataText.setBackground(_buttonColour);
    _botPanel1_2_OKBut = new Button("O.K.");
    _botPanel1_2_OKBut.setFont(_largeFont);
    _botPanel1_2_OKBut.setForeground(Color.white);
    _botPanel1_2_OKBut.setBackground(_buttonColour);
    _botPanel1_2_ESCBut = new Button("Cancel");
    _botPanel1_2_ESCBut.setFont(_largeFont);
    _botPanel1_2_ESCBut.setForeground(Color.white);
    _botPanel1_2_ESCBut.setBackground(_buttonColour);
    // ------------------------------------------------------------------------------- //
    _botPanel1_2.add(_botPanel1_2_1 = new Panel(_botPanel1_2_1GBL = new GridBagLayout()));
    _botPanel1_2_1GBC = new GridBagConstraints();
    _botPanel1_2_1GBC.fill = GridBagConstraints.BOTH;
    _botPanel1_2_1GBC.weightx = 4.0;
    _botPanel1_2_1GBC.gridwidth = GridBagConstraints.REMAINDER;
    // ------------------------------------------------------------------------------- //
    _botPanel1_2_1.add(_botPanel1_2_KeyLabel);
    _botPanel1_2_1GBL.setConstraints(_botPanel1_2_KeyText, _botPanel1_2_1GBC);
    _botPanel1_2_1.add(_botPanel1_2_KeyText);
    _botPanel1_2_1.add(_botPanel1_2_DataLabel);
    _botPanel1_2_1GBL.setConstraints(_botPanel1_2_DataText, _botPanel1_2_1GBC);
    _botPanel1_2_1.add(_botPanel1_2_DataText);
    // ------------------------------------------------------------------------------- //
    _botPanel1_2.add(_botPanel1_2_2 = new Panel(new GridLayout(1, 2)));
    _botPanel1_2_2.add(_botPanel1_2_OKBut);
    _botPanel1_2_OKBut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        String key = _botPanel1_2_KeyText.getText();
        String data = _botPanel1_2_DataText.getText();
        if (! key.equals("")) {
          _pcmi.put(new Cacheable(key, data, data.length()));
        }
        _botPanel1_2_KeyText.setText("");
        _botPanel1_2_DataText.setText("");
        ((CardLayout) _botPanel1.getLayout()).show(_botPanel1, "TOP");
      }
    });
    _botPanel1_2_2.add(_botPanel1_2_ESCBut);
    _botPanel1_2_ESCBut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        _botPanel1_2_KeyText.setText("");
        _botPanel1_2_DataText.setText("");
        ((CardLayout) _botPanel1.getLayout()).show(_botPanel1, "TOP");
      }
    });
    /////////////////////////////////////////////////////////////////////////////////////
    _botPanel1_3 = new Panel(new GridLayout(1, 2));
    _botPanel1_3_WGLabel = new Label("WG: ", Label.RIGHT);
    _botPanel1_3_WGLabel.setFont(_mediumFont);
    _botPanel1_3_UsersLabel = new Label("Users: ", Label.RIGHT);
    _botPanel1_3_UsersLabel.setFont(_mediumFont);
    _botPanel1_3_TypeLabel = new Label("Type: ", Label.RIGHT);
    _botPanel1_3_TypeLabel.setFont(_mediumFont);
    _botPanel1_3_WGText = new TextField();
    _botPanel1_3_WGText.setForeground(Color.white);
    _botPanel1_3_WGText.setBackground(_buttonColour);
    _botPanel1_3_UsersText = new TextField();
    _botPanel1_3_UsersText.setEnabled(false);
    _botPanel1_3_UsersText.setForeground(Color.white);
    _botPanel1_3_UsersText.setBackground(_buttonColour);
    _botPanel1_3_TypeCBoxGrp = new CheckboxGroup();
    _botPanel1_3_TypeCheckbox1 = new Checkbox("Create", _botPanel1_3_TypeCBoxGrp, true);
    _botPanel1_3_TypeCheckbox2 = new Checkbox("Join", _botPanel1_3_TypeCBoxGrp, false);
    _botPanel1_3_AddWG_But = new Button("Add");
    _botPanel1_3_AddWG_But.setFont(_largeFont);
    _botPanel1_3_AddWG_But.setForeground(Color.white);
    _botPanel1_3_AddWG_But.setBackground(_buttonColour);
    _botPanel1_3_ESCBut = new Button("Cancel");
    _botPanel1_3_ESCBut.setFont(_largeFont);
    _botPanel1_3_ESCBut.setForeground(Color.white);
    _botPanel1_3_ESCBut.setBackground(_buttonColour);
    // ------------------------------------------------------------------------------- //
    _botPanel1_3.add(_botPanel1_3_1 = new Panel(_botPanel1_3_1GBL = new GridBagLayout()));
    _botPanel1_3_1GBC = new GridBagConstraints();
    _botPanel1_3_1GBC.fill = GridBagConstraints.BOTH;
    _botPanel1_3_1GBC.weightx = 4.0;
    _botPanel1_3_1GBC.gridwidth = GridBagConstraints.REMAINDER;
    // ------------------------------------------------------------------------------- //
    _botPanel1_3_1.add(_botPanel1_3_WGLabel);
    _botPanel1_3_1GBL.setConstraints(_botPanel1_3_WGText, _botPanel1_3_1GBC);
    _botPanel1_3_1.add(_botPanel1_3_WGText);
    _botPanel1_3_1.add(_botPanel1_3_UsersLabel);
    _botPanel1_3_1GBL.setConstraints(_botPanel1_3_UsersText, _botPanel1_3_1GBC);
    _botPanel1_3_1.add(_botPanel1_3_UsersText);
    _botPanel1_3_1.add(_botPanel1_3_TypeLabel);
    _botPanel1_3_1.add(_botPanel1_3_TypeCheckbox1);
    _botPanel1_3_1.add(_botPanel1_3_TypeCheckbox2);
    // ------------------------------------------------------------------------------- //
    _botPanel1_3.add(_botPanel1_3_2 = new Panel(new GridLayout(1, 3)));
    _botPanel1_3_2.add(_botPanel1_3_AddWG_But);
    _botPanel1_3_AddWG_But.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        String wgName = _botPanel1_3_WGText.getText();
        if (! wgName.equals("")) {
          try {            if (_botPanel1_3_TypeCheckbox1.getState()) {              _pcmi.createWorkgroup(wgName);            } else {              _pcmi.joinWorkgroup(wgName);            }
            // workgroupAdded(wgName);
          } catch (WGCException wgce) {
            wgce.printStackTrace();
          }
        }
        _botPanel1_3_WGText.setText("");
        _botPanel1_3_UsersText.setText("");
        _botPanel1_3_TypeCheckbox1.setState(true);
        ((CardLayout) _botPanel1.getLayout()).show(_botPanel1, "TOP");
      }
    });
    _botPanel1_3_2.add(_botPanel1_3_ESCBut);
    _botPanel1_3_ESCBut.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        _botPanel1_3_WGText.setText("");
        _botPanel1_3_UsersText.setText("");
        _botPanel1_3_TypeCheckbox1.setState(true);
        ((CardLayout) _botPanel1.getLayout()).show(_botPanel1, "TOP");
      }
    });
    /////////////////////////////////////////////////////////////////////////////////////
    _botPanel1.add(_botPanel1_1, "TOP");
    _botPanel1.add(_botPanel1_2, "ADD DATA");
    _botPanel1.add(_botPanel1_3, "ADD WG");

    _botPanel2 = new Panel(new GridLayout(1, 1));
    _botPanel2_TextArea = new TextArea("logs here", 5, 50, TextArea.SCROLLBARS_BOTH);
    _botPanel2_TextArea.setFont(_smallFont);
    _botPanel2_TextArea.setForeground(Color.white);
    _botPanel2_TextArea.setBackground(_buttonColour);
    _botPanel2.add(_botPanel2_TextArea);
    
    _quitButton = new Button("Really quit!");
    _quitButton.setFont(_largeFont);
    _quitButton.setForeground(Color.red);
    _quitButton.setBackground(_buttonColour);
    _quitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        System.exit(0);
      }
    });
    _botPane.addTab("Ctrl Panel", _botPanel1);
    _botPane.addTab("Logs", _botPanel2);
    _botPane.addTab("Quit", _quitButton);
    _botPane.setFont(_smallFont);
    _botPane.setBackground(_panelColour);
    _botPane.setForeground(_textColour);
    _panel.add(_botPane, BorderLayout.SOUTH);

    // _frame.pack();
    _frame.show();
    /////////////////////////////////////////////////////////////////////////////////////
    _pcmi = new PersonalCacheModuleImpl(_roleName, this);
  }
  
  void cacheDataAdded(String key, String data) {
    if (key.equals("")) return;
    Panel p = new Panel(new GridLayout(1, 2));
    p.add(new TextField(key));
    p.add(new TextField(data));
    _midPanel1_1.add(p);
    p.validate(); _midPanel1Scp.validate(); _midPanel1_1.validate();
  }
  
  void workgroupAdded(String wgName) {
    if (wgName.equals("")) return;
    Panel p = new Panel(new GridLayout(1, 1));
    p.add(new TextField(wgName));
    _midPanel2_1.add(p);
    p.validate(); _midPanel2Scp.validate(); _midPanel2_1.validate();  }
  
  public static void main(String args[]) {
    PCM_Visual pcmv = new PCM_Visual("PCM_Visual test");
  }
}
