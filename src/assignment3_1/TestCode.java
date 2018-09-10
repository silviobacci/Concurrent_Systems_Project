package assignment3_1;

public class TestCode{	
	public static void main(String [] args){
		ResourceManager rm = new ResourceManager();
		
		ThreadA A1 = new ThreadA(rm);
		ThreadA A2 = new ThreadA(rm);
		ThreadB B  = new ThreadB(rm);
		
		System.out.println("*************** TEST OF THE PROJECT **************** ");
		
		Thread a1 = new Thread(A1, "A1");
		Thread a2 = new Thread(A2, "A2");
		Thread b = new Thread(B, "B ");
		
		a1.start();
		a2.start();
		b.start();		
		
		try { a1.join(); a2.join(); b.join(); } catch (InterruptedException e) { e.printStackTrace(); }
		
		System.out.println("**************** END OF THE TEST ***************** ");
	}
}