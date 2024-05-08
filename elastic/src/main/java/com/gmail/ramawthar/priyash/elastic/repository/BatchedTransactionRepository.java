package com.gmail.ramawthar.priyash.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gmail.ramawthar.priyash.elastic.model.BatchedTransaction;

public interface BatchedTransactionRepository extends ElasticsearchRepository<BatchedTransaction, String> {

    List<BatchedTransaction> findByTranDate(String tranDate);
    List<BatchedTransaction> findByReference(String reference);
    List<BatchedTransaction> findByLevel1(String level1);


}
