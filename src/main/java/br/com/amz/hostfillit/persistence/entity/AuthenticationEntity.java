package br.com.amz.hostfillit.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "authentication", uniqueConstraints = @UniqueConstraint(
		name = "IX_authentication_username_active",
		columnNames = {"username", "active"}))
public class AuthenticationEntity extends AbstractEntity {

	@Column(unique = true, nullable = false)
	private String username;

	private String password;

	private boolean isActive;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity userEntity;

	public AuthenticationEntity() {
		super();
	}

	public AuthenticationEntity(final UUID id,
								final String username,
								final String password,
								final boolean isActive,
								final UserEntity userEntity,
								final Instant createdAt,
								final Instant updatedAt) {
		super(id, createdAt, updatedAt);
		this.username = username;
		this.password = password;
		this.isActive = isActive;
		this.userEntity = userEntity;
	}


	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean isIsActive() {
		return this.isActive;
	}

	public UserEntity getUserEntity() {
		return this.userEntity;
	}
}
