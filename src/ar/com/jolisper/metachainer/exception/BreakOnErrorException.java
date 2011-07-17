package ar.com.jolisper.metachainer.exception;

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
