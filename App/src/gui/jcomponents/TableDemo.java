package gui.jcomponents;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cliente.net.connection.MainCliente;
import main.mainApplication.MainApplication;
import products.myProducts.main.CellRenderer;
import products.myProducts.main.ConstantesCategorias;
import utils.imageUtils.ImageUtilsUpdater;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
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
public class TableDemo extends JPanel {
	private boolean DEBUG = false;
	MyTableModel m = new MyTableModel();
	private JTable table = new JTable(m);
	public ArrayList<Integer> ids = new ArrayList<>();

	public TableDemo() {
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

		getTable().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		// table.setEnabled(false);
		getTable().setRowHeight(105);
		getTable().getTableHeader().setPreferredSize(new Dimension(800, 40));

		getTable().setDefaultRenderer(Object.class, new CellRenderer());
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(getTable());
		getTable().setFocusable(false);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
		getTable().setDefaultRenderer(String.class, dtcr);
		// Add the scroll pane to this panel.
		add(scrollPane);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			protected void setValue(Object value) {
				if (value instanceof ImageIcon) {
					setIcon((ImageIcon) value);
					setText("");
					setHorizontalAlignment(JLabel.CENTER);
				} else {
					setIcon(null);
					super.setValue(value);
				}
			}
		});

		ListSelectionModel cellSelectionModel = getTable().getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (getTable().getSelectedRow() != -1) {
					MainCliente.requestStatusViewsProductById(ids.get(getTable().getSelectedRow()));
					
					MainApplication.pw.b4.setHabilitado(true);
					MainApplication.pw.b3.setHabilitado(true);
					MainApplication.pw.b2.setHabilitado(true);
					MainApplication.pw.b1.setHabilitado(true);
					MainApplication.pw.setInfoPanelVisible(true);
				}
			}

		});

		getTable().addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				MainApplication.pw.b4.setHabilitado(true);
				MainApplication.pw.b3.setHabilitado(true);
				MainApplication.pw.b2.setHabilitado(true);
				MainApplication.pw.b1.setHabilitado(true);
				MainApplication.pw.setInfoPanelVisible(true);

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void createNewRow(int id, String producto, String informacion, boolean negociable, int categoria, int tipo,
			double price, byte[] imageBytes) {
		String palabra = "Si";
		if (!negociable) {
			palabra = "No";
		}
		ids.add(id);
		DefaultTableModel defaultModel = (DefaultTableModel) getTable().getModel();
		if (imageBytes == null) {
			ImageIcon i = new ImageIcon(getClass().getResource("myproducts.jpg"));
			Image image = i.getImage();
			Image newimg = ImageUtilsUpdater.scale(image, 100,100);
			ImageIcon imageIcon = new ImageIcon(newimg);
			defaultModel.addRow(new Object[] { imageIcon, producto, price, palabra,
					ConstantesCategorias.categorias[categoria], ConstantesCategorias.tipos[tipo] });
		} else {

			Image i = ImageUtilsUpdater.ByteArrayToImage(imageBytes);
			Image newimg = ImageUtilsUpdater.scale(i, 100,100);
			ImageIcon imageIcon = new ImageIcon(newimg);

			defaultModel.addRow(new Object[] { imageIcon, producto, price, palabra,
					ConstantesCategorias.categorias[categoria], ConstantesCategorias.tipos[tipo] });

		}

	}

	public JTable getTable() {
		return table;
	}

	class MyTableModel extends DefaultTableModel {

		public MyTableModel() {
			this.addColumn("Imagen");
			this.addColumn("Product");
			this.addColumn("Price");
			this.addColumn("Negociable");
			this.addColumn("Categoria");
			this.addColumn("Tipo");

		}

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	}

	public void editProduct(int row, String productName, String informacion, boolean negociable, int tipo,
			int categoria, double price) {
		DefaultTableModel defaultModel = (DefaultTableModel) getTable().getModel();
		table.setValueAt(productName, row, 1);
		table.setValueAt(price, row, 2);
		String palabra = "Si";
		if (!negociable) {
			palabra = "No";
		}
		table.setValueAt(palabra, row, 3);
		table.setValueAt(ConstantesCategorias.categorias[categoria], row, 4);
		table.setValueAt(ConstantesCategorias.tipos[tipo], row, 5);

	}

	public void removeProduct(int row) {
		((DefaultTableModel) table.getModel()).removeRow(row);

	}

	public void cleanTable() {
		DefaultTableModel dm = (DefaultTableModel) getTable().getModel();
		int rowCount = dm.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}
		MainApplication.pw.b1.setHabilitado(false);
		MainApplication.pw.b2.setHabilitado(false);
		MainApplication.pw.b3.setHabilitado(false);
		MainApplication.pw.b4.setHabilitado(false);
		MainApplication.pw.setInfoPanelVisible(false);


	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */

}
