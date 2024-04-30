package com.gmail.ramawthar.priyash.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.gmail.ramawthar.priyash.elastic.model.Employee;
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {
}
