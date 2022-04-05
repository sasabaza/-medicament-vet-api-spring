package fr.medicamentvet.application.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.medicamentvet.application.entities.SearchForm;
import fr.medicamentvet.application.entities.UpdateForm;
import fr.medicamentvet.application.utils.ApplicationLogger;
import fr.medicamentvet.application.entities.MedicamentSearch;
import fr.medicamentvet.application.entities.Composition;
import fr.medicamentvet.application.entities.ModeleDestineVente;
import fr.medicamentvet.application.entities.Rcp;
import fr.medicamentvet.application.repository.RestApiRepository;
import fr.medicamentvet.application.entities.Medicament;

@Repository
public class RestApiRepository {

	private static final String QUERY_SQL_SELECT_ALL_NAMES_FROM_T87_MEDICAMENTX = "SELECT id, nom FROM T87_MEDICAMENTX";

	private static final String QUERY_SQL_SELECT_ALL_FROM_T87_MEDICAMENTX_BY_ID = "SELECT * FROM T87_MEDICAMENTX WHERE id = ?";

	private static final String QUERY_SQL_SELECT_ALL_FROM_T87_MEDICAMENTU = "SELECT * FROM T87_MEDICAMENTU";
	private static final String QUERY_SQL_SELECT_ALL_FROM_T87_MEDICAMENTF = "SELECT * FROM T87_MEDICAMENTF";

	private static final String QUERY_SQL_DELETE_FROM_T87_MEDICAMENT_BY_ID = "DELETE FROM T87_MEDICAMENT WHERE id = ?";
	private static final String QUERY_SQL_DELETE_FROM_T87_MEDICAMENTX_BY_ID = "DELETE FROM T87_MEDICAMENTX WHERE id = ?";
	private static final String QUERY_SQL_DELETE_FROM_T87_ESPECES_DESTINATIONS_BY_ID = "DELETE FROM T87_ESPECES_DESTINATIONS WHERE id = ?";
	private static final String QUERY_SQL_DELETE_FROM_T87_COMPOSITION_BY_ID = "DELETE FROM T87_COMPOSITION WHERE id = ?";
	private static final String QUERY_SQL_DELETE_FROM_T87_CONDITIONS_DELIVRANCE_BY_ID = "DELETE FROM T87_CONDITIONS_DELIVRANCE WHERE id = ?";
	private static final String QUERY_SQL_DELETE_FROM_T87_MODELE_DESTINE_VENTE_BY_ID = "DELETE FROM T87_MODELE_DESTINE_VENTE WHERE id = ?";
	private static final String QUERY_SQL_DELETE_FROM_T87_RCP_BY_ID = "DELETE FROM T87_RCP WHERE id = ?";
	private static final String QUERY_SQL_DELETE_FROM_T87_RCP_TC_BY_ID = "DELETE FROM T87_RCP_TC WHERE id = ?";

	private static final String QUERY_SQL_INSERT_INTO_T87_ESPECES_DESTINATIONS = "INSERT INTO T87_ESPECES_DESTINATIONS (id, especeDestination) VALUES (?, ?)";
	private static final String QUERY_SQL_INSERT_INTO_T87_COMPOSITION = "INSERT INTO T87_COMPOSITION (id, idComposition, substanceActive, quantite, unite) VALUES (?, ?, ?, ?, ?)";
	private static final String QUERY_SQL_INSERT_INTO_T87_CONDITIONS_DELIVRANCE = "INSERT INTO T87_CONDITIONS_DELIVRANCE (id, conditionDelivrance) VALUES (?, ?)";
	private static final String QUERY_SQL_INSERT_INTO_T87_MODELE_DESTINE_VENTE = "INSERT INTO T87_MODELE_DESTINE_VENTE (id, idMdv, libelle, codeGTIN, numeroAMM) VALUES (?, ?, ?, ?, ?)";
	private static final String QUERY_SQL_INSERT_INTO_T87_RCP = "INSERT INTO T87_RCP (id, dateValidation, lienRcp, nomFichierImage) VALUES (?, ?, ?, ?)";
	private static final String QUERY_SQL_INSERT_INTO_T87_RCP_TC = "INSERT INTO T87_RCP_TC (id, idRcp, titre, contenu) VALUES (?, ?, ?, ?)";

	private static final String TOUTES_TEXT = "Toutes";

