package ar.com.jolisper.metachainer.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

import ar.com.jolisper.metachainer.annotation.ChainName;
import ar.com.jolisper.metachainer.core.Chain;
import ar.com.jolisper.metachainer.core.ChainFactory;
import ar.com.jolisper.metachainer.exception.ChainError;

/**
 * Chain auto-discovery utility
 *  
 * 
 * @author upsidedownmind
 *
 */
public class ChainDiscovery {

	/**
	 * singleton instance
	 */
	private static ChainDiscovery instance = null;
	
	/**
	 * a list  of classes annotated with @ChainName
	 */
	private Set<String> chainClasses = null; 
	
	private ChainDiscovery() {

	}
	
	/**
	 * get discovery instance
	 * @return
	 */
	public synchronized static ChainDiscovery getInstance() {
		
		if(instance == null) {

			instance = createInstance();
		}
		
		return instance;
	}

	/**
	 * creates an instance and finds chain classes <br />
	 * this operation is expensive and must by done only once
	 * 
	 * @return an instance of ChainDiscovery
	 */
	private static ChainDiscovery createInstance() {
		//get path
		URL[] urls = ClasspathUrlFinder.findClassPaths(); // scan java.class.path
		AnnotationDB db = new AnnotationDB(); 
		
		//find all classes
		try {
			db.scanArchives(urls);
		} catch (IOException e) {
			//TODO: handle error
			throw new ChainError("Error finding classes", e);
		}
		
		//setup
		ChainDiscovery instance = new ChainDiscovery();
		
		//save classes
		instance.chainClasses  = db.getAnnotationIndex().get(ChainName.class.getCanonicalName());
		
		return instance;
	}
	

	/**
	 * get the specified chain by @ChainName
	 * @param string
	 * @see ChainName
	 * @return 
	 */
	public static Chain findByName(String search) {
		 
		//factory
		ChainFactory factory = ChainFactory.instance();
		
		Chain chain = null;
		
		//find by name
		_search : for (String className : getInstance().chainClasses) {
			
			Class<?> theClass = null;
			try {
				theClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				// this exception can't by throw, scannotatios only find existent classes
			}
			
			//validate name
			String chainName = theClass.getAnnotation(ChainName.class).value();
			
			if( search.equalsIgnoreCase(chainName) ) {
				//create chain
				chain = factory.create( theClass )  ; 
				break _search;
			}
			
		}
		
		return chain;

	}
}
