package Controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

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
	 * Constructeur par défaut
	 */
	public GestionVilleController() {
		/** Configure la vue principale */
		this.configureFrame();
		/** Dessine le menu principal */
		this.drawMenu();
		
		/** Ajoute les differentes panels dans la vue principale*/
		this.addPanels();
		
		/** Affiche la vue */
		//super.setVisible(true);
	}
	private void configureFrame() {
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setTitle("Gestion Ville");
		super.setSize(1300,780);
		super.setResizable(true);
	}
	private void drawMenu() {
		GVMenuBar mb1 = new GVMenuBar();
		/** une JFrame a un JMenuBar par defaut mais pas utilise */
		setJMenuBar(mb1);
	}
	/**
	 * Ajouter les sous vues
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
		 
		 /** On construit enfin notre séparateur */
		 split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.debugViewPanel, this.consoleViewScrollPane);
		 
		 /** On place le premier séparateur */
		 split.setDividerLocation(150);
		    
		 split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.mapViewScrollPane , split);
		 /** On place le deuxième séparateur*/
		 split2.setDividerLocation(850);
		 
		 /** On le passe ensuite au content pane de notre objet Fenetre placé au centre 
		     pour qu'il utilise tout l'espace disponible */
		 this.getContentPane().add(split2, BorderLayout.CENTER);	 
	}
}
