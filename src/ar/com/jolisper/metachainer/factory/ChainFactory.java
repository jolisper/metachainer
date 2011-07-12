package ar.com.jolisper.metachainer.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import org.reflections.Reflections;

import ar.com.jolisper.metachainer.annotations.ChainBean;
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

	@SuppressWarnings("unchecked")
	public Chain create(String chainName, String chainPackage) {
		
	     Reflections reflections = new Reflections(chainPackage);
	     Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ChainBean.class);
	     
	     Class chainClass = null;
	     
	     for (Class clazz : annotated) {
	    	 Annotation annotation = clazz.getAnnotation(ChainBean.class);
	    	 String name = ((ChainBean) annotation).name();
	    	 if (name.equals(chainName)) {
	    		 chainClass = clazz;
	    		 break;
	    	 }
	     }

	     if (chainClass == null) {
	    	 throw new RuntimeException("Chain not found: chainName = " + chainName);
	     }
	     
	     Object chainObject = null;
	     try {
	    	 chainObject = chainClass.newInstance();
	     } catch (InstantiationException e1) {
	    	 // TODO Auto-generated catch block
	    	 e1.printStackTrace();
	     } catch (IllegalAccessException e1) {
	    	 // TODO Auto-generated catch block
	    	 e1.printStackTrace();
	     }
	     
	     Method[] methods = chainClass.getDeclaredMethods();
	     
	     try {
			for (Method method : methods) {
				 if (method.getAnnotation(ChainStep.class) != null) {
					 //System.out.println("method founded!");
					 method.invoke(chainObject, new HashMap<String, Object>());
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
	     
	     return new Chain();
	}
	
}
