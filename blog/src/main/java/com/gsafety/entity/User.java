package com.gsafety.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/25 0025 21:59
 * @Description User 用户实体
 */
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid")
	@Column(name = "id",columnDefinition = "varchar(64) binary")
	private String id;

	/**
	 * nullable=false是这个字段在保存时必需有值，不能还是null值就调用save去保存入库
	 */
	@Column(name = "username",nullable = false,length = 32)
	private String username;

	@Column(name = "useremail",nullable = false,length = 32)
	private String useremail;

	@Column(name = "password",nullable = false,length = 32)
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
