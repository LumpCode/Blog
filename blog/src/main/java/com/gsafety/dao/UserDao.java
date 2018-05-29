package com.gsafety.dao;

import com.gsafety.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/25 0025 22:26
 * @Description 用户操作持久层
 */
@Repository
public interface UserDao extends JpaRepository<User,String> {

	/**
	 * 根据用户名密码查找用户
	 * @param username 用户名
	 * @param password 密码
	 * @return	匹配到的user对象
	 */
	@Query("select u from User u where u.username = :username and password = :password")
	User findByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}
