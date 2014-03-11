package androidStringXML;

import java.io.BufferedWriter;
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

/**
 * The class in charge of generating strings.xml files in various languages directories.
 */
public class AndroidStringXML {

    public AndroidStringXML() {        
    }
    
    /**
     * @param resRootDir
     * 		Refer to this.setResRootDir
     * @param resProvider
     * 		Refer to this.setResProvider
     */
    public AndroidStringXML(String resRootDir, IStringResourcesProvider resProvider) {
    	this.setResRootDir(resRootDir);
    	this.setResProvider(resProvider);
    }
    
    
    /*
     * Fields
     *********/
    
    /**
     * The root directory path storing the strings.xml files and the values/ directories. In the Android system, this should be the res/ directory.
     */
    private String resRootDir = null;
    
    /**
     * The string resources provider
     */
    private IStringResourcesProvider resProvider = null;
    
    
    /*
     * Methods 
     **********/
    
    /**
     * Set the root directory path storing the strings.xml files and the values/ directories.
     * 
     * @param resRootDir
     * 		The root directory path.
     */
    public void setResRootDir(String resRootDir) {
    	this.resRootDir = resRootDir;
    }
    
    /**
     * Set the string resources provider.
     * 
     * @param resProvider
     * 		The string resources provider
     */
    public void setResProvider(IStringResourcesProvider resProvider) {
    	this.resProvider = resProvider;
    }
    
    /**
     * Call this method to generate strings.xml files in various languages directories after setting up the resource root directory and the string resources provider.
     * @throws MyException 
     */
    public void generateXMLs() throws MyException {
    	
		if (this.resRootDir == null || this.resProvider == null) {
			throw new MyException("Unable to generate the strings.xml files: No resource root directory or no string resources provider!");
		}    	
		
		AndroidStringXML.StringResourcesReader resReader;
		try {
			resReader = new AndroidStringXML.StringResourcesReader(this.resProvider);		
		} catch (MyException e) {
			throw new MyException("Unable to generate the strings.xml files: " + e.getMessage());
		}
		
		String xmlBody = null;
		String xmlName = "strings.xml";
		String[] langs = resReader.getSupportedLangs();	
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
    }
    
    
    /*
     * Classes
     *********/
    
    /**
     * The class in charge of reading the parsed string resources from the string resources provider and then build up the content of the string.xml.
     */
    private static class StringResourcesReader {
        
    	/**
    	 * @param provider
    	 * 		The string resources provider who provides the parsed string resources
    	 * @throws MyException 
    	 */
        public StringResourcesReader(IStringResourcesProvider provider) throws MyException {
            this.provider = provider;
            this.readStringResources();
        }
        

        /*
         * Fields
         ***********/
        
        /**
         * The string resources provider
         */
        private IStringResourcesProvider provider;
        
        /**
         * The map storing the string.xml's body. The key is the language code and the value is the string.xml's body in the language specified by the key.
         */
        private Map<String, String> xmlBody = new HashMap<String, String>();
        
        /**
         * The map storing the name attribute's values which have already been declared. The key is the language cod and the value is the arrayList of the name attribute's values which have already been declared in the language specified by the key.
         */
        private Map<String, ArrayList<String>> nameMap = new HashMap<String, ArrayList<String>>();        
        
        
        /*
         * Methods
         **********/

        /**
         * Perform the work of reading the string resources from the string resources and then calling other private methods to build the strings.xml's content.
         * 
         * @return
         * 		The count of language of string resources read
         * @throws MyException 
         */
        private int readStringResources() throws MyException {            
           
            String[] langs = this.provider.getSupportedLangs();            
            if (langs == null ||langs.length <= 0) {
                throw new MyException("No supported langs are defined!");
            }
                
            String xml;
            int readCount = 0; // A flag to mark how many lang whose string resource has been read out
            for (String s : langs) {
            	
                xml = this.buildXML(s, this.provider.getStrings(s))
                	+ this.buildXML(s, this.provider.getStringArrays(s))
                	+ this.buildXML(s, this.provider.getQuantityStrings(s));
                
                 if (!xml.equals("")) {
                    readCount++;
                    this.xmlBody.put(s, xml);
                }
            }
            
            return readCount;
        }
        
