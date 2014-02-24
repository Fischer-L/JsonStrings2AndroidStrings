package androidStringXML;

import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;

class androidStringXMLFormat {
    /*
     * Fields
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
    
    public static String formatString(AndroidString s) {
        return String.format(androidStringXMLFormat.stringTmpl, s.getName(), s.getValue());
    }
            
    public static String formatStringArray(AndroidStringArray sa) {
                    
        StringBuilder builder = new StringBuilder();
        String item;
        
        // Format the <item>s elements
        while ((item = sa.nextItem()) != null) {
            builder.append(String.format(androidStringXMLFormat.stringArrayItemTmpl, item));
        }
        
        // Compose the complete <string-array> element
        return String.format(androidStringXMLFormat.stringArrayTagTmpl, sa.getName(), builder.toString());
    }
    
    public static String formatQuantityString(AndroidQuantityString qs) {
                     
        AndroidQuantityString.AndroidQuantityItem item;
        StringBuilder builder = new StringBuilder();
        
        // Format the <item>s elements
        while ((item = qs.nextItem()) != null) {
            builder.append(String.format(androidStringXMLFormat.quantityStringItemTmpl, item.quantity, item.value));
        }
        
        // Compose the complete <plurals> element
        return String.format(androidStringXMLFormat.quantityStringTagTmpl, qs.getName(), builder.toString());
    }

}
