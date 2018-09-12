package gestiune.detalii;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;

public class DetaliiCont extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3721674481133582297L;
	private JPanel contentPane;
	private JTextPane detaliiContTxt = null;
	private static DetaliiCont instance = null;
	private static Connection conn = null;
	private static String userName = null;
	private static UserData user = null;

	/**
	 * Start microservice	
	 */
	public static void showUI(Connection con, String user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DetaliiCont.conn = con;
					DetaliiCont.userName = user;
					DetaliiCont frame = DetaliiCont.getInstance();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String []args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DetaliiCont.connect();
					DetaliiCont.userName = "admin";
					DetaliiCont frame = DetaliiCont.getInstance();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static Connection connect() {
		String user = "root";
		String DRIVER = "com.mysql.jdbc.Driver";
		String JDBC_URL = "jdbc:mysql://localhost/pubdb";
		
		try {
			Class.forName(DRIVER).newInstance();
			DetaliiCont.conn = (Connection) DriverManager.getConnection(JDBC_URL, user, "");
			if (conn != null) {
				return DetaliiCont.conn;
			} else {
				return null;
			}
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException err) {
			JOptionPane.showMessageDialog(null,
				    "Cannot connect to Data Base.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	/**
	 * Singleton
	 * @return
	 */
	private static DetaliiCont getInstance() {
		if(instance == null) {
			instance = new DetaliiCont();
		}
		
		instance.updatetext();
		
		return instance;
	}

	/**
	 * Create the frame.
	 */
	public DetaliiCont() {
		setTitle("Detalii cont");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		detaliiContTxt = new JTextPane();
		detaliiContTxt.setToolTipText("Detalii angajat");
		detaliiContTxt.setEditable(false);
		
		contentPane.add(detaliiContTxt, BorderLayout.CENTER);
	}
	
	public void updatetext() {
		DetaliiCont.user = new DBUserDetails().getUserDetails(DetaliiCont.conn, DetaliiCont.userName);
		detaliiContTxt.setText("Detalii angajat:" + System.lineSeparator() + user.toString());
	}

}
