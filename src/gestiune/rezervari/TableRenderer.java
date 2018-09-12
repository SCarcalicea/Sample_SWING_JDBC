package gestiune.rezervari;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8233434291597425565L;
	
	/**
	 * Set color background to table cell
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 

		if (table.isRowSelected(row))
		{
		    if(c.getBackground().equals(Color.GREEN))
		        c.setBackground(Color.RED);
		    else
		        c.setBackground(table.getBackground());
		}

	    return c;
		
	}

}
