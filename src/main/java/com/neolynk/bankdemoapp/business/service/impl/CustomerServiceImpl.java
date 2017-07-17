package com.neolynk.bankdemoapp.business.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.neolynk.bankdemoapp.business.service.CustomerService;
import com.neolynk.bankdemoapp.dao.CustomerDAO;
import com.neolynk.bankdemoapp.model.Customer;

/**
 * Created by wassim on 2017/07/16
 */
public class CustomerServiceImpl implements CustomerService {

	// TODO ADD try catch block and logger
	// private final Logger logger =
	// LogManager.getLogger(CustomerServiceImpl.class);

	@Inject
	CustomerDAO customerDAO;

	/**
	 * Retrieving customer by customer number
	 * 
	 * @param customerNumber
	 *            : customer number
	 * @return : {@link Customer} customer account
	 */
	public Customer findByCustomerNumber(String customerNumber) {
		List<Customer> customersList = customerDAO.findAll();
		// All customer fetch verifications can be performed by ORM before
		// performing the action
		// Default fetch type is eager : retrieve customer with his accounts
		Customer customer = customersList.stream()
				.filter(customerItem -> customerNumber.equals(customerItem.getCustomerNumber())).findFirst()
				.orElse(null);

		if (customer == null)
			throw new IllegalStateException("No Customer found.");

		return customer;
	}

	/**
	 * Saving new customer
	 * 
	 * @param customer
	 *            : {@link Customer} customer form
	 * @return : {@link Customer} customer account
	 */
	public Customer save(Customer customer) {
		List<Customer> customersList = customerDAO.findAll();
		// we need to check if customer with this customer number exists by
		// adding this additional instruction
		List<Customer> customerExits = customersList.stream()
				.filter(iterator -> customer.getEmail().equals(iterator.getEmail())).collect(Collectors.toList());
		if (!customerExits.isEmpty())
			throw new IllegalStateException("Custmer Email already exists.");
		//Set create date time
		customer.setDateCreated(Instant.now());
		// if customer doesn't exist then we continue by saving it
		// By default we suppose that customer cascade is for all actions
		if (!this.customerDAO.save(customer))
			throw new IllegalStateException("New Customer can't be saved.");

		return customer;

	}

	/**
	 * Update customer account
	 * 
	 * @param customer
	 *            : {@link Customer}
	 * @return : {@link Customer} customer account
	 */
	public Customer update(Customer customer) {
		List<Customer> customersList = customerDAO.findAll();
		// All customer fetch verifications can be performed by ORM before
		// to performing the action
		Customer customerRow = customersList.stream()
				.filter(customerItem -> customer.deepEquals(customerItem))
				.findFirst().orElse(null);

		if (customerRow == null)
			throw new IllegalStateException("No Customer found.");
		//Set update time
		customer.setDateUpdated(Instant.now());
		if (!this.customerDAO.save(customer))
			throw new IllegalStateException("New account can be saved.");

		return customer;
	}

	/**
	 * Delete customer account
	 * 
	 * @param customerNumber
	 *            : customer number
	 * @return : {@link Boolean}
	 */
	public Boolean delete(String customerNumber) {
		List<Customer> customersList = customerDAO.findAll();
		// customer can be checked from data access layer
		Customer customer = customersList.stream()
				.filter(customerItem -> customerNumber.equals(customerItem.getCustomerNumber())).findFirst()
				.orElse(null);
		if (customer == null)
			throw new IllegalStateException("No Customer found.");

		if (!customerDAO.delete(customerNumber))
			throw new IllegalStateException("An error has been occured when trying de delete customer");

		return true;
	}

	/**
	 * list of all bank customers, we can specify account fetch Eager or lazy
	 * 
	 * @return {@link Customer} : customers lists
	 */
	public List<Customer> getAllCustomers() {
		List<Customer> customersList = customerDAO.findAll();
		if (customersList == null || customersList.isEmpty())
			throw new IllegalStateException("No customers found.");
		return customersList;
	}

}
