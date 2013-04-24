public class ChangeArticle {
		public static String getRight(String input ) {
			String input1=input;
			input="";
			while(input1.toLowerCase().contains(" a ")){
				if (input1.substring(input1.toLowerCase().indexOf(" a ")+3,input1.toLowerCase().indexOf(" a ")+4).toLowerCase().matches("[aeiou]")){
					input=input+input1.substring(0,input1.toLowerCase().indexOf(" a "))+" an ";
					input1=input1.substring(input1.toLowerCase().indexOf(" a ")+3);
				}
				else{
					input=input+input1.substring(0,input1.toLowerCase().indexOf(" a ")+3);
					input1=input1.substring(input1.toLowerCase().indexOf(" a ")+3);
				}
			}
			input=input+input1;

			if (input.toLowerCase().startsWith("a ") && input.substring(2,3).toLowerCase().matches("[aeiou]")){
				input="an "+input.substring(2);
			}
			if (input.startsWith("a")){
				input=input.substring(0, 1).toUpperCase()+input.substring(1);
			}
			return input;
		}
}
