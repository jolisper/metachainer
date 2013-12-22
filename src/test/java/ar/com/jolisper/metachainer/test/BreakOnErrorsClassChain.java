package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.core.ChainContext;

@ChainName(value ="breakOnErrorsClassChain", breakOnErrors = true)
public class BreakOnErrorsClassChain {
		
		@ChainStep(order = 1)
		public void throwException(ChainContext context) {
			throw new RuntimeException("");
		}

}
