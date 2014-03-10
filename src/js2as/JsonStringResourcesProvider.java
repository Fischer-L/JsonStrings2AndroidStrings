package js2as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import libs.MyException;
import libs.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;
import androidStringResources.IStringResourcesProvider;
import androidStringResources.AndroidQuantityString.AndroidQuantityItem;

public class JsonStringResourcesProvider implements IStringResourcesProvider {
	
	public boolean isDebugMode() {
		return true;
	}
	
	public JsonStringResourcesProvider(String[] paths) {
		this.parseJsonFiles(paths);
	}

	/*
	 * Fields, methods & classes for parsing JSON string resources 	
	 **********/
	
	private static class CONST {
		
		public static final int NO_DEFAULT_LANG_INDEX = -1;
		
		public static final String NO_DEFAULT_LANG_DEFINED = null;
	}
	
	private static class JsonKeys {
				
		public static final String defaultLangKey = "default_lang";
		
		public static class StringNodeArray {
			
			public static final String key = "string";
			
			public static class StringNode {
				
				public static final String nameKey = "name";
				
				public static class Resources {
					
					public static final String key = "resources";
					
					public static class StringValue {
					
						public static final String langKey = "lang";
						
						public static final String strValueKey = "strValue";
					}
				}
			}			
		}
		
		public static class StringArrayNodeArray {
			
			public static final String key = "string-array";
			
			public static class StringArrayNode {
				
				public static final String nameKey  = "name";
				
				public static class Resources {
					
					public static final String key = "resources";
					
					public static class ItemNodes {
						
						public static final String langKey = "lang";
						
						public static final String itemsKey = "items";
					}
				}
			}
		}
		
		public static class QuantityStringNodeArray {
			
			public static final String key = "plurals";
			
			public static class QuantityStringNode {
				
				public static final String nameKey  = "name";
				
				public static class Resources {
					
					public static final String key = "resources";
					
					public static class ItemResource {
						
						public static final String langKey = "lang";
						
						public static class ItemNodes {
							
							public static final String key = "items";
							
							public static class ItemNode {
								
								public static final String quantityKey = "quantity";
								
								public static final String strValueKey = "strValue";
							}
						}
					}
				}
			}
		}
	}
	
	private JSONObject readJsonObject(String path) {
		
		JSONObject jo = null;
		String jsTxt = Utility.Files.readFileAll(path);
		
		if (jsTxt != null) {
			try {
				jo = new JSONObject(jsTxt);
			} catch (Exception e) {
				jo = null;
				(new MyException("Unable to read the file: " + path + " because of the corrupt JSON.")).print1stPoint();
			}			
		}
		
		return jo;
	}
	
	private int saveJs2AsStrings(JSONArray stringNodeArray) {
		
		int i, j, savedCount = 0;
		
		JSONObject sNode;
		
		String nodeName;
		JSONArray nodeRes;
		JSONObject nodeStr;
		
		String strLang;
		String strValue;
		
		ArrayList<AndroidString> asStrings;
		
		for (i = 0; i < stringNodeArray.length(); i++) {
				
			sNode = stringNodeArray.optJSONObject(i);
			if (sNode != null) {
				
				nodeName = sNode.optString(JsonKeys.StringNodeArray.StringNode.nameKey);
				nodeRes = sNode.optJSONArray(JsonKeys.StringNodeArray.StringNode.Resources.key);
				
				if (   nodeRes != null
					&& !nodeName.equals("")
				) {
					
					for (j = 0; j < nodeRes.length(); j++) {
						
						nodeStr = nodeRes.optJSONObject(j);
						if (nodeStr != null) {
							
							strLang = nodeStr.optString(JsonKeys.StringNodeArray.StringNode.Resources.StringValue.langKey);
							strValue = nodeStr.optString(JsonKeys.StringNodeArray.StringNode.Resources.StringValue.strValueKey, null);
							
							if (   strValue != null
								&& !strLang.equals("")
							) {
								
								asStrings = this.asStringsMap.get(strLang);
								if (asStrings == null) {
									asStrings = new ArrayList<AndroidString>();
								}
								
								asStrings.add(new AndroidString(nodeName, strValue));
								this.asStringsMap.put(strLang, asStrings);
								savedCount++;
							}
						}
					}
				}
			}
		}
		
		return savedCount;
	}
	
