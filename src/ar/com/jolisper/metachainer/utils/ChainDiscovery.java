package ar.com.jolisper.metachainer.utils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
 * @author upsidedownmind
 *
 */
public class ChainDiscovery {

	private static ChainDiscovery instance = null;
	private AnnotationDB annotationDB;
	
	private ChainDiscovery() {

	}
	
	/**
	 * get discovery instance
	 * @return
	 */
	public synchronized static ChainDiscovery getInstance() {
		
		if(instance == null) {

			//get path
			URL[] urls = ClasspathUrlFinder.findClassPaths(); // scan java.class.path
			AnnotationDB db = new AnnotationDB(); 
			
			//this operation is expensive and must by done only once
			try {
				db.scanArchives(urls);
			} catch (IOException e) {
				//TODO: handle error
				throw new ChainError("Error finding classes", e);
			}
			
			//setup
			instance = new ChainDiscovery();
			instance.annotationDB = db;
		}
		
		return instance;
	}
	

	/**
	 * get the specified chain by @ChainName
	 * @param string
	 * @see ChainName
	 * @return 
	 */
	public static List<Chain> findByName(String search) {
		
		//get instance
		ChainDiscovery my = getInstance();

		//factory
		ChainFactory factory = ChainFactory.instance();
		
		//find classes
		Set<String> clazzList = my.annotationDB.getAnnotationIndex().get(ChainName.class.getCanonicalName());
		
		List<Chain> chains = new ArrayList<Chain>();
		
		//find by name
		for (String className : clazzList) {
			
			Class<?> theClass = null;
			try {
				theClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				// this exception can't by throw, scannotatios only find existent classes
			}
			
			//validate name
			String chainName = theClass.getAnnotation(ChainName.class).value();
			
			if(search.equalsIgnoreCase(chainName)) {
				//create chain
				chains.add( factory.create( theClass )  ); 
			}
			
		}
		
		return chains;

	}
}
