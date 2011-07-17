package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName(value = "thereIsAnotherStepWithTheSameOrderNumber")
public class ThereIsAnotherStepWithTheSameOrderNumber {
	
	@ChainStep(order=1)
	public void step1(ChainContext context) {}
	
	@ChainStep(order=1)
	public void step2(ChainContext context) {}
}
