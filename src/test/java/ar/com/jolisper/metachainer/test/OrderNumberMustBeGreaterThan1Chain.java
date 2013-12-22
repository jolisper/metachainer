package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName("orderNumberMustBeGreaterThan1Chain")
public class OrderNumberMustBeGreaterThan1Chain {

	@ChainStep(order=0)
	public void orderNumberMustBeGreaterThan1(ChainContext context) {}
	
}
