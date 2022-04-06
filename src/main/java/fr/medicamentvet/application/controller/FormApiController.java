package fr.medicamentvet.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.medicamentvet.application.service.RestApiService;
import fr.medicamentvet.application.entities.SearchForm;
import fr.medicamentvet.application.entities.UpdateForm;

/**
 * This class provides SearchForm and UpdateForm responses in "application/json"
 * format.
 */
@RestController
@RequestMapping("/api")
public class FormApiController {
	
	private static final String PATH_SEARCH_FORM = "/search-form";
	private static final String PATH_UPDATE_FORM = "/update-form";

	@Autowired
	private RestApiService restApiService;
	
	/**
	 * The method produces a "application/json" response to a HTTP GET request.
	 * 
	 * @return SearchForm object in "application/json" data format
	 */
	@GetMapping(PATH_SEARCH_FORM)
	public ResponseEntity<SearchForm> getSearchFormInputs() {
		return restApiService.getSearchFormInputs();
	}

	/**
	 * The method gives a "application/json" response to a HTTP GET request.
	 * 
	 * @return UpdateForm object in "application/json" data format
	 */
	@GetMapping(PATH_UPDATE_FORM)
	public ResponseEntity<UpdateForm> getUpdateFormInputs() {
		return restApiService.getUpdateFormInputs();
	}

}
