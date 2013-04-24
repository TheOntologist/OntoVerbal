import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
/** This program collect entities literal and change them into natural language
 * 
 * @author fennieliang
 *
 */
public class OWLLabelLiteralCollector {
	public static OWLAnnotationValue getLabel(OWLAnnotationValue val,final OWLDataFactory df){
				OWLAnnotationValue newVal = val
					.accept(new OWLAnnotationValueVisitorEx<OWLAnnotationValue>() {
						@Override
						public OWLAnnotationValue visit(OWLLiteral arg0) {
							String lit = arg0.getLiteral();
							String s = addSpace(lit);
							return df.getOWLLiteral(s);
						}

						@Override
						public OWLAnnotationValue visit(
								OWLAnonymousIndividual arg0) {
							return null;

						}

						@Override
						public OWLAnnotationValue visit(IRI arg0) {
							return null;
						}
					});{}
		return newVal;
	}
	public static String addSpace(String s) {
		if (!s.contains("#")){
			s=s.substring(0,s.lastIndexOf("/"))+"/#"+s.substring(s.lastIndexOf("/")+1);
		}
		if (!s.contains("/#")){
			s=s.substring(0,s.lastIndexOf("#"))+"/#"+s.substring(s.lastIndexOf("#")+1);
		}
		String change = s.toString().replaceAll("<.*#", "").replaceAll(">", "");
		change = change.replaceAll("_", " ");
		System.out.println(s);
		Pattern p = Pattern.compile("[a-z][A-Z]");
		Matcher m = p.matcher(change);
		while (m.find()) {
			String groupStr = m.group();
			String a = groupStr.substring(0, 1);
			String b = groupStr.substring(1);
			change = change.replaceAll(groupStr, a + " " + b);
		}
		return change;
	}
}
