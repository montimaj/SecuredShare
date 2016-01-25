package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
/**
 * Class for various file functions
 * @author Sayantan Majumdar
 * @since 1.0
 */

public class FileOperations {
	/**
	 * Reads a file
	 * @param file Input file
	 * @param flag boolean variable 
	 * @return Base64 decoded bytes if flag is true, normal bytes otherwise
	 * @throws IOException
	 */
	public static byte[] readFile(String file, boolean flag) throws IOException {
		FileInputStream fis=new FileInputStream(file);
		byte[] b=new byte[fis.available()];
		fis.read(b);
		fis.close();
		if(flag)
			return Base64.decodeBase64(b);
		return b;
	}	
	/**
	 * Read file contents
	 * @param s
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String s) throws IOException
	{
		FileInputStream fis=new FileInputStream(s);
		String data="";
		int c;
		while((c=fis.read())!=-1)
			data+=(char)c;		
		fis.close();
		return data;				
	}	
	/**
	 * Writes Base64 encoded bytes to a file if flag is true, normal bytes otherwise
	 * @param data Input bytes
	 * @param outFile Output file
	 * @param flag boolean variable
	 * @throws IOException
	 */
	public static void writeToFile(byte[] data, String outFile, boolean flag) throws IOException {
		FileOutputStream fos=new FileOutputStream(outFile);
		if(flag)
			fos.write(Base64.encodeBase64(data));
		else
			fos.write(data);
		fos.close();				
	}
	/**
	 * Checks whether the input file is a pdf file
	 * @param file Input file 
	 * @return true if pdf false otherwise
	 */
	public static boolean is_pdf(String file)
	{
		String ext=file.substring(file.lastIndexOf('.')+1, file.length());
		if(ext.equalsIgnoreCase("pdf"))
			return true;
		return false;		
	}	
	/**
	 * Deletes files
	 * @param files Files to be deleted
	 */
	public static void deleteFiles(String... files) {
		for(int i=0;i<files.length;++i)
			new File(files[i]).delete();
	}
}
