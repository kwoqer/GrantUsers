package work;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import start.Global;

public class Application3n extends Application implements Iterable {

	private static final int ROW_WIDTH = 54;
	private ArrayList<String> rows;
	private String row;
	
	private void addItemToRow(String l){		
		if(row.length()+(l.length()+1)<ROW_WIDTH){
			if (!row.equals("")) {
				row = row + ","+l;
			} else {
				row = l;
			}
		}
		else{
			rows.add(row);
			row = l;
		}								
	}
	
	public void createRows(){		
		try {
			PreparedStatement st = Global.getConnection().prepareStatement(query);
			String u = user.getLogin();
			st.setString(1,u);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String node = (new Integer(rs.getInt("elm_ref"))).toString();
				addItemToRow(node);
			} 			
			if (!row.equals("")){
				rows.add(row);
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Application3n(User u) {
		super(u);
		rows = new ArrayList<String>();
		row = "";
	}

	public Iterator iterator() {
		return rows.iterator();		
	}

	private String query = "select uns.elm_ref, uns.elm_name, uns.full_name "+
    						"from uss_node uns "+   
    						"where uns.operator=?";
	
}
