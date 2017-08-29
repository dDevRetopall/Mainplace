package findProducts.search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class RssFeedCellRenderer extends DefaultTableCellRenderer {
	public RssFeedCellRenderer() {

	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		 setBorder(noFocusBorder);
//		 if (isSelected)
//	        {
//	            setBackground(table.getSelectionBackground());
//	            setForeground(table.getSelectionForeground());
//	        }
//	        else
//	        {
//	            setBackground(table.getBackground());
//	            setForeground(table.getForeground());
//	        }
	      
		if (value instanceof JPanel) {
			JPanel p = (JPanel) value;
			p.setOpaque(false);
			JButton b = new JButton("More info");
			p.add(b,BorderLayout.SOUTH);
			
			return p;
		}else if(value instanceof ImageIcon){
			setIcon((ImageIcon) value);
			setText("");
			setHorizontalAlignment(JLabel.CENTER);
			return this;
			
		}else{
			setIcon(null);
			
		}
		return this;
	}
	
	
}