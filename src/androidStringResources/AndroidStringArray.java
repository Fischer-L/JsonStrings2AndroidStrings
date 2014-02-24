/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package androidStringResources;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Fischer_Liu
 */
public class AndroidStringArray extends AndroidStringItemsBase {
    
    public AndroidStringArray(String n, ArrayList<String> its) {
        super(n, its);
    }
    
    /*
     * Methods
     **********/
    
    @Override
    public String nextItem() {
        if (this.itemsItr.hasNext()) {
            return (String) this.itemsItr.next();
        } else {
            return null;
        }
    }
}
