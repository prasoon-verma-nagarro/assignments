package org.nagarro.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.nagarro.assignment.exception.RetailStoreException;
import org.nagarro.assignment.model.Cart;
import org.nagarro.assignment.model.DiscountResponse;
import org.nagarro.assignment.model.Item;
import org.nagarro.assignment.model.ItemTypeEnum;
import org.nagarro.assignment.model.User;
import org.nagarro.assignment.model.UserTypeEnum;
import org.springframework.test.util.ReflectionTestUtils;

public class RetailStoreDiscountServiceTest {

	@InjectMocks
	private RetailStoreDiscountService retailStoreDiscountService;

	@BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(retailStoreDiscountService, "discount_employee", 30);
        ReflectionTestUtils.setField(retailStoreDiscountService, "discount_affiliate", 10);
        ReflectionTestUtils.setField(retailStoreDiscountService, "discount_customer", 5);
        ReflectionTestUtils.setField(retailStoreDiscountService, "customer_discount_min_years", 2);
        ReflectionTestUtils.setField(retailStoreDiscountService, "final_discount_per_hundred", 5);
    }
	
	@Test
	public void calculateNetPayableAmountExceptionTest() {
		Cart bill = new Cart();
		try {
			retailStoreDiscountService.calculateNetPayableAmount(bill);
		} catch (RetailStoreException e) {
			assertEquals("User cannot be null", e.getMessage());
		}
	}

	@Test
	public void calculateNetPayableAmountExceptionTest1() {
		Cart bill = new Cart();
		User user = new User();
		user.setName("Dummy User");
		bill.setUser(user);
		try {
			retailStoreDiscountService.calculateNetPayableAmount(bill);
		} catch (RetailStoreException e) {
			assertEquals("User type cannot be null", e.getMessage());
		}
	}

	@Test
	public void calculateNetPayableAmountTest() {
		User user = new User();
		user.setName("Dummy User");
		user.setAddress("Dummy Address");
		user.setType(UserTypeEnum.CUSTOMER);
		user.setUserSince(new Date());
		Cart bill = new Cart();
		bill.setBillId(1);
		Item item = new Item();
		item.setName("Dummy Item");
		item.setPrice(33.55);
		item.setQuantity(1);
		item.setType(ItemTypeEnum.GROCERIES);
		Item item1 = new Item();
		item1.setName("Dummy Item1");
		item1.setPrice(33.55);
		item1.setQuantity(1);
		item1.setType(ItemTypeEnum.FURNITURE);
		List<Item> items = new ArrayList<>();
		items.add(item);
		items.add(item1);
		bill.setItems(items);
		bill.setUser(user);
		DiscountResponse response = retailStoreDiscountService.calculateNetPayableAmount(bill);
		assertNotNull(response);
		assertEquals(1, response.getBillId());
		assertNotNull(response.getNetPayableAmount());
		assertEquals("Dummy Address", user.getAddress());
		assertEquals(UserTypeEnum.CUSTOMER, user.getType());
		assertEquals("Dummy User", user.getName());
		assertEquals(1, bill.getBillId());
		assertNotNull(bill.getItems());
		assertNotNull(bill.getUser());
	}

	@Test
	public void discountBasedOnUserTypeTest() throws Exception {
		User user = new User();
		user.setName("Dummy User");
		user.setAddress("Dummy Address");
		user.setType(UserTypeEnum.CUSTOMER);
		user.setUserSince(new Date());
		Double response = retailStoreDiscountService.discountBasedOnUserType(33.43, user);
		assertNotNull(response);

		user.setType(UserTypeEnum.EMPLOYEE);
		response = retailStoreDiscountService.discountBasedOnUserType(33.43, user);
		assertNotNull(response);

		user.setType(UserTypeEnum.AFFILIATE);
		response = retailStoreDiscountService.discountBasedOnUserType(33.43, user);
		assertNotNull(response);

		user.setType(UserTypeEnum.NEW);
		response = retailStoreDiscountService.discountBasedOnUserType(33.43, user);
		assertNotNull(response);
	}
}
