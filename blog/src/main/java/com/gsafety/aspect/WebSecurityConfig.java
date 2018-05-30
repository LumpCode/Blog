package com.gsafety.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liugan83@gmail.com
 * @version V1.0
 * @date 2018/5/28 0028 09:01
 * @Description 使用cookies完成登陆验证
 */
@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

	/**
	 * 设置常量Session的key为user
	 */
	public final static String SESSION_KEY = "user";

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}


	/**
	 * 重写添加拦截器方法,自定义拦截
	 *
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

		// 排除配置
		addInterceptor.excludePathPatterns("blog/article/**");

		// 拦截配置
		addInterceptor.addPathPatterns("/**");
	}

	/**
	 * SecurityInterceptor继承HandlerInterceptorAdapter
	 * 重写preHandle方法，表明在方法执行前执行拦截的动作
	 * 我们在这里对cookie的内容进行判断，如果有登录成功的标志，就进入后台管理界面，否则跳转到登录界面
	 */
	private class SecurityInterceptor extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
			System.out.println("执行拦截");
			// request.getCookies()返回的是所有Cookie对象
			Cookie[] cookies = request.getCookies();
			// 获取能够与“url-pattern”中匹配的路径，注意是完全匹配的部分，*的部分不包括
			String path = request.getServletPath();
			String KEY_article = "article";
			String KEY_login = "login";
			// 这里要使用获取的路径去查找对应的字符,否则会报空指针异常
			if (path.contains(KEY_article) || path.contains(KEY_login)) {
				return true;
			}

			for (Cookie cookie : cookies) {
				System.out.println(cookie.getName() + "拦截器中的cookies");
				if (cookie.getName().equals(SESSION_KEY)) {
					return true;
				}
			}

			response.sendRedirect("/blog/admin/login");

			return false;
		}

	}
}
