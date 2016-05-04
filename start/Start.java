package start;


import java.sql.*;
import work.*;
import oracle.jdbc.driver.*;

public class Start {

	/**
	 * @param args
	 */


	public Start(){
		
	}
	
	public static void main(String[] args) {
		
		try {
			LoginForm loginform = new LoginForm();
			loginform.setVisible(true);
			Global.fillTransTable();
						
			
			
		} catch (Exception e) {
			System.out.println(e);
	}
	}

}
