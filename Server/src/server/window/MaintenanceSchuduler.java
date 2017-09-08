package server.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import constantesLocalesServidor.ConstantesServer;
import server.main.Main;

public class MaintenanceSchuduler extends JFrame {

	JTextArea ta = new JTextArea();
	JScrollPane sp = new JScrollPane(ta);
	JTextArea ta2 = new JTextArea();
	JScrollPane sp2 = new JScrollPane(ta2);
	JPanel p = new JPanel();
	JPanel pp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel pp4 = new JPanel(new FlowLayout(FlowLayout.LEFT));

	JPanel main = new JPanel();
	JButton b = new JButton("Apply");
	JButton b1 = new JButton("Start countdown");
	JButton b2 = new JButton("Cancel");

	JLabel l = new JLabel("Time to maintenance (in minutes) : ");
	JLabel ll = new JLabel("Aproximately maintenance time (in minutes) : ");
	JLabel l1 = new JLabel("Reason message: ");
	JLabel l2 = new JLabel("Log in forbidden when time goes up: ");

	Font font = new Font("Microsoft Sans Sherif", Font.PLAIN, 12);

	JTextField tf = new JTextField(20);
	JTextField tf1 = new JTextField(20);
	JTextField tf2 = new JTextField(10);
	JTextField tf3 = new JTextField(10);

	JSlider s = new JSlider(JSlider.HORIZONTAL, 0, 120, 0);
	JSlider s2 = new JSlider(JSlider.HORIZONTAL, 0, 180, 0);
	JCheckBox c = new JCheckBox("");
	JCheckBox c1 = new JCheckBox("");
	JCheckBox c2 = new JCheckBox("");
	CountDownWindow cdw;
	public MainWindow mw;

	public MaintenanceSchuduler(MainWindow mw) {
		this.mw = mw;

		this.setSize(600, 400);
		this.setTitle("Maintenance scheduler");
		this.setLocationRelativeTo(mw);
		this.setContentPane(main);
		this.setResizable(false);

		main.setLayout(null);

		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

		GridBagConstraints gbc = new GridBagConstraints();

		b1.setEnabled(false);

		pp.add(l);
		pp.add(s);
		pp3.add(ll);
		pp3.add(s2);
		pp1.add(l1);
		pp1.add(tf1);
		pp2.add(l2);
		pp2.add(c);

		s.setPaintTicks(true);
		s.setFocusable(false);
		s.setMajorTickSpacing(15);
		s.setMinorTickSpacing(5);
		s.setLabelTable(s.createStandardLabels(15));
		s.setPaintLabels(true);
		s.setPreferredSize(new Dimension(250, 100));

		s2.setPaintTicks(true);
		s2.setFocusable(false);
		s2.setMajorTickSpacing(20);
		s2.setMinorTickSpacing(10);
		s2.setLabelTable(s2.createStandardLabels(20));
		s2.setPaintLabels(true);
		s2.setSnapToTicks(true);
		s2.setPreferredSize(new Dimension(250, 100));

		p.setBounds(10, 20, 300, 370);

		p.add(pp, gbc);
		p.add(pp3, gbc);
		p.add(pp1, gbc);
		p.add(pp2, gbc);

		sp.setBounds(320, 20, 203, 130);
		sp2.setBounds(320, 170, 203, 130);

		ta.setFont(font);
		ta.setBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, Color.black), "Warning preview"));
		ta.setOpaque(false);
		ta.setEditable(false);

		ta2.setFont(font);
		ta2.setBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, Color.black), "Log out preview"));
		ta2.setOpaque(false);
		ta2.setEditable(false);

		pp4.add(b);
		pp4.add(b1);
		pp4.setBounds(320, 320, 250, 50);

		main.add(p);
		main.add(sp);
		main.add(sp2);
		main.add(pp4);

		s.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				l.setText(("Time to maintenance (in minutes) : " + s.getValue()));
				updatePreview();
			}
		});
		s2.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				ll.setText("Aproximately maintenance time (in minutes) : " + s2.getValue());
				updatePreview();
			}
		});
		tf1.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				updatePreview();

			}

			@Override
			public void keyReleased(KeyEvent e) {
				updatePreview();

			}

			@Override
			public void keyPressed(KeyEvent e) {
				updatePreview();

			}
		});
		c.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreview();

			}
		});
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updatePreview();

				if (b.getText().equals("Apply")) {
					b.setText("Edit");
					b1.setEnabled(true);
					c.setEnabled(false);
					tf1.setEnabled(false);
					s.setEnabled(false);
					s2.setEnabled(false);
				} else {
					b.setText("Apply");
					b1.setEnabled(false);
					c.setEnabled(true);
					tf1.setEnabled(true);
					s.setEnabled(true);
					s2.setEnabled(true);
				}

			}
		});
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cdw = new CountDownWindow(MaintenanceSchuduler.this.mw, s.getValue(), updatePreview(),
						c.isSelected());
				cdw.setVisible(true);
				Main.actualizarStatusServerEnClientes();
				MaintenanceSchuduler.this.dispose();

			}
		});

	}
	public CountDownWindow getCountdownWindow() {
		return cdw;
	}
	public String[] updatePreview() {
		String[] mensajes = { "", "" };
		int time = s.getValue();
		String timeString = "";
		String m1, m2;
		if (time == 0) {
			timeString = " less than one minute";
		} else {
			timeString = Integer.toString(time) + "  minutes";
		}
		if (tf1.getText() != null && !tf1.getText().isEmpty()) {
			m1 = ("Sorry, the server will be under maintenance in " + timeString + "\n" + "Reason: " + tf1.getText());
		} else {
			m1 = ("Sorry, the server will be under maintenance in " + s.getValue() + " minutes");
		}
		if (c.isSelected()) {
			m2 = ("Sorry, you have log out because the application is under maintenance. \nYou can log in in aproximately "
					+ s2.getValue() + " minutes");
		} else {
			m2 = ("No displayed kick out message.\nKick out users is unselected");
		}
		ta.setText(m1);
		ta2.setText(m2);
		mensajes[0] = m1;
		mensajes[1] = m2;
		return mensajes;
	}

}
