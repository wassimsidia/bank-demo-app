package com.neolynk.bankdemoapp.dao.impl;

import java.util.List;

import com.neolynk.bankdemoapp.dao.CustomerDAO;
import com.neolynk.bankdemoapp.model.Customer;

/**
 * Created by wassim on 2017/07/16
 * To be continued after specifying ORM and data access layer  
 */
public class CustomerDAOImpl implements CustomerDAO {

	@Override
	public boolean save(Customer entity) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Customer findById(String entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String entityId) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Customer findByCustomerNumber(String customerNumber) {
		// TODO Auto-generated method stub
		return null;
	}


}
