package js2as;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import unitestLibs.Uintility;

public class JsonStringResourcesProvider_Private_DecideDefaultLangTest {

	@Before
	public void setUp() throws Exception {
		
		this.testCls = Class.forName("js2as.JsonStringResourcesProvider");
		
		Class[] params = { String[].class };
		Object[] args = { null };
		this.testObj = Uintility.Reflection.newTestObj(this.testCls.getConstructor(params), args);
		
		params[0] = HashMap.class;
		this.decideDefaultLang = Uintility.Reflection.getPrivateMethod(this.testCls, "decideDefaultLang", params);
		
		this.supportedLangs = Uintility.Reflection.getPrivateField(this.testCls, "supportedLangs");
		this.supportedLangs.set(this.testObj, this.fakeSupportedLangs());
	}
	
	@Test
	public void testNormalCase() {
		
		int expectedIdx = 0;
		
		// The normal case has only one supported langs defined as default
		HashMap<String, String> dummyDefaultLangsPool = new HashMap<String, String>();
		for (int i = 0; i < 10; i ++) {
			dummyDefaultLangsPool.put("path_" + i, this.fakeSupportedLangs().get(expectedIdx));			
		}
		
		try {
			Object[] args = {
				dummyDefaultLangsPool
			};
			this.decideDefaultLang.invoke(this.testObj, args);
			testExpDefaultLangIdx(expectedIdx);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void testNoDefineCase() {
		
		int expectedIdx = 0;
		
		// The no-define case has no supported lang defined as default
		HashMap<String, String> dummyDefaultLangsPool = new HashMap<String, String>();
		for (int i = 0; i < 10; i ++) {
			dummyDefaultLangsPool.put("path_" + i, null);			
		}
		
		try {
			Object[] args = {
				dummyDefaultLangsPool
			};
			this.decideDefaultLang.invoke(this.testObj, args);
			testExpDefaultLangIdx(expectedIdx);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
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
		return langs;
	}
	
	private void testExpDefaultLangIdx(int expected) {
		try {
			Field defaultLangIdx = Uintility.Reflection.getPrivateField(this.testCls, "defaultLangIdx");
			assertEquals(expected, defaultLangIdx.get(this.testObj));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
