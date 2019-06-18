package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.RegisterFormBean;
import domain.User;
import net.sf.json.JSONObject;
import service.UserService;

@WebServlet("/CheckRegister")
public class CheckRegister extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private service.UserService UserService = new UserService();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckRegister get");
		// this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setHeader("Content-type", "text/html;charset=GBK");
		response.setCharacterEncoding("utf-8");
		// 获取用户注册时表单提交的参数信息
		System.out.println("post");
		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();
		// string

		/* out.print("dfgdfg"); */
		try {
			String username = request.getParameter("name");
			String password = request.getParameter("password");
			String rePassword = request.getParameter("rePassword");
			String email = request.getParameter("email");
			String isChecked = request.getParameter("isChecked");
			String portrait = request.getParameter("portrait");
			String verification = request.getParameter("verification").toLowerCase();
			HttpSession session = request.getSession();
			System.out.print(session.getId());

			if (!"true".equals(isChecked)) {
				object.put("error", "未同意协议");
				throw new Exception("61");
			}
			if (!session.getAttribute("authCode").toString().toLowerCase().equals(verification)) {
				object.put("error", "邮箱验证码不正确");
				// throw new Exception("65");
			}

			RegisterFormBean formBean = new RegisterFormBean();
			formBean.setName(username);
			formBean.setPassword(password);
			formBean.setPassword2(rePassword);
			formBean.setEmail(email);
			formBean.setChecked(isChecked);
			formBean.setVertification(verification);
			// 验证参数填写是否符合要求，如果不符合，转发到register.jsp重新填写
			object = formBean.validate();
			if (UserService.queryByEmail(request.getParameter("email"))) {
				object.put("error", "该邮箱已注册");
				response.getWriter().print(object);
				return;
			}
			if (object.has("error")) {
				request.setAttribute("formBean", formBean);
				System.out.println("error75行");
				out.print(object);
				System.out.println(object);
				// throw new Exception("传值有误");
				return;
			} // 参数填写符合要求，则将数据封装到User类中

			User userObj = new User();
			userObj.setName(username);
			userObj.setEmail(email);
			userObj.setPassword(password);
			userObj.setEmail(email);
			userObj.setPortrait(portrait);

			request.getSession().setAttribute("userObj", userObj);
			request.getRequestDispatcher("UserServlet?method=add").forward(request, response);
			System.out.println("注册完成");
		} catch (Exception e) {
			object.put("error", "catch传值有误");
			out.print(object);
			System.out.println(object);
			System.out.println("catch传值有误");
		}
	}
}
