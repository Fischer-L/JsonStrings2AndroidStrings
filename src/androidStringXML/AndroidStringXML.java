/**
 *
 * @author Fischer_Liu
 */

package androidStringXML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import libs.MyException;
import libs.Utility;
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
        
        private ArrayList<Object> kickoutDulplicateName(String lang, ArrayList<? extends AndroidStringBase> ls) {
                            
        	int i;
        	
        	String name;
            
            ArrayList<Object> cleanls = new ArrayList<Object>();
            
            ArrayList<String> names = this.nameMap.get(lang);
            if (names == null) {            
            	names = new ArrayList<String>(); // Not yet create the name list for this lang so create one
            }
            
                       
            // Collect the elements without duplicated name
            for (i = 0; i < ls.size(); i++) {
                try {
                    
                	AndroidStringBase s = (AndroidStringBase) ls.get(i);
                    name = s.getName();
                    
                    if (names.indexOf(name) >= 0) {                        
                    	
                        throw new MyException(String.format("The duplicated android:name=\"%s\" in the %s language", name, lang));
                    
                    } else {                        
                        // Put the 1st-met name in the name list so next time we could check the duplicate against it
                        names.add(name);
                        cleanls.add((Object) s);
                    }
                    
                } catch (MyException e) {
                    e.print1stPoint();
                }
            	
            }
            
            this.nameMap.put(lang, names); // Update the name list
            
            return cleanls;
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
                	
                    xml = this.buildXML(s, this.provider.getStrings(s))
                    	+ this.buildXML(s, this.provider.getStringArrays(s))
                    	+ this.buildXML(s, this.provider.getQuantityStrings(s));
                    
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
        
        private String buildXML(String lang, ArrayList<? extends AndroidStringBase> ls) {
        	
        	String xml = ""; 
        	ArrayList<Object> list = this.kickoutDulplicateName(lang, ls);
        	
        	if (list.size() > 0) {
        		
        		Class targetCls;
        		StringBuilder sb = new StringBuilder();
        		
        		try {
        		
	        		for (Object o : list) {
	        			
	        			targetCls = o.getClass();
	        			
	        			if (targetCls == AndroidString.class) {	
	        				
	        				sb.append(androidStringXMLFormat.formatString((AndroidString) o));
	        				
	        			} else if (targetCls == AndroidStringArray.class) {	
	        				
	        				sb.append(androidStringXMLFormat.formatStringArray((AndroidStringArray) o));
	        				
	        			} else if (targetCls == AndroidQuantityString.class) {
	        				
	        				sb.append(androidStringXMLFormat.formatQuantityString((AndroidQuantityString) o));
	        				
	        			} else {
	        				throw new MyException("Unknown resouce class : " + targetCls.toString());
	        			}
	        		}
	        		
	        		xml = sb.toString();
        		
        		} catch (MyException e) {
        			e.print1stPoint();
        		}
        		
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
    
    private static class StringResourcesWriter {
    	
    	public StringResourcesWriter() {}
    	
    	/*
    	 * Methods
    	 **********/
    	
    	public void writeXML(String dstPath, String xml) {
    		
    		// Open the file
// TO DEL
//    		File f = new File(dstPath);
//    		if (!f.exists()) {
//    			
//    			try {    				
//    				if (!f.getParentFile().mkdirs()) {
//    					throw new MyException("Fail to make the dirs for the file path" + dstPath + "!");
//    				}
//    			} catch(MyException e) {
//    				e.print1stPoint();
//    				return;
//    			}
//    			
//    			try {
//    				f.createNewFile();
//    			} catch (IOException e) {
//    				(new MyException("Fail to create to the file:" + dstPath + "!")).print1stPoint();
//    				return;
//    			}
//    		}
//    		
//    		f.setReadable(true, false);
//    		f.setWritable(false, false);
    		
    		// Create the buffer for writing
    		
// TO DEL
//    		try {
//				bw = new BufferedWriter(new FileWriter(f));
//			} catch (IOException e) {
//				(new MyException("Fail to create the BufferedWriter instance!")).print1stPoint();
//				return;
//			}
    		
    		// Write the xml to the file
    		try {
    			BufferedWriter bw = Utility.Files.bufferFileWriter(Utility.Files.openFile(dstPath, true, true, false, false));
				bw.write(xml);
				bw.close();
			} catch (Exception e) {
				(new MyException("Fail to write to the file:" + dstPath + "!")).print1stPoint();
			}
    	}
    }
}