	private static final String QUERY_SQL_UPDATE_FROM_T87_MEDICAMENT_BY_ID = "UPDATE T87_MEDICAMENT SET nomFichierImage = ?, nom = ?, numeroIdentification = ?, nomTitulaire = ?,"
			+ " natureMedicament = ?, typeProcedure = ?, statutAutorisation = ?, dateAMM = ?, formePharmaceutique = ?, numeroAMM = ?, voieAdministration = ?, conditionnementPrimaire = ?,"
			+ " excipientsQSP = ?, codeATCVET = ? WHERE id = ?";
	private static final String QUERY_SQL_UPDATE_FROM_T87_MEDICAMENTX_BY_ID = "UPDATE T87_MEDICAMENTX SET nomFichierImage = ?, nom = ?, numeroIdentification = ?, nomTitulaire = ?,"
			+ " natureMedicament = ?, typeProcedure = ?, statutAutorisation = ?, dateAMM = ?, formePharmaceutique = ?, numeroAMM = ?, especesDestination = ?, compositions = ?,"
			+ " voieAdministration = ?, conditionsDelivrance = ?, conditionnementPrimaire = ?, modelesDestineVente = ?, excipientsQSP = ?, codeATCVET = ? WHERE id = ?";

	// searchMedicamentNames

	private static final String SELECT_DISTINCT = "SELECT DISTINCT T87_MEDICAMENT.nom FROM T87_MEDICAMENT ";
	private static final String INNER_JOIN_T87_MODELE_DESTINE_VENTE = "INNER JOIN T87_MODELE_DESTINE_VENTE ON T87_MODELE_DESTINE_VENTE.id = T87_MEDICAMENT.id ";
	private static final String INNER_JOIN_T87_CONDITIONS_DELIVRANCE = "INNER JOIN T87_CONDITIONS_DELIVRANCE ON T87_CONDITIONS_DELIVRANCE.id = T87_MEDICAMENT.id ";
	private static final String INNER_JOIN_T87_COMPOSITION = "INNER JOIN T87_COMPOSITION ON T87_COMPOSITION.id = T87_MEDICAMENT.id ";
	private static final String INNER_JOIN_T87_ESPECES_DESTINATIONS = "INNER JOIN T87_ESPECES_DESTINATIONS ON T87_ESPECES_DESTINATIONS.id = T87_MEDICAMENT.id ";
	private static final String WHERE_DATE_AMM_BETWEEN = "WHERE T87_MEDICAMENT.dateAMM BETWEEN '";
	private static final String SINGLE_QUOTE_SEARCH = "' ";
	private static final String CLOSED_PARENTHESIS = ") ";
	private static final String WHITE_SPACE = " ";
	private static final String AND_NOM_TITULAIRE = "AND T87_MEDICAMENT.nomTitulaire = ? ";
	private static final String AND_NUMERO_AMM = "AND (T87_MEDICAMENT.numeroAMM = ? ";
	private static final String OR_T87_MODELE_DESTINE_VENTE = "OR T87_MODELE_DESTINE_VENTE.numeroAMM = ?) ";
	private static final String AND_FORME_PHARMACEUTIQUE = "AND T87_MEDICAMENT.formePharmaceutique = ? ";
	private static final String AND_TYPE_PROCEDURE = "AND T87_MEDICAMENT.typeProcedure = ? ";
	private static final String AND_T87_CONDITIONS_DELIVRANCE = "AND T87_CONDITIONS_DELIVRANCE.conditionDelivrance = ? ";
	private static final String AND_T87_COMPOSITION_IN = "AND T87_COMPOSITION.substanceActive IN(?, ?) ";
	private static final String COUNT_DISTINCT_T87_COMPOSITION = "COUNT(DISTINCT T87_COMPOSITION.substanceActive) = 2 ";
	private static final String AND_T87_COMPOSITION = "AND T87_COMPOSITION.substanceActive = ? ";
	private static final String AND_T87_ESPECES_DESTINATIONS = "AND T87_ESPECES_DESTINATIONS.especeDestination = ? ";
	private static final String AND_T87_ESPECES_DESTINATIONS_IN = "AND T87_ESPECES_DESTINATIONS.especeDestination IN(";
	private static final String QUESTION_MARK_COMMA_TEXT = "?, ";
	private static final String COUNT_DISTINCT_T87_ESPECES_DESTINATIONS = "COUNT(DISTINCT T87_ESPECES_DESTINATIONS.especeDestination) = ";
	private static final String GROUP_BY_NOM_HAVING = "GROUP BY T87_MEDICAMENT.nom HAVING ";
	private static final String AND_TEXT1 = "' AND '";
	private static final String AND_TEXT2 = "AND ";
	private static final String ORDER_BY_NOM = "ORDER BY T87_MEDICAMENT.nom ASC";
	private static final String LIMIT_TEXT = " LIMIT ";
	private static final String LIMIT_NUMBER_RESULTS = ", 100";
	private static final String SELECT_COUNT_DISTINCT = "SELECT COUNT(DISTINCT (T87_MEDICAMENT.nom)) FROM T87_MEDICAMENT ";
	
