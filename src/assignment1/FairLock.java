package assignment1;

import java.util.ArrayList;

public class FairLock {
	private ArrayList<EventSemaphore> entryQueue, urgentQueue;
	private boolean busy;

	public FairLock(){
		entryQueue = new ArrayList<EventSemaphore>();
		urgentQueue = new ArrayList<EventSemaphore>();
		busy = false;
	}
	
	private void print(String s){
		System.out.println(Thread.currentThread().getName() + ": FAIR LOCK - " + s);
	}
	
	private boolean isEmpty(ArrayList<EventSemaphore> queue){
		if(queue.size() == 0)
			return true;
		return false;
	}
	
	private EventSemaphore remove(ArrayList<EventSemaphore> queue){
		return queue.remove(0);
	}
	
	private void insert(ArrayList<EventSemaphore> queue, EventSemaphore sem){
		queue.add(queue.size(), sem);
	}
	
	private synchronized boolean isBusy(EventSemaphore sem){
		print("LOCK");
		if(busy){
			// If the lock is busy we need to insert the semaphore of the current thread in the queue
			insert(entryQueue, sem);
			print("BLOCKED");
			return true;
		}
		// If the lock is not busy we can continue and we have to change the state of the lock
		busy = true;
		return false;
	}
	
	public void lock() throws InterruptedException{
		EventSemaphore sem = new EventSemaphore();
		if(isBusy(sem)){
			// If the lock is busy we need to block the current thread on the semaphore.
			// If a signal arrives before the block operation we can continue directly.
			sem.block();
			print("AWAKENED");
		}
		print("END LOCK");
	}

	public synchronized void unlock(){
		print("UNLOCK");
		if(!isEmpty(urgentQueue)){
			// If there is at least one blocked thread on urgent queue we need to wake up the first one
			print("WAKES UP URGENT THREAD");
			remove(urgentQueue).unblock();
		}
		else if(!isEmpty(entryQueue)){
			// If there is at least one blocked thread on entry queue we need to wake up the first one
			print("WAKES UP ENTRY THREAD");
			remove(entryQueue).unblock();
		}
		else
			busy = false;
		print("END UNLOCK");
	}
	
	public Condition newCondition(String n){
		return new Condition(this, urgentQueue, n);
	}
}
