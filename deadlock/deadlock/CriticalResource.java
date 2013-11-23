package deadlock;

public class CriticalResource {

    public  Object sharedState = new Object();
    
	public   synchronized void setResource(Object para)
	 {
	       sharedState = para;
	       Bug24159.root.debug("shared state modified");// acquire the lock for log
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
