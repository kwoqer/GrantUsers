package work;

import java.util.*;

import start.*;


public class Application2 extends Application implements Iterable{
	
	public static final int ROW_FIELD_SIZE = 55;
	public static final int ROWS_COUNT = 18;
	private ArrayList<Row> a_masks;
	private AccessElementList elementlist;	
	
	
	public Application2(User u){
		super(u);
		a_masks = new ArrayList<Row>();		
		elementlist = new AccessElementList(user.getLogin());
		elementlist.createList();
		
		
	}
	
	public void arrangeMasks(){
		for (Iterator iter = elementlist.iterator(); iter.hasNext();) {
			AccessElement element = (AccessElement) iter.next();
			Row r = new Row();
			int rowlength = 0;
			String rowcontent = "";
			r.setS(isPlus(element.isSelect()));
			r.setH(isPlus(element.isHistory()));
			r.setD(isPlus(element.isDebet()));
			r.setK(isPlus(element.isKredit()));
			r.setP(isPlus(element.isParameters()));
			r.setO(isPlus(element.isOpen()));
			r.setR(isPlus(element.isRestore()));
			for (Iterator iterator = element.getMasks().iterator(); iterator.hasNext();) {
				Mask mask = (Mask) iterator.next();
				if (mask.isEnabled()){
					String fm = mask.getFormattedMask()+",";				
					if (rowlength+fm.length()>=ROW_FIELD_SIZE){
						r.setA(excludeComma(rowcontent));
						a_masks.add(r);
						r = new Row();
						rowcontent = fm;
						rowlength = fm.length();
						r.setS(isPlus(element.isSelect()));
						r.setH(isPlus(element.isHistory()));
						r.setD(isPlus(element.isDebet()));
						r.setK(isPlus(element.isKredit()));
						r.setP(isPlus(element.isParameters()));
						r.setO(isPlus(element.isOpen()));
						r.setR(isPlus(element.isRestore()));					
					}
					else{
						rowcontent = rowcontent + fm;
						rowlength += fm.length();					
					}
				}	
			}
			if (rowcontent.length()>0)
				r.setA(excludeComma(rowcontent));
				a_masks.add(r);			
			
		}
	}
	
	

	public Iterator iterator() {
		return a_masks.iterator();
	}
	
	public ArrayList<Row> getRows(){
		return a_masks;
	}
	
	public int differenceRows(){
		if (a_masks.size()>ROWS_COUNT){
			return a_masks.size()-ROWS_COUNT;
		}
		else
			return 0;
	}

	public AccessElementList getElementlist() {
		return elementlist;
	}

	public void setElementlist(AccessElementList elementlist) {
		this.elementlist = elementlist;
	}
	
}
