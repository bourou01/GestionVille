package View;


import java.awt.Color;
import java.awt.Font;
import java.io.PrintStream;

import javax.swing.JTextArea;

public class ConsoleViewPanel extends JTextArea {
	private static final long serialVersionUID = 1L;
	static private Color BG_COLOR = Color.black;
	public PrintStream out;	
	/**
	 * Constructeur par défaut
	 */
	public ConsoleViewPanel() {
		super();
		this.setBackground(BG_COLOR);
		/** draw the view*/
		this.drawView();
		this.initOutputStream();	
	}
	/**
	 * Dessine la vue
	 */
	private void drawView() {
		this.setText("\n");
		Font font = new Font("SanSerif", Font.BOLD, 12);
		this.setFont(font);
		this.setForeground(Color.white);
		this.setEditable(false);
	}
	public void initOutputStream() {
		 this.out = new PrintStream(
				new TextAreaOutputStream( this
				) );
		 System.setOut( out );
		 System.setErr( out );
	}
	@Override
	public void append(String text) {
		super.append(text);
		this.setCaretPosition(this.getCaretPosition()+text.length());
		}
}
