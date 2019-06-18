package mail;

import java.io.IOException;

import java.util.Random;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import net.sf.json.JSONObject;
import service.UserService;
import mail.SendMailText;

/**
 * Servlet implementation class SendMail
 */
@WebServlet("/SendMail")
public class SendMail extends HttpServlet {
	private service.UserService UserService = new UserService();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);
		ServletContext application = request.getServletContext();
		request.setCharacterEncoding("utf-8"); // 只对消息体有效
		response.setCharacterEncoding("utf-8");
		JSONObject object = new JSONObject();
		// System.out.print("1");
		// object.put("error", 1);
		HttpSession session = request.getSession();
		User user = new User();
		String email = request.getParameter("email");
		user.setEmail(email);
		String emailPattern = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]+$";
		if (!Pattern.matches(emailPattern, email)) {
			object.put("error", "邮箱不合法！");
			response.getWriter().print(object);
			return;
		}
		if (UserService.queryByEmail(request.getParameter("email"))) {
			object.put("error", "该邮箱已注册");
			response.getWriter().print(object);
			return;
		}
		try {
			String authCode = authCode(6);
			application.setAttribute("authCode" + email, authCode);
			System.out.print(application.getAttribute("authCode" + email));
			removeAttrbute(application, "authCode" + email, 10 * 60 * 1000);
			Thread.sleep(1000);
			System.out.println(application.getAttribute("authCode" + email));
			// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
			new SendMailText("JavaWebSocketChat", request.getParameter("email"), "JavaWebSocketChat验证码",
					"JavaWebSocketChat在线聊天系统</br>   验证码：<h1>" + authCode + "</h1>    </br> 该验证码十分钟内有效。");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		object.put("success", 1);
		response.getWriter().print(object);
	}

	public String authCode(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	private void removeAttrbute(final ServletContext application, final String attrName, int time) {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// 删除session中存的验证码
				application.removeAttribute(attrName);
				timer.cancel();
			}
		}, time);
	}
}
