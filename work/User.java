package work;

import start.*;

public class User {
		
	private String login;
	private String name;
	private String name1;
	private String name2;
	private String job;
	private String department;
	private String chief;
	
	private String tabnum;
	private String room;
	private String phone;
	
	private String depcode;
	
	
	
	
	
	public User(String login, String name, 
			    String name1, String name2, 
			    String job, String department, 
			    String chief, String tabnum,
			    String room, String phone, String depcode) {		
		this.login = login;
		this.name = name;
		this.name1 = name1;
		this.name2 = name2;
		this.job = job;
		this.department = department;
		this.chief = chief;
		this.tabnum = tabnum;
		this.room = room;
		this.phone = phone;
		this.depcode = depcode;
	}
	
	public String getChief() {
		return chief;
	}
	public void setChief(String chief) {
		this.chief = chief;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFormatName(){
		return Global.format(name)+" "+getInitial(name1)+" "+getInitial(name2);
	}
	
	public String getFormatFullName(){
		return Global.format(name)+" "+Global.format(name1)+" "+Global.format(name2);
	}
		
	
	public String getPhone() {
		return phone;
	}

	public String getRoom() {
		return room;
	}

	public String getTabnum() {
		return tabnum;
	}

	public String getDepcode(){
		return depcode;
	}
		
	private String getInitial(String s){
		return s.substring(0, 1)+".";
	}
	
	

}
