package com.gmail.ramawthar.priyash.elastic.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.gmail.ramawthar.priyash.elastic.model.BatchedTransaction;
import com.gmail.ramawthar.priyash.elastic.process.FetchPathInput;
import com.gmail.ramawthar.priyash.elastic.process.ProcessBatchedTransactions;



@Service
public class BatchIngestServiceImpl implements BatchIngestService {
	
	private String user;
	private String account;
	private boolean processFile = false;

    public String processCSVFile(MultipartFile file){
    	System.out.println("hi");
    	String status = "Empty file!";
    	if (!(file.isEmpty())){
    		status = "File is being processed";
	    	BufferedReader br;
	    	List<String> result = new ArrayList<>();
	    	try {
	
	    	     String line;
	    	     InputStream is = file.getInputStream();
	    	     br = new BufferedReader(new InputStreamReader(is));
	    	     while ((line = br.readLine()) != null) {
	    	          result.add(line);
	    	          //System.out.println(line);
	    	          pushToDB(line);
	    	          /*TO DO: Push to elastic here*/
	    	     }
	  
	    	  } catch (IOException e) {
	    	    System.err.println(e.getMessage());       
	    	  }
	    	
	    	
	    	status = "File processed";
    	}
    	
    	return status;
    }
        
    @Autowired
    @Qualifier("BatchTrxnSrvc")
    BatchedTransactionService batchedTransactionService;
    
    
	private void pushToDB(String processedLine){
    	
    	if (processFile == false) {
    		String value;
        	StringTokenizer st = new StringTokenizer(processedLine,",");
        	
        	while (st.hasMoreTokens()) {
        		value = st.nextToken();
        		//System.out.println(value);
        		if (value.equalsIgnoreCase("Name:")){user = st.nextToken();}
        		else if (value.equalsIgnoreCase("Account:")){account = st.nextToken();}
        		else if (value.equalsIgnoreCase("Date")){processFile = true;}
        		
        	}
    		
    	}
    	
        else{
        	
        	System.out.println("Received <" + processedLine + " "+ user+ " "+ account + ">");
        	
        //System.out.println("Account = "+account);
        //System.out.println("User = "+user);
          	ProcessBatchedTransactions pbt = new ProcessBatchedTransactions(processedLine.toString(), batchedTransactionService, user, account);
            pbt.action();
        }
        
	}
	

    public String reprocessCategories() {
    	
    	
    	
    	List<BatchedTransaction> trxns = batchedTransactionService.findByLevel1("expenseUNCAT");
        for (BatchedTransaction b : trxns) {
        	 
            // Print all elements of ArrayList
            //System.out.println(b.getId());
            //System.out.println(b.getReference());
            //batchedTransactionService.delete(b);
        	
    		String tranType = "I";
        	if (b.getAmount().toString().startsWith("-")){
        		tranType = "E";
    		}
        	
        	//call the get category family here
        	
        	//batchedTransactionObj.setCategoryTree
        	final String uri = "http://127.0.0.1:9006/fetchPath";
        	FetchPathInput fip = new FetchPathInput();
        	fip.setCategory(b.getReference());
        	fip.setTranType(tranType);
        	
        	RestTemplate restTemplate = new RestTemplate();
        	
        	String categoryFamily= restTemplate.postForObject(uri, fip, String.class);
        	

        	b.setCategoryTree(categoryFamily);        	
        	
        	
        	
            //b.setReference("test2");
            
            batchedTransactionService.save(b);
            
        }
        
    	List<BatchedTransaction> trxns2 = batchedTransactionService.findByLevel1("incomeUNCAT");
        for (BatchedTransaction b : trxns2) {
        	 
            // Print all elements of ArrayList
            //System.out.println(b.getId());
            //System.out.println(b.getReference());
            //batchedTransactionService.delete(b);
        	
    		String tranType = "I";
        	if (b.getAmount().toString().startsWith("-")){
        		tranType = "E";
    		}
        	
        	//call the get category family here
        	
        	//batchedTransactionObj.setCategoryTree
        	final String uri = "http://127.0.0.1:9006/fetchPath";
        	FetchPathInput fip = new FetchPathInput();
        	fip.setCategory(b.getReference());
        	fip.setTranType(tranType);
        	
        	RestTemplate restTemplate = new RestTemplate();
        	
        	String categoryFamily= restTemplate.postForObject(uri, fip, String.class);
        	

        	b.setCategoryTree(categoryFamily);  
        	
            //b.setReference("test2");
            
            batchedTransactionService.save(b);
            
        }        
    	
    	
    	return "done";
    	
    }
}
