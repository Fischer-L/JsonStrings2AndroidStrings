/**
 *
 * @author Fischer_Liu
 */

package libs;

public class MyException extends Exception {
    
    public MyException (String msg) {
        super(msg);
    }
    
    public void print1stPoint() {
        
        StackTraceElement p0 = this.getStackTrace()[0]; // The first point where the exception takes place
        
        String log = ""
                   + this.getMessage() + " @ "
                   + p0.getLineNumber() + " : "
                   + p0.getFileName();
        
        System.out.println(log);
    }
}
