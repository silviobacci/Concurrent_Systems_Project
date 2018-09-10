package assignment2_0;

import assignment1.*;

public class ResourceManagerFairLock {
	private Resource resource;
	private boolean busy;
	private Condition a, b;
	private FairLock lock;

	public ResourceManagerFairLock(){
		resource = new Resource();
		busy = false;
		lock = new FairLock();
		a = lock.newCondition("A");
		b = lock.newCondition("B");
	}
	
	private void print(String s){
		System.out.println(Thread.currentThread().getName() + ": RESOURCE MANAGER - " + s);
	}
	
	private	boolean amIB(){
		// We check the name of the thread in order to understand if it is B or not.
		return Thread.currentThread().getName().equals("B ");
	}
	
	public Resource request() throws InterruptedException{
		lock.lock();
		try{
			print("REQUEST ");
			if(busy){
				// If the resource is busy we have to block the thread.
				// According to the type of the thread we choose the condition on which the thread waits.
				if(amIB()){
					print("BLOCK ON CONDITION B");
					b.await();
				}
				else{
					print("BLOCK ON CONDITION A");
					a.await();
				}
			}
			else
				busy = true;
			print("RESOURCE ASSIGNED TO ME");
		}
		finally{ lock.unlock(); }
		return resource;
	}
	
	public void release() throws InterruptedException{
		lock.lock();
		try{
			print("RESOURCE RELEASED");
			// If a thread of type B is blocked we need to wake up it, otherwise send a signal to an A thread.
			if(!b.isEmpty())
				b.signal();
			else if(!a.isEmpty())
				a.signal();
			else
				// If there are no blocked threads we release the resource
				busy = false;
		}
		finally{ lock.unlock(); }
	}
}
