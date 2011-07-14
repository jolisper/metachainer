package ar.com.jolisper.metachainer.chain;

import java.lang.reflect.Method;
import java.util.List;

import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;

/**
 * Represents the chain itself
 * @author jperez
 *
 */
public class Chain {
	
	private Object chainInstance;
	private List<Method> steps;
	private List<Method> activators;
	private Method ensure;
	private ChainContext context;
	private boolean fail;

	public Chain(Object chainInstance, List<Method> steps, List<Method> activators, Method ensure, ChainContext context) {
		this.chainInstance = chainInstance;
		this.steps = steps;
		this.activators = activators;
		this.ensure = ensure;
		this.context = context;
		this.setFailOff();
	}
	
	/**
	 * Start chain execution
	 * @param context execution context
	 * 
	 * @return
	 */
	public ChainContext start() {
		Method currentMethod = null;
		try {
			// Main loop
			for (Method method : steps) {
				currentMethod = method;
				if (method.getAnnotation(ChainStep.class).active()) {
					method.invoke(chainInstance, context);
				}
			}
		} catch (Throwable t) {
			this.setFailOn();
			
			// Run the ensure method
			try {
				if (ensure != null) {
					ensure.invoke(chainInstance, context);
				}
			} catch (Throwable et) {
				throw new RuntimeException(et);
			} 
			
			boolean breakOnErrorsMethod = currentMethod.getAnnotation(ChainStep.class).breakOnErrors();
			boolean breakOnErrorsClass = currentMethod.getDeclaringClass().getAnnotation(ChainName.class).breakOnErrors();
			
			if (breakOnErrorsClass || breakOnErrorsMethod) {
				ChainError chainError = new ChainError(t.getMessage(), t);
				chainError.setChainName(currentMethod.getDeclaringClass().getAnnotation(ChainName.class).value());
				chainError.setMethodName(currentMethod.getName());
				chainError.setMethodOrder(currentMethod.getAnnotation(ChainStep.class).order());
				chainError.setContext(context);

				throw chainError;
			}
		}
		
		return context;
	}

	public Chain setParameter(String key, Object value) {
		getContext().set(key, value);
		return this;
	}
	
	public ChainContext getContext() {
		return context;
	}
	
	public boolean fail() {
		return this.fail;
	}
	
	private void setFailOn() {
		this.fail = true;
	}

	private void setFailOff() {
		this.fail = false;
	}
	
}
