import java.awt.BorderLayout;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.protege.editor.owl.model.inference.OWLReasonerManager;
import org.protege.editor.owl.model.inference.ReasonerStatus;
import org.protege.editor.owl.ui.view.cls.AbstractOWLClassViewComponent;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
/**
 * This program produce natural language descriptions for ontology classes
 * 
 * @author Fennie Liang
 * 
 */
public class OntoVerbalDescriptionTab extends AbstractOWLClassViewComponent {
	private static final long serialVersionUID = -2896209622461162777L;
	private JTextArea namesComponent;

	public void initialiseClassView() throws Exception {
		setLayout(new BorderLayout(6, 6));
		namesComponent = new JTextArea();
		add(new JScrollPane(namesComponent), BorderLayout.CENTER);
	}

	protected OWLClass updateView(OWLClass selectedClass) {
		namesComponent.setText("");
		if (selectedClass != null) {
			TreeSet<String> anno = getAnnotations();
			String verbal = getVerbalisation(selectedClass);
			verbal = VerbalLogicWord.check(verbal);
			verbal = ReplaceClassNameWithAnnotation.replaceAnno(verbal, anno);
			verbal = ChangeArticle.getRight(verbal);
			namesComponent.append(verbal);
		}
		return selectedClass;
	}

	public void disposeView() {
		// do nothing
	}

	private TreeSet<String> getAnnotations() {
		TreeSet<String> anno = new TreeSet<String>();
		Set<OWLAnnotationAssertionAxiom> annx = getOWLModelManager()
				.getActiveOntology().getAxioms(AxiomType.ANNOTATION_ASSERTION);
		for (OWLAnnotationAssertionAxiom an : annx) {
			if (an.toString().contains("rdfs:label")) {
				String label = "<"
						+ an.getSubject().toString()
						+ ">"
						+ "="
						+ an.getValue().toString().replaceFirst(".*?\"", "")
								.replaceFirst("\".*", "");
				anno.add(label);
			}
		}

		return anno;
	}

