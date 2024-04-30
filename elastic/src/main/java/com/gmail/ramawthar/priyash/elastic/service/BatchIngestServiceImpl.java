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
import org.springframework.web.multipart.MultipartFile;

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
}
