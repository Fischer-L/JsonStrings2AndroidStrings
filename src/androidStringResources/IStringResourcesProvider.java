package androidStringResources;

import java.util.ArrayList;

/**
 * The interface which the Android string resource generator used to the outside string resources parser.
 * For instance, there is one outside string resources parser parsing the string resources in one strings.json file.
 * The strings.json parser shall implement the IStringResourcesProvider interface so the Android string resource generator can get the parsed string resources from the strings.json parser.
 */
public interface IStringResourcesProvider {
	public String getDefaultLang();
    public String[] getSupportedLangs();
    public ArrayList<androidStringResources.AndroidString> getStrings(String lang);
    public ArrayList<androidStringResources.AndroidStringArray> getStringArrays(String lang);
    public ArrayList<androidStringResources.AndroidQuantityString> getQuantityStrings(String lang);    
}
