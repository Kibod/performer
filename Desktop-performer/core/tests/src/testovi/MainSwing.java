package core.tests.src.testovi;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import com.sun.java_cup.internal.runtime.Symbol;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;

public class MainSwing {
	final JTextArea pregledani = new JTextArea();
	final List nepregledani = new List();	
	private JFrame frmPerformertester;
	private JTextField adresa;
	private JTextField argumenttxt;
	private JTextField timelimit;
	private JTextField output;
	private int index = 0;

	
	static ExecutorService executor;
	static Set<Callable<String>> callables = new HashSet<Callable<String>>();
	
	private boolean radi=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainSwing window = new MainSwing();
					window.frmPerformertester.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static String[] trimArray(String[] allElements) {
		int c =0;
		for(int i =0;i<allElements.length;i++){
			if(allElements[i]!=null)c++;
		}
		
	    String[] _localAllElements = new String[c];

	    for(int i = 0; i < allElements.length; i++)
	        if(allElements[i] != null)
	            _localAllElements[i] = allElements[i];

	    return _localAllElements;
	}
	
	/**
	 * Create the application.
	 */
	public MainSwing() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void pregledaj(){
		
		
		int l_timelimit =Integer.parseInt(timelimit.getText());
		String putanja = adresa.getText();
		

		String[] argvs = new String[20];
		Scanner scan = new Scanner(argumenttxt.getText());

		for(int i=0;scan.hasNextInt();i++){
				argvs[i]=(""+scan.next()); 
		}
		argvs=trimArray(argvs);
		
	
		String str="--------------"+putanja+"-------------------\n";
		Test test = new ErrorTest(putanja, argvs,Integer.parseInt(timelimit.getText()));
		test.execute();
		str+="Program ima gresaka: "+test.getResult() +"\n";
		if(test.getResult()=="false"){
		test=  new TimeTest(putanja,argvs);
		test.execute();
		str+="Vreme izvrsavanja: " + test.getResult() + "\n";
		
		Test test2= new CorrectOutputTest(putanja,argvs,output.getText());
		test2.execute();
		str+="Da li je output tacan: " + test2.getResult() + "\n";
		str+="Output programa: " + ((CorrectOutputTest) test2).getOutput() + "\n";
		str+="--------------------------------------------------\n\n";
		}
		pregledani.append(str);
		
		pregledani.setCaretPosition(pregledani.getDocument().getLength());
		int j=nepregledani.getItemCount();
	
		while(j!=0){
			if((putanja+"\n").equals(nepregledani.getItem(j-1)) ){ nepregledani.remove(j-1);
			break;}
			j--;
		}
		
		scan.close();
		
		}
		
