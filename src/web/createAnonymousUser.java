package web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import domain.User;
import util.OnlineUser;

public class createAnonymousUser {
	public createAnonymousUser() {
	}

	public static void create(ServletContext application, HttpSession session) {
		User userObj = null;
		userObj = new User();
		String time = "" + System.currentTimeMillis() + (int) Math.floor(Math.random() * 1000);
		userObj.setName("ƒ‰√˚”√ªß" + time.substring(10));
		userObj.setId("");
		System.out.println("createAnonymousUser   " + userObj.getName());
		OnlineUser.insertUser(application, userObj);
		session.setAttribute("userObj", userObj);
	}
}
