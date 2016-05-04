package work;

import java.util.*;
import java.sql.*;
import start.*;

public class UserList implements Iterable<User>{
	
	private ArrayList<User> users;
	final String[] sys_users = {"SUBGRANT","GRAND2","CADR","VOIC","DBA_WORK",
								"CERBERUS","GSMB",
								"DW_AGENT","MONEYGRAM","SMS0","INVENT","ARMIX","EMS",
								"EXIMOFFICE","USER_EMA",
								"SMSD"/*,"VPS_OPER","CBEXIM","KDIS","REVIZOR","BUCH_VPS"*/};
	
	 String query = "select t.operator, t.comments, d.n_sub, d.name, t.chief_usr," +
	  			    "t.last_name, t.first_name, t.sec_name," + 
	 				//"s.tab_n, s.room_n, s.phone " +
	 				"0 as tab_n, 0 as room_n, 0 as phone" +
					"from odb_oper_list t, departments d"+
					//", usr_l_staff s " +
					"where t.n_sub = d.n_sub " +
					//"and s.ref_n=t.op_n " + 
					"and t.revoke_date is null ";
	
	public UserList(){
		users = new ArrayList<User>();
		for (int i = 0; i < sys_users.length; i++) {
			query = query + "and t.operator!='"+sys_users[i]+"'";
		}
		query = query + " order by t.last_name";
	}

	public void addUser(String login, String name, 
						String name1, String name2, 
						String job, String department, 
						String chief, String tabnum,
						String room, String phone, String depcode){
		User user = new User(login,name,name1,name2,job,department,chief,tabnum,room,phone,depcode);
		
		users.add(user);
	};
	
	public void createList(){
		try {
			Statement st = Global.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				String ph;
				if (rs.getString("phone")!=null)
					ph = rs.getString("phone");
				else
					ph = "";
				addUser(rs.getString("operator"),
						rs.getString("last_name"),
						rs.getString("first_name"),
						rs.getString("sec_name"),
						rs.getString("comments"),
						rs.getString("name"),
						rs.getString("chief_usr"),
						rs.getString("tab_n"),
						rs.getString("room_n"),
						ph,
						rs.getString("n_sub"));
								
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			
		}
	}
	
	public String getChiefName(String userlogin){
		String chieflogin = "";
		String chiefname = "";
		if (users.size()!=0){
			for (Iterator iter = this.iterator(); iter.hasNext();) {
				User user = (User)iter.next();				
				if (user.getLogin()==userlogin){
					chieflogin = user.getChief();
					break;
				}
			}
			
			for (Iterator iter = this.iterator(); iter.hasNext();) {
				User user = (User)iter.next();				
				if (user.getLogin().equalsIgnoreCase(chieflogin)){
					chiefname = user.getFormatName();
					break;
				}
			}
		}
		return chiefname;
	}
	
	public String getDepcode(String userlogin){
		String code = "";
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			User user = (User)iter.next();				
			if (user.getLogin().equalsIgnoreCase(userlogin)){
				code = user.getDepcode();
				break;
			}
		}
		return code;
		
	}
	
	public User getUserByNumber(int i){
		return users.get(i);
	}
	
	public User getUserByFullName(String fullname){		
		for (Iterator iter = this.iterator(); iter.hasNext();) {
			User user = (User)iter.next();				
			if (user.getFormatFullName().equalsIgnoreCase(fullname)){
				return user;				
			}
		}
		return null;
	}

	public Iterator<User> iterator() {		
		return users.iterator();
	}

		
	
	
}
