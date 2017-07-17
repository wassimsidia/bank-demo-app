package com.neolynk.bankdemoapp.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.neolynk.bankdemoapp.business.service.impl.BankServiceImpl;
import com.neolynk.bankdemoapp.dao.AccountDAO;
import com.neolynk.bankdemoapp.dao.CustomerDAO;
import com.neolynk.bankdemoapp.model.Account;
import com.neolynk.bankdemoapp.model.Customer;
import com.neolynk.demoapp.common.Transactions;

/**
 * Created by wassim on 2017/07/16
 */
public class BankServiceTest {

	/**
	 * Inject Mock business Bank service
	 */
	@InjectMocks
	BankServiceImpl bankService;

	/**
	 * Mock AccountDAO which will be injected in BanqueServiceImpl
	 */
	@Mock
	AccountDAO accountDAO;

	/**
	 * Mock CustomerDAO which will be injected in BanqueServiceImpl
	 */
	@Mock
	CustomerDAO customerDAO;

	@Captor
	private ArgumentCaptor<Account> accountCaptor;

	@Captor
	private ArgumentCaptor<Account> customerCaptor;
	
//	 @BeforeClass
//	 public static void setUp(){
//	
//	 }
	/**
	 * Before perform Test methods
	 * load customer
	 * mock DAO methods 
	 * @throws Exception
	 */
	@Before
	public void initTest() throws Exception {
		Customer customer = getCustomerWithAccounts();
		List<Account> allAccounts = this.getAccountsMock();

		MockitoAnnotations.initMocks(this);
		when(accountDAO.save(any(Account.class))).thenReturn(true);
		when(accountDAO.delete(any(String.class))).thenReturn(true);

		when(accountDAO.findAll()).thenReturn(allAccounts);
		when(customerDAO.save(any(Customer.class))).thenReturn(true);
		when(customerDAO.findByCustomerNumber("000303")).thenReturn(customer);

	}

	/**
	 * 1 - withdraw and deposit : SUCCESS
	 */
	@Test
	public void accountDebitCreditSuccess() {
		Double amount = 1000.0;
		Double mustBeWhenCredit = 3000.0;
		Double mustBeWhenDebit = 2000.0;
		Account account = bankService.accountDebitCredit("000303", "FR1999999999991", Transactions.CREDIT.getType(),
				amount);

		assertEquals(mustBeWhenCredit, account.getBalance());
		account = bankService.accountDebitCredit("000303", "FR1999999999991", Transactions.DEBIT.getType(), amount);
		assertEquals(mustBeWhenDebit, account.getBalance());
		verify(accountDAO, times(2)).save(any(Account.class));
		verify(customerDAO, times(2)).findByCustomerNumber("000303");
	}

	/**
	 * 2 - withdraw and deposit : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void accountDebitCreditError() {
		Double amount = 1000.0;
		Account account = bankService.accountDebitCredit("000303", "FR1999999999997", Transactions.CREDIT.getType(),
				amount);
		assertNull(account);
		verify(accountDAO, Mockito.never()).save(any(Account.class));
		verify(customerDAO, times(1)).findByCustomerNumber("000303");
	}

	/**
	 * 3 - Link account to customer : SUCCESS
	 */
	@Test
	public void linkAccountToCustomerSuccess() {

		Account accountToLink = getAccountsMock().stream()
				.filter(accountItem -> "FR1999999999993".equals(accountItem.getAccountNumber())).findFirst()
				.orElse(null);
		when(accountDAO.findByAccountNumber("FR1999999999993")).thenReturn(accountToLink);

		Customer customer = bankService.linkAccountToCustomer("000303", "FR1999999999993");
		assertEquals(customer.getCustomerAccounts().size(), 3);
		verify(accountDAO, times(1)).save(any(Account.class));
		verify(customerDAO, times(1)).save(any(Customer.class));
		verify(customerDAO, times(1)).findByCustomerNumber("000303");
	}

