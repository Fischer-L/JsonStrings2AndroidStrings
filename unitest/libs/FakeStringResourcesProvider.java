package libs;

import java.util.ArrayList;

import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidQuantityString.AndroidQuantityItem;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;
import androidStringResources.IStringResourcesProvider;

public class FakeStringResourcesProvider implements IStringResourcesProvider {
	
	public FakeStringResourcesProvider() {}
	
	public FakeStringResourcesProvider(String testCase) {
		this.testCase = testCase;
	}

	@Override
	public String[] getSupportedLangs() {
		return this.supportedLangs;
	}

	@Override
	public ArrayList<AndroidString> getStrings(String lang) {
		int i;
		String[] sv = this.getStrValues(lang);
		
		ArrayList<AndroidString> sList = new ArrayList<AndroidString>();
		
		for (i = 0; i < 3; i++) {
			sList.add(new AndroidString(this.stringNames[i], sv[i]));
			if (this.testCase.equals(FakeStringResourcesProvider.TEST_DOUBLE_NAME)) {
				sList.add(new AndroidString(this.stringNames[i], sv[i]));
			}
		}
		
		return sList;
	}

	@Override
	public ArrayList<AndroidStringArray> getStringArrays(String lang) {
		int i;
		String[] sv = this.getStrValues(lang);
		
		ArrayList<String> itmList;
		ArrayList<AndroidStringArray> saList = new ArrayList<AndroidStringArray>();
		
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

		if (this.testCase.equals(FakeStringResourcesProvider.TEST_DOUBLE_NAME)) {
			saList.add(new AndroidStringArray(this.stringNames[0], itmList));
			saList.add(new AndroidStringArray(this.stringArrayNames[0], itmList));
			saList.add(new AndroidStringArray(this.quantityStringNames[0], itmList));
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
		
		itmList = new ArrayList<AndroidQuantityItem>();
		for (i = 0; i < 3; i++) {
			itm = new AndroidQuantityItem(FakeData.VALID_QUANTITIES[i],sv[i]);
			itmList.add(itm);
		}
		qsList.add(new AndroidQuantityString(this.quantityStringNames[0], itmList));
		
		itmList = new ArrayList<AndroidQuantityItem>();
		for (i = 3; i < 6; i++) {
			itm = new AndroidQuantityItem(FakeData.VALID_QUANTITIES[i],sv[i]);
			itmList.add(itm);
		}
		qsList.add(new AndroidQuantityString(this.quantityStringNames[1], itmList));
		
		if (this.testCase.equals(FakeStringResourcesProvider.TEST_DOUBLE_NAME)) {
			qsList.add(new AndroidQuantityString(this.stringNames[1], itmList));
			qsList.add(new AndroidQuantityString(this.stringArrayNames[1], itmList));
			qsList.add(new AndroidQuantityString(this.quantityStringNames[1], itmList));
		}	
		
		return qsList;
	}
	
	
	/*
	 * Assistive fields for testing
	 **********/
	public static final String TEST_DOUBLE_NAME = "TEST_DOUBLE_NAME";
	private String testCase = "";
	
	private String[] supportedLangs = {
		"en", "zh", "jp"
	};
	
	private String[] stringNames = {
		"Google", "Mozilla", "Facebook"	
	};
	
	private String[] stringArrayNames = {
		"Apple", "Samsung"
	};
	
	private String[] quantityStringNames = {
		"BMW", "BENZ"
	};
	
	private final String[] strValues = {
		"Java", "PHP", "C and C++", "Javascript", "SQLite", "HTML5"
	};
	
	private final String[] strValues_ZH = {
		"ZH_Java", "ZH_PHP", "ZH_C and C++", "ZH_Javascript", "ZH_SQLite", "ZH_HTML5"
	};
	
	private final String[] strValues_JP = {
		"JP_Java", "JP_PHP", "JP_C and C++", "JP_Javascript", "JP_SQLite", "JP_HTML5"
	};
	
	
	/*
	 * Assistive Methods for testing
	 **********/
	
	private String[] getStrValues(String lang) {
	    
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
	
	public final String getXML(String lang) {
		
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
				   + 			"<item quantity=\"" + FakeData.VALID_QUANTITIES[0] + "\">" + strValues[0] + "</item>"
				   + 			"<item quantity=\"" + FakeData.VALID_QUANTITIES[1] + "\">" + strValues[1] + "</item>"
				   + 			"<item quantity=\"" + FakeData.VALID_QUANTITIES[2] + "\">" + strValues[2] + "</item>"
				   + 		"</plurals>"
				   +		"<plurals name=\"" + this.quantityStringNames[1] + "\">"
				   + 			"<item quantity=\"" + FakeData.VALID_QUANTITIES[3] + "\">" + strValues[3] + "</item>"
				   + 			"<item quantity=\"" + FakeData.VALID_QUANTITIES[4] + "\">" + strValues[4] + "</item>"
				   + 			"<item quantity=\"" + FakeData.VALID_QUANTITIES[5] + "\">" + strValues[5] + "</item>"
				   + 		"</plurals>"
				   + "</resources>";
		return xml;
	}

}
