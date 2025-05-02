package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class PetStore {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long petStoreId;
	
	private String petStoreName;
	private String directions;
	private String stateOrProvince;
	private String country;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany (cascade = CascadeType.PERSIST)
	@JoinTable(name = "pet_store_customer", 
		joinColumns = @JoinColumn(name = "pet_store_id"), 
		inverseJoinColumns = @JoinColumn(name = "customer_id"))
	private Set<Customer> customers = new HashSet<>(); // "customers" variable has been used in pt.store.entity.Customer.java class
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true) // petStore is from the Employee class
	private Set<Employee> employees = new HashSet<>();
	
}
