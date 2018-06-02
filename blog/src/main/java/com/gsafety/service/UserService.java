package com.gsafety.service;

import com.gsafety.dao.UserDao;
import com.gsafety.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/25 0025 22:38
 * @Description 用户查找service层
 */
@Service
public class UserService {

	@Autowired
	UserDao userDao;

	/**
	 * 根据传入的用户名密码判断用户是否存在
	 * @param username 用户名
	 * @param password 密码
	 * @return false:不存在该用户 true:存在
	 */
	public boolean login(String username,String password){
		User user = userDao.findByUsernameAndPassword(username,password);
		if (null == user){
			return false;
		}else {
			return true;
		}
	}

	/**
	 * 根据用户姓名查找用户
	 *
	 * @param name
	 * @return User对象
	 */
	public User findUserByName(String name) {
		User user = null;
		try {
			user = userDao.findByUserName(name);
		} catch (Exception e) {
		}
		return user;
	}

	/**
	 * 根据用户邮箱查询用户
	 *
	 * @param email
	 * @return User对象
	 */
	public User findUserByEmail(String email) {
		User user = null;
		try {
			user = userDao.findByUserEmail(email);
		} catch (Exception e) {
		}
		return user;
	}

	/**
	 * 根据用户id查询用户
	 * @param userId 用户id
	 * @return User对象
	 */
	public User	findByUserId(String userId){
		User user = null;
		try {
			user = userDao.findByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 保存用户
	 *
	 * @param user
	 */
	public void saveUser(User user) {
		userDao.save(user);
	}

}
