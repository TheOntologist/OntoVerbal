
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceClassNameWithAnnotation {
		public static String replaceAnno(String input, Set<String> setAnno ) {

			String cl="",label="";
			for(String s:setAnno){
				cl=s.substring(0, s.indexOf("="));
				label=s.substring(s.indexOf("=")+1);
				label=label.toLowerCase();
				Pattern get = Pattern.compile(cl);
		        Matcher action = get.matcher(input);
		        input = action.replaceAll(label);
			}
	  		return input;
		}
}
