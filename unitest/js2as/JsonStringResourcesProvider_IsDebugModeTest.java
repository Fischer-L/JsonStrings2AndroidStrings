package js2as;

import static org.junit.Assert.*;

import org.junit.Test;

public class JsonStringResourcesProvider_IsDebugModeTest {
	
	@Test // A test reminding us whether we are in the debug mode.
	public void testIsDebugMode() {	
		assertFalse("You are now in the debug mode. Are you sure this is ok ?", JsonStringResourcesProvider.isDebugMode());
	}

}
