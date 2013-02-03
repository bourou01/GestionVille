
/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */


package Cases;

import java.util.Vector;

import Debug.Str;
import Helpers.Position;
import Helpers.SharedMethods;
import Persons.AbstractPerson;
import Persons.Citoyen;
import Persons.Medecin;
import Persons.PersonRules;
import Persons.Pompier;
import SharedInterfaces.ActionManager;
import SharedInterfaces.ContagionManager;
import Villes.Ville;

/// LES CASES NE PEUVENT FAIRE QUE DES ACTIONS ET ETRE CONTAMINEES
public class AbstractCase implements ActionManager, ContagionManager{
	
	private Position position;
	private int nombreCitoyensMax;
	private Ville ville;
	
	private double niveauContamination;
	private Vector<AbstractPerson> villageois;
	
	private Vector<AbstractPerson> citoyens;
	private Vector<AbstractPerson> medecins;
	private Vector<AbstractPerson> pompiers;
	
	
	/**
	 * Constructeur par d√©faut
	 * @param 
	 * @return 
	 */
	public AbstractCase() {
		super();
		this.villageois = new Vector<AbstractPerson>();
		this.citoyens = new Vector<AbstractPerson>();
		this.medecins = new Vector<AbstractPerson>();
		this.pompiers = new Vector<AbstractPerson>();
	}
	/**
	 * Constructeur par d√©faut
	 * @param 
	 * @return 
	 */
	public AbstractCase(int x, int y) {
		this();
		this.position = new Position(x, y);
		
	}
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'ContagionManager'
	@Override
	public void contaminate() {
		// TODO Auto-generated method stub	
	}
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// INTERFACE DE 'ActionManager'
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// Regles d'entree sortie
	public boolean accueillirPersonne(AbstractPerson newP) { /// ajoute tout le monde
		
		return this.addPerson(newP);
		
	}
	
	public boolean addPerson(AbstractPerson newP) {
		/**************************
		 *		 REGLE 3 : depassement de pupulation des citoyens (uniquement)
		 **************************/
		boolean depassement = PersonRules.regle3(this);

		if (!depassement)
			return false;
		else
			this.villageois.add(newP);
		
		
		/**************************
		 *		 REGLE 7 : Un pompier entrant dans une case brûle automatiquement tous les corps des morts. + reçoi pulverisateur
		 **************************/
		CasesRules.regle7(this, newP);
		
		//Log.Disp(this.ID() + "ACCUEIL " + newP.ID());
		return true;		
	}

	/////////////////////////////////////////////////////////////////////////////
	////////////////////// DEBUG
	
	public String ID() {
		return this.toString()+"{"+this.position+"}";
	}
	
	@Override
	public String toString() {
		
		String result = "[ ";
		
		if (this instanceof Caserne)
			result += "Caserne";
		else if (this instanceof Hospital)
			result += "Hospital";
		else if (this instanceof Maison)
			result += "Maison";
		else if (this instanceof TerrainVague)
			result += "TerrainVague";
		else
			result += "-@------"; /// erreur

			result += " ]";
		
		return result ;
	}
	public String toString(int row) {
		String result = "";
		
		if (row > 0)
			result += "|";
		
		switch (row) {
		case 0:
			result  += " _________________ ";
			break;
		case 1:
			result  += this.myType();
			break;
		case 2:
			result  += "-----------------";
			break;
		case 3:
			result  += "Cont:"+Str.intToStr((int)(100*this.getNiveauContamination())) + "%        ";
			break;
		case 4:
			result  +=  "Pop:"+Str.intToStr(this.getVillageois().size())+"          ";
			break;
		case 5:
			result  += this.synthesePersons(this.getCitoyens(), "C:");
			break;
		case 6:
			result  += this.synthesePersons(this.getPompiers(), "P:"); 
			break;
		case 7:
			result  += this.synthesePersons(this.getMedecins(), "M:");
			break;		
		case 8:
			result  += "_________________";
			break;
		default:
			break;
		}
		
		if (row > 0)
			result += "|";
		
		return result;
	}
	
	public String synthesePersons(Vector<AbstractPerson> ap, String type) {
		
		String result = type;
		
		result += Str.intToStr(SharedMethods.getSainsFrom(ap).size());
		result += " I:";
		result += Str.intToStr(SharedMethods.getMaladesFrom(ap).size());
		result += " D:";
		result += Str.intToStr(SharedMethods.getMortsFrom(ap).size());
		result +="";
		return result;
	}

	/////////////////////////////////////////////////////////////////////////////
	////////////////////// FIN DEBUG
	private String myType() {
		
		String result = "";
		//.______________.
		if (this instanceof Caserne)
			result += "     Caserne     ";
		else if (this instanceof Hospital)
			result += "     Hopital     ";
		else if (this instanceof Maison)
			result += "     Maison      ";
		else if (this instanceof TerrainVague)
			result += "   TerrainVague  ";
		else
			result += "________________"; /// erreur

			result += "";
		
		return result ;
	}
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// SETTERS/GETTERS
	
	public boolean isContaminated() {
		return this.niveauContamination>0 ? true : false;
	}

	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public Vector<AbstractPerson> getVillageois() {
		return villageois;
	}
	public void setVillageois(Vector<AbstractPerson> villageois) {
		this.villageois = villageois;
	}
	public int getNombreCitoyensMax() {
		return nombreCitoyensMax;
	}
	public void setNombreCitoyensMax(int nombreCitoyensMax) {
		this.nombreCitoyensMax = nombreCitoyensMax;
	}
	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}
	public Vector<AbstractPerson> getCitoyens() {
		citoyens.clear();
		citoyens = new Vector<AbstractPerson>();
		for (int i=0; i<this.villageois.size(); i++) {
			AbstractPerson p = this.villageois.elementAt(i);
			if (p instanceof Citoyen) {
				citoyens.add(p);
			}
		}
		return citoyens;
	}
	public void setCitoyens(Vector<AbstractPerson> citoyens) {
		this.citoyens = citoyens;
	}
	public Vector<AbstractPerson> getMedecins() {
		medecins.clear();
		medecins = new Vector<AbstractPerson>();
		for (int i=0; i<this.villageois.size(); i++) {
			AbstractPerson p = this.villageois.elementAt(i);
			if (p instanceof Medecin) {
				medecins.add(p);
			}
		}
		return medecins;
	}
	public void setMedecins(Vector<AbstractPerson> medecins) {
		this.medecins = medecins;
	}
	public Vector<AbstractPerson> getPompiers() {
		pompiers.clear();
		pompiers = new Vector<AbstractPerson>();
		for (int i=0; i<this.villageois.size(); i++) {
			AbstractPerson p = this.villageois.elementAt(i);
			if (p instanceof Pompier) {
				pompiers.add(p);
			}
		}
		return pompiers;
	}
	public void setPompiers(Vector<AbstractPerson> pompiers) {
		this.pompiers = pompiers;
	}
	public double getNiveauContamination() {
		return niveauContamination;
	}
	public void setNiveauContamination(double niveauContamination) {
		this.niveauContamination = niveauContamination;
	}
}
