package work;

import java.util.GregorianCalendar;

import start.Global;

public class Application {

	public static final int SIGNATURE_SIZE = 10;
	public static final int CHIEF_FIELD_SIZE = 21;
	public static final int INTERVAL = 5;
	public static final int DAY_FIELD_SIZE = 4;
	public static final int MONTH_FIELD_SIZE = 17;
	public static final String[] months = {"січня","лютого","березня","квітня",
											   "травня","червня","липня","серпня",
											   "вересня","жовтня","листопада","грудня"};
	protected User user;
	private boolean addAccessField;
	private boolean updateAccessField = false;
	private boolean deleteAccessField = false;
	private boolean closeAccessField;
	protected String departmentField;
	protected String jobField;
	protected String nameField;
	protected String loginField;
	protected String tabnumField;
	protected String roomField;
	protected String phoneField;

	public Application(User u) {
		user = u;
		departmentField = user.getDepartment();
		jobField = user.getJob();
		nameField = user.getFormatName();
		loginField = user.getLogin();
		tabnumField = user.getTabnum();
		roomField = user.getRoom();
		phoneField = user.getPhone();
	}

	public String getDepartmentField() {
		return departmentField;
	}

	public String getJobField() {
		return jobField;
	}

	public String getLoginField() {
		return loginField;
	}

	public String getNameField() {
		return nameField;
	}

	private String formatLine(int size, String content) {
		String res = "";
		if (content.length()>= size) 
			res=content.substring(0, size);			
		else{
			int i = size - content.length();
			int i1 = i/2;
			int i2 = size - content.length() - i1;
			for (int j = 0; j < i1; j++) {
				res = res+"_";
			}
			res=res+content;
			for (int j = 0; j < i2; j++) {
				res = res+"_";
			}
		}
		return res;
	}

	public String getFullChiefSignature() {
		String res = "";
		String ulogin = user.getLogin();
		String name = Global.getUserList().getChiefName(ulogin);
		for (int i = 0; i < SIGNATURE_SIZE; i++) {
			res = res + "_";
		}
		res = res + "/";
		res = res + formatLine(CHIEF_FIELD_SIZE,name);
		res = res + "/";
		for (int i = 0; i < INTERVAL; i++) {
			res = res + " ";
		}
		res = res + "\"";
		GregorianCalendar calend = new GregorianCalendar();
		int date = calend.get(GregorianCalendar.DAY_OF_MONTH);
		int month = calend.get(GregorianCalendar.MONTH);
		int year = calend.get(GregorianCalendar.YEAR);
		res = res + formatLine(DAY_FIELD_SIZE,(new Integer(date)).toString())+"\"";
		res = res + formatLine(MONTH_FIELD_SIZE,months[month]);
		res = res + (new Integer(year)).toString()+"р.";
		return res; 
	}

	public String getPhoneField() {
		return phoneField;
	}

	public String getRoomField() {
		return roomField;
	}

	public String getTabnumField() {
		return tabnumField;
	}

	public String getUserLastName() {
		return user.getName();
	}
	
	public String isPlus(boolean x){
		if (x)
			return "+";
		else
			return "";
	}
	
	public String excludeComma(String s){
		if ((s!=null)&&(s.substring(s.length()-1).equals(","))){
			return s.substring(0, s.length()-1);
		}	
		else
			return s;
	}

}