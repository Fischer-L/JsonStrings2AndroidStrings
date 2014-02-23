/**
 *
 * @author Fischer_Liu
 */

package androidStringResources;


public abstract class AndroidStringBase {
    
    public AndroidStringBase(String n) {
        this.name = n;
    }
    
    /**
     * Properties
     **/
    
    private String name = "";
    
    /**
     * Methods
     **/
    
    public String getName() {
        return this.name;
    }
    
}
