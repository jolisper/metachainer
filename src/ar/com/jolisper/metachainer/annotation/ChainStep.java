package ar.com.jolisper.metachainer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method that have this annotation is a chain step. 
 * Every chain step needs an order number, optionally 
 * "break on errors" and "break on invalid" attributes 
 * can be set to true.
 * The annotated method must receive only a single 
 * parameter of ChainContext type.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainStep {
	
	public int order();
	public boolean active() default true;
	public boolean breakOnErrors() default false;
	public boolean breakOnInvalid() default false;
	public boolean bypassOnInvalid() default false;
	
}
