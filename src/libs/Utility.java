package libs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Utility {
	public static class Files {
		
		public static File openFile(String dstPath, Boolean readable, Boolean writable, Boolean executable, Boolean ownerOnly) {
    		// Open the file
    		File f = new File(dstPath);
    		if (!f.exists()) {
    			
    			try {    				
    				if (!f.getParentFile().mkdirs()) {
    					throw new MyException("Fail to make the dirs for the file path" + dstPath + "!");
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
	}
}
