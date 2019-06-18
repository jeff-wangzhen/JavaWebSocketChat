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
		// ��ȡ�û�ע��ʱ���ύ�Ĳ�����Ϣ
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
				object.put("error", "δͬ��Э��");
				throw new Exception("61");
			}
			if (!session.getAttribute("authCode").toString().toLowerCase().equals(verification)) {
				object.put("error", "������֤�벻��ȷ");
				// throw new Exception("65");
			}

			RegisterFormBean formBean = new RegisterFormBean();
			formBean.setName(username);
			formBean.setPassword(password);
			formBean.setPassword2(rePassword);
			formBean.setEmail(email);
			formBean.setChecked(isChecked);
			formBean.setVertification(verification);
			// ��֤������д�Ƿ����Ҫ����������ϣ�ת����register.jsp������д
			object = formBean.validate();
			if (UserService.queryByEmail(request.getParameter("email"))) {
				object.put("error", "��������ע��");
				response.getWriter().print(object);
				return;
			}
			if (object.has("error")) {
				request.setAttribute("formBean", formBean);
				System.out.println("error75��");
				out.print(object);
				System.out.println(object);
				// throw new Exception("��ֵ����");
				return;
			} // ������д����Ҫ�������ݷ�װ��User����

			User userObj = new User();
			userObj.setName(username);
			userObj.setEmail(email);
			userObj.setPassword(password);
			userObj.setEmail(email);
			userObj.setPortrait(portrait);

			request.getSession().setAttribute("userObj", userObj);
			request.getRequestDispatcher("UserServlet?method=add").forward(request, response);
			System.out.println("ע�����");
		} catch (Exception e) {
			object.put("error", "catch��ֵ����");
			out.print(object);
			System.out.println(object);
			System.out.println("catch��ֵ����");
		}
	}
}
