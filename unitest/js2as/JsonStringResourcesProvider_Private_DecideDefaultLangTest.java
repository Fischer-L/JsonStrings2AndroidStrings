package js2as;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import libs.MyException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import unitestLibs.Uintility;

public class JsonStringResourcesProvider_Private_DecideDefaultLangTest {

	@Before
	public void setUp() throws Exception {
		
		this.testCls = Class.forName("js2as.JsonStringResourcesProvider");
		
		Class[] params = { HashMap.class };
		Object[] args = { null };
		this.testObj = Uintility.Reflection.newTestObj(this.testCls.getConstructor(params), args);
		
		params[0] = HashMap.class;
		this.decideDefaultLang = Uintility.Reflection.getPrivateMethod(this.testCls, "decideDefaultLang", params);
		
		// Set up the supported langs
		this.supportedLangs = Uintility.Reflection.getPrivateField(this.testCls, "supportedLangs");
		this.supportedLangs.set(this.testObj, this.fakeSupportedLangs());
	}
	
	@Test
	public void testNormalCase() {
		
		int expectedIdx = 0;
		
		// The normal case has only one supported lang defined as default
		HashMap<String, String> dummyDefaultLangsPool = new HashMap<String, String>();
		for (int i = 0; i < 10; i ++) {
			dummyDefaultLangsPool.put("path_" + i, this.fakeSupportedLangs().get(expectedIdx));			
		}
		testExpDefaultLang(dummyDefaultLangsPool, expectedIdx);		
	}

	@Test
	public void testNoDefinedCase() {
		
		int expectedIdx = 0;
		
		// The no-defined case has no supported lang defined as default
		HashMap<String, String> dummyDefaultLangsPool = new HashMap<String, String>();
		for (int i = 0; i < 10; i ++) {
			dummyDefaultLangsPool.put("path_" + i, null);			
		}
		testExpDefaultLang(dummyDefaultLangsPool, expectedIdx);		
	}
	
	@Test
	public void testMultipleDefinedCase() {
		
		int i;
		int expectedIdx = 4;
		ArrayList<String> slangs = this.fakeSupportedLangs();
		
		// The multiple-defined case has multiple supported langs defined as default
		HashMap<String, String> dummyDefaultLangsPool = new HashMap<String, String>();
		for (i = 0; i < 5; i ++) {
			dummyDefaultLangsPool.put("path/a/" + i, slangs.get(0));			
		}
		for (i = 0; i < 4; i ++) {
			dummyDefaultLangsPool.put("path/b/" + i, slangs.get(1));			
		}
		for (i = 0; i < 3; i ++) {
			dummyDefaultLangsPool.put("path/c/" + i, slangs.get(2));			
		}
		for (i = 0; i < 6; i ++) {
			dummyDefaultLangsPool.put("path/d/" + i, slangs.get(3));			
		}
		for (i = 0; i < 8; i ++) {
			dummyDefaultLangsPool.put("path/e/" + i, slangs.get(4));			
		}
		for (i = 0; i < 1; i ++) {
			dummyDefaultLangsPool.put("path/f/" + i, slangs.get(5));			
		}
		testExpDefaultLang(dummyDefaultLangsPool, expectedIdx);
	}
	
	@Test
	public void testUnknownDefinedCase() {
		
		int i;
		int expectedIdx = 1;
		
		// The unknown-defined case has unknown lang and supported lang defined as default
		HashMap<String, String> dummyDefaultLangsPool = new HashMap<String, String>();
		for (i = 0; i < 3; i ++) {
			dummyDefaultLangsPool.put("path/a/" + i, "unknown_supported_lang");			
		}
		for (i = 0; i < 3; i ++) {
			dummyDefaultLangsPool.put("path/a/" + i, this.fakeSupportedLangs().get(expectedIdx));			
		}
		testExpDefaultLang(dummyDefaultLangsPool, expectedIdx);		
	}
	
	@Test
	public void testOnlyUnknownDefinedCase() {
		
		int i;
		int expectedIdx = 0;
		
		// The only-unknown-defined case has only unknown supported lang defined as default
		HashMap<String, String> dummyDefaultLangsPool = new HashMap<String, String>();
		for (i = 0; i < 3; i ++) {
			dummyDefaultLangsPool.put("path/a/" + i, "unknown_supported_lang");			
		}
		testExpDefaultLang(dummyDefaultLangsPool, expectedIdx);
	}
	
	
	/*
	 * Assistive fields
	 **********/
	
	private Class testCls;
	private Object testObj;
	private Field supportedLangs;
	private Method decideDefaultLang;
	
	
	/*
	 * Assistive methods
	 **********/
	
	private ArrayList<String> fakeSupportedLangs() {
		ArrayList<String> langs = new ArrayList<String>();
		langs.add("en");
		langs.add("zh");
		langs.add("jp");
		langs.add("ru");
		langs.add("ko");
		langs.add("cn");
		return langs;
	}
	
	private void testExpDefaultLang(HashMap<String, String> dummyDefaultLangsPool, int expectedIdx) {
		
		try {
			
			Object[] args = {
				dummyDefaultLangsPool
			};
			this.decideDefaultLang.invoke(this.testObj, args);
			
			Field defaultLangIdx = Uintility.Reflection.getPrivateField(this.testCls, "defaultLangIdx");
			assertEquals(expectedIdx, defaultLangIdx.get(this.testObj));
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}		
	}
}