	private String getVerbalisation(OWLClass selectedClass) {
		String result = "";
		Object[] sup = null, sub = null, equ = null, dis = null, ind = null;

		OWLReasonerManager reasonerManager = getOWLModelManager()
				.getOWLReasonerManager();

		if (reasonerManager.getReasonerStatus() == ReasonerStatus.INITIALIZED) {

			sup = reasonerManager.getCurrentReasoner()
					.getSuperClasses(selectedClass, true).getFlattened()
					.toArray();

			sub = reasonerManager.getCurrentReasoner()
					.getSubClasses(selectedClass, true).getFlattened()
					.toArray();

			dis = reasonerManager.getCurrentReasoner()
					.getDisjointClasses(selectedClass).getFlattened().toArray();
			ind = reasonerManager.getCurrentReasoner()
					.getInstances(selectedClass, true).getFlattened().toArray();
		} else {
			sup = selectedClass.getSuperClasses(
					getOWLModelManager().getActiveOntology()).toArray();
			sub = selectedClass.getSubClasses(
					getOWLModelManager().getActiveOntology()).toArray();
			dis = selectedClass.getDisjointClasses(
					getOWLModelManager().getActiveOntologies()).toArray();
			ind = selectedClass.getIndividuals(
					getOWLModelManager().getActiveOntologies()).toArray();

		}
		equ = selectedClass.getEquivalentClasses(
				getOWLModelManager().getActiveOntology()).toArray();

		Object[] ax = selectedClass.getReferencingAxioms(
				getOWLModelManager().getActiveOntology()).toArray();
		int control = 0;
		Object[] simSup = null, comSup = null;
		if (sup != null) {
			comSup = getCategory(sup, "complex");
			simSup = getCategory(sup, "simple");
		}

		Object[] simSub = null, comSub = null;
		if (sub != null) {
			comSub = getCategory(sub, "complex");
			simSub = getCategory(sub, "simple");
		}
		Object[] simEqu = null, comEqu = null;
		if (equ != null) {
			comEqu = getCategory(equ, "complex");
			simEqu = getCategory(equ, "simple");
		}

		if (simSup != null) {
			if (simSup.length > 0) {
				result +=  VerbalSimple
								.producePara(selectedClass, simSup, "sup");
				control = 1;
			}
		}
		if (simSub != null) {
			if (simSub.length > 0) {
				result +=  VerbalSimple
								.producePara(selectedClass, simSub, "sub");
				control = 1;
			}

		}
		if (simEqu != null) {
			if (simEqu.length > 0) {
				String get = VerbalSimple
								.producePara(selectedClass, simEqu, "equ");
				if (control == 1) {
					result += "In addition, " + get;
				} else {
					get = get.substring(0, 1).toUpperCase() + get.substring(1);
					result += get;
				}
				control = 1;
			}

		}

		if (dis != null) {
			if (dis.length>0) {
				String get =  VerbalSimple.producePara(selectedClass, dis, "dis");
				if (control == 1) {
					result += "Also, " + get;
				} else {

					get = get.substring(0, 1).toUpperCase() + get.substring(1);
					result += get;
				}

				control = 1;
			}
		}
		int control2 = 0;
		if (comSup != null) {
			if (comSup.length > 0) {
				String get = VerbalComplex.producePara(selectedClass, comSup,
								"sup");
				if (control == 1) {
					result += "Additionally, " + get;
				} else {
					get = get.substring(0, 1).toUpperCase() + get.substring(1);
					result += get;
				}
				control2 = 1;
			}
		}
		if (comSub != null) {
			if (comSub.length > 0) {
				String get = VerbalComplex.producePara(selectedClass, comSub,
								"sub");
				if (control2 == 1) {
					result = result.substring(0, result.lastIndexOf("."))
							+ ";\n" + get;
				} else {
					if (control == 1) {

						result += "Additionally, " + get;
					} else {

						get = get.substring(0, 1).toUpperCase()
								+ get.substring(1);
						result += get;
					}
				}
				control2 = 1;
			}
		}
		if (comEqu != null) {
			if (comEqu.length > 0) {
				String get = VerbalComplex.producePara(selectedClass, comEqu,
								"equ");
				if (control2 == 1) {
					result = result.substring(0, result.lastIndexOf("."))
							+ ";\n" + get;
				} else {
					if (control == 1) {

						result += "Additionally, " + get;
					} else {

						get = get.substring(0, 1).toUpperCase()
								+ get.substring(1);
						result += get;
					}
				}
				control2 = 1;
			}
		}
		if (ind != null) {
			if (ind.length > 0) {
				String get = VerbalSimple.producePara(selectedClass, ind, "ind");
				if (control == 1 || control2 == 1) {
					result += "Moreover, " + get;
				} else {
					get = get.substring(0, 1).toUpperCase() + get.substring(1);
					result += get;
				}
				control = 1;
			}
		}
		if (ax != null) {
			if (ax.length > 0) {
				String other = VerbalOtherRelevantAspect.producePara(
						selectedClass, ax);
				if (!other.isEmpty()) {
					Object[] ob = other.split(";\n");
					if (ob.length > 1) {
						result += "Other relevant aspects of a "
								+ selectedClass.toString()
								+ " include the following:\n";
					} else {
						result += "Another relevant aspect of a "
								+ selectedClass.toString() + " is that\n";
					}
					result += other;
				}
			}
		}
		return result;
	}

	private Object[] getCategory(Object[] in, String var) {
		Set<Object> simple = new TreeSet<Object>();
		Set<Object> complex = new TreeSet<Object>();
		for (Object s : in) {
			if (!s.toString().contains("owl:Nothing")) {
				if (!s.toString().contains("Value")&&!s.toString().contains("Cardinality")) {

					simple.add(s);
				} else {
					complex.add(s);
				}
			}
		}
		if (var.equals("simple")) {
			return simple.toArray();
		} else {
			return complex.toArray();
		}

	}

}
