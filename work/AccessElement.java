package work;

import java.sql.*;

import start.Global;

public class AccessElement {
	
	String query = "select uas.acn_mask, uas.acn_right, uas.n_place," +
			       "uas.n_sub,uas.flag,uas.cur_ref " +
				   "from ua_elmdesc uas " +
				   "where uas.elm_ref= ? order by 1";
	
	private int id;
	private String name;
	private boolean select;
	private boolean history;
	private boolean debet;
	private boolean kredit;
	private boolean parameters;
	private boolean open;
	private boolean restore;
	private boolean method;
	
	private MaskList masks;
	
	
	public AccessElement(int id, String name, boolean select, boolean history, boolean debet, boolean kredit, boolean parameters, boolean open, boolean restore, boolean method) {
		masks = new MaskList();
		this.id = id;
		this.name = name;
		this.select = select;
		this.history = history;
		this.debet = debet;
		this.kredit = kredit;
		this.parameters = parameters;
		this.open = open;
		this.restore = restore;
		this.method = method;
	}
	
	public void createMaskList(){
		try {
			PreparedStatement st = Global.getConnection().prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				masks.addMask(rs.getString("acn_mask"),
						      rs.getString("n_place"),
						      rs.getString("n_sub"),
						      rs.getString("cur_ref"),
						      rs.getString("acn_right"),
						      isPlus(rs.getString("flag")));
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public MaskList getMasks(){
		return masks;
	}
	
	private boolean isPlus(String s){
		boolean res = false;
		if ((s!=null)&&(s.equals("+"))){
			res = true;
		}
		return res; 
	}


	public boolean isDebet() {
		return debet;
	}


	public boolean isHistory() {
		return history;
	}


	public int getId() {
		return id;
	}


	public boolean isKredit() {
		return kredit;
	}


	public boolean isMethod() {
		return method;
	}


	public String getName() {
		return name;
	}


	public boolean isOpen() {
		return open;
	}


	public boolean isParameters() {
		return parameters;
	}


	public boolean isRestore() {
		return restore;
	}


	public boolean isSelect() {
		return select;
	}
	
	
	
	
	

}
