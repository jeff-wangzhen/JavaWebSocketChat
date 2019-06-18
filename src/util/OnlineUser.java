package util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import domain.User;

public class OnlineUser {
	private static OnlineUser instance = new OnlineUser();
// 定义users集合，用于模拟数据库
	public static HashMap<String, User> users = new HashMap<String, User>();

	public static Map<?, ?> objectToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		return new org.apache.commons.beanutils.BeanMap(obj);
	}

	public OnlineUser() {
	}

	public static OnlineUser getInstance() {
		return instance;
	}

	// 获取数据库(users)中的数据
	public static User getUser(String userId) {
		User user = (User) users.get(userId);
		return user;
	}

	public static User getUser(User user) {
		user = (User) users.get(user.getId());
		return user;
	}

	// 向数据库(users)插入数据
	@SuppressWarnings("unlikely-arg-type")
	public boolean removeUser(String userId) {
		try {
			users.remove(getUser(userId));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean removeUser(User user) {
		try {
			users.remove(user);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean insertUser(User user) {
		if (user == null) {
			return false;
		}
		String userId = user.getId();
		System.out.println(user);
		if (users.get(userId) != null) {
			System.out.println("users.get(userId) != null DBUtil 43 ");

		} else {

			users.put(userId, user);
		}
		return true;
	}

	public static void insertUser(ServletContext application, User userObj) {

		if (!userObj.getId().equals("")) {

			OnlineUser.users.put(userObj.getId(), userObj);
		} else {

			OnlineUser.users.put(userObj.getName().substring(4), userObj);
		}

		application.setAttribute("userList", OnlineUser.users);
	}

	public static void removeUser(ServletContext application, User userObj) {

		try {
			if (!userObj.getId().equals("")) {

				OnlineUser.users.remove(userObj.getId(), userObj);
			} else {

				if (userObj.getName() != null) {
					System.out.println("name  " + userObj.getName());
					OnlineUser.users.remove(userObj.getName().toString().substring(4), userObj);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		application.setAttribute("userList", OnlineUser.users);


	}

}