	private static Map<String, Integer> nomIdMap;
	private static Map<Integer, String> idNomMap;
	private static SearchForm searchForm;
	private static UpdateForm updateForm;


	@Autowired
	private final DataSource dataSource;

	public RestApiRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * The method returns all names and corresponding ids of the medicaments.
	 * 
	 * @return {@code HashMap<String, Integer>} where the key is name of a
	 *         medicament, and the value is associated id
	 */
	public Map<String, Integer> getAllMedicamentsNomId() {

		int id;
		String nom;

		nomIdMap = new HashMap<>();

		try (Connection connection = dataSource.getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery(QUERY_SQL_SELECT_ALL_NAMES_FROM_T87_MEDICAMENTX)) {

					while (results.next()) {
						id = results.getInt(1);
						nom = results.getString(2);

						nomIdMap.put(nom, id);
					}
				}
			}
		} catch (SQLException e) {
			ApplicationLogger.throwableLog(e);
		}

		idNomMap = nomIdMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

		return nomIdMap;
	}
	
	/**
	 * The method returns a Medicament object by it id.
	 * 
	 * @param id id of the Medicament objects
	 * @return Medicament object
	 */
	public Medicament getMedicamentById(int id) {

		Medicament medicament = null;

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement preparedStatement = connection
					.prepareStatement(QUERY_SQL_SELECT_ALL_FROM_T87_MEDICAMENTX_BY_ID)) {
				preparedStatement.setInt(1, id);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					while (resultSet.next()) {
						medicament = getMedicament(resultSet, connection);
					}
				}
			}
		} catch (SQLException e) {
			ApplicationLogger.throwableLog(e);
		}

		return medicament;
	}
	
	/**
	 * The method deletes medicament data of all tables by it id and updates the
	 * SearchForm and the nomIdMap objects.
	 * 
	 * @param id id of the Medicament object
	 */
	public void deleteMedicamentById(int id) {

		try (Connection connection = dataSource.getConnection()) {

			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_MEDICAMENT_BY_ID);
			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_MEDICAMENTX_BY_ID);
			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_ESPECES_DESTINATIONS_BY_ID);
			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_COMPOSITION_BY_ID);
			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_CONDITIONS_DELIVRANCE_BY_ID);
			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_MODELE_DESTINE_VENTE_BY_ID);
			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_RCP_BY_ID);
			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_RCP_TC_BY_ID);

			createAndInsertSearchFormInputs(connection);

			searchForm = getSearchFormInputs();

			deleteNomIdWithId(id);
		} catch (SQLException e) {
			ApplicationLogger.throwableLog(e);
		}
	}
	
	/**
	 * The method performs a data update of a Medicament object, and updates the
	 * SearchForm and the nomIdMap objects.
	 * 
	 * @param medicament Medicament object
	 * @return Medicament object
	 */
	public Medicament updateMedicament(Medicament medicament) {

		int id = medicament.getId();
		String imageURL = medicament.getImageURL();
		String nom = medicament.getNom();
		String numeroIdentification = medicament.getNumeroIdentification();
		String nomTitulaire = medicament.getNomTitulaire();
		String natureMedicament = medicament.getNatureMedicament();
		String typeProcedure = medicament.getTypeProcedure();
		String statutAutorisation = medicament.getStatutAutorisation();

		LocalDate date = medicament.getDateAMM();
		Date dateAMM = java.sql.Date.valueOf(date);

		String formePharmaceutique = medicament.getFormePharmaceutique();
		String numeroAMM = medicament.getNumeroAMM();

		List<String> especeDestinationList = medicament.getEspeceDestinationList();
		String especesDestination = stringListToString(especeDestinationList);

		List<Composition> compositionList = medicament.getCompositionList();
		String compositions = compositionListToString(compositionList);

		List<String> list = medicament.getVoieAdministrationList();
		String voieAdministration = stringListToString(list);

		List<String> conditionDelivranceList = medicament.getConditionDelivranceList();
		String conditionsDelivrance = stringListToString(conditionDelivranceList);

		list = medicament.getConditionnementPrimaireList();
		String conditionnementPrimaire = stringListToString(list);

		List<ModeleDestineVente> modeleDestineVenteList = medicament.getModeleDestineVenteList();
		String modelesDestineVente = modeleDestineVenteListToString(modeleDestineVenteList);

		list = medicament.getExcipientQSPList();
		String excipientsQSP = stringListToString(list);

		list = medicament.getCodeATCVETList();
		String codeATCVET = stringListToString(list);

		Rcp rcp = medicament.getRcp();

		String nomImageRcp = null;
		Date dateValidation = null;
		String lienRcp = null;
		List<String> titreList = null;
		List<String> contenuList = null;

		if (rcp != null) {
			list = rcp.getImageURLList();
			nomImageRcp = stringListToString(list);

			date = rcp.getDateValidation();
			dateValidation = java.sql.Date.valueOf(date);

			lienRcp = rcp.getLienRcp();

			titreList = rcp.getTitreList();
			contenuList = rcp.getContenuList();
		}

		try (Connection connection = dataSource.getConnection()) {
			try (PreparedStatement statement = connection
					.prepareStatement(QUERY_SQL_UPDATE_FROM_T87_MEDICAMENT_BY_ID)) {
				statement.setString(1, imageURL);
				statement.setString(2, nom);
				statement.setString(3, numeroIdentification);
				statement.setString(4, nomTitulaire);
				statement.setString(5, natureMedicament);
				statement.setString(6, typeProcedure);
				statement.setString(7, statutAutorisation);
				statement.setDate(8, dateAMM);
				statement.setString(9, formePharmaceutique);
				statement.setString(10, numeroAMM);
				statement.setString(11, voieAdministration);
				statement.setString(12, conditionnementPrimaire);
				statement.setString(13, excipientsQSP);
				statement.setString(14, codeATCVET);
				statement.setInt(15, id);
			}

			try (PreparedStatement statement = connection
					.prepareStatement(QUERY_SQL_UPDATE_FROM_T87_MEDICAMENTX_BY_ID)) {
				statement.setString(1, imageURL);
				statement.setString(2, nom);
				statement.setString(3, numeroIdentification);
				statement.setString(4, nomTitulaire);
				statement.setString(5, natureMedicament);
				statement.setString(6, typeProcedure);
				statement.setString(7, statutAutorisation);
				statement.setDate(8, dateAMM);
				statement.setString(9, formePharmaceutique);
				statement.setString(10, numeroAMM);
				statement.setString(11, especesDestination);
				statement.setString(12, compositions);
				statement.setString(13, voieAdministration);
				statement.setString(14, conditionsDelivrance);
				statement.setString(15, conditionnementPrimaire);
				statement.setString(16, modelesDestineVente);
				statement.setString(17, excipientsQSP);
				statement.setString(18, codeATCVET);
				statement.setInt(19, id);
			}

			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_ESPECES_DESTINATIONS_BY_ID);

			insertFromStatement(id, connection, especeDestinationList, QUERY_SQL_INSERT_INTO_T87_ESPECES_DESTINATIONS);

			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_COMPOSITION_BY_ID);

			for (Composition composition : compositionList) {
				int idComposition = composition.getIdComposition();
				String substanceActive = composition.getSubstanceActive();
				String quantite = composition.getQuantite();
				String unite = composition.getUnite();
				try (PreparedStatement statement = connection.prepareStatement(QUERY_SQL_INSERT_INTO_T87_COMPOSITION)) {
					statement.setInt(1, id);
					statement.setInt(2, idComposition);
					statement.setString(3, substanceActive);
					statement.setString(4, quantite);
					statement.setString(5, unite);
				}
			}

			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_CONDITIONS_DELIVRANCE_BY_ID);

			insertFromStatement(id, connection, especeDestinationList, QUERY_SQL_INSERT_INTO_T87_CONDITIONS_DELIVRANCE);

			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_MODELE_DESTINE_VENTE_BY_ID);

			for (ModeleDestineVente modeleDestineVente : modeleDestineVenteList) {
				int idMdv = modeleDestineVente.getIdMdv();
				String libelle = modeleDestineVente.getLibelle();
				String codeGTIN = modeleDestineVente.getCodeGTIN();
				String numeroAMMMdv = modeleDestineVente.getNumeroAMM();
				try (PreparedStatement statement = connection
						.prepareStatement(QUERY_SQL_INSERT_INTO_T87_MODELE_DESTINE_VENTE)) {
					statement.setInt(1, id);
					statement.setInt(2, idMdv);
					statement.setString(3, libelle);
					statement.setString(4, codeGTIN);
					statement.setString(5, numeroAMMMdv);
				}
			}

			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_RCP_BY_ID);

			try (PreparedStatement statement = connection.prepareStatement(QUERY_SQL_INSERT_INTO_T87_RCP)) {
				statement.setInt(1, id);
				statement.setDate(2, dateValidation);
				statement.setString(3, lienRcp);
				statement.setString(4, nomImageRcp);
			}

			deleteFromStatement(id, connection, QUERY_SQL_DELETE_FROM_T87_RCP_TC_BY_ID);

			if (rcp != null) {
				for (int i = 0; i < titreList.size(); i++) {
					int idRcp = i + 1;
					try (PreparedStatement statement = connection.prepareStatement(QUERY_SQL_INSERT_INTO_T87_RCP_TC)) {
						statement.setInt(1, id);
						statement.setInt(2, idRcp);
						statement.setString(3, titreList.get(i));
						statement.setString(4, contenuList.get(i));
					}
				}
			}

			createAndInsertSearchFormInputs(connection);

			searchForm = getSearchFormInputs();

			updateNomIdMap(id, nom);

		} catch (SQLException e) {
			ApplicationLogger.throwableLog(e);
		}

		return medicament;
	}
	
	/**
	 * The method returns a UpdateForm object corresponding to all inputs used to
	 * complete Medicament object.
	 * 
	 * @return UpdateForm object
	 */
	public UpdateForm getUpdateFormInputs() {

		try (Connection connection = dataSource.getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery(QUERY_SQL_SELECT_ALL_FROM_T87_MEDICAMENTU)) {

					while (results.next()) {

						String nomsTitulaire = results.getString(1);
						List<String> nomTitulaireList = new ArrayList<>();
						stringToStringList(nomsTitulaire, nomTitulaireList);

						String typesProcedure = results.getString(2);
						List<String> typeProcedureList = new ArrayList<>();
						stringToStringList(typesProcedure, typeProcedureList);

						String statutsAutorisation = results.getString(3);
						List<String> statutAutorisationList = new ArrayList<>();
						stringToStringList(statutsAutorisation, statutAutorisationList);

						String formesPharmaceutiques = results.getString(4);
						List<String> formePharmaceutiqueList = new ArrayList<>();
						stringToStringList(formesPharmaceutiques, formePharmaceutiqueList);

						String especesDestination = results.getString(5);
						List<String> especeDestinationList = new ArrayList<>();
						stringToStringList(especesDestination, especeDestinationList);

						String voiesAdministration = results.getString(6);
						List<String> voieAdministrationList = new ArrayList<>();
						stringToStringList(voiesAdministration, voieAdministrationList);

						String conditionsDelivrance = results.getString(7);
						List<String> conditionDelivranceList = new ArrayList<>();
						stringToStringList(conditionsDelivrance, conditionDelivranceList);

						String excipientsQSP = results.getString(8);
						List<String> excipientQSPList = new ArrayList<>();
						stringToStringList(excipientsQSP, excipientQSPList);

						String substancesActives = results.getString(9);
						List<String> substanceActiveList = new ArrayList<>();
						stringToStringList(substancesActives, substanceActiveList);

						updateForm = new UpdateForm(nomTitulaireList, typeProcedureList, statutAutorisationList,
								formePharmaceutiqueList, especeDestinationList, voieAdministrationList,
								conditionDelivranceList, excipientQSPList, substanceActiveList);
					}
				}
			}
		} catch (SQLException e) {
			ApplicationLogger.throwableLog(e);
		}

		return updateForm;
	}
	
	/**
	 * The method returns a SearchForm object containing inputs used to search names
	 * of the medicaments.
	 * 
	 * @return SearchForm object
	 */
	public SearchForm getSearchFormInputs() {

		try (Connection connection = dataSource.getConnection()) {
			try (Statement statement = connection.createStatement()) {
				try (ResultSet results = statement.executeQuery(QUERY_SQL_SELECT_ALL_FROM_T87_MEDICAMENTF)) {

					while (results.next()) {

						String nomsTitulaire = results.getString(1);
						List<String> nomTitulaireList = new ArrayList<>();
						stringToStringList(nomsTitulaire, nomTitulaireList);

						String typesProcedure = results.getString(2);
						List<String> typeProcedureList = new ArrayList<>();
						stringToStringList(typesProcedure, typeProcedureList);

						String formesPharmaceutiques = results.getString(3);
						List<String> formePharmaceutiqueList = new ArrayList<>();
						stringToStringList(formesPharmaceutiques, formePharmaceutiqueList);

						String numerosAMM = results.getString(4);
						List<String> numeroAMMList = new ArrayList<>();
						stringToStringList(numerosAMM, numeroAMMList);

						String especesDestination = results.getString(5);
						List<String> especeDestinationList = new ArrayList<>();
						stringToStringList(especesDestination, especeDestinationList);

						String substancesActives = results.getString(6);
						List<String> substanceActiveList = new ArrayList<>();
						stringToStringList(substancesActives, substanceActiveList);

						String conditionsDelivrance = results.getString(7);
						List<String> conditionDelivranceList = new ArrayList<>();
						stringToStringList(conditionsDelivrance, conditionDelivranceList);

						searchForm = new SearchForm(nomTitulaireList, typeProcedureList, formePharmaceutiqueList,
								numeroAMMList, especeDestinationList, substanceActiveList, conditionDelivranceList);
					}
				}
			}
		} catch (SQLException e) {
			ApplicationLogger.throwableLog(e);
		}

		return searchForm;
	}
	
	/**
	 * The method returns a list of names of the medicaments based on variables of
	 * MedicamentSearch object.
	 * 
	 * @param medicamentSearch MedicamentSearch object
	 * @return {@code List<String>} i.e. list of names of the medicaments
	 */
	public List<String> searchMedicamentNames(MedicamentSearch medicamentSearch) {

		String nomTitulaire = medicamentSearch.getNomTitulaire();
		String numeroAMM = medicamentSearch.getNumeroAMM();
		String formePharmaceutique = medicamentSearch.getFormePharmaceutique();
		String typeProcedure = medicamentSearch.getTypeProcedure();
		String conditionDelivrance = medicamentSearch.getConditionDelivrance();
		LocalDate debutDate = medicamentSearch.getDebutDate();
		LocalDate finDate = medicamentSearch.getFinDate();
		String substanceActive1 = medicamentSearch.getSubstanceActive1();
		String substanceActive2 = medicamentSearch.getSubstanceActive2();
		List<String> especeDestinationList = medicamentSearch.getEspeceDestinationList();
		int skip = medicamentSearch.getSkip();

		StringBuilder stringBuilder = new StringBuilder(SELECT_DISTINCT);
		int sizeEspeceDestinationList = especeDestinationList.size();
		String queryComposition = null;
		String queryEspece = null;

		if (!numeroAMM.isEmpty()) {
			stringBuilder.append(INNER_JOIN_T87_MODELE_DESTINE_VENTE);
		}

		if (!conditionDelivrance.equals(TOUTES_TEXT)) {
			stringBuilder.append(INNER_JOIN_T87_CONDITIONS_DELIVRANCE);
		}

		if (!substanceActive1.isEmpty() || !substanceActive2.isEmpty()) {
			stringBuilder.append(INNER_JOIN_T87_COMPOSITION);
		}

		if (sizeEspeceDestinationList > 0) {
			stringBuilder.append(INNER_JOIN_T87_ESPECES_DESTINATIONS);
		}

//        stringBuilder.append("WHERE 1 = 1 ");
		stringBuilder.append(WHERE_DATE_AMM_BETWEEN).append(debutDate).append(AND_TEXT1).append(finDate)
				.append(SINGLE_QUOTE_SEARCH);

		if (!nomTitulaire.isEmpty()) {
			stringBuilder.append(AND_NOM_TITULAIRE);
		}

		if (!numeroAMM.isEmpty()) {
			stringBuilder.append(AND_NUMERO_AMM);
			stringBuilder.append(OR_T87_MODELE_DESTINE_VENTE);
		}

		if (!formePharmaceutique.equals(TOUTES_TEXT)) {
			stringBuilder.append(AND_FORME_PHARMACEUTIQUE);
		}

		if (!typeProcedure.equals(TOUTES_TEXT)) {
			stringBuilder.append(AND_TYPE_PROCEDURE);
		}

		if (!conditionDelivrance.equals(TOUTES_TEXT)) {
			stringBuilder.append(AND_T87_CONDITIONS_DELIVRANCE);
		}

		if (!substanceActive1.isEmpty() && !substanceActive2.isEmpty()) {
			stringBuilder.append(AND_T87_COMPOSITION_IN);
			queryComposition = COUNT_DISTINCT_T87_COMPOSITION;
		} else {
			if (!substanceActive1.isEmpty() || !substanceActive2.isEmpty()) {
				stringBuilder.append(AND_T87_COMPOSITION);
			}
		}

		if (sizeEspeceDestinationList > 0) {
			if (sizeEspeceDestinationList == 1) {
				stringBuilder.append(AND_T87_ESPECES_DESTINATIONS);
			} else {
				stringBuilder.append(AND_T87_ESPECES_DESTINATIONS_IN);

				StringBuilder stringBuilderqueryIN = new StringBuilder();

				StringBuilder appendQuestionMark = new StringBuilder();
				for (int i = 0; i < sizeEspeceDestinationList; i++) {
					appendQuestionMark.append(QUESTION_MARK_COMMA_TEXT);
				}

				stringBuilderqueryIN.append(appendQuestionMark);

				// Delete question mark and comma at the end
				stringBuilderqueryIN.delete(stringBuilderqueryIN.length() - 2, stringBuilderqueryIN.length());
				stringBuilder.append(stringBuilderqueryIN);
				stringBuilder.append(CLOSED_PARENTHESIS);

				queryEspece = COUNT_DISTINCT_T87_ESPECES_DESTINATIONS + sizeEspeceDestinationList + WHITE_SPACE;
			}
		}

		if ((!substanceActive1.isEmpty() && !substanceActive2.isEmpty()) || sizeEspeceDestinationList > 1) {
			stringBuilder.append(GROUP_BY_NOM_HAVING);
		}

		if (!substanceActive1.isEmpty() && !substanceActive2.isEmpty()) {
			stringBuilder.append(queryComposition);
		}

		if (sizeEspeceDestinationList > 1 && (!substanceActive1.isEmpty() && !substanceActive2.isEmpty())) {
			stringBuilder.append(AND_TEXT2).append(queryEspece);
		} else if (sizeEspeceDestinationList > 1) {
			stringBuilder.append(queryEspece);
		}

		stringBuilder.append(ORDER_BY_NOM);

		String skipString = null;

		if (skip >= 0) {
			skipString = LIMIT_TEXT + skip + LIMIT_NUMBER_RESULTS;
			stringBuilder.append(skipString);
		}

		int resultNumber = -1;
		List<String> list = null;

		try (Connection connection = dataSource.getConnection()) {

			if (skip == 0) {

				StringBuilder stringBuilderCount = new StringBuilder(stringBuilder);

				stringBuilderCount.delete(stringBuilderCount.length() - ORDER_BY_NOM.length() - skipString.length(),
						stringBuilderCount.length());
				stringBuilderCount.delete(0, SELECT_DISTINCT.length());

				String stringStatement = SELECT_COUNT_DISTINCT + stringBuilderCount;

				try (PreparedStatement statement = connection.prepareStatement(stringStatement)) {
					int i = 0;
					if (!nomTitulaire.isEmpty()) {
						i++;
						statement.setString(i, nomTitulaire);
					}
					if (!numeroAMM.isEmpty()) {
						i++;
						statement.setString(i, numeroAMM);
						i++;
						statement.setString(i, numeroAMM);
					}
					if (!formePharmaceutique.equals(TOUTES_TEXT)) {
						i++;
						statement.setString(i, formePharmaceutique);
					}
					if (!typeProcedure.equals(TOUTES_TEXT)) {
						i++;
						statement.setString(i, typeProcedure);
					}
					if (!conditionDelivrance.equals(TOUTES_TEXT)) {
						i++;
						statement.setString(i, conditionDelivrance);
					}
					if (!substanceActive1.isEmpty() && !substanceActive2.isEmpty()) {
						i++;
						statement.setString(i, substanceActive1);
						i++;
						statement.setString(i, substanceActive2);
					} else {
						if (!substanceActive1.isEmpty() || !substanceActive2.isEmpty()) {
							i++;
							if (!substanceActive1.isEmpty())
								statement.setString(i, substanceActive1);
							if (!substanceActive2.isEmpty())
								statement.setString(i, substanceActive2);
						}
					}
					if (sizeEspeceDestinationList > 0) {
						for (String espece : especeDestinationList) {
							i++;
							statement.setString(i, espece);
						}
					}

					try (ResultSet results = statement.executeQuery()) {
						while (results.next()) {
							resultNumber = results.getInt(1);
						}
					}
				}
			}

			try (PreparedStatement statement = connection.prepareStatement(stringBuilder.toString())) {
				int i = 0;
				if (!nomTitulaire.isEmpty()) {
					i++;
					statement.setString(i, nomTitulaire);
				}
				if (!numeroAMM.isEmpty()) {
					i++;
					statement.setString(i, numeroAMM);
					i++;
					statement.setString(i, numeroAMM);
				}
				if (!formePharmaceutique.equals(TOUTES_TEXT)) {
					i++;
					statement.setString(i, formePharmaceutique);
				}
				if (!typeProcedure.equals(TOUTES_TEXT)) {
					i++;
					statement.setString(i, typeProcedure);
				}
				if (!conditionDelivrance.equals(TOUTES_TEXT)) {
					i++;
					statement.setString(i, conditionDelivrance);
				}
				if (!substanceActive1.isEmpty() && !substanceActive2.isEmpty()) {
					i++;
					statement.setString(i, substanceActive1);
					i++;
					statement.setString(i, substanceActive2);
				} else {
					if (!substanceActive1.isEmpty() || !substanceActive2.isEmpty()) {
						i++;
						if (!substanceActive1.isEmpty())
							statement.setString(i, substanceActive1);
						if (!substanceActive2.isEmpty())
							statement.setString(i, substanceActive2);
					}
				}
				if (sizeEspeceDestinationList > 0) {
					for (String espece : especeDestinationList) {
						i++;
						statement.setString(i, espece);
					}
				}

				try (ResultSet results = statement.executeQuery()) {

					list = new ArrayList<>();

					if (resultNumber > 0) {
						list.add(String.valueOf(resultNumber));
					}

					while (results.next()) {
						list.add(results.getString(1));
					}
				}
			}
		} catch (SQLException e) {
			ApplicationLogger.throwableLog(e);
		}

		return list;
	}
	
	/**
	 * The method generates new data for the SearchForm object.
	 * 
	 * @param connection Connection object
	 */
	private void createAndInsertSearchFormInputs(Connection connection) {
	}
	
	/**
	 * The method deletes the data by id for a specific table.
	 * 
	 * @param id         id of the Medicament object
	 * @param connection Connection object
	 * @param query      String query statement
	 */
	private void deleteFromStatement(int id, Connection connection, String query) {
	}

	/**
	 * The method removes entry by id from the nomIdMap object.
	 * 
	 * @param id id of the Medicament object
	 */
	private void deleteNomIdWithId(int id) {
		String nom = idNomMap.get(id);
		nomIdMap.remove(nom);
		idNomMap.remove(id);
	}
	
	/**
	 * The method updates the key of the nomIdMap object if it has changed.
	 * 
	 * @param id  id of the Medicament object
	 * @param nom Name of the Medicament object
	 */
	private void updateNomIdMap(int id, String nom) {
		// if the condition is true, then String key nom has changed
		if (!nomIdMap.containsKey(nom)) {

			// To update the key we must delete first K,V and then put new K,V
			String oldValue = idNomMap.get(id);
			nomIdMap.remove(oldValue);
			nomIdMap.put(nom, id);
			idNomMap.put(id, nom);
		}
	}
	
	/**
	 * The method performs an insert statement for each String data along with the
	 * id.
	 * 
	 * @param id         id of the Medicament object
	 * @param connection Connection object
	 * @param list       List of Strings
	 * @param query      String query statement
	 */
	private void insertFromStatement(int id, Connection connection, List<String> list, String query) {
	}
	
	/**
	 * The method returns a Medicament object: it performs SELECT statement.
	 * 
	 * @param resultSet  ResultSet
	 * @param connection Connection object
	 * @return Medicament object
	 * @throws SQLException
	 */
	private Medicament getMedicament(ResultSet resultSet, Connection connection) throws SQLException {
		return new Medicament();
	}
	
	/**
	 * The method adds a String or an array of Strings to a list.
	 * 
	 * @param string String
	 * @param list   {@code List<String>}
	 */
	private static void stringToStringList(String string, List<String> list) {
	}

	/**
	 * The method joins elements of a list to a String separated by a pattern.
	 * 
	 * @param list {@code List<String>}
	 * @return String
	 */
	private String stringListToString(List<String> list) {
		return new String();
	}

	/**
	 * The method transforms a list of Composition objects to a String.
	 * 
	 * @param compositionList {@code List<Composition>}
	 * @return String
	 */
	private String compositionListToString(List<Composition> compositionList) {
		return new String();
	}
	
	/**
	 * The method turns a list of ModeleDestineVente objects to a String.
	 * 
	 * @param modeleDestineVenteList {@code List<ModeleDestineVente>}
	 * @return String
	 */
	private String modeleDestineVenteListToString(List<ModeleDestineVente> modeleDestineVenteList) {
		return new String();
	}
	
	public static Map<String, Integer> getNomIdMap() {
		return nomIdMap;
	}

	public static Map<Integer, String> getIdNomMap() {
		return idNomMap;
	}

	public static SearchForm getSearchForm() {
		return searchForm;
	}

	public static UpdateForm getUpdateForm() {
		return updateForm;
	}
}
