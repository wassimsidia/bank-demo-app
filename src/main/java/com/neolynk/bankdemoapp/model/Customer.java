package com.neolynk.bankdemoapp.model;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.neolynk.demoapp.validation.DOB;

/**
 * Created by wassim on 2017/07/16
 */
public class Customer {

	/**
	 * Customer ID
	 */
	private Integer id;

	/**
	 * Customer bank unique ID
	 */
	private UUID customerUid;

	/**
	 * Bank customer number
	 */
	private String customerNumber;

	/**
	 * Customer First name
	 */
	private String firstName;

	/**
	 * Customer last name
	 */
	private String lastName;

	/**
	 * Customer email address
	 */
	private String email;

	/**
	 * Customer phone number
	 */
	private String phoneNumber;

	/**
	 * Customer password
	 */
	private String password;

	/**
	 * Customer birth date
	 */
	@DOB(minimumAge = 2, maximumAge = 120, message = "Min Max birth date must be respected")
	private LocalDate birthDate;

	/**
	 * Customer address
	 */
	private String address;

	/**
	 * Customer country
	 */
	private String country;

	/**
	 * Customer registration date
	 */
	private Instant dateCreated;

	/**
	 * Customer Update date
	 */
	private Instant dateUpdated;

	/**
	 * Customer accounts CASCADE by default is for all action: CREATE , UPDATE
	 * FETCH by default FETCH type is Eager
	 * 
	 */
	private List<Account> customerAccounts;
	
	private Boolean active = true;

	/**
	 * Constructor with fields
	 * 
	 * @param id
	 * @param customerUid
	 * @param customerNumber
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phoneNumber
	 * @param password
	 * @param birthDate
	 * @param address
	 * @param country
	 * @param dateCreated
	 * @param dateUpdated
	 * @param customerAccounts
	 */
	public Customer(Integer id, UUID customerUid, String customerNumber, String firstName, String lastName,
			String email, String phoneNumber, String password, LocalDate birthDate, String address, String country) {
		super();
		this.id = id;
		this.customerUid = customerUid;
		this.customerNumber = customerNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.birthDate = birthDate;
		this.address = address;
		this.country = country;

	}

	public Customer() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UUID getCustomerUid() {
		return customerUid;
	}

	public void setCustomerUid(UUID customerUid) {
		this.customerUid = customerUid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Instant getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Instant dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public List<Account> getCustomerAccounts() {
		return customerAccounts;
	}

	public void setCustomerAccounts(List<Account> customerAccounts) {
		this.customerAccounts = customerAccounts;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * Override to String method
	 */
	public String toString() {

		Field[] fields = Customer.class.getFields();
		StringBuffer sb = new StringBuffer();

		for (Field field : fields) {
			try {
				if (field.get(this) != null) {
					sb.append(field.getName() + " = " + field.get(this) + "\r\n");
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * Override equals method
	 */
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof Customer)) {
			return false;
		}

		Customer anotherCustomer = (Customer) anotherObject;

		return Objects.equals(customerNumber, anotherCustomer.getCustomerNumber());
	}

	/**
	 * Deep equals method to compare Customers
	 * @param anotherObject
	 * @return
	 */
	public boolean deepEquals(Object anotherObject) {
		if (!(anotherObject instanceof Customer)) {
			return false;
		}

		Customer anotherCustomer = (Customer) anotherObject;
		// we compare customer unique attributes
		return Objects.equals(id, anotherCustomer.getId())
//				&& Objects.equals(customerUid, anotherCustomer.getCustomerUid())
				&& Objects.equals(customerNumber, anotherCustomer.getCustomerNumber())
				&& Objects.equals(email, anotherCustomer.getEmail())
				&& Objects.equals(phoneNumber, anotherCustomer.getPhoneNumber());

	}

}
