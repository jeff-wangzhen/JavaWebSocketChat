package web;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;

import net.sf.json.JSONObject;

public class RegisterFormBean {
	private String name; // �����ǳ�
	private String password; // ��������
	private String password2; // ����ȷ������
	private String email; // ��������
	private String isChecked; // ��������
	private String verification; // ��������
	// �����Ա����errors,���ڷ�װ����֤ʱ�Ĵ�����Ϣ
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

			object.put("error", "δͬ��Э��");
			errors.put("isChecked", "δͬ��Э��.");
			flag = false;
		}
		
		if (name.length() < 3 || name.length() > 20 || !Pattern.matches(unamePattern, name)) {
			object.put("error", "�ǳƲ��Ϸ�");
			errors.put("name", "����������.");
			flag = false;
		}
		if (password.length() < 6 || password.length() > 20 || !Pattern.compile("[0-9]").matcher(password).find()
				|| !Pattern.compile("[a-zA-Z]").matcher(password).find()) {
			object.put("error", "���벻�Ϸ�");
			errors.put("password", "����������.");
			flag = false;
		}
		if (!password.equals(password2)) {
			object.put("error", "�����������벻һ��");
			errors.put("password2", "������������벻ƥ��.");
			flag = false;
		}
		// ��email��ʽ��У�������������ʽ
		if (!Pattern.matches(emailPattern, email)) {
			object.put("error", "���䲻�Ϸ�");
			errors.put("email", "���䲻�Ϸ�");
			flag = false;
		}
		System.out.println("65786765676767575");
		System.out.println(object);
		return object;
	}

	// ��Map����errors����Ӵ�����Ϣ
	public void setErrorMsg(String err, String errMsg) {
		if ((err != null) && (errMsg != null)) {
			errors.put(err, errMsg);
		}
	}

	// ��ȡerrors����
	public Map<String, String> getErrors() {
		return errors;
	}
}
