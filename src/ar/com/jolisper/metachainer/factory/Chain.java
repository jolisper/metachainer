package ar.com.jolisper.metachainer.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import ar.com.jolisper.metachainer.annotations.ChainStep;

/**
 * Represents the chain itself
 * @author jperez
 *
 */
public class Chain {
	
	private Object chainInstance;
	private List<Method> methodList;

	public Chain(Object chainInstance, List<Method> methodList) {
		this.chainInstance = chainInstance;
		this.methodList = methodList;
	}

	public void start(Map<String, Object> params) {
	 	try {
			for (Method method : methodList) {
				 if (method.getAnnotation(ChainStep.class) != null) {
					 method.invoke(chainInstance, params);
				 }
			 }
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
