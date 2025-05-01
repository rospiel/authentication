package com.security.DTO;

import com.security.entity.BaseEntity;
import lombok.Data;

@Data
public class UserRequestDTO extends BaseEntity {

	private String name;
	
	private String email;
	
	private String password;
	
	private String groupName;
	
}
