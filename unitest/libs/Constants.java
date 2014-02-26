package libs;

public class Constants {
	
	public static final String[] VALID_QUANTITIES = {
        "zero", "one", "two", "few", "many", "other"
    };
	
	public static final String[] STRING_VALUES = {
		"Java", "PHP", "C and C++", "Javascript", "SQLite", "HTML5"
    };
	
	public static final String RES_ROOT = "./res";
	
	public static final String RES_XML_NAME = "strings.xml";
	
	public static final String[] RES_PATHS = {
		Constants.RES_ROOT + "/values/" + Constants.RES_XML_NAME,
		Constants.RES_ROOT + "/values-en/" + Constants.RES_XML_NAME,
		Constants.RES_ROOT + "/values-zh/" + Constants.RES_XML_NAME,
		Constants.RES_ROOT + "/values-jp/" + Constants.RES_XML_NAME
	};
	
}
