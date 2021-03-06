package ar.com.jolisper.metachainer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The type annotated with this annotation is a chain.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainName {
	
	public String value();
	public boolean breakOnErrors() default false;

}
