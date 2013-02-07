/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

package Persons;
 
import java.util.Vector;

import Cases.AbstractCase;
import Helpers.Position;
import Helpers.ProbabilityManager;
import Helpers.SharedMethods;

public class Pompier extends AbstractPerson  {

	private double niveauPulverisateur;
	
	/**
	 * Constructeur par defaut
	 * @param 
	 * @return 
	 */
	public Pompier() {
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'MouvementRules'
	
	@Override
	public void deplacer() {
		if (this.isMort())
			return;
		
		AbstractCase lieuCourant = super.getLieuCourant();
		
		/** deplacement aleatoire */
		Position pToMove = this.choisirCaseAleatoirement();
		
		/** deplacement intelligent */
		
		
		/**
		 * Un pompier en sortant d'une caserne, se déplacera à chaque tour vers la case la 
		 * plus contaminée parmi les 8 voisines. Si la case, sur laquelle il est, 
		 * est plus contaminée que ces 8 voisines
		 * */
		
		AbstractCase laPlusContaminee = SharedMethods.caseLaPlusContaminee(lieuCourant);
		
		if (laPlusContaminee != null) {
			pToMove = laPlusContaminee.getPosition();
			
			boolean deplaceeOuPas = super.moveTo(pToMove);
			
			if (!deplaceeOuPas) { // Aleatoire - conditions donnees par le cahier des charges est trop compliquee
				pToMove = this.choisirCaseAleatoirement();
				super.moveTo(pToMove);
			}
		}
		this.moveTo(pToMove);
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
		
		// En gros l'objet ne sera plus r√©f√©renc√© et sera donc d√©truit par le barbage Collector
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
