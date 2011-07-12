package ar.com.jolisper.metachainer.examples;

import java.util.HashMap;

import ar.com.jolisper.metachainer.annotations.ChainBean;
import ar.com.jolisper.metachainer.annotations.ChainEnd;
import ar.com.jolisper.metachainer.annotations.ChainInit;
import ar.com.jolisper.metachainer.annotations.ChainStep;

@ChainBean(name = "myChain")
public class MyChain {
	
	@ChainInit
	public void init(HashMap<String, Object> req) { } 

	@ChainStep(order = 1)
	public void stepOne(HashMap<String, Object> req) {
		System.out.println("step one executed!");
	}

	@ChainStep(order = 2)
	public void stepTwo(HashMap<String, Object> req) {
		System.out.println("step two executed!");
	}
	
	@ChainEnd
	public void end(HashMap<String, Object> req) { } 
	
}
