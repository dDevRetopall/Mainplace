package gui.jcomponents;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import gui.jcomponents.tables.RssFeedCellRenderer;
import main.mainApplication.MainApplication;
import products.myProducts.main.CellRenderer;
import products.myProducts.main.ConstantesCategorias;
import utils.imageUtils.ImageUtilsUpdater;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * TableDemo is just like SimpleTableDemo, except that it uses a custom
 * TableModel.
 */
public class TableSearchProducts extends JPanel {
	private boolean DEBUG = false;
	public boolean izquierda = true;
	MyTableModel m = new MyTableModel();
	private JTable table = new JTable(m);
	public ArrayList<Integer> ids = new ArrayList<>();

	public TableSearchProducts() {
		super(new GridLayout(1, 0));

		getTable().setPreferredScrollableViewportSize(new Dimension(500, 70));
		// ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
		getTable().setShowGrid(false);
		getTable().getTableHeader().setReorderingAllowed(false);
		getTable().getTableHeader().setResizingAllowed(false);
		getTable().setSelectionBackground(new Color(192, 192, 192));
		getTable().setSelectionForeground(new Color(0, 0, 0));
		getTable().setBackground(new Color(224, 224, 224));
		getTable().getTableHeader().setOpaque(false);
		table.setIntercellSpacing(new Dimension(0, 1));
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setShowVerticalLines(false);
		// table.setEnabled(false);
		getTable().setRowHeight(105);
		getTable().getTableHeader().setPreferredSize(new Dimension(0, 0));

		getTable().setDefaultRenderer(Object.class, new CellRenderer());
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(getTable());
		getTable().setFocusable(false);

		add(scrollPane);

		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		table.setCellSelectionEnabled(true);
		table.setOpaque(false);

		table.setDefaultRenderer(Object.class, new RssFeedCellRenderer() {
			{
				setOpaque(false);

				setBorder(new LineBorder(Color.BLACK));
				setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
			}

		});

		ListSelectionModel cellSelectionModel = getTable().getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				if (getTable().getSelectedRow() != -1) {
					// MainApplication.pw.b3.setHabilitado(true);
					// MainApplication.pw.b2.setHabilitado(true);
					// MainApplication.pw.b1.setHabilitado(true);

				}
			}

		});

		getTable().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// MainApplication.pw.b3.setHabilitado(true);
				// MainApplication.pw.b2.setHabilitado(true);
				// MainApplication.pw.b1.setHabilitado(true);

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void createNewRow(String usuario, int id, String producto, String informacion, boolean negociable,
			int categoria, int tipo, double price, byte[] imageBytes) {
		String palabra = "Si";
		if (!negociable) {
			palabra = "No";
		}
		ids.add(id);
		DefaultTableModel defaultModel = (DefaultTableModel) getTable().getModel();
		if (imageBytes == null) {
			ImageIcon i = new ImageIcon(getClass().getResource("myproducts.jpg"));
			Image image = i.getImage();
			Image newimg = ImageUtilsUpdater.scale(image, 100, 100);
			ImageIcon imageIcon = new ImageIcon(newimg);
			defaultModel.addRow(new Object[] { imageIcon, producto, price, palabra,
					ConstantesCategorias.categorias[categoria], ConstantesCategorias.tipos[tipo] });
		} else {

			Image i = ImageUtilsUpdater.ByteArrayToImage(imageBytes);
			Image newimg = ImageUtilsUpdater.scale(i, 100, 100);
			ImageIcon imageIcon = new ImageIcon(newimg);
			int row = table.getRowCount();
			String s = producto + usuario;
			JPanel p = new JPanel(new BorderLayout());
			
			
			JLabel titulo = new JLabel(producto);
			titulo.setHorizontalAlignment(JLabel.CENTER);
			titulo.setFont(new Font("Arial", Font.BOLD, 14));
			p.add(titulo,BorderLayout.NORTH);
			
			JLabel texto = new JLabel();
			if (izquierda) {
				defaultModel.addRow(new Object[] { imageIcon, p });
				izquierda = false;
			} else {
				izquierda = true;
				table.setValueAt(imageIcon, row - 1, 2);
				table.setValueAt(p, row - 1, 3);
			}

		}

	}

	public JTable getTable() {
		return table;
	}

	class MyTableModel extends DefaultTableModel {

		public MyTableModel() {
			this.addColumn("Imagen");
			this.addColumn("Product");
			this.addColumn("Image");
			this.addColumn("Product");

		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	}

	public void cleanTable() {
		DefaultTableModel dm = (DefaultTableModel) getTable().getModel();
		int rowCount = dm.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}

	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */

}
