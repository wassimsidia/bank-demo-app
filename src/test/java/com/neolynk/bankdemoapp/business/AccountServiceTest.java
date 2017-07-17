package com.neolynk.bankdemoapp.business;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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

import com.neolynk.bankdemoapp.business.service.impl.AccountServiceImpl;
import com.neolynk.bankdemoapp.dao.AccountDAO;
import com.neolynk.bankdemoapp.dao.CustomerDAO;
import com.neolynk.bankdemoapp.model.Account;
import com.neolynk.bankdemoapp.model.Customer;

/**
 * Created by wassim on 2017/07/16
 */
public class AccountServiceTest {

	@InjectMocks
	AccountServiceImpl accountService;

	@Mock
	AccountDAO accountDAO;

	@Mock
	Account account;

	@Mock
	List<Account> accounts;
	
	@Mock
	CustomerDAO customerDAO;

	@Captor
	private ArgumentCaptor<Account> accountCaptor;

	// @BeforeClass
	// public static void setUp(){
	// account = new Account();
	// account.setId(1);
	// accounts = Arrays.asList(account);
	// System.out.println(accounts.size());
	// }
	/**
	 * Before perform Test methods
	 * load customer
	 * mock DAO methods 
	 * @throws Exception
	 */
	@Before
	public void initTest() throws Exception {
		Customer customer = getMockedCustomer();
		List<Account> allAccounts = getAccountsMock();
		
		MockitoAnnotations.initMocks(this);
		when(accountDAO.save(any(Account.class))).thenReturn(true);
		when(accountDAO.delete(any(String.class))).thenReturn(true);
		
		when(accountDAO.findAll()).thenReturn(allAccounts);
		when(customerDAO.findByCustomerNumber("000303")).thenReturn(customer);

	}
	
	/**
	 * 1- Get Accounts List : SUCCESS
	 */
	@Test
	public void getAccountsListSuccess() {
		accounts = accountService.getAllAccounts();
		assertThat(accounts.size(), is(2));
		verify(accountDAO, times(1)).findAll();
	}

	/**
	 * 2- Get Accounts List : FAILURE
	 */
	@Test(expected = IllegalStateException.class)
	public void getAccountsListFailure() {
		when(accountDAO.findAll()).thenReturn(null);
		assertNull(accountService.getAllAccounts());
		verify(accountDAO, times(1)).findAll();
	}

	/**
	 * 3 - ADD new Account : SUCCESS
	 */
	@Test
	public void addAccountSuccess() {
		Customer customer = customerDAO.findByCustomerNumber("000303");
		Account newAccount = new Account(1, UUID.randomUUID(), "FR1999999999993", customer, Account.Currencies.EUR.toString() ,Account.AccountType.CHECKING.toString(), 100.0);
		account = accountService.save(newAccount);
		verify(accountDAO, times(1)).save(any(Account.class));
		verify(accountDAO, times(1)).findAll();
		assertEquals(account.getAccountNumber(), newAccount.getAccountNumber());
		assertEquals(account.getAccountOwner().getCustomerNumber(), newAccount.getAccountOwner().getCustomerNumber());
	}

	/**
	 * 4 - ADD new Account : FAILURE
	 */
	@Test(expected = IllegalStateException.class)
	public void addAccountFailure() {
		Customer customer = customerDAO.findByCustomerNumber("000303");
		Account newAccount = new Account(1, UUID.randomUUID(), "FR1999999999991", customer, Account.Currencies.EUR.toString() ,Account.AccountType.CHECKING.toString(), 100.0);
		account =  accountService.save(newAccount);
		assertNull(account);
		verify(accountDAO, times(1)).findAll();
		verify(accountDAO, Mockito.never()).save(any(Account.class));
	}

