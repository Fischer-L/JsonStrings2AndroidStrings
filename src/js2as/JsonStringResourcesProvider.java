package js2as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import libs.MyException;

import org.json.JSONArray;
import org.json.JSONObject;

import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;
import androidStringResources.IStringResourcesProvider;

public class JsonStringResourcesProvider implements IStringResourcesProvider {


	/*
	 * Fields, methods & classes for parsing JSON string resources 	
	 **********/
	
	private static class JsonKeys {
				
		public static final String defaultLangKey = "default_lang";
		
		public static class StringNodeArray {
			
			public static final String key = "string";
			
			public static class StringNode {
				
				public static final String nameKey = "name";
				
				public static class ValuesArray {
					
					public static final String key = "values";
					
					public static final String langKey = "lang";
					
					public static final String valueKey = "value";
				}
			}			
		}
		
		public static class StringArrayNodeArray {
			
			public static final String key = "string-array";
			
			public static class StringArrayNode {
				
				public static final String nameKey  = "name";
				
				public static class ValuesArray {
					
					public static final String key = "values";
					
					public static class ItemNodeArray {
						
						public static final String key = "item";
						
						public static final String langKey = "lang";
						
						public static final String valueKey = "value";
					}
				}
			}
		}
		
		public static class QuantityStringNodeArray {
			
			public static final String key = "plurals";
			
			public static class QuantityStringNode {
				
				public static final String nameKey  = "name";
				
				public static class ValuesArray {
					
					public static final String key = "values";
					
					public static class ItemNode {
						
						public static final String key = "item";
						
						public static final String quantityKey = "quantity";
						
						public static class StringsArray {
							
							public static final String key = "strings";
							
							public static final String langKey = "lang";
							
							public static final String valueKey = "value";
						}
					}
				}
			}
		}
	}
	
	private JSONObject readJsonObject(String path) {
		
	}
	
	private boolean js2asStrings(JSONArray ja) {
		
	}
	
	private boolean js2asStringArrays(JSONArray ja) {
		
	}
	
	private boolean js2asQuantityStrings(JSONArray ja) {
		
	}
	
	private void decideDefaultLang(HashMap<String, String> defaultLangsPool) {
		
		int i;
		String path;
		String lang;
		ArrayList<String> langList = new ArrayList<String>(); // The list storing all the langs defined as default.
		ArrayList<ArrayList<String>> langPathList = new ArrayList<ArrayList<String>>(); // This list storing the lists of paths of files which define the same default lang, its index corresponds to the index of langList.
		
		// Firstly put the paths of files which define the same default lang together.
		Iterator<String> pathItr = defaultLangsPool.keySet().iterator();
		while (pathItr.hasNext()) {
			
			path = pathItr.next();
			lang = defaultLangsPool.get(path);
			i = langList.indexOf(lang);
			
			if (i < 0) {
			// This lang never appear before
				
				// Create the list of paths of files which define this lang as the default lang
				ArrayList<String> pathList = new ArrayList<String>();
				pathList.add(path);

				langList.add(lang); // Add this new lang
				langPathList.add(pathList); // Add the list of paths of files which define this new lang as the default lang
				
			} else {
			// This lang has appeared in other .json files before
				
				// Get the list of paths of files which also define this lang as the default lang
				// and then add this file's path into that list.
				langPathList.get(i).add(path);						
			}
		}
		
		
		// Secondly kick out the case which defines no default lang
		try {
			for (i = langList.size() - 1; i >= 0; i--) {						
				if (langList.get(i) == null) {							
					MyException e = new MyException("The files: " + langPathList.get(i).toString() + " define no default language. If this is ok, please jsut ignore this warning.");
					langList.remove(i);
					langPathList.remove(i);
					throw e;
				}						
			}					
		} catch (MyException e) {
			e.print1stPoint();					
		}
		
		
		// Thirdly pick out one lang as default from the langList
		lang = null;
		if (langList.size() == 1) {
		// Ideal case: there is only one default lang defined
			
			lang = langList.get(0);
			
		} else if (langList.size() > 1) {
		// Conflict case: there are multiple default langs defined
			
			// Take the lang which has been defined most
			lang = langList.get(0);
			for (i = 1; i < langList.size(); i++) {							
				if (langPathList.get(i).size() > langPathList.get(i-1).size()) {
					lang = langList.get(i);
				}
			}
			
			try {
				StringBuilder sb = new StringBuilder("Multiple default language defined >>> \n");
				for (i = 0; i < langList.size(); i++) {	
					sb.append("The language " + langList.get(i) + " is defined by the files: " + langPathList.get(i).toString() + "\n");
				}
				sb.append("Try to take the language " + lang + "as default.");
				throw new MyException(sb.toString());
			} catch (MyException e) {
				e.print1stPoint();					
			}
		}
		
		
		// Finally make sure the picked default lang is in the supported langs and then decide the final default lang
		try {
			this.defaultLangIdx = this.supportedLangs.indexOf(lang);
			if (this.defaultLangIdx < 0) {
				this.defaultLangIdx = 0;
				throw new MyException("The default lang " + lang + "is not in the supported langs; take the lang " + this.supportedLangs.get(this.defaultLangIdx) + " as fallback.");
			}					
		} catch (MyException e) {
			e.print1stPoint();					
		}
		
	}
	
	public int parseJsonFiles(String[] paths) {
		
		int parsedCount = 0;
		
		if (paths != null && paths.length > 0) {
			
			JSONObject jObj;
			HashMap<String, String> defaultLangsPool = new HashMap<String, String>(); // key = the file path, value = the default lang defined in this file
			
			// Read JSON object from .json files and convert the JSON string resources into the Android string resources one file by one file
			for (String p : paths) {
				
				if (defaultLangsPool.get(p) == null) {
					
					// TBW: Cannot use null!!!
					defaultLangsPool.put(p, null);
					
					jObj = this.readJsonObject(p);
					
					try {	
						
						if (jObj != null) {
							
							if (   this.js2asStrings(jObj.optJSONArray(JsonKeys.StringNodeArray.key))
								|| this.js2asStringArrays(jObj.optJSONArray(JsonKeys.StringArrayNodeArray.key))
								|| this.js2asQuantityStrings(jObj.optJSONArray(JsonKeys.QuantityStringNodeArray.key))
							) {
								parsedCount++;
								// Collect all the default langs defined in the different .json files first.
								// Later decide to take which one.
								defaultLangsPool.put(p, jObj.optString(JsonKeys.defaultLangKey, null));								
							}
							
						} else {
							throw new MyException("Fail to parse the JSON resources at " + p + "!");
						}
						
					} catch (MyException e) {
						e.print1stPoint();
					}
				}
			}
			
			// After successfully parsing the JSON resources, let's decide the default lang.
			if (parsedCount > 0) {
				this.decideDefaultLang(defaultLangsPool);
			}
		}
		
		return parsedCount;
	}
	
	/*
	 * Fields of the Android string resources
	 **********/
	
	private int defaultLangIdx = -1;
	private ArrayList<String> supportedLangs = null;
	private ArrayList<AndroidString> asStrings = null;
	private ArrayList<AndroidStringArray> asStringArrays = null;
	private ArrayList<AndroidQuantityString> asQuantityStrings = null;
	
	/*
	 * Methods @see androidStringResources.IStringResourcesProvider
	 **********/
	
	@Override
	public String getDefaultLang() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getSupportedLangs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AndroidString> getStrings(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AndroidStringArray> getStringArrays(String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AndroidQuantityString> getQuantityStrings(String lang) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
