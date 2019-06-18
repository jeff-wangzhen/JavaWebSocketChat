package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import domain.User;

import net.sf.json.JSONObject;

@WebServlet("/getUserlListIndex")
public class getUserlListIndex extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext application = request.getSession().getServletContext();
		User[] userList = null;
		JSONObject object = new JSONObject();
		PrintWriter out = response.getWriter();
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);

		@SuppressWarnings("unchecked")
		HashMap<String, User> map = (HashMap<String, User>) application.getAttribute("userList");
		Iterator<Entry<String, User>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, User> entry = iter.next();

			Object val = entry.getValue();
			object.put("success", ((User) val).getName());
		}
		object.put("error", "Œ¥’“µΩ");
		out.print(object);
	}
}
