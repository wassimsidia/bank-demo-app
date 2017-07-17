package com.neolynk.bankdemoapp.model;

/**
 * Created by wassim on 2017/07/16
 *  TODO will be completed : After defining
 * domain attributes that must be validated
 */
public class CustomerTest {

	// ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	// Validator validator = factory.getValidator();

	// //@Test
	// public void testDOB() {
	//
	// Customer customer = getAFreshCustomer();
	// Set<ConstraintViolation<Customer>> constraintViolations;
	//
	// LocalDate now = LocalDate.now();
	// LocalDate oneYearAgo = now.minusYears(1);
	// LocalDate inOneYear = now.plusYears(1);
	// LocalDate two00YearsAgo = now.minusYears(200);
	// LocalDate twentyYearsAgo = now.minusYears(20);
	//
	// customer.birthDate = oneYearAgo;
	// constraintViolations = validator.validateProperty(customer, "birthDate");
	// assertTrue("bad birthDate is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.birthDate = now;
	// constraintViolations = validator.validateProperty(customer, "birthDate");
	// assertTrue("bad birthDate is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.birthDate = inOneYear;
	// constraintViolations = validator.validateProperty(customer, "birthDate");
	// assertTrue("bad birthDate is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.birthDate = two00YearsAgo;
	// constraintViolations = validator.validateProperty(customer, "birthDate");
	// assertTrue("bad birthDate is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.birthDate = twentyYearsAgo;
	// constraintViolations = validator.validateProperty(customer, "birthDate");
	// assertEquals("good birthDate is not verified", 0,
	// constraintViolations.size());
	//
	//
	// }
	//
	// //@Test
	// public void testFirstName() {
	//
	// Customer customer = getAFreshCustomer();
	// Set<ConstraintViolation<Customer>> constraintViolations;
	//
	// customer.firstName = null;
	// constraintViolations = validator.validateProperty(customer, "firstName");
	// assertTrue("bad first name is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.firstName = "";
	// constraintViolations = validator.validateProperty(customer, "firstName");
	// assertTrue("bad first name is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.firstName =
	// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	// constraintViolations = validator.validateProperty(customer, "firstName");
	// assertTrue("bad first name is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.firstName = "François";
	// constraintViolations = validator.validateProperty(customer, "firstName");
	// assertEquals("good first name is not verified", 0,
	// constraintViolations.size());
	//
	// }
	//
	// //@Test
	// public void testLastName() {
	//
//	 Customer customer = getAFreshCustomer();
	// Set<ConstraintViolation<Customer>> constraintViolations;
	//
	// customer.lastName= null;
	// constraintViolations = validator.validateProperty(customer, "lastName");
	// assertTrue("bad lastName is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.lastName = "";
	// constraintViolations = validator.validateProperty(customer, "lastName");
	// assertTrue("bad lastName is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.lastName =
	// "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	// constraintViolations = validator.validateProperty(customer, "lastName");
	// assertTrue("bad lastName is not verified", 0 <
	// constraintViolations.size());
	//
	// customer.lastName = "Hollande";
	// constraintViolations = validator.validateProperty(customer, "lastName");
	// assertEquals("good last name is not verified", 0,
	// constraintViolations.size());
	//
	// }
	//
	// private Customer getAFreshCustomer() {
	//
	// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	//
	//
	// return new Customer(3, UUID.randomUUID(), "000303", "testFN", "testLN",
	// "customer3@gmail.com",
	// "0677500437", "encryptedPassword", LocalDate.parse("1990-01-01", dtf),
	// "street1", "FR");
	// }

}