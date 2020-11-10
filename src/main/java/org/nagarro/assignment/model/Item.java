package org.nagarro.assignment.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Item {
	private String name;
	@NotBlank(message = "price is mandatory")
	private Double price;
	@NotBlank(message = "quantity is mandatory")
	private Integer quantity;
	@NotBlank(message = "ItemType is mandatory")
	private ItemTypeEnum type;
}
