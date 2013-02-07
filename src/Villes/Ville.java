/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

package Villes;


import Cases.*;
import java.util.ArrayList;
import Persons.*;
import java.util.Vector;
import Helpers.*;
import Debug.*;


public class Ville {
	private AbstractCase[][] village;
	private Vector<AbstractPerson> villageois;
	
	/// properties
	private double contaminationLevel;
	private int lignes;// = 7;
	private int colonnes;// = 7;
	
	private int nbHospital = 1;
	private int nbMaison = 12;
	private int nbCaserne = 2;
	private int nbTerrainVagues = 34;	
	
	private int nbMedecin = 8;
	private int nbCitoyen = 50;
	private int nbPompier = 12;	
	
	/// parametees
	private int habMaxMaison = 3; /// entre 3 et 6
	private int habMaxCaserne = 4; /// entre 4 et 8
	private int habMaxHospital = 8; /// entre 8 et 12
	private int habMaxTerrainVagues = 4; /// entre 4 et 16
	
	/// Autre
	private int tours = 0;
	ArrayList<Integer> positionAvailable = new ArrayList<Integer>();
	
	/**
	 * Constructeur 
	 * @param 
	 * @return 
	 */
	public Ville(int _lignes, int _colonnes) {
		super();
		/// initialise le tableau pour gerer les places disponibles
		this.lignes = _lignes;
		this.colonnes = _colonnes;
		for (int i = 0; i < _lignes; i++) {
			for (int j = 0; j < _colonnes; j++) {
				this.positionAvailable.add(new Integer((i * _colonnes) + j));
			}
		}
	}
	/**
	 * Constructeur 
	 * @param 
	 * @return 
	 */
	public Ville(	int _lignes,
					int _colonnes,
					int _nbHospital,
					int _nbMaison,
					int _nbCaserne,
					int _nbTerrainVague,
					int _nbMedecin,
					int _nbPompier,
					int _nbCitoyen) {
		this(_lignes, _colonnes);
		this.initVillageois(_nbMedecin, _nbPompier, _nbCitoyen);		
		this.initVille(_lignes, _colonnes, _nbHospital, _nbMaison, _nbCaserne, _nbTerrainVague);
	}
	/////////////////////////////////////////////////////////////////////////////
	////////////////////// Am√©nagement de la ville
	/**
	 * verifie si un lieu est vide ou pas
	 * @param 
	 * @return 
	 */
	public boolean isEmpty(int x, int y) {
		if (this.village[x][y] == null) {
			return true;
		}
		return false;
	}
	/**
	 * verifie si un lieu est vide ou pas
	 * @param 
	 * @return 
	 */
	public boolean isEmpty(Position p) {
		return this.isEmpty(p.getX(), p.getY());
	}
	/**
	 * initialise des villageois
	 * @param 
	 * @return 
	 */
	public void addCase(AbstractCase lieu) {
		if (isEmpty(lieu.getPosition().getX(), lieu.getPosition().getY())) {
			/// ON AJOUTE NOTRE ELEMENT 
			this.village[lieu.getPosition().getX()][lieu.getPosition().getY()] = lieu;
			/// ON NOTE QUE CETTE POSITION EST OCCUPEE ET N'EST PLUS DISPONIBLE
			this.positionAvailable.remove(new 
					Integer((lieu.getPosition().getX() * this.colonnes) + lieu.getPosition().getY()));
		}
	}
	/**
	 * retourne la case donnee
	 * @param 
	 * @return 
	 */
	public AbstractCase getCase(int x, int y) {
		if (!isEmpty(x, y)) {
			return this.village[x][y];
		}
		return null;
	}
	/**
	 * retourne la case donnee
	 * @param 
	 * @return 
	 */
	public AbstractCase getCase(Position p) {
		return this.getCase(p.getX(), p.getY());
	}
	/**
	 * initialise des villageois
	 * @param 
	 * @return 
	 */
	private void initVillageois(int _nbMedecin, int _nbPompier, int _nbCitoyen) {
		this.nbMedecin = _nbMedecin;
		this.nbPompier = _nbPompier;
		this.nbCitoyen = _nbCitoyen;
		this.villageois = new Vector<AbstractPerson>();
		int currentId = 0; /// on rend les villageois uniques
		//////////////////// 8 medecins (par defaut)
		for (int i=0; i<this.nbMedecin; i++) {
			Medecin newMedecin = new Medecin();
			newMedecin.setVille(this);
			newMedecin.setNombreKitDeTraitement(5);
			currentId++;
			newMedecin.setId(currentId);
			this.villageois.addElement(newMedecin);
		}
		//////////////////// 12 pompiers (par defaut)
		for (int i=0; i<this.nbPompier; i++) {
			Pompier newPompier = new Pompier();
			newPompier.setVille(this);
			newPompier.setHasAppareilMesureContaminationLevel(true);
			newPompier.setNiveauPulverisateur(10.0/2);
			currentId++;
			newPompier.setId(currentId);
			this.villageois.addElement(newPompier);
		}
		//////////////////// 50 citoyens (par defaut)
		for (int i=0; i<this.nbCitoyen; i++) {
			Citoyen newCitoyen = new Citoyen();
			/**************************
			 *		 REGLE 2 : citoyens ont niveau de contamination compris entre 0% et 100%.
			 **************************/
			VilleRules.regle2(newCitoyen);
			newCitoyen.setVille(this);
			currentId++;
			newCitoyen.setId(currentId);
			this.villageois.addElement(newCitoyen);
		}
	}
	/**
	 * initialise la ville
	 * @param 
	 * @return 
	 */
	public void initVille(int lignes,
							int colonnes,
							int nbHospital,
							int nbMaison,
							int nbCaserne,
							int nbTerrainVague) {
		this.lignes = lignes;
		this.colonnes = colonnes;
		this.nbHospital = nbHospital;
		this.nbMaison = nbMaison;
		this.nbCaserne = nbCaserne;
		this.nbTerrainVagues = nbTerrainVague;
		village = new AbstractCase[lignes][colonnes];
		//////////////////// 1 hospital en son centre
		for (int h=0; h<this.nbHospital; h++) {
			int index = RandomManager.randInt(0, this.positionAvailable.size());
			Integer position = positionAvailable.get(index);
			Hospital newHospital = new Hospital(position / colonnes, position % colonnes);
			newHospital.setVille(this);
			newHospital.setNombreCitoyensMax(this.habMaxHospital);
			addCase(newHospital);
		}
		//////////////////// 2 casernes au nord-est et sud-ouest
		for (int h=0; h<this.nbCaserne; h++) {
			int index = RandomManager.randInt(0, this.positionAvailable.size());
			Integer position = positionAvailable.get(index);
			Caserne newCaserne = new Caserne(position / colonnes, position % colonnes);
			newCaserne.setVille(this);
			newCaserne.setNombreCitoyensMax(this.habMaxCaserne);
			addCase(newCaserne);
		}
		//////////////////// 12 maisons amenages aleatoirement
		for (int h=0; h<this.nbMaison; h++) {
			int index = RandomManager.randInt(0, this.positionAvailable.size());
			Integer position = positionAvailable.get(index);
			Maison newMaison = new Maison(position / colonnes, position % colonnes);
			newMaison.setVille(this);
			newMaison.setNombreCitoyensMax(this.habMaxMaison);
			addCase(newMaison);
		}
		//////////////////// 34 terrains vagues amenag√©s aleatoirement
		Vector<AbstractCase> r1Array = new Vector<AbstractCase>(); /// servira pour la 'regle1'
		for (int h=0; h<this.nbTerrainVagues; h++) {
			int index = RandomManager.randInt(0, this.positionAvailable.size());
			Integer position = positionAvailable.get(index);
			TerrainVague newTerrainVague = new TerrainVague(position / colonnes, position % colonnes);
			newTerrainVague.setVille(this);
			newTerrainVague.setNombreCitoyensMax(this.habMaxTerrainVagues);
			r1Array.addElement(newTerrainVague);
			addCase(newTerrainVague);
		}
		/**************************
		 *		 REGLE 1 : terrains vagues ont un niveau de contamination superieure a zero
		 **************************/
		VilleRules.regle1(r1Array);
		///////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////	AJOUT LES VILLAGEOIS DANS LE VILLAGE
		this.ajouterVillageoies();
	}
	/**
	 * ajoute les villageois dans les lieux
	 * @param 
	 * @return 
	 */
	public void ajouterVillageoies() {
		int nombrePlaces =  this.habMaxTerrainVagues * this.nbTerrainVagues	+ 
							this.habMaxMaison * this.nbMaison	+
							this.habMaxHospital * this.nbHospital	+
							this.habMaxCaserne * this.nbCaserne;
		int nombreVillageois = this.villageois.size();
		Log.Disp(nombreVillageois+"Villageois / "+nombrePlaces+"Places");
		if (nombreVillageois>nombrePlaces) {
			Log.Disp("ERREUR : PAS ASSEZ DE PLACES POUR TOUT LE MONDE (reduire le nombre d'habitants )");
			return;
		}
		int counter = 0;
		while (counter < nombreVillageois) {
			int randX = RandomManager.randInt(0, this.lignes);
			int randY = RandomManager.randInt(0, this.colonnes);
			if (!this.isEmpty(randX, randY)) {
				AbstractCase c =this.getCase(randX, randY);
				int habitants = c.getVillageois().size();
				int limite = c.getNombreCitoyensMax();
				if ( habitants < limite ) {
					/// on a joute
					AbstractPerson p = this.villageois.get(counter);
					c.getVillageois().addElement(p);
					/// echange des positions
					p.setPosition(c.getPosition());
					counter++;
				}
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	////////////////////// Actions et Mouvements sur la Ville
	/**
	 * jours suivant
	 * @param 
	 * @return 
	 */
	public void oneStep() {
		if (this.village == null) {
			Log.Disp( "ERREUR : VILLAGE NON INITIALISE");
			return ;
		}
		Log.Disp("*************************");
		Log.Disp("JOUR:" + this.tours );
		Log.Disp("*************************");
		this.tours ++;
		doMove();
		doActions();
	}
	/**
	 * avance d'un pas
	 * @param 
	 * @return 
	 */
	public void doActions(){
		///// Actions des personnes
		for (int i=0; i<this.villageois.size(); i++) {
			AbstractPerson person = this.villageois.elementAt(i);
			person.action();
		}
		///// Actions des batiments
		for (int i = 0; i < this.lignes; i++) {
			for (int j = 0; j < this.colonnes; j++) {
				if (!isEmpty(i, j)) {
					AbstractCase lieu = getCase(i, j);
					lieu.action();
				}	
			}
		}
	}
	/**
	 * deplacement
	 * @param 
	 * @return 
	 */
	public void doMove(){
		for (int i=0; i<this.villageois.size(); i++) {
			AbstractPerson person = this.villageois.elementAt(i);
			person.deplacer();
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	////////////////////// DEBUG	
	@Override
	public String toString() {
		if (this.village == null) {
			return "ERREUR : VILLAGE NON INITIALISE";
		}
		String result = "Ville [lignes=" + lignes + ", colonnes=" + colonnes+ "]\n";
		int nbSousLinesParCase = 9;
		for (int i=0; i<this.lignes; i++) {
			for (int l=0; l<nbSousLinesParCase; l++) {
				for (int j=0; j<this.colonnes; j++) {
					if (!isEmpty(i, j)) {
						AbstractCase c = this.getCase(i, j);
						result += c.toString(l);
					}
					else {
						result +="...................";
					}
					result +="";			
				}
				result +="\n";
			}
			result +="";
		}
		return result;
	}
	
	/**
	 * hospitals
	 * @param 
	 * @return 
	 */
	public Vector<AbstractCase> getHospitals() {
		Vector<AbstractCase> toReturn = new Vector<AbstractCase>();
		for (int i=0; i<lignes; i++) {
			for (int j=0; j<colonnes; j++) {
				AbstractCase currentCase = this.getCase(i,j);
				if (currentCase instanceof Hospital)
					toReturn.add(currentCase);
			}
		}
		return toReturn;
	}
	/**
	 * casernes
	 * @param 
	 * @return 
	 */
	public Vector<AbstractCase> getCasernes() {
		Vector<AbstractCase> toReturn = new Vector<AbstractCase>();
		for (int i=0; i<lignes; i++) {
			for (int j=0; j<colonnes; j++) {
				AbstractCase currentCase = this.getCase(i,j);
				if (currentCase instanceof Caserne)
					toReturn.add(currentCase);
			}
		}
		return toReturn;
	}
	/**
	 * maisons
	 * @param 
	 * @return 
	 */
	public Vector<AbstractCase> getMaisons() {
		Vector<AbstractCase> toReturn = new Vector<AbstractCase>();
		for (int i=0; i<lignes; i++) {
			for (int j=0; j<colonnes; j++) {
				AbstractCase currentCase = this.getCase(i,j);
				if (currentCase instanceof Maison)
					toReturn.add(currentCase);
			}
		}
		return toReturn;
	}
	/////////////////////////////////////////////////////////////////////////////
	////////////////////// Getters/Setters
	public AbstractCase[][] getVillage() {
		return village;
	}
	public void setVillage(AbstractCase[][] village) {
		this.village = village;
	}
	public Vector<AbstractPerson> getVillageois() {
		return villageois;
	}
	public void setVillageois(Vector<AbstractPerson> villageois) {
		this.villageois = villageois;
	}
	public double getContaminationLevel() {
		return contaminationLevel;
	}
	public void setContaminationLevel(double contaminationLevel) {
		this.contaminationLevel = contaminationLevel;
	}
	public int getLignes() {
		return lignes;
	}
	public void setLignes(int lignes) {
		this.lignes = lignes;
	}
	public int getColonnes() {
		return colonnes;
	}
	public void setColonnes(int colonnes) {
		this.colonnes = colonnes;
	}
	public int getNbHospital() {
		return nbHospital;
	}
	public void setNbHospital(int nbHospital) {
		this.nbHospital = nbHospital;
	}
	public int getNbMaison() {
		return nbMaison;
	}
	public void setNbMaison(int nbMaison) {
		this.nbMaison = nbMaison;
	}
	public int getNbCaserne() {
		return nbCaserne;
	}
	public void setNbCaserne(int nbCaserne) {
		this.nbCaserne = nbCaserne;
	}
	public int getNbTerrainVagues() {
		return nbTerrainVagues;
	}
	public void setNbTerrainVagues(int nbTerrainVagues) {
		this.nbTerrainVagues = nbTerrainVagues;
	}
	public int getNbMedecin() {
		nbMedecin = 0;
		for (int i=0; i<villageois.size(); i++) {
			AbstractPerson currentPerson = villageois.elementAt(i);
			if (currentPerson instanceof Medecin)
				nbMedecin++;
		}
		return nbMedecin;
	}
	public void setNbMedecin(int nbMedecin) {
		this.nbMedecin = nbMedecin;
	}
	public int getNbCitoyen() {
		nbCitoyen = 0;
		for (int i=0; i<villageois.size(); i++) {
			AbstractPerson currentPerson = villageois.elementAt(i);
			if (currentPerson instanceof Citoyen)
				nbCitoyen++;
		}
		return nbCitoyen;
	}
	public void setNbCitoyen(int nbCitoyen) {
		this.nbCitoyen = nbCitoyen;
	}
	public int getNbPompier() {
		nbPompier = 0;
		for (int i=0; i<villageois.size(); i++) {
			AbstractPerson currentPerson = villageois.elementAt(i);
			if (currentPerson instanceof Pompier)
				nbPompier++;
		}
		return nbPompier;
	}
	public void setNbPompier(int nbPompier) {
		this.nbPompier = nbPompier;
	}
	public ArrayList<Integer> getPositionAvailable() {
		return positionAvailable;
	}
	public void setPositionAvailable(ArrayList<Integer> positionAvailable) {
		this.positionAvailable = positionAvailable;
	}
	
	public int getTours() {
		return tours;
	}
	public void setTours(int tours) {
		this.tours = tours;
	}
	
}
