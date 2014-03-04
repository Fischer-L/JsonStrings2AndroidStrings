package androidStringResources;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Ignore;
import org.junit.Test;

public class AndroidStringItemsBaseTest {

	@Ignore
	public void testNextItem() {
		// Leave the descendant class to test this
	}

	@Ignore
	public void testSetItems() {
		// Too simple to test. The testSize should test this implicitly btw.
	}

	@Test
	public void testSize() {
		int count = 10;
		ArrayList<Integer> iList = new ArrayList<Integer>();
		
		for (int i = 0; i < count; i++) {
			iList.add(i);
		}
		
		AndroidStringItemsBaseImpl base = new AndroidStringItemsBaseImpl("test", iList);
		
		assertEquals(count, base.size());
	}

	
	/*
	 * Assistive classes
	 **********/
	
	private static class AndroidStringItemsBaseImpl extends AndroidStringItemsBase {

		public AndroidStringItemsBaseImpl(String n) {
			super(n);
		}

		public AndroidStringItemsBaseImpl(String n, ArrayList<?> its) {
			super(n, its);
		}

		@Override
		public Object nextItem() {
			return null;
		}
		
	}
}

