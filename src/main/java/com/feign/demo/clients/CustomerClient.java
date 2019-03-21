package com.feign.demo.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.feign.demo.clients.fallback.CustomerClientFallback;
import com.feign.demo.domain.Customer;

@FeignClient(name = "CUSTOMERSERVICE", fallback = CustomerClientFallback.class, primary = false)
@RequestMapping(value = "customer")
public interface CustomerClient {

	@RequestMapping(method = RequestMethod.GET, value = "/getAllCustomers")
	List<Customer> getAllCustomers();

	@RequestMapping(method = RequestMethod.PATCH, value = "/{customerId}", consumes = "application/json")
	Customer update(@PathVariable("customerId") long customerId, @RequestBody Customer customer);

	@RequestMapping(method = RequestMethod.GET, value = "/{customerId}")
	Customer getCustomerById(@PathVariable("customerId") long customerId);

	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = "application/json")
	Customer saveCustomer(@RequestBody Customer customer);

}
