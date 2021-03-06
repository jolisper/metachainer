package ar.com.jolisper.metachainer.test;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.jolisper.metachainer.core.Chain;
import ar.com.jolisper.metachainer.core.ChainContext;
import ar.com.jolisper.metachainer.core.ChainFactory;
import ar.com.jolisper.metachainer.exception.BreakOnErrorException;
import ar.com.jolisper.metachainer.exception.BreakOnInvalidException;
import ar.com.jolisper.metachainer.exception.ChainError;
import ar.com.jolisper.metachainer.utils.ChainDiscovery;

/**
 * Main tests
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
public class MainTest {
	
	@Test
	public void parameters() {
		
		ChainFactory factory = ChainFactory.instance();
		
		Chain chain = factory.create(ParametersChain.class);
		
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
			Chain chain = factory.create(EnsureChain.class);
			ChainContext context = chain.start();
			
			Assert.assertTrue(chain.fail());
			Assert.assertEquals("Ensure method run!", (String) context.get("ensure")); 
	}
	
	@Test
	public void stepValidator() {
			
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create(ValidatorChain.class);
			
			ChainContext context = chain.start();
	
			Assert.assertNotNull( context.get("valid method result") );
			Assert.assertNull( context.get("invalid method result") ); 
	}
	
	@Test(expected=ChainError.class)
	public void orderNumberMustBeGreaterThan1() {
			
			ChainFactory factory = ChainFactory.instance();
			factory.create(OrderNumberMustBeGreaterThan1Chain.class);
	}
	
	@Test(expected=ChainError.class)
	public void thereIsAnotherStepWithTheSameOrderNumber() {
			
			ChainFactory factory = ChainFactory.instance();
			factory.create(ThereIsAnotherStepWithTheSameOrderNumber.class);
	}

	@Test
	public void breakOnErrorsMethod() {

			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create(BreakOnErrorsMethodChain.class);
			
			chain.start();
			
			Assert.assertTrue(chain.fail());
			Assert.assertNotNull(chain.getContext().get("chainException"));
			Assert.assertTrue(chain.getContext().get("chainException") instanceof BreakOnErrorException);
	}

	@Test
	public void breakOnErrorsClass() {
			
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create(BreakOnErrorsClassChain.class);
			
			chain.start();
			
			Assert.assertTrue(chain.fail());
			Assert.assertNotNull(chain.getContext().get("chainException"));
			Assert.assertTrue(chain.getContext().get("chainException") instanceof BreakOnErrorException);
	}

	@Test
	public void breakOnInvalidMethod() {
			
			ChainFactory factory = ChainFactory.instance();
			Chain chain = factory.create(BreakOnInvalidMethodChain.class);
			
			chain.start();
			
			Assert.assertTrue(chain.fail());
			Assert.assertNotNull(chain.getContext().get("chainException"));
			Assert.assertTrue(chain.getContext().get("chainException") instanceof BreakOnInvalidException);
			Assert.assertNull(chain.getContext().get("Step2"));
	}

	@Test
	public void byPassOnInvalidMethod() { 
		ChainFactory factory = ChainFactory.instance();
		Chain chain = factory.create(BypassOnInvalidMethodChain.class);
		
		chain.start();
		
		Assert.assertNotNull(chain.getContext().get("Step1"));
		Assert.assertNull(chain.getContext().get("Step2"));
		Assert.assertNotNull(chain.getContext().get("Step3"));
	}
	
	@Test
	public void findChainByName() { 
		Assert.assertNotNull( ChainDiscovery.findByName("stepValidatorChain") );
	}
	
}
