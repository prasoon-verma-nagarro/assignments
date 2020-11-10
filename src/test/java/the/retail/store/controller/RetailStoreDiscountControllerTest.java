package the.retail.store.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import the.retail.store.model.Bill;
import the.retail.store.model.DiscountResponse;
import the.retail.store.service.RetailStoreDiscountService;

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
		value.setBillId(112);;
		when(service.calculateNetPayableAmount(Mockito.any())).thenReturn(value);
		DiscountResponse response = controller.getNetPayableAmount(new Bill());
		assertNotNull(response);
	}
}
