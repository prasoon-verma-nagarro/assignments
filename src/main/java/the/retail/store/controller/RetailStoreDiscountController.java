package the.retail.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import the.retail.store.model.Bill;
import the.retail.store.model.DiscountResponse;
import the.retail.store.service.RetailStoreDiscountService;

@RequestMapping("/retail-store/discount")
@RestController
public class RetailStoreDiscountController {

	@Autowired
	RetailStoreDiscountService discountService;
	
	@PostMapping("/calculate")
	public DiscountResponse getNetPayableAmount(@RequestBody Bill bill) {
		return discountService.calculateNetPayableAmount(bill);
	}
}
