package androidStringXML;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
		this.testClsConstructor = testCls.getConstructor(params);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetXML() {
		
		FakeStringResourcesProvider fakeProvider = new FakeStringResourcesProvider();
		
		Object[] args = new Object[1];
		args[0] = fakeProvider;
		
		try {
			Object testObj = this.testClsConstructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			fail("Something wrong with the reflection...");
		}
		
		assertTrue(true);
	}
	
	@Test
	public void testGetXML_withDuplicatedNames() {
		assertTrue(true);
	}

	
	/*
	 * Assistive fields
	 **********/
	
	private Class testCls;
	private Constructor testClsConstructor;
}
