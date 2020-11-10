package the.retail.store.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import the.retail.store.exception.RetailStoreException;
import the.retail.store.model.Bill;
import the.retail.store.model.DiscountResponse;
import the.retail.store.model.Item;
import the.retail.store.model.ItemTypeEnum;
import the.retail.store.model.User;
import the.retail.store.model.UserTypeEnum;

@Service
public class RetailStoreDiscountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetailStoreDiscountService.class);
	
	public DiscountResponse calculateNetPayableAmount(Bill bill) {
		
		// User is mandatory to calculate discount
		if(bill.getUser() == null) {
			throw new RetailStoreException("User cannot be null");
		}
		
		if(bill.getUser().getType() == null) {
			throw new RetailStoreException("User type cannot be null");
		}
		
		LOGGER.info("Calculating discount for {}: {} with {} items in list", bill.getUser().getType(), bill.getUser().getName(), bill.getItems().size());
		Double netPayableTotal = 0.0;
		for(Item i : bill.getItems()) {
			LOGGER.info(i.toString());
			if(i.getType().equals(ItemTypeEnum.Groceries)) {
				LOGGER.info("NO DISCOUNT FOR THIS ITEM");
				netPayableTotal = netPayableTotal + (i.getPrice() * i.getQuantity());
			} else {
				LOGGER.info("{} DISCOUNT FOR THIS ITEM", bill.getUser().getType());
				netPayableTotal = netPayableTotal + getDiscountedPriceForItem(i, bill.getUser());
			}
			LOGGER.info("Updated netPayableTotal : {}", netPayableTotal);
		}
		
		// populate response
		DiscountResponse discountResponse = new DiscountResponse();
		discountResponse.setBillId(bill.getBillId());
		discountResponse.setNetPayableAmount(netPaybleAfterFinalDiscount(netPayableTotal));
		return discountResponse;
	}
	
	public Double discountBasedOnUserType(Double discountableTotal, User user) throws Exception {
		
		LocalDate currentDate = LocalDate.now();
	    Date d1 = Date.from(currentDate.minusYears(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
		LOGGER.info("2 years before date: " + d1);
		LOGGER.info("User since date: " + user.getUserSince());
		if(user.getType().equals(UserTypeEnum.EMPLOYEE)) {
			return ( 30*discountableTotal)/100;
		} else if(user.getType().equals(UserTypeEnum.AFFILIATE)) {
			return ( 10*discountableTotal)/100;
		} else if(user.getType().equals(UserTypeEnum.CUSTOMER) && user.getUserSince().before(d1)){
			return ( 5*discountableTotal)/100;
		} else {
			return Double.valueOf(0);
		}
	}
	
	private Double getDiscountedPriceForItem(Item item, User user) {
		try {
			Double withoutDiscountTotal = item.getPrice()*item.getQuantity();
			Double discountedTotal = withoutDiscountTotal - discountBasedOnUserType(withoutDiscountTotal, user);
			return discountedTotal;
		} catch(Exception e) {
			throw new RetailStoreException("Error while calculating discount, 'userSince' may be missing", e);
		}
	}
	
	private Double netPaybleAfterFinalDiscount(Double payable) {
		LOGGER.info("Final discount ($5 on every $100) on : {}", payable);
		int div = (int) (payable/100);
		LOGGER.info("DIV: {}", div);
		return payable - (5*div);
	}
}
