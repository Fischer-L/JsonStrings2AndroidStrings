package libs;

/**
 * Just a little customized exception class
 */
public class MyException extends Exception {
    
	/**
	 * @param msg
	 * 		The exception message
	 */
    public MyException (String msg) {
        super(msg);
    }
    
    /**
     * Print some exception info
     */
    public void print1stPoint() {
        
        StackTraceElement p0 = this.getStackTrace()[0]; // The first point where the exception takes place
        
        String log = "!   "
                   + this.getMessage() + " @ "
                   + p0.getLineNumber() + " : "
                   + p0.getFileName();
        
        System.out.println(log);
    }
}
