/**
 * @author ABDULLATIF Mouhamadi et LUCAS COSTA Amaro
 * @version 
 */
package Debug;

public class Log {
	static int logIndex = 0;
	static int personIdAcess = -1; /** Tout le monde */
	
	/**
	 * LOG GENERAL
	 * @param 
	 * @return 
	 */
	public static void Disp(Object s) {
		logIndex++;
		System.out.println("Log "+logIndex+": "+s);
	}
}
