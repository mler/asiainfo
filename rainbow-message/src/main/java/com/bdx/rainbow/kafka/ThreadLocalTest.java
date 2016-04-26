package com.bdx.rainbow.kafka;


public class ThreadLocalTest {

	public ThreadLocalTest() {

	}

	ThreadLocal<Content> tl = new ThreadLocal<Content>();

	void start() {
		System.out.println("begin");
		Content content = tl.get();
		if (content == null) {
			tl.set(new Content());
		}
		System.out.println("try to release content data");
		// tl.set(null);//@1
		tl.remove();//@2
//		tl = null;// @3
//		content = null;// @4
		System.out.println("request gc");
		System.gc();
		System.out.println(tl.get());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}
	
	public final static void main(String[] args)
	
	{
		
		ThreadLocalTest test = new ThreadLocalTest();
		
		test.start();
	}
}

class Content {
	byte data[] = new byte[1024 * 1024 * 10];

	protected void finalize() {
		System.out.println("I am released");
	}
}