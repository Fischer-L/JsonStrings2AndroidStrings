package js2as;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import unitestLibs.Constants;
import unitestLibs.Uintility;

public class JsonStringResourcesProvider_Private_SaveJs2AsQuantityStringsTest {

	@Before
	public void setUp() throws Exception {
		
		Class testCls = Class.forName("js2as.JsonStringResourcesProvider");
		
		Class[] params = { HashMap.class };
		Object[] args = { null };
		this.testObj = Uintility.Reflection.newTestObj(testCls.getConstructor(params), args);
		
		params[0] = JSONArray.class;
		this.saveJs2AsQuantityStrings = Uintility.Reflection.getPrivateMethod(testCls, "saveJs2AsQuantityStrings", params);
	}

	@Test
	public void testNormalCase() {
		try {
			Object[] args = {
					new JSONArray(JSONSource.normalCase)	
				};
			assertEquals(8, this.saveJs2AsQuantityStrings.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidNameCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSource.invalidNameCase)	
			};
			assertEquals(4, this.saveJs2AsQuantityStrings.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidLangCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSource.invalidLangCase)	
			};
			assertEquals(4, this.saveJs2AsQuantityStrings.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidQuantityCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSource.invalidQuantityCase)	
			};
			assertEquals(6, this.saveJs2AsQuantityStrings.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidFormatCase() {
		try {
			Object[] args = {
				new JSONArray(JSONSource.invalidFormatCase)	
			};
			assertEquals(1, this.saveJs2AsQuantityStrings.invoke(this.testObj, args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Assistive fields
	 **********/
	
	private Object testObj;
	private Method saveJs2AsQuantityStrings;

	
	/*
	 * Assistive classes
	 ***********/
	
	private static class JSONSource {		
		public static final String normalCase = Constants.JSONSource.QuantityStringSource.NORMAL_CASE;
		public static final String invalidNameCase = Constants.JSONSource.QuantityStringSource.INVALID_NAME_CASE;
		public static final String invalidLangCase = Constants.JSONSource.QuantityStringSource.INVALID_LANG_CASE;
		public static final String invalidQuantityCase = Constants.JSONSource.QuantityStringSource.INVALID_QUANTITY_CASE;
		public static final String invalidFormatCase = Constants.JSONSource.QuantityStringSource.INVALID_FORMAT_CASE;
	}
}
