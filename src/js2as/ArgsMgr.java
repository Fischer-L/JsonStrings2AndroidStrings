package js2as;

public class ArgsMgr {
	
	/*
	 * Fields
	 *********/
	

	private static final String cmdTips = "js2as the_output_directory_path the_json_source_file_paht...";	
	private static final String[] CMD_HELP = { "-h", "--help" };
	
	public static String ARG_RES_DIR_ROOT = null;
	public static String[] ARG_FILE_PATHS = null;
	
	
	/*
	 * Methods 
	 **********/
	
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
