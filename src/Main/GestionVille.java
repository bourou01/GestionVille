package Main;

/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

import Villes.*;

public class GestionVille {
	static private Ville ville;
	static public int STEPS = 100;
	/**
	 * Constructeur par défaut
	 * @param 
	 * @return 
	 */
	public GestionVille() {
		super();
		initVille();
	}
	/**
	 * Initialise la ville
	 * @param 
	 * @return 
	 */
	public static Ville initVille() {
		ville = new Ville(	7,	// _lignes,
							7,	// _colonnes,
							1,	// _nbHospital,
							12,	// _nbMaison,
							2,	// _nbCaserne,
							34,	// _nbTerrainVague,
							8,	//_nbMedecin,
							12,	//_nbPompier,
							120 //50	//_nbCitoyen,
						);
		return ville;
	}
	/**
	 * Getteur de la ville
	 * @param 
	 * @return 
	 */
	public static Ville getVille() {
		
		if (ville != null)
			return ville;
		else
			return initVille();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void testAvecUneVille() {
		System.out.println("TEST AVEC 1 SEULE VILLE");
				ville = null;
				ville = new Ville(	7,	// _lignes,
									7,	// _colonnes,
									1,	// _nbHospital,
									12,	// _nbMaison,
									2,	// _nbCaserne,
									34,	// _nbTerrainVague,
									8,	//_nbMedecin,
									12,	//_nbPompier,
									120//50	//_nbCitoyen,
									);
		System.out.println(ville.toString());
		for (int i=0; i<STEPS; i++){
			System.out.println("Step"+(i+1));
			ville.oneStep();
			System.out.println(ville.toString());
		}
	}
	
	
	
	
}
