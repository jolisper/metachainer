package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.annotation.StepValidator;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName("bypassOnInvalidMethodChain")
public class BypassOnInvalidMethodChain {

	@StepValidator({"invalidMethod"})
	public boolean invalidMethodValidator(ChainContext context) {
		return false;
	}

	@ChainStep(order = 1)
	public void stepOneMethod(ChainContext context) {
		context.set("Step1", "");
	}
	
	// This step should NOT run
	@ChainStep(order = 2, bypassOnInvalid = true)
	public void invalidMethod(ChainContext context) {
		context.set("Step2", "I should never be here");
	}
	
	@ChainStep(order = 3)
	public void stepThreeMethod(ChainContext context) {
		context.set("Step3", "");
	}
	
}
