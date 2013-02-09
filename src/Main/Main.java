/**
 * @author ABDULLATIF Mouhamadi et LUCAS COSTA Amaro
 * @version 
 */

package Main;
import Controller.GestionVilleController;

public class Main {
	public static void main(String[] args) {
		/// sur console
		//GestionVille.testAvecUneVille();

		/// avec swing
		GestionVilleController IHM = new GestionVilleController();
		IHM.setVisible(true);
	}
}