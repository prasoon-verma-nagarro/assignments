package the.retail.store.model;

public class DiscountResponse {

	private Double netPayableAmount;
	private Integer billId;
	
	public Double getNetPayableAmount() {
		return netPayableAmount;
	}
	public void setNetPayableAmount(Double netPayableAmount) {
		this.netPayableAmount = netPayableAmount;
	}
	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
}
