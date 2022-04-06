package fr.medicamentvet.application.entities;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents the variables of the search form. When a user performs
 * a search, this object saves the variables.
 */
public final class MedicamentSearch {

	private String nomTitulaire;
	private String numeroAMM;
	private String formePharmaceutique;
	private String typeProcedure;
	private String conditionDelivrance;
	private LocalDate debutDate;
	private LocalDate finDate;
	private String substanceActive1;
	private String substanceActive2;
	private List<String> especeDestinationList;
	// skip = -1 will display all results
	private int skip = -1;

	public MedicamentSearch() {
		super();
	}

	public MedicamentSearch(String nomTitulaire, String numeroAMM, String formePharmaceutique, String typeProcedure,
			String conditionDelivrance, LocalDate debutDate, LocalDate finDate, String substanceActive1,
			String substanceActive2, List<String> especeDestinationList, int skip) {
		super();
		this.nomTitulaire = nomTitulaire;
		this.numeroAMM = numeroAMM;
		this.formePharmaceutique = formePharmaceutique;
		this.typeProcedure = typeProcedure;
		this.conditionDelivrance = conditionDelivrance;
		this.debutDate = debutDate;
		this.finDate = finDate;
		this.substanceActive1 = substanceActive1;
		this.substanceActive2 = substanceActive2;
		this.especeDestinationList = especeDestinationList;
		this.skip = skip;
	}

	public String getNomTitulaire() {
		return nomTitulaire;
	}

	public void setNomTitulaire(String nomTitulaire) {
		this.nomTitulaire = nomTitulaire;
	}

	public String getNumeroAMM() {
		return numeroAMM;
	}

	public void setNumeroAMM(String numeroAMM) {
		this.numeroAMM = numeroAMM;
	}

	public String getFormePharmaceutique() {
		return formePharmaceutique;
	}

	public void setFormePharmaceutique(String formePharmaceutique) {
		this.formePharmaceutique = formePharmaceutique;
	}

	public String getTypeProcedure() {
		return typeProcedure;
	}

	public void setTypeProcedure(String typeProcedure) {
		this.typeProcedure = typeProcedure;
	}

	public String getConditionDelivrance() {
		return conditionDelivrance;
	}

	public void setConditionDelivrance(String conditionDelivrance) {
		this.conditionDelivrance = conditionDelivrance;
	}

	public LocalDate getDebutDate() {
		return debutDate;
	}

	public void setDebutDate(LocalDate debutDate) {
		this.debutDate = debutDate;
	}

	public LocalDate getFinDate() {
		return finDate;
	}

	public void setFinDate(LocalDate finDate) {
		this.finDate = finDate;
	}

	public String getSubstanceActive1() {
		return substanceActive1;
	}

	public void setSubstanceActive1(String substanceActive1) {
		this.substanceActive1 = substanceActive1;
	}

	public String getSubstanceActive2() {
		return substanceActive2;
	}

	public void setSubstanceActive2(String substanceActive2) {
		this.substanceActive2 = substanceActive2;
	}

	public List<String> getEspeceDestinationList() {
		return especeDestinationList;
	}

	public void setEspeceDestinationList(List<String> especeDestinationList) {
		this.especeDestinationList = especeDestinationList;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	@Override
	public String toString() {
		return "nomTitulaire='" + nomTitulaire + '\'' + ", numeroAMM='" + numeroAMM + '\'' + ", formePharmaceutique='"
				+ formePharmaceutique + '\'' + ", typeProcedure='" + typeProcedure + '\'' + ", conditionDelivrance='"
				+ conditionDelivrance + '\'' + ", debutDate=" + debutDate + ", finDate=" + finDate
				+ ", substanceActive1='" + substanceActive1 + '\'' + ", substanceActive2='" + substanceActive2 + '\''
				+ ", especeDestinationList='" + especeDestinationList + '\'' + ", skip=" + skip;
	}
}
