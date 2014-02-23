/**
 *
 * @author Fischer_Liu
 */

package androidStringResourcesGenerator;

import androidStringResources.*;
import java.util.ArrayList;
import java.util.Map;
import libs.MyException;


public class AndroidStringXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }
    
    
    /**
     * Classes
     **/
    
    private static class xmlFormat {
        /**
         * Properties
         **/
        
        public static final String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        public static final String openingTag = "<resources>";
        public static final String closingTag = "</resources>";
        public static final String stringTmpl = "<string name=\"%s\">%s</string>";
        public static final String stringArrayTagTmpl = "<string-array name=\"%s\">%s</string>";
        public static final String stringArrayItemTmpl = "<item>%s</item>";
        public static final String quantityStringTagTmpl = "<plurals name=\"%s\">%s</plurals>";
        public static final String quantityStringItemTmpl = "<item quantity=\"%s\">%s</item>";
        
        /**
         * Methods
         **/
        
        public static String formatString(AndroidString s) {
            return String.format(xmlFormat.stringTmpl, s.getName(), s.getValue());
        }
                
        public static String formatStringArray(AndroidStringArray sa) {
                        
            StringBuilder builder = new StringBuilder();
            String item;
            
            // Format the <item>s elments
            while ((item = sa.nextItem()) != null) {
                builder.append(String.format(xmlFormat.stringArrayItemTmpl, item));
            }
            
            // Compose the complete <string-array> element
            return String.format(xmlFormat.stringArrayTagTmpl, sa.getName(), builder.toString());
        }
        
        public static String formatQuantityString(AndroidQuantityString qs) {
                         
            AndroidQuantityString.AndroidQuantityItem item;
            StringBuilder builder = new StringBuilder();
            
            // Format the <item>s elments
            while ((item = qs.nextItem()) != null) {
                builder.append(String.format(xmlFormat.quantityStringItemTmpl, item.quantity, item.value));
            }
            
            // Compose the complete <plurals> element
            return String.format(xmlFormat.quantityStringTagTmpl, builder.toString());
        }
                
    }
    
    private class StringResourcesReader {
        
        public StringResourcesReader(IStringResourcesProvider provider) {
            this.provider = provider;
            this.readStringResources();
        }
        
        
        /**
         * Properties
         **/
        
        private IStringResourcesProvider provider;        
        private Map<String, String> xmlBody = null;
        private Map<String, String> nameMap = null;        
        
        /**
         * Methods
         **/
                
        private int kickoutDulplicateName(String lang, ArrayList<? extends androidStringResources.AndroidStringBase> ls) {
            
            int duplicated = 0;
            String name;
            
            for (androidStringResources.AndroidStringBase s : ls) {
                try {
                    
                    name = s.getName();
                    
                    if (this.nameMap.get(name) != null) {
                        
                        ls.remove(s);
                        duplicated++;
                        throw new MyException(String.format("The duplicated android:name=\"%s\" in the %s language", name, lang));
                    
                    } else {
                        
                        // Put the 1st-met name in the nameMap so next time we could check the duplicate against it
                        this.nameMap.put(lang, name);
                    }
                    
                } catch (MyException e) {
                    e.print1stPoint();
                }
            }
            
            return duplicated;
        }
        
        private int readStringResources() {
            
            int readCount = 0; // A flag to mark how many lang whose string resource has been read out
            String[] langs = this.provider.getSupportedLangs();
            
            try {
                
                if (langs.length <= 0) {
                    throw new MyException("No supported langs were found!");
                }
                
                String xml;
                for (String s : langs) {                    
                    xml = this.readStringsXML(s) + this.readStringArraysXML(s) + this.readQuantityStringsXML(s);
                    if (xml != "") {
                        readCount++;
                        this.xmlBody.put(s, xml);
                    }
                }
                                
            } catch (MyException e) {
                e.print1stPoint();
            }
            
            return readCount;
        }
        
        private String readStringsXML(String lang) {
            
            String xml = "";            
            ArrayList<androidStringResources.AndroidString> list = this.provider.getStrings(lang);
            
            this.kickoutDulplicateName(lang, list);
            
            if (list != null) {
                
                StringBuilder sb = new StringBuilder();
                
                for (androidStringResources.AndroidString s : list) {
                    sb.append(xmlFormat.formatString(s));
                }
                
                xml = sb.toString();
            }
                        
            return xml;
        }
        
        private String readStringArraysXML(String lang) {
            
            String xml = "";            
            ArrayList<androidStringResources.AndroidStringArray> list = this.provider.getStringArrays(lang);
                        
            this.kickoutDulplicateName(lang, list);
            
            if (list != null) {
                
                StringBuilder sb = new StringBuilder();
                
                for (androidStringResources.AndroidStringArray sa : list) {
                    sb.append(xmlFormat.formatStringArray(sa));
                }
                
                xml = sb.toString();
            }
                        
            return xml;
        }
        
        private String readQuantityStringsXML(String lang) {
            
            String xml = "";            
            ArrayList<androidStringResources.AndroidQuantityString> list = this.provider.getQuantityStrings(lang);
            
            this.kickoutDulplicateName(lang, list);            
            
            if (list != null) {
                
                StringBuilder sb = new StringBuilder();
                
                for (androidStringResources.AndroidQuantityString qs : list) {
                    sb.append(xmlFormat.formatQuantityString(qs));
                }
                
                xml = sb.toString();
            }
                        
            return xml;
        }
    
        public String getXML(String lang) {
            String xmlBody = this.xmlBody.get(lang);
            return (xmlBody == null) ?
                    null :
                    xmlFormat.header + xmlFormat.openingTag + xmlBody + xmlFormat.closingTag;                   
        }
    
        public String[] getSupportedLangs() {
            return this.provider.getSupportedLangs();
        }
    }
    
    private class StringResourcesWriter {
        
    }
}
