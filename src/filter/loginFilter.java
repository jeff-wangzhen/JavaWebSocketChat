package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;

import java.io.IOException;

@WebFilter(filterName = "CharsetFilter", urlPatterns = "*.jsp", /* 通配符（*）表示对所有的web资源进行拦截 */
		initParams = { @WebInitParam(name = "charset", value = "utf-8")/* 这里可以放一些初始化的参数 */
		})
public class loginFilter implements Filter {

	public void destroy() {
		/* 销毁时调用 */

//		System.out.println(filterName + "销毁");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws ServletException, IOException {
		/* 过滤方法 主要是对request和response进行一些处理，然后交给下一个过滤器或Servlet处理 */
//		System.out.println(filterName + "过滤中");

		HttpServletRequest request = (HttpServletRequest) req;// 获取request对象
		HttpServletResponse response = (HttpServletResponse) resp;// 获取response对象
		HttpSession session = request.getSession();// 获取session对象
		String path = request.getRequestURI();

		User user = (User) session.getAttribute("userObj");
		if (user != null && user.getLogined()) {// 已经登录
			if (path.contains("/register.html")) {
				response.sendRedirect("index.jsp");
				return;
			}
			if (path.contains("/login.html")) {
				response.sendRedirect("index.jsp");
				return;
			}
			chain.doFilter(req, resp);// 放行，递交给下一个过滤器
			return;
		}
		if (path.contains("/modifyInfo.jsp")) {
			response.sendRedirect("login.html");
			return;
		}
	

		chain.doFilter(req, resp);
		return;
	}

	public void init(FilterConfig config) throws ServletException {

		/* 初始化方法 接收一个FilterConfig类型的参数 该参数是对Filter的一些配置 */

//		filterName = config.getFilterName();
//		charset = config.getInitParameter("charset");

//		System.out.println("过滤器名称：" + filterName);
//		System.out.println("字符集编码：" + charset);
		System.out.println("过滤器");
	}

}
