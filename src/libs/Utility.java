package libs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.NoSuchFileException;

public class Utility {
	public static class Files {
		
		/*
		 * Methods
		 **********/
		
		public static BufferedWriter bufferFileWriter(File f) {
    		try {
    			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
    			return bw;
			} catch (IOException e) {
				(new MyException("Fail to create the BufferedWriter instance!")).print1stPoint();
				return null;
			}
		}
		
		public static BufferedReader bufferFileReader(File f) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				return br;
			} catch (FileNotFoundException e) {
				(new MyException("Fail to create the BufferedReader instance!")).print1stPoint();
				return null;
			}
		}
		
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
		
		public static String readFileAll(File f) {
						
			String s;
			BufferedReader br = Utility.Files.bufferFileReader(f);
			
			if (br == null) {
				s = null;
				
			} else {
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
			
			return s;			
		}
	
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
