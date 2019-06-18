package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.jdbc.TxQueryRunner;

import domain.User;

/**
 * Created by codingBoy on 16/10/23.
 */
public class UserDao {
	private QueryRunner qr = new TxQueryRunner();

	public void add(User c) {
		try {
			String sql = "insert into tb_users(id,name,password,email,portrait,gender) values(?,?,?,?,?,?)";

			Object[] params = { c.getId(), c.getName(), c.getPassword(), c.getEmail(), c.getPortrait(), c.getGender() };
			System.out.print(params);
			qr.update(sql, params);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User queryByEmail(String email) {
		try {
			String sql = "select * from tb_users where email=?";

			Object[] params = { email };
			System.out.print(params);
			return qr.query(sql, new BeanHandler<User>(User.class), params);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int updateInfo(User c) {
		try {
			String update = c.getUpdate();
			String param = "";
			String sql = "";
			switch (update) {
			case "logined":
				Boolean logined = c.getLogined();
				if (logined)
					sql = "update tb_users set logined=1  where id=?";
				else
					sql = "update tb_users set logined=0  where id=?";
				Object[] params = { c.getId() };
				return qr.update(sql, params);
			case "delete":
				sql = " DELETE FROM tb_users WHERE id=?";
				Object[] deleteParams = { c.getId() };
				return qr.update(sql, deleteParams);
			case "name":
				param = c.getName();
				sql = "update tb_users set name=?  where id=?";
				break;
			case "password":
				param = c.getPassword();
				sql = "update tb_users set password=?  where id=?";
				break;

			case "email":
				param = c.getEmail();
				sql = "update tb_users set email=?  where id=?";
				break;

			case "portrait":
				param = c.getPortrait();
				sql = "update tb_users set portrait=?  where id=?";
				break;

			}

			Object[] params = { param, c.getId() };
			System.out.print(params);
			return qr.update(sql, params);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void basicInfoSubmit(User c) {
		try {
			String sql = "update tb_users set name=?,phone=?,gender=? where id=?";

			Object[] params = { c.getName(), c.getPhone(), c.getGender(), c.getId() };
			System.out.print(params);
			qr.update(sql, params);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String queryId(User c) {
		try {
			String sql = "select id  from tb_users where email=? and password=?";

			Object[] params = { c.getEmail(), c.getPassword() };
			System.out.print(params);
			// qr.update(sql, params);
			String id = qr.query(sql, new BeanHandler<String>(String.class), params);
			return id;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User findById(String id) {
		try {
			String sql = "select * from tb_users where id=?";
			return qr.query(sql, new BeanHandler<User>(User.class), id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User findByName(String name) {
		try {
			String sql = "select * from tb_users where name=?";
			return qr.query(sql, new BeanHandler<User>(User.class), name);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public User login(User user) {
		try {
			String sql = "select * from tb_users where email=? and password=?";
			Object[] params = new Object[] { user.getEmail(), user.getPassword() };
			return qr.query(sql, new BeanHandler<User>(User.class), params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void edit(User User) {
		try {
			String sql = "update tb_users set name=?,password=?,email=? where id=?";
			Object[] params = { User.getName(), User.getPassword(), User.getEmail(), User.getId() };

			qr.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String id) {
		try {
			String sql = "delete from tb_users where id=?";

			qr.update(sql, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
