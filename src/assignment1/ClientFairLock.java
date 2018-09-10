package assignment1;

public class ClientFairLock extends Thread{
	private FairLock lock;
	private int time;
	
	public ClientFairLock(FairLock l, int t){
		lock = l;
		time = t;
	}	
	
	public void run(){
		System.out.println("****************************** START THREAD "+Thread.currentThread().getName()+" ****************************** ");
		try { lock.lock(); } catch (InterruptedException e) { e.printStackTrace(); }
		try { sleep(time); } catch(InterruptedException e){ e.printStackTrace(); }
		lock.unlock();
		System.out.println("****************************** END THREAD "+Thread.currentThread().getName()+" ****************************** ");
	}
}
