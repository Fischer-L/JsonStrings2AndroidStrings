package androidStringResources;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;
import libs.Constants;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AndroidStringArrayTest {

	@Before
	public void setUp() throws Exception {
		this.items = new ArrayList<String>();
		for (String s : this.ITMES) {
			this.items.add(s);
		}
	}

	@Test
	public void testNextItem() {
		String s;
		int i = 0;
		AndroidStringArray asa = new AndroidStringArray(this.NAME, this.items);
		while (
				(s = asa.nextItem()) != null
		) {
			Assert.assertEquals(this.ITMES[i], s);
			i++;
		}
	}
	
	
	/*
	 * Assistive fields
	 ***********/
	
	private String NAME = "programming_lang";
	private String[] ITMES = Constants.STRING_VALUES;
	private ArrayList<String> items;
}
