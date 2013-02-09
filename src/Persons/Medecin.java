/**
 * @author ABDULLATIF Mouhamadi et LUCAS COSTA Amaro
 * @version 
 */

package Persons;


import Cases.AbstractCase;
import Cases.Hospital;
import Helpers.Position;
import Helpers.SharedMethods;

public class Medecin extends AbstractPerson  {
	private int nombreKitDeTraitement;
	/**
	 * Constructeur par defaut
	 * @param 
	 * @return 
	 */
	public Medecin() {
		super();
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'MouvementRules'
	
	@Override
	public void deplacer() {
		
		if (this.isMort())
			return;
		
		/** DEPLACEMENT ALEATOIRE*/
		Position pToMove = this.choisirCaseAleatoirement();
		
		
		/** DEPLACEMENT INTELLIGENT */
		AbstractCase lieuCourant = super.getLieuCourant();
		
		
		/** si pas de doses de traitement => HOSPITAL 
		 * Un médecin retourne à l’hôpital s'il n'a pas de dose de traitement. 
		 * Il choisit la case autour de lui qui lui permet de se rapprocher le plus près de l’hôpital
		 * */
		if (this.nombreKitDeTraitement <= 0) {
			pToMove = SharedMethods.casePourLieuLePlusProche(lieuCourant, "Hospital");
		
			/**
			 * si cette case n'est pas accessible, car ayant atteint sa capacité maximal, 
			 * il choisi de se déplacer sur la case à droite ou à gauche (par rapport au citoyen) 
			 * de celle la plus direct (et ainsi de suite, si ces cases sont également inaccessible).
			 * */
			boolean deplaceeOuPas = super.moveTo(pToMove);
			
			if (!deplaceeOuPas) { // Aleatoire - conditions donnees par le cahier des charges est trop compliquee
				pToMove = this.choisirCaseAleatoirement();
				super.moveTo(pToMove);
			}
			return;
		}
		
		
		/**
		 * Lorsqu'il est dans un hôpital, il y reste tant que le nombre de médecin 
		 * est inférieure au nombre de malades
		 * */
		int nbMalades = SharedMethods.getMaladesFrom(lieuCourant.getVillageois()).size();
		int nbMedecins = lieuCourant.getMedecins().size(); 
		if ((nbMalades>nbMedecins) && (lieuCourant instanceof Hospital))
			return; // bouge pas
		
		super.moveTo(pToMove);
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////
	///////////////// REGLES DE MOUVEMENT SPECIFIQUES
	void choisirCaseAvecMalade() {
		
	}
	void choisirCaseLaMoinsContaminee() {
		
	}
	//////////////////////////////////////////////////////////////////////////////
	///////////////// REGLES DE TRAITEMENT SPECIFIQUES
	void administrerSoins(AbstractPerson person) {	
		/// Un meÃÅdecin n'a pas besoin de kit de soins pour soigner un malade lorsqu'il est dans un hoÃÇpital.
		if ( super.getLieuCourant() instanceof Hospital) {
			person.setMalade(false);
		}
		else {
			if (this.nombreKitDeTraitement>0) {
				this.nombreKitDeTraitement--;
				person.setMalade(false);
				//Log.Disp( this.ID()+" Soigne " + person.ID());
			}
		}
	}
	boolean verifieSiKitDeTraitement() {
		return false;
	}
	public int getNombreKitDeTraitement() {
		return nombreKitDeTraitement;
	}
	public void setNombreKitDeTraitement(int nombreKitDeTraitement) {
		this.nombreKitDeTraitement = nombreKitDeTraitement;
	}
}
