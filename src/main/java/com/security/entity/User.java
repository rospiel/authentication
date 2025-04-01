package com.security.entity;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
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
