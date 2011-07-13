package ar.com.jolisper.metachainer.factory;

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
	private List<Method> methodList;
	private ChainContext context;
	private boolean fail;

	public Chain(Object chainInstance, List<Method> methodList, ChainContext context) {
		this.chainInstance = chainInstance;
		this.methodList = methodList;
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
			for (Method method : methodList) {
				currentMethod = method;
				if (method.getAnnotation(ChainStep.class).active()) {
					method.invoke(chainInstance, context);
				}
			}
		} catch (Throwable t) {
			this.setFailOn();
			ChainError chainError = new ChainError(t.getMessage(), t);
			chainError.setChainName(currentMethod.getDeclaringClass().getAnnotation(ChainName.class).value());
			chainError.setMethodName(currentMethod.getName());
			chainError.setMethodOrder(currentMethod.getAnnotation(ChainStep.class).order());
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
