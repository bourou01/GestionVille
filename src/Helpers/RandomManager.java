
/**
 * @author ABDULLATIF Mouhamadi et LUCAS COSTA Amaro
 * @version 
 */

package Helpers;

import java.util.Random;

public class RandomManager {
	
	/**
	 * Creer un entier aleatoire
	 * @param 
	 * @return 
	 */
	public static int randInt(int valeurMin, int valeurMax) {
		Random r = new Random();
		int valeur = valeurMin + r.nextInt(valeurMax - valeurMin);
		return valeur;
	}
	
	
	
}
