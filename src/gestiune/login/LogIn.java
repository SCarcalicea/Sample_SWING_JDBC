package gestiune.login;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import gestiune.main.MainMenu;

public class LogIn extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userText;
	private JPasswordField passwordText;

	/**
	 * Start microservice	
	 */
	public static void main(String [] args) {
		showUI();
	}
	
	/**
	 * Start microservice	
	 */
	public static void showUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame =  new LogIn();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public LogIn() {
		setTitle("Logare");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(62, 52, 46, 14);
		contentPane.add(lblUser);
		
		JLabel lblParola = new JLabel("Parola:");
		lblParola.setBounds(62, 96, 46, 14);
		contentPane.add(lblParola);
		
		userText = new JTextField();
		userText.setToolTipText("Intruduceti numele");
		userText.setBounds(177, 49, 161, 20);
		contentPane.add(userText);
		userText.setColumns(10);
		
		passwordText = new JPasswordField();
		passwordText.setToolTipText("Introduceti parola");
		passwordText.setBounds(177, 93, 161, 20);
		contentPane.add(passwordText);
		passwordText.setColumns(10);
		
		JButton loginBtn = new JButton("Logare");
		loginBtn.setActionCommand("login");
		loginBtn.addActionListener(this);
		loginBtn.setToolTipText("Autentificare user");
		loginBtn.setBounds(89, 174, 89, 23);
		contentPane.add(loginBtn);
		
		JButton cancelLoginBtn = new JButton("Anulare");
		cancelLoginBtn.setActionCommand("cancel");
		cancelLoginBtn.addActionListener(this);
		cancelLoginBtn.setToolTipText("Inchidere aplicatie");
		cancelLoginBtn.setBounds(224, 174, 89, 23);
		contentPane.add(cancelLoginBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
		case "login": try {
				validateUserAndLogIn();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		break;
		case "cancel" : System.exit(0);
		}
	}
	
	private void validateUserAndLogIn() throws SQLException {
		DataBaseConnectivity connection = new DataBaseConnectivity();
		
		String userInput = userText.getText();
		String passInput = new String(passwordText.getPassword());
		
		if (userInput.isEmpty() || passInput.isEmpty()) {
			ErrorLogIn.showUI();
		} else {
		
			if (connection.connect() && connection.checkPassword(userText.getText(), new String(passwordText.getPassword()))) {
				MainMenu.showUI(connection.getConnection(), userInput);
				userText.setText("");
				passwordText.setText("");
				this.dispose();
			} else {
				ErrorLogIn.showUI();
			}
		}
	}
}
