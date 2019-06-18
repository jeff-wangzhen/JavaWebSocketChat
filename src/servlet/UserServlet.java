
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.User;

import net.sf.json.JSONObject;
import service.UserService;
import util.OnlineUser;

/**
 * Created by codingBoy on 16/10/23.
 */
@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserService UserService = new UserService();

	public String queryId(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User User = CommonUtils.toBean(request.getParameterMap(), User.class);

		return UserService.queryId(User);
	}

	public void checkPasssword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User User = CommonUtils.toBean(request.getParameterMap(), User.class);

		User.setPassword(request.getParameter("password"));
		User userObj = UserService.login(User);
		JSONObject object = new JSONObject();

		if (userObj != null) {
			object.put("success", 1);
		} else {
			object.put("error", "尝试匹配用户名密码失败");
		}
		response.getWriter().print(object);
	}

	public void checkEmail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject object = new JSONObject();
		if (UserService.queryByEmail(request.getParameter("email"))) {
			object.put("error", "该邮箱已注册");

		} else {
			object.put("success", 1);

		}
		response.getWriter().print(object);
		return;
	}

	public void updateInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User();
		ServletContext	application=request.getServletContext();
		String update = request.getParameter("update");
		user = CommonUtils.toBean(request.getParameterMap(), User.class);
		user.setUpdate(update);
		JSONObject object = new JSONObject();
		String email=request.getParameter("email");
		switch (update) {
		case "email":
			if (UserService.queryByEmail(email)) {
				object.put("error", "该邮箱已注册");
			}
			if (!request.getParameter("authCode").toLowerCase()
					.equals(application.getAttribute("authCode"+email).toString().toLowerCase())) {

				object.put("error", "验证码不正确");
			}
			if (object.has("error")) {
				response.getWriter().print(object);
				return;
			}
			break;
		case "name":
			String unamePattern = "^[\\u4E00-\\u9FA5a-zA-Z][\\u4E00-\\u9FA5a-zA-Z0-9_]*$";
			String name = request.getParameter("name");
			if (name.length() < 3 || name.length() > 20 || !Pattern.matches(unamePattern, name)) {
				object.put("error", "昵称不合法");
				return;
			}
			break;
		case "password":
			User temp = new User();
			temp.setEmail(user.getEmail());
			temp.setPassword(user.getOldPassword());
			
			if (!request.getParameter("password").equals(request.getParameter("rePassword"))) {
				System.out.println(request.getParameter("password"));
				System.out.println(request.getParameter("rePassword"));
				object.put("error", "新密码输入不一致");
				response.getWriter().print(object);
				return;
			}
			if (UserService.login(temp) == null) {
				object.put("error", "原密码不正确");
				response.getWriter().print(object);
				return;
			}
			break;
		case "delete":
			if (request.getParameter("cancelInput").equals("false")) {
				object.put("error", "未确认注销账号");
				response.getWriter().print(object);
				return;
			}
			System.out.println("1" + request.getSession().getId());
			request.getSession().invalidate();
//			request.getSession(true).setAttribute("userObj", null);
			System.out.println("2" + request.getSession().getId());
			break;
		default:
			break;
		}

		if (UserService.updateInfo(user)) {
			object.put("success", 1);
			request.getSession().setAttribute("userObj", user);
		} else {
			object.put("error", "更新失败");
		}
		response.getWriter().print(object);
	}

	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User User = CommonUtils.toBean(request.getParameterMap(), User.class);
		User.setId(CommonUtils.uuid());
		// User.setName(request.getParameter("name"));

		UserService.add(User);

		request.setAttribute("msg", "恭喜，成功添加用户");

		JSONObject object = new JSONObject();
		object.put("success", "用户添加成功");
		// object.put("url", "index.jsp");
//        out.print(object);
		System.out.println(object);
		request.setAttribute("register", true);
		request.getRequestDispatcher("UserServlet?method=login").forward(request, response);
		// return "/msg.jsp";
	}

	public void basicInfoSubmit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();

		User User = CommonUtils.toBean(request.getParameterMap(), User.class);
		User.setId(queryId(request, response));

		UserService.basicInfoSubmit(User);
		object.put("success", "信息更新成功");
		out.print(object);
		System.out.println(object);
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject object = new JSONObject();

		User userObj = CommonUtils.toBean(request.getParameterMap(), User.class);
		//userObj.setId(queryId(request, response));

		// User userObj;
		userObj = UserService.login(userObj);
		if (userObj != null) {
			object.put("url", "index.jsp");
			object.put("success", 1);
			System.out.println("invalidate");
			request.getSession().invalidate();
			System.out.println("setAttribute");
			request.getSession(true).setAttribute("userObj", userObj);
			ServletContext application = request.getSession().getServletContext();

			userObj.setLogined(true);
			String hadLogined = "";
			try {
				hadLogined = request.getParameter("hadChecked").toString();
				if (!hadLogined.equals("true") && OnlineUser.getUser(userObj) != null) {
					object.put("check", "该账号已登录，是否继续？");
					out.print(object);
					System.out.println("该账号已登录，是否继续？");
					return;
				}
			} catch (Exception e) {

			}

			OnlineUser.insertUser(application, userObj);
			out.print(object);
			System.out.println(object);
		} else {
			object.put("error", "登录失败，请确认账号密码无误");
			out.print(object);
			System.out.println(object);
			return;
		}
	}

}