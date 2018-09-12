package gestiune.comanda;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ComandaBar extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ComandaBar instance = null;
	private JTable meniuTable;
	private JTable comandaTable;
	private static Connection con = null;
	private ArrayList<DetaliiComandaBar> comandaFinala = new ArrayList<DetaliiComandaBar>();
	private String numarMasa = ""; //Numar masa

	/**
	 * Start microservice	
	 */
	public static void showUI(Connection con) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComandaBar.con = con;
					ComandaBar frame = ComandaBar.getInstance();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComandaBar.connect();
					ComandaBar frame = ComandaBar.getInstance();
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
			ComandaBar.con = (Connection) DriverManager.getConnection(JDBC_URL, user, "");
			if (con!=null) {
				return ComandaBar.con;
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
	private static ComandaBar getInstance() {
		if(instance == null) {
			instance = new ComandaBar();
		}
		
		return instance;
	}

	/**
	 * Create the frame.
	 */
	private ComandaBar() {
		setTitle("Comanda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 456, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMeniu = new JLabel("Meniu");
		lblMeniu.setBounds(42, 23, 49, 25);
		contentPane.add(lblMeniu);
		
		JLabel lblComanda = new JLabel("Comanda curenta");
		lblComanda.setBounds(45, 232, 175, 25);
		contentPane.add(lblComanda);
		
		JButton selectTableBtn = new JButton("Selectie masa");
		selectTableBtn.setToolTipText("Selecteaza masa");
		selectTableBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseTable();
			}
		});
		selectTableBtn.setBounds(22, 480, 144, 23);
		contentPane.add(selectTableBtn);
		
		JButton finalizeBtn = new JButton("Finalizeaza");
		finalizeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeUpdate();
			}
		});
		finalizeBtn.setToolTipText("Finalizeaza comanda");
		finalizeBtn.setBounds(176, 480, 113, 23);
		contentPane.add(finalizeBtn);
		
		JButton CancelBtn = new JButton("Anuleaza");
		CancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearTableContent();
			}
		});
		CancelBtn.setToolTipText("Sterge tot");
		CancelBtn.setBounds(299, 480, 113, 23);
		contentPane.add(CancelBtn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 47, 408, 174);
		contentPane.add(scrollPane);
		
		meniuTable = new JTable();
		meniuTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		meniuTable.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
						"Categorie","Denumire", "Cantitate", "Pret"
				}
			));
		meniuTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				selectDrink(evt);
			}
		});
		scrollPane.setViewportView(meniuTable);
		Show_ComandaBar_In_JTable();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(22, 256, 408, 213);
		contentPane.add(scrollPane_1);
		
		comandaTable = new JTable();
		comandaTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		comandaTable.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
						"Categorie","Denumire", "Cantitate", "Pret"
				}
			));
		scrollPane_1.setViewportView(comandaTable);
	}
	
	public void selectDrink(MouseEvent evt) {

		JTable target = (JTable)evt.getSource();
		int row = target.getSelectedRow();

		DetaliiComandaBar produs = new DetaliiComandaBar(
				(String)target.getValueAt(row, 0),
				(String)target.getValueAt(row, 1),
				(String)target.getValueAt(row, 2),
				(String)target.getValueAt(row, 3));
		addProduct(produs);
	}
	
	private void addProduct(DetaliiComandaBar detalii) {
		
		Integer lowestValue = 0;
		boolean sameProductAddedTwice = false;
		for (DetaliiComandaBar comanda : comandaFinala) {
			if (comanda.getDenumire().equals(detalii.getDenumire())) {
				lowestValue = Integer.parseInt(comanda.getCantitate()) - 1;
				sameProductAddedTwice = true;
			}
		}
		
		if (sameProductAddedTwice) {
			detalii.setCantitate(lowestValue.toString());
		}
		
		if (Integer.parseInt(detalii.getCantitate()) < 0) {
			JOptionPane.showMessageDialog(instance, "Stoc epuizat!!!");
			return;
		}
		
		DefaultTableModel model = (DefaultTableModel) comandaTable.getModel();
		Object[] rowData = new Object[5];
		rowData[0] = detalii.getCategorie();
		rowData[1] = detalii.getDenumire();
		rowData[2] = detalii.getCantitate();
		rowData[3] = detalii.getPret();
		model.addRow(rowData);
		
		comandaFinala.add(detalii);
		
	}
	
	public void clearTableContent() {
		DefaultTableModel model = (DefaultTableModel) comandaTable.getModel();
		model.setNumRows(0);
	}
	
	public void chooseTable() {

		Object[] possibilities = { "1", "2", "3", "4", "5" };
		numarMasa = (String) JOptionPane.showInputDialog(
				this, 
				"Numar de ordine", 
				"Alege numar masa: ",
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				possibilities, 
				"1");
		
	}
	
	private ArrayList<DetaliiComandaBar> getInventoryList() {
		ArrayList<DetaliiComandaBar> detaliiComandaBar = new ArrayList<DetaliiComandaBar>();
		Connection connection = ComandaBar.con;

		String query = "SELECT * FROM  `inventar`";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);

			DetaliiComandaBar divertisment;

			while (rs.next()) {
				divertisment = new DetaliiComandaBar(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				detaliiComandaBar.add(divertisment);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return detaliiComandaBar;
	}
	
	public void Show_ComandaBar_In_JTable() {
		ArrayList<DetaliiComandaBar> list = getInventoryList();
		DefaultTableModel model = (DefaultTableModel) meniuTable.getModel();
		Object[] row = new Object[5];
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCategorie().equals("bar")) {
				row[0] = list.get(i).getCategorie();
				row[1] = list.get(i).getDenumire();
				row[2] = list.get(i).getCantitate();
				row[3] = list.get(i).getPret();
			} else {
				continue;
			}

			model.addRow(row);
		}
	}
	
	public void executeSQlQuery(String query, String message) {
		Connection con = ComandaBar.con;
		Statement st;
		try {
			st = con.createStatement();
			if (st.executeUpdate(query) == 1) {
				// refresh jtable data
				DefaultTableModel model = (DefaultTableModel) meniuTable.getModel();
				model.setRowCount(0);
				Show_ComandaBar_In_JTable();

				JOptionPane.showMessageDialog(null, message);
			} else {
				JOptionPane.showMessageDialog(null, message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void executeUpdate() {
		
		if (numarMasa.equals("") || numarMasa.isEmpty()) {
			chooseTable();
		}
		
		clearTableContent();
		comandaFinala.stream().forEach(
				i -> updateData(
						Integer.parseInt(i.getCantitate())-1, 
						i.getDenumire()));
		instance.dispose();
		
	}
	
	private void updateData(Integer cantitate, String denumire) {
		String sql = "UPDATE `inventar` SET `cantitate`='" + cantitate.toString() + "' WHERE `denumire`='" + denumire + "'";
		executeSQlQuery(sql, "Ati comandat: " + denumire + " la masa cu numarul " + numarMasa + "!!!");
	}
	
}
