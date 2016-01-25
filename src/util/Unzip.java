package util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Extracts zip file
 * @author Sayantan Majumdar
 */
 public class Unzip {
    private static final int BUFFER_SIZE = 4096;

	/**
	 *
	 * @param zipFilePath input zip file path
	 * @param destDirectory output directory for storing extracted files
	 * @return array of Strings containing filepaths of the extracted files
	 * @throws IOException
	 */
    public static String[] unzip(String zipFilePath, String destDirectory) throws IOException {
    	ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
	    ZipEntry entry = zipIn.getNextEntry();
	    String extractedFiles[]=new String[4];
	    int index=0;
	    while (entry != null) {
	    	String ent=entry.getName(),filePath = destDirectory + "/" + ent;
	        if (!entry.isDirectory()) {
	            extractFile(zipIn, filePath);
	            extractedFiles[index++]=filePath;
	        }
	        else {
	        	File dir = new File(filePath);
	            dir.mkdir();
	        }
	        zipIn.closeEntry();
	        entry = zipIn.getNextEntry();
	    }
	    zipIn.close();
	    return extractedFiles;
    }    
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }    
}