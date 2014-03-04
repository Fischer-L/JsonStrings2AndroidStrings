
package androidStringResources;
/**
 * This class is the basis of the Android string resource. Every Android string element in the strings.xml carries one attribute called "name".
 * <br/>
 * This class represents the abstraction of the common "name" attribute.
 */
public abstract class AndroidStringBase {
    
	/**
	 * @param n
	 * 		the value of Android string element's "name" attribute.
	 */
    public AndroidStringBase(String n) {
        this.name = n;
    }
    
    /*
     * Properties
     **********/
    
    /**
     * The value of Android string element's "name" attribute.
     */
    private String name = "";
    
    /*
     * Methods
     **********/
    
    /**
     * Get the value of Android string element's "name" attribute
     * @return
     * 		the value of Android string element's "name" attribute
     */
    public String getName() {
        return this.name;
    }
    
}
