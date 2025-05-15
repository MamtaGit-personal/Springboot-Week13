package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store") //application name
@Slf4j
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;
	
	/**************************************************************************
	 *                   Create a pet_store. All the fields of petStore 
	 *                   is provided by the user
    **************************************************************************/
	@PostMapping("/pet_store") //resource name
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) 
	{
		log.info("Creating and then saving Pet Store data {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	/**************************************************************************
	 *      Update a pet_store. User has to provide the petStoreID.
    **************************************************************************/
	@PutMapping("/pet_store/{petStoreId}") // resource ID
	public PetStoreData updatePetStoreData(@PathVariable Long petStoreId,
			@RequestBody PetStoreData petStoreData) 
	{
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating a Pet Store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	/**************************************************************************
	 *                     Delete all pet_stores
    **************************************************************************/
	@DeleteMapping("/pet_store")
	public void deleteAllPetStores() {
		log.info("Attempting to delete all petStores.");
		throw new UnsupportedOperationException("Deleting all PetStores in NOT allowed.");
	}
	
	/**************************************************************************
	 *                     Delete a pet_store for a given petStoreId
    **************************************************************************/
	@DeleteMapping("/pet_store/{petStoreId}")
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Deleting a petStore with ID={}", petStoreId);
		petStoreService.deletePetStoreById(petStoreId);
		
		return Map.of("message", "Deletion of petStore with ID=" + petStoreId + " was successful.");
	}
	
	/**************************************************************************
	 *                       Retrieve all petStores
    **************************************************************************/
	@GetMapping("/pet_store")
	public List<PetStoreData> retrieveAllPetStores() {
		log.info("Retrieving all the Pet Stores data.");
		return petStoreService.retrieveAllPetStores();
	}
	
	/**************************************************************************
	 *                   Retrieve a petStore by petStoreId
    **************************************************************************/
	@GetMapping("/pet_store/{petStoreId}")
	public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving Pet Store data for a given ID {}", petStoreId);
		return petStoreService.retrievePetStoreById(petStoreId);
	}
	
	/**************************************************************************
	 *                  Week 15 work
    **************************************************************************/
	/************************************************************************
	 *             Create a pet_store employee. All the fields of employee 
	 *             is provided by the user
    *************************************************************************/
	@PostMapping("/pet_store/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee createEmployeeData(@PathVariable Long petStoreId,
			@RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Creating and then saving Employee data with ID={}", petStoreEmployee, petStoreId);
		
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
		
	/**************************************************************************
	 *             Create a pet_store customer. All the fields of customer 
	 *             is provided by the user
    **************************************************************************/
	@PostMapping("/pet_store/{petStoreId}/customer") 
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer createCustomer(@PathVariable Long petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer) {
		
		log.info("Creating and then saving Customer data with ID={}", petStoreCustomer, petStoreId);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
}
