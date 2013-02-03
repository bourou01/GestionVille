package View;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Main.GestionVille;
import Persons.AbstractPerson;
import Persons.Citoyen;
import Persons.Medecin;
import Persons.Pompier;

public class PersonView extends TownElementView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int NB_PERSONS = 5;
	public static int PERSON_WIDTH = (int)((double)((CaseView.WIDTH - CaseView.BORDER_SIZE * 2)/NB_PERSONS));
	public static int PERSON_HEIGHT = (int)((double)((CaseView.HEIGHT - CaseView.BORDER_SIZE * 2)/NB_PERSONS));
	
	static int BORDER_SIZE = 9;
	static Color BORDER_COLOR = Color.lightGray;
	
	static int JAUGE_HEIGHT = (int)((BORDER_SIZE - 3)/2);
	static int JAUGE_WEIGH = PERSON_WIDTH - 2*2;
	
	static Font PERSON_LABEL_FONT = new Font("TimesRoman",Font.BOLD,11);
	static BasicStroke BORDER_DEAD_LINE = new BasicStroke(1);
	private int niveauContaminationJauge;
	private int nombreJourMaladeJauge;
	
	private AbstractPerson abstractPerson;
	
	/**
	 * Constructeur par défaut
	 * 
	 * 
	 */
	public PersonView() {
		super();
	}
	
	/**
	 * Constructeur avec frame
	 * 
	 */
	public PersonView(int x, int y, AbstractPerson personToDraw, String imagePath) {
		 
		super(new Rectangle(x,y,PERSON_WIDTH,PERSON_HEIGHT));

		this.abstractPerson = personToDraw;

		if (abstractPerson instanceof Citoyen)
			this.setBackground(Color.gray);
		else if (abstractPerson instanceof Pompier)
			this.setBackground(Color.red);
		else if (abstractPerson instanceof Medecin)
			this.setBackground(Color.blue);

		this.updateDraws();
		
	}
	
	
	private void updateDraws() {
		this.niveauContaminationJauge = (int)(this.abstractPerson.getNiveauContamination()*JAUGE_WEIGH);
		this.nombreJourMaladeJauge = (int) ((double)this.abstractPerson.getNombreJourMalade()*JAUGE_WEIGH / GestionVille.STEPS);

	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        
        this.updateDraws();
        
        super.paintBorder(g, new Rectangle(0,0,PERSON_HEIGHT,PERSON_WIDTH), BORDER_SIZE, CaseView.BG_COLOR);
        
        g.setColor(Color.green);
        g.fillRect (2, 0, JAUGE_WEIGH , JAUGE_HEIGHT);
        
        
        if (this.abstractPerson.isMalade()) 
        	g.setColor(Color.red);
        else
        	g.setColor(Color.blue);
        
        g.fillRect(2, 0, this.niveauContaminationJauge, JAUGE_HEIGHT);
        
        g.setColor(Color.green);
        g.fillRect (2, JAUGE_HEIGHT+1, JAUGE_WEIGH , JAUGE_HEIGHT); 
        g.setColor(Color.magenta);
        g.fillRect(2, JAUGE_HEIGHT+1, this.nombreJourMaladeJauge, JAUGE_HEIGHT);
        
        g.setColor(Color.white);
        g.setFont(PERSON_LABEL_FONT);
        //g.drawString(abstractPerson.toString() + abstractPerson.getId(), BORDER_SIZE + 0, BORDER_SIZE + 11);
        g.drawString(""+abstractPerson.getId(), BORDER_SIZE + 0, BORDER_SIZE + 11);
        /// dead
        if (this.abstractPerson.isMort()) {
        	g2D.setColor(Color.white);
        	g2D.setStroke(BORDER_DEAD_LINE);
        	g2D.drawLine(0, 0, PERSON_WIDTH, PERSON_WIDTH);
        	g2D.drawLine(PERSON_WIDTH, 0, 0, PERSON_WIDTH);
        } 
    }
}
