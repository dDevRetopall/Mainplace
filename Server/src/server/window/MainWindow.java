package server.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import constantes.NetConstants;
import constantesLocalesServidor.ConstantesServer;
import server.main.Main;
import server.net.clientHandler.Cliente;
import tools.datautils.FileUtils;
import tools.datautils.MessageUtils;
import utils.threadUtils.CooldownLoader;

public class MainWindow extends JFrame {
	JPanel p = new JPanel(new BorderLayout());
	JButton stop = new JButton("Stop server");
	JButton connectionManager = new JButton("Refuse new connections");
	JButton restart = new JButton("Restart server");
	public JTextArea ta = new JTextArea(25, 60);
	JButton b = new JButton("Save cooldowns");
	JButton b2 = new JButton("Change settings");
	JButton b3 = new JButton("Maintenance scheduler");
	JButton b4 = new JButton("Maintenance");
	JButton b5 = new JButton("Save settings");
	JPanel pCenter = new JPanel(new FlowLayout());
	JPanel pSouth = new JPanel(new FlowLayout());
	public JScrollPane ps = new JScrollPane(ta);
	Font font = new Font("Microsoft Sans Sherif", Font.PLAIN, 12);
	JLabel l = new JLabel("Server status: ");
	public JLabel l2 = new JLabel("AVAILABLE ");
	JPanel information = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel spPanel= new JPanel(new FlowLayout());
	JProgressBar pb = new JProgressBar();
	MaintenanceSchuduler ms;
	protected Maintenance m;
	
	JPanel statusContent = new JPanel(new FlowLayout());
	JPanel statusTitle = new JPanel(new FlowLayout());
	JPanel statusBar = new JPanel(new FlowLayout());
	public MainWindow() {

		this.setSize(850, 685);
		this.setTitle("Manage Server");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(p);

		ta.setEditable(false);
		ta.setFont(font);

		spPanel.add(ps);
		pCenter.add(statusTitle);
		pCenter.add(statusBar);
		pCenter.add(statusContent);
		pCenter.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), "Status application"));
	
		statusBar.add(pb);
		
		statusTitle.add(information);
		
		statusContent.add(stop);
		statusContent.add(connectionManager);
		statusContent.add(restart);
		
		restart.setFocusable(false);
		stop.setFocusable(false);
		connectionManager.setFocusable(false);
		
		b.setFocusable(false);
		b2.setFocusable(false);
		b3.setFocusable(false);
		b4.setFocusable(false);

		

		pb.setStringPainted(true);
		pb.setString("");
		pb.setPreferredSize(new Dimension(700, 30));
		pb.setIndeterminate(true);
		pb.setForeground(new Color(0, 200, 0));

		l.setFont(font.deriveFont(Font.BOLD, 20));
		l2.setFont(font.deriveFont(Font.BOLD, 20));

		ps.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		pSouth.add(b);
		pSouth.add(b5);
		pSouth.add(b2);
		pSouth.add(b3);
		pSouth.add(b4);
		pSouth.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), "Maintenance settings"));
		
		p.add(spPanel, BorderLayout.NORTH);
		p.add(pCenter, BorderLayout.CENTER);
		p.add(pSouth, BorderLayout.SOUTH);

		l.setText("Server status: accepting new connections - running");

		information.add(l);
		information.add(l2);
		if (ConstantesServer.serverOperational) {
			l2.setText("AVAILABLE");
			l2.setForeground(new Color(0, 153, 0));

		} else {
			l2.setText("NOT AVAILABLE");
			l2.setForeground(new Color(204, 0, 0));
		}

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				MessageUtils.logn("Closing window");
				FileUtils.saveSettings();
				MessageUtils.logn("Stopping server");
				CooldownLoader.saveCooldownsTransferation(Main.onCooldownProducts);
				CooldownLoader.saveCooldownsExpiration(Main.onExpirationTimeProducts);
				MessageUtils.logn("Server stopped");
				super.windowClosing(e);
			}

		});
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CooldownLoader.saveCooldownsTransferation(Main.onCooldownProducts);
				CooldownLoader.saveCooldownsExpiration(Main.onExpirationTimeProducts);

			}
		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(MainWindow.this,
						"Are you sure you want to change constants. Something could start not working");
				if (respuesta == 0) {
					ConstantsWindow cw = new ConstantsWindow(MainWindow.this);
					cw.setVisible(true);
				}
			}
		});
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(MainWindow.this,
						"Are you sure you want to perform a maintenance update?");
				if (respuesta == 0) {
					ms = new MaintenanceSchuduler(MainWindow.this);
					ms.setVisible(true);
				}
			}
		});
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				m = new Maintenance(MainWindow.this);
				m.setVisible(true);
			}

		});
		b5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FileUtils.saveSettings();
				
			}
		});
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				l.setText("Server status: stopped");
				pb.setIndeterminate(false);
				pb.setValue(100);
				pb.setForeground(new Color(200, 0, 0));
				stop.setEnabled(false);
				connectionManager.setEnabled(false);
				MessageUtils.logn("Closing server connections");
				l2.setText("");
				b2.setEnabled(false);
				b3.setEnabled(false);
				for (Cliente c : Main.clientes) {
					try {
						c.s.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				try {
					Main.ss.close();
					MessageUtils.logn("Server succesfully closed");
				} catch (IOException e1) {
					MessageUtils.logn("Error cerrando el servidor");
					e1.printStackTrace();
				}

			}
		});
		connectionManager.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (connectionManager.getText().equals("Refuse new connections")) {
					l.setText("Server status: refusing new connections - running");
					pb.setIndeterminate(true);
					Main.aceptarConexiones = false;
					Main.actualizarStatusServerEnClientes();
					connectionManager.setText("Accept new connections");
				} else {
					l.setText("Server status: accepting new connections - running");
					pb.setIndeterminate(true);
					connectionManager.setText("Refuse new connections");
					Main.aceptarConexiones = true;
					Main.actualizarStatusServerEnClientes();
				}

			}
		});

		restart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Main.ss.close();
					MessageUtils.logn("Servidor cerrado correctamente");
				} catch (IOException e1) {
					MessageUtils.logn("Error cerrando el servidor");
					e1.printStackTrace();
				}

				try {
					Main.restartApplication(null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

	}

	public MaintenanceSchuduler getSchedulerWindow() {
		return ms;
	}
}
