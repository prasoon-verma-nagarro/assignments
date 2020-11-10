package org.nagarro.assignment.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Cart {

	@NotBlank(message = "billId is mandatory")
	private Integer billId;
	@NotBlank(message = "User is mandatory")
	private User user;
	private List<Item> items;
}
