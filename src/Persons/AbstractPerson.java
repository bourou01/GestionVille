
/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */


package Persons;

import Villes.*;
import Cases.*;
import Debug.Log;
import Helpers.Position;
import Helpers.RandomManager;
import SharedInterfaces.*;
import SharedInterfaces.*;
import java.util.Vector;


/// LES PERSONNES PEUVENT FAIRE DES ACTIONS ET BOUGER ET ETRE CONTAMINEE
public class AbstractPerson implements MovementRules, ActionManager, ContagionManager { 
	
	private Ville ville;
	private AbstractCase lieuCourant;
	
	private Position position;
	
	private int tours;
	
	private boolean hasAppareilMesureContaminationLevel = false;
	private boolean malade = false;
	private boolean contaminated = false;
	private boolean mort = false;
	private int nombreJourMalade = 0;
	private double niveauContamination = 0;
	
	private boolean dejaEteMalade;
	
	///
	private int id;
	
	/**
	 * Constructeur par défaut
	 * @param 
	 * @return 
	 */
	public AbstractPerson() {
		super();
		this.position = new Position(0,0);
	}
	/**
	 * Constructeur
	 * @param 
	 * @return 
	 */
	public AbstractPerson(int x, int y) {
		this();
		this.position.setX(x);
		this.position.setY(y);
	}
	/**
	 * Constructeur
	 * @param 
	 * @return 
	 */
	public AbstractPerson(int x, int y, int tours) {
		this(x, y);
		this.tours = tours;
	}

	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'ContagionManager'
	@Override
	public void contaminate() {
		// TODO Auto-generated method stub
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'ActionManager'
	@Override
	public void action() {
		if (this.isMort())
			return;
		
		
		AbstractCase caseActuel = this.getLieuCourant();
		
		/** Règles spécifique aux medecins */
		if (this instanceof Medecin) { 
			/**************************
			 *		 REGLE 7 : Un médecin peut soigner qu'un seul malade par jour ... sur lui même pour guérir de sa maladie.
			 **************************/
			PersonRules.regle7(caseActuel, this);
		}
		/** Règles spécifique aux pompiers */
		else if (this instanceof Pompier) { 
			
			/**************************
			 *		 REGLE 8 : décontamination, il peut diminuer jusqu'à 20% par tour la contamination d'un citoyen ou d'un lieu ...
			 **************************/
			PersonRules.regle8(caseActuel, this);
			
			/**************************
			 *		 REGLE 9 : pompier peut être contaminé et malade
			 **************************/
			PersonRules.regle9(caseActuel, this);	
		}
		
		/** Règles communs */
		if ((!this.isMort()) && this.isContaminated()) {
			/**************************
			 *		 REGLE 1 : risque de devenir malade
			 **************************/
			PersonRules.regle1(this);
			
			/**************************
			 *		 REGLE 5 : augmentation du niveau de contamination par rapport au lieu
			 **************************/
			PersonRules.regle5(caseActuel, this);
		}
		if (this.isMalade()) {
			
			if (!this.isMort()) {
				this.updateJoursMalade();
				
				/**************************
				 *		 REGLE 2 : il peut décéder des suites de sa maladie
				 **************************/
				PersonRules.regle2(caseActuel, this);
			}
			/**************************
			 *		 REGLE 6 : 10% de probabilité de transmettre le virus de sa maladie aux citoyens dans le même lieu
			 **************************/
			PersonRules.regle6(caseActuel , this);
		}
		
		
		
	}
	
	public void updateJoursMalade() {
		if (this.isMalade() && !this.isMort()) {
			this.nombreJourMalade++;
		}
	}
	
	
	/*
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'MouvementRules'
	@Override
	public void deplacer() {

		if (!isMort()) {
			/// deplacement aleatoire
			Position pAleatoire = this.choisirCaseAleatoirement();
			//Log.Disp("Nature:"+this.toString()+" PosSrc:"+this.position+" PosDest:"+pAleatoire);
			this.moveToCase(pAleatoire);
		}
	}
	
	*/
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'MouvementRules'
	
	@Override
	public void deplacer() {
		/*
		if (this.isMort())
			return;
		*/
		
		// deplacement aleatoire
		if (!isMort()) {
			/// deplacement aleatoire
			Position pToMove = this.choisirCaseAleatoirement();
			/// Log.Disp("Nature:"+super.toString()+"  id: "+super.getId()+" PosSrc:"+super.getPosition()+" PosDest:"+pAleatoire);
			
			/// lance le deplacement en fonction de la position calculee
			boolean deplacee = this.moveToCase(pToMove);
			/**************************
			 *		 REGLE 4 : niveau de contamination augmente en fonction du lieu d'arrivee
			 **************************/
			PersonRules.regle4(this, this.getVille().getCase(pToMove), deplacee);
			
		}
		
	}
	
	@Override
	public Vector<Position> casesValidesPourDeplacement() {
		
		Vector<Position> validPlaces = new Vector<Position>();
		
		/// maximas
		int xMax = this.ville.getLignes();
		int yMax = this.ville.getColonnes();
		/// position Actuel
		Position p = this.getPosition();
		
		/////////////////////////////////////////////////////////////////////
		///////////////8 positions possibles
		
		// p1(x-1,y-1)
		if ((p.getX()-1>=0) && (p.getY()-1>=0)) {
			Position p1 = new Position(p.getX()-1, p.getY()-1);
			validPlaces.add(p1);
		}
		// p2(x,y-1)
		if ((p.getY()-1>=0)) {
			Position p2 = new Position(p.getX(), p.getY()-1);
			validPlaces.add(p2);
		}
		// p3(x+1,y-1)
		if ((p.getX()+1<xMax) && (p.getY()-1>=0)) {
			Position p3 = new Position(p.getX()+1, p.getY()-1);
			validPlaces.add(p3);
		}
		// p4(x-1,y)
		if ((p.getX()-1>=0)) {
			Position p4 = new Position(p.getX()-1, p.getY());
			validPlaces.add(p4);
		}		
		// p5(x+1,y)
		if ((p.getX()+1<xMax)) {
			Position p5 = new Position(p.getX()+1, p.getY());
			validPlaces.add(p5);
		}
		// p6(x-1,y+1)
		if ((p.getX()-1>=0) && (p.getY()+1<yMax)) {
			Position p6 = new Position(p.getX()-1, p.getY()+1);
			validPlaces.add(p6);
		}
		// p7(x,y+1)
		if ((p.getY()+1<yMax)) {
			Position p7 = new Position(p.getX(), p.getY()+1);
			validPlaces.add(p7);
		}
		// p8(x+1,y+1)
		if ((p.getX()+1<xMax) && (p.getY()+1<yMax)) {
			Position p8 = new Position(p.getX()+1, p.getY()+1);
			validPlaces.add(p8);
		}
		
		return validPlaces;
	}

	@Override
	public Position choisirCaseAleatoirement() {
		Vector<Position> validCases = this.casesValidesPourDeplacement();
		int rIndex = RandomManager.randInt(0, validCases.size());
		return validCases.elementAt(rIndex);
	}

	@Override
	public Position choisirCaseLaPlusProcheDeCaserne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position choisirCaseLaPlusProcheDeHospital() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position choisirZoneMoinsContaminee() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position choisirZoneAvecPompier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position choisirZoneAvecMaisonSain() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position choisirMaisonAleatoirement() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	@Override
	public boolean moveToCase(Position pDest) {
		/// calcule la case source
		AbstractCase src = this.ville.getCase(this.getPosition());
		/// calcule la case destination
		AbstractCase dest = this.ville.getCase(pDest);
		this.movePersonFromCaseToCase(src, dest); /// ne prends pas en compte le depassement de population
		return true;

	}
	*/
	
	@Override
	public boolean moveToCase(Position pDest) {
		/// calcule la case source
		AbstractCase src = this.getVille().getCase(this.getPosition());
		/// calcule la case destination
		AbstractCase dest = this.getVille().getCase(pDest);
		
		
		//System.out.println(this.ID()+" s:"+src.ID()+" d:"+dest.ID());
		this.movePersonFromCaseToCase(src, dest);
		
		
		return true;
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// 
	
	public boolean movePersonFromCaseToCase(AbstractCase src, AbstractCase dest) {
		if (dest.accueillirPersonne(this)) { /// ESSAYE de L'ajouter a la nouvelle case;
			this.setPosition(dest.getPosition()); /// Echange les positions
			src.getVillageois().remove(this); /// Le supprime de la premiere case
			return true;
		}
		else {
			
			return false;
		}
	}
	
	
	
	
	
	@Override
	public String toString() {
		
		/*
		if (this instanceof Citoyen) {
			if (this.isMort())
				return "C-D";
			else if (this.isMalade())
				return "C-I";
			else
				return "C";
		}
		else if (this instanceof Medecin) {
			if (this.isMort())
				return "M-D";
			else if (this.isMalade())
				return "M-I";
			else
				return "M";
		}
		else if (this instanceof Pompier) {
			if (this.isMort())
				return "P-D";
			else if (this.isMalade())
				return "P-I";
			else
				return "P";
		}
		else {
			return "";
		}	
		*/
		
		if (this instanceof Citoyen) {
				return "C";
		}
		else if (this instanceof Medecin) {
				return "M";
		}
		else if (this instanceof Pompier) {
				return "P";
		}
		else {
			return "@";
		}	
		
		
		
	}
	
	public String ID() {
		return this.toString()+"{"+this.getId()+"}"+this.position+"";
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// GETTERS/SETTERS

	public boolean isMort() {
		return mort;
	}


	public void setMort(boolean mort) {
		this.mort = mort;
	}

	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public AbstractCase getLieuCourant() {
		return this.getVille().getCase(this.getPosition());
		// return lieuCourant;
	}
/*
	public void setLieuCourant(AbstractCase lieuCourant) {
		this.lieuCourant = lieuCourant;
	}
*/
	public boolean isHasAppareilMesureContaminationLevel() {
		return hasAppareilMesureContaminationLevel;
	}
	
	public void setHasAppareilMesureContaminationLevel(
			boolean hasAppareilMesureContaminationLevel) {
		this.hasAppareilMesureContaminationLevel = hasAppareilMesureContaminationLevel;
	}
	
	public boolean isMalade() {
		return malade;
	}


	public void setMalade(boolean malade) {
		this.malade = malade;
	}


	public int getNombreJourMalade() {
		return nombreJourMalade;
	}


	public void setNombreJourMalade(int nombreJourMalade) {
		this.nombreJourMalade = nombreJourMalade;
	}


	public double getNiveauContamination() {
		return niveauContamination;
	}


	public void setNiveauContamination(double niveauContamination) {
		this.niveauContamination = niveauContamination;
	}


	public boolean isDejaEteMalade() {
		return dejaEteMalade;
	}


	public void setDejaEteMalade(boolean dejaEteMalade) {
		this.dejaEteMalade = dejaEteMalade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int getTours() {
		return tours;
	}
	public void setTours(int tours) {
		this.tours = tours;
	}

	public Position getPosition() {
		return position;
	}
	public void setPosition(Position p) {
		
		this.position.setPosition(p);
		//this.position = p;
	}
	
	public boolean isContaminated() {
		return contaminated  = this.niveauContamination > 0 ? true : false;
	}
	public void setContaminated(boolean contaminated) {
		this.contaminated = contaminated;
	}

}
