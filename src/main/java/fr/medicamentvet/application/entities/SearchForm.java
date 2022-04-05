package fr.medicamentvet.application.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents the inputs to choose from in the search form. The
 * inputs are based on the existing medicament data.
 */
@JsonPropertyOrder({ "nomTitulaireList", "typeProcedureList", "formePharmaceutiqueList", "numeroAMMList",
		"especeDestinationList", "substanceActiveList", "conditionDelivranceList" })
public final class SearchForm {

	private List<String> nomTitulaireList;
	private List<String> typeProcedureList;
	private List<String> formePharmaceutiqueList;
	private List<String> numeroAMMList;
	private List<String> especeDestinationList;
	private List<String> substanceActiveList;
	private List<String> conditionDelivranceList;

	public SearchForm(List<String> nomTitulaireList, List<String> typeProcedureList,
			List<String> formePharmaceutiqueList, List<String> numeroAMMList, List<String> especeDestinationList,
			List<String> substanceActiveList, List<String> conditionDelivranceList) {
		super();
		this.nomTitulaireList = nomTitulaireList;
		this.typeProcedureList = typeProcedureList;
		this.formePharmaceutiqueList = formePharmaceutiqueList;
		this.numeroAMMList = numeroAMMList;
		this.especeDestinationList = especeDestinationList;
		this.substanceActiveList = substanceActiveList;
		this.conditionDelivranceList = conditionDelivranceList;
	}

	public List<String> getNomTitulaireList() {
		return nomTitulaireList;
	}

	public void setNomTitulaireList(List<String> nomTitulaireList) {
		this.nomTitulaireList = nomTitulaireList;
	}

	public List<String> getTypeProcedureList() {
		return typeProcedureList;
	}

	public void setTypeProcedureList(List<String> typeProcedureList) {
		this.typeProcedureList = typeProcedureList;
	}

	public List<String> getFormePharmaceutiqueList() {
		return formePharmaceutiqueList;
	}

	public void setFormePharmaceutiqueList(List<String> formePharmaceutiqueList) {
		this.formePharmaceutiqueList = formePharmaceutiqueList;
	}

	public List<String> getNumeroAMMList() {
		return numeroAMMList;
	}

	public void setNumeroAMMList(List<String> numeroAMMList) {
		this.numeroAMMList = numeroAMMList;
	}

	public List<String> getEspeceDestinationList() {
		return especeDestinationList;
	}

	public void setEspeceDestinationList(List<String> especeDestinationList) {
		this.especeDestinationList = especeDestinationList;
	}

	public List<String> getSubstanceActiveList() {
		return substanceActiveList;
	}

	public void setSubstanceActiveList(List<String> substanceActiveList) {
		this.substanceActiveList = substanceActiveList;
	}

	public List<String> getConditionDelivranceList() {
		return conditionDelivranceList;
	}

	public void setConditionDelivranceList(List<String> conditionDelivranceList) {
		this.conditionDelivranceList = conditionDelivranceList;
	}
}
