/**
 * @author ABDULLATIF Mouhamadi et LUCAS COSTA Amaro
 * @version 
 */

package Helpers;

import java.util.Vector;

import Cases.AbstractCase;
import Persons.AbstractPerson;
import Villes.Ville;

public class SharedMethods {

	/**
	 * Calcule la position des cases disponibles pour un deplacement
	 * @param 
	 * @return 
	 */
	static public Vector<Position> casesAround(Ville ville, Position p) {
		Vector<Position> validPlaces = new Vector<Position>();
		/// maximas
		int xMax = ville.getLignes();
		int yMax = ville.getColonnes();
		
		/////////////////////////////////////////////////////////////////////
		///////////////8 positions possibles au plus
		
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
	/**
	 * Calcule le lieux disponibles pour un deplacement
	 * @param 
	 * @return 
	 */
	static public Vector<AbstractCase> lieuxVoisins(Ville ville, Position p) {
		Vector<AbstractCase> casesVoisins = new Vector<AbstractCase>();
		Vector<Position> positionsDesLieux = SharedMethods.casesAround(ville, p);
		int size = positionsDesLieux.size();
		for (int i=0; i<size; i++) {
			AbstractCase currentCase = ville.getCase(positionsDesLieux.elementAt(i));
			casesVoisins.add(currentCase);
		}
		return casesVoisins;
	}
	/**
	 * Calcule les sains
	 * @param 
	 * @return 
	 */
	static public Vector<AbstractPerson> getSainsFrom(Vector<AbstractPerson> p) {
		Vector<AbstractPerson> newP = new Vector<AbstractPerson>();
		for (int i=0; i<p.size(); i++) {
			AbstractPerson currentP = p.elementAt(i);
			if ((!currentP.isMort()) && (!currentP.isMalade())) {
				newP.add(currentP);
			}
		}
		return newP;
	}
	/**
	 * Calcule les contaminŽes
	 * @param 
	 * @return
	 * 
	 */
	static public Vector<AbstractPerson> getContaminatedFrom(Vector<AbstractPerson> p) {
		Vector<AbstractPerson> newP = new Vector<AbstractPerson>();
		for (int i=0; i<p.size(); i++) {
			AbstractPerson currentP = p.elementAt(i);
			if ((!currentP.isMort()) && (currentP.isContaminated())) {
				newP.add(currentP);
			}
		}
		return newP;
	}
	/**
	 * Calcule les malades
	 * @param 
	 * @return 
	 */
	static public Vector<AbstractPerson> getMaladesFrom(Vector<AbstractPerson> p) {
		Vector<AbstractPerson> newP = new Vector<AbstractPerson>();
		for (int i=0; i<p.size(); i++) {
			AbstractPerson currentP = p.elementAt(i);
			if ( (currentP.isMalade()) && (!currentP.isMort()) ) {
				newP.add(currentP);
			}
		}
		return newP;
	}
	/**
	 * Calcule les morts
	 * @param 
	 * @return 
	 */
	static public Vector<AbstractPerson> getMortsFrom(Vector<AbstractPerson> p) {
		Vector<AbstractPerson> newP = new Vector<AbstractPerson>();
		for (int i=0; i<p.size(); i++) {
			AbstractPerson currentP = p.elementAt(i);
			if ( (currentP.isMort()) ) {
				newP.add(currentP);
			}
		}
		return newP;
	}
	/**
	 * Calcule la personne la plus malade
	 * @param 
	 * @return 
	 */
	static public AbstractPerson getPersonneLaPlusMaladeFrom(Vector<AbstractPerson> personnes) {
		int nombreDePersonnes = personnes.size();
		AbstractPerson lePauvre = new AbstractPerson();
		if (nombreDePersonnes<=0)
			return null;
		for (int i=0; i<nombreDePersonnes; i++) {
			if (personnes.elementAt(i).getNombreJourMalade()>lePauvre.getNombreJourMalade()) {
				lePauvre = personnes.elementAt(i);
			}
		}
		return lePauvre;
	}
	/**
	 * Trie un tableau par un nombre de personnes les plus malade au moins malades
	 * @param 
	 * @return 
	 */
	static public Vector<AbstractPerson> plusMaladeAuMoinsMalade(Vector<AbstractPerson> personnes) {
		int size = personnes.size();
		Vector<AbstractPerson> copyPersonnes = SharedMethods.copyVector(personnes);
		Vector<AbstractPerson> result = new Vector<AbstractPerson>();
		for (int i=0; i<size; i++) {
			AbstractPerson lePauvre = SharedMethods.getPersonneLaPlusMaladeFrom(copyPersonnes);
			result.add(lePauvre);
			copyPersonnes.remove(lePauvre);
		}
		return result;
	}
	/**
	 * Calcule la personne la plus malade
	 * @param 
	 * @return 
	 */
	static public AbstractPerson getPersonneLaPlusContamineFrom(Vector<AbstractPerson> personnes) {
		int nombreDePersonnes = personnes.size();

		AbstractPerson lePauvre = new AbstractPerson();
		if (nombreDePersonnes>0)
			lePauvre = personnes.elementAt(0);
		
		for (int i=0; i<nombreDePersonnes; i++) {
			if (personnes.elementAt(i).getNiveauContamination()>lePauvre.getNiveauContamination()) {
				lePauvre = personnes.elementAt(i);
			}
		}
		return lePauvre;
	}
	/**
	 * Trie un tableau par un nombre de personnes les plus malade au moins malades
	 * @param 
	 * @return 
	 */
	static public Vector<AbstractPerson> plusContamineAuMoinsContamine(Vector<AbstractPerson> personnes) {
		int size = personnes.size();
		
		Vector<AbstractPerson> copyPersonnes = SharedMethods.copyVector(personnes);
		Vector<AbstractPerson> result = new Vector<AbstractPerson>();
		
		for (int i=0; i<size; i++) {
			AbstractPerson lePauvre = SharedMethods.getPersonneLaPlusContamineFrom(copyPersonnes);
			result.add(lePauvre);
			copyPersonnes.remove(lePauvre);
		}
		return result;
	}
	/**
	 * Copie 2 vecteurs
	 * @param 
	 * @return 
	 */
	static public Vector<AbstractPerson> copyVector(Vector <AbstractPerson> toCopy) {
		Vector<AbstractPerson> result = new Vector<AbstractPerson>();
		for (int i=0; i<toCopy.size(); i++ ) {
			result.add(toCopy.elementAt(i));
		}
		return result;
	}
	/**
	 * Calcul le minimum d'un tableau
	 * @param 
	 * @return 
	 */
	public static int getMinValue(int[] numbers){  
		int minValue = numbers[0];  
		for(int i=1;i<numbers.length;i++){  
			if(numbers[i] < minValue){  
				minValue = numbers[i];  
			}  
		}  
		return minValue;
	}
	/**
	 * Calcul le maximum d'un tableau
	 * @param 
	 * @return 
	 */
	public static int getMaxValue(int[] numbers){  
		int maxValue = numbers[0];  
		for(int i=1;i < numbers.length;i++){  
			if(numbers[i] > maxValue){  
				maxValue = numbers[i];  
		    }  
		}  
		return maxValue;  
	}
	
	/**
	 * Calcul la distance entre 2 cases
	 * @param 
	 * @return 
	 */
	public static double distance(Position pA, Position pB) {
		//return Math.sqrt( (pB.x-pA.x)^2 + (pB.y-pA.y)^2 );
		/*
		double A = Math.pow((pB.x-pA.x), 2.0);
		double B = Math.pow((pB.y-pA.y), 2.0);
		double dist = Math.sqrt( A + B);
		*/
		
		double dist = Math.max(Math.abs(pB.x-pA.x) , Math.abs(pB.y-pA.y));
		return dist;
		
	}
	/**
	 * Calcul la case la plus proche pour se rendre a un lieu
	 * @param 
	 * @return 
	 */
	public static Position casePourLieuLePlusProche(AbstractCase caseActuel, String typeLieu) {
		/// Lieu de la ville
		
		Vector <AbstractCase> lieux = null;
		if (typeLieu.equals("Hospital") )
			lieux = caseActuel.getVille().getHospitals();
		else if (typeLieu.equals("Caserne"))
			lieux = caseActuel.getVille().getCasernes();
		else if (typeLieu.equals("Maison"))
			lieux = caseActuel.getVille().getMaisons();
		else
			return null;
		
		/// cases auTour
		Vector<Position> casesAutour = SharedMethods.casesAround(caseActuel.getVille() , caseActuel.getPosition());
		
		/// Optimal
		Position caseOptimal = new Position();
		if (casesAutour.size()>0)
			caseOptimal = casesAutour.elementAt(0);
		
		double distanceOptimal = 1000000000000000.0;

		for (int i=0; i<lieux.size(); i++) {
			for (int j=0; j<casesAutour.size(); j++) {
				Position currentLieu = lieux.elementAt(i).getPosition();
				Position currentCaseAutour = casesAutour.elementAt(j);
				double currentDistance = SharedMethods.distance(currentCaseAutour, currentLieu);
				if (currentDistance < distanceOptimal) {
					distanceOptimal = currentDistance;
					caseOptimal = currentCaseAutour;
				}
			}
		}
		return caseOptimal;
	}
	
	/**
	 * Calcule la case la plus contaminee
	 * @param 
	 * @return 
	 */
	public static AbstractCase caseLaPlusContaminee(AbstractCase caseActuel){ 
		
		AbstractCase laPlusContaminee = null;
		
		/** casesAutour*/
		Vector <Position> casesAutour = SharedMethods.casesAround(caseActuel.getVille(), caseActuel.getPosition());
		
		double niveauContaminationMax = 0.0;
		
		for (int i=0; i<casesAutour.size(); i++) {
			Position currentPosition = casesAutour.elementAt(i);
			AbstractCase currentCase = caseActuel.getVille().getCase(currentPosition);
			double niveauContaminationCourant = currentCase.getNiveauContamination();

			if (niveauContaminationMax > niveauContaminationCourant) {
				laPlusContaminee = currentCase;
			}
		}
		return laPlusContaminee;
	}	
}