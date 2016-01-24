package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

/**
 * User library to create a zip file	
 * @author Sayantan Majumdar
 * @since 1.0
*/
public class ZipCreator
{   
	/**
	 * Create a zip file
	 * @param zipFile Output zip file path
	 * @param infiles input files to be zipped
	 * @throws IOException
	 */
	public static void createZip(String zipFile,String[] infiles) throws IOException
	{        
		byte[] buffer = new byte[4096];	 
	    FileOutputStream fos = new FileOutputStream(zipFile);	 
	    ZipOutputStream zos = new ZipOutputStream(fos);	             
	    for (int i=0;i<infiles.length;i++)
	    {	                 
	    	File srcFile = new File(infiles[i]);	 
	        FileInputStream fis = new FileInputStream(srcFile);                
	        zos.putNextEntry(new ZipEntry(srcFile.getName()));	                 
	        int length;	 
	        while ((length = fis.read(buffer)) > 0)	           
	        	zos.write(buffer, 0, length);
	        zos.closeEntry();              
	        fis.close();	                 
	    }           
	    zos.close();       
	} 
}