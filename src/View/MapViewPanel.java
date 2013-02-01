package View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import Cases.AbstractCase;
import Cases.Caserne;
import Cases.Hospital;
import Cases.Maison;
import Cases.TerrainVague;
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
		
		/** Définit la taille du JscrollPane */
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
				AbstractCase currentCase = maVille.getCase(i, j);
				
				int xGraphic = i*RELATIVE_X_GRAPHIC + SEP;
				int yGraphic = j*RELATIVE_Y_GRAPHIC + SEP;
				
				String imagePath = new String();
				if (currentCase instanceof TerrainVague) {
					imagePath = "res/tv.png";
				} else if (currentCase instanceof Maison) {
					imagePath = "res/mz.png";
				} else if (currentCase instanceof Hospital) {
					imagePath = "res/hs.png";
				} else if (currentCase instanceof Caserne) {
					imagePath = "res/cz.png";
				}
				
				
				CaseView cv = new CaseView(xGraphic,yGraphic, currentCase, imagePath);
				add(cv);
				
			}
		}

		
	}
	
	
}
