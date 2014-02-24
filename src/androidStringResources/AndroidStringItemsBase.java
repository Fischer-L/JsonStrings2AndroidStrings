package androidStringResources;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class AndroidStringItemsBase extends AndroidStringBase {

	public AndroidStringItemsBase(String n) {
		super(n);
	}

	public AndroidStringItemsBase(String n, ArrayList<?> its) {
		super(n);
		this.setItems(its);
	}
	
	
	/*
	 * Fields
	 **********/
	
	private ArrayList<?> items = null;	
	protected Iterator<?> itemsItr = null;
	
    
    /*
     * Methods
     *********/
	
    public abstract Object nextItem();

    public void setItems(ArrayList<?> its) {
		this.items = its;
		this.itemsItr = this.items.iterator();    	
    }
    
    public int size() {
    	return (this.items != null) ? this.items.size() : 0;
    }
}
