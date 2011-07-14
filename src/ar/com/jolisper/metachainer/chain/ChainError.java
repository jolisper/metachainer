package ar.com.jolisper.metachainer.chain;

public class ChainError extends RuntimeException {

	private static final long serialVersionUID = 7243535258262914708L;
	private String chainName;
	private String methodName;
	private int methodOrder;
	private ChainContext context;

	public ChainError(String msg, Throwable t) {
		super(msg, t);
	}

	public void setChainName(String chainName) {
		this.chainName = chainName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void setMethodOrder(int methodOrder) {
		this.methodOrder = methodOrder;
	}

	public String getChainName() {
		return chainName;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getMethodOrder() {
		return methodOrder;
	}

	public void setContext(ChainContext context) {
		this.context = context;
	}

	public ChainContext getContext() {
		return context;
	}
	
}
