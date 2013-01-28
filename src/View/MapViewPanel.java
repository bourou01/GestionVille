package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;

import Main.GestionVille;
import Villes.Ville;


public class MapViewPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int SEP = 0;
	private static Color SEP_COLOR = Color.white;
	
	private static int RELATIVE_X_GRAPHIC = (CaseView.WIDTH + SEP);
	private static int RELATIVE_Y_GRAPHIC = (CaseView.HEIGHT + SEP);
	
	private Ville maVille = GestionVille.getVille();

	/**
	 * Constructeur par défaut
	 * 
	 * 
	 */
	public MapViewPanel() {
		super();
		super.setLayout(null);

		
		super.setBackground(SEP_COLOR);
		
		/** Définit la tainne du JscrollPane */
		super.setPreferredSize(new Dimension(
				maVille.getLignes()*RELATIVE_X_GRAPHIC + SEP,
				maVille.getColonnes()*RELATIVE_Y_GRAPHIC + SEP
				));
		
		
		this.drawMap();
	}
	
	public void drawMap() {
		this.removeAll();
		/** Déssine la ville avec une instance de la ville courante */
		for (int i=0; i<maVille.getLignes(); i++) {
			for (int j=0; j<maVille.getColonnes(); j++) {
				
				int xGraphic = i*RELATIVE_X_GRAPHIC + SEP;
				int yGraphic = j*RELATIVE_Y_GRAPHIC + SEP;
				
				CaseView cv = new CaseView(xGraphic,yGraphic, maVille.getCase(i, j));
				add(cv);
				
			}
		}

		
	}
	
	
}
