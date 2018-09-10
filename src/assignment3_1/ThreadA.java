package assignment3_1;
public class ThreadA extends Thread{
	private final int time = 2000;
	private ResourceManager rm;
	
	private void print(String s){
		System.out.println(Thread.currentThread().getName() + ": " + s);
	}
	
	private void use(){
		print("USE START");
		try { sleep(time); } catch (InterruptedException e) { e.printStackTrace(); }
		print("USE END");
	}
	
	public ThreadA(ResourceManager r){
		rm = r;
	}
	
	public void run(){
		System.out.println("********************* START "+Thread.currentThread().getName()+" *********************");
		try { rm.acquire_a(); } catch (InterruptedException e) { e.printStackTrace(); }
		try { rm.endacquire_a(); } catch (InterruptedException e) { e.printStackTrace(); }
		use();
		try { rm.release(); } catch (InterruptedException e) { e.printStackTrace(); }
		System.out.println("********************* END "+Thread.currentThread().getName()+" *********************");
	}
}
