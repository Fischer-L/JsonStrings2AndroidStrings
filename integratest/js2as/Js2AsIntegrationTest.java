package js2as;

import static org.junit.Assert.*;

import java.util.ArrayList;

import libs.MyException;
import libs.Utility;
import org.junit.Test;


public class Js2AsIntegrationTest {

	@Test
	public void testMain() {
		Js2As.main(getArgsForTest(ONE_SOURCE_NORMAL_CASE));
		assertExpected(ONE_SOURCE_NORMAL_CASE);
		
		Js2As.main(getArgsForTest(ONE_SOURCE_DUPLICATE_NAME_CASE));
		assertExpected(ONE_SOURCE_DUPLICATE_NAME_CASE);

		Js2As.main(getArgsForTest(ONE_SOURCE_NO_DEFAULT_LANG_CASE));
		assertExpected(ONE_SOURCE_NO_DEFAULT_LANG_CASE);

		Js2As.main(getArgsForTest(ONE_SOURCE_INVALID_FORMAT_CASE_A));
		assertExpected(ONE_SOURCE_INVALID_FORMAT_CASE_A);

		Js2As.main(getArgsForTest(ONE_SOURCE_INVALID_FORMAT_CASE_B));
		assertExpected(ONE_SOURCE_INVALID_FORMAT_CASE_B);
		
		Js2As.main(getArgsForTest(MULTIPLE_SOURCE_NORMAL_CASE));
		assertExpected(MULTIPLE_SOURCE_NORMAL_CASE);

		Js2As.main(getArgsForTest(MULTIPLE_SOURCE_DUPLICATE_NAME_CASE));
		assertExpected(MULTIPLE_SOURCE_DUPLICATE_NAME_CASE);

		Js2As.main(getArgsForTest(MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A));
		assertExpected(MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A);

		Js2As.main(getArgsForTest(MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE));
		assertExpected(MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE);
	}
	
	
	/*
	 * Assistive members
	 **********/	

	private static final String RES_ROOT_DIR = "./integratest/integratestRes/outputs";
	private static final String JSON_SOURCE_ROOT_DIR = "./integratest/integratestRes/json_sources";
	private static final String EXPECTED_OUTPUTS_ROOT_DIR = "./integratest/integratestRes/expected_outputs";
	
	private static final int ONE_SOURCE_NORMAL_CASE = 0; // ONE_SOURCE_NORMAL_CASE.json
	private static final int ONE_SOURCE_DUPLICATE_NAME_CASE = 1; // ONE_SOURCE_DUPLICATE_NAME_CASE.json
	private static final int ONE_SOURCE_NO_DEFAULT_LANG_CASE = 2; // ONE_SOURCE_NO_DEFAULT_LANG_CASE.json
	private static final int ONE_SOURCE_INVALID_FORMAT_CASE_A = 3; // ONE_SOURCE_INVALID_FORMAT_CASE_A.json
	private static final int ONE_SOURCE_INVALID_FORMAT_CASE_B = 4; // ONE_SOURCE_INVALID_FORMAT_CASE_B.json
	
	private static final int MULTIPLE_SOURCE_NORMAL_CASE = 5; // MULTIPLE_SOURCE_NORMAL_CASE_S1~2.json
	private static final int MULTIPLE_SOURCE_DUPLICATE_NAME_CASE = 6; // MULTIPLE_SOURCE_DUPLICATE_NAME_CASE_S1~2.json
	private static final int MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A = 7; // MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A_S1~2.json
	private static final int MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE = 8; // MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE_S1~3.json
		
