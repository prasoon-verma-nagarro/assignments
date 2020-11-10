package org.nagarro.assignment.controller;

import org.nagarro.assignment.model.Cart;
import org.nagarro.assignment.model.DiscountResponse;
import org.nagarro.assignment.service.RetailStoreDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/discount")
@RestController
public class RetailStoreDiscountController {

	@Autowired
	RetailStoreDiscountService discountService;

	@PostMapping("/calculate")
	public DiscountResponse getNetPayableAmount(@RequestBody Cart cart) {
		return discountService.calculateNetPayableAmount(cart);
	}
}
