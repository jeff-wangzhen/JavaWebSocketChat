package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import domain.User;
import util.OnlineUser;

public class SessionAttributeListener implements HttpSessionAttributeListener {

	public SessionAttributeListener() {
	};

	public void attributeAdded(HttpSessionBindingEvent arg0) {
//		System.out.println("增加了" + arg0.getName() + " " + ((User) arg0.getValue()).getName());
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
//		System.out.println("删除了" + arg0.getName() + " " + ((User) arg0.getValue()).getName());
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		try {
			ServletContext application = arg0.getSession().getServletContext();
//		System.out.println("取代了" + arg0.getName() + " " + ((User) arg0.getValue()).getName() + "现在的新值是"
//				+ ((User) arg0.getSession().getAttribute(arg0.getName())).getName());
			OnlineUser.removeUser(application, (User) arg0.getValue());
		} catch (Exception e) {
		}
	}

}