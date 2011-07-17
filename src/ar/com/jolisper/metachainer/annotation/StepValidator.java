package ar.com.jolisper.metachainer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark a method as a validator of one or more chain methods.
 * The annotated method must return a boolean value.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StepValidator {
	
	public String[] value();

}
