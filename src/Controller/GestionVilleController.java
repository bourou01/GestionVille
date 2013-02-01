package Controller;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import Debug.Log;
import View.CaseView;
import View.ConsoleViewPanel;
import View.DebugViewPanel;
import View.GVMenuBar;
import View.MapViewPanel;


public class GestionVilleController extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int SMALL =1;
	private static final int MEDIUM =2;
	private static final int BIG =3;
	private int categorieTaille = BIG;
	private int posX=50;
	private int posY=50;
	private int nbrc=1;
	
	
	/** Panels */
	public JScrollPane mapViewScrollPane;
	public JScrollPane consoleViewScrollPane;
	public MapViewPanel mapViewPanel;
	public ConsoleViewPanel consoleViewPanel;
	public DebugViewPanel debugViewPanel;
	
	/** Splits */
	private JSplitPane split, split2;
	
	/** Ville */
	
	/**
	 * Constructeur par dŽfaut
	 */
	public GestionVilleController() {
		
		/** Configure la vue principale */
		this.configureFrame();
		
		/** Dessine le menu principal */
		this.drawMenu();
		
		/** Ajoute les differentes panels dans la vue principale*/
		this.addPanels();
		
		/** Affiche la vue */
		super.setVisible(true);
	}
	
	/**
	 * Constructeur par dŽfaut
	 * 
	 * 
	 */
	private void configureFrame() {
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setTitle("Gestion Ville");
		
		super.setSize(1300,780);
		super.addMouseListener(new EcouteurSouris());
		
		super.setResizable(true);
	}
	
	/**
	 * Tout pour dessiner le menu
	 * 
	 * 
	 * 
	 */
	private void drawMenu() {
		GVMenuBar mb1 = new GVMenuBar();
		/** une JFrame a un JMenuBar par defaut mais pas utilise */
		setJMenuBar(mb1);
	}
	
	/**
	 * Ajouter les sous vues
	 * 
	 * 
	 * 
	 */
	private void addPanels() {
		/** Construction et Initialisation*/
		 this.mapViewPanel = new MapViewPanel();
		 this.mapViewScrollPane = new JScrollPane(this.mapViewPanel);
		 this.mapViewScrollPane.getVerticalScrollBar().setUnitIncrement(CaseView.WIDTH/2);
		 
		 /** La console */
		 this.consoleViewPanel = new ConsoleViewPanel();
		 this.consoleViewScrollPane = new JScrollPane(this.consoleViewPanel);

		 /** La vue d'informations */
		 this.debugViewPanel = new DebugViewPanel();
		 this.debugViewPanel.gestionVilleController = this;
		 
		 /** On construit enfin notre sŽparateur */
		 split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.debugViewPanel, this.consoleViewScrollPane);
		 
		 /** On place le premier sŽparateur */
		 split.setDividerLocation(150);
		    
		 split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.mapViewScrollPane , split);
		 /** On place le deuxime sŽparateur*/
		 split2.setDividerLocation(850);
		 
		 /** On le passe ensuite au content pane de notre objet Fenetre placŽ au centre 
		     pour qu'il utilise tout l'espace disponible */
		 this.getContentPane().add(split2, BorderLayout.CENTER);	 
	}

	
	
	
	
	
	
	

	public class EcouteurSouris implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");
			int nb=e.getClickCount();
			int x =e.getX();
			int y =e.getY();
			int sx = e.getXOnScreen();
			int sy = e.getYOnScreen();
			System.out.println("("+x+","+y+") ("+sx+","+sy+") nb="+nb);
			int btn = e.getButton();
			int modif = e.getModifiers();
			System.out.println("numero bouton : "+btn+" ("+modif+")");
			System.out.println(InputEvent.CTRL_DOWN_MASK);
			if ((btn==1) && ((modif & 2)>0)){
				posX=x; posY=y; nbrc = nb;
				repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("mousePressed");
			int x =e.getX();
			int y =e.getY();
			int sx = e.getXOnScreen();
			int sy = e.getYOnScreen();
			System.out.println("("+x+","+y+") ("+sx+","+sy+")");
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("mouseReleased");
			System.out.println("mousePressed");
			int x =e.getX();
			int y =e.getY();
			int sx = e.getXOnScreen();
			int sy = e.getYOnScreen();
			System.out.println("("+x+","+y+") ("+sx+","+sy+")");
		}
		
	}
	
	
	
	
	
}
