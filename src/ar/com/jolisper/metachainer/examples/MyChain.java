package ar.com.jolisper.metachainer.examples;

import java.util.Map;

import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;

@ChainName("myChain")
public class MyChain {
	
	@ChainStep(order = 1)
	public void stepOne(Map<String, Object> params) {
		
		String param = (String) params.get("first key");
		
		System.out.println("step one executed! " + "first param = " + param);
	}

	@ChainStep(order = 2)
	public void stepTwo(Map<String, Object> params) {
		
		String param = (String) params.get("second key");
		
		System.out.println("step two executed! " + "second param = " + param);
	}
	
}
