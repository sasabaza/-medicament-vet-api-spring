package fr.medicamentvet.application.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.medicamentvet.application.repository.RestApiRepository;
import fr.medicamentvet.application.entities.Medicament;
import fr.medicamentvet.application.exception.NotFoundException;
import fr.medicamentvet.application.utils.Utils;
import fr.medicamentvet.application.entities.SearchForm;
import fr.medicamentvet.application.entities.UpdateForm;
import fr.medicamentvet.application.entities.MedicamentSearch;
import fr.medicamentvet.application.exception.UnavailableException;
import fr.medicamentvet.application.exception.UnvalidException;

@Service
public class RestApiService {

//	private static final String PATH_MEDICAMENT_IMAGE = "\\medicament\\image";
	private static final String PATH_MEDICAMENT_IMAGE = "/medicament/image";
//	private static final String PATH_RCP_IMAGE = "\\rcp\\image";
	private static final String PATH_RCP_IMAGE = "/rcp/image";

	private static final String REGEX_NATURAL_INTEGER_TEXT = "^[1-9]|[1-9][0-9]{1,4}";
	private static final Pattern PATTERN_NATURAL_INTEGER = Pattern.compile(REGEX_NATURAL_INTEGER_TEXT);

	private static final String ID_TEXT = "id ";
	private static final String SERVICE_NON_DISPONIBLE_1 = "Le service n'est pas disponible (id ";
	private static final String SERVICE_NON_DISPONIBLE_2 = ")";
	private static final String ID_INVALIDE_TEXT_1 = "id est invalide (id ";
	private static final String ID_INVALIDE_TEXT_2 = ")";
	private static final String INTROUVABLE_TEXT = " est introuvable";
	private static final String NUMBER_INVALIDE_TEXT = "Le numéro de l'image est invalide";

	private static final String METHOD_NAME_MEDICAMENTIMAGE = "getMedicamentImage() method";
	private static final String METHOD_NAME_RCPIMAGE = "getRcpImage() method";
	private static final String IMAGE_NUMBER_TEXT = ", image number ";

	private static final String ERREUR_INTERNE_TEXT = "Erreur interne";

	@Autowired
	private RestApiRepository restApiRepository;
	
	@Value("${image.path.folder}")
	private String imagePathFolder;
	
	/**
	 * The method returns a Medicament object given the id of the object.
	 * 
	 * @param stringId           String id of the Medicament object
	 * @return medicament Medicament object
	 * @throws NotFoundException
	 * @throws UnavailableException
	 * @throws UnvalidException
	 */
	public ResponseEntity<Medicament> getMedicamentById(String stringId)
			throws NotFoundException, UnavailableException, UnvalidException {

		if (!PATTERN_NATURAL_INTEGER.matcher(stringId).matches()) {
			throw new UnvalidException(ID_INVALIDE_TEXT_1 + stringId + ID_INVALIDE_TEXT_2);
		}

		Map<Integer, String> idNomMap = getIdNomMap();

		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(restApiRepository.getMedicamentById(Integer.parseInt(stringId)));
		}

