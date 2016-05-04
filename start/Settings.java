package start;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.awt.*;

public class Settings {
	
	private Properties properties; 
	
	private String applicationPath;
	private String RTFPath;
	private String templatesPath;
	
	private String DBlogin;
	private String DBpassword;
	private String DBconnectionURI;
	private String userDepartment;
	
	
	public String getRTFPath() {
		return RTFPath;
	}

	public String getTemplatesPath() {
		return templatesPath;
	}
	
	public String getApplicationPath(){
		return applicationPath;
	}
	
	private void setPaths(){
		applicationPath = System.getProperty("user.dir");
		RTFPath = applicationPath+System.getProperty("file.separator")+"RTF";
		templatesPath = applicationPath+System.getProperty("file.separator")+"Templates";
	}
	
	public Settings(){
		properties = new Properties();
		try {
			FileInputStream in = new FileInputStream("GrantUsers.ini");
			properties.load(in);
			//DBlogin = properties.getProperty("login");
			//DBpassword = properties.getProperty("password");
			DBconnectionURI = properties.getProperty("URI");
			setPaths();
		} catch (Exception e) {
			try {
				FileOutputStream out = new FileOutputStream("GrantUsers.ini");
				//properties.put("login", "");
				//properties.put("password", "");
				properties.put("URI", "");
				properties.store(out, "GrantUsers settings");
			} catch (Exception e1) {
				// TODO: handle exception
			}
		}
		
	}

	public String getDBconnectionURI() {
		return DBconnectionURI;
	}

	public String getDBlogin() {
		return DBlogin;
	}

	public String getDBpassword() {
		return DBpassword;
	}
	
	public Dimension getScreenSize(){
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	public void setDBlogin(String blogin) {
		DBlogin = blogin;
	}

	public void setDBpassword(String bpassword) {
		DBpassword = bpassword;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

}
