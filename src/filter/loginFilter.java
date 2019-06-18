package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;

import java.io.IOException;

@WebFilter(filterName = "CharsetFilter", urlPatterns = "*.jsp", /* ͨ�����*����ʾ�����е�web��Դ�������� */
		initParams = { @WebInitParam(name = "charset", value = "utf-8")/* ������Է�һЩ��ʼ���Ĳ��� */
		})
public class loginFilter implements Filter {

	public void destroy() {
		/* ����ʱ���� */

//		System.out.println(filterName + "����");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws ServletException, IOException {
		/* ���˷��� ��Ҫ�Ƕ�request��response����һЩ����Ȼ�󽻸���һ����������Servlet���� */
//		System.out.println(filterName + "������");

		HttpServletRequest request = (HttpServletRequest) req;// ��ȡrequest����
		HttpServletResponse response = (HttpServletResponse) resp;// ��ȡresponse����
		HttpSession session = request.getSession();// ��ȡsession����
		String path = request.getRequestURI();

		User user = (User) session.getAttribute("userObj");
		if (user != null && user.getLogined()) {// �Ѿ���¼
			if (path.contains("/register.html")) {
				response.sendRedirect("index.jsp");
				return;
			}
			if (path.contains("/login.html")) {
				response.sendRedirect("index.jsp");
				return;
			}
			chain.doFilter(req, resp);// ���У��ݽ�����һ��������
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

		/* ��ʼ������ ����һ��FilterConfig���͵Ĳ��� �ò����Ƕ�Filter��һЩ���� */

//		filterName = config.getFilterName();
//		charset = config.getInitParameter("charset");

//		System.out.println("���������ƣ�" + filterName);
//		System.out.println("�ַ������룺" + charset);
		System.out.println("������");
	}

}
