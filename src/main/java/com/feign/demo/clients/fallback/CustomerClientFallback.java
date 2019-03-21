package com.feign.demo.clients.fallback;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.feign.demo.clients.CustomerClient;
import com.feign.demo.domain.Customer;

@Component
public class CustomerClientFallback implements CustomerClient {

	@Override
	public List<Customer> getAllCustomers() {

		return new ArrayList<Customer>();
	}

	@Override
	public Customer update(long customerId, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerById(long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

}
