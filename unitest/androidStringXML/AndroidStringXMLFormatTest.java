package androidStringXML;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import unitestLibs.Constants;
import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidQuantityString.AndroidQuantityItem;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;

public class AndroidStringXMLFormatTest {

	@Test
	public void testFormatString_NormalCase() {
		String name = "AndroidString";
		String value = "AndroidString";
		AndroidString s = new AndroidString(name, value);
		String expected = "<string name=\"" + name + "\">" + value + "</string>";
		assertEquals(expected, AndroidStringXMLFormat.formatString(s));
	}

	@Test
	public void testFormatStringArray_NormalCase() {
		String name = "AndroidStringArray";
		
		ArrayList<String> itms = new ArrayList<String>();
		itms.add("ITEM_0");
		itms.add("ITEM_1");
		itms.add("ITEM_2");
		
		String expected = "<string-array name=\"" + name + "\">"
						+ 		"<item>" + itms.get(0) + "</item>"
						+ 		"<item>" + itms.get(1) + "</item>"
						+ 		"<item>" + itms.get(2) + "</item>"
						+ "</string-array>";
		
		AndroidStringArray sa = new AndroidStringArray(name, itms);
		
		assertEquals(expected, AndroidStringXMLFormat.formatStringArray(sa));
	}

	@Test
	public void testFormatStringArray_NoArrayItemCase() {
		String name = "AndroidStringArray";
		
		ArrayList<String> itms = new ArrayList<String>();
		
		String expected = "";
		
		AndroidStringArray sa = new AndroidStringArray(name, itms);
		
		assertEquals(expected, AndroidStringXMLFormat.formatStringArray(sa));
	}

	@Test
	public void testFormatQuantityString_NormalCase() {
		
		String expected = "";
		String name = "AndroidQuantityString";
		
		AndroidQuantityItem itm;
		ArrayList<AndroidQuantityItem> itms = new ArrayList<AndroidQuantityItem>();
		for (int i = 0; i < Constants.VALID_QUANTITIES.length; i++) {
			itm = new AndroidQuantityItem(Constants.VALID_QUANTITIES[i], "ITEM_" + Integer.toString(i));
			expected += "<item quantity=\"" + itm.quantity + "\">" + itm.value + "</item>";
			itms.add(itm);
		}		
		expected = "<plurals name=\"" + name + "\">" + expected + "</plurals>";
		
		AndroidQuantityString qs = new AndroidQuantityString(name, itms);
		
		assertEquals(expected, AndroidStringXMLFormat.formatQuantityString(qs));
	}

	@Test
	public void testFormatQuantityString_NoValidQuantityItemCase() {
		
		String expected = "";
		String name = "AndroidQuantityString";
		
		AndroidQuantityItem itm;
		ArrayList<AndroidQuantityItem> itms = new ArrayList<AndroidQuantityItem>();
		for (int i = 0; i < Constants.VALID_QUANTITIES.length; i++) {
			itm = new AndroidQuantityItem("NOT_VALID", "ITEM_" + Integer.toString(i));
			itms.add(itm);
		}
		
		AndroidQuantityString qs = new AndroidQuantityString(name, itms);
		
		assertEquals(expected, AndroidStringXMLFormat.formatQuantityString(qs));
	}

}