	private void initialize() {
		frmPerformertester = new JFrame();
		frmPerformertester.setTitle("Performer-Tester");
		frmPerformertester.setBounds(100, 100, 1012, 706);
		frmPerformertester.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPerformertester.getContentPane().setLayout(null);
		
		adresa = new JTextField();
		adresa.setText("C:/uredjivanje4.exe");
		adresa.setToolTipText("Apsolutna putanja ka fajlu");
		adresa.setBounds(78, 499, 417, 20);
		frmPerformertester.getContentPane().add(adresa);
		adresa.setColumns(10);
		
		argumenttxt = new JTextField();
		argumenttxt.setToolTipText("Argumenti razdvojeni blanko znakom");
		argumenttxt.setText("3000 1");
		argumenttxt.setBounds(505, 499, 417, 20);
		frmPerformertester.getContentPane().add(argumenttxt);
		argumenttxt.setColumns(10);
		
		JLabel lblLog = new JLabel("Pregledani");
		lblLog.setBounds(80, 11, 91, 14);
		frmPerformertester.getContentPane().add(lblLog);
		
		JLabel lblPutanja = new JLabel("Putanja:");
		lblPutanja.setBounds(78, 474, 46, 14);
		frmPerformertester.getContentPane().add(lblPutanja);
		
		JLabel lblArgumenti = new JLabel("Argumenti:");
		lblArgumenti.setBounds(505, 474, 66, 14);
		frmPerformertester.getContentPane().add(lblArgumenti);
		
		timelimit = new JTextField();
		timelimit.setText("1000");
		timelimit.setBounds(786, 549, 136, 20);
		frmPerformertester.getContentPane().add(timelimit);
		timelimit.setColumns(10);
		
		JLabel lblTimeLimitms = new JLabel("Time limit (ms):");
		lblTimeLimitms.setBounds(786, 530, 91, 14);
		frmPerformertester.getContentPane().add(lblTimeLimitms);
		
		
		JLabel lblNaCekanju = new JLabel("Nepregledani:");
		lblNaCekanju.setBounds(505, 11, 144, 14);
		frmPerformertester.getContentPane().add(lblNaCekanju);
		
		output = new JTextField();
		output.setBounds(78, 549, 698, 20);
		frmPerformertester.getContentPane().add(output);
		output.setColumns(10);
		
		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setToolTipText("Ocekivani output");
		lblOutput.setBounds(78, 530, 46, 14);
		frmPerformertester.getContentPane().add(lblOutput);
		 
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(getClass().getResourceAsStream("on.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		final JLabel status_masina =  new JLabel(new ImageIcon( myPicture ));
		status_masina.setToolTipText("Masina");
		status_masina.setEnabled(false);
		status_masina.setBounds(980, 633, 16, 14);
		frmPerformertester.getContentPane().add(status_masina);
		
		final JLabel status_akcija =  new JLabel(new ImageIcon( myPicture ));
		status_akcija.setToolTipText("Pregledanje u toku");
		status_akcija.setEnabled(false);
		status_akcija.setBounds(970, 633, 16, 14);
		frmPerformertester.getContentPane().add(status_akcija);
		
		 
		
		  pregledani.setLineWrap(true);
		  pregledani.setEditable(false);
		  pregledani.setToolTipText("Log");
		  pregledani.setBounds(78, 30, 417, 425);
		
		
		  JScrollPane scroller = new JScrollPane(pregledani);
		  scroller.setBounds(78, 30, 417, 425);
		  frmPerformertester.getContentPane().add(scroller);
				
			nepregledani.setBounds(505, 31, 415, 424);
			frmPerformertester.getContentPane().add(nepregledani);  
			  
			final JButton dodaj = new JButton("Dodaj");
		dodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				nepregledani.add(adresa.getText()+"\n",++index);
				if(radi==false){
					callables.add(new Callable<String>() {
						public String call() throws Exception {
					status_akcija.setEnabled(true);
					pregledaj();
					status_akcija.setEnabled(false);
					
					callables.remove(this);
			        return "true";
						}
					});
					
				}
				else{
					/*---------------------*/
					executor.submit(new Callable<String>() {
						public String call() throws Exception {
					status_akcija.setEnabled(true);
					pregledaj();
					status_akcija.setEnabled(false);
			        return "true";
						}
					});
					/*---------------------*/
				}
				
			}
		});
		
		
		
		
		dodaj.setBounds(786, 594, 136, 23);
		frmPerformertester.getContentPane().add(dodaj);
		
		
		
	
		
		
		
		JMenuBar menuBar = new JMenuBar();
		frmPerformertester.setJMenuBar(menuBar);
		
		JMenu mnAkcije = new JMenu("Akcije");
		menuBar.add(mnAkcije);
		
		JMenuItem pocni_meni = new JMenuItem("Pocni pregledanje");
		
		pocni_meni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				radi=true;
				 executor= Executors.newSingleThreadExecutor();
				try {
					status_masina.setEnabled(true);
					executor.invokeAll(callables);
					
				} catch (InterruptedException e) {}
				
			}
		});
		
		
		pocni_meni.setSelectedIcon(new ImageIcon(MainSwing.class.getResource("/com/sun/java/swing/plaf/windows/icons/ListView.gif")));
		mnAkcije.add(pocni_meni);
		
		JMenuItem zavrsi_meni = new JMenuItem("Zavrsi pregledanje");
		zavrsi_meni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radi=false;
				executor.shutdown();
				callables.clear();
				status_masina.setEnabled(false);
			}
		});
		zavrsi_meni.setSelectedIcon(new ImageIcon(MainSwing.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mnAkcije.add(zavrsi_meni);
		
		JMenuItem obustavi_meni = new JMenuItem("Obustavi pregledanje");
		obustavi_meni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radi=false;
				callables.clear();
				executor.shutdownNow();
				pregledani.setText("");
				nepregledani.removeAll();
				status_masina.setEnabled(false);
				status_akcija.setEnabled(false);
				
			}
		});
		obustavi_meni.setSelectedIcon(new ImageIcon(MainSwing.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		mnAkcije.add(obustavi_meni);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
