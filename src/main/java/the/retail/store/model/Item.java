package the.retail.store.model;

public class Item {

	private String name;
	private Double price;
	private Integer quantity;
	private ItemTypeEnum type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public ItemTypeEnum getType() {
		return type;
	}
	public void setType(ItemTypeEnum type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + ", quantity=" + quantity + ", type=" + type + "]";
	}
}
