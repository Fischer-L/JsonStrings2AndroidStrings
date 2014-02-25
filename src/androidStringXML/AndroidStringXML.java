/**
 *
 * @author Fischer_Liu
 */

package androidStringXML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import libs.MyException;
import androidStringResources.AndroidQuantityString;
import androidStringResources.AndroidString;
import androidStringResources.AndroidStringArray;
import androidStringResources.AndroidStringBase;
import androidStringResources.IStringResourcesProvider;


public class AndroidStringXML {

    /**
     * @param args the command line arguments
     */
    public AndroidStringXML() {
        
    }
    
    
    /*
     * Classes
     *********/
    
    private static class StringResourcesReader {
        
        public StringResourcesReader(IStringResourcesProvider provider) {
            this.provider = provider;
            this.readStringResources();
        }
        

        /*
         * Fields
         ***********/
        
        private IStringResourcesProvider provider;        
        private Map<String, String> xmlBody = new HashMap<String, String>();
        private Map<String, ArrayList<String>> nameMap = new HashMap<String, ArrayList<String>>();        
        
        
        /*
         * Methods
         **********/
        
        private ArrayList<? extends AndroidStringBase> kickoutDulplicateName(String lang, ArrayList<? extends AndroidStringBase> ls) {
                            
        	String name;
            
            ArrayList<Integer> duplicateds = new ArrayList<Integer>();
            
            ArrayList<String> names = this.nameMap.get(lang);
            if (names == null) {            
            	names = new ArrayList<String>(); // Not yet create the name list for this lang so create one
            }
            
            // Collect the indexes of the elements with duplicated name
            // Notice: we loop through from the arrayList's tail to the head here
            for (int i = ls.size() - 1; i >= 0; i--) {
                try {
                    
                	AndroidStringBase s = ls.get(i);
                    name = s.getName();
                    
                    if (names.indexOf(name) >= 0) {
                        
                    	duplicateds.add(i);
                        throw new MyException(String.format("The duplicated android:name=\"%s\" in the %s language", name, lang));
                    
                    } else {                        
                        // Put the 1st-met name in the name list so next time we could check the duplicate against it
                        names.add(name);
                    }
                    
                } catch (MyException e) {
                    e.print1stPoint();
                }
            	
            }
                        
            // Remove the elements with duplicated name
            // Notice: Above we collect the indexes from the tail so we starting remove from the tail.
            //         In this way, we can remove the element in the right order
            if (duplicateds.size() > 0) {
            	for (Integer i : duplicateds) {
            		ls.remove(i);
            	}
            }
            
            this.nameMap.put(lang, names); // Update the name list
            
            return ls;
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
                    if (!xml.equals("")) {
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
            ArrayList<AndroidString> list =  (ArrayList<AndroidString>) this.kickoutDulplicateName(lang, this.provider.getStrings(lang));
            
            if (list.size() > 0) {
                
                StringBuilder sb = new StringBuilder();
                
                for (AndroidString s : list) {
                    sb.append(androidStringXMLFormat.formatString(s));
                }
                
                xml = sb.toString();
            }
                        
            return xml;
        }
        
        private String readStringArraysXML(String lang) {
            
            String xml = "";            
            ArrayList<AndroidStringArray> list = (ArrayList<AndroidStringArray>) this.kickoutDulplicateName(lang, this.provider.getStringArrays(lang));
            
            if (list.size() > 0) {
                
                StringBuilder sb = new StringBuilder();
                
                for (AndroidStringArray sa : list) {
                    sb.append(androidStringXMLFormat.formatStringArray(sa));
                }
                
                xml = sb.toString();
            }
                        
            return xml;
        }
        
        private String readQuantityStringsXML(String lang) {
            
            String xml = "";            
            ArrayList<AndroidQuantityString> list = (ArrayList<AndroidQuantityString>) this.kickoutDulplicateName(lang, this.provider.getQuantityStrings(lang));
            
            if (list.size() > 0) {
                
                StringBuilder sb = new StringBuilder();
                
                for (AndroidQuantityString qs : list) {
                    sb.append(androidStringXMLFormat.formatQuantityString(qs));
                }
                
                xml = sb.toString();
            }
                        
            return xml;
        }
    
        public String getXML(String lang) {
            String xmlBody = this.xmlBody.get(lang);
            return (xmlBody == null) ?
                    null :
                    androidStringXMLFormat.header + androidStringXMLFormat.openingTag + xmlBody + androidStringXMLFormat.closingTag;                   
        }
    
        public String[] getSupportedLangs() {
            return this.provider.getSupportedLangs();
        }
    }
    
    private class StringResourcesWriter {
        
    }
}
