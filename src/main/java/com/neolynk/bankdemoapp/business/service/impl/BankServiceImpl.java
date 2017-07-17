package com.neolynk.bankdemoapp.business.service.impl;

import java.time.Instant;
import java.util.List;

import javax.inject.Inject;

import com.neolynk.bankdemoapp.business.service.BankService;
import com.neolynk.bankdemoapp.dao.AccountDAO;
import com.neolynk.bankdemoapp.dao.CustomerDAO;
import com.neolynk.bankdemoapp.model.Account;
import com.neolynk.bankdemoapp.model.Customer;
import com.neolynk.demoapp.common.Transactions;

/**
 * Created by wassim on 2017/07/16
 */
public class BankServiceImpl implements BankService {

	// TODO ADD try catch block and logger
	// private final Logger logger =
	// LogManager.getLogger(BankServiceImpl.class);

	/**
	 * Inject customerDAO
	 */
	@Inject
	CustomerDAO customerDAO;

	/**
	 * Inject accountDAO
	 */
	@Inject
	AccountDAO accountDAO;

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
	public Account accountDebitCredit(String customerNumber, String accountNumber, String actionType, Double amount) {
		Customer customer = customerDAO.findByCustomerNumber(customerNumber);
		// fetch customer account
		Account customerAccount = customer.getCustomerAccounts().stream()
				.filter(accountItem -> accountNumber.equals(accountItem.getAccountNumber())).findFirst().orElse(null);
		if (customerAccount == null)
			throw new IllegalStateException("No account found");
		Double debitCreditAmmount;
		// check transaction type to be performed
		if (actionType.equals(Transactions.DEBIT.getType())) {
			debitCreditAmmount = customerAccount.getBalance() - amount;
		} else {
			debitCreditAmmount = customerAccount.getBalance() + amount;
			if (debitCreditAmmount < 0)
				throw new IllegalStateException("not enough balance");

		}
		customerAccount.setBalance(debitCreditAmmount);
		//set date time update 
		customerAccount.setDateUpdated(Instant.now());
		if (!accountDAO.save(customerAccount)) {
			throw new IllegalStateException("Error occured when trying to apply changes in account balance");
		}

		return customerAccount;
	}

	/**
	 * This method aims to add existing account to customer
	 * 
	 * @param customerNumber
	 *            : the customer number
	 * @param accountNumber
	 *            : Account number
	 * @return {@link Customer} customer after update
	 */
	public Customer linkAccountToCustomer(String customerNumber, String accountNumber) {
		Customer customer = customerDAO.findByCustomerNumber(customerNumber);
		Account account = accountDAO.findByAccountNumber(accountNumber);
		// check if account has owner or affected
		if (account.getAccountOwner() != null)
			throw new IllegalStateException("Account is already affected to customer");

		customer.getCustomerAccounts().add(account);

		// No cascade for this case
		account.setAccountOwner(customer);

		// set update date time for both customer and account
		account.setDateUpdated(Instant.now());
		customer.setDateUpdated(Instant.now());
		if (!accountDAO.save(account))
			throw new IllegalStateException("Error occured when trying to update account owner");
		if (!customerDAO.save(customer))
			throw new IllegalStateException("Error occured when trying to add accounts to customer");

		return customer;
	}

	/**
	 * This method aims to retrieve all customer Accounts
	 * 
	 * @param customerNumber
	 *            : customer number
	 * @return : {@link List<Account>} list of accounts
	 */
	public List<Account> getCustomerAccounts(String customerNumber) {
		Customer customer = customerDAO.findByCustomerNumber(customerNumber);
		if (customer == null)
			throw new IllegalStateException("no customer found");
		if (customer.getCustomerAccounts().isEmpty())
			throw new IllegalStateException("Customer has no accounts");
		// FETCH type is eager
		return customer.getCustomerAccounts();
	}

	/**
	 * This method aims to calculate customer accounts balance
	 * 
	 * @param customerNumber
	 *            : customer number
	 * @return : {@link Double} sum
	 */
	public Double customerAccountsBalance(String customerNumber) {
		Customer customer = customerDAO.findByCustomerNumber(customerNumber);
		if (customer == null)
			throw new IllegalStateException("no customer found");
		// calculate balance sum
		Double totalBalancet = customer.getCustomerAccounts().stream().mapToDouble(f -> f.getBalance()).sum();
		return totalBalancet;
	}

}
