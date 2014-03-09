package androidStringXML;

import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;

/**
 * The class in charge of formating the Android XML.
 */
class AndroidStringXMLFormat {
    /*
     * Fields of the Android XML template
     **********/
    
    public static final String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    public static final String openingTag = "<resources>";
    public static final String closingTag = "</resources>";
    public static final String stringTmpl = "<string name=\"%s\">%s</string>";
    public static final String stringArrayTagTmpl = "<string-array name=\"%s\">%s</string-array>";
    public static final String stringArrayItemTmpl = "<item>%s</item>";
    public static final String quantityStringTagTmpl = "<plurals name=\"%s\">%s</plurals>";
    public static final String quantityStringItemTmpl = "<item quantity=\"%s\">%s</item>";
    
    /*
     * Methods
     ***********/
    
    /**
     * Format the basic string element in the Android's strings.xml file. In the strings.xml, it is like:
	 * <br/>
	 * {@code
	 * 		<string name="string_name">text_string</string>
	 * }
	 * <br/>
     * 
     * @param s
     * 		One Android string element resource.
     * @return
     * 		- If OK: One complete string element xml
     */
    public static String formatString(AndroidString s) {
        return String.format(AndroidStringXMLFormat.stringTmpl, s.getName(), s.getValue());
    }

    
    /**
     * Format the string array element in the Android's strings.xml file. In the strings.xml, it is like:
	 * <br/>
	 * {@code
	 * 		<string-array name="string_array_name">
	 * 			<item>text_string</item>
	 * 		</string-array>
	 * }
     * 
     * @param s
     * 		One Android string array element resource.
     * @return
     * 		- If OK: One complete string array element xml.
     * 		<br/>
     * 		- If NG: An empty string, "".
     */
    public static String formatStringArray(AndroidStringArray sa) {
        
    	if (sa.size() <= 0) {
    		return "";
    	}
                    
        StringBuilder builder = new StringBuilder();
        String item;
        
        // Format the <item>s elements
        sa.rewind();
        while ((item = sa.nextItem()) != null) {
            builder.append(String.format(AndroidStringXMLFormat.stringArrayItemTmpl, item));
        }

        // Compose the complete <string-array> element
        return String.format(AndroidStringXMLFormat.stringArrayTagTmpl, sa.getName(), builder.toString());
    }


    
    /**
     * Format the quantity string element in the Android's strings.xml file. In the strings.xml, it is like:
	 * <br/>
	 * {@code
	 * 		<plurals name="plural_name">
	 * 			<item quantity=["zero" | "one" | "two" | "few" | "many" | "other"]>text_string</item>
	 * 		</plurals>
	 * }
     * 
     * @param s
     * 		One Android quantity string element resource.
     * @return
     * 		- If OK: One complete quantity string element xml.
     * 		<br/>
     * 		- If NG: An empty string, "".
     */
    public static String formatQuantityString(AndroidQuantityString qs) {
        
    	if (qs.size() <= 0) {
    		return "";
    	}
    	
        AndroidQuantityString.AndroidQuantityItem item;
        StringBuilder builder = new StringBuilder();
        
        // Format the <item>s elements
        qs.rewind();
        while ((item = qs.nextItem()) != null) {
            builder.append(String.format(AndroidStringXMLFormat.quantityStringItemTmpl, item.quantity, item.value));
        }

        // Compose the complete <plurals> element	        
        return String.format(AndroidStringXMLFormat.quantityStringTagTmpl, qs.getName(), builder.toString());
    }

}
