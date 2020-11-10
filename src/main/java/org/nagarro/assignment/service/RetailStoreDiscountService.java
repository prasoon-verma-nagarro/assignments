package org.nagarro.assignment.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.nagarro.assignment.exception.RetailStoreException;
import org.nagarro.assignment.model.Cart;
import org.nagarro.assignment.model.DiscountResponse;
import org.nagarro.assignment.model.Item;
import org.nagarro.assignment.model.ItemTypeEnum;
import org.nagarro.assignment.model.User;
import org.nagarro.assignment.model.UserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RetailStoreDiscountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetailStoreDiscountService.class);

	@Value("${discount.percent.employee}")
	Integer discount_employee;

	@Value("${discount.percent.affiliate}")
	Integer discount_affiliate;

	@Value("${discount.percent.customer}")
	Integer discount_customer;

	@Value("${customer.discount.minimum.years}")
	Integer customer_discount_min_years;

	@Value("${final.discount.per.hundred}")
	Integer final_discount_per_hundred;

	public DiscountResponse calculateNetPayableAmount(Cart cart) {

		// User is mandatory to calculate discount, checked exception
		if (null == cart.getUser()) {
			throw new RetailStoreException("User cannot be null");
		}

		if (null == cart.getUser().getType()) {
			throw new RetailStoreException("User type cannot be null");
		}

		LOGGER.info("Calculating discount for {}: {} with {} items in list", cart.getUser().getType(),
				cart.getUser().getName(), cart.getItems().size());
		Double netPayableTotal = 0.0;
		for (Item i : cart.getItems()) {
			LOGGER.debug(i.toString());
			if (ItemTypeEnum.GROCERIES.equals(i.getType())) {
				LOGGER.debug("NO DISCOUNT FOR THIS ITEM");
				netPayableTotal = netPayableTotal + (i.getPrice() * i.getQuantity());
			} else {
				LOGGER.debug("{} DISCOUNT FOR THIS ITEM", cart.getUser().getType());
				netPayableTotal = netPayableTotal + getDiscountedPriceForItem(i, cart.getUser());
			}
			LOGGER.info("Updated netPayableTotal : {}", netPayableTotal);
		}

		// populate response
		DiscountResponse discountResponse = new DiscountResponse();
		discountResponse.setBillId(cart.getBillId());
		discountResponse.setNetPayableAmount(netPaybleAfterFinalDiscount(netPayableTotal));
		return discountResponse;
	}

	public Double discountBasedOnUserType(Double discountableTotal, User user) throws Exception {

		LocalDate currentDate = LocalDate.now();
		Date permanentCustomerDate = Date.from(
				currentDate.minusYears(customer_discount_min_years).atStartOfDay(ZoneId.systemDefault()).toInstant());
		LOGGER.debug("User since date: " + user.getUserSince());
		
		if (UserTypeEnum.CUSTOMER.equals(user.getType()) && user.getUserSince().before(permanentCustomerDate)) {
			return (discount_customer * discountableTotal) / 100;
		} else if (UserTypeEnum.EMPLOYEE.equals(user.getType())) {
			return (discount_employee * discountableTotal) / 100;
		} else if (UserTypeEnum.AFFILIATE.equals(user.getType())) {
			return (discount_affiliate * discountableTotal) / 100;
		} else {
			return Double.valueOf(0);
		}
	}

	private Double getDiscountedPriceForItem(Item item, User user) {
		try {
			Double withoutDiscountTotal = item.getPrice() * item.getQuantity();
			Double discountedTotal = withoutDiscountTotal - discountBasedOnUserType(withoutDiscountTotal, user);
			return discountedTotal;
		} catch (Exception e) {
			// Calculation exception, due to missing inputs - UserSince customer, should lead to checked exception
			// caught to differentiate with service exception
			LOGGER.error("Error while calculating discount, 'userSince' may be missing");
			throw new RetailStoreException("Error while calculating discount, 'userSince' may be missing", e);
		}
	}

	private Double netPaybleAfterFinalDiscount(Double payable) {
		LOGGER.info("Final discount ($5 on every $100) on : {}", payable);
		int div = (int) (payable / 100);
		LOGGER.debug("DIV: {}", div);
		return payable - (final_discount_per_hundred * div);
	}
}
