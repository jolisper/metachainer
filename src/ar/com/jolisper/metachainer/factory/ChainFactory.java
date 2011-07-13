package ar.com.jolisper.metachainer.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import ar.com.jolisper.metachainer.annotations.ChainName;
import ar.com.jolisper.metachainer.annotations.ChainStep;

/**
 * Chain Factory
 * @author jorge
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

	@SuppressWarnings("unchecked")
	public Chain create(String chainName, String chainPackage) {

		Reflections reflections = new Reflections(chainPackage);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ChainName.class);

		Class chainClass = null;

		for (Class clazz : annotated) {
			Annotation annotation = clazz.getAnnotation(ChainName.class);
			String name = ((ChainName) annotation).value();
			if (name.equals(chainName)) {
				chainClass = clazz;
				break;
			}
		}

		if (chainClass == null) {
			throw new RuntimeException("Chain not found: chainName = "
					+ chainName);
		}

		Object chainInstance = null;
		try {
			chainInstance = chainClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error on chain instantiation");
		}

		Method[] methods = chainClass.getDeclaredMethods();

		List<Method> methodList = new ArrayList<Method>();

		for (Method method : methods) {
			if (method.getAnnotation(ChainStep.class) != null) {
				methodList.add(method);
			}
		}

		sort(methodList);

		return new Chain(chainInstance, methodList, createContext());
	}
	
	/** 		 
	 * Order methods by order annotation attribute
	 * @param methodList
	 */
	private void sort(List<Method> methodList) {

	 	Collections.sort(methodList, new Comparator<Method>() {

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
