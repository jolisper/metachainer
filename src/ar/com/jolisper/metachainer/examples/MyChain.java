package ar.com.jolisper.metachainer.examples;

import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;
import ar.com.jolisper.metachainer.factory.ChainContext;

@ChainName("myChain")
public class MyChain {
	
	@ChainStep(order = 1)
	public void stepOne(ChainContext context) {
		
		String param = (String) context.get("first key");
		
		System.out.println("step one executed! " + "first param = " + param);
	}

	@ChainStep(order = 2)
	public void stepTwo(ChainContext context) {
		
		String param = (String) context.get("second key");
		
		System.out.println("step two executed! " + "second param = " + param);
		
		throw new RuntimeException("ERROR!!!");
	}
	
}
