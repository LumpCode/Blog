package service;

import dao.UserDao;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/25 0025 22:38
 * @Description 用户查找service层
 */
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
		if (user == null){
			return false;
		}else {
			return true;
		}
	}
}
