package assignment2_1;

import java.util.ArrayList;
import java.util.concurrent.locks.*;
import assignment2_0.Resource;

public class ResourceManagerJavaLock {
	private final int b = 0, a1 = 1, a2 = 2, NUM_THREADS = 3;
	private Resource resource;
	private boolean busy;
	private ArrayList<Integer> aBlocked, bBlocked;
	private Condition cond[];
	private Lock lock;

	public ResourceManagerJavaLock(){
		resource = new Resource();
		busy = false;
		lock = new ReentrantLock(true);
		
		cond = new Condition[NUM_THREADS];
		for(int i = 0; i < NUM_THREADS; i++) cond[i] = lock.newCondition();
		
		aBlocked = new ArrayList<Integer>();
		bBlocked = new ArrayList<Integer>();
	}
	
	private void print(String s){
		System.out.println(Thread.currentThread().getName() + ": RESOURCE MANAGER - " + s);
	}
	
	private boolean isEmpty(ArrayList<Integer> queue){
		if(queue.size() == 0)
			return true;
		return false;
	}
	
	private Integer remove(ArrayList<Integer> queue){
		return queue.remove(0);
	}
	
	private boolean iAmInQueue(ArrayList<Integer> queue, Integer me){
		for(int i = 0; i < queue.size(); i++)
			if(me == queue.get(i))
				return true;
		return false;
	}
	
	private void insert(ArrayList<Integer> queue, Integer id){
		queue.add(queue.size(), id);
	}
	
	private int whoIs(String name){
		if(name.equals("A1"))
			return a1;
		if(name.equals("A2"))
			return a2;
		return b;
	}
	
	public Resource request() throws InterruptedException{
		lock.lock();
		try{
			print("REQUEST ");
			if(busy){
				// We need to know which thread we are
				int me = whoIs(Thread.currentThread().getName());
				if(me == b){
					print("BLOCK ON CONDITION B");
					// Insert the "thread id" in the queue
					insert(bBlocked, me);
					// While we are in the queue no one woke up us and so we need to block the thread again
					while(iAmInQueue(bBlocked, me)){ cond[me].await(); }
				}
				else{
					print("BLOCK ON CONDITION A");
					insert(aBlocked, me);
					while(iAmInQueue(aBlocked, me)){ cond[me].await(); }
				}
			}
			else
				busy = true;
			print("RESOURCE ASSIGNED TO ME ");
		}
		finally{ lock.unlock(); }
		return resource;
	}
	
	public void release(){
		lock.lock();
		try{
			print("RESOURCE RELEASED");
			if(!isEmpty(bBlocked))
				cond[remove(bBlocked)].signal();
			else if(!isEmpty(aBlocked))
				cond[remove(aBlocked)].signal();
			else
				busy = false;
		}
		finally{ lock.unlock(); }
	}
}
