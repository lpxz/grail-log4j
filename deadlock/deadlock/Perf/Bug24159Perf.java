package deadlock.Perf;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import org.apache.log4j.AsyncAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.VectorAppender;
import org.apache.log4j.WriterAppender;

import sun.misc.Hashing;


public class Bug24159Perf {

//	public static Logger root = Logger.getLogger("com.MyLog");
//	public static 
	
	static{
		
//		PropertyConfigurator.configure ("log4j.properties");
	}
	public static int iterationNo = 10;// make the atomic region large enough.
	public static int InternalIterationNo = 10000;// make the atomic region large enough.
	public static int loggerNo = 100;
     public static  int resourceNo = 500;
     public static final Random RANDOM = new Random();
     
   public static   CriticalResource[] resources = new CriticalResource[resourceNo];
   public static   Logger[] loggers = new Logger[loggerNo];
	public static void main(String[] args) throws InterruptedException {
		
		int threadNo = Integer.parseInt(args[0]);
		Thread[] threads = new Thread[threadNo];
		
		for(int i=0; i<resourceNo; i++)
		{
			resources[i] = new CriticalResource();
		}
		
		for(int i=0; i<loggerNo; i++)
		{
			loggers[i] = Logger.getLogger("mylogger" + i);
			 Layout layout = new SimpleLayout();
			try {
				loggers[i].addAppender(new FileAppender(layout, "/home/lpxz/eclipse/workspacegrail/log4j/logger"+i));
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		for(int i=0;i<threadNo; i++)
		{
			Thread t = new Thread(){
					public void run(){
						
						for(int m=0;m<iterationNo; m++)
						{
							int id = RANDOM.nextInt(resourceNo-1);
							CriticalResource resource = resources[id];
							
							int id2 = RANDOM.nextInt(loggerNo-1);
							Logger logger = loggers[id2];
							
							
							
							
							synchronized(Bug24159Perf.class)//synchronized ((resource.hashCode() + " " + logger.hashCode()).intern())
							{
								for(int z=0;z<InternalIterationNo; z++){
								
								resource.setResource("newResource", logger);// hold the lock for resource and wait for the lock for log.
//						         logger.debug(resource);
								}
							}
							 
							
						}
						
					}

					private Object hashing(CriticalResource resource,
							Logger logger) {
						
						return (resource.hashCode()<<8 + logger.hashCode());
					}
				};
			
			threads[i] =t;
	   }
		long start = System.currentTimeMillis();
		for(int i=0;i<threadNo; i++)
		{
			threads[i].start();
		}
		for(int i=0;i<threadNo; i++)
		{
			threads[i].join();
		}
		long end = System.currentTimeMillis();
		System.out.println("duration: " + (end-start));
		

		
		
		
		

	}

}
