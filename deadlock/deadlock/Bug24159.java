package deadlock;
import java.util.Vector;

import org.apache.log4j.AsyncAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.VectorAppender;
import org.apache.log4j.WriterAppender;


public class Bug24159 {

	public static Logger root = Logger.getRootLogger();
	public static CriticalResource resource = new CriticalResource();
	
	static{
		
		PropertyConfigurator.configure ("log4j.properties");
//	    Layout layout = new SimpleLayout();
//	    VectorAppender vectorAppender = new VectorAppender();
//	    WriterAppender writerAppender = new WriterAppender();
//	    AsyncAppender asyncAppender = new AsyncAppender();
//	    writerAppender.setName("lpxzAppender");
//	    asyncAppender.addAppender(vectorAppender);
//	    root.addAppender(writerAppender); 

//	    asyncAppender.close();
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(){
			public void run(){
				
				root.debug(resource);// hold the lock for log (category.java) and wait for lock for resource.
				

				    
			}
		};
		
		Thread t2 = new Thread(){
			public void run(){
				resource.setResource("newResource");// hold the lock for resource and wait for the lock for log.
			}
		};
		
		t1.start();
		t2.start();
		
		
		t1.join();
		t2.join();
		
		

	}

}
