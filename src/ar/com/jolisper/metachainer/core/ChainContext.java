package ar.com.jolisper.metachainer.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the execution context of chains.
 * Contain all objects necessary for the execution.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 * 
 */
public class ChainContext {
	
	private Map<String, Object> context;
	
	public ChainContext() {
		context = new HashMap<String, Object>();
	}
	
	public Object get(String key) {
		return context.get(key);
	}
	
	public void set(String key, Object value) {
		context.put(key, value);
	}
}
