package br.com.amz.hostfillit.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import java.time.Instant;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "IX_user_mail", columnNames = {"mail"}))
public class UserEntity extends AbstractEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String mail;

	public UserEntity() {}

	public UserEntity(UUID id, String name, String mail, Instant createdAt, Instant updatedAt) {
		super(id, createdAt, updatedAt);
		this.mail = mail;
		this.name = name;
	}

	public String getMail() {
		return this.mail;
	}

	public String getName() {
		return this.name;
	}
}
