package gui.jcomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class MySliderUI extends BasicSliderUI {

    private static float[] fracs = {0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f};
   

    public MySliderUI(JSlider slider) {
 
        super(slider);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle t = trackRect;
        Point2D start = new Point2D.Float(t.x, t.y);
        Point2D end = new Point2D.Float(t.width, t.height);
        Color[] colors = {Color.magenta, Color.blue, Color.cyan,
            Color.green, Color.yellow, Color.red};
        g2d.setColor(Color.gray);
        g2d.fillRect(t.x, t.y, t.width, t.height);
    }

    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle t = thumbRect;
        g2d.setColor(Color.white);
        int tw2 = t.width / 2;
        int xPoints[]={t.x,t.x+t.width-1,t.x+tw2};
        int yPoints[]={t.y,t.y,t.y+t.height};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }
    public void paintMajorTickForHorizSlider(Graphics g, Rectangle tickBounds, int x)  
    {  
     int coordinateX=x;  
      
     if(slider.getOrientation()==JSlider.HORIZONTAL)  
     {  
      //Create color using RGB value(RED=255,GREEN=0,BLUE=0)  
      //You can use Color picker above to get RGB value for color that you want  
      Color majorTickColor=new Color(0,0,153);  
       
      //Set color that will use to draw MAJOR TICK using created color  
      g.setColor(majorTickColor);  
       
      //Draw MAJOR TICK  
      g.drawLine(coordinateX,0,coordinateX,tickBounds.height);  
      g.drawLine(coordinateX-1,0,coordinateX-1,tickBounds.height);  
      g.drawLine(coordinateX+1,0,coordinateX+1,tickBounds.height);  
     }  
    }  
}
