package Debug;

public class Str {
	static public String intToStr(int val) {
		
		String s = "0000000000000000000000000000000000000000   ";
		s = s+val;		
		String result = s.substring(s.length()-3, s.length());
		return result;
		
	}

}
