package com.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@CreationTimestamp
	@Column
	private OffsetDateTime dateRegistration;
	
	@ManyToMany
	@JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name = "group_id"))
	private Set<Group> groups = new HashSet<>();
	
}
