package Villes;

import Cases.*;
import java.util.*;
import Helpers.*;
import Persons.*;

public class VilleRules {

	/**
	 * Regle 1 
	 * @param 
	 * @return 
	 * Initialement seul les terrains vagues ont un niveau de contamination supérieure à zéro. 
	 * Le niveau de contamination d'un lieu est compris entre 0,0 (0%) et 1,0 (100%). 
	 * Seulement 10 % des terrains vagues sont contaminées 
	 * (niveaux de contamination compris entre 20 % et 40%) au début de la partie.
	 */
	static public void regle1(Vector<AbstractCase> c) {
		int size = c.size();
		int dixPourCent = ProbabilityManager.percentOfAnInt(size, 0.1);
		for (int i=0; i<dixPourCent; i++) {
			AbstractCase currentC = c.elementAt(i);
			double niveauContamination = (double)(RandomManager.randInt(20, 40))/100; /// 20 à 40 % de contamination
			currentC.setNiveauContamination(niveauContamination);
		}	
	}
	/**
	 * Regle 2
	 * @param 
	 * @return 
	 *Les citoyens ont également un niveau de contamination compris entre 0% et 100%.
	 */
	static public void regle2(AbstractPerson p) {
		double niveauContamination = (double)(RandomManager.randInt(0, 100))/100; /// 0 à 100 % de contamination
		p.setNiveauContamination(niveauContamination);
	}

	/**
	 * Regle 3
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle3() {
		
		
	}
	
	/**
	 * Regle 4
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle4() {
		
		
	}
	/**
	 * Regle 5
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle5() {
		
		
	}

	/**
	 * Regle 6
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle6() {
		
		
	}
	
	/**
	 * Regle 7
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle7() {
		
		
	}
	/**
	 * Regle 8
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle8() {
		
		
	}
	
	/**
	 * Regle 9
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle9() {
		
		
	}
	/**
	 * Regle 10
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle10() {
		
		
	}
	
	/**
	 * Regle 11
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle11() {
		
		
	}
	
	
	
	
}
