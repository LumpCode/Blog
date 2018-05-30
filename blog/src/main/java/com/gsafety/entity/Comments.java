package com.gsafety.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/30 0030 23:48
 * @Description 评论实体类
 */
@Entity
@Table(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", columnDefinition = "varchar(64) binary")
	private String id;

	@Column(name = "article_id")
	private Integer articleId;

	@Column(name = "comm_ip")
	private String commIp;

	@Column(name = "comment", columnDefinition = "text")
	private String comment;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getCommIp() {
		return commIp;
	}

	public void setCommIp(String commIp) {
		this.commIp = commIp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
