package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.factory.Chain;
import ar.com.jolisper.metachainer.factory.ChainFactory;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChainFactory factory = ChainFactory.instance();
		
		Chain chain = factory.create("myChain", "ar.com.jolisper.metachainer.examples");
		
		chain.setParameter("first key", "first param")
			.setParameter("second key", "second param")
			.start();
		
		/*
		ChainError error = null;
		
		if ((error = chain.getError()) != null) {
			StringBuffer msg = new StringBuffer();
			msg.append("chainName="+error.getChainName()+":");
			msg.append("chainMethod="+error.getMethodName()+":");
			msg.append("chainOrder="+error.getMethodOrder()+":");
			msg.append("msg="+error.getMessage());
			System.out.println(msg.toString());
		}
		*/
	}

}
