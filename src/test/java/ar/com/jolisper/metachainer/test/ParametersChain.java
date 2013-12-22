package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName("parametersChain")
public class ParametersChain {
	
	@ChainStep(order = 1)
	public void addOneToParameterOne(ChainContext context) {
		Integer parameter1 = (Integer) context.get("parameter1");

		context.set("parameter1", ++parameter1);
	}
	
	@ChainStep(order = 2)
	public void addPostfixToParameterTwo(ChainContext context) {
		String parameter2 = (String) context.get("parameter2");
		String newString = parameter2 + "Postfix";
		
		context.set("parameter2", newString);
	}
	
}
