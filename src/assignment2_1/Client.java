package assignment2_1;
import assignment2_0.Resource;

public class Client extends Thread{
	private ResourceManagerJavaLock rm;
	private Resource res;
	private int time;
	
	public Client(ResourceManagerJavaLock r, int t){
		rm = r;
		time = t;
	}	
	
	public void run(){
		System.out.println("****************************** START THREAD "+Thread.currentThread().getName()+" ****************************** ");
		try { res = rm.request(); } catch (InterruptedException e) { e.printStackTrace(); }
		try { sleep(time); } catch (InterruptedException e) { e.printStackTrace(); }
		res.increment();
		System.out.println(Thread.currentThread().getName() + ": RESOURCE = " + res.getValue());
		rm.release();
		System.out.println("****************************** END THREAD "+Thread.currentThread().getName()+" ****************************** ");
	}
}
