/**
 *
 * @author Fischer_Liu
 */

package androidStringResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class AndroidQuantityString extends AndroidStringItemsBase {
    
    public AndroidQuantityString(String n, ArrayList<AndroidQuantityItem> items) {  
        super(n);  
        
        // Retain items with valid quantity
        ArrayList<AndroidQuantityItem> valids = new  ArrayList<AndroidQuantityItem>();
        for (AndroidQuantityItem i : items) {
            if (AndroidQuantityString.isValidQuantity(i.quantity)) {
            	valids.add(i);
            }
        }        
        this.setItems(valids);
    }
    
    
    /*
     * Properties
     **********/
    
    private static final String[] validQuantities = {
        "zero", "one", "two", "few", "many", "other"
    };
    
    
    /*
     * Methods
     **********/
        
    public static boolean isValidQuantity(String quantity) {
        return (Arrays.asList(AndroidQuantityString.validQuantities).indexOf(quantity) >= 0);
    }
    
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
    
    public static class AndroidQuantityItem {
        
        public AndroidQuantityItem(String q, String v) {
            this.quantity = q;
            this.value = v;
        }
        
        public String quantity;
        public String value;
    }
}
