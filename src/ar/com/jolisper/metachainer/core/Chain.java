package ar.com.jolisper.metachainer.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.exception.BreakOnErrorException;
import ar.com.jolisper.metachainer.exception.BreakOnInvalidException;
import ar.com.jolisper.metachainer.exception.ChainError;

/**
 * Represents the chain itself.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
public class Chain {
	
	private Object chainInstance;
	private List<Method> steps;
	private Map<String, List<Method>> validators;
	private Method ensure;
	private ChainContext context;
	private boolean fail;

	public Chain(
			Object chainInstance, 
			List<Method> steps, 
			Map<String, 
			List<Method>> validators, 
			Method ensure, 
			ChainContext context
			) {
		this.chainInstance = chainInstance;
		this.steps = steps;
		this.validators = validators;
		this.ensure = ensure;
		this.context = context;
		this.setFailOff();
		
		try {
			Field chainField = ChainContext.class.getDeclaredField("chain");
			chainField.setAccessible(true);
			chainField.set(context, this);
		} catch (Exception e) {
		}
		
	}
	
	/**
	 * Start chain execution
	 * @param context execution context
	 * 
	 * @return
	 */
	public ChainContext start() {
		Method currentStep = null;
		try {
			// FIXME  this main loop needs refactoring
			// Main loop
			for (Method step : steps) {
				currentStep = step;
				ChainStep stepMetadata = step.getAnnotation(ChainStep.class);
				if (stepMetadata.active()) {
					if (validate(step, chainInstance, context)) {
						step.invoke(chainInstance, context);
					} else if (stepMetadata.breakOnInvalid()) {
						setFailOn();
						ChainName chainMetadata = 
							currentStep.getDeclaringClass().getAnnotation(ChainName.class);
						BreakOnInvalidException boi = 
							new BreakOnInvalidException("The step " + step.getName() + " is invalid!");
						boi.setChainName(chainMetadata.value());
						boi.setStepName(currentStep.getName());
						boi.setStepOrder(stepMetadata.order());
						boi.setContext(context);
						context.set("chainException", boi);
						break;
					} else if (stepMetadata.bypassOnInvalid()) {
						continue;
					}
				}
			}
		} catch (InvocationTargetException ite) {
			setFailOn();
			// Run the ensure method
			try {
				if (ensure != null) {
					ensure.invoke(chainInstance, context);
				}
			} catch (Throwable et) {
				throw new ChainError("Error on ensure method", et);
			} 
			
			ChainName chainMetadata = 
				currentStep.getDeclaringClass().getAnnotation(ChainName.class);
			ChainStep stepMetadata =  
				currentStep.getAnnotation(ChainStep.class);
			
			// Break the execution if its 
			boolean breakOnErrorsClass = chainMetadata.breakOnErrors();
			boolean breakOnErrorsMethod = 
				currentStep.getAnnotation(ChainStep.class).breakOnErrors();
			
			if (breakOnErrorsClass || breakOnErrorsMethod) {
				BreakOnErrorException boe = new BreakOnErrorException(ite.getMessage(), ite);
				boe.setChainName(chainMetadata.value());
				boe.setStepName(currentStep.getName());
				boe.setStepOrder(stepMetadata.order());
				boe.setContext(context);
				context.set("chainException", boe);
			}
		} catch (IllegalAccessException iae) {
			throw new ChainError(iae.getMessage(), iae); 
		} catch (IllegalArgumentException iarge) {
			throw new ChainError(
					"Step methods must receive only one parameter of ContextChain type: " 
					+ iarge.getMessage(), iarge);
		}
		
		return context;
	}
	
	/**
	 * Running method validations.
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	private boolean validate(
			Method step, 
			Object chainInstance, 
			ChainContext context) 
		throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String methodName = step.getName();
		
		List<Method> stepValidators = validators.get(methodName);
		
		// If the method is not validated, the method is valid
		if (stepValidators == null || stepValidators.size() == 0) {
			return true;
		}
		
		for (Method validator : stepValidators) {
			if (! (Boolean) validator.invoke(chainInstance, context) ) {
				return false;
			}
		}
		
		return true;
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
