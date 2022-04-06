package fr.medicamentvet.application.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents all inputs to choose from when a user wishes to modify
 * a Medicament object.
 */
@JsonPropertyOrder({ "nomTitulaireList", "typeProcedureList", "statutAutorisationList", "formePharmaceutiqueList",
		"especeDestinationList", "voieAdministrationList", "conditionDelivranceList", "excipientQSPList",
		"substanceActiveList" })
public final class UpdateForm {

	private List<String> nomTitulaireList;
	private List<String> typeProcedureList;
	private List<String> statutAutorisationList;
	private List<String> formePharmaceutiqueList;
	private List<String> especeDestinationList;
	private List<String> voieAdministrationList;
	private List<String> conditionDelivranceList;
	private List<String> excipientQSPList;
	private List<String> substanceActiveList;

	public UpdateForm(List<String> nomTitulaireList, List<String> typeProcedureList,
			List<String> statutAutorisationList, List<String> formePharmaceutiqueList,
			List<String> especeDestinationList, List<String> voieAdministrationList,
			List<String> conditionDelivranceList, List<String> excipientQSPList, List<String> substanceActiveList) {
		super();
		this.nomTitulaireList = nomTitulaireList;
		this.typeProcedureList = typeProcedureList;
		this.statutAutorisationList = statutAutorisationList;
		this.formePharmaceutiqueList = formePharmaceutiqueList;
		this.especeDestinationList = especeDestinationList;
		this.voieAdministrationList = voieAdministrationList;
		this.conditionDelivranceList = conditionDelivranceList;
		this.excipientQSPList = excipientQSPList;
		this.substanceActiveList = substanceActiveList;
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

	public List<String> getStatutAutorisationList() {
		return statutAutorisationList;
	}

	public void setStatutAutorisationList(List<String> statutAutorisationList) {
		this.statutAutorisationList = statutAutorisationList;
	}

	public List<String> getFormePharmaceutiqueList() {
		return formePharmaceutiqueList;
	}

	public void setFormePharmaceutiqueList(List<String> formePharmaceutiqueList) {
		this.formePharmaceutiqueList = formePharmaceutiqueList;
	}

	public List<String> getEspeceDestinationList() {
		return especeDestinationList;
	}

	public void setEspeceDestinationList(List<String> especeDestinationList) {
		this.especeDestinationList = especeDestinationList;
	}

	public List<String> getVoieAdministrationList() {
		return voieAdministrationList;
	}

	public void setVoieAdministrationList(List<String> voieAdministrationList) {
		this.voieAdministrationList = voieAdministrationList;
	}

	public List<String> getConditionDelivranceList() {
		return conditionDelivranceList;
	}

	public void setConditionDelivranceList(List<String> conditionDelivranceList) {
		this.conditionDelivranceList = conditionDelivranceList;
	}

	public List<String> getExcipientQSPList() {
		return excipientQSPList;
	}

	public void setExcipientQSPList(List<String> excipientQSPList) {
		this.excipientQSPList = excipientQSPList;
	}

	public List<String> getSubstanceActiveList() {
		return substanceActiveList;
	}

	public void setSubstanceActiveList(List<String> substanceActiveList) {
		this.substanceActiveList = substanceActiveList;
	}
}
