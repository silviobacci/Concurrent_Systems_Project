package assignment3_1;

import java.util.concurrent.locks.*;

public class ResourceManager {
	private int state = 0;
	private final Lock lock = new ReentrantLock();;
	private final Condition condAcquireA = lock.newCondition();
	private final Condition condAcquireB = lock.newCondition();
	private final Condition condEndacquireA = lock.newCondition();
	private final Condition condEndacquireB = lock.newCondition();
	private final Condition condRelease = lock.newCondition();
										//    0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11
	private final int[] nextAcquireA = 		{ 1, -1, -1, -1, -1,  6, -1,  8, -1, 10, 11, -1};
	private final int[] nextAcquireB = 		{ 3, -1, -1, -1, -1,  7,  8, -1, -1, -1, -1, -1};
	private final int[] nextEndacquireA = 	{-1,  5,  6, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	private final int[] nextEndacquireB = 	{-1, -1, -1,  9, 10, -1, -1, -1, -1, -1, -1, -1};
	private final int[] nextRelease = 		{ 0, -1, -1, -1, -1,  0,  1,  3,  4,  0,  1,  2};
	
	private void print(String s){
		System.out.println(Thread.currentThread().getName() + ": " + s);
	}
	
	private void signalAll(){
		if (nextAcquireA[state]!=-1) 	condAcquireA.signalAll();
		if (nextAcquireB[state]!=-1) 	condAcquireB.signalAll();
		if (nextEndacquireA[state]!=-1) condEndacquireA.signalAll();
		if (nextEndacquireB[state]!=-1) condEndacquireB.signalAll();
		if (nextRelease[state]!=-1) 	condRelease.signalAll();
	}
	
	public void acquire_a() throws InterruptedException{
		lock.lock();
		print("ACQUIRE A START");
		try{
			while(nextAcquireA[state] == -1){
				print("BLOCK");
				condAcquireA.await();
			}
			state = nextAcquireA[state];
			signalAll();
			print("ACQUIRE A END");
		}
		finally{ lock.unlock(); }
	}
	
	public void acquire_b() throws InterruptedException{
		lock.lock();
		print("ACQUIRE B START");
		try{
			while(nextAcquireB[state] == -1){
				print("BLOCK");
				condAcquireB.await();
			}
			state = nextAcquireB[state];
			signalAll();
			print("ACQUIRE B END");
		}
		finally{ lock.unlock(); }
	}
	
	public void endacquire_a() throws InterruptedException{
		lock.lock();
		print("ENDACQUIRE A START");
		try{
			while(nextEndacquireA[state] == -1){
				print("BLOCK");
				condEndacquireA.await();
			}
			state = nextEndacquireA[state];
			signalAll();
			print("ENDACQUIRE A END");
		}
		finally{ lock.unlock(); }
	}
	
	public void endacquire_b() throws InterruptedException{
		lock.lock();
		print("ENDACQUIRE B START");
		try{
			while(nextEndacquireB[state] == -1){
				print("BLOCK");
				condEndacquireB.await();
			}
			state = nextEndacquireB[state];
			signalAll();
			print("ENDACQUIRE B END");
		}
		finally{ lock.unlock(); }
	}
	
	public void release() throws InterruptedException{
		lock.lock();
		print("RELEASE START");
		try{
			while(nextRelease[state] == -1){
				print("BLOCK");
				condRelease.await();
			}
			state = nextRelease[state];
			signalAll();
			print("RELEASE END");
		}
		finally{ lock.unlock(); }
	}
}
