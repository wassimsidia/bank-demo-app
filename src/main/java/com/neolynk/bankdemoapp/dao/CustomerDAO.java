package com.neolynk.bankdemoapp.dao;


import com.neolynk.bankdemoapp.model.Customer;

/**
 * Created by wassim on 2017/07/16
 */
public interface CustomerDAO extends CrudService<Customer, String> {

	/**
	 * This method aims to find customer by customer number
	 * @param customerNumber : customer number
	 * @return {@link Customer}
	 */
	public Customer findByCustomerNumber(String customerNumber); //  throws Exception, ApplicationException;	;
}