		throw new UnavailableException(SERVICE_NON_DISPONIBLE_1 + stringId + SERVICE_NON_DISPONIBLE_2);
	}
	
	/**
	 * The purpose of the method is to delete all data associated with Medicament
	 * object and files, directories.
	 * 
	 * @param stringId           String id of the Medicament object
	 * @return Empty response if status is OK or response error when there is an
	 *         exception
	 * @throws NotFoundException
	 * @throws UnavailableException
	 * @throws UnvalidException
	 */
	public void deleteMedicamentById(String stringId)
			throws NotFoundException, UnavailableException, UnvalidException {

		if (!PATTERN_NATURAL_INTEGER.matcher(stringId).matches()) {
			throw new UnvalidException(ID_INVALIDE_TEXT_1 + stringId + ID_INVALIDE_TEXT_2);
		}

		Map<Integer, String> idNomMap = getIdNomMap();
		
		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}

			restApiRepository.deleteMedicamentById(Integer.parseInt(stringId));

			String path = imagePathFolder + stringId;
			Path directory = Paths.get(path);

			Utils.deleteDirectory(directory);
		} else {
			throw new UnavailableException(SERVICE_NON_DISPONIBLE_1 + stringId + SERVICE_NON_DISPONIBLE_2);
		}
	}
	
	/**
	 * The method allows the client to download an image by the id of the Medicament
	 * object.
	 * 
	 * @param stringId
	 * @return Image in "application/octet-stream" format
	 * @throws NotFoundException
	 * @throws UnavailableException
	 * @throws UnvalidException
	 */
	public ResponseEntity<byte[]> getMedicamentImage(String stringId)
			throws NotFoundException, UnavailableException, UnvalidException {

		if (!PATTERN_NATURAL_INTEGER.matcher(stringId).matches()) {
			throw new UnvalidException(ID_INVALIDE_TEXT_1 + stringId + ID_INVALIDE_TEXT_2);
		}

		Map<Integer, String> idNomMap = getIdNomMap();
		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}
			String pathImage = imagePathFolder + stringId + PATH_MEDICAMENT_IMAGE;

			return findFileName(pathImage, METHOD_NAME_MEDICAMENTIMAGE);
		}
		throw new UnavailableException(SERVICE_NON_DISPONIBLE_1 + stringId + SERVICE_NON_DISPONIBLE_2);
	}
	
	/**
	 * The method allows the client to download an image by the id of the Medicament
	 * object and a image number of the Rcp object.
	 * 
	 * @param stringId
	 * @param stringNumber
	 * @return Image in "application/octet-stream" format
	 * @throws NotFoundException
	 * @throws UnavailableException
	 * @throws UnvalidException
	 */
	public ResponseEntity<byte[]> getRcpImage(String stringId, String stringNumber) throws NotFoundException, UnavailableException, UnvalidException {

		if (!PATTERN_NATURAL_INTEGER.matcher(stringId).matches()) {
			throw new UnvalidException(ID_INVALIDE_TEXT_1 + stringId + ID_INVALIDE_TEXT_2);
		}

		if (!PATTERN_NATURAL_INTEGER.matcher(stringNumber).matches()) {
			throw new UnvalidException(NUMBER_INVALIDE_TEXT);
		}

		Map<Integer, String> idNomMap = getIdNomMap();
		
		if (idNomMap != null) {

			boolean exist = idNomMap.containsKey(Integer.valueOf(stringId));

			if (!exist) {
				throw new NotFoundException(ID_TEXT + stringId + INTROUVABLE_TEXT);
			}
			String pathImage = imagePathFolder + stringId + PATH_RCP_IMAGE + stringNumber;

			return findFileName(pathImage, METHOD_NAME_RCPIMAGE);
		}
		throw new UnavailableException(
				SERVICE_NON_DISPONIBLE_1 + stringId + IMAGE_NUMBER_TEXT + stringNumber + SERVICE_NON_DISPONIBLE_2);
	}
	
	/**
	 * The method updates the data of a Medicament object.
	 * 
	 * @param medicament         Medicament object
	 * @param imageMedicament
	 * @param imageRcpList
	 * @return medicament Medicament object
	 * @throws NotFoundException
	 * @throws UnavailableException
	 * @throws UnvalidException
	 */
	public ResponseEntity<Medicament> updateMedicament(Medicament medicament, MultipartFile imageMedicament,
			List<MultipartFile> imageRcpList)
			throws NotFoundException, UnavailableException, UnvalidException {

		if (medicament != null) {
			int id = medicament.getId();

			Map<Integer, String> idNomMap = getIdNomMap();

			if (idNomMap != null) {

				boolean exist = idNomMap.containsKey(id);

				if (!exist) {
					throw new NotFoundException(ID_TEXT + id + INTROUVABLE_TEXT);
				}
				
				// process

				return ResponseEntity.status(HttpStatus.OK).body(restApiRepository.updateMedicament(medicament));
			} else {
				throw new UnavailableException(SERVICE_NON_DISPONIBLE_1 + id + SERVICE_NON_DISPONIBLE_2);
			}
		}

		throw new UnvalidException(ERREUR_INTERNE_TEXT);
	}
	
	/**
	 * The method gets all noms and associated ids (K,V) of the Medicaments.
	 * 
	 * @return {@code HashMap<String, Integer>}
	 */
	public ResponseEntity<Map<String, Integer>> getAllMedicamentsNomId() {

		Map<String, Integer> nomIdMap = getNomIdMap();

		if (nomIdMap != null) {
			return ResponseEntity.status(HttpStatus.OK).body(nomIdMap);
		}

		nomIdMap = restApiRepository.getAllMedicamentsNomId();

		return ResponseEntity.status(HttpStatus.OK).body(nomIdMap);
	}
	
	/**
	 * The method performs a search with the MedicamentSearch data, and returns a
	 * list of names of the medicaments.
	 * 
	 * @param medicamentSearch   MedicamentSearch object
	 * @return List of names of the medicaments
	 */
	public ResponseEntity<List<String>> searchMedicamentNames(MedicamentSearch medicamentSearch) {

		return ResponseEntity.status(HttpStatus.OK).body(restApiRepository.searchMedicamentNames(medicamentSearch));
	}
	
	/**
	 * The method gets the inputs for the SearchForm object.
	 * 
	 * @return SearchForm object
	 */
	public ResponseEntity<SearchForm> getSearchFormInputs() {

		SearchForm searchForm = getSearchForm();

		if (searchForm != null) {
			return ResponseEntity.status(HttpStatus.OK).body(searchForm);
		}

		searchForm = restApiRepository.getSearchFormInputs();

		return ResponseEntity.status(HttpStatus.OK).body(searchForm);
	}
	
	/**
	 * The method gets the inputs for the UpdateForm object.
	 * 
	 * @return UpdateForm object
	 */
	public ResponseEntity<UpdateForm> getUpdateFormInputs() {

		UpdateForm updateForm = getUpdateForm();

		if (updateForm != null) {
			return ResponseEntity.status(HttpStatus.OK).body(updateForm);
		}

		updateForm = restApiRepository.getUpdateFormInputs();

		return ResponseEntity.status(HttpStatus.OK).body(updateForm);
	}
	
	public Map<String, Integer> getNomIdMap() {
		return RestApiRepository.getNomIdMap();
	}

	public Map<Integer, String> getIdNomMap() {
		return RestApiRepository.getIdNomMap();
	}

	public SearchForm getSearchForm() {
		return RestApiRepository.getSearchForm();
	}

	public UpdateForm getUpdateForm() {
		return RestApiRepository.getUpdateForm();
	}
	
	/**
	 * The method returns a response constructed with a file or an error if the file
	 * is not found.
	 * 
	 * @param filePath   File path
	 * @param methodName The name of parent method name
	 * @return File or a error message response
	 * @throws NotFoundException File name is not found given the filePath parameter
	 */
	private ResponseEntity<byte[]> findFileName(String filePath, String methodName) throws NotFoundException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);

		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(null);
	}
}
