package com.gsafety.controller;

import com.gsafety.aspect.WebSecurityConfig;
import com.gsafety.entity.Article;
import com.gsafety.entity.Category;
import com.gsafety.entity.User;
import com.gsafety.service.ArticleService;
import com.gsafety.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gsafety.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 后台主页
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String admin(Model model){
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
	 * 登陆检查
	 *
	 * @param response 响应
	 * @param user 用户对象
	 * @param model 模型驱动
	 * @return 判断结果相应跳转的url
	 */
	@RequestMapping(value = "/checklogin", method = RequestMethod.POST)
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

			return "admin/login";
		}
	}

	/**
	 * 删除博客
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id){
		articleService.delete(id);

		return "redirect:/admin";
	}

	/**
	 * 写博客
	 * @param model
	 * @return
	 */
	@RequestMapping("/write")
	public String write(Model model){
		List<Category> categories = categoryService.list();
		model.addAttribute("categories", categories);
		model.addAttribute("article", new Article());

		return "admin/write";
	}

	/**
	 * 设置种类
	 * @param article 文章实体
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Article article){
		//设置种类
		String name = article.getCategory().getName();
		Category category = categoryService.findByName(name);
		article.setCategory(category);
		//设置摘要,取前40个字
		if(article.getContent().length() > 40){
			article.setSummary(article.getContent().substring(0, 40));
		}else {
			article.setSummary(article.getContent().substring(0, article.getContent().length()));
		}
		article.setDate(sdf.format(new Date()));
		articleService.save(article);

		return "redirect:/admin";
	}

	/**
	 * 更新博客
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/update/{id}")
	public String update(@PathVariable("id") String id, Model model){
		Article article = articleService.getById(id);
		model.addAttribute("target", article);
		List<Category> categories = categoryService.list();
		model.addAttribute("categories", categories);
		model.addAttribute("article", new Article());

		return "admin/update";
	}
}
