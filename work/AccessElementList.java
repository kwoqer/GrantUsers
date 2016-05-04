package work;

import java.sql.*;
import java.util.*;

import start.Global;


public class AccessElementList implements Iterable {
	
	private ArrayList<AccessElement> elements;
	private String user;
	
	final String query = "select distinct uas.elm_ref," +
						 "uas.sel,uas.hst,uas.deb,uas.crd,uas.upd," +
						 "uas.ins,uas.rst,uas.exe,oe.elm_name " +
						 "from uss_account uas, odb_elements oe " +
						 "where uas.userid= ? and oe.elm_id=uas.elm_ref order by uas.elm_ref";
	
	public AccessElementList(String user){
		elements = new ArrayList<AccessElement>();
		this.user = user;
	}
	
	public void addElement(int id, String name, boolean select, boolean history, boolean debet, boolean kredit, boolean parameters, boolean open, boolean restore, boolean method){
		AccessElement element = new AccessElement(id, name, select, history, debet, kredit, parameters, open, restore, method);
		element.createMaskList();
		elements.add(element);
	}
	
	public void createList(){
		try {
			PreparedStatement st = Global.getConnection().prepareStatement(query);
			st.setString(1, user);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				addElement(rs.getInt("elm_ref"),
						   rs.getString("elm_name"),
						   isPlus(rs.getString("sel")),
						   isPlus(rs.getString("hst")),
						   isPlus(rs.getString("deb")),
						   isPlus(rs.getString("crd")),
						   isPlus(rs.getString("upd")),
						   isPlus(rs.getString("ins")),
						   isPlus(rs.getString("rst")),
						   isPlus(rs.getString("exe")));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private boolean isPlus(String s){
		boolean res = false;
		if ((s!=null)&&(s.equals("1"))){
			res = true;
		}
		return res; 
	}

	public Iterator iterator() {		
		return elements.iterator();
	}

}
