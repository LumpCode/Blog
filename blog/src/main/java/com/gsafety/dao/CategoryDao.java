package com.gsafety.dao;

import com.gsafety.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/31 0031 00:19
 * @Description 类别
 */
public interface CategoryDao extends JpaRepository<Category,String> {
	/**
	 * 根据类别名查询类别信息
	 * @param name 类别名
	 * @return 类别
	 */
	Category findByName(String name);
}
