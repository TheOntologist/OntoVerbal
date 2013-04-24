public class ListClasses {
	public static String getListedClasses(Object[] in, String article,
			String lastTwoConnection) {
		int i = 0;
		String result = "";
		int length = in.length;
		for (Object s : in) {
			String key = "";
			String a = s.toString();
			if (a.contains("Intersection")) {
				key = "and";
			}
			if (a.contains("Union")) {
				key = "or";
			}
			if (key != "") {
				a = a.substring(a.indexOf("(") + 1, a.indexOf(")"));
				String[] a1 = a.split(" ");
				int j = 0;
				for (String a2 : a1) {
					if (article.equals("yes")) {
						result += " a " + a2;
					} else {

						result += a2;
					}
					String connection = getPontu(j, a1.length, key);
					result += connection;
					j++;
				}
			} else {
				if (article.equals("yes")) {
					result += " a " + a;
				} else {
					result += a;
				}

				String connection = getPontu(i, length, "and");
				result += connection;
			}
			i++;
		}
		return result;

	}

	private static String getPontu(int i, int length, String key) {
		String s = "";
		if (i < length - 2) {
			s = ",\n";
		} else if (i == length - 2) {
			s = "\n" + key + "\n";
		} else {
			s = ".\n";
		}
		return s;

	}
}
