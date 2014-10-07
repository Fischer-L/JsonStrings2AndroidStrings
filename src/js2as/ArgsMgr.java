package js2as;

/**
 * One class in charge of handling the args passed from the command line
 *
 */
public class ArgsMgr {
	
	/*
	 * Fields
	 *********/

	private static final String cmdTips = "Usage: java -jar js2as.jar [-ch=<charset>] <the_output_directory_path> <the_json_source_file_path>..."
										+ "\r\n    -- Remark:"
										+ "\r\n       -ch=<charset>  Charset is used when reading files, could be UTF-8 / UTF-16. If not present, the default is UTF-8.";
	
	private static final String[] CMD_HELP = { "-h", "--help" };
	
	private static final String CMD_CHARSET = "-ch=";
	
	/**
	 * The charset used when reading files
	 */
	public static String ARG_FILE_CHARSET = "UTF-8";
	
	/**
	 * The resource output directory root specified by user
	 */
	public static String ARG_RES_DIR_ROOT;
	
	/**
	 * The .json file paths specified by user
	 */
	public static String[] ARG_FILE_PATHS;
	
	
	/*
	 * Methods 
	 **********/
	
	/**
	 * Take the args passed from the command line and store them for use. If found error, would show the help tips by the way.
	 * 
	 * @param args
	 * 		The args passed from the command line
	 * @return
	 * 		- If OK: true
	 * 		<br/>
	 * 		- If NG: false
	 */
	public static boolean collectArgs(String[] args) {
		
		int idx_ARG_RES_DIR_ROOT = -1;
		
		if (args.length > 1) {
			
			// Parse the command
			
			if (ArgsMgr.CMD_HELP[0].equals(args[0]) || ArgsMgr.CMD_HELP[1].equals(args[0])) {		
			
				// Ask for help
				
			} else if (args[0].indexOf(ArgsMgr.CMD_CHARSET) == 0 && args.length > 2) {
				
				idx_ARG_RES_DIR_ROOT = 1;
				ArgsMgr.ARG_FILE_CHARSET = args[0].substring(ArgsMgr.CMD_CHARSET.length());
				
			} else {
				
				idx_ARG_RES_DIR_ROOT = 0;
			}
			
			if (idx_ARG_RES_DIR_ROOT >= 0) {
				
				ArgsMgr.ARG_RES_DIR_ROOT = args[idx_ARG_RES_DIR_ROOT];
				
				ArgsMgr.ARG_FILE_PATHS = new String[args.length - 1 - idx_ARG_RES_DIR_ROOT];
				System.arraycopy(args, idx_ARG_RES_DIR_ROOT + 1, ArgsMgr.ARG_FILE_PATHS, 0, ArgsMgr.ARG_FILE_PATHS.length);
			}			
		}
		
		if (idx_ARG_RES_DIR_ROOT < 0) {
			System.out.println(ArgsMgr.cmdTips);
			return false;
		}
		return true;
	}
}
