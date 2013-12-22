package ar.com.jolisper.metachainer.exception;

/**
 * This exception is throw if an invocation
 * error occurs and breakOnErrors is set to true.
 * 
 * @author Jorge Luis Pérez (jolisper@gmail.com)
 *
 */
public class BreakOnErrorException extends ChainError {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6411015475152478756L;

	public BreakOnErrorException(String msg) {
		super(msg);
	}
	
	public BreakOnErrorException(String msg, Throwable t) {
		super(msg, t);
	}


}
