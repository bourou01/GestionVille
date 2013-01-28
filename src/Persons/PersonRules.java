package Persons;
import java.util.Vector;

import Helpers.*;
import Debug.*;
import Cases.*;
import Persons.*;

public class PersonRules {

	/**
	 * Regle 1
	 * @param 
	 * @return 
	 * A chaque tour, un citoyen contaminé à un risque (probabilité) 
	 * de devenir malade qui est égal à son niveau de contamination.
	 */
	static public void regle1(AbstractPerson p) {
		int chancesDeDevenirMalade = (int)(p.getNiveauContamination()*100);
		int r = RandomManager.randInt(0, 100);
		if ( (r>=0) && (r <= chancesDeDevenirMalade) ) {
			p.setMalade(true);
		}
	}
	/**
	 * Regle 2
	 * @param 
	 * @return 
	 * A partir du 5ème jour de maladie, il peut décéder des suites de sa maladie.
	 *  La probabilité de décéder est de 5% par jour de maladie au delà de 5ème jour de maladie. 
	 *  Ce risque de décès est diminué de moitié si un médecin est dans le même lieu. 
	 */
	static public void regle2(AbstractCase caseActuel, AbstractPerson p) {
		if (p.getNombreJourMalade() >= 5) {
			int probabiliteDeMourrir =  (int)((double)( (p.getNombreJourMalade()-5) * 0.05) * 100 );
			/// si medecin - risque divisé par 2
			int nombreMedecins = caseActuel.getMedecins().size();
			if (nombreMedecins > 0)
				probabiliteDeMourrir /= 2;
			
			int r = RandomManager.randInt(0, 100);
			if ( (r>=0) && (r<=probabiliteDeMourrir) ) {
				 p.setMort(true);
			}
		}	
	}
	/**
	 * Regle 3
	 * @param 
	 * @return 
	 * Par contre, un déplacement de citoyen ne doit jamais conduire à un dépassement de capacité maximale.
	 */
	static public boolean regle3(AbstractCase dest) {
		/// verifie si ' pas de depassement de population '
		int nombreDeVillageoiesAvenir = dest.getVillageois().size() ; ///  (pour celui qu'on va ajouter)
		int nombreDeVillageoiesMax = dest.getNombreCitoyensMax();
		if (nombreDeVillageoiesAvenir>=nombreDeVillageoiesMax) {
			// Log.Disp("POPULATION");
			return false;
		}
		return true;
	}
	/**
	 * Regle 4
	 * @param 
	 * @return 
	 * Un citoyen en se déplaçant voit son niveau de contamination augmenter 
	 * seulement de 2% du niveau de contamination du lieu sur lequel il arrive 
	 * et de 5% s'il reste sur la même case.
	 */
	static public void regle4(AbstractPerson p, AbstractCase cDest, boolean deplacee) {
		double niveauContaminationPersonne = p.getNiveauContamination();
		double niveauContaminationLieu = cDest.getNiveauContamination();
		if (deplacee) { /// 2%
			double deuxPourcentsDuLieu = 0.02*niveauContaminationLieu;
			double result = ProbabilityManager.augmentation(niveauContaminationPersonne, deuxPourcentsDuLieu);
			p.setNiveauContamination(result);

		}else { /// 5%
			double cinqPourcentsDuLieu = 0.05*niveauContaminationLieu;
			double result = ProbabilityManager.augmentation(niveauContaminationPersonne, cinqPourcentsDuLieu);
			p.setNiveauContamination(result);
		}	
	}
	/**
	 * Regle 5
	 * @param 
	 * @return 
	 * Un citoyen contaminé augmente de 1%, de son niveau de contamination, 
	 * celui du lieu sur lequel il est situé
￼￼	 * (une caserne à un niveau de contamination toujours nulle
 	 * et un hôpital ne répercute que 1⁄4 de l'augmentation grâce aux conditions d'hygiène élevées)
	 */
	static public void regle5(AbstractCase lieuActuel, AbstractPerson p) {
		double niveauContaminationPersonne = p.getNiveauContamination();
		double niveauContaminationLieu = lieuActuel.getNiveauContamination();
		double unPourcentDuLieu = 0.01*niveauContaminationLieu;
		double result = ProbabilityManager.augmentation(niveauContaminationPersonne, unPourcentDuLieu);
		if (lieuActuel instanceof Caserne) {
			/// ba ... rien
		}
		else if (lieuActuel instanceof Hospital) {
			p.setNiveauContamination( (1/4) * result);
		}
		else {
			p.setNiveauContamination(result);
		}
	}
	
