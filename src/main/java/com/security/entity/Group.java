package com.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Group extends BaseEntity {

	@Column
	private String name;
	
	@ManyToMany
	@JoinTable(name = "group_permission", joinColumns = @JoinColumn(name = "group_id"),
			   inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private Set<Permission> permissions = new HashSet<>();
	
}