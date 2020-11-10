package org.nagarro.assignment.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	@NotBlank(message = "name is mandatory")
	private String name;
	private String address;
	@NotBlank(message = "UserType is mandatory")
	private UserTypeEnum type;
	@NotBlank(message = "userSince is mandatory")
	private Date userSince;
}
