package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
	USER("ROLE_USER"),
	MANAGER("ROLE_MANAGER"),
	ADMIN("ROLE_ADMIN");



	UserRole(String value) {
		this.value = value;
	}

	private String value;
}
