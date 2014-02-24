package androidStringResources;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class AndroidStringTest {

	@Test
	public void testGetValue() {
		ArrayList<AndroidString> ass = new ArrayList<AndroidString>();
		String[] names = {
			"Googler", "Mozillar", "KKBOXer"	
		};
		String[] values = {
			"Google", "Mozilla", "KKBOX"	
		};
		for (int i = 0; i < names.length; i++) {
			ass.add(new AndroidString(names[i], values[i]));
			Assert.assertEquals(values[i], ass.get(i).getValue());
		}
	}

}
