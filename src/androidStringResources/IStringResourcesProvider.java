/**
 * @author Fischer_Liu
 */

package androidStringResources;

import java.util.ArrayList;

/*
 */
public interface IStringResourcesProvider {
	public String getDefaultLang();
    public String[] getSupportedLangs();
    public ArrayList<androidStringResources.AndroidString> getStrings(String lang);
    public ArrayList<androidStringResources.AndroidStringArray> getStringArrays(String lang);
    public ArrayList<androidStringResources.AndroidQuantityString> getQuantityStrings(String lang);    
}
