package main;

import java.io.IOException;

import org.apache.pdfbox.ExtractText;

import crypto.Encrypt;
import crypto.signature.GenSig;

import stegano.DoStegano;

import util.FileOperations;
import util.Log;
import util.ZipCreator;

/**
 * Takes plain text file path as input and uses 
 * <p>
 * {@link crypto.Encrypt} to generate out.zip which consists of a key file, cipher, signature and public key
 * @author Sayantan Majumdar
 * @since 1.0
 */
public class UserInput {

	/**
	 * Main module 
	 * @param args Input arguments where
	 * <p>
	 * args[0]->Input file
	 * args[1]->Input image
	 * args[2]->Output directory
	 * </p>
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException	{
		Process p=null;
		String[] inFiles={""};
		try	{
			if(args.length<3 || args[0].isEmpty() || args[1].isEmpty() || args[2].isEmpty())
				throw new IOException("Invalid Input"); 
			String[] x={"zenity","--progress","--pulsate","--no-cancel","--text=Encrypting..."};
			p=new ProcessBuilder(x).start();			
			if(FileOperations.is_pdf(args[0])) {	    	  
		    	  String[] s={args[0], args[2]+"/out.txt"};
		    	  ExtractText.main(s); //extract text from pdf file
		    	  args[0]=args[2]+"/out.txt";
		    }			
			new Encrypt(args[0],args[2]);
			new GenSig(args[0],args[2]);	
			String[] files={args[2]+"/cipher.txt",args[2]+"/key.txt",args[2]+"/pub.txt",args[2]+"/sig.txt"};			
			inFiles=files;
			ZipCreator.createZip(args[2]+"/out.zip", files);
			new DoStegano(args[2]+"/out.zip",args[1], args[2]);			
			p.destroy();
			String x1[]={"zenity","--info","--title=Result","--text=Done!"};
			p=new ProcessBuilder(x1).start();
			p.waitFor();
		}catch(Exception e) {
			if(p!=null)
				p.destroy();
			String s=Log.createLog(args[2], e), x[]={"zenity","--error","--text="+s};
			p=new ProcessBuilder(x).start();
			p.waitFor();
		}finally {
			FileOperations.deleteFiles(inFiles);			
		}
	}
}