        /**
         * Kick out the string resources which its name attribute's value is duplicated with another string resource's under one specific language.
         * Suppose there are 2 string elements, String A and String B, both having name attribute's value as "title" for the "en" language.
         * The StringResourcesReader reads String A first then String B. In this case String B would be kicked out because the StringResourcesReader's record shows one string with the same name arribute already exists.
         * 
         * @param lang
         * 		The string resources's language code
         * @param ls
         * 		The arrayList of the string resources
         * @return
         * 		One arrayList of the string resources which have unique name attribute to each other.
         * 		Please note that the string resources inside the returned arrayList all have been cast to the Object type.
         * 		Convert the string resources back to the desired type as appropriate.
         */
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
                
        /**
         * Build strings.xml's body for one language. The xml body doesn't include the header, the opening tag and the closing tag.
         * 
         * @param lang
         * 		The language to build
         * @param ls
         * 		The arrayList of string resources to build upon.
         * @return
         * 		- OK: The strings.xml's body for one language.
         *		<br/>
         * 		- NG: One empty string
         */
        private String buildXML(String lang, ArrayList<? extends AndroidStringBase> ls) {
        	
        	String xml = ""; 
        	
        	if (ls != null) {
        		
	        	ArrayList<Object> list = this.kickoutDulplicateName(lang, ls);
	        	
	        	if (list.size() > 0) {
	        		
	        		Class targetCls;
	        		StringBuilder sb = new StringBuilder();
	        		
	        		try {
	        		
		        		for (Object o : list) {
		        			
		        			targetCls = o.getClass();
		        			
		        			if (targetCls == AndroidString.class) {	
		        				
		        				sb.append(AndroidStringXMLFormat.formatString((AndroidString) o));
		        				
		        			} else if (targetCls == AndroidStringArray.class) {	
		        				
		        				sb.append(AndroidStringXMLFormat.formatStringArray((AndroidStringArray) o));
		        				
		        			} else if (targetCls == AndroidQuantityString.class) {
		        				
		        				sb.append(AndroidStringXMLFormat.formatQuantityString((AndroidQuantityString) o));
		        				
		        			} else {
		        				throw new MyException("Unknown resouce class : " + targetCls.toString());
		        			}
		        		}
		        		
		        		xml = sb.toString();
	        		
	        		} catch (MyException e) {
	        			e.print1stPoint();
	        		}
	        	}
        	}
        	
        	return xml;
        }
            
        /**
         * Get the complete strings.xml's content for one language.
         * 
         * @param lang
         * 		The language to get
         * @return
         * 		- If available: Strings.xml's body for one language.
         * 		- If unavailable: null
         */
        public String getXML(String lang) {            
        	String xmlBody = this.xmlBody.get(lang);            
            return (xmlBody == null) ?
                    null :
                    AndroidStringXMLFormat.header + AndroidStringXMLFormat.openingTag + xmlBody + AndroidStringXMLFormat.closingTag;                   
        }
    
        /**
         * @return
         * 		Refer to the interface IStringResourcesProvider.getDefaultLang
         */
        public String getDefaultLang() {
        	return this.provider.getDefaultLang();
        }

        /**
         * @return
         * 		Refer to the interface IStringResourcesProvider.getSupportedLangs
         */
        public String[] getSupportedLangs() {
            return this.provider.getSupportedLangs();
        }
    }
    
    /**
     * The class in charge of writing the content of the string.xml files to the various values/ directories.
     */
    private static class StringResourcesWriter {
    	/*
    	 * Methods
    	 **********/
    	
    	/**
    	 * Perform the work of writing the content of the string.xml files to the various values/ directories.
    	 * 
    	 * @param dstPath
    	 * 		The destine path to write
    	 * @param xml
    	 * 		The xml content to write
    	 */
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
