package web;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import net.sf.json.JSONObject;

public class RegisterFormBean {
	private String name; // 定义昵称
	private String password; // 定义密码
	private String password2; // 定义确认密码
	private String email; // 定义邮箱
	private String isChecked; // 定义邮箱
	private String verification; // 定义邮箱
	// 定义成员变量errors,用于封装表单验证时的错误信息
	private Map<String, String> errors = new HashMap<String, String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name ;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String isChecked() {
		return isChecked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public void setChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getChecked() {
		return isChecked;
	}

	public void setVertification(String Verification) {
		this.verification = Verification;
	}

	public String getVertification() {
		return verification;
	}
	public JSONObject validate() {
		boolean flag = true;
		JSONObject object = new JSONObject();
		String emailPattern = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]+$";
		String unamePattern = "^[\\u4E00-\\u9FA5a-zA-Z][\\u4E00-\\u9FA5a-zA-Z0-9_]*$";
		if (!"true".equals(isChecked)) {

			object.put("error", "未同意协议");
			errors.put("isChecked", "未同意协议.");
			flag = false;
		}
		
		if (name.length() < 3 || name.length() > 20 || !Pattern.matches(unamePattern, name)) {
			object.put("error", "昵称不合法");
			errors.put("name", "请输入姓名.");
			flag = false;
		}
		if (password.length() < 6 || password.length() > 20 || !Pattern.compile("[0-9]").matcher(password).find()
				|| !Pattern.compile("[a-zA-Z]").matcher(password).find()) {
			object.put("error", "密码不合法");
			errors.put("password", "请输入密码.");
			flag = false;
		}
		if (!password.equals(password2)) {
			object.put("error", "两次密码输入不一致");
			errors.put("password2", "两次输入的密码不匹配.");
			flag = false;
		}
		// 对email格式的校验采用了正则表达式
		if (!Pattern.matches(emailPattern, email)) {
			object.put("error", "邮箱不合法");
			errors.put("email", "邮箱不合法");
			flag = false;
		}
		System.out.println("65786765676767575");
		System.out.println(object);
		return object;
	}

	// 向Map集合errors中添加错误信息
	public void setErrorMsg(String err, String errMsg) {
		if ((err != null) && (errMsg != null)) {
			errors.put(err, errMsg);
		}
	}

	// 获取errors集合
	public Map<String, String> getErrors() {
		return errors;
	}
}
