package com.neolynk.bankdemoapp.dao.impl;

import java.util.List;

import com.neolynk.bankdemoapp.dao.AccountDAO;
import com.neolynk.bankdemoapp.model.Account;

/**
 * Created by wassim on 2017/07/16
 * TODO specify DAO access layer 
 * To be continued after specifying ORM and data access layer  
 */
public class AccountDAOImpl implements AccountDAO {

	@Override
	public boolean save(Account entity) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Account findById(String entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String entityId) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Account findByAccountNumber(String accountNumber) {
		// TODO Auto-generated method stub
		return null;
	}



}
