package com.gsafety.controller;

import com.gsafety.aspect.WebSecurityConfig;
import com.gsafety.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gsafety.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/25 0025 22:43
 * @Description 控制层
 */
@Controller
@RequestMapping("/admin")
public class UserController {

	@Autowired
	UserService userService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



	/**
	 * 登陆模块
	 *
	 * @return url
	 */
	@RequestMapping("/login")
	public String login() {
		return "admin/login";
	}

	/**
	 * 登陆检查
	 *
	 * @param response 响应
	 * @param user 用户对象
	 * @param model 模型驱动
	 * @return 判断结果相应跳转的url
	 */
	@RequestMapping(value = "checkLogin", method = RequestMethod.POST)
	public String checkLogin(HttpServletResponse response, User user, Model model) {
		if (userService.login(user.getUsername(), user.getPassword())) {
			Cookie cookie = new Cookie(WebSecurityConfig.SESSION_KEY, user.toString());
			// 将cookies放入response
			response.addCookie(cookie);
			// 将请求参数封装到user属性中
			model.addAttribute("user", user);
			System.out.println(cookie.getName());

			return "redirect:/admin";
		} else {
			model.addAttribute("error", "用户名或密码错误");
			System.out.println("failure");

			return "admin/login";
		}
	}
}
