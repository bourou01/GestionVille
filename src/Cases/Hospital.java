package Cases;

import Persons.AbstractPerson;
import Helpers.*;
import Debug.*;

public class Hospital extends AbstractCase {

	/**
	 * Constructeur par d√©faut
	 * @param 
	 * @return 
	 */
	public Hospital(int x, int y) {
		super (x, y);
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
		/**************************
		 *		 REGLE 3 : niveau de contamination baisse à chaque tour de 10%
		 **************************/
		CasesRules.regle3(this);
		
		/**************************
		 *		 REGLE 4 : nombre de personnes guéries, par tour, de leurs maladies
		 **************************/
		CasesRules.regle4(this);		
		
	}
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// Regles d'entree sortie
	@Override
	public boolean accueillirPersonne(AbstractPerson newP) { /// ajoute que les citoyens malades, medecins, pompier
		
		/**************************
		 *		 REGLE 5 : 10 Kits pour les medecins
		 **************************/		
		CasesRules.regle5(newP);
		
		/**************************
		 *		 REGLE 2 : ajoute que les citoyens malades, medecins, pompier + traitement immediat
		 **************************/
		boolean okOuPas = CasesRules.regle2(this, newP);

		if (okOuPas)
			return this.addPerson(newP);
		else
			return false;
	}
	
	
	
	
	
}
