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
		// ��ȡ�û�ע��ʱ���ύ�Ĳ�����Ϣ
		System.out.println("post");
		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();

		try {

			request.getSession().invalidate();
			object.put("success", "ע���ɹ�");
			response.sendRedirect(response.encodeRedirectURL("index.jsp"));

			System.out.println("ע�����");
		} catch (Exception e) {
			if(!object.has("error")) {
				object.put("error", "catch��ֵ����");
			}
			out.print(object);
			System.out.println(object);
			System.out.println("catch��ֵ����");
		}
	}
}
