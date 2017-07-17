package com.neolynk.bankdemoapp.business.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.neolynk.bankdemoapp.business.service.AccountService;
import com.neolynk.bankdemoapp.dao.AccountDAO;
import com.neolynk.bankdemoapp.model.Account;

/**
 * Created by wassim on 2017/07/16
 */
public class AccountServiceImpl implements AccountService {

	// TODO ADD try catch block and logger
	// private final Logger logger =
	// LogManager.getLogger(AccountServiceImpl.class);

	/**
	 * Inject accountDAO
	 */
	@Inject
	AccountDAO accountDAO;

	/**
	 * this method aims to retrieve Account by account number
	 * 
	 * @author wassim
	 * @param accountNumber
	 *            : the account number
	 * @return {@link Account}
	 */
	public Account findByAccountNumber(String accountNumber) {
		List<Account> accountsList = accountDAO.findAll();
		// All customer fetch verifications can be performed by ORM before
		// performing the action
		Account account = accountsList.stream()
				.filter(accountItem -> accountNumber.equals(accountItem.getAccountNumber())).findFirst().orElse(null);
		// in case no account retrieved system will throw exeption
		if (account == null)
			throw new IllegalStateException("No Account found.");

		return account;
	}

	/**
	 * saving new account
	 * 
	 * @author wassim
	 * @param account
	 *            :{@link Account} account form to save
	 * @return {@link Account}
	 */
	public Account save(Account account) {
		List<Account> accountsList = accountDAO.findAll();
		// All customer fetch verifications can be performed by ORM before
		// performing the action
		List<Account> accountsItem = accountsList.stream()
				.filter(accountIterator -> account.getAccountNumber().equals(accountIterator.getAccountNumber()))
				.collect(Collectors.toList());
		// in case accounts list is not empty : account exists
		if (!accountsItem.isEmpty())
			throw new IllegalStateException("Account number already exists.");
		//Set date created
		account.setDateCreated(Instant.now());
		if (!this.accountDAO.save(account))
			throw new IllegalStateException("New Customer can't be saved.");

		return account;
	}

	/**
	 * update account
	 * 
	 * @author wassim
	 * @param account:
	 *            {@link Account} account form to save
	 * @return {@link Account}
	 */
	public Account update(Account account) {
		List<Account> accountsList = accountDAO.findAll();
		// All customer fetch verifications can be performed by ORM before
		// performing the action
		Account accountItem = accountsList.stream()
				.filter(accountItr -> account.equals(accountItr)).findFirst()
				.orElse(null);
		//Set update date
		account.setDateUpdated(Instant.now());
		if (accountItem == null)
			throw new IllegalStateException("No account found.");
		// update account
		if (!this.accountDAO.save(account))
			throw new IllegalStateException("New account can be saved.");

		return account;
	}

	/**
	 * Delete customer account
	 * 
	 * @author wassim
	 * @param accountNumber:
	 *            account number
	 * @return {@link Boolean} status
	 */
	public Boolean delete(String accountNumber) {
		List<Account> accountsList = accountDAO.findAll();
		// before deleting it, we must add additional instruction to check if
		// account exists
		Account account = accountsList.stream()
				.filter(accountItr -> accountNumber.equals(accountItr.getAccountNumber())).findFirst().orElse(null);
		if (account == null)
			throw new IllegalStateException("No account found.");
		// perform delete action
		if (!accountDAO.delete(accountNumber))
			throw new IllegalStateException("An error has been occured when trying de delete account");

		return true;
	}

	/**
	 * Retrieve accounts list
	 * 
	 * @author wassim
	 * @return {@link List<Account>} accounts list
	 */
	public List<Account> getAllAccounts() {
		List<Account> accountsList = accountDAO.findAll();
		if (accountsList == null || accountsList.isEmpty())
			throw new IllegalStateException("No accounts found.");
		return accountsList;
	}

}
