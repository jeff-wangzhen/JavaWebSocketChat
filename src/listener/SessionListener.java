package listener;

import javax.servlet.http.HttpSessionListener;

import domain.User;
import service.UserService;
import util.OnlineUser;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;

public class SessionListener implements HttpSessionListener {
	private service.UserService UserService = new UserService();

	/* Session创建事件 */

	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("sessionCreated   ");

	}

	/* Session失效事件 */
	public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext application = se.getSession().getServletContext();
		User user = (User) se.getSession().getAttribute("userObj");
		try {
			user.setLogined(false);
			user.setUpdate("logined");
			UserService.updateInfo(user);
		} catch (Exception e) {
		}
		OnlineUser.removeUser(application, user);
//		System.out.println(user);
//		System.out.println("Session Destroyed   " + user.getName());//某些情况下可能抛出异常，不清楚
	}
}