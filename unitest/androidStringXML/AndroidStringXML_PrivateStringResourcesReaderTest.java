package androidStringXML;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import libs.FakeStringResourcesProvider;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import androidStringResources.IStringResourcesProvider;

public class AndroidStringXML_PrivateStringResourcesReaderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Class[] params = { 
			IStringResourcesProvider.class
		};
		this.testCls = Class.forName("androidStringXML.AndroidStringXML$StringResourcesReader");
		this.testClsConstructor = this.testCls.getConstructor(params);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	public void testGetSupportedLangs() {
		// Too simple to test. The tests on the getXML method will test this btw so not testing here.
	}
	
	//@Ignore
	@Test
	public void testGetXML() {
		
		FakeStringResourcesProvider fakeProvider = new FakeStringResourcesProvider();
		
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
				assertEquals(fakeProvider.getXML(lang), getXML.invoke(testObj, args));
				
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
		
		FakeStringResourcesProvider fakeProvider = new FakeStringResourcesProvider(FakeStringResourcesProvider.TEST_DOUBLE_NAME);
		
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
				assertEquals(fakeProvider.getXML(lang), getXML.invoke(testObj, args));
				
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
		
		Object testObj = null;
		
		try {
			
			testObj = this.testClsConstructor.newInstance(args);	
			
		} catch (InstantiationException
				 | IllegalAccessException
				 | IllegalArgumentException
				 | InvocationTargetException e
		) {
			e.printStackTrace();
			fail("Something wrong with the reflection on newing instance...");
		}	
		
		return testObj;
	}
	
	private Method getPrivateMethod(String name, Class[] params) {
		
		Method method = null;
		
		try {
			
			method = this.testCls.getDeclaredMethod(name, params);
			method.setAccessible(true);
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			fail("Something wrong with the reflection on getting method...");
		}
		
		return method;
	}
}
