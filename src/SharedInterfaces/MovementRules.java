/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

package SharedInterfaces;
import java.util.Vector;
import Helpers.Position;

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