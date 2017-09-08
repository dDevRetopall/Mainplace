package server.window;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import constantesLocalesServidor.ConstantesServer;
import server.main.Main;

public class CountDownWindow extends JFrame {
	JPanel main = new JPanel();
	JLabel phase = new JLabel("Starting");
	JLabel time = new JLabel("25");
	JProgressBar pb = new JProgressBar();
	JPanel center = new JPanel();
	JPanel buttons = new JPanel(new FlowLayout());
	Font font = new Font("Microsoft Sans Sherif", Font.BOLD, 24);
	JButton cancel = new JButton("Cancel");
	private boolean stop = false;
	int countdownSeconds;
	int totalSeconds;
	private MainWindow mw;
	private int countdown;
	private String[] messages;
	private boolean logout;

	public CountDownWindow(MainWindow mw, int countdown, String[] messages, boolean logout) {

		this.mw = mw;
		this.countdown = countdown;
		this.messages = messages;
		this.logout = logout;

		GridBagConstraints gbc = new GridBagConstraints();

		this.setAlwaysOnTop(true);
		this.setSize(400, 300);
		this.setTitle("Countdown");
		this.setLocationRelativeTo(mw);
		this.setContentPane(main);
		this.setResizable(false);

		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

		main.setLayout(null);

		phase.setFont(font.deriveFont(Font.BOLD, 18));

		time.setFont(font);
		time.setText("Executing tasks");

		center.setBounds(50, 50, 300, 100);
		center.add(phase, gbc);
		center.add(time, gbc);
		center.add(pb, gbc);
		center.setBackground(Color.WHITE);

		buttons.add(cancel);
		buttons.setBounds(50, 210, 200, 50);

		main.add(center);
		main.add(buttons);

		totalSeconds = countdown * 60;
		countdownSeconds = countdown * 60;
		phase.setText("Sending warning message to all connected users");

		pb.setIndeterminate(true);
		pb.setStringPainted(true);
		pb.setString("");
		pb.setForeground(new Color(0, 200, 0));

		Main.enviarWarningATodos(messages[0]);

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!isStop()) {

					try {
						Thread.sleep(1000);

					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					if (isStop() != true) {
						pb.setIndeterminate(false);
						phase.setText("Executing countdown");
						if (countdownSeconds == 0) {
							pb.setValue(100);

							if (logout) {
								setStop(true);
								phase.setText("Executing tasks");
								time.setText("Kicking out all accounts");
								Main.logoutAllAccounts(messages[1], true);
								phase.setForeground(new Color(0, 153, 0));
								phase.setText("Completed");
								time.setText("");
								cancel.setEnabled(false);
							} else {
								phase.setText("Finished");
								cancel.setEnabled(false);
								phase.setForeground(new Color(0, 153, 0));
								time.setText("No kick out accounts");
							}
							Main.actualizarStatusServerEnClientes();
						} else {
							countdownSeconds--;

							updateTime(countdownSeconds);
						}
					}
				}

			}
		});
		t.start();
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setStop(true);
				pb.setValue(100);
				pb.setStringPainted(true);
				pb.setForeground(new Color(200, 0, 0));

				pb.setString("");
				Main.enviarWarningATodos("The server maintenance have been cancelled");
				phase.setForeground(new Color(153, 0, 0));
				phase.setText("Cancelled");
				time.setText("");
				cancel.setEnabled(false);

			}
		});

	}

	public void updateTime(int count) {
		time.setText(count + " seconds left");
		pb.setValue((int) ((count * 100) / totalSeconds));
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}
}
