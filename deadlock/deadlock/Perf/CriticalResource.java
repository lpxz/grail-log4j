package deadlock.Perf;

import org.apache.log4j.Logger;

public class CriticalResource {

    public  Object sharedState = new Object();
    
	public   synchronized void setResource(Object para, Logger logger)
	 {
	       sharedState = para;
	       logger.debug("shared state modified");// acquire the lock for log
	}
	
	// the caller acquires the lock for log.
	
	public String toString()
	{
		return "Obj@" + getResource().hashCode();
	}
	public  synchronized Object getResource()
	{
		return sharedState;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
