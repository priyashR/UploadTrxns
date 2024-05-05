package com.gmail.ramawthar.priyash.elastic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gmail.ramawthar.priyash.elastic.service.BatchIngestService;


@RestController
public class FileIngestController {
	@Autowired
	BatchIngestService budgetIngestService;
	
 	  @RequestMapping(value = "/processCSVFile", method = RequestMethod.POST)
	  	public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file,
				RedirectAttributes redirectAttributes) {
		  System.out.println(budgetIngestService.processCSVFile(file));
		  redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename() + "!");
			//logger.info("Controller call to fetchPath");
	    return new ResponseEntity<>(HttpStatus.OK);
 	  }
 	  
	  @RequestMapping(value = "/reprocessCategories", method = RequestMethod.POST)
	  public ResponseEntity<Object> createCategory() {
		  budgetIngestService.reprocessCategories();
		  System.out.println("process");
	    return new ResponseEntity<>(HttpStatus.OK);
	  }	  
 	  
}