	/**
	 * 5 - Update Account : SUCCESS
	 */
	@Test
	public void updateAccountSuccess() {
		
		Account existingAccount = new Account(1, UUID.randomUUID(), "FR1999999999991", null, Account.Currencies.EUR.toString() ,Account.AccountType.CHECKING.toString(), 100.0);
		existingAccount.setActive(false);
		accountService.update(existingAccount);
		verify(accountDAO, times(1)).save(any(Account.class));
		verify(accountDAO, times(1)).findAll();
	}

	/**
	 * 6 - Update Account : FAILURE
	 */
	@Test(expected = IllegalStateException.class)
	public void updateAccountFailure() {
		Account existingAccount = new Account();
		existingAccount.setAccountNumber("FR1999999999998");
		existingAccount.setActive(false);
		account = accountService.update(existingAccount);
		assertNull(account);
		verify(accountDAO, Mockito.never()).save(any(Account.class));
		verify(accountDAO, times(1)).findAll();
	}

	/**
	 * 7 - Find Account : SUCCESS
	 */
	@Test
	public void findAccountSuccess() {
		List<Account> allAccounts = getAccountsMock();

		Account accountRow = allAccounts.stream()
				.filter(accountItem -> "FR1999999999991".equals(accountItem.getAccountNumber())).findFirst().orElse(null);

		account = accountService.findByAccountNumber("FR1999999999991");
		verify(accountDAO, times(1)).findAll();
		assertSame(accountRow.getAccountNumber(), account.getAccountNumber());
	}

	/**
	 * 8 - Find Account : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void findAccountFails() {
		List<Account> allAccounts = getAccountsMock();
		when(accountDAO.findAll()).thenReturn(allAccounts);
		account = accountService.findByAccountNumber("FR1999999999999");
		verify(accountDAO, times(1)).findAll();
		assertNull(account);
	}

	/**
	 * 9 - delete account : SUCCESS
	 */
	@Test
	public void deleteAccountSuccess() {
		List<Account> allAccounts = getAccountsMock();
		assertTrue(accountService.delete("FR1999999999991"));
		verify(accountDAO, times(1)).delete(any(String.class));
		verify(accountDAO, times(1)).findAll();
		allAccounts.removeIf(accountItr -> "FR1999999999991".equals(accountItr.getAccountNumber()));
		//OR
//		allAccounts = allAccounts.stream()
//				.filter(iterator -> !"FR1999999999991".equals(iterator.getAccountNumber()))
//				.collect(Collectors.toList());
		when(accountDAO.findAll()).thenReturn(allAccounts);
        assertEquals(1, accountDAO.findAll().size());
		
	}

	/**
	 * 10 - delete account : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void deleteAccountFails() {
		
		List<Account> allAccounts = getAccountsMock();
		when(accountDAO.findAll()).thenReturn(allAccounts);
		assertNull(accountService.delete("FR1999999999999"));
		verify(accountDAO, times(1)).delete(any(String.class));
		verify(accountDAO, times(1)).findAll();
        assertEquals(2, accountDAO.findAll().size());
	}

	/**
	 * Mocking accounts to perform test cases
	 * @return {@link List<Account>} accounts list
	 */
	public List<Account> getAccountsMock() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Customer customer1  = new Customer(1, UUID.randomUUID(), "000301", "testFN", "testLN", "customer1@gmail.com", "0677500437", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
		
		Account account1 = new Account(1, UUID.randomUUID(), "FR1999999999991", customer1, Account.Currencies.EUR.toString() ,Account.AccountType.CHECKING.toString(), 100.0);
		Account account2 = new Account(2, UUID.randomUUID(), "FR1999999999992", customer1, Account.Currencies.EUR.toString() ,Account.AccountType.SAVINGS.toString(), 200.0);
		
		return new ArrayList<Account>(Arrays.asList(account1, account2));
	}
	
	/**
	 * Mock customer
	 * @return
	 */
	public Customer getMockedCustomer(){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return new Customer(1, UUID.randomUUID(), "000303", "testFN", "testLN", "customer1@gmail.com", "0677500437", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
	}


}
