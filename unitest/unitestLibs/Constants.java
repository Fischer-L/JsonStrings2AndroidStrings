package unitestLibs;

public class Constants {
	
	public static final String[] VALID_QUANTITIES = {
        "zero", "one", "two", "few", "many", "other"
    };
	
	public static final String[] STRING_VALUES = {
		"Java", "PHP", "C and C++", "Javascript", "SQLite", "HTML5"
    };
	
	public static final String RES_ROOT = "./unitest/res";
	
	public static final String RES_XML_NAME = "strings.xml";
	
	public static final String[] RES_PATHS = {
		Constants.RES_ROOT + "/values/" + Constants.RES_XML_NAME,
		Constants.RES_ROOT + "/values-en/" + Constants.RES_XML_NAME,
		Constants.RES_ROOT + "/values-zh/" + Constants.RES_XML_NAME,
		Constants.RES_ROOT + "/values-jp/" + Constants.RES_XML_NAME
	};
	
	public static final String TEST_RES_ROOT = "./unitest/unitestRes";
	
	public static final String TEST_RES_JSON = Constants.TEST_RES_ROOT + "/strings.json";
	
	public static final String TEST_RES_JSON_CONTENT =   ""
														+"{"
														+"	\"name\" : \"Name\","
														+"	\"age\" : 18,"
														+"	\"addr\" : \"abc, def, 987 fg, jkl\","
														+"	\"array\" : [0, 1, 2, 3]"
														+"}";
}
