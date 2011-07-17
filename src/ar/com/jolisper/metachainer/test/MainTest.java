package ar.com.jolisper.metachainer.test;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.jolisper.metachainer.core.Chain;
import ar.com.jolisper.metachainer.core.ChainContext;
import ar.com.jolisper.metachainer.core.ChainFactory;
import ar.com.jolisper.metachainer.exception.BreakOnErrorException;
import ar.com.jolisper.metachainer.exception.BreakOnInvalidException;
import ar.com.jolisper.metachainer.exception.ChainError;

/**
 * Main tests
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
public class MainTest {
	
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
	
	@Test
	public void stepValidator() {
			
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create("stepValidatorChain", "ar.com.jolisper.metachainer.test");
			
			ChainContext context = chain.start();
	
			Assert.assertNotNull( context.get("valid method result") );
			Assert.assertNull( context.get("invalid method result") ); 
	}
	
	@Test(expected=ChainError.class)
	public void orderNumberMustBeGreaterThan1() {
			
			ChainFactory factory = ChainFactory.instance();
			factory.create("orderNumberMustBeGreaterThan1Chain", "ar.com.jolisper.metachainer.test");
	}
	
	@Test(expected=ChainError.class)
	public void thereIsAnotherStepWithTheSameOrderNumber() {
			
			ChainFactory factory = ChainFactory.instance();
			factory.create("thereIsAnotherStepWithTheSameOrderNumber", "ar.com.jolisper.metachainer.test");
	}

	@Test(expected=BreakOnErrorException.class)
	public void breakOnErrorsMethod() {

			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create("breakOnErrorsMethodChain", "ar.com.jolisper.metachainer.test");
			
			chain.start();
	}

	@Test(expected=BreakOnErrorException.class)
	public void breakOnErrorsClass() {
			
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create("breakOnErrorsClassChain", "ar.com.jolisper.metachainer.test");
			
			chain.start();
	}

	@Test(expected=BreakOnInvalidException.class)
	public void breakOnInvalidMethod() {
			
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create("breakOnInvalidMethodChain", "ar.com.jolisper.metachainer.test");
			
			chain.start();
	}
	
}
