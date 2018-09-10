package assignment1;

public class TestCode {
	public static void main(String [] args){
		FairLock lock = new FairLock();
		ClientFairLock A1 = new ClientFairLock(lock, 2000);
		ClientFairLock A2 = new ClientFairLock(lock, 2000);
		ClientFairLock B  = new ClientFairLock(lock, 1000);
		
		System.out.println("**************************** TEST OF THE PROJECT **************************** ");
		
		Thread a1 = new Thread(A1, "A1");
		Thread a2 = new Thread(A2, "A2");
		Thread b  = new Thread(B,  "B ");
		
		a1.start();
		a2.start();
		b.start();
		
		try { a1.join(); a2.join(); b.join(); } catch (InterruptedException e) { e.printStackTrace(); }
		
		System.out.println("***************************** END OF THE TEST ***************************** ");
	}
}