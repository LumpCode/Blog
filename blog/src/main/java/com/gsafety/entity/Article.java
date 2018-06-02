package com.gsafety.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 文章实体
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/30 0030 11:31
 * @Description 文章实体
 */
@Entity
@Table(name = "article")
public class Article {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", columnDefinition = "varchar(64) binary")
	private String id;

	/**
	 * 文章标题
	 */
	@Column(name = "title")
	private String title;

	/**
	 * 文章内容
	 */
	@Column(name = "content", columnDefinition = "text")
	private String content;

	/**
	 * 文章类别
	 */
	@ManyToOne
	private Category category;

	/**
	 * 文章概要
	 */
	@Column(name = "summary", columnDefinition = "text")
	private String summary;

	/**
	 * 创建时间
	 */
	@Column(name = "date", columnDefinition = "text")
	private String date;

	@Column(name = "userid", columnDefinition = "varchar(64)")
	private Integer userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
