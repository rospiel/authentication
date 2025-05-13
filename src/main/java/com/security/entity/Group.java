package com.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "group_permission", joinColumns = @JoinColumn(name = "group_id"),
			   inverseJoinColumns = @JoinColumn(name = "permission_id"))
	private List<Permission> permissions;
	
}