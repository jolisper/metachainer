package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainEnsure;
import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName("ensureChain")
public class EnsureChain {

	@ChainStep(order = 1)
	public void throwException(ChainContext context) {
		throw new RuntimeException("");
	}
	
	@ChainEnsure
	public void ensure(ChainContext context) {
		context.set("ensure", "Ensure method run!");
	}
	
}
