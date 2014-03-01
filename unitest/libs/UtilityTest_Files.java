package libs;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import unitestLibs.Constants;

public class UtilityTest_Files {
	
	@Test
	public void testAccesExistingFile_S1_OpenFile() {
		assertTrue(null != Utility.Files.openFile(this.existingFilePath, true, false, false, false));
	}
	
	@Test
	public void testAccesExistingFile_S2_readFileAll() {
		String reads = Utility.Files.readFileAll(Utility.Files.openFile(this.existingFilePath, true, false, false, false));
		assertEquals(Constants.TEST_RES_JSON_CONTENT, reads);
	}
	
	@Test
	public void testAccesNewFile_S1_WriteFile() {
		BufferedWriter bw;
		for (String path : this.paths) {
			bw = Utility.Files.bufferFileWriter(Utility.Files.openFile(path, true, true, false, false));
			try {
				bw.write(this.text);
				bw.close();
				assertTrue(true);
			} catch (IOException e) {
				e.printStackTrace();
				assertTrue(false);
			}
		}
	}
	
	@Test
	public void testAccesNewFile_S2_readFileAll() {
		File f;
		for (String path : this.paths) {
			f = Utility.Files.openFile(path, true, true, false, false);
			Assert.assertEquals(this.text, Utility.Files.readFileAll(f));
		}		
	}
	
	@Test
	public void testAccesNewFile_S3_deleteAll() {
		File f = new File(this.rootPath);
		Utility.Files.deleteAll(f);
		assertTrue(!f.exists());		
	}
	
	@Ignore
	public void bufferFileWriter() {
		// Testing this is equal to test the Java API, so not doing the redundant test again.
	}
	
	@Ignore
	public void bufferFileReader() {
		// Testing this is equal to test the Java API, so not doing the redundant test again.		
	}
	
	@Ignore
	public void deleteAll_StringSignature() {
		// Too simple to test.
	}
	
	
	/*
	 * Assistive fields
	 **********/
	private String existingFilePath = Constants.TEST_RES_JSON;
	private String text = "This is a unit test on Uitility.Files class.";
	private String rootPath = Constants.RES_ROOT;
	private String[] paths = Constants.RES_PATHS;
}
