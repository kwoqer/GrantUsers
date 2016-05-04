package start;

import java.sql.*;
import java.util.HashMap;
import work.*;
import javax.swing.*;

public class Global {
			
	private static Connection conn = null;
	
	private static UserList userlist = new UserList();	 
	
	private static WordInstance word = new WordInstance();
	
	private static Settings settings = new Settings();
	
	public static HashMap<String, String> transt;
	
	
	public static void fillTransTable(){
		transt = new HashMap<String, String>();
		transt.put("�","a");
		transt.put("�","b");
		transt.put("�","v");
		transt.put("�","g");
		transt.put("�","d");
		transt.put("�","e");
		transt.put("�","yo");
		transt.put("�","zh");
		transt.put("�","z");
		transt.put("�","i");
		transt.put("�","y");
		transt.put("�","k");
		transt.put("�","l");
		transt.put("�","m");
		transt.put("�","n");
		transt.put("�","o");
		transt.put("�","p");
		transt.put("�","r");
		transt.put("�","s");
		transt.put("�","t");
		transt.put("�","u");
		transt.put("�","f");
		transt.put("�","h");
		transt.put("�","c");
		transt.put("�","ch");
		transt.put("�","sh");
		transt.put("�","sch");
		transt.put("�","");
		transt.put("�","y");
		transt.put("�","");
		transt.put("�","e");
		transt.put("�","yu");
		transt.put("�","ya");
		transt.put("�","i");
		transt.put("�","yi");
		transt.put("�","ye");
	}
	
	public static String translit(String s){
		String sl = s.toLowerCase();
		String s1, res = "";
		//System.out.println(sl);
		for (int i = 0; i < sl.length(); i++) {
			s1 = sl.substring(i, i+1);
			if (transt.containsKey(s1)) {
				res =  res + transt.get(s1);
			}
			else
				res =  res + "_";
		}
		return res;
	}
	
	public static Connection getConnection(){
		return conn;
	}
	
	public static void  setConnection(String URL, String login, String password){
		try {
			if (conn == null) {
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
				conn = DriverManager.getConnection(URL, login, password);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			errorBox(e.getMessage());
		}
			
			
	}
	
	public static void closeConnection(){
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static UserList getUserList(){
		return userlist;
	}
		
	
	public static WordInstance getWordObject(){
		return word;
	}
	
	public static Settings getSettings(){
		return settings;
	}
	
	public static void errorBox(String s){
		JOptionPane.showMessageDialog(null, s, "������!", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void messageBox(String s){
		JOptionPane.showMessageDialog(null, s, "��������!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String format(String s){		
		String fs = s.substring(0, 1);
		fs = fs.toUpperCase();
		String r = s.substring(1).toLowerCase();
		return  fs + r;
	}
	

}
