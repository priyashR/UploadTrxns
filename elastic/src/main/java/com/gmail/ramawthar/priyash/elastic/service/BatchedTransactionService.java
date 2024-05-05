package com.gmail.ramawthar.priyash.elastic.service;

import java.util.List;

import com.gmail.ramawthar.priyash.elastic.model.BatchedTransaction;

public interface BatchedTransactionService {

	BatchedTransaction save (BatchedTransaction batchedTransaction);

    void delete(BatchedTransaction batchedTransaction);
    
    List<BatchedTransaction> findByTranDate(String tranDate);
    

    List<BatchedTransaction> findByReference(String reference);
}
