
package net.openhft.chronicle.examples;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.junit.Test;

import net.openhft.chronicle.map.ChronicleMapBuilder;

public class ChronicleTest {
	
	
	public static void main (String[] args){
		ChronicleTest ct = new ChronicleTest();
		ct.testChMapPersistedFile();
	}
	
	@Test
	public void testChMapHelloWorld(){
		//creates a MAP without a file.
		Map<Integer, CharSequence> map = ChronicleMapBuilder.of(Integer.class, CharSequence.class).create();
		
		map.put(1, "hello world");
		System.out.println(map.get(1));
	}
	
	
	@Test
	public void testChMapPersistedFile(){
		try {

		    String tmp = "C:\\Downloads\\Temp\\";
		    String pathname = tmp + "myfile.dat";
		    
		    File file = new File(pathname);
		    
		    ChronicleMapBuilder<Integer, Integer> builder = ChronicleMapBuilder.of(Integer.class, Integer.class);
		    //Map<Integer, Integer> map = ChronicleMapBuilder.of(Integer.class, Integer.class).create();
		    ConcurrentMap<Integer, Integer> map = builder.entries(10000000).createPersistedTo(file);
		    System.out.println("before putting inside the ChMap-->"+ map.get(1) +"\t mapSize "+map.size());
		    for (int iter=0;iter < 10000000; iter++){
		    
			    if (	map.get(map.size()+1)  == null ){
			    	map.put(map.size()+1, map.size()+1);
			    }
			    
/*			    for (int i=0;i< map.size(); i++){
			    	System.out.println("Map Value "+i+"\t-->"+map.get(i));
			    }
*/			    
		     //Thread.currentThread().sleep(1);
		     //System.out.println("##################################################");
		    }
		    System.out.println("last Map Value -->"+map.get(map.size()));
		    
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	@Test
	public void testChMapMultiThreads(){
		try {

		    String tmp = "C:\\Downloads\\Temp\\";
		    String pathname = tmp + "myfile.dat";
		    
		    File file = new File(pathname);
		    
		    ChronicleMapBuilder<Integer, CharSequence> builder = ChronicleMapBuilder.of(Integer.class, CharSequence.class);
		    ConcurrentMap<Integer, CharSequence> map = builder.createPersistedTo(file);

		    //TODO multiple threads trying to access the same .dat fileby calling testChMapPersistedFile()
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}	
	
	@Test
	public void testMap(){
		HashMap map = new HashMap();
	}
	
}
