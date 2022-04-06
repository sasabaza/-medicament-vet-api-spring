package fr.medicamentvet.application.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.medicamentvet.application.utils.LocalDateSerializer;

/**
 * This class describes the Résumé des caractéristiques du produit (RCP): the
 * characteristics of a medicament.
 */
@JsonPropertyOrder({ "imageURLList", "dateValidation", "lienRcp", "titreList", "contenuList" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rcp {

	private List<String> imageURLList;
	
    @JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateValidation;
	
	private String lienRcp;
	private List<String> titreList;
	private List<String> contenuList;

	public Rcp() {
		super();
	}

	public Rcp(List<String> imageURLList, LocalDate dateValidation, String lienRcp, List<String> titreList,
			List<String> contenuList) {
		super();
		this.imageURLList = imageURLList;
		this.dateValidation = dateValidation;
		this.lienRcp = lienRcp;
		this.titreList = titreList;
		this.contenuList = contenuList;
	}

	public List<String> getImageURLList() {
		return imageURLList;
	}

	public void setImageURLList(List<String> imageURLList) {
		this.imageURLList = imageURLList;
	}

	public LocalDate getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(LocalDate dateValidation) {
		this.dateValidation = dateValidation;
	}

	public String getLienRcp() {
		return lienRcp;
	}

	public void setLienRcp(String lienRcp) {
		this.lienRcp = lienRcp;
	}

	public List<String> getTitreList() {
		return titreList;
	}

	public void setTitreList(List<String> titreList) {
		this.titreList = titreList;
	}

	public List<String> getContenuList() {
		return contenuList;
	}

	public void setContenuList(List<String> contenuList) {
		this.contenuList = contenuList;
	}
}