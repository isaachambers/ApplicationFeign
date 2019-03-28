package com.feign.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feign.demo.clients.CustomerClient;
import com.feign.demo.clients.PaymentsClient;
import com.feign.demo.controllers.CustomerController;
import com.feign.demo.domain.Customer;
import com.feign.demo.domain.Payment;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CustomerControllerTest {

	@Mock
	private CustomerClient customerClient;

	@Mock
	private PaymentsClient paymentsClient;

	@InjectMocks
	private CustomerController customerController;

	private MockMvc mockMvc;

	/**
	 * Setting Up Mock Data for the Various Tests
	 */
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		Customer c = new Customer(new Long(12), "Bintu", "Anna", "bintu@ana.com");
		Customer c2 = new Customer(new Long(13), "Masiga", "Kitooke", "masiga@kito.com");
		List<Customer> customers = new ArrayList<>();
		customers.add(c);
		customers.add(c2);
		Mockito.when(customerClient.getAllCustomers()).thenReturn(customers);
		Mockito.when(customerClient.getCustomerById(c.getCustomerId())).thenReturn(c);

	}

	@Test
	public void shouldGetAllCustomers() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/getAllCustomers")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(2)));
	}

	@Test
	public void shouldGetCustomerById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/12")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.firstName", Matchers.is("Bintu")));
	}

	@Test
	public void shouldReturn404WhenCustomerIsNotPresent() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/23")).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void shouldAcceptPaymentsWhenCustomerExists() throws Exception {
		Payment p = new Payment(new Long(12), new Long(12), 12000, "Mpesa", new Date());

		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(
				post("/registerPayment").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(p)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shouldFailAcceptPaymentsWhenCustomerExists() throws Exception {
		Payment p = new Payment(new Long(1), new Long(234), 12000, "Mpesa", new Date());
		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(
				post("/registerPayment").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(p)))
				.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
	}
}
