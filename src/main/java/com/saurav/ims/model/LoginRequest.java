package com.saurav.ims.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	private String userNameOrEmail;
	private String password;
}
