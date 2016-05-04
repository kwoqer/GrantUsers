package work;


import java.sql.*;
import java.util.*;

import start.Global;


public class Application3m extends Application implements Iterable {
	
	private static final int ROW_WIDTH = 54;
	private ArrayList<String> rows;
	private ArrayList<String> rootMenu;
	private String row;
	private ArrayList<String> items;
	
	

	public Application3m(User u) {
		super(u);
		rows = new ArrayList<String>();
		rootMenu = new ArrayList<String>();		
	}

	public Iterator iterator() {		
		return rows.iterator();
	}
	
	 
	
	
	private void createElements(){
		try {
			PreparedStatement st = Global.getConnection().prepareStatement(query1);
			String u = user.getLogin();
			for (int i = 1; i <= 8; i++) {
				st.setString(i,u);
			}			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				rootMenu.add(new Integer(rs.getInt("line_id")).toString()+" ("+Global.format(rs.getString("line"))+")");
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			//Global.errorBox("createElements"+e.getMessage());
			e.printStackTrace();			
		}		
	}

	private void addItemToRow(String l){
		if (!items.contains(l)){
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
			items.add(l);
		}	
		
	}
	
	private void recurse(String l){		
		addItemToRow(l);
		try {
			PreparedStatement st = Global.getConnection().prepareStatement(query2);
			String u = user.getLogin();
			st.setString(1,l); st.setString(2,u);
			st.setString(3,l);st.setString(4,u);st.setString(5,u);
			st.setString(6,l);st.setString(7,u);
			st.setString(8,l);st.setString(9,u);
			st.setString(10,l);st.setString(11,u);st.setString(12,u);
			st.setString(13,l);st.setString(14,l);st.setString(15,l);
			st.setString(16,u);st.setString(17,l);
			st.setString(18,u);st.setString(19,u);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String line = (new Integer(rs.getInt("line_id"))).toString();
				//if (childQuantity(line)>0){
					recurse(line);
				//}	
				if (childQuantity(line)==0){
					if (usChilDost(line)==0){					
						addItemToRow(line);
					}
				}
			}
			rs.close();
			st.close();			
		} catch (Exception e) {
			//Global.errorBox("recurse"+e.getMessage());
			e.printStackTrace();
			
		}
	}
	
	private int childQuantity(String l){
		try {
			PreparedStatement st = Global.getConnection().prepareStatement(query3);
			String u = user.getLogin();
//			 l,u,l,l,u,u,l,l,u,l,u,l,u,u,l,u,l,u,u
			st.setString(1,l); st.setString(2,u);
			st.setString(3,l);st.setString(4,l);st.setString(5,u);
			st.setString(6,u);st.setString(7,l);
			st.setString(8,l);st.setString(9,u);
			st.setString(10,l);st.setString(11,u);st.setString(12,l);
			st.setString(13,u);st.setString(14,u);st.setString(15,l);
			st.setString(16,u);st.setString(17,l);
			st.setString(18,u);st.setString(19,u);
			ResultSet rs = st.executeQuery();
			rs.next();
			int res = rs.getInt(1);
			rs.close();
			st.close();
			return res;
		} catch (Exception e) {
			//Global.errorBox("childQuantity"+e.getMessage());			
			return 0;
		}
		
	}
	
	public void createRows(){				
		try {
			createElements();
			for (Iterator iter = rootMenu.iterator(); iter.hasNext();) {
				String rm = (String) iter.next();				
				rows.add(rm+":");
				items = new ArrayList<String>();
				rm = readToSpace(rm);
				PreparedStatement st = Global.getConnection().prepareStatement(query2);
				String u = user.getLogin();
//				 - u -user, l - line:  l,u,l,u,u,l,u,l,u,l,u,u,l,l,l,u,l,u,u
				st.setString(1,rm); st.setString(2,u);
				st.setString(3,rm);st.setString(4,u);st.setString(5,u);
				st.setString(6,rm);st.setString(7,u);
				st.setString(8,rm);st.setString(9,u);
				st.setString(10,rm);st.setString(11,u);st.setString(12,u);
				st.setString(13,rm);st.setString(14,rm);st.setString(15,rm);
				st.setString(16,u);st.setString(17,rm);
				st.setString(18,u);st.setString(19,u);
				ResultSet rs = st.executeQuery(); 
				row = "";				
				while (rs.next()) {
					String line = (new Integer(rs.getInt("line_id"))).toString();
					recurse(line);
				} 
				
				if (!row.equals("")){
					rows.add(row);
				}
				rs.close();
				st.close();
			}
		} catch (Exception e) {
			//Global.errorBox("createRows"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String readToSpace(String s){
		String res = "";
		String c = "";
		int i = 0;
		while ((!c.equals(" ")) && (i<s.length()-1)) {
			c=s.substring(i,i+1);
			res = res + c;
			i++;
		}
		return res;
	}
	
	private int usChilDost(String l){
		try {
			PreparedStatement st = Global.getConnection().prepareStatement(query4);
			st.setString(1, l);
			ResultSet rs = st.executeQuery(); 
			rs.next();
			int res = rs.getInt(1);
			rs.close();
			st.close();
			return res;
		} catch (Exception e) {
			//Global.errorBox("usChilDost"+e.getMessage());
			return 0;
		}
		
		
	} 
	
	// - 8 подстановок username
	private String query1 = "select distinct mln.line, mln.line_id "+
    					"from menu_tree t,"+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b,"+
    					"menu_line_ mln  ,   odb_grants ogr , odb_roles orl, OPER_ROLES_ orp "+
    					"where t.line_parent=0 "+
    					"and ml.line_id=t.line_parent "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? "+
    					"and mln.line_id=t.line_child "+
    					"and mln.elm_ref= ogr.elm_ref "+
    					"and ogr.role_ref=orl.role_id "+
    					"and orl.role_id=orp.granted_role "+
    					"and orp.username=? "+
    					"union "+
    					"select distinct mln.line, mln.line_id "+
    					"from menu_tree t, menu_line_ mln ,odb_grants ogr "+
    					"where t.line_parent=0 "+
    					"and t.line_child=mln.line_id "+
    					"and mln.elm_ref = ogr.elm_ref "+
    					"and ogr.role_ref= '-1' "+
    					"union "+
    					"select  distinct mln.line, mln.line_id "+
    					"from menu_tree t, menu_line_ ml,odb_grants mnum,"+
    					"odb_roles c, OPER_ROLES_ b,odb_elements oe,menu_line_ mln, odb_grants ogr1,"+
    					"odb_roles orl1, OPER_ROLES_ orp1,odb_elements oel "+
    					"where t.line_parent=0 "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? "+
    					"and mln.line_id=t.line_child "+
    					"and mln.elm_ref is NULL "+
    					"and mln.name_ref=oe.elm_name "+
    					"and oe.elm_id=ogr1.elm_ref "+
    					"and ogr1.role_ref=orl1.role_id "+
    					"and orl1.role_id=orp1.granted_role "+
    					"and orp1.username=? "+
    					"union "+
    					"select distinct mln.line, mln.line_id "+
    					"from menu_tree t, menu_line_ mln ,odb_grants ogr, odb_elements oe "+
    					"where t.line_parent=0 "+
    					"and t.line_child=mln.line_id "+
    					"and mln.elm_ref is NULL "+
    					"and mln.name_ref=oe.elm_name "+
    					"and oe.elm_id=ogr.elm_ref "+
    					"and ogr.role_ref= '-1' "+
    					"union "+
    					"select distinct mln.line, mln.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, oper_all_roles b, "+
    					"menu_line_ mln,odb_grants ogr,odb_roles orl,oper_all_roles orp "+
    					"where t.line_parent=0 "+
    					"and ml.line_id=t.line_parent "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.role_ref "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? "+
    					"and mln.line_id=t.line_child "+
    					"and mln.elm_ref= ogr.elm_ref "+
    					"and ogr.role_ref=orl.role_id "+
    					"and orl.role_id=orp.role_ref "+
    					"and orp.username=? "+
    					"union "+
    					"select  distinct mln.line, mln.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, oper_all_roles b,odb_elements oe, "+
    					"menu_line_ mln,odb_grants ogr1, "+
    					"odb_roles orl1, oper_all_roles orp1 , odb_elements oel "+
    					"where t.line_parent=0 "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.role_ref "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=?"+
    					"and mln.line_id=t.line_child "+
    					"and mln.elm_ref is NULL "+
    					"and mln.name_ref=oe.elm_name "+
    					"and oe.elm_id=ogr1.elm_ref "+
    					"and ogr1.role_ref=orl1.role_id "+
    					"and orl1.role_id=orp1.role_ref "+
    					"and orp1.username=? "+
    					"order by 2 ";
	
	// - u -user, l - line:  l,u,l,u,u,l,u,l,u,l,u,u,l,l,l,u,l,u,u
	private String query2 = "select distinct ml.line, ml.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id not in (select en.elm_ref from elm2node en, odb_elements oe1) "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? "+
    					"union "+
    					"select distinct ml.line, ml.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and ml.name_ref=oe.elm_name "+
    					"and 1  in (select us_nodDos(?,t.line_child) from dual) "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? "+
    					"union "+
    					"select distinct ml.line, ml.line_id "+
    					"from menu_tree t,menu_line_ ml,odb_grants mnum, odb_roles c, "+
    					"oper_all_roles bb, "+
    					"odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID=bb.role_ref "+
    					"and (bb.end_date>=sysdate or bb.end_date is NULL) "+
    					"and bb.username=? "+
    					"union "+
    					"select  distinct ml.line, ml.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and oe.elm_id not in (select en.elm_ref from elm2node en, odb_elements oe1) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? "+
    					"union "+
    					"select  distinct ml.line, ml.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and 1  in (select us_nodDos(?,t.line_child) from dual) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username= ?"+
    					"union "+
    					"select distinct mln.line, mln.line_id "+
    					"from menu_tree t, menu_line_ mln, odb_grants ogr "+
    					"where t.line_parent=? "+
    					"and t.line_child=mln.line_id "+
    					"and mln.elm_ref = ogr.elm_ref "+
    					"and ogr.role_ref= '-1' "+
    					"union "+
    					"select distinct mln.line, mln.line_id "+
    					"from menu_tree t, menu_line_ mln ,odb_grants ogr, odb_elements oe "+
    					"where t.line_parent=? "+
    					"and t.line_child=mln.line_id "+
    					"and mln.elm_ref is NULL "+
    					"and mln.name_ref=oe.elm_name "+
    					"and oe.elm_id=ogr.elm_ref "+
    					"and ogr.role_ref= '-1' "+
    					"union "+
    					"select  distinct ml.line, ml.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, "+
    					"oper_all_roles b,odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and oe.elm_id not in (select en.elm_ref from elm2node en, odb_elements oe1) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.role_ref "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? "+
    					"union "+
    					"select  distinct ml.line, ml.line_id "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, "+
    					"oper_all_roles b,odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and 1  in (select us_nodDos(?,t.line_child) from dual) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.role_ref "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? ";
						
	// l,u,l,l,u,u,l,l,u,l,u,l,u,u,l,u,l,u,u
	private String query3 = "select sum(cnt) from "+
    					"((select count (distinct  ml.line_id) cnt "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, "+                 
    					"odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id not in (select en.elm_ref from elm2node en, odb_elements oe1) "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=?) "+
    					"union "+
    					"(select count( distinct  mln.line_id) cnt "+
    					"from menu_tree t, menu_line_ mln  ,   odb_grants ogr "+
    					"where t.line_parent=? "+
    					"and t.line_child=mln.line_id "+
    					"and mln.elm_ref = ogr.elm_ref "+
    					"and ogr.role_ref= '-1' ) "+
    					"union "+
    					"(select count( distinct  ml.line_id) cnt "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, "+                 
    					"odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and ml.name_ref=oe.elm_name "+
    					"and 1  in (select us_nodDos(?,t.line_child) from dual) "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+	
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? )"+
    					"union "+
    					"(select count(distinct  mln.line_id) cnt "+
    					"from menu_tree t, menu_line_ mln ,odb_grants ogr, odb_elements oe "+
    					"where t.line_parent=? "+
    					"and t.line_child=mln.line_id "+
    					"and mln.elm_ref is NULL "+
    					"and mln.name_ref=oe.elm_name "+
    					"and oe.elm_id=ogr.elm_ref "+
    					"and ogr.role_ref= '-1' )"+
    					"union "+
    					"(select count( distinct  ml.line_id) cnt "+
    					"from menu_tree t,menu_line_ ml,odb_grants mnum, odb_roles c, "+
    					"oper_all_roles bb, odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref= mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID=bb.role_ref "+
    					"and (bb.end_date>=sysdate or bb.end_date is NULL) "+
    					"and bb.username=? )"+
    					"union "+
    					"(select count( distinct  ml.line_id) cnt "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, "+                
    					"odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and oe.elm_id not in (select en.elm_ref from elm2node en, odb_elements oe1) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? )"+
    					"union "+
    					"(select count( distinct  ml.line_id) cnt "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, OPER_ROLES_ b, "+                
    					"odb_elements oe "+                
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and 1  in (select us_nodDos(?,t.line_child) from dual) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.GRANTED_ROLE "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username=? )"+
    					"union "+
    					"(select count( distinct  ml.line_id) cnt "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, "+
    					"oper_all_roles b,odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and oe.elm_id not in (select en.elm_ref from elm2node en, odb_elements oe1) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.role_ref "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username= ? )"+
    					"union "+
    					"(select count( distinct  ml.line_id) cnt "+
    					"from menu_tree t, "+
    					"menu_line_ ml,odb_grants mnum, odb_roles c, "+
    					"oper_all_roles b,odb_elements oe "+
    					"where t.line_parent=? "+
    					"and ml.line_id=t.line_child "+
    					"and ml.elm_ref is NULL "+
    					"and 1  in (select us_nodDos(?,t.line_child) from dual) "+
    					"and ml.name_ref=oe.elm_name "+
    					"and oe.elm_id=mnum.elm_ref "+
    					"and mnum.role_ref=c.role_id "+
    					"and c.ROLE_ID= b.role_ref "+
    					"and (b.end_date>=sysdate or b.end_date is NULL) "+
    					"and b.username= ? ))";
	
	private String query4 ="select us_ChilDost(?) from dual";
	
	
}
