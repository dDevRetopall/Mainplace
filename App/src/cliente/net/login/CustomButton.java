package cliente.net.login;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import main.mainApplication.MainApplication;

public class CustomButton extends JButton {

	/**
	 * 
	 */
	int press = 0;
	private static final long serialVersionUID = 1L;
	private Color bgHoverColor;
	private Color bgPressedColor;

	public CustomButton() {
		super();
		super.setContentAreaFilled(false);
		setBackground(new Color(192, 192, 192));
		setBgHoverColor(new Color(162, 162, 162));
		setBgPressedColor(new Color(92, 92, 92));
		setHorizontalTextPosition(SwingConstants.CENTER);
		setFocusPainted(false);
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		setForeground((Color.BLACK));

		super.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				press = 1;

				super.mouseClicked(e);
			}

		});
		super.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {

				press = 0;

				super.mouseClicked(e);
			}

		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isRollover()) {
			g.setColor(getBgHoverColor());
			setBorder(BorderFactory.createLineBorder(Color.WHITE));

		} else if (getModel().isPressed()) {
			g.setColor(getBgPressedColor());

		} else {
			this.setBackground(new Color(192, 192, 192));
			g.setColor(getBackground());

		}
		if (press >= 1) {
			g.setColor(getBgPressedColor());
			press++;

		}

		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	public Color getBgPressedColor() {
		return bgPressedColor;
	}

	public void setBgPressedColor(Color bgPressedColor) {
		this.bgPressedColor = bgPressedColor;
	}

	public Color getBgHoverColor() {
		return bgHoverColor;
	}

	public void setBgHoverColor(Color bgHoverColor) {
		this.bgHoverColor = bgHoverColor;
	}

}
