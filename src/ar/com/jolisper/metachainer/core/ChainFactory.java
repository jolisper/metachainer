package ar.com.jolisper.metachainer.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import ar.com.jolisper.metachainer.annotation.ChainEnsure;
import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.annotation.ChainStep;
import ar.com.jolisper.metachainer.annotation.StepValidator;
import ar.com.jolisper.metachainer.exception.ChainError;

/**
 * Chain Factory
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
public class ChainFactory {
	
	/* Singleton */
	private static ChainFactory instance = new ChainFactory();
	
	private ChainFactory() {}
	
	public static ChainFactory instance() {
		return instance;
	}
	/* ********* */
	
	/**
	 * Create a chain by name and package
	 */
	public Chain create(String chainName, String chainPackage) {

		Reflections reflections = new Reflections(chainPackage);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ChainName.class);

		Class<?> chainClass = null;

		for (Class<?> clazz : annotated) {
			Annotation annotation = clazz.getAnnotation(ChainName.class);
			String name = ((ChainName) annotation).value();
			if (name.equals(chainName)) {
				chainClass = clazz;
				break;
			}
		}

		if (chainClass == null) {
			throw new RuntimeException("Chain not found: chainName = " + chainName);
		}

		Object chainInstance = null;
		try {
			chainInstance = chainClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error on chain instantiation");
		}

		Method[] methods = chainClass.getDeclaredMethods();

		List<Method> steps = new ArrayList<Method>();
		Map<String, List<Method>> validators = new HashMap<String, List<Method>>();
		Method ensure = null;

		StepValidator stepValidator = null;
		for (Method method : methods) {
			if (method.getAnnotation(ChainStep.class) != null) {
				steps.add(method);
			}
			
			if (method.getAnnotation(ChainEnsure.class) != null) {
				ensure = method;
			}
			
			if ( (stepValidator = method.getAnnotation(StepValidator.class) ) != null) {
				String[] associatedMethods = stepValidator.value();
				
				for (String methodName : associatedMethods) {
					if (validators.get(methodName) != null) {
						validators.get(methodName).add(method);
					} else {
						List<Method> validatorMethods = new ArrayList<Method>();
						validatorMethods.add(method);
						validators.put(methodName, validatorMethods);
					}
				}
				
			}
		}
		
		validateOrder(steps);
		
		sort(steps);

		return new Chain(chainInstance, steps, validators, ensure, createContext());
	}
	
	/**
	 * Validate step orders
	 * @param steps
	 */
	private void validateOrder(List<Method> steps) {
		
		List<Integer> orders = new ArrayList<Integer>();
		
		for (Method step : steps) {
			Integer stepOrder = step.getAnnotation(ChainStep.class).order();
			
			if (step.getAnnotation(ChainStep.class).order() < 1) {
				throw new ChainError("The order number must be greater than 1, step = " + step.getName());
			}

			if (orders.contains(stepOrder)) {
				throw new ChainError("There is another step with the same order number, step = " + step.getName());
			}
			
			orders.add(stepOrder);
		}
		
	}

	/** 		 
	 * Sort steps by order annotation attribute
	 * @param stepsList
	 */
	private void sort(List<Method> stepsList) {

	 	Collections.sort(stepsList, new Comparator<Method>() {

			@Override
			public int compare(Method m1, Method m2) {
				
				int method1order = m1.getAnnotation(ChainStep.class).order();
				int method2order = m2.getAnnotation(ChainStep.class).order();
				
				if (method1order > method2order) {
					return 1;
				}

				if (method1order < method2order) {
					return -1;
				}
				
				//TODO: Its not valid that two diferents  steps have the same order , add validation 
				return 0;
			}
	 	});
	}
	
	/**
	 * Create an empty chain context
	 * @return
	 */
	public ChainContext createContext() {
		return new ChainContext();
	}
	
}
