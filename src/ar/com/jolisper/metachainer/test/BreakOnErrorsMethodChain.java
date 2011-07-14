package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;
import ar.com.jolisper.metachainer.chain.ChainContext;

@ChainName("breakOnErrorsMethodChain")
public class BreakOnErrorsMethodChain {
	
	@ChainStep(order = 1, breakOnErrors = true)
	public void throwException(ChainContext context) {
		throw new RuntimeException("");
	}
	
}
