package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;
import ar.com.jolisper.metachainer.annotations.ChainStepActivator;
import ar.com.jolisper.metachainer.chain.ChainContext;

@ChainName("activatorChain")
public class ActivatorChain {
	
	@ChainStepActivator({"unactiveMethod"})
	public boolean activator() {
		return false;
	}
	
	@ChainStep(order = 1)
	public void unactiveMethod(ChainContext context) {
		context.set("unactive method result", new Object());
	}

}
