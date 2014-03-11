package androidStringResources;

import java.util.ArrayList;

/**
 * The content of strings.xml file is composed from string resources.
 * String resources could be stored in the form like one database, one .json file or one excel file so there should be a string resources parser which is in charge of parsing outside string resources.
 * The Android string resource generator's duty is to generate strings.xml files with given string resources, not to parse string resources.
 * It is the outside string resources parser's duty to parse string resources and then provide parsed string resources for the Android string resource generator's use.
 * For instance, there is one outside string resources parser parsing the string resources in one strings.json file.
 * The Android string resource generator uses the IStringResourcesProvider interface to interact with the outside string resources parser.
 * The strings.json parser shall implement the IStringResourcesProvider interface so the Android string resource generator can get the parsed string resources from the strings.json parser.
 */
public interface IStringResourcesProvider {
	
	/**
	 * Get the default string resource language code. The string resources in this language will be used as the content of the strings.xml in the values/ directory.
	 * The default string resource language shall be one of the supported languages.
	 *
	 * @return
	 * 		- If available: The default language for string resources
     *      <br/>
     *      - If unavailable: null
	 */
	public String getDefaultLang();
	
	/**
	 * Get the array of the supported languages. 
	 * Android system supports multi-languages by creating additional values directories inside res/ that include a hyphen and the ISO country code at the end of the directory name.
	 * For example, values-es/ is the directory containing simple resources for the locale with the language code "es".
	 * The XXXX in the values-XXXX/ directory is the language code which the Android string resource generator takes to generate the corresponding multi-language directory.
	 * For instance, there are 3 language directories to generate. Those are values-kx/, values-tt-rEE/, and values-aa_bb/.
	 * The returned supported languages array shall be like [ "kx", "tt-rEE", "aa_bb" ].
	 * 
	 * @return
	 * 		- If available: The array of the supported languages.
     *      <br/>
     *      - If unavailable: null
	 */
    public String[] getSupportedLangs();
    
    /**
     * Get all the strings(the instances of androidStringResources.AndroidStrin) in one specific language.
     * 
     * @param lang
     * 		The language
     * @return
     * 		- If available: All the strings in one specific language.
     * 		<br/>
     * 		- If not available: null
     */
    public ArrayList<androidStringResources.AndroidString> getStrings(String lang);
    
    /**
     * Get all the string arrays(the instances of androidStringResources.AndroidStringArray) in one specific language.
     * 
     * @param lang
     * 		The language
     * @return
     * 		- If available: All the string arrays in one specific language.
     * 		<br/>
     * 		- If not available: null
     */
    public ArrayList<androidStringResources.AndroidStringArray> getStringArrays(String lang);
    
    /**
     * Get all the quantity strings(the instances of androidStringResources.AndroidQuantityString) in one specific language.
     * 
     * @param lang
     * 		The language
     * @return
     * 		- If available: All the quantity strings in one specific language.
     * 		<br/>
     * 		- If not available: null
     */
    public ArrayList<androidStringResources.AndroidQuantityString> getQuantityStrings(String lang);    
}
