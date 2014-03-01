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

    public AndroidStringXML() {        
    }
    
    public AndroidStringXML(String resRootDir, IStringResourcesProvider resProvider) {
    	this.setResRootDir(resRootDir);
    	this.setResProvider(resProvider);
    }
    
    
    /*
     * Fields
     *********/
    
    private String resRootDir = null;
    private IStringResourcesProvider resProvider = null;
    
    
    /*
     * Methods 
     **********/
    
    public void setResRootDir(String resRootDir) {
    	this.resRootDir = resRootDir;
    }
    
    public void setResProvider(IStringResourcesProvider resProvider) {
    	this.resProvider = resProvider;
    }
    
    public void generateXMLs() {
    	
    	AndroidStringXML.StringResourcesReader resReader = new AndroidStringXML.StringResourcesReader(this.resProvider);
    	
    	try {

	    	String[] langs = resReader.getSupportedLangs();	
	    	if (langs.length > 0) {	    		

	    		String xmlBody = null;
	    		String xmlName = "strings.xml";
		    	AndroidStringXML.StringResourcesWriter resWriter = new AndroidStringXML.StringResourcesWriter();
	    		
		    	// Generate strings.xml files for all supported languages
	    		for (int i = 0; i < langs.length; i++) {
	    			
	    			xmlBody = resReader.getXML(langs[i]);
	    			
	    			try {
		    			if (xmlBody != null) {
			    			resWriter.writeXML(this.resRootDir + "/values-" + langs[i] + "/" + xmlName, xmlBody);
		    			} else {
		    				throw new MyException("No string resouces for the language, " + langs[i] + ", unable to generate the strings.xml!");
		    			}
	    			} catch (MyException e) {
	    	    		e.print1stPoint();
	    	    	}
	    		}
	    		
	    		// Generate strings.xml files for default language 			
    			try {
    	    		
    				xmlBody = resReader.getXML(resReader.getDefaultLang());
    				
    				// Try to use the 1st-met language string resource as the fallback resource.
	    			if (xmlBody == null) {	    				
	    				for (int i = 0; i < langs.length; i++) {
	    					xmlBody = resReader.getXML(langs[i]);
	    					if (xmlBody != null) {
	    						throw new MyException("No string resouces for the default language, " + resReader.getDefaultLang() + ", take the langauge resources, " + langs[i] + ", as the fallback!");
	    					}
	    				}	    				
	    				throw new MyException("No string resouces for the default language, " + resReader.getDefaultLang() + ", unable to generate the strings.xml!");
	    			}
	    			
    			} catch (MyException e) {
    				
    	    		e.print1stPoint();
    	    		
    	    	} finally {
    	    		
    	    		if (xmlBody != null) {
    	    			resWriter.writeXML(this.resRootDir + "/values/" + xmlName, xmlBody);
    	    		}
    	    	}
	    		
	    	} else {
	    		throw new MyException("The string resources have no supported language(s)! Unable to proceed!");
	    	}   
	    	
    	} catch (MyException e) {
    		e.print1stPoint();
    	}
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
    
        public String getDefaultLang() {
        	return this.provider.getDefaultLang();
        }
        
        public String[] getSupportedLangs() {
            return this.provider.getSupportedLangs();
        }
    }
    
    private static class StringResourcesWriter {
    	/*
    	 * Methods
    	 **********/
    	
    	public void writeXML(String dstPath, String xml) {    		
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
