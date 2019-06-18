package web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

@WebServlet("/SessionDestroy")
public class SessionDestroy extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SessionDestroy get");
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setHeader("Content-type", "text/html;charset=GBK");
		response.setCharacterEncoding("utf-8");
		// 获取用户注册时表单提交的参数信息
		System.out.println("post");
		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();

		try {

			request.getSession().invalidate();
			object.put("success", "注销成功");
			response.sendRedirect(response.encodeRedirectURL("index.jsp"));

			System.out.println("注销完成");
		} catch (Exception e) {
			if(!object.has("error")) {
				object.put("error", "catch传值有误");
			}
			out.print(object);
			System.out.println(object);
			System.out.println("catch传值有误");
		}
	}
}
