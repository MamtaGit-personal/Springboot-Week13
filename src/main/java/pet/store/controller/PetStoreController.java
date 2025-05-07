package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
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
	public PetStoreData insertPetStoreData(@RequestBody PetStoreData petStoreData) 
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
	 *                     Delete a pet_store
    **************************************************************************/
	
	/**************************************************************************
	 *                       Retrieve pet_stores
    **************************************************************************/
}
