package com.gsafety.controller;

import com.google.gson.Gson;
import com.gsafety.aspect.WebSecurityConfig;
import com.gsafety.entity.Article;
import com.gsafety.entity.Category;
import com.gsafety.entity.RequestData;
import com.gsafety.entity.User;
import com.gsafety.service.ArticleService;
import com.gsafety.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gsafety.service.UserService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	@Autowired
	ArticleService articleService;
	@Autowired
	CategoryService categoryService;

	/**
	 * 邮件发送
	 */
	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * 邮件模版引擎
	 */
	@Autowired
	private TemplateEngine templateEngine;

	/**
	 * 从配置文件中获取用户名
	 */
	@Value("${spring.mail.username}")
	private String sender;

	/**
	 * 创建返回值对象
	 */
	private RequestData requestData = new RequestData();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 后台主页
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String admin(Model model) {
		List<Article> articles = articleService.findAll();
		model.addAttribute("articles", articles);

		return "admin/index";
	}

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
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/checklogin", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletResponse response, User user, Model model) {
		User u = userService.findUserByEmail(user.getUseremail());
		if (u != null && u.getPassword().equals(user.getPassword()) && u.getUseremail().equals(user.getUseremail())) {
			if (u.getState() == 0) {
				requestData.setCode("9999");
				requestData.setState("500");
				requestData.setMessage("请前往" + user.getUseremail() + "邮箱激活");
				return new Gson().toJson(requestData);
			}
			Cookie cookie = new Cookie(WebSecurityConfig.SESSION_KEY, user.toString());
			// 将cookies放入response
			response.addCookie(cookie);
			// 将请求参数封装到user属性中
			model.addAttribute("user", user);
			System.out.println(cookie.getName());
			requestData.setMessage("success");
			return new Gson().toJson(requestData);
		}
		System.out.println("failure");
		requestData.setCode("9999");
		requestData.setState("500");
		requestData.setMessage("邮箱或者密码错误");
		return new Gson().toJson(requestData);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public String register(User user) {
		if (userService.findUserByEmail(user.getUseremail()) != null) {
			requestData.setMessage("该邮箱已经注册!");
		} else {
			// 保存User
			userService.saveUser(user);
			System.out.println(user.getId());
			// 发送注册邮件
			sendTemplateMail(user.getUseremail(), user.getId());
			requestData.setMessage("注册成功, 快去激活");
		}
		return new Gson().toJson(requestData);
	}

	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	@ResponseBody
	public String forget(User user) {
		User u = userService.findUserByEmail(user.getUseremail());
		if (u != null || u.getUseremail() == user.getUseremail()) {
			u.setPassword("6666");
			userService.saveUser(u);
			requestData.setMessage("密码已经重置，快去查看你的邮箱");
			sendSimpleEmail(u.getUseremail(), "您好，您密码已重置，初始密码：6666，" +
					"为了你的安全请尽快修改密码。");
			return new Gson().toJson(requestData);
		}
		requestData.setCode("9999");
		requestData.setState("500");
		requestData.setMessage("无效邮箱");
		return new Gson().toJson(requestData);
	}

	@RequestMapping(value = "/activation/{userId}", method = RequestMethod.GET)
	public void activation(@PathVariable String userId, HttpServletResponse response) throws IOException {
		User user = userService.findByUserId(userId);
		System.out.println(user.getId());
		if (user != null) {
			user.setState(1);
			//将用户状态修改为激活
			userService.saveUser(user);
		}
		response.sendRedirect("../login");
	}

	public void sendSimpleEmail(String recipient, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		// 发送者
		message.setFrom(sender);
		// 接收者
		message.setTo(recipient);
		//邮件主题
		message.setSubject("密码重置邮件");
		// 邮件内容
		message.setText(text);
		javaMailSender.send(message);
	}

	public void sendTemplateMail(String recipient, String userId) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(sender);
			helper.setTo(recipient);
			helper.setSubject("验证邮件");
			Context context = new Context();
			context.setVariable("id", userId);
			String emailContent = templateEngine.process("emailTemplate", context);
			helper.setText(emailContent, true);
		} catch (MessagingException e) {
			throw new RuntimeException("Messaging  Exception !", e);
		}
		javaMailSender.send(message);
	}


	/**
	 * 登陆检查
	 *
	 * @param response 响应
	 * @param user 用户对象
	 * @param model 模型驱动
	 * @return 判断结果相应跳转的url
	 */
/*	@RequestMapping(value = "/checklogin", method = RequestMethod.POST)
	public String checklogin(HttpServletResponse response, User user, Model model) {
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

			return "login";
		}
	}*/


	/**
	 * 删除博客
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		articleService.delete(id);

		return "redirect:/admin";
	}

	/**
	 * 写博客
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("/write")
	public String write(Model model) {
		List<Category> categories = categoryService.list();
		model.addAttribute("categories", categories);
		model.addAttribute("article", new Article());

		return "admin/write";
	}

	/**
	 * 设置种类
	 *
	 * @param article 文章实体
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Article article) {
		//设置种类
		String name = article.getCategory().getName();
		Category category = categoryService.findByName(name);
		article.setCategory(category);
		//设置摘要,取前40个字
		if (article.getContent().length() > 40) {
			article.setSummary(article.getContent().substring(0, 40));
		} else {
			article.setSummary(article.getContent().substring(0, article.getContent().length()));
		}
		article.setDate(sdf.format(new Date()));
		articleService.save(article);

		return "redirect:/admin";
	}

	/**
	 * 更新博客
	 *
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/update/{id}")
	public String update(@PathVariable("id") String id, Model model) {
		Article article = articleService.getById(id);
		model.addAttribute("target", article);
		List<Category> categories = categoryService.list();
		model.addAttribute("categories", categories);
		model.addAttribute("article", new Article());

		return "admin/update";
	}
}
