package androidStringResources;

/**
 * This class represents the basic string element in the Android's strings.xml file. In the strings.xml, it is like:
 * <br/>
 * {@code
 * 		<string name="string_name">text_string</string>
 * }
 * <br/>
 */
public class AndroidString extends AndroidStringBase {
    
	/**
	 * @param n
	 * 		Refer to the AndroidStringBase class
	 * @param v
	 * 		The string value
	 */
    public AndroidString (String n, String v) {
        super(n);
        this.value = v;
    }
    
    /*
     * Properties
     **********/
    
    /**
     * The string value which this string element carrys
     */
    private String value = "";
    
    /*
     * Methods
     **********/
    
    /**
     * Get the string value of this string element
     * 
     * @return
     * 		The string value of this string element
     */
    public String getValue() {
        return this.value;
    }

}
