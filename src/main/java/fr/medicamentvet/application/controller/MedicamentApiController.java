package fr.medicamentvet.application.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.medicamentvet.application.service.RestApiService;
import fr.medicamentvet.application.entities.Medicament;
import fr.medicamentvet.application.exception.NotFoundException;
import fr.medicamentvet.application.exception.UnavailableException;
import fr.medicamentvet.application.exception.UnvalidException;

/**
 * The class consists of several responses to a HTTP request:<br>
 * - A Medicament object response to a HTTP GET request in "application/json"
 * format<br>
 * - A "application/json" data format response to a HTTP DELETE request<br>
 * - A Medicament object response to a HTTP PUT request in "application/json"
 * format<br>
 * - A "application/octet-stream" data format response to download an image
 */
@RestController
@RequestMapping("/api/medicament")
public class MedicamentApiController {

	private static final String PATH_RESOURCE_ENTITY_ID = "/{entityId}";
	private static final String PARAM_ENTITY_ID = "entityId";
	private static final String PATH_RESOURCE_UPDATE = "/update";
	private static final String PATH_RESOURCE_MEDICAMENT_IMAGE = "/{entityId}/image";
	private static final String PATH_RESOURCE_RCP_IMAGE = "/{entityId}/rcp/image/{number}";
	private static final String PARAM_NUMBER = "number";
	
	private static final String IMAGE_TYPE_JPG = "image/jpg";
	private static final String IMAGE_TYPE_JPEG = "image/jpeg";
	private static final String IMAGE_TYPE_PNG = "image/png";

	private static final String REGEX_NATURAL_INTEGER_TEXT = "^[1-9]|[1-9][0-9]{1,4}";
    public static final Pattern PATTERN_NATURAL_INTEGER = Pattern.compile(REGEX_NATURAL_INTEGER_TEXT);

	private static final String MEDICAMENT_TEXT = "medicament";
	private static final String HEADER_IMAGE_MEDICAMENT = "imageMedicament";
	private static final String HEADER_IMAGE_RCP = "imageRcp";
	
	@Autowired
	private RestApiService restApiService;
	
	/**
	 * The method produces a "application/json" response to a HTTP GET request. The
	 * response is Medicament object in "application/json" data format.
	 * 
	 * @param stringId String id of the Medicament object
	 * @return Medicament object in "application/json" data format
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException the service is not available
	 * @throws UnvalidException the parameter stringId is not valid
	 */
	@GetMapping(PATH_RESOURCE_ENTITY_ID)
	public ResponseEntity<Medicament> getMedicamentById(@PathVariable(PARAM_ENTITY_ID) String stringId)
			throws NotFoundException, UnavailableException, UnvalidException {
		return restApiService.getMedicamentById(stringId);
	}
	
	/**
	 * The purpose of the method is to delete the data of the medicament and the
	 * associated image(s) by it id.
	 * 
	 * @param stringId String id of the Medicament object
	 * @return Empty response if the status is OK or "application/json" response
	 *         error when there is IOException exception
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException the service is not available
	 * @throws UnvalidException the parameter stringId is not valid
	 */
	@DeleteMapping(PATH_RESOURCE_ENTITY_ID)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMedicamentById(@PathVariable(PARAM_ENTITY_ID) String stringId)
			throws NotFoundException, UnavailableException, UnvalidException {
		restApiService.deleteMedicamentById(stringId);
	}
	
	/**
	 * The method achieves an update of a given Medicament object. It updates the
	 * data and the images.
	 * 
	 * @param medicament Medicament object
	 * @param imageMedicament imageMedicament represents the image of the medicament (uploaded file via multipart request). This parameter is not required.
	 * @param imageRcpList imageRcpList represents the list of images of the Rcp object (uploaded files via multipart request). This parameter is not required.
	 * @return Medicament object in "application/json" data format
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException   the service is not available
	 * @throws UnvalidException UnvalidException is thrown when there is an internal error
	 */
	@PutMapping(value = PATH_RESOURCE_UPDATE, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Medicament> updateMedicament(@RequestPart(MEDICAMENT_TEXT) Medicament medicament, @RequestPart(value = HEADER_IMAGE_MEDICAMENT, required = false) MultipartFile imageMedicament,
			@RequestPart(value = HEADER_IMAGE_RCP, required = false) List<MultipartFile> imageRcpList) throws NotFoundException, UnavailableException, UnvalidException {		
		return restApiService.updateMedicament(medicament, imageMedicament, imageRcpList);
	}
	
	/**
	 * The method allows the client to download an image by Medicament object id.
	 * 
	 * @param stringId String id of the Medicament object
	 * @return Image in "application/octet-stream" format
	 * @throws UnavailableException the service is not available
	 * @throws UnvalidException the parameter stringId is not valid
	 */
	@GetMapping(value = PATH_RESOURCE_MEDICAMENT_IMAGE, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, IMAGE_TYPE_JPG, IMAGE_TYPE_JPEG, IMAGE_TYPE_PNG})
	public ResponseEntity<byte[]> getMedicamentImage(@PathVariable(PARAM_ENTITY_ID) String stringId)
			throws NotFoundException, UnavailableException, UnvalidException {
		return restApiService.getMedicamentImage(stringId);
	}
	
	/**
	 * The method allows the client to download an image by Medicament object id and
	 * an image number from the Rcp object.
	 * 
	 * @param stringId     String id of the Medicament object
	 * @param stringNumber String number of image number.
	 * @return Image in "application/octet-stream" format
	 * @throws NotFoundException    id parameter does not exist
	 * @throws UnavailableException the service is not available
	 * @throws UnvalidException the parameter stringId is not valid or the parameter stringNumber is not valid
	 */
	@GetMapping(value = PATH_RESOURCE_RCP_IMAGE, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE, IMAGE_TYPE_JPG, IMAGE_TYPE_JPEG, IMAGE_TYPE_PNG})
	public ResponseEntity<byte[]> getRcpImage(@PathVariable(PARAM_ENTITY_ID) String stringId, @PathVariable(PARAM_NUMBER) String stringNumber)
			throws NotFoundException, UnavailableException, UnvalidException{
		return restApiService.getRcpImage(stringId, stringNumber);
	}
}
