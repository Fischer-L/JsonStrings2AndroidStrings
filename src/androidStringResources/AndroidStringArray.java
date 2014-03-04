package androidStringResources;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represent the string array element in the Android's strings.xml file. In the strings.xml, it is like:
 * <br/>
 * {@code
 * 		<string-array name="string_array_name">
 * 			<item>text_string</item>
 * 		</string-array>
 * }
 */
public class AndroidStringArray extends AndroidStringItemsBase {
    
	/**
	 * @param n
	 * 		Refer to the AndroidStringItemsBase class
	 * @param its
	 * 		The arrayList storing strings. These strings are the items which the string array element carries.
	 */
    public AndroidStringArray(String n, ArrayList<String> its) {
        super(n, its);
    }
    
    /*
     * Methods
     **********/
    
    /**
     * Get the next string value
     * 
     * @return
     * 		- If there are items: the next string value
     * 		<br/>
     * 		- If no items: null
     */
    @Override
    public String nextItem() {
        if (this.itemsItr.hasNext()) {
            return (String) this.itemsItr.next();
        } else {
            return null;
        }
    }
}
