package com.gmail.ramawthar.priyash.elastic.process;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.StringTokenizer;

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
    		else if (count==3){}//ignore balance
    		else if (count==4){batchedTransactionObj.setReference(st.nextToken());}//reference
    		//else if (count==5){batchedTransactionObj.setCategoryTree(st.nextToken());}//categoryTree
    		//else if (count==6){batchedTransactionObj.setAccount(st.nextToken());}//account
    		//else if (count==7){batchedTransactionObj.setUser(transactionLine);}//user
        }
    	
    	batchedTransactionObj.setCategoryTree("UNCATEGORISED");
    	batchedTransactionObj.setAccount(this.trxnAccount);
    	batchedTransactionObj.setUser(this.trxnUser);
    	
		if (batchedTransactionObj.getAmount().startsWith("-")){
			batchedTransactionObj.setReference("expenseUNCAT");
		}
		else{batchedTransactionObj.setReference("incomeUNCAT");};
    	
		/*
		String tranType = "I";
    	if (batchedTransactionObj.getAmount().startsWith("-")){
    		tranType = "E";
		}
    	
    	//call the get category family here
    	
    	//batchedTransactionObj.setCategoryTree
    	final String uri = "http://127.0.0.1:9875/fetchPath";
    	FetchPathInput fip = new FetchPathInput();
    	fip.setCategory(batchedTransactionObj.getReference());
    	fip.setTranType(tranType);
    	
    	RestTemplate restTemplate = new RestTemplate();
    	
    	String categoryFamily= restTemplate.postForObject(uri, fip, String.class);*/
    	String categoryFamily= batchedTransactionObj.getReference();
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
		
		
		batchedTransactionService.save(batchedTransaction);
	}
}
