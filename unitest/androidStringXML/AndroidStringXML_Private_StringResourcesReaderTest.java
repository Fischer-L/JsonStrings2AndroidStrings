package androidStringXML;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import unitestLibs.Constants;
import unitestLibs.FakeStringResourcesProvider;
import unitestLibs.Uintility;
import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;
import androidStringResources.IStringResourcesProvider;
import androidStringResources.AndroidQuantityString.AndroidQuantityItem;

public class AndroidStringXML_Private_StringResourcesReaderTest {

	@Before
	public void setUp() throws Exception {
		Class[] params = { 
			IStringResourcesProvider.class
		};
		this.testCls = Class.forName("androidStringXML.AndroidStringXML$StringResourcesReader");
		this.testClsConstructor = this.testCls.getConstructor(params);
	}

	@Ignore
	public void testGetSupportedLangs() {
		// Too simple to test. The tests on the getXML method will test this btw so not testing here.
	}
	
	@Test
	public void testGetXML() {
		
		FakeProvider fakeProvider = new FakeProvider();
		
		// New the test obj
		Object[] args = { fakeProvider };
		Object testObj = this.newTestObj(args);	
		
		// Get the testing private method
		Class[] params = { String.class };
		Method getXML = this.getPrivateMethod("getXML", params);
		
		// Test
		String[] langs = fakeProvider.getSupportedLangs();
		for (String lang : langs) {			
			try {
				
				args[0] = lang;
				assertEquals(fakeProvider.getExpectedXML(lang), getXML.invoke(testObj, args));
				
			} catch (IllegalAccessException
					 | IllegalArgumentException
					 | InvocationTargetException e
			) {
				e.printStackTrace();
				fail("Something wrong with the reflection on invoking method...");
			}
		}
		
	}
	
	@Test
	public void testGetXML_withDuplicatedNames() {
		
		FakeProvider fakeProvider = new FakeProvider(FakeProvider.TEST_DOUBLE_NAME);
		
		// New the test obj
		Object[] args = { fakeProvider };
		Object testObj = this.newTestObj(args);	
		
		// Get the testing private method
		Class[] params = { String.class };
		Method getXML = this.getPrivateMethod("getXML", params);
		
		// Test
		String[] langs = fakeProvider.getSupportedLangs();
		for (String lang : langs) {			
			try {
				
				args[0] = lang;
				assertEquals(fakeProvider.getExpectedXML(lang), getXML.invoke(testObj, args));
				
			} catch (IllegalAccessException
					 | IllegalArgumentException
					 | InvocationTargetException e
			) {
				e.printStackTrace();
				fail("Something wrong with the reflection on invoking method...");
			}
		}
	}

	
	/*
	 * Assistive fields
	 **********/
	
	private Class testCls;
	private Constructor testClsConstructor;
	
	
	/*
	 * Assistive method
	 **********/
	
	private Object newTestObj(Object[] args) {		
		return Uintility.Reflection.newTestObj(this.testClsConstructor, args);
	}
	
	private Method getPrivateMethod(String name, Class[] params) {		
		return Uintility.Reflection.getPrivateMethod(this.testCls, name, params);
	}


	/*
	 * Assistive classes
	 **********/
	
	private static class FakeProvider extends FakeStringResourcesProvider {
		
		public FakeProvider() {
			super();
		}
		
		public FakeProvider(String testCase) {
			this.testCase = testCase;
		}

		@Override
		public ArrayList<AndroidString> getStrings(String lang) {	
			
			ArrayList<AndroidString> sList = super.getStrings(lang);
			
			if (this.testCase.equals(FakeProvider.TEST_DOUBLE_NAME)) {
				Integer i;
				for (i = sList.size() - 1; i >= 0 ; i--) {
					sList.add(new AndroidString(sList.get(i).getName(), i.toString()));
				}
			}
			
			return sList;
		}

		@Override
		public ArrayList<AndroidStringArray> getStringArrays(String lang) {

			ArrayList<AndroidStringArray> saList = super.getStringArrays(lang);
			
			if (this.testCase.equals(FakeProvider.TEST_DOUBLE_NAME)) {
				String[] sv = this.getStrValues(lang);
				ArrayList<String> itmList = new ArrayList<String>();
				for (int i = 3; i < 6; i++) {
					itmList.add(sv[i]);
				}
				saList.add(new AndroidStringArray(this.stringNames[0], itmList));
				saList.add(new AndroidStringArray(this.stringArrayNames[0], itmList));
			}
			
			return saList;
		}

		@Override
		public ArrayList<AndroidQuantityString> getQuantityStrings(String lang) {
			
			ArrayList<AndroidQuantityString> qsList = super.getQuantityStrings(lang);
			
			if (this.testCase.equals(FakeProvider.TEST_DOUBLE_NAME)) {
				AndroidQuantityItem itm;
				String[] sv = this.getStrValues(lang);
				ArrayList<AndroidQuantityItem> itmList = new ArrayList<AndroidQuantityItem>();
				for (int i = 3; i < 6; i++) {
					itm = new AndroidQuantityItem(Constants.VALID_QUANTITIES[i],sv[i]);
					itmList.add(itm);
				}
				qsList.add(new AndroidQuantityString(this.stringNames[1], itmList));
				qsList.add(new AndroidQuantityString(this.stringArrayNames[1], itmList));
				qsList.add(new AndroidQuantityString(this.quantityStringNames[1], itmList));
			}	
			
			return qsList;
		}
		
		
		/*
		 * Assistive fields
		 **********/
		public static final String TEST_DOUBLE_NAME = "TEST_DOUBLE_NAME";
		private String testCase = "";

	}
}
