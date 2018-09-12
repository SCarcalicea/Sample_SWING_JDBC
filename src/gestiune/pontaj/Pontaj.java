package gestiune.pontaj;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.ChronoUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Pontaj extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Pontaj instance = null;
	private JTextArea workingHourStatusTxt = null;

	/**
	 * Start microservice	
	 */
	public static void showUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pontaj frame = Pontaj.getInstance();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String []args) {
		showUI();
	}
	
	/**
	 * Singleton
	 * @return
	 */
	private static Pontaj getInstance() {
		if(instance == null) {
			instance = new Pontaj();
		}
		
		return instance;
	}

	/**
	 * Create the frame.
	 */
	public Pontaj() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 620, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton startWorkTimeBtn = new JButton("Pontaj Intrare");
		startWorkTimeBtn.addActionListener(this);
		startWorkTimeBtn.setActionCommand("intrare");
		startWorkTimeBtn.setToolTipText("Seteaza ora de intrare");
		startWorkTimeBtn.setBounds(32, 50, 129, 23);
		contentPane.add(startWorkTimeBtn);
		
		JButton endWorkTimeBtn = new JButton("Pontaj Iesire");
		endWorkTimeBtn.addActionListener(this);
		endWorkTimeBtn.setActionCommand("iesire");
		endWorkTimeBtn.setToolTipText("Seteaza ora de iesire");
		endWorkTimeBtn.setBounds(32, 104, 129, 23);
		contentPane.add(endWorkTimeBtn);
		
		JButton automaticTimingBtn = new JButton("Pontaj Automat");
		automaticTimingBtn.addActionListener(this);
		automaticTimingBtn.setActionCommand("automatic");
		automaticTimingBtn.setToolTipText("Pontaj automatic (activ 8 ore)");
		automaticTimingBtn.setBounds(32, 151, 129, 23);
		contentPane.add(automaticTimingBtn);
		
		JLabel lblStatusPontare = new JLabel("Status pontare");
		lblStatusPontare.setBounds(282, 29, 90, 14);
		contentPane.add(lblStatusPontare);
		
		workingHourStatusTxt = new JTextArea();
		workingHourStatusTxt.setEditable(false);
		workingHourStatusTxt.setToolTipText("Status");
		workingHourStatusTxt.setBounds(224, 49, 353, 125);
		contentPane.add(workingHourStatusTxt);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();

		switch (command) {
		case "intrare" : {
			workingHourStatusTxt.setText(PontajCalculator.startTime() + System.lineSeparator());
		}
			break;
		case "iesire" : {
			workingHourStatusTxt.append(PontajCalculator.endTime() + System.lineSeparator());
		}
			break;
		case "automatic" : {
			clear();
			workingHourStatusTxt.setText(PontajCalculator.startTime() + System.lineSeparator());
			PontajCalculator.automaticTimer();
			workingHourStatusTxt.append("Aplicatia se va inchide automat in 8 ore" + System.lineSeparator());
			workingHourStatusTxt.append("Ora iesire: " + PontajCalculator.getStartTime().plus(8, ChronoUnit.HOURS));
		}
			break;
		}
	}
	
	public void clear () {
		workingHourStatusTxt.setText("");
	}
}
