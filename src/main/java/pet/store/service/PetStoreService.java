package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	
	/******************************************************************
	 * savePetStore() method, 
	 *  calls findOrCreatePetStore() method to see if the pet 
	 *  store ID exists. There are two scenarios:
	 * 		a. ID doesn't exist - the method returns petStore 
	 * with nothing in it. Then calls setFieldsInPetStore() method to 
	 * set all the fields in petStore field and the then saves it 
	 * in the pet_store table by calling petStoreDao.save() method.
	 *      b. ID exists - the method then calls findPetStoreById()
	 * method to find the pet store data for the given ID. Then 
	 * calls setFieldsInPetStore() method to set all the fields in
	 * petStore field and the then saves it in the pet_store table by 
	 * calling petStoreDao.save() method. If the ID is incorrect then it 
	 * gives NoSuchElementException error message informing that the ID 
	 * doesn't exist. The NoSuchElementException error is handled in 
	 * the global controller error handling package.
	 * 
	 * ***************************************************************/
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		setFieldsInPetStore(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}
	
	/******************************************************************
	 * findOrCreatePetStore() method is called to see if the pet 
	 *  store ID exists. There are two scenarios:
	 * 		a. ID doesn't exist - the method returns petStore 
	 * with nothing in it. 
	 *      b. ID exists - the method then calls findPetStoreById()
	 * method to find the pet store data for the given ID.
	 * 
	 * 
	 * ***************************************************************/
	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;
		if(Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		}
		else {
			petStore = findPetStoreById(petStoreId);
		}
		return petStore;
		
	}
	
	/*******************************************************************
	 * setFieldsInPetStore() method copies all the fields from 
	 * petStoreData to petStore's respective fields
	 *******************************************************************/
	private void setFieldsInPetStore(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
	}
	 
	/********************************************************************
	 * findPetStoreById() method looks for petStore data for the given 
	 * ID. If it finds the ID the it returns petStore value for the given
	 * ID. If the ID is incorrect then it gives NoSuchElementException 
	 * error message informing that the ID doesn't exist. The 
	 * NoSuchElementException error is handled in the global controller 
	 * error handling package.
	 ********************************************************************/
	private PetStore findPetStoreById(Long petStoreId) {
		
		return petStoreDao.findById(petStoreId)
			.orElseThrow( () -> new NoSuchElementException(
			"PetStore with ID=" + petStoreId + " was NOT found.") );
	}
	
	
}
