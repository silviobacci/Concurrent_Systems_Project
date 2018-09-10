package assignment1;

public class EventSemaphore {
	private enum Semaphore {RED, GREEN}
	private Semaphore state;
	
	public EventSemaphore(){
		state = Semaphore.RED; 
	}
	
	public EventSemaphore(boolean initialState){
		if(initialState)
			state = Semaphore.GREEN;
		else
			state = Semaphore.RED;
	}
	
	public synchronized void block() throws InterruptedException{
		// In order to avoid spurious wake up we check the condition in a while loop
		while(state == Semaphore.RED){ wait(); }
		state = Semaphore.RED; 
	}
	
	public synchronized void unblock(){
		state = Semaphore.GREEN; 
		notifyAll();
	}
}