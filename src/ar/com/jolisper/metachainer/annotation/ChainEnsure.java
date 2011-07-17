package ar.com.jolisper.metachainer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The method annotated with this annotation run when the chain throws exception.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainEnsure {
	
}
