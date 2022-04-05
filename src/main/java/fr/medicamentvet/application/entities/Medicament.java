package fr.medicamentvet.application.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class represents Medicament object.
 */
@JsonPropertyOrder({ "id", "imageURL", "nom", "numeroIdentification", "nomTitulaire", "natureMedicament",
		"typeProcedure", "statutAutorisation", "dateAMM", "formePharmaceutique", "numeroAMM", "especeDestinationList",
		"compositionList", "voieAdministrationList", "conditionDelivranceList", "conditionnementPrimaireList",
		"modeleDestineVenteList", "excipientQSPList", "codeATCVETList", "rcp" })
public class Medicament {

	private int id;
	private String imageURL;
	private String nom;
	private String numeroIdentification;
	private String nomTitulaire;
	private String natureMedicament;
	private String typeProcedure;
	private String statutAutorisation;
	private LocalDate dateAMM;
	private String formePharmaceutique;
	private String numeroAMM;
	private List<String> especeDestinationList;
	private List<Composition> compositionList;
	private List<String> voieAdministrationList;
	private List<String> conditionDelivranceList;
	private List<String> conditionnementPrimaireList;
	private List<ModeleDestineVente> modeleDestineVenteList;
	private List<String> excipientQSPList;
	private List<String> codeATCVETList;
	private Rcp rcp;

	public Medicament() {
		super();
	}

	public Medicament(int id, String imageURL, String nom, String numeroIdentification, String nomTitulaire,
			String natureMedicament, String typeProcedure, String statutAutorisation, LocalDate dateAMM,
			String formePharmaceutique, String numeroAMM, List<String> especeDestinationList,
			List<Composition> compositionList, List<String> voieAdministrationList,
			List<String> conditionDelivranceList, List<String> conditionnementPrimaireList,
			List<ModeleDestineVente> modeleDestineVenteList, List<String> excipientQSPList, List<String> codeATCVETList,
			Rcp rcp) {
		super();
		this.id = id;
		this.imageURL = imageURL;
		this.nom = nom;
		this.numeroIdentification = numeroIdentification;
		this.nomTitulaire = nomTitulaire;
		this.natureMedicament = natureMedicament;
		this.typeProcedure = typeProcedure;
		this.statutAutorisation = statutAutorisation;
		this.dateAMM = dateAMM;
		this.formePharmaceutique = formePharmaceutique;
		this.numeroAMM = numeroAMM;
		this.especeDestinationList = especeDestinationList;
		this.compositionList = compositionList;
		this.voieAdministrationList = voieAdministrationList;
		this.conditionDelivranceList = conditionDelivranceList;
		this.conditionnementPrimaireList = conditionnementPrimaireList;
		this.modeleDestineVenteList = modeleDestineVenteList;
		this.excipientQSPList = excipientQSPList;
		this.codeATCVETList = codeATCVETList;
		this.rcp = rcp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNumeroIdentification() {
		return numeroIdentification;
	}

	public void setNumeroIdentification(String numeroIdentification) {
		this.numeroIdentification = numeroIdentification;
	}

	public String getNomTitulaire() {
		return nomTitulaire;
	}

	public void setNomTitulaire(String nomTitulaire) {
		this.nomTitulaire = nomTitulaire;
	}

	public String getNatureMedicament() {
		return natureMedicament;
	}

	public void setNatureMedicament(String natureMedicament) {
		this.natureMedicament = natureMedicament;
	}

	public String getTypeProcedure() {
		return typeProcedure;
	}

	public void setTypeProcedure(String typeProcedure) {
		this.typeProcedure = typeProcedure;
	}

	public String getStatutAutorisation() {
		return statutAutorisation;
	}

	public void setStatutAutorisation(String statutAutorisation) {
		this.statutAutorisation = statutAutorisation;
	}

	public LocalDate getDateAMM() {
		return dateAMM;
	}

	public void setDateAMM(LocalDate dateAMM) {
		this.dateAMM = dateAMM;
	}

	public String getFormePharmaceutique() {
		return formePharmaceutique;
	}

	public void setFormePharmaceutique(String formePharmaceutique) {
		this.formePharmaceutique = formePharmaceutique;
	}

	public String getNumeroAMM() {
		return numeroAMM;
	}

	public void setNumeroAMM(String numeroAMM) {
		this.numeroAMM = numeroAMM;
	}

	public List<String> getEspeceDestinationList() {
		return especeDestinationList;
	}

	public void setEspeceDestinationList(List<String> especeDestinationList) {
		this.especeDestinationList = especeDestinationList;
	}

	public List<Composition> getCompositionList() {
		return compositionList;
	}

	public void setCompositionList(List<Composition> compositionList) {
		this.compositionList = compositionList;
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

	public List<String> getConditionnementPrimaireList() {
		return conditionnementPrimaireList;
	}

	public void setConditionnementPrimaireList(List<String> conditionnementPrimaireList) {
		this.conditionnementPrimaireList = conditionnementPrimaireList;
	}

	public List<ModeleDestineVente> getModeleDestineVenteList() {
		return modeleDestineVenteList;
	}

	public void setModeleDestineVenteList(List<ModeleDestineVente> modeleDestineVenteList) {
		this.modeleDestineVenteList = modeleDestineVenteList;
	}

	public List<String> getExcipientQSPList() {
		return excipientQSPList;
	}

	public void setExcipientQSPList(List<String> excipientQSPList) {
		this.excipientQSPList = excipientQSPList;
	}

	public List<String> getCodeATCVETList() {
		return codeATCVETList;
	}

	public void setCodeATCVETList(List<String> codeATCVETList) {
		this.codeATCVETList = codeATCVETList;
	}

	public Rcp getRcp() {
		return rcp;
	}

	public void setRcp(Rcp rcp) {
		this.rcp = rcp;
	}
}
