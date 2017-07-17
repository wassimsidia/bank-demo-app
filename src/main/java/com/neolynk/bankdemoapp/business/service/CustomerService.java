package com.neolynk.bankdemoapp.business.service;

import java.util.List;

import com.neolynk.bankdemoapp.model.Customer;

/**
 * Created by wassim on 2017/07/16
 */
public interface CustomerService {
	
	/**
	 * Retrieving customer by customer number
	 * @param customerNumber : customer number
	 * @return : {@link Customer} customer account
	 */
	public Customer findByCustomerNumber(String customerNumber);
	
	/**
	 * Saving new customer
	 * @param customer : {@link Customer} customer form
	 * @return : {@link Customer} customer account
	 */
	public Customer save(Customer customer);
	
	/**
	 * Update customer account
	 * @param customer : {@link Customer}
	 * @return : {@link Customer} customer account
	 */
	public Customer update(Customer customer);
	
	/**
	 * Delete customer account
	 * @param customerNumber : customer number
	 * @return : {@link Boolean} 
	 */
	public Boolean delete(String customerNumber);
	
	/**
	 * list of all bank customers, we can specify account fetch Eager or lazy 
	 *  
	 * @return {@link Customer} : customers lists
	 */
	public List<Customer> getAllCustomers();
	

}
