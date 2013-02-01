package Cases;

import Debug.Log;
import Helpers.*;

public class TerrainVague extends AbstractCase{
	/**
	 * Constructeur par défaut
	 * @param 
	 * @return 
	 */
	public TerrainVague(int x, int y) {
		super (x, y);
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'ActionManager'
	@Override
	public void action() {

		/**************************
		 *		 REGLE 1 : Un terrain vague peut augmenter le niveau de contamination d'un autre terrain vague autour ...
		 **************************/
		CasesRules.regle1(super.getVille(), this);
		
	}
}
