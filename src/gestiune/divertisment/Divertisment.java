package gestiune.divertisment;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Divertisment extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -659850329213343435L;
	private JPanel contentPane;
	private JTable availabilityTable;
	private static Divertisment instance = null;
	private static String dbColumnName = "";
	private static Connection con = null;
	private static int numarOrdine = 0;
	
	/**
	 * Start microservice	
	 */
	public static void showUI(Connection con) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Divertisment.con = con;
					Divertisment frame = Divertisment.getInstance();
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
					Divertisment.connect();
					Divertisment frame = Divertisment.getInstance();
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
			Divertisment.con = (Connection) DriverManager.getConnection(JDBC_URL, user, "");
			if (con!=null) {
				return Divertisment.con;
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
	private static Divertisment getInstance() {
		if(instance == null) {
			instance = new Divertisment();
		}
		
		return instance;
	}
	
	/**
	 * Create the frame.
	 */
	public Divertisment() {
		setTitle("Divertisment");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 657, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton startBiliardBtn = new JButton("Start biliard");
		startBiliardBtn.setActionCommand("startBiliard");
		startBiliardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Divertisment.dbColumnName = "biliard";
				chooseTable(e);
			}
		});
		startBiliardBtn.setBounds(10, 34, 103, 23);
		contentPane.add(startBiliardBtn);
		
		JButton startDartsBtn = new JButton("Start darts");
		startDartsBtn.setActionCommand("startDarts");
		startDartsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Divertisment.dbColumnName = "darts";
				chooseTable(e);
			}
		});
		startDartsBtn.setBounds(10, 84, 103, 23);
		contentPane.add(startDartsBtn);
		
		JButton startBowlingBtn = new JButton("Start bowling");
		startBowlingBtn.setActionCommand("startBowling");
		startBowlingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Divertisment.dbColumnName = "bowling";
				chooseTable(e);
			}
		});
		startBowlingBtn.setBounds(10, 135, 103, 23);
		contentPane.add(startBowlingBtn);
		
		JButton stopBiliardBtn = new JButton("Stop biliard");
		stopBiliardBtn.setActionCommand("stopBiliard");
		stopBiliardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Divertisment.dbColumnName = "biliard";
				chooseTable(e);
			}
		});
		stopBiliardBtn.setBounds(123, 34, 103, 23);
		contentPane.add(stopBiliardBtn);
		
		JButton stopDartsBtn = new JButton("Stop darts");
		stopDartsBtn.setActionCommand("stopDarts");
		stopDartsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Divertisment.dbColumnName = "darts";
				chooseTable(e);
			}
		});
		stopDartsBtn.setBounds(123, 84, 103, 23);
		contentPane.add(stopDartsBtn);
		
		JButton stopBowlingBtn = new JButton("Stop bowling");
		stopBowlingBtn.setActionCommand("stopBowling");
		stopBowlingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Divertisment.dbColumnName = "bowling";
				chooseTable(e);
			}
		});
		stopBowlingBtn.setBounds(123, 135, 103, 23);
		contentPane.add(stopBowlingBtn);
		
		availabilityTable = new JTable();
		availabilityTable.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"", "Bowling", "Darts", "Biliard"
			}
		));
		availabilityTable.getColumnModel().getColumn(0).setMinWidth(17);
		availabilityTable.getColumnModel().getColumn(1).setMinWidth(17);
		availabilityTable.getColumnModel().getColumn(2).setMinWidth(17);
		availabilityTable.setToolTipText("Disponibilitate pentru ziua curenta");
		availabilityTable.setRowSelectionAllowed(false);
		availabilityTable.setEnabled(false);
		availabilityTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		availabilityTable.setBackground(Color.WHITE);
		availabilityTable.setBounds(249, 62, 382, 96);
		contentPane.add(availabilityTable);
		
		Show_Entertaintment_In_JTable();
		
		JButton closeWindowBtn = new JButton("Inchide fereastra");
		stopBowlingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instance.dispose();
			}
		});
		closeWindowBtn.setBounds(10, 188, 621, 23);
		contentPane.add(closeWindowBtn);
		
		JLabel lblDisponibilitate = new JLabel("Disponibilitate");
		lblDisponibilitate.setBounds(390, 34, 97, 14);
		contentPane.add(lblDisponibilitate);
	}
	
	private ArrayList<DetaliiDivertisment> getDivertismentList() {
		ArrayList<DetaliiDivertisment> detaliiDivertisment = new ArrayList<DetaliiDivertisment>();
		Connection connection = Divertisment.con;

		String query = "SELECT * FROM  `divertisment` ";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);

			DetaliiDivertisment divertisment;

			while (rs.next()) {
				divertisment = new DetaliiDivertisment(rs.getString(1), rs.getString(2), rs.getString(3));
				divertisment.setNumarOrdine(rs.getString(4));
				detaliiDivertisment.add(divertisment);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detaliiDivertisment;
	}
	
	public void Show_Entertaintment_In_JTable() {
		ArrayList<DetaliiDivertisment> list = getDivertismentList();
		DefaultTableModel model = (DefaultTableModel) availabilityTable.getModel();
		Object[] row = new Object[5];
		String[] header = new String[] {"","Biliard", "Darts", "Bowling"};
		model.addRow(header);
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getNumarOrdine();
			row[1] = list.get(i).getBiliard();
			row[2] = list.get(i).getDarts();
			row[3] = list.get(i).getBowling();

			model.addRow(row);
		}
	}
	
	public void executeSQlQuery(String query, String message) {
		Connection con = Divertisment.con;
		Statement st;
		try {
			st = con.createStatement();
			if (st.executeUpdate(query) == 1) {
				// refresh jtable data
				DefaultTableModel model = (DefaultTableModel) availabilityTable.getModel();
				model.setRowCount(0);
				Show_Entertaintment_In_JTable();

				JOptionPane.showMessageDialog(null, message);
			} else {
				JOptionPane.showMessageDialog(null, message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void setOcupied() {
		String query = "UPDATE `divertisment` SET`" + Divertisment.dbColumnName + "`='ocupat' WHERE `numarOrdine`='" + Divertisment.numarOrdine + "'";
		executeSQlQuery(query, "Ocupare " + getThing() + " efectuata cu secces!!!");
	}

	private String getThing() {
		String mesaj = "";
		switch (mesaj) {
			case "biliard" : mesaj = "masa";
				break;
			case "darts" : mesaj = "aparat";
				break;
			case "bowling": mesaj = "pista";
					break;
		}
		return mesaj;
	}
	
	private void setAvailable() {
		String query = "UPDATE `divertisment` SET`" + Divertisment.dbColumnName + "`='liber' WHERE `numarOrdine`='" + Divertisment.numarOrdine + "'";
		executeSQlQuery(query, "Eliberare " + getThing() + " efectuata cu secces!!!");
	}
	
	public void chooseTable(ActionEvent e) {

		Object[] possibilities = { "1", "2", "3", "4", "5" };
		String s = (String) JOptionPane.showInputDialog(
				this, 
				"Numar de ordine", 
				"Biliard, bowling sau darts",
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				possibilities, 
				"1");
		
		try {
			Divertisment.numarOrdine = Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(
					this,
					"Valoare incorecta",
				    "Eroare",
				    JOptionPane.ERROR_MESSAGE);
		}

		String command = e.getActionCommand();
		switch (command) {
		case "startBiliard": setOcupied();
			break;
		case "stopBiliard": setAvailable();
			break;
		case "startBowling": setOcupied();
			break;
		case "stopBowling":	setAvailable();
			break;
		case "startDarts": setOcupied();
			break;
		case "stopDarts": setAvailable();
			break;
		}
	}

}
