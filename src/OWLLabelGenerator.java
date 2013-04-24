import org.protege.editor.owl.ui.action.ProtegeOWLAction;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * This program annotates classes, properties and individuals into RDF labels
 * for labelless entities from their URIs fragment 
 * by splitting camel cases or removing underscores
 * into natural language words
 * 
 * @author Fennie Liang
 * 
 */
public class OWLLabelGenerator extends ProtegeOWLAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void initialise() throws Exception {
	}

	public void dispose() throws Exception {
	}

	public void actionPerformed(ActionEvent event) {

		OWLOntology ont = getOWLModelManager().getActiveOntology();
		OWLOntologyManager owlOntologyManager = ont.getOWLOntologyManager();
		OWLDataFactory factory = owlOntologyManager.getOWLDataFactory();
		OWLAnnotationProperty property = factory
				.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

		StringBuffer message = new StringBuffer(
				"RDF labels have been generated successfully\n");

		getClassChanges(ont, factory, property);
		getIndividualChanges(ont, factory, property);
		getObjectPropertyChanges(ont, factory, property);
		getDataPropertyChanges(ont, factory, property);
		JOptionPane.showMessageDialog(getOWLWorkspace(), message.toString());
	}

	protected ArrayList<OWLAxiom> getClassChanges(OWLOntology ontology,
			OWLDataFactory factory, OWLAnnotationProperty property) {
		ArrayList<OWLAxiom> changes = new ArrayList<OWLAxiom>();

		{
			for (OWLClass cl : ontology.getClassesInSignature()) {
				if (!cl.getAnnotations(ontology).toString()
						.contains("rdfs:label")) {
					OWLAnnotationValue val = factory.getOWLLiteral(cl
							.toString());
					OWLAnnotationValue newVal = OWLLabelLiteralCollector
							.getLabel(val, factory);
					if (newVal != null) {
						OWLAnnotationAssertionAxiom nax = factory
								.getOWLAnnotationAssertionAxiom(property,
										cl.getIRI(), newVal);
						ontology.getOWLOntologyManager()
								.addAxiom(ontology, nax);
						changes.add(nax);
					}
				}
			}
		}
		return changes;
	}

	protected ArrayList<OWLAxiom> getObjectPropertyChanges(OWLOntology ontology,
			OWLDataFactory factory, OWLAnnotationProperty property) {
		ArrayList<OWLAxiom> changes = new ArrayList<OWLAxiom>();
		{
			for (OWLObjectProperty cl : ontology.getObjectPropertiesInSignature()) {
				if (!cl.getAnnotations(ontology).toString()
						.contains("rdfs:label")) {
					OWLAnnotationValue val = factory.getOWLLiteral(cl
							.toString());
					OWLAnnotationValue newVal = OWLLabelLiteralCollector
							.getLabel(val, factory);
					if (newVal != null) {
						OWLAnnotationAssertionAxiom nax = factory
								.getOWLAnnotationAssertionAxiom(property,
										cl.getIRI(), newVal);
						ontology.getOWLOntologyManager()
								.addAxiom(ontology, nax);
						changes.add(nax);
					}
				}
			}
		}
		return changes;
	}
	
	protected ArrayList<OWLAxiom> getDataPropertyChanges(OWLOntology ontology,
			OWLDataFactory factory, OWLAnnotationProperty property) {
		ArrayList<OWLAxiom> changes = new ArrayList<OWLAxiom>();
		{
			for (OWLDataProperty cl : ontology.getDataPropertiesInSignature()) {
				if (!cl.getAnnotations(ontology).toString()
						.contains("rdfs:label")) {
					OWLAnnotationValue val = factory.getOWLLiteral(cl
							.toString());
					OWLAnnotationValue newVal = OWLLabelLiteralCollector
							.getLabel(val, factory);
					if (newVal != null) {
						OWLAnnotationAssertionAxiom nax = factory
								.getOWLAnnotationAssertionAxiom(property,
										cl.getIRI(), newVal);
						ontology.getOWLOntologyManager()
								.addAxiom(ontology, nax);
						changes.add(nax);
					}
				}
			}
		}
		return changes;
	}

	protected ArrayList<OWLAxiom> getIndividualChanges(OWLOntology ontology,
			OWLDataFactory factory, OWLAnnotationProperty property) {
		ArrayList<OWLAxiom> changes = new ArrayList<OWLAxiom>();

		{
			for (OWLNamedIndividual cl : ontology.getIndividualsInSignature()) {
				if (!cl.getAnnotations(ontology).toString()
						.contains("rdfs:label")) {
					OWLAnnotationValue val = factory.getOWLLiteral(cl
							.toString());
					OWLAnnotationValue newVal = OWLLabelLiteralCollector
							.getLabel(val, factory);
					if (newVal != null) {
						OWLAnnotationAssertionAxiom nax = factory
								.getOWLAnnotationAssertionAxiom(property,
										cl.getIRI(), newVal);
						ontology.getOWLOntologyManager()
								.addAxiom(ontology, nax);
						changes.add(nax);
					}
				}
			}
		}
		return changes;
	}

}