	private static String[] getArgsForTest(int caseType) {
		
		ArrayList<String> args = new ArrayList<String>();
		
		args.add(RES_ROOT_DIR);
		
		String jsonSourceDir = JSON_SOURCE_ROOT_DIR;
		
		switch (caseType) {
		
			case ONE_SOURCE_NORMAL_CASE:
				jsonSourceDir += "/ONE_SOURCE_NORMAL_CASE/";
				args.add(jsonSourceDir + "ONE_SOURCE_NORMAL_CASE.json");
			break;
			
			case ONE_SOURCE_DUPLICATE_NAME_CASE:
				jsonSourceDir += "/ONE_SOURCE_DUPLICATE_NAME_CASE/";
				args.add(jsonSourceDir + "ONE_SOURCE_DUPLICATE_NAME_CASE.json");
			break;
			
			case ONE_SOURCE_NO_DEFAULT_LANG_CASE:
				jsonSourceDir += "/ONE_SOURCE_NO_DEFAULT_LANG_CASE/";
				args.add(jsonSourceDir + "ONE_SOURCE_NO_DEFAULT_LANG_CASE.json");
			break;
			
			case ONE_SOURCE_INVALID_FORMAT_CASE_A:
				jsonSourceDir += "/ONE_SOURCE_INVALID_FORMAT_CASE_A/";
				args.add(jsonSourceDir + "ONE_SOURCE_INVALID_FORMAT_CASE_A.json");
			break;
			
			case ONE_SOURCE_INVALID_FORMAT_CASE_B:
				jsonSourceDir += "/ONE_SOURCE_INVALID_FORMAT_CASE_B/";
				args.add(jsonSourceDir + "ONE_SOURCE_INVALID_FORMAT_CASE_B.json");
			break;
			case MULTIPLE_SOURCE_NORMAL_CASE:
				jsonSourceDir += "/MULTIPLE_SOURCE_NORMAL_CASE/";
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_NORMAL_CASE_S1.json");
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_NORMAL_CASE_S2.json");
			break;
			
			case MULTIPLE_SOURCE_DUPLICATE_NAME_CASE:
				jsonSourceDir += "/MULTIPLE_SOURCE_DUPLICATE_NAME_CASE/";
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_DUPLICATE_NAME_CASE_S1.json");
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_DUPLICATE_NAME_CASE_S2.json");
			break;
			
			case MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A:
				jsonSourceDir += "/MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A/";
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A_S1.json");
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A_S2.json");
			break;
			
			case MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE:
				jsonSourceDir += "/MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE/";
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE_S1.json");
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE_S2.json");
				args.add(jsonSourceDir + "MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE_S3.json");
			break;
			
			default:
				MyException e = new MyException("Unknown integration test case!!!");
				e.print1stPoint();
			break;
		}
		
		return (String[]) args.toArray(new String[args.size()]);
	}

	private static void assertExpected(int caseType) {
		
		String expOutputDir = EXPECTED_OUTPUTS_ROOT_DIR;
		
		switch (caseType) {
		
			case ONE_SOURCE_NORMAL_CASE:
				expOutputDir += "/ONE_SOURCE_NORMAL_CASE";
			break;
			
			case ONE_SOURCE_DUPLICATE_NAME_CASE:
				expOutputDir += "/ONE_SOURCE_DUPLICATE_NAME_CASE";
			break;
			
			case ONE_SOURCE_NO_DEFAULT_LANG_CASE:
				expOutputDir += "/ONE_SOURCE_NO_DEFAULT_LANG_CASE";
			break;
			
			case ONE_SOURCE_INVALID_FORMAT_CASE_A:
				expOutputDir += "/ONE_SOURCE_INVALID_FORMAT_CASE_A";
			break;
			
			case ONE_SOURCE_INVALID_FORMAT_CASE_B:
				expOutputDir += "/ONE_SOURCE_INVALID_FORMAT_CASE_B";
			break;
			case MULTIPLE_SOURCE_NORMAL_CASE:
				expOutputDir += "/MULTIPLE_SOURCE_NORMAL_CASE";
			break;
			
			case MULTIPLE_SOURCE_DUPLICATE_NAME_CASE:
				expOutputDir += "/MULTIPLE_SOURCE_DUPLICATE_NAME_CASE";
			break;
			
			case MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A:
				expOutputDir += "/MULTIPLE_SOURCE_INVALID_FORMAT_CASE_A";
			break;
			
			case MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE:
				expOutputDir += "/MULTIPLE_SOURCE_MULTIPLE_DEFAULT_LANG_CASE";
			break;
			
			default:
				MyException e = new MyException("Unknown integration test case!!!");
				e.print1stPoint();
			break;
		}
		
		String actual;
		String expected;		
		String[] xmlPaths = {
			"/values/strings.xml", "/values-en/strings.xml", "/values-es/strings.xml"
		};	
		for (String p : xmlPaths) {
			actual = Utility.Files.readFileAll(RES_ROOT_DIR + p);
			expected = Utility.Files.readFileAll(expOutputDir + p);
			assertEquals("Fail case = " + caseType + " at " + p + " >>> ", expected, actual);
		}
		
		Utility.Files.deleteAll(RES_ROOT_DIR);
	}
}
