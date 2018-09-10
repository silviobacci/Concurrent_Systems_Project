package assignment2_1;

public class TestCode{	
	public static void main(String [] args){
		ResourceManagerJavaLock rm = new ResourceManagerJavaLock();
		Client A1 = new Client(rm, 2000);
		Client A2 = new Client(rm, 2000);
		Client B  = new Client(rm, 3000);
		
		System.out.println("**************************** TEST OF THE PROJECT **************************** ");
		
		Thread a1 = new Thread(A1, "A1");
		Thread a2 = new Thread(A2, "A2");
		Thread b  = new Thread(B,  "B ");
		
		b.start();
		a1.start();
		a2.start();
		
		try { a1.join(); a2.join(); b.join(); } catch (InterruptedException e) { e.printStackTrace(); }
		
		System.out.println("***************************** END OF THE TEST ***************************** ");
	}
}
