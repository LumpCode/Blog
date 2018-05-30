package com.gsafety.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/30 0030 11:38
 * @Description 文章类别
 */
@Entity
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", columnDefinition = "varchar(64) binary")
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "display_name")
	private String displayName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
