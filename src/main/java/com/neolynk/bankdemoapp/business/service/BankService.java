package com.neolynk.bankdemoapp.business.service;

import java.util.List;

import com.neolynk.bankdemoapp.model.Account;
import com.neolynk.bankdemoapp.model.Customer;
import com.neolynk.demoapp.common.Transactions;

/**
 * Created by wassim on 2017/07/16
 */
public interface BankService {

	/**
	 * This method aims to perform debit and credit action to account
	 * 
	 * @param customerNumber
	 *            : the customer number
	 * @param accountNumber
	 *            : customer account number
	 * @param actionType
	 *            : {@link Transactions} DEBIT | CREDIT action type
	 * @param amount
	 *            : amount to withdraw or deposit
	 * @return {@link Account} account after transaction success
	 */
	public Account accountDebitCredit(String customerNumber, String accountNumber, String actionType, Double amount);

	/**
	 * This method aims to add existing account to customer
	 * 
	 * @param customerNumber
	 *            : the customer number
	 * @param accountNumber
	 *            : Account number
	 * @return {@link Customer} customer after update
	 */
	public Customer linkAccountToCustomer(String customerNumber, String accountNumber);

	/**
	 * This method aims to retrieve all customer Accounts
	 * 
	 * @param customerNumber
	 *            : customer number
	 * @return : {@link List<Account>} list of accounts
	 */
	public List<Account> getCustomerAccounts(String customerNumber);

	/**
	 * This method aims to calculate customer accounts balance
	 * 
	 * @param customerNumber
	 *            : customer number
	 * @return : {@link Double} sum
	 */
	public Double customerAccountsBalance(String customerNumber);
}
