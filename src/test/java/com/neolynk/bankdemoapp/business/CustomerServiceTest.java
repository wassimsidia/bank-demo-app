
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
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.neolynk.bankdemoapp.business.service.impl.CustomerServiceImpl;
import com.neolynk.bankdemoapp.dao.CustomerDAO;
import com.neolynk.bankdemoapp.model.Customer;

/**
 * Created by wassim on 2017/07/16
 */
public class CustomerServiceTest {

	@InjectMocks
	CustomerServiceImpl customerService;
	@Mock
	CustomerDAO customerDao;
	@Mock
	Customer customer;
	@Mock
	List<Customer> customers;

	@Captor
	private ArgumentCaptor<Customer> customerCaptor;

	// @BeforeClass
	// public static void setUp(){
	// customer = new Customer();
	// customer.setId(1);
	// customers = Arrays.asList(customer);
	// System.out.println(customers.size());
	// }
	@Before
	public void initTest() throws Exception {

		MockitoAnnotations.initMocks(this);
		when(customerDao.save(any(Customer.class))).thenReturn(true);
		when(customerDao.delete(any(String.class))).thenReturn(true);
		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
	}

	/**
	 * 1 - Get Customer List : SUCCESS
	 */
	@Test
	public void getCustomersListSuccess() {
		customers = customerService.getAllCustomers();
		// assertEquals(2, customers.size());
		assertThat(customers.size(), is(2));
		verify(customerDao, times(1)).findAll();
	}

	/**
	 * 2 - Get Customer List : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void getUsersListError() {
		when(customerDao.findAll()).thenReturn(null);
		assertNull(customerService.getAllCustomers());
		verify(customerDao, times(1)).findAll();
	}

	/**
	 * 3 - Add new Customer : SUCCESS
	 */
	@Test
	public void addCustomerSuccess() {
		Customer newCustomer = getCustomerMock();
		customer = customerService.save(newCustomer);
		verify(customerDao, times(1)).findAll();
		verify(customerDao, times(1)).save(any(Customer.class));
		assertEquals(customer.getEmail(), newCustomer.getEmail());
	}

	/**
	 * 4 - Add Customer : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void addCustomerExists() {
		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
		Customer newCustomer = new Customer();
		newCustomer.setEmail("customer2@gmail.com");
		customer = customerService.save(newCustomer);
		assertNull(customer);
		verify(customerDao, Mockito.never()).save(any(Customer.class));
		verify(customerDao, times(1)).findAll();
	}

	/**
	 * TODO add captor
	 */
	// @Test
	public void addCustomerCaptor() {
		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
		Customer customerToAdd = new Customer();

		ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);

		verify(customerDao).save(customerCaptor.capture());
		customer = customerCaptor.getValue();
		assertSame(customer, customerToAdd);
	}

	/**
	 * 5 - Update Customer : SUCCESS
	 */
	@Test
	public void updateCustomerSuccess() {
		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
		Customer existingCustomer = new Customer(2, UUID.randomUUID(), "000303", "testFN", "testLN", "customer2@gmail.com",
				"0677500438", "encryptedPassword", null, "street1", "FR");
		existingCustomer.setAddress("Verdun street");
		customerService.update(existingCustomer);
		verify(customerDao, times(1)).save(any(Customer.class));
		verify(customerDao, times(1)).findAll();
	}

	/**
	 * 6 - Update Customer : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void updateCustomerFails() {
		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
		Customer existingCustomer = new Customer();
		existingCustomer.setCustomerNumber("000308");
		existingCustomer.setEmail("customer2@gmail.com");
		existingCustomer.setAddress("Verdun street");
		customer = customerService.update(existingCustomer);
		assertNull(customer);
		verify(customerDao, Mockito.never()).save(any(Customer.class));
		verify(customerDao, times(1)).findAll();
	}

	/**
	 * 7 - Fetch Customer : SUCCESS
	 */
	@Test
	public void findCustomerSuccess() {
		List<Customer> allCustomers = getCustomersMock();

		Customer customerRow = allCustomers.stream()
				.filter(customerItem -> "000303".equals(customerItem.getCustomerNumber())).findFirst().orElse(null);

		when(customerDao.findAll()).thenReturn(allCustomers);
		customer = customerService.findByCustomerNumber("000303");
		verify(customerDao, times(1)).findAll();
		assertSame(customerRow, customer);
	}

	/**
	 * 8 - Find Customer : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void findCustomerFails() {
		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
		customer = customerService.findByCustomerNumber("000333");
		verify(customerDao, times(1)).findAll();
		assertNull(customer);
	}

	/**
	 * 9 - Delete Customer : SUCCESS
	 */
	@Test
	public void deleteCustomerSuccess() {
		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
		assertTrue(customerService.delete("000303"));
		verify(customerDao, times(1)).delete(any(String.class));
		verify(customerDao, times(1)).findAll();
		// allCustomers.removeIf(customerItr ->
		// "000303".equals(customerItr.getCustomerNumber()));
		allCustomers = allCustomers.stream().filter(iterator -> !"000303".equals(iterator.getCustomerNumber()))
				.collect(Collectors.toList());
		when(customerDao.findAll()).thenReturn(allCustomers);
		assertEquals(1, customerDao.findAll().size());
		verify(customerDao, times(2)).findAll();

	}

	/**
	 * 10 - Delete CUstomer : FAILS
	 */
	@Test(expected = IllegalStateException.class)
	public void deleteCustomerFails() {

		List<Customer> allCustomers = getCustomersMock();
		when(customerDao.findAll()).thenReturn(allCustomers);
		assertNull(customerService.delete("000343"));
		verify(customerDao, times(1)).delete(any(String.class));
		assertEquals(2, customerDao.findAll().size());
	}

	/**
	 * get List of Customers to perform test cases
	 * 
	 * @return {@link List<Customer>} : list of customers
	 */
	public List<Customer> getCustomersMock() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Customer customer1 = new Customer(1, UUID.randomUUID(), "000301", "testFN", "testLN", "customer1@gmail.com",
				"0677500436", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
		Customer customer2 = new Customer(2, UUID.randomUUID(), "000303", "testFN", "testLN", "customer2@gmail.com",
				"0677500438", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
		return new ArrayList<Customer>(Arrays.asList(customer1, customer2));

	}

	/**
	 * Mock customer
	 * 
	 * @return {
	 */
	public Customer getCustomerMock() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return new Customer(3, UUID.randomUUID(), "000303", "testFN", "testLN", "customer3@gmail.com",
				"0677500437", "encryptedPassword", LocalDate.parse("1990-01-01", dtf), "street1", "FR");
	}

}
