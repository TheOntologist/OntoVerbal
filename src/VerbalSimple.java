import org.semanticweb.owlapi.model.OWLClass;

public class VerbalSimple {
	public static String producePara(OWLClass selectedClass, Object[] in,
			String type) {
		String p = "";
		if (type.equals("sup")) {
			p = "A " + selectedClass + " is a kind of "
					+ ListClasses.getListedClasses(in, "no", "and");
		} 
		else if (type.equals("sub")) {
			if (in.length > 1) {
				p = "More specialised kinds of " + selectedClass + " are\n"
						+ ListClasses.getListedClasses(in, "no", "and");
			} else {
				p = "A more specialised kind of " + selectedClass + " is "
						+ ListClasses.getListedClasses(in, "no", "and");
			}

		}
		else if (type.equals("equ")) {
			p = "a " + selectedClass + " is defined as "
					+ ListClasses.getListedClasses(in, "yes", "and");
		}
		else if (type.equals("dis")) {
			String get=ListClasses.getListedClasses(in, "yes", "and");
			if (!get.isEmpty()){
			p = "a " + selectedClass + " is different from "
					+ get;
			}
		}
		else{
			if (in.length > 1) {
				p = "a " + selectedClass + " has instances "
						+ ListClasses.getListedClasses(in, "no", "and");
			} else {

				p = "a " + selectedClass + " has an instance "
						+ ListClasses.getListedClasses(in, "no", "and");
			}
		}
		return p;
	}
}
