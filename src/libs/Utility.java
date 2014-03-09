package libs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The class encompassing the utility classes and methods
 */
public class Utility {
	
	/**
	 * The utility class in charge of handling files
	 */
	public static class Files {
		
		/*
		 * Methods
		 **********/
		
		/**
		 * Buffer the File object for writing
		 * 
		 * @param f
		 * 		The File object to be buffered
		 * @return
		 * 		- If OK: One BufferedWriter object
		 * 		<br/>
		 * 		- If NG: null
		 */
		public static BufferedWriter bufferFileWriter(File f) {
    		try {
    			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
    			return bw;
			} catch (IOException e) {
				(new MyException("Fail to create the BufferedWriter instance!")).print1stPoint();
				return null;
			}
		}

		
		/**
		 * Buffer the File object for reading
		 * 
		 * @param f
		 * 		The File object to be buffered
		 * @return
		 * 		- If OK: One BufferedReader object
		 * 		<br/>
		 * 		- If NG: null
		 */
		public static BufferedReader bufferFileReader(File f) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				return br;
			} catch (FileNotFoundException e) {
				(new MyException("Fail to create the BufferedReader instance!")).print1stPoint();
				return null;
			}
		}
		
		/**
		 * Open(if not existing, then create) one file
		 * 
		 * @param dstPath
		 * 		The file path
		 * @param readable
		 * 		True = open the file as readable. False = not readable.
		 * @param writable
		 * 		True = open the file as writable. False = not writable.
		 * @param executable
		 * 		True = open the file as executable. False = not executable.
		 * @param ownerOnly
		 * 		True = open the file as owner only. False = not owner only.
		 * @return
		 * 		- If OK: One File object representing the opened file
		 * 		<br/>
		 * 		- If NG: null
		 */
		public static File openFile(String dstPath, Boolean readable, Boolean writable, Boolean executable, Boolean ownerOnly) {
    		// Open the file
    		File f = new File(dstPath);
    		if (!f.exists()) {
    			
    			try {    				
    				if (!f.getParentFile().mkdirs()) {
    					throw new MyException("Fail to make the dirs for the file path: " + dstPath + "!");
    				}
    			} catch(MyException e) {
    				e.print1stPoint();
    				return null;
    			}
    			
    			try {
    				f.createNewFile();
    			} catch (IOException e) {
    				(new MyException("Fail to create to the file:" + dstPath + "!")).print1stPoint();
    				return null;
    			}
    		}
    		
    		f.setReadable(readable, ownerOnly);
    		f.setWritable(writable, ownerOnly);
    		f.setExecutable(executable, ownerOnly);
    		
    		return f;
		}
		
		/**
		 * readFileAll(1): Read out all the file's content
		 * 
		 * @param f
		 *		The file object to be read
		 * @return
		 * 		- If OK: The file content
		 * 		<br/>
		 * 		- If NG: null
		 */
		public static String readFileAll(File f) {
						
			String s = null;
			
			if (f.isFile()) {
				
				BufferedReader br = Utility.Files.bufferFileReader(f);
				
				if (br != null) {
					
					StringBuilder sb = new StringBuilder();
						
					try {
						while ((s = br.readLine()) != null) {
							sb.append(s);
						}
						s = sb.toString();
					} catch (IOException e) {
						s = null;
						(new MyException("Fail to read the file:" + f.getPath() + "!")).print1stPoint();
					}
					
					try {
						br.close();
					} catch (IOException e) {
						(new MyException("Fail to close the file buffer:" + f.getPath() + "!")).print1stPoint();
					}
				}
			} else {				
				try {
					throw new MyException(f.getPath() + " is not a readable file!");
				} catch (MyException e) {
					e.print1stPoint();
				}				
			}
			
			return s;			
		}

		
		/**
		 * readFileAll(2): The overload method of this.readFileAll(1)
		 * 		
		 * @param path
		 * 		The path to the file to be read
		 * @return
		 */
		public static String readFileAll(String path) {
			
    		File f = new File(path);    		
    		
    		try {    			
    			if (f.exists()) {
    				f.setReadable(true, false);
    				return Utility.Files.readFileAll(f);
    			} else {
    				throw new MyException(path + " -> No such file to read!");
    			}    			
    		} catch (MyException e) {
    			e.print1stPoint();
    			return null;
    		}
		}
	
		/**
		 * deleteAll(1): Delete one file or one directory(including all stuff inside)
		 * 
		 * @param f
		 * 		The File object to be deleted
		 */
		public static void deleteAll(File f) {
			
			if (f.isDirectory()) {
				
	    		// directory is empty, then delete it
	    		if(f.list().length==0){
	 
	    		   f.delete();
	 
	    		}else{
	 
	    		   // list all the directory contents
	        	   String files[] = f.list();
	 
	        	   for (String temp : files) {
	        	      // construct the file structure
	        	      File fileDelete = new File(f, temp);
	 
	        	      // recursive delete
	        	      Utility.Files.deleteAll(fileDelete);
	        	   }
	 
	        	   // check the directory again, if empty then delete it
	        	   if(f.list().length==0){
	           	     f.delete();
	        	   }
	    		}			
				
			} else {
				
				f.delete();					
			}
		}
	
		/**
		 * deleteAll(2): The overload method of this.deleteAll(1)
		 * 		
		 * @param path
		 * 		The path to the file to be deleted
		 */
		public static void deleteAll(String path) {
			
    		File f = new File(path);    		
    		
    		try {    			
    			if (f.exists()) {
    				Utility.Files.deleteAll(f);
    			} else {
    				throw new MyException(path + " -> No such file or dir to delete!");
    			}    			
    		} catch (MyException e) {
    			e.print1stPoint();
    		}
		}
	}
}
