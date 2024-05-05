package com.gmail.ramawthar.priyash.elastic.service;

import org.springframework.web.multipart.MultipartFile;

public interface BatchIngestService {
    String processCSVFile(MultipartFile file);
    String reprocessCategories();
}
