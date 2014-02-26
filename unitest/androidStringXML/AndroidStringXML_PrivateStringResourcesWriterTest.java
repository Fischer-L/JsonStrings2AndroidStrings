package androidStringXML;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Assert;

import libs.Constants;
import libs.Utility;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AndroidStringXML_PrivateStringResourcesWriterTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.testCls = Class.forName("androidStringXML.AndroidStringXML$StringResourcesWriter");
		this.testClsConstructor = this.testCls.getConstructor(null);
		this.testObj = this.newTestObj(null);
		Class[] params = {
			String.class, String.class	
		};
		this.testMethod = this.getPrivateMethod("writeXML", params);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteXML_OnThe1stTime() {
		this.writeXMLs(this.contents[0]);
		this.cmpXMLs(this.contents[0]);
	}

	@Test // This test shall run behind the testWriteXML_OnThe1stTime test
	public void testWriteXML_OnThe2ndTime() {
		this.writeXMLs(this.contents[1]);
		this.cmpXMLs(this.contents[1]);
	}
	
	
	/*
	 * Assistive fields
	 **********/
	
	private Class testCls;
	private Object testObj;
	private Method testMethod;
	private Constructor testClsConstructor;
	private String[] resPaths = Constants.RES_PATHS;
	private String[] contents = {
		"Write on the 1st time", "Write on the 2nd time"
	};
	
	/*
	 * Assistive methods
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

	private void writeXMLs(String content) {
		BufferedWriter bw;
		for (String path : this.resPaths) {
			bw = Utility.Files.bufferFileWriter(Utility.Files.openFile(path, true, true, false, false));
			try {
				bw.write(content);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void cmpXMLs(String expectedContent) {
		File f;
		for (String path : this.resPaths) {
			f = Utility.Files.openFile(path, true, true, false, false);
			Assert.assertEquals(expectedContent, Utility.Files.readFileAll(f));
		}
	}
}
