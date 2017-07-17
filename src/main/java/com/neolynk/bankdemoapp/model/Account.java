package com.neolynk.bankdemoapp.model;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by wassim on 2017/07/16
 */
public class Account {

	/**
	 * Account type Enum
	 * @author wassim
	 *
	 */
	public enum AccountType {
		CHECKING, SAVINGS
	}
	
	/**
	 * Account Currency Enum
	 * @author wassim
	 *
	 */
	public enum Currencies {
		EUR, USD
	}

	/**
	 * Account Id
	 */
	private Integer id;

	/**
	 * Bank account unique ID
	 */
	private UUID accountUid;

	/**
	 * Account number
	 */
	private String accountNumber;
	
	/**
	 * Account owner
	 */
	private Customer accountOwner;
	
	/**
	 * Account type : CHECKING, SAVINGS
	 */
	private String accountType;
	
	/**
	 * Account currency code
	 */
	private String currency;
	
	/**
	 * Account balance
	 */
	private Double balance;
	
	/**
	 * Account creation date
	 */
	private Instant dateCreated;

	/**
	 * Account Update date
	 */
	private Instant dateUpdated;
	
	/**
	 * Account default status
	 */
	private Boolean active = true;
	
	/**
	 * Account deleted status
	 */
	private Boolean deleted = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UUID getAccountUid() {
		return accountUid;
	}

	public void setAccountUid(UUID accountUid) {
		this.accountUid = accountUid;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Customer getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(Customer accountOwner) {
		this.accountOwner = accountOwner;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Instant getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Instant dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Instant getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Instant dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Account(Integer id, UUID accountUid, String accountNumber, Customer accountOwner, String currency, String accountType,
			Double balance) {
		super();
		this.id = id;
		this.accountUid = accountUid;
		this.accountNumber = accountNumber;
		this.accountOwner = accountOwner;
		this.currency = currency;
		this.accountType = accountType;
		this.balance = balance;

	}
	
	public Account(){
		
	}
	
	/**
	 * Override to String method
	 */
	public String toString() {

		Field[] fields = Account.class.getFields();
		StringBuffer sb = new StringBuffer();

		for (Field field : fields) {
			try {
				if (field.get(this) != null) {
					sb.append(field.getName() + " = " + field.get(this) + "\r\n");
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * Override equals method
	 */
	public boolean equals(Object anotherObject) {
		if (!(anotherObject instanceof Account)) {
			return false;
		}

		Account anotherCustomer = (Account) anotherObject;

		return Objects.equals(accountNumber, anotherCustomer.getAccountNumber());
	}

	/**
	 * Deep equals method to compare Accounts
	 * @param anotherObject
	 * @return
	 */
	public boolean deepEquals(Object anotherObject) {
		if (!(anotherObject instanceof Customer)) {
			return false;
		}

		Account anotherAccount = (Account) anotherObject;
		// we compare customer unique attributes
		return Objects.equals(id, anotherAccount.getId())
				&& Objects.equals(accountUid, anotherAccount.getAccountUid())
				&& Objects.equals(accountNumber, anotherAccount.getAccountNumber())
				&& Objects.equals(accountOwner, anotherAccount.getAccountOwner());

	}

}
