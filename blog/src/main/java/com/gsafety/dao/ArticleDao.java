package com.gsafety.dao;

import com.gsafety.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/25 0025 22:26
 * @Description 文章操作持久层
 */
@Repository
public interface ArticleDao extends JpaRepository<Article,String>{
	/**
	 * 根据类别名查找所有文章
	 * @param name 类别名
	 * @return 查找到所有文章的集合
	 */
	public List<Article> findAllByCategoryName(String name);

	/**
	 * 根据文章标题名进行模糊查询
	 * @param title 文章标题
	 * @return 符合要求的文章集合
	 */
	@Query("select a from Article as a where title like %:title%")
	public List<Article> findByTitleLike(@Param("title") String title);

}
