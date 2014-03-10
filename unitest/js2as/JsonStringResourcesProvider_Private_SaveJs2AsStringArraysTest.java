package js2as;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import unitestLibs.Constants;
import unitestLibs.Uintility;

public class JsonStringResourcesProvider_Private_SaveJs2AsStringArraysTest {

	@Before
	public void setUp() throws Exception {
		
		Class testCls = Class.forName("js2as.JsonStringResourcesProvider");
		
		Class[] params = { String[].class };
		Object[] args = { null };
		this.testObj = Uintility.Reflection.newTestObj(testCls.getConstructor(params), args);
		
		params[0] = JSONArray.class;
		this.saveJs2AsStringArrays = Uintility.Reflection.getPrivateMethod(testCls, "saveJs2AsStringArrays", params);
	}

	@Test
	public void testNormalCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSouce.normalCase)
			};
			assertEquals(4, this.saveJs2AsStringArrays.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidNameCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSouce.invalidNameCase)
			};
			assertEquals(0, this.saveJs2AsStringArrays.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidLangCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSouce.invalidLangCase)
			};
			assertEquals(2, this.saveJs2AsStringArrays.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidItemsCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSouce.invalidItemsCase)
			};
			assertEquals(1, this.saveJs2AsStringArrays.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInvalidFormatCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSouce.invalidFormatCase)
			};
			assertEquals(1, this.saveJs2AsStringArrays.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * Assistive fields
	 **********/
	
	private Object testObj;
	private Method saveJs2AsStringArrays;

	
	/*
	 * Assistive classes
	 *********/
	
	private static class JSONSouce {		
		public static final String normalCase = Constants.JSONSource.StringArraySource.NORMAL_CASE;		
		public static final String invalidNameCase = Constants.JSONSource.StringArraySource.INVALID_NAME_CASE;		
		public static final String invalidLangCase = Constants.JSONSource.StringArraySource.INVALID_LANG_CASE;		
		public static final String invalidItemsCase = Constants.JSONSource.StringArraySource.INVALID_ITEMS_CASE;	
		public static final String invalidFormatCase = Constants.JSONSource.StringArraySource.INVALID_FORMAT_CASE;
	}
}
