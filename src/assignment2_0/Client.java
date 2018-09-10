package assignment2_0;

public class Client extends Thread{
	private ResourceManagerFairLock rm;
	private Resource res;
	private int time;
	
	public Client(ResourceManagerFairLock r, int t){
		rm = r;
		time = t;
	}	
	
	public void run(){
		System.out.println("****************************** START THREAD "+Thread.currentThread().getName()+" ****************************** ");
		try { res = rm.request(); } catch (InterruptedException e) { e.printStackTrace(); }
		try { sleep(time); } catch(InterruptedException e) { e.printStackTrace(); }
		res.increment();
		System.out.println(Thread.currentThread().getName() + ": RESOURCE = " + res.getValue());
		try { rm.release(); } catch (InterruptedException e) { e.printStackTrace(); }
		System.out.println("****************************** END THREAD "+Thread.currentThread().getName()+" ****************************** ");
	}
}
