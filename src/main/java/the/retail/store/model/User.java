package the.retail.store.model;

import java.util.Date;

public class User {

	private String name;
	private String address;
	private UserTypeEnum type;
	private Date userSince;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserTypeEnum getType() {
		return type;
	}
	public void setType(UserTypeEnum type) {
		this.type = type;
	}
	public Date getUserSince() {
		return userSince;
	}
	public void setUserSince(Date userSince) {
		this.userSince = userSince;
	}
}