	private int saveJs2AsStringArrays(JSONArray stringArrayNodeArray) {
		
		int i, j, k, savedCount = 0;
		
		JSONObject saNode;
		
		String nodeName;
		JSONArray nodeRes;
		
		JSONObject itemNodes;
		JSONArray items;
		
		String itemLang;
		String itemValue;
				
		ArrayList<String> itemsList;
		ArrayList<AndroidStringArray> asStringArrays;
		
		for (i = 0; i < stringArrayNodeArray.length(); i++) {
			
			saNode = stringArrayNodeArray.optJSONObject(i);
			if (saNode != null) {
				
				nodeName = saNode.optString(JsonKeys.StringArrayNodeArray.StringArrayNode.nameKey);
				nodeRes = saNode.optJSONArray(JsonKeys.StringArrayNodeArray.StringArrayNode.Resources.key);

				if (   nodeRes != null
					&& !nodeName.equals("")
				) {
					
					for (j = 0; j < nodeRes.length(); j++) {
						
						itemNodes = nodeRes.optJSONObject(j);
						if (itemNodes != null) {

							items = itemNodes.optJSONArray(JsonKeys.StringArrayNodeArray.StringArrayNode.Resources.ItemNodes.itemsKey);
							itemLang = itemNodes.optString(JsonKeys.StringArrayNodeArray.StringArrayNode.Resources.ItemNodes.langKey);
							
							if (   items != null
								&& !itemLang.equals("")
							) {
								
								itemsList = new ArrayList<String>();								
								for (k = 0; k < items.length(); k++) {
									
									itemValue = items.optString(k);
									if (!itemValue.equals("")) {
										itemsList.add(itemValue);
									}
								}
								
								if (itemsList.size() > 0) {
									
									asStringArrays = this.asStringArraysMap.get(itemLang);
									if (asStringArrays == null) {
										asStringArrays = new ArrayList<AndroidStringArray>();
									}
									
									asStringArrays.add(new AndroidStringArray(nodeName, itemsList));
									this.asStringArraysMap.put(itemLang, asStringArrays);
									savedCount++;
								}
							}
						}
					}
				}
			}			
		}
		
		return savedCount;
	}
	
