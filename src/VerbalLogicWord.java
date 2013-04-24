
public class VerbalLogicWord {
	public static String check(String input) {

		String[] all = null;
		String need = "";
		if (input.contains(";")) {
			all = input.split(";");
		} else {
			input = input + ";";
			all = input.split(";");
		}
		for (String input1 : all) {
				input1 = input1.replaceAll(">some a<", "> a <");
				input1 = input1.replaceAll(">some<", "> a <");
				input1 = input1.replaceAll(">that<", "> that <");
				input1 = input1.replaceAll(">and a<", "> and a <");
				input1 = input1.replaceAll(">and<", "> and <");
				input1 = input1.replaceAll(">or a<", "> or a <");
				input1 = input1.replaceAll(">or<", "> or <");
				input1 = input1.replaceAll(">value a<", "> <");
				input1 = input1.replaceAll(">value<", "> <");
				input1 = input1.replaceAll(">someoneof<", "> <");
				need = need + input1+";";
		}
		need = need.replaceAll(" ,", ",");
		while (need.endsWith(";")) {
			need = need.substring(0, need.length() - 1);
		}
		return need;
	}
}
