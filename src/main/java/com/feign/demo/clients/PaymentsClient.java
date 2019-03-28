package com.feign.demo.clients;

import java.sql.Date;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.feign.demo.domain.Payment;

@FeignClient(name = "PAYMENTSSERVICE")

@RequestMapping(value = "payments")
@Primary
public interface PaymentsClient {

	@RequestMapping(method = RequestMethod.GET, value = "/getAllPayments")
	List<Payment> getAllPayments();

	@RequestMapping(method = RequestMethod.GET, value = "/getPaymentsByDate/{paymentDate}")
	Payment getPaymentsByDate(@PathVariable("paymentDate") Date paymentDate);

	@RequestMapping(method = RequestMethod.GET, value = "/getPaymentsByCustomerId/{customerId}")
	List<Payment> getPaymentsByCustomerId(@PathVariable("customerId") long customerId);

	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = "application/json")
	Payment saveCustomerPayment(@RequestBody Payment payment);

}
