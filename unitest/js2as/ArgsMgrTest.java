package js2as;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArgsMgrTest {

	@Test
	public void testCollectArgs_InNormalCase() {
		String[] args = {
			"root_dir/", "./a.json", "./b.json", "./c.json"
		};
		
		assertTrue(ArgsMgr.collectArgs(args));
		assertEquals(args[0], ArgsMgr.ARG_RES_DIR_ROOT);
		assertEquals(3, ArgsMgr.ARG_FILE_PATHS.length);
		for (int i = 0; i < ArgsMgr.ARG_FILE_PATHS.length; i++) {
			assertEquals(args[i+1], ArgsMgr.ARG_FILE_PATHS[i]);
		}
	}
	
	@Test
	public void testCollectArgs_InNeedHelpCase() {
		String[] args = new String[0];
		assertFalse(ArgsMgr.collectArgs(args));
		
		args = new String[1];
		args[0] = "-h";
		assertFalse(ArgsMgr.collectArgs(args));		

		args = new String[2];
		args[0] = "--help";
		args[1] = "aaa";
		assertFalse(ArgsMgr.collectArgs(args));
	}

}
