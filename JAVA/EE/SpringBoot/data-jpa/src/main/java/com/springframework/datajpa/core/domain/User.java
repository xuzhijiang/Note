package com.springframework.datajpa.core.domain;

import javax.persistence.*;

/**
 * 通过ORM框架,User会被映射到数据库表中
 *
 * 由于配置了hibernate.hbm2ddl.auto，在应用启动的时候框架会自动去数据库中创建对应的表。
 */
@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean gender;

	@Column(nullable = false, updatable = false)
	private long createdAt;

	@Column(nullable = false)
	private long updatedAt;

	@Version
	@Column(nullable = false)
	private long version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@PrePersist
	public void prePersist() {
		long t = System.currentTimeMillis();
		setCreatedAt(t);
		setUpdatedAt(t);
	}

	@PreUpdate
	public void preUpdate() {
		long t = System.currentTimeMillis();
		setUpdatedAt(t);
	}

}
