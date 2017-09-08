package server.window;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constantesLocalesServidor.ConstantesServer;
import server.main.Main;
import tools.datautils.MessageUtils;

public class ConstantsWindow extends JFrame {
	JPanel p;
	JPanel pp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp7 = new JPanel(new FlowLayout(FlowLayout.CENTER));

	JButton b = new JButton("Apply");
	JButton b1 = new JButton("Accept");
	JButton b2 = new JButton("Cancel");

	JLabel l = new JLabel("Expiration file: ");
	JLabel l1 = new JLabel("Transferation file:");
	JLabel l2 = new JLabel("Days to be recent: ");
	JLabel l3 = new JLabel("Expiration time");
	JLabel l4 = new JLabel("Transferation time");
	JLabel l5 = new JLabel("Actual version");
	JLabel l6 = new JLabel("Server available");
	JLabel ll3 = new JLabel("(in days)");
	JLabel ll4 = new JLabel("(in hours)");
	JTextField tf = new JTextField(10);
	JTextField tf1 = new JTextField(10);
	JTextField tf2 = new JTextField(10);
	JTextField tf3 = new JTextField(10);
	JTextField tf4 = new JTextField(10);
	JTextField tf5 = new JTextField(10);

	JCheckBox c = new JCheckBox("");

	public ConstantsWindow(MainWindow mw) {
		b1.setEnabled(false);
		p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		this.setSize(400, 300);
		this.setTitle("Edit constants");
		this.setLocationRelativeTo(mw);
		this.setContentPane(p);

		this.setResizable(false);
		GridBagConstraints gbc = new GridBagConstraints();
		pp.add(l);
		pp.add(tf);
		pp1.add(l1);
		pp1.add(tf1);
		pp2.add(l2);
		pp2.add(tf2);
		pp3.add(l3);
		pp3.add(tf3);
		pp3.add(ll3);
		pp4.add(l4);
		pp4.add(tf4);
		pp4.add(ll4);
		pp5.add(l5);
		pp5.add(tf5);
		pp6.add(l6);
		pp6.add(c);

		pp7.add(b);
		pp7.add(b1);
		pp7.add(b2);

		p.add(pp, gbc);
		p.add(pp1, gbc);
		p.add(pp2, gbc);
		p.add(pp3, gbc);
		p.add(pp4, gbc);
		p.add(pp5, gbc);
		p.add(pp6, gbc);
		p.add(pp7, gbc);

		tf.setText(ConstantesServer.nameCooldownExpirationFile);
		tf1.setText(ConstantesServer.nameCooldownTransferationFile);
		tf2.setText(Integer.toString(ConstantesServer.requiredDaysToBeRecent));
		tf3.setText(Integer.toString(ConstantesServer.timeToExpireProduct));
		tf4.setText(Integer.toString(ConstantesServer.timeToTransferProduct));
		tf5.setText(ConstantesServer.requiredVersion);
		if (ConstantesServer.serverOperational) {
			c.setSelected(true);
		} else {
			c.setSelected(false);
		}

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (b.getText().equals("Apply")) {
					tf.setEnabled(false);
					tf1.setEnabled(false);
					tf2.setEnabled(false);
					tf3.setEnabled(false);
					tf4.setEnabled(false);
					tf5.setEnabled(false);
					c.setEnabled(false);
					b.setText("Edit");
					b1.setEnabled(true);
				} else {
					tf.setEnabled(true);
					tf1.setEnabled(true);
					tf2.setEnabled(true);
					tf3.setEnabled(true);
					tf4.setEnabled(true);
					c.setEnabled(true);
					b.setText("Apply");
					b1.setEnabled(false);
				}
			}
		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConstantsWindow.this.dispose();
			}
		});
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(ConstantsWindow.this,
						"Are you sure you want to change constants. Something could start not working");
				if (respuesta == 0) {
					ConstantesServer.nameCooldownExpirationFile = tf.getText();
					ConstantesServer.nameCooldownTransferationFile = tf1.getText();
					ConstantesServer.requiredDaysToBeRecent = Integer.parseInt(tf2.getText());
					ConstantesServer.timeToExpireProduct = Integer.parseInt(tf3.getText());
					ConstantesServer.timeToTransferProduct = Integer.parseInt(tf4.getText());
					ConstantesServer.requiredVersion = tf5.getText();
					boolean resultado;
					if (c.isSelected()) {
						resultado = true;
					} else {
						resultado = false;
					}
					ConstantesServer.serverOperational = resultado;
					ConstantsWindow.this.dispose();
				}
				MessageUtils.logn("-------------------------------------------------");
				MessageUtils.logn("Constants have been edited");
				MessageUtils.logn("Cooldown Expiration File : " + ConstantesServer.nameCooldownExpirationFile);
				MessageUtils.logn("Cooldown Transferation File : " + ConstantesServer.nameCooldownTransferationFile);
				MessageUtils.logn("Required days to be recent : " + ConstantesServer.requiredDaysToBeRecent);
				MessageUtils.logn("Time to expire product : " + ConstantesServer.timeToExpireProduct);
				MessageUtils.logn("Time to transfer product : " + ConstantesServer.timeToTransferProduct);
				MessageUtils.logn("Required version : " + ConstantesServer.requiredVersion);
				MessageUtils.logn("Server operational : " + ConstantesServer.serverOperational);
				MessageUtils.logn("-------------------------------------------------");
				
				Main.actualizarStatusServerEnClientes();
				if (ConstantesServer.serverOperational) {
					mw.l2.setText("AVAILABLE");
					mw.l2.setForeground(new Color(0, 153, 0));

				} else {
					mw.l2.setText("NOT AVAILABLE");
					mw.l2.setForeground(new Color(204, 0, 0));

				}

			}
		});
	}
}
