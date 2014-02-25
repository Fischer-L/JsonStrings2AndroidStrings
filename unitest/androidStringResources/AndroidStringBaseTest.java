package androidStringResources;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class AndroidStringBaseTest {

	public AndroidStringBaseTest() {
	}

	@Test
	public void testGetName() {

		ArrayList<AndroidStringBaseImpl> bases = new ArrayList<AndroidStringBaseImpl>();

		String[] names = {
			"Adam", "Ada", "Mike", "Michelle"	
		};

		for (String n : names) {
			bases.add(new AndroidStringBaseImpl(n));
		}

		for (int i = names.length-1; i >= 0; i--) {
			Assert.assertEquals(names[i], bases.get(i).getName());
		}
	}

}



/*
 * Assistive class
 */
class AndroidStringBaseImpl extends AndroidStringBase {

	public AndroidStringBaseImpl(String n) {
		super(n);
	}

}