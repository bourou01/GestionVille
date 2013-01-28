/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */


package SharedInterfaces;

import Persons.*;
import Helpers.*;
import Cases.*;
import java.util.Vector;

public interface MovementRules {

	
	//////////////////////////////////////////////////////////////////////////////
	///////////////// SERA APPLELLE A CHAQUE DEMANDE DE MOUVEMENT
	void deplacer();

	//////////////////////////////////////////////////////////////////////////////
	///////////////// REGLES DE MOUVEMENT COMMUN A TOUTES LES PERSONNES
	Vector<Position> casesValidesPourDeplacement();
	
	Position choisirCaseAleatoirement();
	Position choisirCaseLaPlusProcheDeCaserne();
	Position choisirCaseLaPlusProcheDeHospital();
	Position choisirZoneMoinsContaminee();
	Position choisirZoneAvecPompier();
	Position choisirZoneAvecMaisonSain();
	Position choisirMaisonAleatoirement();
	
	boolean moveToCase(Position pDest);
}