	private int saveJs2AsQuantityStrings(JSONArray quantityStringNodeArray) {
		
		int i, j, k, savedCount = 0;
		
		JSONObject qsNode;
		
		String nodeName;
		JSONArray nodeRes;		
		
		JSONObject ItemRes;
		String lang;
		
		JSONArray itemNodes;
		JSONObject item;
		String quantity;
		String strValue;
		
		ArrayList<AndroidQuantityItem> asQuantityItems;
		ArrayList<AndroidQuantityString> asQuantityStrings;
		
		for (i = 0; i < quantityStringNodeArray.length(); i++) {
			
			qsNode = quantityStringNodeArray.optJSONObject(i);
			if (qsNode != null) {
				
				nodeName = qsNode.optString(JsonKeys.QuantityStringNodeArray.QuantityStringNode.nameKey);
				nodeRes = qsNode.optJSONArray(JsonKeys.QuantityStringNodeArray.QuantityStringNode.Resources.key);
				
				if (   nodeRes != null
					&& !nodeName.equals("")
				) {
				
					for (j = 0; j < nodeRes.length(); j++) {
						
						ItemRes = nodeRes.optJSONObject(j);						
						if (ItemRes != null) {
							
							lang = ItemRes.optString(JsonKeys.QuantityStringNodeArray.QuantityStringNode.Resources.ItemResource.langKey);
							itemNodes = ItemRes.optJSONArray(JsonKeys.QuantityStringNodeArray.QuantityStringNode.Resources.ItemResource.ItemNodes.key);
														
							if (   !lang.equals("")
								&& itemNodes != null
							) {
								
								asQuantityItems = null;
								
								for (k = 0; k < itemNodes.length(); k++) {
									
									item = itemNodes.optJSONObject(k);
									if (item != null) {
										
										quantity = item.optString(JsonKeys.QuantityStringNodeArray.QuantityStringNode.Resources.ItemResource.ItemNodes.ItemNode.quantityKey);
										strValue = item.optString(JsonKeys.QuantityStringNodeArray.QuantityStringNode.Resources.ItemResource.ItemNodes.ItemNode.strValueKey, null);
									
										if (   strValue != null
											&& !quantity.equals("")
										) {
											asQuantityItems = new ArrayList<AndroidQuantityItem>();
											asQuantityItems.add(new AndroidQuantityItem(quantity, strValue));
											savedCount++;
										}
									}
								}
								
								if (asQuantityItems != null && asQuantityItems.size() > 0) {
									
									asQuantityStrings = this.asQuantityStringsMap.get(lang);
									if (asQuantityStrings == null) {
										asQuantityStrings = new ArrayList<AndroidQuantityString>();
									}
									
									asQuantityStrings.add(new AndroidQuantityString(nodeName, asQuantityItems));
									this.asQuantityStringsMap.put(lang, asQuantityStrings);									
								}
							}
						}
					}
				}
			}
		}
		
		return savedCount;
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
			
			try {
				
				path = pathItr.next();
				lang = defaultLangsPool.get(path);				
				if (lang == CONST.NO_DEFAULT_LANG_DEFINED) { // kick out the case which defines no default lang btw
					throw new MyException("The file: " + path + " define no default language. If this is ok, please jsut ignore this warning.");
				}
				
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
				
			} catch (MyException e) {
				e.print1stPoint();					
			}
		}

		
		// Secondly pick out one lang as default from the langList
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
				StringBuilder sb = new StringBuilder("Multiple default language defined => \n");
				for (i = 0; i < langList.size(); i++) {	
					sb.append("    The language " + langList.get(i) + " is defined by the files: " + langPathList.get(i).toString() + "\n");
				}
				sb.append("    Try to take the language " + lang + "as default.");
				throw new MyException(sb.toString());
			} catch (MyException e) {
				e.print1stPoint();					
			}
		}
		
		
		// Finally make sure the picked default lang is in the supported langs and then decide the final default lang
		try {
			
			if (lang == null) {
				
				this.defaultLangIdx = 0;
				throw new MyException("No default lang is defined; take the lang " + this.supportedLangs.get(this.defaultLangIdx) + " as fallback.");
				
			} else {
			
				this.defaultLangIdx = this.supportedLangs.indexOf(lang);
				if (this.defaultLangIdx < 0) {
					this.defaultLangIdx = 0;
					throw new MyException("The default lang " + lang + " is not in the supported langs; take the lang " + this.supportedLangs.get(this.defaultLangIdx) + " as fallback.");
				}
			}
			
		} catch (MyException e) {
			e.print1stPoint();					
		}
		
	}
	
	private int parseJsonFiles(String[] paths) {
		
		int parsedCount = 0;
		
		if (paths != null && paths.length > 0) {
			
			JSONObject jObj;
			ArrayList<String> pathsChked = new ArrayList<String>();
			HashMap<String, String> defaultLangsPool = new HashMap<String, String>(); // key = the file path, value = the default lang defined in this file
			
			// Read JSON object from .json files and convert the JSON string resources into the Android string resources one file by one file
			for (String p : paths) {
				
				if (pathsChked.indexOf(p) < 0) {
					
					pathsChked.add(p);
					
					jObj = this.readJsonObject(p);
					
					try {	
						
						if (jObj != null) {
							
							if (   this.saveJs2AsStrings(jObj.optJSONArray(JsonKeys.StringNodeArray.key)) > 0
								|| this.saveJs2AsStringArrays(jObj.optJSONArray(JsonKeys.StringArrayNodeArray.key)) > 0
								|| this.saveJs2AsQuantityStrings(jObj.optJSONArray(JsonKeys.QuantityStringNodeArray.key)) > 0
							) {
								// Collect all the default langs defined in the different .json files first.
								// Later decide to take which one.
								defaultLangsPool.put(p, jObj.optString(JsonKeys.defaultLangKey, CONST.NO_DEFAULT_LANG_DEFINED));		
								parsedCount = defaultLangsPool.size();
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

	private int defaultLangIdx = CONST.NO_DEFAULT_LANG_INDEX;
	private ArrayList<String> supportedLangs = new ArrayList<String>();
	private HashMap<String, ArrayList<AndroidString>> asStringsMap = new HashMap<String, ArrayList<AndroidString>>();
	private HashMap<String, ArrayList<AndroidStringArray>> asStringArraysMap = new HashMap<String, ArrayList<AndroidStringArray>>();
	private HashMap<String, ArrayList<AndroidQuantityString>> asQuantityStringsMap = new HashMap<String, ArrayList<AndroidQuantityString>>();
	
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
