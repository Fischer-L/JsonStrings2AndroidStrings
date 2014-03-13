package js2as;

/**
 * One class in charge of handling the args passed from the command line
 *
 */
public class ArgsMgr {
	
	/*
	 * Fields
	 *********/

	private static final String cmdTips = "Usage: java -jar js2as.jar <the_output_directory_path> <the_json_source_file_path>...\r\n-- Remark: Please use UTF-16 charset.";	
	private static final String[] CMD_HELP = { "-h", "--help" };
	
	/**
	 * The resource output directory root specified by user
	 */
	public static String ARG_RES_DIR_ROOT = null;
	
	/**
	 * The .json file paths specified by user
	 */
	public static String[] ARG_FILE_PATHS = null;
	
	
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
				
		// Not correct args input or ask for help...
		if (args.length <= 1
			|| ArgsMgr.CMD_HELP[0].equals(args[0])
			|| ArgsMgr.CMD_HELP[1].equals(args[0])
		) {
			System.out.println(ArgsMgr.cmdTips);
			return false;
		}
		
		ArgsMgr.ARG_RES_DIR_ROOT = args[0];
		
		ArgsMgr.ARG_FILE_PATHS = new String[args.length-1];
		System.arraycopy(args, 1, ArgsMgr.ARG_FILE_PATHS, 0, ArgsMgr.ARG_FILE_PATHS.length);
		
		return true;
	}
}
