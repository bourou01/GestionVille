package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;


public abstract class TownElementView extends JPanel {

	private double scale;
	private double borderSize;
	private Color borderColor = Color.red;
	private Image backgroundImage;
	Rectangle frame;
	
	/**
	 * Constructeur par d�faut
	 * 
	 * 
	 */
	public TownElementView() {
		super();
		
		this.scale = 1.0F;
		this.borderSize = 10.0F;
		this.borderColor = Color.red;
		
		super.setLayout(null);
	}
	
	/**
	 * Constructeur par d�faut
	 * 
	 * 
	 */
	public TownElementView (Rectangle frame) {
		this();
		super.setSize(new Dimension(frame.width, frame.height));
		super.setBounds(frame.x, frame.y, frame.width, frame.height);
	}
	
	public void paintBorder(Graphics g, Rectangle baseFrame, int size, Color lineColor) {

		  	Insets thickness = new Insets(size, size, size, size);
		  
		  	int x = baseFrame.x;
		  	int y = baseFrame.y;
		  	int width = baseFrame.width;
		  	int height = baseFrame.height;
		  
		    Color oldColor = g.getColor();
		    g.setColor(lineColor);
		    
		    // top
		    for (int i = 0; i < thickness.top; i++) {
		      g.drawLine(x, y + i, x + width, y + i);
		    }
		    // bottom
		    for (int i = 0; i < thickness.bottom; i++) {
		      g.drawLine(x, y + height - i - 1, x + width, y + height - i - 1);
		    }
		    // right
		    for (int i = 0; i < thickness.right; i++) {
		      g.drawLine(x + width - i - 1, y, x + width - i - 1, y + height);
		    }
		    // left
		    for (int i = 0; i < thickness.left; i++) {
		      g.drawLine(x + i, y, x + i, y + height);
		    }
		    
		    g.setColor(oldColor);
		    
		  }
	
	
}
