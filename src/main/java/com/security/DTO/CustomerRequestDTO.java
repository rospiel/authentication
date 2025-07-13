package com.security.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDTO {

	private String mail;

	private String name;

	private String secret;
	
	private String scope;
	
	private Long duration;
	
}
