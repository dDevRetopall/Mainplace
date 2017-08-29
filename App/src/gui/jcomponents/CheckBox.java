package gui.jcomponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;

class CheckBoxIcon implements Icon {
	  public void paintIcon(Component component, Graphics g, int x, int y) {
		    AbstractButton abstractButton = (AbstractButton)component;
		    ButtonModel buttonModel = abstractButton.getModel();
		    
		    Color color = buttonModel.isSelected() ?  Color.BLUE : Color.RED;
		    g.setColor(color);
		    
		    g.drawRect(1, 1, 20,20);
		    
		  }
		  public int getIconWidth() {
		    return 20;
		  }
		  public int getIconHeight() {
		    return 20;
		  }
		}