	/**
	 * 4 - Link account to customer : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void linkAccountToCustomerFails() {

		Account accountToLink = getAccountsMock().stream()
				.filter(accountItem -> "FR1999999999991".equals(accountItem.getAccountNumber())).findFirst()
				.orElse(null);
		when(accountDAO.findByAccountNumber("FR1999999999991")).thenReturn(accountToLink);

		Customer customer = bankService.linkAccountToCustomer("000303", "FR1999999999991");
		assertNull(customer);
		verify(accountDAO).findByAccountNumber("FR1999999999991");
		verify(accountDAO, Mockito.never()).save(any(Account.class));
		verify(customerDAO, Mockito.never()).save(any(Customer.class));
		verify(customerDAO, times(1)).findByCustomerNumber("000303");
	}

	/**
	 * 5 - find customer accounts : SUCCESS
	 */
	@Test
	public void getCustomerAccountsSuccess() {
		Customer customer = getCustomerWithAccounts();
		List<Account> customerAccouts = bankService.getCustomerAccounts(customer.getCustomerNumber());
		assertEquals(customerAccouts.size(), 2);
		verify(customerDAO, times(1)).findByCustomerNumber(customer.getCustomerNumber());
	}

	/**
	 * 6 - find customer accounts : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void getCustomerAccountsFails() {

		assertNull(bankService.getCustomerAccounts("000301"));
		verify(customerDAO, times(1)).findByCustomerNumber("000301");
		Customer customer = getCustomerWithNoAccounts();
		assertNull(bankService.getCustomerAccounts(customer.getCustomerNumber()));
		verify(customerDAO, times(1)).findByCustomerNumber(customer.getCustomerNumber());
	}

	/**
	 * 7 - Customer Accounts Balance : SUCCESS 
	 */
	@Test
	public void customerAccountsBalanceSuccess() {
		Customer customer = getCustomerWithAccounts();
		Double balance = bankService.customerAccountsBalance(customer.getCustomerNumber());
		assertEquals(balance, (Double) customer.getCustomerAccounts().stream().mapToDouble(f -> f.getBalance()).sum());
		verify(customerDAO, times(1)).findByCustomerNumber(customer.getCustomerNumber());
	}
	
	/**
	 * 8 - Customer Accounts Balance :FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void customerAccountsBalanceFails() {
		Double balance = bankService.customerAccountsBalance("000343");
		assertNull(balance);
		verify(customerDAO, times(1)).findByCustomerNumber("000343");
	}
	
	/**
	 * get List of mocked customer with accounts to perform tests scenarios 
	 * @return {@link Account} list of accounts
	 */
	public List<Account> getAccountsMock() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Customer customer1  = new Customer(1, UUID.randomUUID(), "000301", "testFN", "testLN", "customer1@gmail.com", "0677500437", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
				
		Account account1 = new Account(1, UUID.randomUUID(), "FR1999999999991", customer1, Account.Currencies.EUR.toString() ,Account.AccountType.CHECKING.toString(), 2000.0);
		Account account2 = new Account(2, UUID.randomUUID(), "FR1999999999993", null, Account.Currencies.EUR.toString() ,Account.AccountType.SAVINGS.toString(), 3500.0);

		return new ArrayList<Account>(Arrays.asList(account1, account2));
	}

	/**
	 * define Customer with mocked accounts
	 * @return {@link Customer} customer with accounts
	 */
	public Customer getCustomerWithAccounts() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Customer customer1  = new Customer(1, UUID.randomUUID(), "000303", "testFN", "testLN", "customer1@gmail.com", "0677500437", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
		
		Account account1 = new Account(1, UUID.randomUUID(), "FR1999999999991", customer1, Account.Currencies.EUR.toString() ,Account.AccountType.CHECKING.toString(), 2000.0);
		Account account2 = new Account(2, UUID.randomUUID(), "FR1999999999992", customer1, Account.Currencies.EUR.toString() ,Account.AccountType.SAVINGS.toString(), 3500.0);
	
		customer1.setCustomerAccounts(new ArrayList<Account>(Arrays.asList(account1, account2)));
		return customer1;
	}

	/**
	 * Define customer with no accounts
	 * @return {@link Customer} customer with no accounts
	 */
	public Customer getCustomerWithNoAccounts() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return new Customer(1, UUID.randomUUID(), "000305", "testFN", "testLN", "customer1@gmail.com", "0677500437", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
	}

}
