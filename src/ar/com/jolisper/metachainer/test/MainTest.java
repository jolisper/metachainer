package ar.com.jolisper.metachainer.test;

import java.util.HashMap;
import java.util.Map;

import ar.com.jolisper.metachainer.factory.Chain;
import ar.com.jolisper.metachainer.factory.ChainFactory;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ChainFactory factory = ChainFactory.instance();
		
		Chain chain = factory.create("myChain", "ar.com.jolisper.metachainer.examples");
		
		Map<String, Object> hashMap = new HashMap<String, Object>() ;
		
		hashMap.put("first key", "first param");
		hashMap.put("second key", "second param");
		
		chain.start(hashMap);
	}

}
