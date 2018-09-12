package gestiune.login;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ErrorLogIn extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4734826564260647919L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void showUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErrorLogIn frame = new ErrorLogIn();
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
	public ErrorLogIn() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 366, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(130, 70, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("User sau parola gresite");
		lblNewLabel.setBounds(115, 11, 192, 48);
		contentPane.add(lblNewLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		
	}
}
