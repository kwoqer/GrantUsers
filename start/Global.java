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
		transt.put("а","a");
		transt.put("б","b");
		transt.put("в","v");
		transt.put("г","g");
		transt.put("д","d");
		transt.put("е","e");
		transt.put("Є","yo");
		transt.put("ж","zh");
		transt.put("з","z");
		transt.put("и","i");
		transt.put("й","y");
		transt.put("к","k");
		transt.put("л","l");
		transt.put("м","m");
		transt.put("н","n");
		transt.put("о","o");
		transt.put("п","p");
		transt.put("р","r");
		transt.put("с","s");
		transt.put("т","t");
		transt.put("у","u");
		transt.put("ф","f");
		transt.put("х","h");
		transt.put("ц","c");
		transt.put("ч","ch");
		transt.put("ш","sh");
		transt.put("щ","sch");
		transt.put("ь","");
		transt.put("ы","y");
		transt.put("ъ","");
		transt.put("э","e");
		transt.put("ю","yu");
		transt.put("€","ya");
		transt.put("≥","i");
		transt.put("њ","yi");
		transt.put("Ї","ye");
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
		JOptionPane.showMessageDialog(null, s, "ќшибка!", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void messageBox(String s){
		JOptionPane.showMessageDialog(null, s, "¬нимание!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String format(String s){		
		String fs = s.substring(0, 1);
		fs = fs.toUpperCase();
		String r = s.substring(1).toLowerCase();
		return  fs + r;
	}
	

}
