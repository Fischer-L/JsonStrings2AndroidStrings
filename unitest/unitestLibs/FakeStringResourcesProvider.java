package unitestLibs;

import java.util.ArrayList;

import libs.MyException;
import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidQuantityString.AndroidQuantityItem;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;
import androidStringResources.IStringResourcesProvider;

public class FakeStringResourcesProvider implements IStringResourcesProvider {
	
	public FakeStringResourcesProvider() {}
	
	// To delete
//	public FakeStringResourcesProvider(String testCase) {
//		this.testCase = testCase;
//	}
	
	@Override
	public String getDefaultLang() {
		return this.supportedLangs[0];
	}
	
	@Override
	public String[] getSupportedLangs() {
		return this.supportedLangs;
	}

	@Override
	public ArrayList<AndroidString> getStrings(String lang) {
		Integer i;
		String[] sv = this.getStrValues(lang);
		
		ArrayList<AndroidString> sList = new ArrayList<AndroidString>();
		
		if (sv != null) {		
			for (i = 0; i < 3; i++) {
				sList.add(new AndroidString(this.stringNames[i], sv[i]));
			}
		}
				
		return sList;
	}

	@Override
	public ArrayList<AndroidStringArray> getStringArrays(String lang) {
		int i;
		String[] sv = this.getStrValues(lang);
		ArrayList<AndroidStringArray> saList = new ArrayList<AndroidStringArray>();
		
		if (sv != null) {
			ArrayList<String> itmList;
			
			itmList = new ArrayList<String>();
			for (i = 0; i < 3; i++) {
				itmList.add(sv[i]);
			}
			saList.add(new AndroidStringArray(this.stringArrayNames[0], itmList));
			
			itmList = new ArrayList<String>();
			for (i = 3; i < 6; i++) {
				itmList.add(sv[i]);
			}
			saList.add(new AndroidStringArray(this.stringArrayNames[1], itmList));
		}
		
		return saList;
	}

	@Override
	public ArrayList<AndroidQuantityString> getQuantityStrings(String lang) {
		int i;		
		String[] sv = this.getStrValues(lang);
		
		AndroidQuantityItem itm;
		ArrayList<AndroidQuantityItem> itmList;
		ArrayList<AndroidQuantityString> qsList = new ArrayList<AndroidQuantityString>();
		
		if (sv != null) {
			itmList = new ArrayList<AndroidQuantityItem>();
			for (i = 0; i < 3; i++) {
				itm = new AndroidQuantityItem(Constants.VALID_QUANTITIES[i],sv[i]);
				itmList.add(itm);
			}
			qsList.add(new AndroidQuantityString(this.quantityStringNames[0], itmList));
			
			itmList = new ArrayList<AndroidQuantityItem>();
			for (i = 3; i < 6; i++) {
				itm = new AndroidQuantityItem(Constants.VALID_QUANTITIES[i],sv[i]);
				itmList.add(itm);
			}
			qsList.add(new AndroidQuantityString(this.quantityStringNames[1], itmList));
		}
		
		return qsList;
	}
	
	
	/*
	 * Assistive fields for testing
	 **********/
// To delete 
//	public static final String TEST_DOUBLE_NAME = "TEST_DOUBLE_NAME";
//	private String testCase = "";
	
	private String[] supportedLangs = {
		"en", "zh", "jp"
	};
	
	protected String[] strValues = {
		"Java", "PHP", "C and C++", "Javascript", "SQLite", "HTML5"
	};
	
	protected String[] strValues_ZH = {
		"ZH_Java", "ZH_PHP", "ZH_C and C++", "ZH_Javascript", "ZH_SQLite", "ZH_HTML5"
	};
	
	protected String[] strValues_JP = {
		"JP_Java", "JP_PHP", "JP_C and C++", "JP_Javascript", "JP_SQLite", "JP_HTML5"
	};
	
	protected String[] stringNames = {
		"Google", "Mozilla", "Facebook"	
	};
	
	protected String[] stringArrayNames = {
		"Apple", "Samsung"
	};
	
	protected String[] quantityStringNames = {
		"BMW", "BENZ"
	};
	
	
	/*
	 * Assistive Methods for testing
	 **********/
	
	protected String[] getStrValues(String lang) {
	    
	    final String[] strValues = lang.equals(this.supportedLangs[0]) ?
	    						   this.strValues : lang.equals(this.supportedLangs[1]) ?
	    						   this.strValues_ZH : lang.equals(this.supportedLangs[2]) ?
	    						   this.strValues_JP : null;
	    try { 	
	    	if (strValues == null) {
	    		throw new MyException("The fake data doesn't include this lang: " + lang);
	    	}	    	
	    } catch (MyException e) {
	    	e.print1stPoint();
	    	return null;
	    }
		
	    return strValues;
	}
	
	public String getExpectedXML(String lang) {
		
		String[] strValues = this.getStrValues(lang);
		
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				   + "<resources>"
				   +		"<string name=\"" + this.stringNames[0] + "\">" + strValues[0]  + "</string>"
				   +		"<string name=\"" + this.stringNames[1] + "\">" + strValues[1] + "</string>"
				   +		"<string name=\"" + this.stringNames[2] + "\">" + strValues[2] + "</string>"
				   +		"<string-array name=\"" + this.stringArrayNames[0] + "\">"
				   + 			"<item>" + strValues[0] + "</item>"
				   + 			"<item>" + strValues[1] + "</item>"
				   + 			"<item>" + strValues[2] + "</item>"
				   + 		"</string-array>"
				   +		"<string-array name=\"" + this.stringArrayNames[1] + "\">"
				   + 			"<item>" + strValues[3] + "</item>"
				   + 			"<item>" + strValues[4] + "</item>"
				   + 			"<item>" + strValues[5] + "</item>"
				   + 		"</string-array>"
				   +		"<plurals name=\"" + this.quantityStringNames[0] + "\">"
				   + 			"<item quantity=\"" + Constants.VALID_QUANTITIES[0] + "\">" + strValues[0] + "</item>"
				   + 			"<item quantity=\"" + Constants.VALID_QUANTITIES[1] + "\">" + strValues[1] + "</item>"
				   + 			"<item quantity=\"" + Constants.VALID_QUANTITIES[2] + "\">" + strValues[2] + "</item>"
				   + 		"</plurals>"
				   +		"<plurals name=\"" + this.quantityStringNames[1] + "\">"
				   + 			"<item quantity=\"" + Constants.VALID_QUANTITIES[3] + "\">" + strValues[3] + "</item>"
				   + 			"<item quantity=\"" + Constants.VALID_QUANTITIES[4] + "\">" + strValues[4] + "</item>"
				   + 			"<item quantity=\"" + Constants.VALID_QUANTITIES[5] + "\">" + strValues[5] + "</item>"
				   + 		"</plurals>"
				   + "</resources>";
		return xml;
	}


}
