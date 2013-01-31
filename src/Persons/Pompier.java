/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

package Persons;
 
import SharedInterfaces.*;
import Cases.*;
import Helpers.ProbabilityManager;

import java.util.*;

public class Pompier extends AbstractPerson  {

	private double niveauPulverisateur;
	
	/**
	 * Constructeur par d√©faut
	 * @param 
	 * @return 
	 */
	public Pompier() {
		
	}

	//////////////////////////////////////////////////////////////////////////////
	///////////////// REGLES DE MOUVEMENT SPECIFIQUES
	void choisirCaseLaPlusContaminee() {
		
	}
	
	//////////////////////////////////////////////////////////////////////////////
	///////////////// REGLES DE TRAITEMENT SPECIFIQUES
	public void decontaminer(AbstractCase lieu) {
		double niveauContaminationLieu = lieu.getNiveauContamination();
		
		if ((this.niveauPulverisateur>=0.2) && (niveauContaminationLieu>=0.2)) {
		
			double result = ProbabilityManager.diminution(niveauContaminationLieu, 0.2);
			lieu.setNiveauContamination(result);
			
			this.niveauPulverisateur -= 0.2;
		}
	}
	public void decontaminer(AbstractPerson person) {
		double niveauContaminationPeronne = person.getNiveauContamination();
		
		if ((this.niveauPulverisateur >= 0.2) && (niveauContaminationPeronne>=0.2) ) {
			
			double result = ProbabilityManager.diminution(niveauContaminationPeronne, 0.2);
			person.setNiveauContamination(result);
			
			this.niveauPulverisateur -= 0.2;
		}
	}
	
	public void brulerCadavre(AbstractPerson person) {
		/// Du lieu
		AbstractCase lieu = person.getLieuCourant();
		lieu.getVillageois().remove(person);
		
		// Du Village
		Vector<AbstractPerson> villageoisDansVille = person.getVille().getVillageois();
		villageoisDansVille.remove(person);
		
		// En gros l'objet ne sera plus référencé et sera donc détruit par le barbage Collector
	}

	//////////////////////////////////////////////////////////////////////////////
	///////////////// SETTERS/GETTERS
	public double getNiveauPulverisateur() {
		return niveauPulverisateur;
	}

	public void setNiveauPulverisateur(double niveauPulverisateur) {
		this.niveauPulverisateur = niveauPulverisateur;
	}
	
}
