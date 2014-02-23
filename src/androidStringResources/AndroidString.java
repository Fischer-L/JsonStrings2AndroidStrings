/**
 *
 * @author Fischer_Liu
 */

package androidStringResources;


public class AndroidString extends AndroidStringBase {
    
    public AndroidString (String n, String v) {
        super(n);
        this.value = v;
    }
    
    /**
     * Properties
     **/
    
    private String value = "";
    
    /**
     * Methods
     **/
    
    public String getValue() {
        return this.value;
    }

}
