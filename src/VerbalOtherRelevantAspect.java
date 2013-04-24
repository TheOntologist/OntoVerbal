import org.semanticweb.owlapi.model.OWLClass;

public class VerbalOtherRelevantAspect {
	public static String producePara(OWLClass selectedClass, Object[] in) {
		String p = "";
		String lead = "";
		int judge = 0;
		for (Object s1 : in) {
			String s = s1.toString();
			if (!s.contains("ObjectProperty")) {
				if (s.contains("Object")) {
					lead = s.substring(s.indexOf("<"), s.indexOf(">") + 1);
					if (!lead.equals(selectedClass.toString())) {
						if (s.contains("SubClassOf")) {
							p = p + " a " + lead + " is a kind of ";
							s = s.replace("SubClassOf", "");
						}
						if (s.contains("EquivalentClasses")) {
							p = p + " a " + lead + " is defined as a ";
							s = s.replace("EquivalentClasses", "");
						}
						s = s.replaceAll("\\) \\)", "\\)\\)");
						s = s.substring(1, s.length() - 1);
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
						s = s.substring(s.indexOf(" ") + 1);
						s = OV_Value.producePara(s);
						p = p +  s + ";\n";
					}
				}
			}
		}
		if (p != "") {
			p = p.substring(0, p.lastIndexOf(";\n"));
			p = p + ".";
		}
		return p;
	}

}
