/**
 *
 * @author Fischer_Liu
 */

package androidStringResources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class AndroidQuantityString extends AndroidStringBase {
    
    public AndroidQuantityString(String n, ArrayList<AndroidQuantityItem> items) {
        super(n);
                
        // Remove items with invalid quantity btw
        this.items = items;
        for (AndroidQuantityItem i : this.items) {
            if (!this.isValidQuantity(i)) {
                this.items.remove(i);
            }
        }
        
        this.itemsItr = this.items.iterator();
    }
    
    /**
     * Properties
     **/
    
    private ArrayList<AndroidQuantityItem> items;
    private Iterator<AndroidQuantityItem> itemsItr;
    private final String[] validQuantities = {
        "zero", "one", "two", "few", "many", "other"
    };
    
    /**
     * Methods
     **/
        
    private boolean isValidQuantity(AndroidQuantityItem itm) {
        return (Arrays.asList(this.validQuantities).indexOf(itm.quantity) >= 0);
    }
    public AndroidQuantityItem nextItem() {          
        if (this.itemsItr.hasNext()) {
            AndroidQuantityItem it = this.itemsItr.next();
            return new AndroidQuantityItem(it.quantity, it.value);
        } else {
            return null;
        }
    }
    
    
    /**
     * Classes
     **/   
    
    public static class AndroidQuantityItem {
        
        public AndroidQuantityItem(String q, String v) {
            this.quantity = q;
            this.value = v;
        }
        
        public String quantity;
        public String value;
    }
}
