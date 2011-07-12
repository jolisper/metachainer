package ar.com.jolisper.metachainer.test;

import ar.com.jolisper.metachainer.factory.ChainFactory;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChainFactory factory = ChainFactory.instance();
		
		factory.create("myChain", "ar.com.jolisper.examples");

	}

}
