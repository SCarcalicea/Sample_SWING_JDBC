package gestiune.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

import gestiune.comanda.ComandaBar;
import gestiune.comanda.ComandaRestaurant;
import gestiune.detalii.DetaliiCont;
import gestiune.divertisment.Divertisment;
import gestiune.login.LogIn;
import gestiune.pontaj.Pontaj;
import gestiune.rezervari.Rezervari;

public class MainMenu extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static MainMenu instance = null;
	private static Connection con = null;
	private static String user = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = MainMenu.getInstance();
					frame.connect();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Connection connect() {
		MainMenu.user = "root";
		String DRIVER = "com.mysql.jdbc.Driver";
		String JDBC_URL = "jdbc:mysql://localhost/pubdb";
		
		try {
			Class.forName(DRIVER).newInstance();
			MainMenu.con = (Connection) DriverManager.getConnection(JDBC_URL, MainMenu.user, "");
			if (con!=null) {
				return MainMenu.con;
			} else {
				return null;
			}
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException err) {
			JOptionPane.showMessageDialog(this,
				    "Cannot connect to Data Base.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	
	public static void showUI(Connection con, String user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu.con = con;
					MainMenu.user = user;
					MainMenu frame = MainMenu.getInstance();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static MainMenu getInstance() {
		if (instance == null) {
			instance = new MainMenu();
		}
		return instance;
	}
	
	
	/**
	 * Create the frame.
	 */
	private MainMenu() {
		setTitle("Sistem Gestiune");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 805, 673);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/logo.png")));
		lblNewLabel.setBounds(0, 0, 788, 52);
		contentPane.add(lblNewLabel);
		
		JButton barBtn = new JButton("Comanda Bar");
		barBtn.setActionCommand("comandaBar");
		barBtn.addActionListener(this);
		barBtn.setToolTipText("Comanda Bar");
		barBtn.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/Bar.png")));
		barBtn.setBounds(266, 63, 256, 256);
		contentPane.add(barBtn);
		
		JButton divertismentBtn = new JButton("Divertisment");
		divertismentBtn.setActionCommand("divertisment");
		divertismentBtn.addActionListener(this);
		divertismentBtn.setToolTipText("Divertisment");
		divertismentBtn.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/divertisment.png")));
		divertismentBtn.setBounds(532, 63, 256, 256);
		contentPane.add(divertismentBtn);
		
		JButton pontajBtn = new JButton("Pontaj");
		pontajBtn.setActionCommand("pontaj");
		pontajBtn.addActionListener(this);
		pontajBtn.setToolTipText("Pontaj");
		pontajBtn.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/pontaj.png")));
		pontajBtn.setBounds(0, 330, 256, 256);
		contentPane.add(pontajBtn);
		
		JButton rezervariBtn = new JButton("Rezervari");
		rezervariBtn.setActionCommand("rezervari");
		rezervariBtn.addActionListener(this);
		rezervariBtn.setToolTipText("Rezervari");
		rezervariBtn.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/rezervari.png")));
		rezervariBtn.setBounds(266, 330, 256, 256);
		contentPane.add(rezervariBtn);
		
		JButton contulMeuBtn = new JButton("Detalii cont");
		contulMeuBtn.setActionCommand("detaliiCont");
		contulMeuBtn.addActionListener(this);
		contulMeuBtn.setToolTipText("Detalii cont");
		contulMeuBtn.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/contulMeu.jpg")));
		contulMeuBtn.setBounds(532, 330, 256, 256);
		contentPane.add(contulMeuBtn);
		
		JButton logoutBtn = new JButton("Delogare");
		logoutBtn.setActionCommand("delogare");
		logoutBtn.addActionListener(this);
		logoutBtn.setToolTipText("Delogare");
		logoutBtn.setBounds(0, 596, 788, 40);
		contentPane.add(logoutBtn);
		
		JButton restaurantBtn = new JButton("Comanda Restaurant");
		restaurantBtn.setActionCommand("comandaRestaurant");
		restaurantBtn.addActionListener(this);
		restaurantBtn.setToolTipText("Comanda Restaurant");
		restaurantBtn.setIcon(new ImageIcon(MainMenu.class.getResource("/resources/restaurantIcon.png")));
		restaurantBtn.setBounds(0, 63, 256, 256);
		contentPane.add(restaurantBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
			case "divertisment": Divertisment.showUI(MainMenu.con);
				break;
			case "comandaBar" : ComandaBar.showUI(MainMenu.con);
				break;	
			case "pontaj" : Pontaj.showUI();
				break;
			case "rezervari" : Rezervari.showUI(MainMenu.con);
				break;
			case "detaliiCont" : DetaliiCont.showUI(MainMenu.con, MainMenu.user);
				break;
			case "comandaRestaurant" : ComandaRestaurant.showUI(MainMenu.con);
				break;
			case "delogare":  {
				LogOut.logOut(MainMenu.con);
				this.dispose();
				LogIn.showUI();
			}
			
				break;
		}
	}
	
}
