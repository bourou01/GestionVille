package Cases;

import Helpers.*;
import Debug.*;
import Cases.*;
import Villes.*;
import java.util.*;
import Persons.*;

public class CasesRules {
	
	
	/**
	 * Regle 1
	 * @param 
	 * @return 
	 *  Un terrain vague peut augmenter le niveau de contamination d'un autre terrain vague 
	 *  dans son voisinage (les 8 cases l'entourant), seulement si son niveau est supeÃÅrieure aux voisins consideÃÅreÃÅs. 
	 *  Dans ce cas la probabiliteÃÅ d'avoir une augmentation est de 15% 
	 *  et l'augmentation est comprise entre 1% et 20% de la diffeÃÅrence de niveau de contamination entre les deux terrains.
	 *  Il est important de noter que les maisons, hoÃÇpitaux et casernes ne peuvent voir leur niveau de contamination
	 *  augmenter aÃÄ causes de terrains vagues voisins (et reÃÅciproquement), mais seulement aÃÄ cause des citoyens preÃÅsents.
	 */
	static public void regle1(Ville ville, AbstractCase caseActuel) {
		Vector<Position> positionsAutour = SharedMethods.casesAround(ville, caseActuel.getPosition());
		int size = positionsAutour.size();
		for (int i=0; i<size; i++) {
			AbstractCase c = ville.getCase(positionsAutour.elementAt(i));
			if (c instanceof TerrainVague) { /// seulement
				///Log.Disp("Terrain Vague");
				double niveauContaminationCaseActuel = caseActuel.getNiveauContamination();
				double niveauContaminationCaseAutour = c.getNiveauContamination();
				if (niveauContaminationCaseActuel>niveauContaminationCaseAutour) {
					int tirage = RandomManager.randInt(0, 100);
					if ((tirage >= 0) && (tirage<=15)) {
						double differanceNiveauContamination = niveauContaminationCaseActuel - niveauContaminationCaseActuel;
						double augmentation = ((double)RandomManager.randInt(0, 20)/100.0) * differanceNiveauContamination;
						double result = ProbabilityManager.augmentation(c.getNiveauContamination(), augmentation);
						c.setNiveauContamination(result);
					}	
				}
			}	
		}
	}
	/**
	 * Regle 2
	 * @param 
	 * @return 
	 * Seul les citoyens malades, les meÃÅdecins et les pompiers peuvent entrer dans un hoÃÇpital.
	 * Un citoyen malade entrant dans un hoÃÇpital est directement pris en charge. 
	 * Son risque de moraliteÃÅ est alors diviseÃÅ par 4. 
	 */
	static public boolean regle2(AbstractCase lieuCourant, AbstractPerson p) {
		boolean admissible = false;
		if ((p instanceof Medecin) || (p instanceof Pompier)) {
			admissible = true;
		}

		
		else if (p instanceof Citoyen) {
			if (p.isMalade())
				admissible = true;
		}
		
		if (admissible == true) {
			
			/// risque de mortalite = niveauDeContamination
			double newNiveauDeContamination = p.getNiveauContamination()/4.0;
			p.setNiveauContamination(newNiveauDeContamination);			
			return true;
		}
		
		else {
			return false;
		}
	}
	/**
	 * Regle 3
	 * @param 
	 * @return 
	 * De plus, son niveau de contamination baisse aÃÄ chaque tour de 10%,
	 * s'il eÃÅtait supeÃÅrieur aÃÄ celui de l‚ÄôhoÃÇpital, sans pouvoir devenir infeÃÅrieur aÃÄ celui de l'hoÃÇpital. 
	 */
	static public void regle3(AbstractCase hospital) {
		double niveauContaminationHospital = hospital.getNiveauContamination();
		Vector<AbstractPerson> citoyens = hospital.getCitoyens();
		int size = citoyens.size();
		for (int i=0; i<size; i++) {
			AbstractPerson currentCitoyen = citoyens.elementAt(i);
			double niveauContaminationCitoyen = currentCitoyen.getNiveauContamination();
			double diminution10Pourcents = ProbabilityManager.diminution(niveauContaminationCitoyen, 0.1);
			double result = Math.max(niveauContaminationHospital, diminution10Pourcents);
			currentCitoyen.setNiveauContamination(result);	
		}
	}
	
