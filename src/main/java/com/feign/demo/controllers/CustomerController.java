package com.feign.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feign.demo.clients.CustomerClient;
import com.feign.demo.domain.Customer;

@RestController
public class CustomerController {

	@Autowired
	private CustomerClient customerClient;

	@Autowired
	public CustomerController(@Qualifier("customerClientFallback") CustomerClient customerClient) {
		this.customerClient = customerClient;
	}

	@RequestMapping(path = "/getAllCustomers", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllCustomers() {
		List<Customer> customers = customerClient.getAllCustomers();
		return new ResponseEntity<>(customers, HttpStatus.OK);

	}

	@RequestMapping(path = "/{customerId}", method = RequestMethod.GET)
	public ResponseEntity<Object> get(@PathVariable() long customerId) {
		try {
			Customer c = customerClient.getCustomerById(customerId);
			if (c != null) {
				return new ResponseEntity<>(c, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(path = "/{customerId}", method = RequestMethod.PATCH)
	public ResponseEntity<Object> UpdateCustomer(@PathVariable() Long customerId, @RequestBody Customer customer) {
		Customer c;
		try {
			c = customerClient.update(customerId, customer);
			if (c != null) {
				return new ResponseEntity<>(c, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<Object> saveCustomer(@RequestBody Customer customer) {
		Customer c;
		try {
			c = customerClient.saveCustomer(customer);
			return new ResponseEntity<>(c, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
