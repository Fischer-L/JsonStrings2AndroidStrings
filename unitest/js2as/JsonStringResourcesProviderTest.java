package js2as;

import static org.junit.Assert.*;

import java.util.HashMap;

import libs.MyException;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import unitestLibs.Constants;

public class JsonStringResourcesProviderTest {
	
	@Test
	public void testNormalCase() {
		
		try {
			
			JsonStringResourcesProvider jp = new JsonStringResourcesProvider(JSONResources.get(JSONResources.NORMAL_CASE));
			
			assertEquals(2, jp.getSupportedLangs().length);
			assertEquals(Constants.JSONSource.LANG_ES, jp.getDefaultLang());
			assertEquals(4, jp.getStrings(Constants.JSONSource.LANG_EN).size());
			assertEquals(4, jp.getStrings(Constants.JSONSource.LANG_ES).size());
			assertEquals(4, jp.getStringArrays(Constants.JSONSource.LANG_EN).size());
			assertEquals(4, jp.getStringArrays(Constants.JSONSource.LANG_ES).size());
			assertEquals(4, jp.getQuantityStrings(Constants.JSONSource.LANG_EN).size());
			assertEquals(4, jp.getQuantityStrings(Constants.JSONSource.LANG_ES).size());
			
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidFormatCaseA() {
		
		try {
			
			JsonStringResourcesProvider jp = new JsonStringResourcesProvider(JSONResources.get(JSONResources.INVALID_FORMAT_CASE_A));
			
			assertEquals(2, jp.getSupportedLangs().length);
			assertEquals(Constants.JSONSource.LANG_ES, jp.getDefaultLang());
			assertEquals(2, jp.getStrings(Constants.JSONSource.LANG_EN).size());
			assertEquals(2, jp.getStrings(Constants.JSONSource.LANG_ES).size());
			assertEquals(null, jp.getStringArrays(Constants.JSONSource.LANG_EN));
			assertEquals(null, jp.getStringArrays(Constants.JSONSource.LANG_ES));
			assertEquals(2, jp.getQuantityStrings(Constants.JSONSource.LANG_EN).size());
			assertEquals(2, jp.getQuantityStrings(Constants.JSONSource.LANG_ES).size());
			
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidFormatCaseB() {
		
		try {
			
			JsonStringResourcesProvider jp = new JsonStringResourcesProvider(JSONResources.get(JSONResources.INVALID_FORMAT_CASE_B));
			
			assertEquals(2, jp.getSupportedLangs().length);
			assertEquals(Constants.JSONSource.LANG_ES, jp.getDefaultLang());
			assertEquals(null, jp.getStrings(Constants.JSONSource.LANG_EN));
			assertEquals(null, jp.getStrings(Constants.JSONSource.LANG_ES));
			assertEquals(null, jp.getStringArrays(Constants.JSONSource.LANG_EN));
			assertEquals(2, jp.getStringArrays(Constants.JSONSource.LANG_ES).size());
			assertEquals(2, jp.getQuantityStrings(Constants.JSONSource.LANG_EN).size());
			assertEquals(2, jp.getQuantityStrings(Constants.JSONSource.LANG_ES).size());
			
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidFormatCaseC() {
		
		try {
			
			JsonStringResourcesProvider jp = new JsonStringResourcesProvider(JSONResources.get(JSONResources.INVALID_FORMAT_CASE_C));
			
			assertEquals(2, jp.getSupportedLangs().length);
			assertEquals(Constants.JSONSource.LANG_ES, jp.getDefaultLang());
			assertEquals(1, jp.getStrings(Constants.JSONSource.LANG_EN).size());
			assertEquals(1, jp.getStrings(Constants.JSONSource.LANG_ES).size());
			assertEquals(1, jp.getStringArrays(Constants.JSONSource.LANG_EN).size());
			assertEquals(null, jp.getStringArrays(Constants.JSONSource.LANG_ES));
			assertEquals(null, jp.getQuantityStrings(Constants.JSONSource.LANG_EN));
			assertEquals(1, jp.getQuantityStrings(Constants.JSONSource.LANG_ES).size());
			
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * Assistive classes
	 **********/
	
	private static class JSONResources {
		
		public static final int NORMAL_CASE = 0;
		public static final int INVALID_FORMAT_CASE_A = 1;
		public static final int INVALID_FORMAT_CASE_B = 2;
		public static final int INVALID_FORMAT_CASE_C = 3;
		
		private static JSONObject assembleJSON(String defaultLang, String stringSource, String stringArraySource, String pluralsSource) {
			return new JSONObject(String.format(
							 "{"
							+	"\"default_lang\" : \"%s\","
							+	"\"string\" : %s,"
							+	"\"string-array\" : %s,"
							+	"\"plurals\" : %s"
							+"}",
							defaultLang, stringSource, stringArraySource, pluralsSource
					   ));
		}
		
		public static HashMap<String, JSONObject> get(int theCase) {
			
			JSONObject json;			
			HashMap<String, JSONObject> res = new HashMap<String, JSONObject>();
			
			switch (theCase) {
			
				case JSONResources.NORMAL_CASE:					
					json = JSONResources.assembleJSON(
								Constants.JSONSource.LANG_ES,
								Constants.JSONSource.StringSource.NORMAL_CASE,
								Constants.JSONSource.StringArraySource.NORMAL_CASE,
								Constants.JSONSource.QuantityStringSource.NORMAL_CASE
						   );
					res.put("/path/a.json", json);
					res.put("/path/b.json", json);					
				break;
				
				case JSONResources.INVALID_FORMAT_CASE_A:	
					json = new JSONObject(String.format(
								 "{"
								+	"\"default_lang\" : \"%s\","
								+	"\"string\" : %s,"
								+	"\"INVALID\" : %s,"
								+	"\"plurals\" : %s"
								+"}",
								Constants.JSONSource.LANG_ES,
								Constants.JSONSource.StringSource.NORMAL_CASE,
								Constants.JSONSource.StringArraySource.NORMAL_CASE,
								Constants.JSONSource.QuantityStringSource.NORMAL_CASE
						   ));
					res.put("/path/a.json", json);
				break;
			
				case JSONResources.INVALID_FORMAT_CASE_B:					
					json = JSONResources.assembleJSON(
								Constants.JSONSource.LANG_ES,
								Constants.JSONSource.StringSource.INVALID_NAME_CASE,
								Constants.JSONSource.StringArraySource.INVALID_LANG_CASE,
								Constants.JSONSource.QuantityStringSource.INVALID_QUANTITY_CASE
						   );
					res.put("/path/a.json", json);					
				break;
			
				case JSONResources.INVALID_FORMAT_CASE_C:					
					json = JSONResources.assembleJSON(
								Constants.JSONSource.LANG_ES,
								Constants.JSONSource.StringSource.INVALID_LANG_CASE,
								Constants.JSONSource.StringArraySource.INVALID_ITEMS_CASE,
								Constants.JSONSource.QuantityStringSource.INVALID_FORMAT_CASE
						   );
					res.put("/path/a.json", json);					
				break;
			}
			
			return res;
		}
	}
}
