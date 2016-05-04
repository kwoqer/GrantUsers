package work;

import java.util.*;

public class MaskList implements Iterable{
	
	private ArrayList<Mask> masks;
	
	public MaskList(){
		masks = new ArrayList<Mask>();
	}
	
	public void addMask(String mask) {
		Mask m = new Mask(mask);
		masks.add(m);
	}
	
	public void addMask(String mask, String n_place, String n_sub, String currency, String acn_right, boolean enable){
		Mask m = new Mask(mask,n_place,n_sub,currency,acn_right,enable);
		masks.add(m);
	}
	
	
	public Iterator iterator() {		
		return masks.iterator();
	}

}
