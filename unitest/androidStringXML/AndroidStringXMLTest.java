package androidStringXML;

import libs.Utility;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import unitestLibs.Constants;
import unitestLibs.FakeStringResourcesProvider;

public class AndroidStringXMLTest {
	
	@After
	public void tearDown() {
		Utility.Files.deleteAll(this.resRoot);
	}

	@Ignore
	public void testSetResRootDir() {
		// Too simple to test
	}

	@Ignore
	public void testSetResProvider() {
		// Too simple to test
	}

	@Test
	public void testGenerateXMLs_NormalCase() {
		this.testGenerateXMLs(new FakeProvider(FakeProvider.CASE_NORMAL));
	}
	
	@Test
	public void testGenerateXMLs_LackOfResource() {
		this.testGenerateXMLs(new FakeProvider(FakeProvider.CASE_LACK_OF_RESOURCE));
	}
	
	@Test
	public void testGenerateXMLs_NoDefaultLang() {
		this.testGenerateXMLs(new FakeProvider(FakeProvider.CASE_NO_DEFAULT_LANG));
	}

	
	/*
	 * Assistive fields
	 *********/
	
	private String resRoot = Constants.RES_ROOT;
	
	
	/*
	 * Assistive methods
	 **********/
	
	private void testGenerateXMLs(FakeProvider fp) {
		
		// Call the tested method
		AndroidStringXML asXML = new AndroidStringXML(this.resRoot, fp);
		asXML.generateXMLs();	
		
		// Compare the expected xml with the generated one
		String pathTmpl = this.resRoot + "/values%s/strings.xml";
		
		String[] langs = fp.gerRealSupportedLangs();
		for (int i = 0; i < langs.length; i++) {
			Assert.assertEquals(
				fp.getExpectedXML(langs[i]),
				Utility.Files.readFileAll(Utility.Files.openFile(String.format(pathTmpl, "-" + langs[i]), true, false, false, false))
			);			
		}

		Assert.assertEquals(
				fp.getExpectedXML(fp.getRealDefaultLang()),
				Utility.Files.readFileAll(Utility.Files.openFile(String.format(pathTmpl, ""), true, false, false, false))
		);
	}
	
	
	/*
	 * Assistive classes
	 *********/
	
	private static class FakeProvider extends FakeStringResourcesProvider {
		
		public FakeProvider(int c) {
			this.testCase = c;
		}

		@Override
		public String getDefaultLang() {
			return this.testCase.equals(FakeProvider.CASE_NO_DEFAULT_LANG) ? "N/A" : super.getDefaultLang();			
		}
		
		@Override
		public String[] getSupportedLangs() {
			
			int i = 0;
			String[] ls;
			String[] sls = super.getSupportedLangs();
			if (this.testCase.equals(FakeProvider.CASE_LACK_OF_RESOURCE)) {
				ls = new String[sls.length + 1];
				ls[0] = "N/A";
				i = 1;
			} else {
				ls = new String[sls.length];
			}
			
			for (String s : sls) {
				ls[i] = s;
				i++;
			}
			
			return ls;
		}
		
		
		/*
		 * fields
		 **********/
		
		private Integer testCase = -1;
		public static final int CASE_NORMAL = 0;
		public static final int CASE_NO_DEFAULT_LANG = 1;
		public static final int CASE_LACK_OF_RESOURCE = 2;
	
	
		/*
		 * Methods
		 **********/
		
		public String getRealDefaultLang() {
			return super.getDefaultLang();
		}
		
		public String[] gerRealSupportedLangs() {
			return super.getSupportedLangs();
		}
	}
}
