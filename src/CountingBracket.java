
public class CountingBracket {
	public static int producePara(String inputString) {
		String s2=inputString;
		int l=0,found=0;
			for (int i = 0; i < s2.length(); i++) {
				if (s2.charAt(i) == (char) 40) {
					l++;
				}
				if (s2.charAt(i) == (char) 41) {
					l--;
				}
				if (l==0&&i>s2.indexOf("(")) {
					found = i;
					i=s2.length();
				}
			}
		return found;
	}
}
