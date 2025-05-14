package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;


@Data
@NoArgsConstructor
public class PetStoreData {
	
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	
	private Set<PetStoreEmployee> employees = new HashSet<>();
	private Set<PetStoreCustomer> customers = new HashSet<>();
	
	public PetStoreData(PetStore petStore)
	{
		petStoreId = petStore.getPetStoreId();
		petStoreName = petStore.getPetStoreName();
		petStoreAddress = petStore.getPetStoreCity();
		petStoreCity = petStore.getPetStoreCity();
		petStoreState = petStore.getPetStoreState();
		petStoreZip = petStore.getPetStoreZip();
		petStorePhone = petStore.getPetStorePhone();
		
		for(Customer customer: petStore.getCustomers()) 
		{
			this.customers.add(new PetStoreCustomer(customer));
		}
		
		for(Employee employee : petStore.getEmployees()) 
		{
			this.employees.add(new PetStoreEmployee(employee));
		} 
		
	} //PetStoreData(PetPark petPark)
	
	@Data
	@NoArgsConstructor
	public class PetStoreCustomer {
		
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerEmail;
		
		public PetStoreCustomer(Customer customer)
		{
			this.customerId = customer.getCustomerId();
			this.customerFirstName = customer.getCustomerFirstName();
			this.customerLastName = customer.getCustomerLastName();
			this.customerEmail = customer.getCustomerEmail();
					
		} //PetStoreCustomer(PetPark petPark)
	}
	
	@Data
	@NoArgsConstructor
	public class PetStoreEmployee {
		private Long employeeId;
		
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeeJobTitle;
		
		public PetStoreEmployee(Employee employee){
			
			this.employeeId = employee.getEmployeeId();
			this.employeeFirstName = employee.getEmployeeFirstName();
			this.employeeLastName = employee.getEmployeeLastName();
			this.employeePhone = employee.getEmployeePhone();
			this.employeeJobTitle = employee.getEmployeeJobTitle();
		}
	}

}
