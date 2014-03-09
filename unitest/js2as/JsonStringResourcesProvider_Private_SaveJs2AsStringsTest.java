package js2as;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.json.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import unitestLibs.Uintility;

public class JsonStringResourcesProvider_Private_SaveJs2AsStringsTest {
	
	@Before
	public void setUp() throws Exception {
		
		Class testCls = Class.forName("js2as.JsonStringResourcesProvider");
		
		Class[] params = { String[].class };
		Object[] args = { null };
		this.testObj = Uintility.Reflection.newTestObj(testCls.getConstructor(params), args);
		
		params[0] = JSONArray.class;
		this.saveJs2AsStrings = Uintility.Reflection.getPrivateMethod(testCls, "saveJs2AsStrings", params);
	}

	@Test
	public void testNormalCase() {		
		try {			
			Object[] args = {
				new JSONArray(JSONSouce.normalCase)
			};
			assertEquals(4, this.saveJs2AsStrings.invoke(this.testObj, args));
			
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
			assertEquals(0, this.saveJs2AsStrings.invoke(this.testObj, args));
			
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
			assertEquals(2, this.saveJs2AsStrings.invoke(this.testObj, args));
			
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}		
	}

	
	/*
	 * Assistive fields
	 **********/
	
	private Object testObj;
	private Method saveJs2AsStrings;
	
	
	/*
	 * Assistive classes
	 *********/
	
	private static class JSONSouce {
		
		public static final String normalCase = ""
			+"[" // 4 valid string resources
			+	"{"
			+		"\"name\" : \"s0\","
			+		"\"resources\" : ["
			+			"{"
			+				"\"lang\" : \"en\","
			+				"\"strValue\" : \"s0\""
			+			"}, "
			+			"{"
			+				"\"lang\" : \"es\","
			+				"\"strValue\" : \"s0_in_Espanol\""
			+			"}"
			+		"]"
			+	"}, "
			+	"{"
			+		"\"name\" : \"s1\","
			+		"\"resources\" : ["
			+			"{"
			+				"\"lang\" : \"en\","
			+				"\"strValue\" : \"s1\""
			+			"}, "
			+			"{"
			+				"\"lang\" : \"es\","
			+				"\"strValue\" : \"s1_in_Espanol\""
			+			"}"
			+		"]"
			+	"}"
			+"]";
		
		public static final String invalidNameCase = ""
			+"[" // 0 valid string resources
			+	"{"
			+		"\"name\" : \"\"," // Invalid name
			+		"\"resources\" : ["
			+			"{"
			+				"\"lang\" : \"en\","
			+				"\"strValue\" : \"s0\""
			+			"}, "
			+			"{"
			+				"\"lang\" : \"es\","
			+				"\"strValue\" : \"s0_in_Espanol\""
			+			"}"
			+		"]"
			+	"}, "
			+	"{"
			+		"\"name\" : null," // Invalid name
			+		"\"resources\" : ["
			+			"{"
			+				"\"lang\" : \"en\","
			+				"\"strValue\" : \"s1\""
			+			"}, "
			+			"{"
			+				"\"lang\" : \"es\","
			+				"\"strValue\" : \"s1_in_Espanol\""
			+			"}"
			+		"]"
			+	"}"
			+"]";
		
		public static final String invalidLangCase = ""
			+"[" // 2 valid string resources
			+	"{"
			+		"\"name\" : \"s0\","
			+		"\"resources\" : ["
			+			"{"
			+				"\"lang\" : \"\"," // Invalid lang
			+				"\"strValue\" : \"s0\""
			+			"}, "
			+			"{"
			+				"\"lang\" : \"es\","
			+				"\"strValue\" : \"s0_in_Espanol\""
			+			"}"
			+		"]"
			+	"}, "
			+	"{"
			+		"\"name\" : \"s1\","
			+		"\"resources\" : ["
			+			"{"
			+				"\"lang\" : \"en\","
			+				"\"strValue\" : \"s1\""
			+			"}, "
			+			"{"
			+				"\"lang\" : null," // Invalid lang
			+				"\"strValue\" : \"s1_in_Espanol\""
			+			"}"
			+		"]"
			+	"}"
			+"]";
		
	}
}
