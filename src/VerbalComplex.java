
import org.semanticweb.owlapi.model.OWLClass;

public class VerbalComplex {
	public static String producePara(OWLClass selectedClass, Object[] in ,String type) {
		String p = "";
		if (type.equals("equ")) {
			p = "a " + selectedClass + " is defined as a ";
		}
		else {
			
			p = "a " + selectedClass + " is a kind of ";
		}
		for (Object s1 : in) {
			String s=s1.toString();
			s = s.replaceAll("ObjectUnionOf", "OR");
			s = s.replaceAll("ObjectIntersectionOf", "AND");
			s = s.replaceAll("ObjectComplementOf", "NOT");
			s = s.replaceAll("ObjectSomeValuesFrom", "SOME");
			s = s.replaceAll("ObjectOneOf", "ONEOF");
			s = s.replaceAll("ObjectAllValuesFrom", "ONLY");
			s = s.replaceAll("ObjectHasValue", "VALUE");
			s = s.replaceAll("ObjectExactCardinality", "EXACTLY");
			s = s.replaceAll("ObjectMinCardinality", "ATLEAST");
			s = s.replaceAll("ObjectMaxCardinality", "ATMOST");
			s = OV_Value.producePara(s);
			p=p+s+".\n";
		}
		return p;
	}

}