	/**
	 * Regle 4
	 * @param 
	 * @return 
	 * Le nombre de personnes gueÃÅries, par tour, de leurs maladies 
	 * est de un plus le nombre de meÃÅdecins (non malade) dans l‚ÄôhoÃÇpital.
	 * Les personnes les plus malades (en nombre de jours) sont soigneÃÅes en premier. 
	 */
	static public void regle4(AbstractCase hospital) {
		
		Vector<AbstractPerson> malades = SharedMethods.plusMaladeAuMoinsMalade(hospital.getVillageois());
		int nombreMedecinsDansHospital = hospital.getMedecins().size();
		int nombrePersonnesAGuerrir = 1 + nombreMedecinsDansHospital;
		int counter = 0;
		for (int i=0; i<malades.size(); i++) {
			counter++;
			
			AbstractPerson currentPerson = malades.elementAt(i);
			currentPerson.setMalade(false);
			// TODO decontaminer ausssi??
			
			if (counter>nombrePersonnesAGuerrir)
				break;
		}
	}
	/**
	 * Regle 5
	 * @param 
	 * @return 
	 * Un meÃÅdecin entrant dans un hoÃÇpital recÃßoit 10 kits de soins permettant,
	 * chacun, de gueÃÅrir un malade situeÃÅ en dehors de l'hoÃÇpital.
	 */
	static public void regle5(AbstractPerson p) {
		if (p instanceof Medecin) {
			((Medecin) p).setNombreKitDeTraitement(10);
		}
	}
	/**
	 * Regle 6
	 * @param 
	 * @return 
	 * Un citoyen entrant dans une caserne voit son niveau de contamination diminuer de 20% par tour.
	 *  Un citoyen est donc totalement deÃÅcontamineÃÅ en un maximum de 5 tours, 
	 *  mais cela n'a aucune incidence sur son status de malade et seul un meÃÅdecin ou un hoÃÇpital pourra le soigner. 
	 *  De plus, il recÃßoit un appareil lui permettant de mesurer le niveau de contamination des personnes 
	 *  et des lieux dans son voisinage immeÃÅdiat, ainsi que de la case sur laquelle il est situeÃÅ.
	 */
	static public void regle6(AbstractCase caserne) {
		Vector<AbstractPerson> citoyens = caserne.getCitoyens();
		int size = citoyens.size();
		for (int i=0; i<size; i++) {
			AbstractPerson currentCitoyen = citoyens.elementAt(i);
			double niveauContaminationCitoyen = currentCitoyen.getNiveauContamination();
			double diminution20Pourcents = ProbabilityManager.diminution(niveauContaminationCitoyen, 0.2);
			currentCitoyen.setNiveauContamination(diminution20Pourcents);
			
			if(!currentCitoyen.isHasAppareilMesureContaminationLevel()) {
				currentCitoyen.setHasAppareilMesureContaminationLevel(true);
			}
		}
	}
	/**
	 * Regle 7
	 * @param 
	 * @return 
	 * 1) Un pompier entrant dans une case bruÃÇle automatiquement tous les corps des morts.
	 * 
	 * 2) En entrant dans une caserne, un pompier reçoit un pulvérisateur permettant de décontaminer jusqu'à 1000% 
	 * de niveau de radiation.
	 */
	static public void regle7(AbstractCase lieuCourant, AbstractPerson p) {
		
		if (p instanceof Pompier) {
			
			// 1)
			Vector<AbstractPerson> morts = SharedMethods.getMortsFrom(lieuCourant.getVillageois());
			for (int i=0; i<morts.size(); i++) {
				AbstractPerson currentMort = morts.elementAt(i);
				((Pompier)p).brulerCadavre(currentMort);
			}
			// 2)
			if (lieuCourant instanceof Caserne)
				((Pompier)p).setNiveauPulverisateur(10.0); /// 1000% = 10
		}
	}
	/**
	 * Regle 8
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle8() {
		
		
	}
	
	/**
	 * Regle 9
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle9() {
		
		
	}
	/**
	 * Regle 10
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle10() {
		
		
	}
	
	/**
	 * Regle 11
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle11() {
		
		
	}
	
}
