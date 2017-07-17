package com.neolynk.bankdemoapp.business.service;

import java.util.List;

import com.neolynk.bankdemoapp.model.Account;

/**
 * Created by wassim on 2017/07/16
 */
public interface AccountService {

	/**
	 * this method aims to retrieve Account by account number
	 * 
	 * @author wassim
	 * @param accountNumber
	 *            : the account number
	 * @return {@link Account}
	 */
	public Account findByAccountNumber(String accountNumber);

	/**
	 * saving new account
	 * 
	 * @author wassim
	 * @param account
	 *            :{@link Account} account form to save
	 * @return {@link Account}
	 */
	public Account save(Account account);

	/**
	 * update account
	 * 
	 * @author wassim
	 * @param account:
	 *            {@link Account} account form to save
	 * @return {@link Account}
	 */
	public Account update(Account acoount);

	/**
	 * Delete customer account
	 * 
	 * @author wassim
	 * @param accountNumber:
	 *            account number
	 * @return {@link Boolean} status
	 */
	public Boolean delete(String accountNumber);

	/**
	 * Retrieve accounts list
	 * 
	 * @author wassim
	 * @return {@link List<Account>} accounts list
	 */
	public List<Account> getAllAccounts();

}
