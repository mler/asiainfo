package com.bdx.rainbow.etl.pool;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolConfig {

	private static ReentrantLock lock = new ReentrantLock();
	
	public static void test1() throws InterruptedException
	{
		lock.lock();
		
		System.out.println("test1 sleep 10000ms");
		Thread.sleep(10000);
		
		lock.unlock();
	}
	
	public static void test2() throws InterruptedException
	{
		lock.lock();
		
		System.out.println("test2 sleep 10000ms");
		Thread.sleep(10000);
		
		lock.unlock();
	}
	
	
	public final static void main(String[] args) throws Exception
	{
		Thread t1 = new Thread( new Runnable() {
			
			@Override
			public void run() {
				try {
					ThreadPoolConfig.test1();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		Thread t2 = new Thread( new Runnable() {
			
			@Override
			public void run() {
				try {
					ThreadPoolConfig.test2();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		t1.start();
		t2.start();
	}
}
