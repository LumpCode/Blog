package com.gsafety.service;

import com.gsafety.dao.ArticleDao;
import com.gsafety.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/31 0031 00:02
 * @Description 文章服务层操作
 */
@Service
public class ArticleService {
	/**
	 * 自动装配articleDao
	 */
	@Autowired
	ArticleDao articleDao;

	/**
	 * 格式化当前时间
	 */
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


	/**
	 * 根据id查询文章
	 *
	 * @param id 文章id
	 * @return 返回文章
	 */
	public Article getById(String id) {
		Article article = articleDao.findOne(id);
		return article;
	}

	/**
	 * 查询所有文章
	 *
	 * @return 返回文章集合
	 */
	public List<Article> findAll() {
		List<Article> articles = articleDao.findAll();
		return articles;
	}

	/**
	 * 根据类别名查找文章
	 *
	 * @param CategoryName 类别名
	 * @return 返回符合条件的分组集合
	 */
	public List<Article> getArticleByCategoryName(String CategoryName) {
		List<Article> articles = articleDao.findAllByCategoryName(CategoryName);
		return articles;
	}

	/**
	 * 删除文章
	 * @param id 文章id
	 */
	public void delete(String id) {
		articleDao.delete(id);
	}

	/**
	 * 保存博客
	 * @param article 文章
	 */
	public void save(Article article) {
		articleDao.save(article);
	}

	/**
	 * 根据标题查找
	 * @param key 关键字
	 * @return 符合要求的文章集合
	 */
	public List<Article> search(String key) {

		return articleDao.findByTitleLike(key);
	}
}
