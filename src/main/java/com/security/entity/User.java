package com.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.List;

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
	@Column(name = "date_registration")
	private OffsetDateTime dateRegistration;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_group", joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name = "group_id"))
	private List<Group> groups;
	
}
