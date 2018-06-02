package com.gsafety.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 文章实体
 *
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
	 * 文章类别 多对一
	 * CascadeType.MERGE指A类新增或者变化，会级联B对象（新增或者变化）
	 * optional = true 外键为空时仍可以向表中添加数据
	 */
	@ManyToOne(cascade = CascadeType.MERGE, optional = true)
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

	/**
	 * 用户 多对一
	 */
	@ManyToOne(cascade = CascadeType.MERGE, optional = true)
	private User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
