package com.gmail.ramawthar.priyash.elastic.model;

import java.math.BigDecimal;
import java.util.StringTokenizer;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "batched-trxns")
public class BatchedTransaction {

	@Id
	private String id;
	private String tranDate;
	private String reference;
	private String account;
	private String categoryTree;   
	private BigDecimal amount; 
	private String user;
	private String level0;
	private String level1;
	private String level2;
	private String level3;
	private String level4;
	private String level5;
	private String level6;
	private String level7;
	private String level8;
	private String level9;
	
	public BatchedTransaction(String id, String tranDate, String reference, String account, String categoryTree,
			BigDecimal amount, String batch) {
		super();
		this.id = id;
		this.tranDate = tranDate;
		this.reference = reference;
		this.account = account;
		this.categoryTree = categoryTree;
		setLevels();
		this.amount = amount;
		this.user = batch;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
		setLevels();
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getBatch() {
		return user;
	}


	public void setBatch(String bacth) {
		this.user = bacth;
	}


	public String getLevel0() {
		return level0;
	}


	public void setLevel0(String level0) {
		this.level0 = level0;
	}


	public String getLevel1() {
		return level1;
	}


	public void setLevel1(String level1) {
		this.level1 = level1;
	}


	public String getLevel2() {
		return level2;
	}


	public void setLevel2(String level2) {
		this.level2 = level2;
	}


	public String getLevel3() {
		return level3;
	}


	public void setLevel3(String level3) {
		this.level3 = level3;
	}


	public String getLevel4() {
		return level4;
	}


	public void setLevel4(String level4) {
		this.level4 = level4;
	}


	public String getLevel5() {
		return level5;
	}


	public void setLevel5(String level5) {
		this.level5 = level5;
	}


	public String getLevel6() {
		return level6;
	}


	public void setLevel6(String level6) {
		this.level6 = level6;
	}


	public String getLevel7() {
		return level7;
	}


	public void setLevel7(String level7) {
		this.level7 = level7;
	}


	public String getLevel8() {
		return level8;
	}


	public void setLevel8(String level8) {
		this.level8 = level8;
	}


	public String getLevel9() {
		return level9;
	}


	public void setLevel9(String level9) {
		this.level9 = level9;
	}
	
	private void setLevels(){
  		StringTokenizer st = new StringTokenizer(this.categoryTree,"/");  
		int count = 0;
		String maxMember = "";
    	while (st.hasMoreTokens()) {

    		if (count==0){setLevel0(st.nextToken());
			   maxMember = getLevel0();}
    		else if (count==1){setLevel1(st.nextToken());
    						   maxMember = getLevel1();}
    		else if (count==2){setLevel2(st.nextToken());
			   maxMember = getLevel2();}
    		else if (count==3){setLevel3(st.nextToken());
			   maxMember = getLevel3();}
    		else if (count==4){setLevel4(st.nextToken());
			   maxMember = getLevel4();}
    		else if (count==5){setLevel5(st.nextToken());
			   maxMember = getLevel5();}
    		else if (count==6){setLevel6(st.nextToken());
			   maxMember = getLevel6();}
    		else if (count==7){setLevel7(st.nextToken());
			   maxMember = getLevel7();}
    		else if (count==8){setLevel8(st.nextToken());
			   maxMember = getLevel8();}
    		else if (count==9){setLevel9(st.nextToken());
			   maxMember = getLevel9();}
    		count++;
        }
    	
    	if (count<10){
    		
    		while (count<10){
	    		if (count==0){setLevel0(maxMember);}
	    		else if (count==1){setLevel1(maxMember);}
	    		else if (count==2){setLevel2(maxMember);}
	    		else if (count==3){setLevel3(maxMember);}
	    		else if (count==4){setLevel4(maxMember);}
	    		else if (count==5){setLevel5(maxMember);}
	    		else if (count==6){setLevel6(maxMember);}
	    		else if (count==7){setLevel7(maxMember);}
	    		else if (count==8){setLevel8(maxMember);}
	    		else if (count==9){setLevel9(maxMember);}
	    		count++;
    		}
    		
    	}
		
	}
	
	
}
