package com.gmail.ramawthar.priyash.elastic.process;

import java.math.BigDecimal;

public class BatchedTransactionObj {

	
	private String tranDate;
	private String reference;
	private String account;
	private String categoryTree;   
	private String amount; 
	private String user;
	
	public BatchedTransactionObj() {
		super();
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCategoryTree() {
		return categoryTree;
	}

	public void setCategoryTree(String categoryTree) {
		this.categoryTree = categoryTree;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String bacth) {
		this.user = bacth;
	}
	
	
}
