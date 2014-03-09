package androidStringResources;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is basis of the Android's string array and quantity string resources. the Android's string array and quantity string elements in the strings.xml use the node, &lt;item&gt;, to carry the string resources.
 * <br/>
 * This class represents this &lt;item&gt; concept's abstraction.
 */
public abstract class AndroidStringItemsBase extends AndroidStringBase {

	/**
	 * @param n
	 * 		Refer to the AndroidStringBase class
	 */
	public AndroidStringItemsBase(String n) {
		super(n);
	}

	/**
	 * @param n
	 * 		Refer to the AndroidStringBase class
	 * @param its
	 * 		Refer to this.setItems
	 */
	public AndroidStringItemsBase(String n, ArrayList<?> its) {
		super(n);
		this.setItems(its);
	}
	
	
	/*
	 * Fields
	 **********/
	
	/**
	 * The collection of &lt;item&gt; resources which the Android string element has
	 */
	private ArrayList<?> items = null;
	
	/**
	 * The iterator of this.items
	 */
	protected Iterator<?> itemsItr = null;
	
    
    /*
     * Methods
     *********/
    
	
	/**
	 * Get the next item resource.
	 * 
	 * @return
	 * 		- If there are items: The next item resource in the iteration of this.itemsItr
	 * 		<br/>
	 * 		- If no items: null
	 */
    public abstract Object nextItem();

    /**
     * Set this.items
     * 
     * @param
     * 		its: The arrayList storing the &lt;item&gt; resources
     */
    protected void setItems(ArrayList<?> its) {
		this.items = its;
		this.itemsItr = this.items.iterator();    	
    }
    
    /**
     * Rewind the internal item iterator to the head so next time when calling this.nextItem(), this.nextItem will start returning items from the the 1st one
     * 
     */
    public void rewind() {
    	if (this.items != null) {
    		this.itemsItr = this.items.iterator();
    	}
    }
    
    /**
     * Get the number of &lt;item&gt; resources
     * 
     * @return
     * 		The number of &lt;item&gt; resources
     */
    public int size() {
    	return (this.items != null) ? this.items.size() : 0;
    }
}
