package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
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

	private static final int SMALL =1;
	private static final int MEDIUM =2;
	private static final int BIG =3;
	private int categorieTaille = BIG;
	
	/**
	 * Constructeur accompagn� d'un listener
	 * 
	 * @param actionListener L'ecouteur d'evenemnts sur le menu
	 *
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

	// Une classe interne qui à accès à tous les attributs privé de la classe la contenant
	public class EcouteurAction implements ActionListener{ 
		//private GestionVilleController men;
		private GVMenuBar men;
		
		public EcouteurAction(GVMenuBar menu){
			men=menu;
			}// initialisation de la fenetre 
		
		public void actionPerformed(ActionEvent e) {
			String item = e.getActionCommand(); 
			JPopupMenu pop=new JPopupMenu ( ) ;
			if(item.equals("Couleur"))
			{// action declenche par quel composant 
				ButtonGroup bg1=new ButtonGroup();// selectionner un seul bouton 
				JRadioButton rb1=new JRadioButton("bleu"); 
				rb1.addActionListener(this);// rb1 declenche une action 
				JRadioButton rb2=new JRadioButton("rouge");
				rb2.addActionListener(this);
				JRadioButton rb3=new JRadioButton("jaune");
				rb3.addActionListener(this);
				bg1.add(rb1); 
				bg1.add(rb2); 
				bg1.add(rb3) ;
				JPanel pan=new JPanel();
				men.add(pan);
				pop.add(rb1); 
				pop.add(rb2); 
				pop.add(rb3); 
				pop.show(pan,100,100);// affiche le popup dans le panneau
		}
		else if(item.equals("bleu")) men.setBackground(Color.blue); 
		else if(item.equals("rouge")) men.setBackground(Color.red); 
		else if(item.equals("jaune")) men.setBackground(Color.yellow); 
		else if(item.equals("Petit")) {
			men.setSize(200,200);
			
			categorieTaille =SMALL;
		}
		else if(item.equals("Normal")) {
			men.setSize(500,500);
			categorieTaille =MEDIUM;
		}
		else if(item.equals("Grand")) {
			men.setSize(800,800);
			categorieTaille =BIG;
			
			}
		}
	}
	
	
	
}
