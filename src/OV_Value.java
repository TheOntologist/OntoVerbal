public class OV_Value {
	public static String producePara(String input) {
		while (input.contains("(")) {
			// System.out.println("doing="+input);
			int find = input.indexOf("(");
			int point = 0, start = 0;
			String s1 = "", s2 = "", s3 = "";
			s1 = input.substring(0, find);// first part usually starts with a
											// white space
			s2 = input.substring(find);
			point = CountingBracket.producePara(s2);// find a completed
													// point for this
													// logic description
			s3 = s2.substring(point + 1);
			s2 = s2.substring(1, point);
			/**
			 * second part this has removed the first level of ()
			 **/
			for (int i = s1.length() - 1; i > 0; i--) {// find the index of the
														// keyword
				if (!s1.substring(i, i + 1).matches("[A-Z]")) {
					start = i;
					i = 0;
				}
			}
			if (start == 0) {
				start = -1;
			}
			String keyword = s1.substring(start + 1);// find the keyword such as
														// AND, OR, SOME...
			s1 = s1.substring(0, start + 1);
			point = CountingBracket.producePara(s2);
			/**
			 * find one level down of the keywork's right bracket end point
			 **/
			if (point == 0) {// found noting then remove bracket and replace keyword into its places

				s2 = s2.replaceAll("> <", ">" + keyword.toLowerCase() + " a<");
				input = s1 + s2 + s3;
			} else if (point < s2.length() - 1) {// check how may conjunction in this part of string
				while (point < s2.length() - 1) {
					String part1 = s2.substring(0, point + 1);
					String part2 = s2.substring(point + 2);
					int firstSpace = part1.indexOf(" ");
					int firstLeftBracket = part1.indexOf("(");
					if (firstSpace < firstLeftBracket) {// check any conjunction
														// appears before (..)
						part1 = part1.replaceFirst(" ", "that");
					}
					s2 = part1 + keyword.toLowerCase() + part2;
					int adding = s2.indexOf("(", point);
					point = CountingBracket.producePara(s2.substring(s2
							.indexOf("(", point)));
					if (point == 0) {
						point = s2.length();
					}
					point = point + adding;
				}
				input = s1 + s2 + s3;
			} else {
				int check = s2.indexOf("(");
				String part1 = s2.substring(0, check);
				String part2 = s2.substring(check);
				part1 = part1.replaceAll("> <", ">"+keyword.toLowerCase()+"<");
				if (keyword.equals("AND") || keyword.equals("OR")) {
					part1 = part1.replaceFirst(" ", "that");
				} else {
					part1 = part1.replaceFirst(" ", keyword.toLowerCase());
				}
				s2 = part1 + part2;
				input = s1 + s2 + s3;
			}
		}
		while (input.contains("someObjectOneOf")){
			int a=input.lastIndexOf("someObjectOneOf");
			String s1=input.substring(0, a);
			String s2=input.substring(a);
			s2=s2.replaceFirst("someObjectOneOf", "");
			int found=s1.lastIndexOf(">some<");
			s1=s1.substring(0, found+1)+"or"+s1.substring(found+5);
			input=s1+s2;
		}
		return input;
	}
}
