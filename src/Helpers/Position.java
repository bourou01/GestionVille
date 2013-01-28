
/**
 * @author ABDULLATIF Mouhamadi
 * @version 
 */

package Helpers;

public class Position {
	private int x;
	private int y;
	
	
	/**
	 * Constructeur par défaut
	 * @param 
	 * @return 
	 */
	public Position() {
		super();
	}
	/**
	 * Constructeur avec les positions
	 * @param 
	 * @return 
	 */
	public Position(int x, int y) {
		this();
		this.x = x;
		this.y = y;
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// SETTERS/GETTERS
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setPosition(Position newP) {
		this.x = newP.x;
		this.y = newP.y;
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////// DEBUG
	@Override
	public String toString() {
		return "#Pos("+this.x+","+this.y+")";
	}
	

}
