package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//
import net.sf.json.JSON;

import net.sf.json.JSONObject;
import util.OnlineUser;
import domain.User;

@WebServlet("/CheckLogin")
public class CheckLogin extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CheckLogin get");
		// this.doPost(request, response);
	}

	public String ReadAsChars(HttpServletRequest request) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder("");
		try {
			br = request.getReader();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setHeader("Content-type", "text/html;charset=GBK");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();

		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			HttpSession session = request.getSession();

			User userObj = new User();
			userObj.setEmail(email);
			userObj.setPassword(password);
			request.getSession().setAttribute("userObj", userObj);
			request.getRequestDispatcher("UserServlet?method=login").forward(request, response);

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
