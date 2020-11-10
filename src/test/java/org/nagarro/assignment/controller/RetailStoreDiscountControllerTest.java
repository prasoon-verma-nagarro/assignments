package org.nagarro.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.nagarro.assignment.model.Cart;
import org.nagarro.assignment.model.DiscountResponse;
import org.nagarro.assignment.service.RetailStoreDiscountService;

public class RetailStoreDiscountControllerTest {

	@InjectMocks
	RetailStoreDiscountController controller;

	@Mock
	RetailStoreDiscountService service;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getNetPayableAmountTest() {
		DiscountResponse value = new DiscountResponse();
		value.setBillId(112);
		;
		when(service.calculateNetPayableAmount(Mockito.any())).thenReturn(value);
		DiscountResponse response = controller.getNetPayableAmount(new Cart());
		assertNotNull(response);
	}
}
