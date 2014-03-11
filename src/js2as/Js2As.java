package js2as;

import java.util.HashMap;

import libs.MyException;
import libs.Utility;

import org.json.JSONObject;

public class Js2As {
	
	public static void main(String[] args) {
		
		// Collect the command args (if fail, then just return.)
		if (!ArgsMgr.collectArgs(args)) {
			return;
		}
		
		// Read the .json files to get the JSON object resources
		HashMap<String, JSONObject> jsonResources = Js2As.readJsonFiles(ArgsMgr.ARG_FILE_PATHS);
		
		// Init the implementation of the interface IStringResourcesProvider
		
		// Call AndroidStringXML to generate strings.xml files
		
		// End
	}
	
	public static HashMap<String, JSONObject> readJsonFiles(String[] paths) {
		
		HashMap<String, JSONObject> jsonResources = null;
		
		if (paths != null && paths.length > 0) {
		
			String jsTxt;
			
			for (String p : paths) {
				
				try {
				
					jsTxt = Utility.Files.readFileAll(p);
					
					if (jsTxt != null) {
						
						try {
							
							if (jsonResources == null) {
								jsonResources = new HashMap<String, JSONObject>();
							}						
							jsonResources.put(p, new JSONObject(jsTxt));
							
						} catch (Exception e) {
							throw new MyException("Unable to read the file: " + p + " because of the corrupt JSON.");
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
