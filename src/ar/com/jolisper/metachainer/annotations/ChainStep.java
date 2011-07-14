package ar.com.jolisper.metachainer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Chain step annotation
 * @author jolisper
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChainStep {
	
	public int order();
	public boolean active() default true;
	public boolean breakOnErrors() default false;

}
