package ar.com.jolisper.metachainer.exception;

/**
 * This exception is throw if a step validator
 * returns false and breakOnInvalid is set to true.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
public class BreakOnInvalidException extends ChainError {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4718341478549928376L;

	public BreakOnInvalidException(String msg) {
		super(msg);
	}
	
	public BreakOnInvalidException(String msg, Throwable t) {
		super(msg, t);
	}

}
