package com.gsafety.controller;

import com.gsafety.entity.Article;
import com.gsafety.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tautua.markdownpapers.Markdown;
import org.tautua.markdownpapers.parser.ParseException;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/31 0031 21:34
 * @Description 文章控制层
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	/**
	 * 根据id返回文章
	 * @param model
	 * @param id 文章id
	 * @return
	 */
	@RequestMapping("/get/{id}")
	public String get(Model model, @PathVariable(name = "id") String id){
		return "index";
	}

	/**
	 * 查询所有
	 * @param model
	 * @return
	 */
	@RequestMapping("")
	public String list(Model model){
		List<Article> articles = articleService.findAll();
		model.addAttribute("articles", articles);

		return "front/index";
	}

	/**
	 * 按类型显示博客
	 * @param dispalyname
	 * @param category
	 * @param model
	 * @return
	 */
	@RequestMapping("/column/{displayname}/{category}")
	public String column(@PathVariable("displayname") String dispalyname, @PathVariable("category") String category, Model model){
		model.addAttribute("articles", articleService.getArticleByCategoryName(category));
		model.addAttribute("displayName", dispalyname);

		return "front/columnPage";
	}

	/**
	 * 显示详细信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable("id") String id, Model model){
		System.out.println(id);
		Article article = articleService.getById(id);
		System.out.println(article.getId());
		Markdown markdown = new Markdown();
		try {
			StringWriter out = new StringWriter();
			markdown.transform(new StringReader(article.getContent()), out);
			out.flush();
			article.setContent(out.toString());
		}catch (ParseException e){
			e.printStackTrace();
		}
		model.addAttribute("article", article);

		return "front/detail";
	}

	/**
	 * 查找文章
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping("/search")
	public String search(String key, Model model){
		List<Article> articles = articleService.search(key);
		model.addAttribute("articles", articles);

		return "front/columnPage";
	}
}
