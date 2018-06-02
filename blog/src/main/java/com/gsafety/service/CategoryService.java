package com.gsafety.service;

import com.gsafety.dao.CategoryDao;
import com.gsafety.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/31 0031 00:22
 * @Description 类别
 */
@Service
public class CategoryService {
	@Autowired
	CategoryDao categoryDao;

	/**
	 * 查询所有的类型
	 *
	 * @return
	 */
	public List<Category> list() {
		List<Category> categories = categoryDao.findAll();

		return categories;
	}

	/**
	 * 根据ID获取一个类型
	 *
	 * @param id
	 * @return
	 */
	public Category get(String id) {

		return categoryDao.findOne(id);
	}

	/**
	 * 根据类型名称获取一个类型
	 *
	 * @param name
	 * @return
	 */
	public Category findByName(String name) {

		return categoryDao.findByName(name);
	}
}
