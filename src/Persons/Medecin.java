
/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

package Persons;


import Cases.*;
import Debug.Log;
import SharedInterfaces.*;


public class Medecin extends AbstractPerson  {
	private int nombreKitDeTraitement;
	
	
	/**
	 * Constructeur par d√©faut
	 * @param 
	 * @return 
	 */
	public Medecin() {
		super();
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
		/// Un médecin n'a pas besoin de kit de soins pour soigner un malade lorsqu'il est dans un hôpital.
		if ( super.getLieuCourant() instanceof Hospital) {
			person.setMalade(false);
		}
		else {
			if (this.nombreKitDeTraitement>0) {
				this.nombreKitDeTraitement--;
				person.setMalade(false);
				Log.DispP(this.getId(), 
						this.ID()+" Soigne " + 
								person.ID()
				);
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
