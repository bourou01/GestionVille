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
	
	/**
	 * LOG POUR LES PERSONNES
	 * @param 
	 * @return 
	 */
	public static void DispP(int key, Object s) {
		if ((key == personIdAcess) || (personIdAcess == -1)) {
			logIndex++;
			//System.out.println(s);
			//System.out.println("#"+p+"{"+p.getId()+"}");
		}
	}

}
