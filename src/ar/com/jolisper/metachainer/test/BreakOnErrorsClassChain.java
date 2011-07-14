package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;
import ar.com.jolisper.metachainer.chain.ChainContext;

@ChainName(value ="breakOnErrorsClassChain", breakOnErrors = true)
public class BreakOnErrorsClassChain {
		
		@ChainStep(order = 1)
		public void throwException(ChainContext context) {
			throw new RuntimeException("");
		}

}
