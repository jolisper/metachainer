package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName("breakOnErrorsMethodChain")
public class BreakOnErrorsMethodChain {
	
	@ChainStep(order = 1, breakOnErrors = true)
	public void throwException(ChainContext context) {
		throw new RuntimeException("");
	}
	
}
