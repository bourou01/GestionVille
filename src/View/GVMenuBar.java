package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * 
 * Dessine le menu
 * 
 * @author 
 */
public class GVMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructeur accompagne d'un listener
	 * 
	 * @param actionListener L'ecouteur d'evenemnts sur le menu
	 */
	public GVMenuBar() {
		super();
		this.initMenuBarWithListener(new EcouteurAction(this));
	}
	/**
	 * Fill an area with a given terrain type
	 * 
	 * @param actionListener L'ecouteur d'evenemnts sur le menu
	 *
	 */
	private void initMenuBarWithListener(ActionListener actionListener) {
		JMenu m1=new JMenu("Parametres");
		JMenu ma=new JMenu("Aide");
		/** defini le raccourci clavier sur alt // (par defaut) et p */
		m1.setMnemonic(KeyEvent.VK_P);
		JMenuItem mi1=new JMenuItem("Couleur");
		mi1.setMnemonic(KeyEvent.VK_C) ;
		/** la selection de mi1 */
		mi1.addActionListener(actionListener);
		/** declence une action */
		m1.add(mi1) ;
		/** ajout d un separateur */
		m1.addSeparator();
		JMenu m2=new JMenu("Taille");
		m2.setMnemonic(KeyEvent.VK_T);
		JMenuItem mi2=new JMenuItem("Petit");
		JMenuItem mi3=new JMenuItem("Normal");
		/** defini le raccourci clavier sur ctrl+n */
		mi3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		JMenuItem mi4=new JMenuItem("Grand");
		mi2.addActionListener(actionListener);
		mi3.addActionListener(actionListener);
		mi4.addActionListener(actionListener);
		m2.add(mi2) ; m2.add(mi3) ; m2.add(mi4);
		/** ajout d un menu dans un menu */
		m1.add(m2);
		this.add(m1);
		this.add(ma);
	}
	// Une classe interne qui a accees et tous les attributs privee de la classe la contenant
	public class EcouteurAction implements ActionListener{ 
		public EcouteurAction(GVMenuBar menu){
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
