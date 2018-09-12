package gestiune.rezervari;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AlegeMasa extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5425946573361406153L;
	private JPanel contentPane;
	private JTextField textField;
	private static int numarMasa = 0;

	public static int getNumarMasa() {
		return numarMasa;
	}

	/**
	 * Launch the application.
	 */
	public static void showUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlegeMasa frame = new AlegeMasa();
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
	public AlegeMasa() {
		setTitle("Alege");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 229, 152);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Numar masa sau pista");
		lblNewLabel.setBounds(40, 19, 161, 14);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(20, 44, 161, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Confirma");
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand("ok");
		btnNewButton.setBounds(0, 79, 213, 23);
		contentPane.add(btnNewButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			numarMasa = Integer.parseInt(textField.getText());
			this.dispose();
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Valoare introdusa gresit, introduceti doar numere!", "Avertisment", JOptionPane.WARNING_MESSAGE);
		}
	}
}
