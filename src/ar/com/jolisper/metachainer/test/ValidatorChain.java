package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.annotation.StepValidator;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName("stepValidatorChain")
public class ValidatorChain {

	@StepValidator({"validMethod"})
	public boolean validMethodValidator(ChainContext context) {
		return true;
	}
	
	// This step should run and the object 
	// should be added to the context
	@ChainStep(order = 1)
	public void validMethod(ChainContext context) {
		// For assert not null
		context.set("valid method result", new Object());
	}
	
	@StepValidator({"invalidMethod"})
	public boolean invalidMethodValidator(ChainContext context) {
		return false;
	}
	
	// This step should NOT run and the object 
	// should NEVER be added to the context
	@ChainStep(order = 2)
	public void invalidMethod(ChainContext context) {
		// For assert not null
		context.set("invalid method result", new Object());
	}
	
}