	/**
	 * Regle 6
	 * @param 
	 * @return 
	 * 1)De plus, un malade (mort ou vivant) à 10% de probabilité de transmettre
	 * le virus de sa maladie aux citoyens dans le même lieu, 
	 * le citoyen recevant le virus est alors automatiquement malade. 
	 * 
	 * 2)Si le malade est dans un terrain vague, il a également 1% de chance de transmettre 
	 * le virus aux citoyens dans les terrains vagues voisins.
	 * 
	 * 
	 * 
	 */
	static public void regle6(AbstractCase caseActuel, AbstractPerson p) {
		/// 1)
		Vector<AbstractPerson> personnes = caseActuel.getCitoyens();
		int size = personnes.size();
		for (int i=0; i<size; i++) {
			// TODO tout le monde en même temps ou 1 à 1
			int tirage = RandomManager.randInt(0, 100);
			if ((tirage>=0) && (tirage<=10)) { /// 1 tirage par citoyen
				AbstractPerson citoyen = personnes.elementAt(i);
				citoyen.setMalade(true);
			}
		}
		/// 2)
		if (caseActuel instanceof TerrainVague) {
			Vector<AbstractCase> lieuxVoisins = SharedMethods.lieuxVoisins(caseActuel.getVille(), caseActuel.getPosition());
			int size2 = lieuxVoisins.size();
			for (int i=0; i<size2; i++) {
				AbstractCase currentCase = lieuxVoisins.elementAt(i);
				if (currentCase instanceof TerrainVague) { /// si Terrain Vague
					int size3 = currentCase.getCitoyens().size();
					for (int j=0; j<size3; j++) {
						
						AbstractPerson currentCitoyen = currentCase.getCitoyens().elementAt(j); /// Que le citoyens
						int tirage = RandomManager.randInt(0, 100);
						// TODO tout le monde en même temps ou 1 à 1
						if ((tirage>=0) && (tirage<=1)) { /// 1 tirage par citoyen
							currentCitoyen.setContaminated(true); /// le contamine !
							// TODO contamination ou maladie
						}
					}	
				}	
			}			
		}
		
	}
	/**
	 * Regle 7
	 * @param 
	 * @return  
	 * Un médecin peut soigner qu'un seul malade par jour et 
	 * il soignera toujours celui le plus malade s'ils sont plusieurs dans la même case. 
	 * Un médecin malade ne peut soigner aucun autre citoyen tant qu'il est malade,
	 * mais s'il est malade depuis moins de 10 jours6 et qu'il possède encore un kit de soins, 
	 * il peut alors l'utiliser sur lui même pour guérir de sa maladie. 
	 * Un médecin n'a pas besoin de kit de soins pour soigner un malade lorsqu'il est dans un hôpital.
	 */
	static public void regle7(AbstractCase lieuActuel, AbstractPerson p) {
		AbstractPerson lePlusMalade = SharedMethods.getPersonneLaPlusMaladeFrom(lieuActuel.getVillageois());
		
		if (p.isMalade()) { /// ne peut soigner personnes
			/// mais peut se soigner lui - même si nombreJourMalade <= 10
			if (p.getNombreJourMalade() <= 10) {
				p.setMalade(false);
			}
		} else { /// le medecin n'est pas malade
			if (lePlusMalade.isMalade()) {
				((Medecin) p).administrerSoins(lePlusMalade);

			}
		}
	}
	/**
	 * Regle 8
	 * @param 
	 * @return 
	 * Pour la décontamination, il peut diminuer jusqu'à 20% par tour la contamination d'un citoyen ou d'un lieu.
	 * Il commencera toujours par décontaminer les personnes avant le lieu sur lequel il est situé.
	 * Il peut utiliser jusqu'à un maximum du 10ème de la capacité du pulvérisateur par tour.
	 */
	static public void regle8(AbstractCase lieuActuel, AbstractPerson pompier) {
				
		int nombreDeDecontamination = (int)((((Pompier)pompier).getNiveauPulverisateur() * 100)/20);
		
		Vector<AbstractPerson> lesContamines = SharedMethods.getContaminatedFrom(lieuActuel.getVillageois());
		Vector<AbstractPerson> lesPlusContamineesAuMoinContaminees = SharedMethods.plusContamineAuMoinsContamine(lesContamines);
		
		int size = lesPlusContamineesAuMoinContaminees.size();
		
		/// les vilaageois d'abord
		for (int i=0; i<size; i++) {
			
			if (nombreDeDecontamination<=0)
				break;
			
			AbstractPerson currentPerson = lesPlusContamineesAuMoinContaminees.elementAt(i);
			((Pompier)pompier).decontaminer(currentPerson);
			
			nombreDeDecontamination--;	
		}
		/// decontamination lieu actuel
		if (nombreDeDecontamination>0);
			((Pompier)pompier).decontaminer(lieuActuel);
	}
	
	/**
	 * Regle 9
	 * @param 
	 * @return 
	 * Un pompier peut être contaminé et malade, mais grâce à sa tenue de protection, 
	 * la niveau de contamination augmente 10 fois moins vite que ce qu'il reçoit d'un lieu et 
	 * dans 70% des cas, le virus propagé par un autre citoyen n'arrive pas à traverser
	 * sa tenue (ne le rendant donc pas malade).
	 */
	static public void regle9(AbstractCase lieuActuel, AbstractPerson p) {
		// TODO le citoyen ne transmettent pas leur maladie qu'au citoyens voisins?
		
		/*
		/// contamination venant des personnes
		int chancesDeDevenirMalade = (int)(p.getNiveauContamination()*100);
		int r = RandomManager.randInt(0, 100);
		if ( (r>=0) && (r <= chancesDeDevenirMalade) ) {
			p.setMalade(true);
		}
		
		
		/// contamination vena des lieux
		double niveauContaminationPersonne = p.getNiveauContamination();
		double niveauContaminationLieu = cDest.getNiveauContamination();
		if (deplacee) { /// 2%
			double deuxPourcentsDuLieu = 0.02*niveauContaminationLieu;
			double result = ProbabilityManager.augmentation(niveauContaminationPersonne, deuxPourcentsDuLieu);
			p.setNiveauContamination(result);

		}else { /// 5%
			double cinqPourcentsDuLieu = 0.05*niveauContaminationLieu;
			double result = ProbabilityManager.augmentation(niveauContaminationPersonne, cinqPourcentsDuLieu);
			p.setNiveauContamination(result);
		}
		*/
		
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
	/**
	 * Regle 12
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle12() {
		
		
	}
	/**
	 * Regle 13
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle13() {
		
		
	}
	
	/**
	 * Regle 14
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle14() {
		
		
	}
	/**
	 * Regle 15
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle15() {
		
		
	}
	
	/**
	 * Regle 16
	 * @param 
	 * @return 
	 * 
	 */
	static public void regle16() {
		
		
	}
	

	
}
