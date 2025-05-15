package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	
	// added on Week 15
	@Autowired
	private EmployeeDao employeeDao; 
	
	@Autowired
	private CustomerDao customerDao;
	
	/******************************************************************
	 * savePetStore() method - 
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
	 * findOrCreatePetStore() method - is called to see if the pet 
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
	
	/****************************************************************************/
	/************************        Week 15 below      ******************************/
	/****************************************************************************/
		
	/********************************************************************
	 *     Retrieve all the rows from petStore table.
	 ********************************************************************/
	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for(PetStore petStore : petStores) {
			result.add(new PetStoreData(petStore));
		}
		
		return result;
	/*	
		// @formatter:off
		return petStoreDao.findAll()
				.stream()
				.map(PetStoreData::new)
				.toList();
		// @formatter:on
	*/
	}
	
	/********************************************************************
	 *        Retrieve petStore for a given petStoreId
	 ********************************************************************/
	@Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		PetStoreData petStoreData = new PetStoreData(petStore);
		return petStoreData;
	}
	
	/********************************************************************
	 *        Delete petStore for a given petStoreId
	 ********************************************************************/
	@Transactional(readOnly = false)
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
				
	}
	
	/********************************************************************
	 *          Save an employee's information provided by user
	 ********************************************************************/
	
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);
		
		copyEmployeeFromPetStoreEmployee(employee, petStoreEmployee);
		employee.setPetStore(petStore);   
		petStore.getEmployees().add(employee);
		
		Employee savedEmployee = employeeDao.save(employee);
		return new PetStoreEmployee(savedEmployee); 
	}
		
	/******************************************************************/
	
	private void copyEmployeeFromPetStoreEmployee(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeId(petStoreEmployee.getEmployeeId());
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
		
	}

	/******************************************************************/
	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
				
		if(Objects.isNull(employeeId)) {
			return new Employee();
		}
	return findEmployeeById(petStoreId, employeeId);
		
	}
	
	/******************************************************************/
	 private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow( () -> new NoSuchElementException(
				"Employee with ID=" + employeeId + " was NOT found.") );
		
		if(employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new IllegalArgumentException("Employee with ID=" + employeeId + " was NOT found.");
		}
		
		return employee; 
	}

	/******************************************************************/
	 @Transactional(readOnly = false)
	 public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);
		
		copyCustomerFromPetStoreCustomer(customer, petStoreCustomer);
		
		customer.getPetStores().add(petStore);  
		petStore.getCustomers().add(customer);
		
		Customer savedCustomer = customerDao.save(customer);
		return new PetStoreCustomer(savedCustomer); 
	}
	
	/******************************************************************/
	private void copyCustomerFromPetStoreCustomer(Customer customer, PetStoreCustomer petStoreCustomer) {
		
		customer.setCustomerId(petStoreCustomer.getCustomerId());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}
	
	/******************************************************************/
	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		
		if(Objects.isNull(customerId)) {
			return new Customer();
		}
	return findCustomerById(petStoreId, customerId);
	}
	
	
	/******************************************************************/
	private Customer findCustomerById(Long petStoreId, Long customerId) {
		
		Customer customer = customerDao.findById(customerId)
				.orElseThrow( () -> new NoSuchElementException(
				"Customer with ID=" + customerId + " was NOT found.") );
		
		boolean foundCustomer = false;
		
		// loop through each PetStore and see if PetStoreId was found for a given customer.
		for(PetStore petStore : customer.getPetStores())
		{    
			if(petStore.getPetStoreId() == petStoreId)
			{
				foundCustomer = true;
			}
		}
		
		if(foundCustomer)
			return customer;
		else throw new IllegalArgumentException("Customer with ID=" + customerId + " was NOT found.");
	}
	
	/******************************************************************/
	
}
