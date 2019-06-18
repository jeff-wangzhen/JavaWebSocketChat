package service;

import dao.UserDao;
import domain.User;

/**
 * Created by codingBoy on 16/10/23.
 */
public class UserService {
	UserDao UserDao = new UserDao();

	public void add(User User) {
		UserDao.add(User);
	}

	public void basicInfoSubmit(User User) {
		UserDao.basicInfoSubmit(User);
	}

	public String queryId(User User) {
		return UserDao.queryId(User);
	}

	public Boolean updateInfo(User User) {
		return UserDao.updateInfo(User) > 0;
	}

	public User findById(String id) {
		return UserDao.findById(id);
	}

	public User findByName(String name) {
		return UserDao.findByName(name);
	}

	public Boolean hasUser(String name) {
		if (UserDao.findByName(name) != null)
			return true;
		else
			return false;
	}

	public User login(User userObj) {

		userObj = UserDao.login(userObj);
		System.out.println(userObj);
		if (userObj != null)
			return userObj;
		else
			return null;
	}

	public boolean queryByEmail(String email) {
		return UserDao.queryByEmail(email) != null;
	}

	public void edit(User User) {
		UserDao.edit(User);
	}

	public void delete(String id) {
		UserDao.delete(id);
	}

}
