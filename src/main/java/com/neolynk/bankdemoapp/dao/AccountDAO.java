package com.neolynk.bankdemoapp.dao;


import com.neolynk.bankdemoapp.model.Account;

/**
 * Created by wassim on 2017/07/16
 */
public interface AccountDAO extends CrudService<Account, String> {

	/**
	 * DAO : fetch customer account by account number
	 * @param accountNumber : account number
	 * @return : {@link Account}
	 */
	public Account findByAccountNumber(String accountNumber); //throws Exception, ApplicationException;
}
