package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Debug.Log;


public abstract class TownElementView extends JPanel {

	private double scale;
	private double borderSize;
	private Color borderColor = Color.red;
	private Image backgroundImage;
	Rectangle frame;
	
	/**
	 * Constructeur par défaut
	 * 
	 * 
	 */
	public TownElementView() {
		super();
		
		this.scale = 1.0F;
		this.borderSize = 10.0F;
		this.borderColor = Color.red;
		//this.setBackgroundImage("res/grass.png");
		//super.setLayout(null);
	}
	
	  public void setBackgroundImage(String path) {
		  BufferedImage img = TownElementView.loadImage(path);
		  if (img == null)
			  return;
		  this.backgroundImage = img;
		  
		  Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		  setPreferredSize(size);
		  setMinimumSize(size);
		  setMaximumSize(size);
		  setSize(size);
		  
		  setLayout(null);
	  }
	
	/**
	 * Constructeur par défaut
	 * 
	 * 
	 */
	public TownElementView (Rectangle frame) {
		this();
		super.setSize(new Dimension(frame.width, frame.height));
		super.setBounds(frame.x, frame.y, frame.width, frame.height);
	}
	
	public TownElementView(Rectangle frame, String imagePath) {
		this();
		this.setBackgroundImage(imagePath);
		super.setSize(new Dimension(frame.width, frame.height));
		super.setBounds(frame.x, frame.y, frame.width, frame.height);
	}
	
	static public BufferedImage loadImage(String path) {
		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File(path));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return myPicture;
	}
	   
	public void drawBackgroud(Graphics g) {
		g.drawImage(this.backgroundImage, 0, 0, null);
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
