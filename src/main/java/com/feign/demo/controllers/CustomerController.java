package com.feign.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.feign.demo.clients.CustomerClient;
import com.feign.demo.clients.PaymentsClient;
import com.feign.demo.domain.Customer;
import com.feign.demo.domain.Payment;

@RestController
public class CustomerController {

	@Autowired
	private CustomerClient customerClient;

	@Autowired
	private PaymentsClient paymentsClient;

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

	@RequestMapping(path = "/registerPayment", method = RequestMethod.POST)
	public ResponseEntity<Object> saveCustomer(@RequestBody Payment payment) {
		Payment p = null;
		Customer c = null;
		try {
			c = customerClient.getCustomerById(payment.getCustomerId());
			if (null == c) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Customer Does not Exist");
			} else {
				p = paymentsClient.saveCustomerPayment(payment);
				return new ResponseEntity<>(p, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (null == c) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Customer Does not Exist");
			} else {

				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
	}
}
