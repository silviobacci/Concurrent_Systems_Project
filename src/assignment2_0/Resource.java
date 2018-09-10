package assignment2_0;

public class Resource {
	private int res;
	
	public Resource(){
		res = 0;
	}
	
	public void increment(){
		res++;
	}
	
	public void decrement(){
		res--;
	}
	
	public int getValue(){
		return res;
	}
}
