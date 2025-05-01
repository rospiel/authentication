package com.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Permission extends BaseEntity {

	@Column
	private String name;

	@Column
	private String description;
	
}