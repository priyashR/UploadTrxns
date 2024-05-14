package com.gmail.ramawthar.priyash.elastic.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.springframework.web.client.RestTemplate;

import com.gmail.ramawthar.priyash.elastic.model.BatchedTransaction;
//import com.gmail.ramawthar.priyash.elastic.model.Transaction;
import com.gmail.ramawthar.priyash.elastic.service.BatchedTransactionService;
//import org.springframework.web.client.RestTemplate;

public class ProcessBatchedTransactions {
	String transactionLine;
	BatchedTransactionObj batchedTransactionObj = new BatchedTransactionObj();
	BatchedTransactionService batchedTransactionService;
	String trxnUser = "";
	String trxnAccount = "";

	public ProcessBatchedTransactions(String transaction, BatchedTransactionService batchedTransactionService, String user, String account) {
		this.batchedTransactionService = batchedTransactionService;
		this.transactionLine = transaction;
		this.trxnUser = user;
		this.trxnAccount = account;
	}
	
	public void action(){
		loadTransactionObj();
		pushTransactionToDB();
		
	}
	
	
	private void loadTransactionObj(){
		System.out.println(transactionLine);
		StringTokenizer st = new StringTokenizer(transactionLine,",");  
		int count = 0;
    	while ((st.hasMoreTokens()) & (count<5)) {  
    		count++;

    		if (count==1){batchedTransactionObj.setTranDate(st.nextToken());}//tranDate 
    		else if (count==2){batchedTransactionObj.setAmount(st.nextToken().trim());}//amount
    		else if (count==3){st.nextToken();}//ignore balance
    		else if (count==4){batchedTransactionObj.setReference(st.nextToken().trim());}//reference
    		//else if (count==5){batchedTransactionObj.setCategoryTree(st.nextToken());}//categoryTree
    		//else if (count==6){batchedTransactionObj.setAccount(st.nextToken());}//account
    		//else if (count==7){batchedTransactionObj.setUser(transactionLine);}//user
        }
    	
    	batchedTransactionObj.setCategoryTree("UNCATEGORISED");
    	batchedTransactionObj.setAccount(this.trxnAccount);
    	batchedTransactionObj.setUser(this.trxnUser);
    	
    	
    	
    	batchedTransactionObj.setReference(trimReference(batchedTransactionObj.getReference()));
    	
    	/*
		if (batchedTransactionObj.getAmount().startsWith("-")){
			batchedTransactionObj.setReference("expenseUNCAT");
		}
		else{batchedTransactionObj.setReference("incomeUNCAT");};*/
    	
		
		String tranType = "I";
    	if (batchedTransactionObj.getAmount().startsWith("-")){
    		tranType = "E";
		}
    	
    	//call the get category family here
    	
    	//batchedTransactionObj.setCategoryTree
    	final String uri = "http://127.0.0.1:9006/fetchPath";
    	FetchPathInput fip = new FetchPathInput();
    	fip.setCategory(batchedTransactionObj.getReference());
    	fip.setTranType(tranType);
    	
    	RestTemplate restTemplate = new RestTemplate();
    	
    	String categoryFamily= restTemplate.postForObject(uri, fip, String.class);
    	
    	//String categoryFamily= batchedTransactionObj.getReference();
    	batchedTransactionObj.setCategoryTree(categoryFamily);
    	System.out.println(batchedTransactionObj.getAccount()+" "+batchedTransactionObj.getAmount()+" "+batchedTransactionObj.getCategoryTree()+" " +batchedTransactionObj.getUser());
    	//System.out.println("result: " +categoryFamily);
	}
	
	private void pushTransactionToDB(){
		
		BatchedTransaction batchedTransaction = new BatchedTransaction(batchedTransactionObj.getUser()+batchedTransactionObj.getTranDate()+batchedTransactionObj.getCategoryTree()+Calendar.getInstance().getTimeInMillis(),
																	   batchedTransactionObj.getTranDate(), 
																	   batchedTransactionObj.getReference(), 
																	   batchedTransactionObj.getAccount(), 
																	   batchedTransactionObj.getCategoryTree(), 
																	   new BigDecimal(batchedTransactionObj.getAmount()),
																	   batchedTransactionObj.getUser());
		
		System.out.println("saving the transaction");
		batchedTransactionService.save(batchedTransaction);
		System.out.println("DONE saving the transaction");
	}
	
	private String trimReference(String ref) {
		
		if (ref.trim().toUpperCase().startsWith("ADT JHB")){
			return "ADT JHB";
		}
		
		if (ref.trim().toUpperCase().startsWith("BEAME")){
			return "BEAME";
		}
		
		if (ref.trim().toUpperCase().startsWith("1BB CC")){
			return "1BB CC";
		}
		
		if (ref.trim().toUpperCase().startsWith("CELL CASH")){
			return "CELL CASH";
		}
		
		if (ref.trim().toUpperCase().startsWith("FNB INSURE")){
			return "FNB INSURE";
		}
		
		if (ref.trim().toUpperCase().startsWith("FNBBROKERS")){
			return "FNBBROKERS";
		}
		
		if (ref.trim().toUpperCase().startsWith("FNB CC")){
			return "FNB CC";
		}
		
		if (ref.trim().toUpperCase().startsWith("HM CONNECTHC1")){
			return "HM CONNECTHC1";
		}
		
		if (ref.trim().toUpperCase().startsWith("LIBERTY050")){
			return "LIBERTY050";
		}
		
		if (ref.trim().toUpperCase().startsWith("SALARY")){
			return "SALARY";
		}
		
		return ref;
	}
	
	
	
	
	
	
	
	
	
	
}
