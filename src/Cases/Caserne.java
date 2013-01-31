
/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

package Cases;

import Persons.AbstractPerson;

public class Caserne extends AbstractCase {
	
	/**
	 * Constructeur par d√©faut
	 * @param 
	 * @return 
	 */
	public Caserne() {
		super();
		super.setNiveauContamination(0); /// toujours
	}
	/**
	 * Constructeur par d√©faut
	 * @param 
	 * @return 
	 */
	public Caserne(int x, int y) {
		super (x, y);
		super.setNiveauContamination(0); /// toujours
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'ActionManager'
	@Override
	public void action() {
		
		
		/**************************
		 *		 REGLE 6 : Un citoyen entrant dans une caserne voit son niveau de contamination diminuer de 20% par tour.+Kit
		 **************************/
		CasesRules.regle6(this);
		
		
		
		
		
	}
	
	
}
