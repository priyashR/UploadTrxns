package com.gmail.ramawthar.priyash.elastic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gmail.ramawthar.priyash.elastic.model.BatchedTransaction;
import com.gmail.ramawthar.priyash.elastic.repository.BatchedTransactionRepository;

@Service
@Component("BatchTrxnSrvc")
public class BatchedTransactionServiceImpl implements BatchedTransactionService{
	
	private BatchedTransactionRepository batchedtransactionRepository;
	
	@Autowired
	public void setBatchedTransactionRepository(BatchedTransactionRepository batchedtransactionRepository) {
		this.batchedtransactionRepository = batchedtransactionRepository;
	}

	@Override
	public BatchedTransaction save(BatchedTransaction batchedtransaction) {
		return batchedtransactionRepository.save(batchedtransaction);
	}

	@Override
	public void delete(BatchedTransaction batchedtransaction) {
		batchedtransactionRepository.delete(batchedtransaction);

	}

	@Override
	public List<BatchedTransaction> findByTranDate(String tranDate) {
		// TODO Auto-generated method stub
		return batchedtransactionRepository.findByTranDate(tranDate);
	}

}
