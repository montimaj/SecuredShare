package stegano;

import java.io.IOException;

import main.*;
/**
 * Steganography module
 * @author Sayantan Majumdar
 * @since 1.0
 */

public class DoStegano {     
	private String zipFileName;
	/**
	 * Call this constructor to encode data inside a JPEG image
	 * @param imgfile Path to the image
	 * @param dir Output directory
	 * @throws IOException
	 */
	public DoStegano(String inFile, String imgFile, String dir) throws IOException	{     
		  encode(inFile,imgFile, dir);		  
	}
	/**
	 * Call this constructor to decode a steg image
	 * @param imgfile Path to the image
	 * @throws IOException
	 */
	public DoStegano(String imgFile, String dir) throws IOException {
		decode(imgFile, dir);
	}
	/**
	 * Get the extracted zip file name
	 * @return String 
	 */
	public String getExtractedFile() {
		return zipFileName;
	}		
	private void encode(String inFile, String imgFile, String dir) throws IOException {
		String s[]={"-e",inFile,imgFile,dir+"/steg.jpg"};
		Embed.main(s);		
	}
	private void decode(String imgFile, String dir) throws IOException {
		zipFileName=dir+"/extracted.zip";
		String s[]={"-e",zipFileName,imgFile};
		Extract.main(s);			  
	}	
}