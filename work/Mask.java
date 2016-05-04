package work;

public class Mask {
	
	private String acn_mask;
	private String n_place;
	private String n_sub;
	private String currency;
	private String acn_right;
	private boolean enabled;
	
	
	public Mask(String acn_mask) {	
		this.acn_mask = acn_mask;
		this.n_place = "";
		this.n_sub = "";
		this.currency = "";
		this.acn_right ="";
		this.enabled = true;
	}


	public Mask(String acn_mask, String n_place, String n_sub, String currency, String acn_right,boolean enabled) {
		
		this.acn_mask = acn_mask;
		this.n_place = n_place;
		this.n_sub = n_sub;
		this.currency = currency;
		this.acn_right = acn_right;
		this.enabled = enabled;
	}


	public String getFormattedMask(){
		String r = acn_mask;		
		if ((currency!=null) && (currency.length()>0))
			r = r+"."+currency;
		if ((n_place!=null) && (n_place.length()>0))
			r = r+"-"+n_place;
		if ((n_sub!=null) && (n_sub.length()>0))
			r = r+"-"+n_sub;
		return r;
	}
	
	public String getMask() {
		return acn_mask;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public String getN_place() {
		return n_place;
	}


	public String getN_sub() {
		return n_sub;
	}
	
	
	
	
	
	
}
