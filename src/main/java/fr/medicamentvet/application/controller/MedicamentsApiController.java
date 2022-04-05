package fr.medicamentvet.application.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.medicamentvet.application.entities.MedicamentSearch;
import fr.medicamentvet.application.service.RestApiService;

/**
 * This class contains 2 responses in "application/json" format: nomId map
 * object response, and a search result list of names of the medicaments.
 */
@RestController
@RequestMapping("/api/medicaments")
public class MedicamentsApiController {

	private static final String PATH_NOM_ID = "/nom-id";
	private static final String PATH_SEARCH = "/search";

	@Autowired
	private RestApiService restApiService;

	/**
	 * The method returns a "application/json" response to a HTTP GET request
	 * defined by "nom-id" URI path.
	 * 
	 * @return List of names and ids in "application/json" data format
	 */
	@GetMapping(PATH_NOM_ID)
	public ResponseEntity<Map<String, Integer>> getAllMedicamentsNomId() {
		return restApiService.getAllMedicamentsNomId();
	}

	/**
	 * The method generates a "application/json" response to a HTTP GET request
	 * given the MedicamentSearch object parameter.
	 * 
	 * @param medicamentSearch MedicamentSearch object in "application/json"
	 * @return List of search result names
	 */
	@PostMapping(PATH_SEARCH)
	public ResponseEntity<List<String>> searchMedicamentNames(@RequestBody MedicamentSearch medicamentSearch) {
		return restApiService.searchMedicamentNames(medicamentSearch);
	}
}
