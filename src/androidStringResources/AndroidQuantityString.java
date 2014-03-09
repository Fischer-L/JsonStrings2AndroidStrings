package androidStringResources;

import java.util.ArrayList;
import java.util.Arrays;

import libs.MyException;

/**
 * This class represent the quantity string element in the Android's strings.xml file. In the strings.xml, it is like:
 * <br/>
 * {@code
 * 		<plurals name="plural_name">
 * 			<item quantity=["zero" | "one" | "two" | "few" | "many" | "other"]>text_string</item>
 * 		</plurals>
 * }
 */
public class AndroidQuantityString extends AndroidStringItemsBase {
    
	/**
	 * @param n
	 * 		Refer to the AndroidStringItemsBase class
	 * @param items
	 * 		The arrayList storing the instances of AndroidQuantityString.AndroidQuantityItem.
	 */
    public AndroidQuantityString(String n, ArrayList<AndroidQuantityItem> items) {  
        super(n);  
        
        // Retain items with valid quantity
        ArrayList<AndroidQuantityItem> valids = new  ArrayList<AndroidQuantityItem>();
        for (AndroidQuantityItem i : items) {
        	try {
	            if (AndroidQuantityString.isValidQuantity(i.quantity)) {
	            	valids.add(i);
	            } else {
	            	throw new MyException(
	            		String.format("The item(value = %s) in the quantity string(name = %s) has invalid quantity(= %s)", i.value, n, i.quantity)
	            	);
	            }
        	} catch (MyException e) {
        		e.print1stPoint();
        	}
        }        
        this.setItems(valids);
    }
    
    
    /*
     * Properties
     **********/
    
    /**
     * The valid quantity values.
     */
    private static final String[] validQuantities = {
        "zero", "one", "two", "few", "many", "other"
    };
    
    
    /*
     * Methods
     **********/
        
    /**
     * Check the validity of input quantity value.
     * @param quantity
     * 		The quantity value.
     * @return
     * 		- If valid: true
     * 		<br/>
     * 		- If invalid: false
     */
    public static boolean isValidQuantity(String quantity) {
        return (Arrays.asList(AndroidQuantityString.validQuantities).indexOf(quantity) >= 0);
    }
    
    /**
     * @return
     * 		- If there are items: the next instance of AndroidQuantityString.AndroidQuantityItem.
     * 		<br/>
     * 		- If no items: null
     */
    @Override
    public AndroidQuantityItem nextItem() {          
        if (this.itemsItr.hasNext()) {
            AndroidQuantityItem it = (AndroidQuantityItem) this.itemsItr.next();
            return new AndroidQuantityItem(it.quantity, it.value);
        } else {
            return null;
        }
    }
    
    
    /*
     * Classes
     **********/   
    
    /**
     * This class represent the item node in the quantity string element. In the strings.xml, it is like:
	 * <br/>
	 * {@code
	 * 		<item quantity=["zero" | "one" | "two" | "few" | "many" | "other"]>text_string</item>
	 * }
     */
    public static class AndroidQuantityItem {
        
    	/**
    	 * @param q
    	 * 		Refer to this.quantity.
    	 * @param v
    	 * 		Refer to this.value.
    	 */
        public AndroidQuantityItem(String q, String v) {
            this.quantity = q;
            this.value = v;
        }
        
        /**
         * The value of the item node's quantity attribute.
         */
        public String quantity;
        
        /**
         * The string value of the item node
         */
        public String value;
    }
}
