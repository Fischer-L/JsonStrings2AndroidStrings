package js2as;

import java.util.HashMap;

import libs.MyException;
import libs.Utility;

import org.json.JSONObject;

import androidStringXML.AndroidStringXML;

/**
 * The main class performing the job that reads JSON source files and parse JSON resources and generate Android strings.xml files
 *
 */
public class Js2As {
	
	public static void main(String[] args) {
		
		try {
			// Collect the command args (if fail, then just return.)
			if (!ArgsMgr.collectArgs(args)) {
				return;
			}
			
			System.out.println(">>> Reading the JSON files to get the JSON resources...");
			HashMap<String, JSONObject> jsonResources = Js2As.readJsonFiles(ArgsMgr.ARG_FILE_PATHS);
			if (jsonResources == null) {
				throw (new MyException("Reading the JSON files fails or no valid JSON resources available!!!"));
			}
			
			System.out.println(">>> Parsing the JSON resources into the Android string resources...");
			JsonStringResourcesProvider jp = new JsonStringResourcesProvider(jsonResources);
			
			System.out.println(">>> Generating the strings.xml files...");
			(new AndroidStringXML(ArgsMgr.ARG_RES_DIR_ROOT, jp)).generateXMLs();
			
			// End successfully
			System.out.println(">>> Done on generating: Please check " + ArgsMgr.ARG_RES_DIR_ROOT);
			
		} catch (MyException e) {
			// End erroneously
			System.out.println(">>> Fail on generating: " + e.getMessage());
		}
	}
	
	
	/**
	 * Read .json files and convert the contents into the JSON object resources
	 * 
	 * @param paths
	 * 		The paths of .json files
	 * @return
	 * 		- If OK: One HashMap object which its key is the file path and its value is the JSON object resource parsed from that path
	 * 		<br/>
	 * 		- If NG: null
	 */
	public static HashMap<String, JSONObject> readJsonFiles(String[] paths) {
		
		HashMap<String, JSONObject> jsonResources = null;
		
		if (paths != null && paths.length > 0) {
		
			String jsTxt;
			
			for (String p : paths) {
				
				try {
				
					jsTxt = Utility.Files.readFileAll(p, "UTF-16");
					
					if (jsTxt != null) {
						
						try {
							
							if (jsonResources == null) {
								jsonResources = new HashMap<String, JSONObject>();
							}
							jsonResources.put(p, new JSONObject(jsTxt));
							
						} catch (Exception e) {
							jsTxt = "\n        ********** corrupt JSON **********\n        " + jsTxt + "\n        **********************************\n";
							throw new MyException("Unable to read the file: " + p + " because of the corrupt JSON: " + e.getMessage() + jsTxt);	
						}
						
					} else {
						throw new MyException("Nothing can be read from the file: " + p);
					}
					
				} catch (MyException e) {
					e.print1stPoint();
				}
			}
		}
		
		return jsonResources;
	}
}
