package gestiune.rezervari;

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
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class Rezervari extends JFrame implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable statusRezervariTable;
	private static Rezervari instance = null;
	private static Connection con = null;
	private static String dbColumnName = "";

	/**
	 * Start microservice	
	 */
	public static void showUI(Connection con) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rezervari.con = con;
					Rezervari frame = Rezervari.getInstance();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Start microservice	
	 */
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rezervari.connect();
					Rezervari frame = Rezervari.getInstance();
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
			Rezervari.con = (Connection) DriverManager.getConnection(JDBC_URL, user, "");
			if (con != null) {
				return Rezervari.con;
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
	private static Rezervari getInstance() {
		if(instance == null) {
			instance = new Rezervari();
		}
		
		return instance;
	}

	/**
	 * Create the frame.
	 */
	private Rezervari() {
		setTitle("Rezervari");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 487, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton rezervariMasaBarBtn = new JButton("Masa bar/restaurant");
		rezervariMasaBarBtn.setActionCommand("masa");
		rezervariMasaBarBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseTable(e);
				Rezervari.dbColumnName = "masa";
			}
		});
		rezervariMasaBarBtn.setToolTipText("Selecteaza masa bar/restaurant");
		rezervariMasaBarBtn.setBounds(10, 34, 137, 23);
		contentPane.add(rezervariMasaBarBtn);
		
		JButton rezervariBiliardBtn = new JButton("Masa biliard");
		rezervariBiliardBtn.setActionCommand("biliard");
		rezervariBiliardBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseTable(e);
				Rezervari.dbColumnName = "biliard";
			}
		});
		rezervariBiliardBtn.setToolTipText("Selecteaza masa biliard");
		rezervariBiliardBtn.setBounds(10, 85, 137, 23);
		contentPane.add(rezervariBiliardBtn);
		
		JButton rezervariBowlingBtn = new JButton("Pista bowling");
		rezervariBowlingBtn.setActionCommand("bowling");
		rezervariBowlingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseTable(e);
				Rezervari.dbColumnName = "bowling";
			}
		});
		rezervariBowlingBtn.setToolTipText("Selecteaza pista bowling");
		rezervariBowlingBtn.setBounds(10, 143, 137, 23);
		contentPane.add(rezervariBowlingBtn);
		
		JButton rezervariDartsBtn = new JButton("Darts");
		rezervariDartsBtn.setActionCommand("darts");
		rezervariDartsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseTable(e);
				Rezervari.dbColumnName = "darts";
			}
		});
		rezervariDartsBtn.setToolTipText("Selecteaza aparat darts");
		rezervariDartsBtn.setBounds(10, 198, 137, 23);
		contentPane.add(rezervariDartsBtn);
		
		statusRezervariTable = new JTable();
		statusRezervariTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		statusRezervariTable.setColumnSelectionAllowed(true);
		statusRezervariTable.setCellSelectionEnabled(true);
		statusRezervariTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		statusRezervariTable.setBackground(Color.WHITE);
		statusRezervariTable.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
					"","Masa", "Biliard", "Bowling", "Darts"
			}
		));
		statusRezervariTable.getColumnModel().getColumn(0).setResizable(false);
		statusRezervariTable.getColumnModel().getColumn(1).setResizable(false);
		statusRezervariTable.getColumnModel().getColumn(2).setResizable(false);
		statusRezervariTable.getColumnModel().getColumn(3).setMinWidth(17);
		statusRezervariTable.setToolTipText("Disponibilitate pentru ziua curenta");
		statusRezervariTable.setBounds(157, 38, 304, 112);
		
		Show_Rezervations_In_JTable();
		
//		statusRezervariTable.addMouseListener(new java.awt.event.MouseAdapter() {
//			public void mouseClicked(java.awt.event.MouseEvent evt) {
//				jTable_Display_UsersMouseClicked(evt);
//			}
//		});
		TableRenderer tableRenderer = new TableRenderer();
		statusRezervariTable.setDefaultRenderer(Object.class, tableRenderer);
		contentPane.add(statusRezervariTable);
		
		JLabel lblNewLabel = new JLabel("Status");
		lblNewLabel.setBounds(261, 13, 105, 14);
		contentPane.add(lblNewLabel);
		
		JButton confirmRezervBtn = new JButton("Confirmare rezervare");
		confirmRezervBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateData();
			}
		});
		confirmRezervBtn.setToolTipText("Inchide fereastra");
		confirmRezervBtn.setBounds(185, 198, 276, 23);
		contentPane.add(confirmRezervBtn);
		
		JButton resetBtn = new JButton("Reseteaza toate rezervarile");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetAllReservations();
			}
		});
		resetBtn.setBounds(185, 161, 276, 23);
		contentPane.add(resetBtn);
	}
	
	private ArrayList<DetaliiRezervare> getReservationList() {
		ArrayList<DetaliiRezervare> detaliiRezervari = new ArrayList<DetaliiRezervare>();
		Connection connection = Rezervari.con;

		String query = "SELECT * FROM  `rezervari` ";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);

			DetaliiRezervare rezervari;

			while (rs.next()) {
				rezervari = new DetaliiRezervare(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				rezervari.setNumarOrdine(rs.getString(1));
				detaliiRezervari.add(rezervari);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detaliiRezervari;
	}
	
	public void Show_Rezervations_In_JTable() {
		ArrayList<DetaliiRezervare> list = getReservationList();
		DefaultTableModel model = (DefaultTableModel) statusRezervariTable.getModel();
		Object[] row = new Object[5];
		String[] header = new String[] {"","Masa", "Biliard", "Bowling", "Darts"};
		model.addRow(header);
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getNumarOrdine();
			row[1] = list.get(i).getMasa();
			row[2] = list.get(i).getBiliard();
			row[3] = list.get(i).getBowling();
			row[4] = list.get(i).getDarts();

			model.addRow(row);
		}
	}
	
	public void executeSQlQuery(String query, String message) {
		Connection con = Rezervari.con;
		Statement st;
		try {
			st = con.createStatement();
			if (st.executeUpdate(query) == 1) {
				// refresh jtable data
				DefaultTableModel model = (DefaultTableModel) statusRezervariTable.getModel();
				model.setRowCount(0);
				Show_Rezervations_In_JTable();

				JOptionPane.showMessageDialog(null, message);
			} else {
				JOptionPane.showMessageDialog(null, message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	private void resetAllReservations() {
		String querry = "UPDATE `rezervari` SET `masa`='liber',`biliard`='liber',`bowling`='liber',`darts`='liber' WHERE 1";
		executeSQlQuery(querry, "Toate rezervarile au fost resetate");
	}
	
	 private void updateData() {
		String query = "UPDATE `rezervari` SET`" + Rezervari.dbColumnName + "`='ocupat' WHERE `numarOrdine`='" + AlegeMasa.getNumarMasa() + "'";
	    executeSQlQuery(query, "Rezervare efectuata cu succes");
	 }

	@Override
	public void tableChanged(TableModelEvent e) {
		System.out.println("Event fired " + e.getSource());
		
	}
	
	public void chooseTable(ActionEvent e) {
		AlegeMasa.showUI();
	}
}
