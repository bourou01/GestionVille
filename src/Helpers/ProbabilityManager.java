
/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */



package Helpers;

//import GestionVille;

public class ProbabilityManager {

	/*
	//////////////////TEST/////////////////
	public static void main(String[] args) {
		System.out.println("salut: " + ProbabilityManager.percentOfAnInt(13, .1));	
	}
	*/
	
	/**
	 * Fait une augmentation de 'val' de 'aug' pour cent
	 * @param 
	 * @return 
	 */
	public static double augmentation(double val, double aug) {
		//return val*(1+aug);
		return val + aug;
	}
	/**
	 * Fait une diminution de 'val' de 'aug' pour cent
	 * @param 
	 * @return 
	 */
	public static double diminution(double val, double dim) {
		//return val*(1-aug);
		return val - dim;
	}
	/**
	 * Calcule un nombre en fonction d'un pourcentage et l'arrondit, s'il le faut
	 * @param 
	 * @return 
	 */
	public static int percentOfAnInt(int val, double percent) {
		double result = Math.round(val*percent);
		return (int)result;
	}
	
}
