package com.bdx.rainbow.etl;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.bdx.rainbow.etl.pool.EtlTheadPoolFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class CallTwo implements Callable<String>
{
	public static CompletionService<String> completionService = new ExecutorCompletionService<String>(EtlTheadPoolFactory.getPoolInstance(),new LinkedBlockingQueue<Future<String>>());
	
	private String name;
	
	public CallTwo(String name) {
		super();
		this.name = name;
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		
//		Thread.sleep(1000);
		
		throw new InterruptedException("打断[InterruptedException]");
	}
	
	public final static void main(String[] args) throws Exception
	{
		CallTwo t = new CallTwo("mler");
		
		completionService.submit(t);
		
		try {
			String pages =  completionService.take().get();
			System.out.println("ok");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("1111 InterruptedException");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("1111 ExecutionException");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("11111 Exception");
		}
		
	}
	
}