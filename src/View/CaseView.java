package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Cases.AbstractCase;
import Debug.Log;
import Main.GestionVille;
import Persons.AbstractPerson;
import Villes.Ville;

public class CaseView extends TownElementView {

	static public int HEIGHT = 180;
	static public int WIDTH = 180;
	static public int BORDER_SIZE = 8;
	
	public static int SEP = 0;
	
	private static int RELATIVE_X_GRAPHIC = (PersonView.PERSON_WIDTH + SEP);
	private static int RELATIVE_Y_GRAPHIC = (PersonView.PERSON_HEIGHT + SEP);
	
	public static Color BG_COLOR = Color.black;
	
	private AbstractCase abstractCase;
	private Color contaminationLevelColor = Color.white;
	
	private Ville maVille = GestionVille.getVille();
	
	
	
	/**
	 * Constructeur par d�faut
	 * 
	 * 
	 */
	public CaseView() {
		super();
	}
	
	/**
	 * Constructeur avec frame
	 * 
	 * 
	 */
	public CaseView(int x, int y, AbstractCase caseToDraw) {
		super(new Rectangle(x, y, WIDTH, HEIGHT));
		
		this.abstractCase = caseToDraw;
		this.updateDatas();
		
		super.setBackground(BG_COLOR);
		BG_COLOR = super.getBackground();
		
		this.drawPersons();
	}
	
	private void drawPersons() {
		super.removeAll();
		/** D�ssine la ville avec une instance de la ville courante */
		int index = 0;
		int maxPersons = this.abstractCase.getVillageois().size();
		
		for (int i=1; i<PersonView.NB_PERSONS; i++) {
			for (int j=0; j<PersonView.NB_PERSONS; j++) {
				
				int xGraphic = i*RELATIVE_X_GRAPHIC + SEP + BORDER_SIZE;
				int yGraphic = j*RELATIVE_Y_GRAPHIC + SEP + BORDER_SIZE;
				
				if ( index< maxPersons) {
					AbstractPerson currentPerson = this.abstractCase.getVillageois().elementAt(index);
					PersonView pv = new PersonView(yGraphic,xGraphic, currentPerson);
					add(pv);
					
					index++;
				}
			}
		}
	}
	
	private void updateDatas() {
		int contamination = (int)( this.abstractCase.getNiveauContamination() * 255 *(5/2) );
		contamination = Math.min(255, contamination);
		this.contaminationLevelColor = new Color(255, 
												 255-contamination,
												 255-contamination);
		
		this.drawPersons();
		
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.updateDatas();
        
        super.paintBorder(g, new Rectangle(0,0,HEIGHT,WIDTH), BORDER_SIZE, this.contaminationLevelColor);
        
        g.setColor(Color.white);
        g.drawString(this.abstractCase.toString() + this.abstractCase.getPosition(), BORDER_SIZE + 0, BORDER_SIZE + 11);
    }
}
