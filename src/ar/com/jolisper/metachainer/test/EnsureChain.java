package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotations.ChainEnsure;
import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;
import ar.com.jolisper.metachainer.chain.ChainContext;

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
