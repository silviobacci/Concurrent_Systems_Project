package assignment1;

import java.util.ArrayList;

public class Condition {
	private FairLock lock;
	private ArrayList<EventSemaphore> conditionQueue, urgentQueue;
	String name;

	public Condition(FairLock fl, ArrayList<EventSemaphore> uq, String n){
		lock = fl;
		conditionQueue = new ArrayList<EventSemaphore>();
		urgentQueue = uq;
		name = n;
	}
	
	private void print(String s){
		System.out.println(Thread.currentThread().getName() + ": CONDITION " + name + " - " + s);
	}
	
	public boolean isEmpty(){
		if(conditionQueue.size() == 0)
			return true;
		return false;
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

	public void await() throws InterruptedException{		
		EventSemaphore sem = new EventSemaphore();
		
		// We have to wait on a condition queue
		insert(conditionQueue, sem);
		
		// We need to release the lock or to awake another thread (urgent threads have the precedence)
		lock.unlock();
		sem.block();
		print("AWAKENED FROM CONDITION QUEUE");
	}
	
	public void signal() throws InterruptedException{	
		if(!isEmpty(conditionQueue)){
			// If at least one thread is blocked on the condition queue we have to wake up the first one
			EventSemaphore sem = new EventSemaphore();
			
			// We have to wait on a urgent queue
			insert(urgentQueue, sem);
			
			// Waking up the first thread blocked on the urgent queue
			remove(conditionQueue).unblock();
			sem.block();
			print("AWAKENED FROM URGENT QUEUE");
		}
	}
}
