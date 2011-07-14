package ar.com.jolisper.metachainer.test;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.jolisper.metachainer.chain.Chain;
import ar.com.jolisper.metachainer.chain.ChainContext;
import ar.com.jolisper.metachainer.chain.ChainFactory;

/**
 * Main tests
 * @author jperez
 *
 */
public class ChainTest {
	
	@Test
	public void parameters() {
		
		ChainFactory factory = ChainFactory.instance();
		
		Chain chain = factory.create("parametersChain", "ar.com.jolisper.metachainer.test");
		
		ChainContext context =
		chain.setParameter("parameter1", 1)
			.setParameter("parameter2", "PrefixString")
			.start();
		
		Assert.assertEquals(new Integer(2), ((Integer) context.get("parameter1"))); 
		Assert.assertEquals("PrefixStringPostfix", ((String) context.get("parameter2")));
	}
	
	@Test
	public void ensureMethod() {
		
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create("ensureChain", "ar.com.jolisper.metachainer.test");
			ChainContext context = chain.start();
			
			Assert.assertTrue(chain.fail());
			Assert.assertEquals("Ensure method run!", (String) context.get("ensure")); 
	}
	
	@Test(expected=ar.com.jolisper.metachainer.chain.ChainError.class)
	public void breakOnErrorsMethod() {
		
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create("breakOnErrorsMethodChain", "ar.com.jolisper.metachainer.test");
			
			chain.start();
	}

	@Test(expected=ar.com.jolisper.metachainer.chain.ChainError.class)
	public void breakOnErrorsClass() {
		
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create("breakOnErrorsClassChain", "ar.com.jolisper.metachainer.test");
			
			chain.start();
	}
	
}
