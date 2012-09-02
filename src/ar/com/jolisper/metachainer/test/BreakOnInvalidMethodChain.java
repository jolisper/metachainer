package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.annotation.StepValidator;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName("breakOnInvalidMethodChain")
public class BreakOnInvalidMethodChain {

	@StepValidator({"invalidMethod"})
	public boolean invalidMethodValidator(ChainContext context) {
		return false;
	}
	
	// This step should NOT run
	@ChainStep(order = 1, breakOnInvalid = true)
	public void invalidMethod(ChainContext context) {
	}
	
	// This step should NOT run
	@ChainStep(order = 2)
	public void nextMethod(ChainContext context) {
		context.set("Step2", "I never should be here");
	}

}
